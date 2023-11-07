package com.etri.issuetracker.domain.post.Infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class APIService {

    private final WebClient webClient;

    @Autowired
    public APIService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<Object> classifier(String sentence_1,String sentence_2){
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("request_id", "jym7546@gmail,com");
        Map<String, String> sentenceMap = new HashMap<>();

        sentenceMap.put("sentence1",sentence_1);
        sentenceMap.put("sentence2",sentence_2);

        bodyMap.put("argument", sentenceMap);

        return webClient
                .post()
                .uri("/ParaphraseQA")
                .bodyValue(bodyMap)
                .retrieve()
                .toEntity(Object.class)
                .block();
    }

    public String bjectToString(Object result) {
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
}
