package com.etri.issuetracker.domain.post.Infra;

import com.etri.issuetracker.domain.post.domain.entity.Word;
import com.etri.issuetracker.domain.post.domain.repository.WordRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class APIService {

    private final WebClient webClient;

    private final WordRepository wordRepository;

    @Autowired
    public APIService(WebClient webClient, WordRepository wordRepository) {
        this.webClient = webClient;
        this.wordRepository = wordRepository;
    }


    public ResponseEntity<Object> classifier(String sentence_1, String sentence_2) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("request_id", "jym7546@gmail,com");
        Map<String, String> sentenceMap = new HashMap<>();

        sentenceMap.put("sentence1", sentence_1);
        sentenceMap.put("sentence2", sentence_2);

        bodyMap.put("argument", sentenceMap);

        return webClient
                .post()
                .uri("/ParaphraseQA")
                .bodyValue(bodyMap)
                .retrieve()
                .toEntity(Object.class)
                .block();
    }

    public ResponseEntity<Object> classification(String sentence) {
        Map<String, String> sentenceMap = new HashMap<>();

        sentenceMap.put("sentence", sentence);
        return WebClient.builder()
                .baseUrl("http://localhost:8000/classification")
                .build()
                .post()
                .bodyValue(sentenceMap)
                .retrieve()
                .toEntity(Object.class)
                .block();
    }

    public String objectToString(Object result) {
        ObjectMapper objectMapper = new ObjectMapper();
        String resultJson = null;
        String resultString;
        try {
            resultJson = objectMapper.writeValueAsString(result);

            JSONObject jsonObject = new JSONObject(resultJson);
            JSONObject body = jsonObject.getJSONObject("body");
            JSONObject returnObject = body.getJSONObject("return_object");

            resultString = returnObject.getString("result");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }

    public ResponseEntity<Object> analyze(String text) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("request_id", "jym7546@gmail,com");
        Map<String, String> sentenceMap = new HashMap<>();

        sentenceMap.put("analysis_code", "morp");
        sentenceMap.put("text", text);


        bodyMap.put("argument", sentenceMap);
        return webClient
                .post()
                .uri("/WiseNLU")
                .bodyValue(bodyMap)
                .retrieve()
                .toEntity(Object.class)
                .block();
    }


    public void objectToString_v1(Object result) {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // JSON 객체를 생성합니다.
        JSONObject jsonObject = new JSONObject(jsonString);

        JSONObject body = jsonObject.getJSONObject("body");
        // "sentence" 키의 배열 값을 가져옵니다.
        JSONArray sentences = body.getJSONObject("return_object").getJSONArray("sentence");

        // 각 문장에서 NNP 및 NNG 단어를 추출하고 출력합니다.
        for (int i = 0; i < sentences.length(); i++) {
            JSONObject sentence = sentences.getJSONObject(i);
            JSONArray morp = sentence.getJSONArray("morp");

            System.out.println("2");
            for (int j = 0; j < morp.length(); j++) {
                JSONObject morpInfo = morp.getJSONObject(j);
                String lemma = morpInfo.getString("lemma");
                String type = morpInfo.getString("type");

                // NNP 및 NNG 단어인 경우 출력
                if (type.equals("NNP") || type.equals("NNG")) {
                    System.out.println("단어: " + lemma + ", 형태: " + type);
                    List<Word> words = wordRepository.findAll();
                    int count = 0;
                    // 단어 있으면 찾아서 카운트 증가
                    for (int k = 0; k < words.size(); k++) {
                        if (words.get(k).getWord().equals(lemma)) {
                            Long id = words.get(k).getId();
                            int newCount=words.get(k).getCount()+1;
                            wordRepository.save(new Word(id,lemma,newCount));
                            count++;
                            break;
                        }
                    }
                    // 없으면 save
                    if (count == 0) {
                        wordRepository.save(new Word(lemma, 1));
                    }
                }
            }
        }


    }
}
