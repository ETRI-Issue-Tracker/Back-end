package com.etri.issuetracker.domain.post.application.service;

import com.etri.issuetracker.domain.post.Infra.APIService;
import com.etri.issuetracker.domain.post.application.dto.PostDTO;
import com.etri.issuetracker.domain.post.domain.entity.Post;
import com.etri.issuetracker.domain.post.domain.entity.enumType.Block;
import com.etri.issuetracker.domain.post.domain.entity.vo.MemberVO;
import com.etri.issuetracker.domain.post.domain.repository.PostCustomRepoImpl;
import com.etri.issuetracker.domain.post.domain.repository.PostRepository;
import com.etri.issuetracker.domain.user.command.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final APIService apiService;

    private final PostCustomRepoImpl postCustomRepo;
    private  final MemberRepository memberRepository;

//    private final WebClient webClient;

    @Autowired
    public PostService(PostRepository postRepository, APIService apiService, PostCustomRepoImpl postCustomRepo, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.apiService = apiService;
        this.postCustomRepo = postCustomRepo;
        this.memberRepository = memberRepository;
    }



    // Create
    public PostDTO createPost1(PostDTO createDTO) {
        MemberVO memberId = MemberVO.builder().memberId(createDTO.getMemberId()).build();
        Post newPost = postRepository.save(new Post(createDTO.getTitle(), createDTO.getContent(), memberId));

        return new PostDTO(
                newPost.getId(),
                newPost.getTitle(),
                newPost.getContent(),
                newPost.getMemberId().getId(),
                newPost.getCreatedDate(),
                newPost.getBlock(),
                newPost.isEcho()
        );
    }

    public PostDTO createPost(PostDTO createDTO) {
        MemberVO memberId = MemberVO.builder().memberId(createDTO.getMemberId()).build();

        Object analyzeResult = apiService.analyze(createDTO.getContent());


        LinkedHashMap<String, Integer> classification = (LinkedHashMap<String, Integer>) apiService.classification(createDTO.getContent()).getBody();
        int classificationResult = classification.get("result");
        Block status = null;
        switch (classificationResult) {
            case 0:
                status = Block.NORMAL;
                break;
            case 1:
                status = Block.AGGRESSIVE;
                break;
            case 2:
                status = Block.BIASED;
                break;
            case 3:
                status = Block.BOTH;
                break;
        }

        Post newPost = postRepository.save(new Post(createDTO.getTitle(), createDTO.getContent(), memberId, status));

        int count = 0;
        int postCount = postRepository.findAll().size() - 1;
        for (int i = postCount; i > postCount - 5 && i > 0; i--) {
            System.out.println("i = " + i);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Optional<Post> post = postRepository.findById((long) i);
            Object result = apiService.classifier(newPost.getContent(), post.get().getContent());

            System.out.println("newPost.getContent()" + newPost.getContent());
            System.out.println("post.get().getContent()" + post.get().getContent());

            String resultString = apiService.objectToString(result);

            System.out.println("resultString = " + resultString);

            if (resultString.equals("paraphrase")) {
                if (post.get().isEcho()) {
                    apiService.objectToString_v1(analyzeResult);
                    postRepository.save(new Post(newPost.getId(), newPost.getTitle(), newPost.getContent(), memberId, newPost.getBlock(),true));
                    return new PostDTO(
                            newPost.getId(),
                            newPost.getTitle(),
                            newPost.getContent(),
                            newPost.getMemberId().getId(),
                            newPost.getCreatedDate(),
                            newPost.getBlock(),
                            true
                    );
                } else {
                    count++;
                }
            }
        }

        if (count >= 2) {
            postRepository.save(new Post(newPost.getId(), newPost.getTitle(), newPost.getContent(), memberId, newPost.getBlock(),true));
            apiService.objectToString_v1(analyzeResult);
            return new PostDTO(
                    newPost.getId(),
                    newPost.getTitle(),
                    newPost.getContent(),
                    newPost.getMemberId().getId(),
                    newPost.getCreatedDate(),
                    newPost.getBlock(),
                    true
            );

        } else {
            return new PostDTO(
                    newPost.getId(),
                    newPost.getTitle(),
                    newPost.getContent(),
                    newPost.getMemberId().getId(),
                    newPost.getCreatedDate(),
                    newPost.getBlock(),
                    newPost.isEcho()
            );
        }
    }

    // Update
    public boolean updatePost(PostDTO updateDTO) {
        Optional<Post> beforePost = postRepository.findById(updateDTO.getId());
        if (beforePost.isPresent()) {
            Post afterPost = beforePost.get();
            if (!updateDTO.getTitle().isEmpty()) {
                afterPost.setTitle(updateDTO.getTitle());
            }
            if (!updateDTO.getContent().isEmpty()) {
                afterPost.setContent(updateDTO.getContent());
            }
            postRepository.save(afterPost);
            return true;
        }
        return false;
    }

    // Delete
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // Read
    public List<PostDTO> findAll() {
        List<Post> posts = postRepository.findAll();
        System.out.println("posts = " + posts);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO =PostDTO.entityToDto(post);
            postDTO.setUid(memberRepository.findById(post.getMemberId().getId()).get().getUid());
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    public PostDTO findById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        System.out.println("post.get().toString() = " + post.get().toString());
        if (post.isPresent()) {
            return PostDTO.entityToDto(post.get());
        } else {
            return null;
        }
    }


//    // 추가 기능 (유해 콘텐츠 판별 및 동조적 현상 판별)
//    public ResponseEntity<Object> test(){
//        Map<String, Object> bodyMap = new HashMap<>();
//        bodyMap.put("request_id", "jym7546@gmail,com");
//        Map<String, String> sentenceMap = new HashMap<>();
//        // 비교할 문장 1
//        sentenceMap.put("sentence1", "해변에서 파도 소리를 듣며 햇빛이 푸른 하늘에 미소 짓는 아름다운 풍경은 마치 자연의 음악처럼 우리를 감동시킵니다. 바닷가는 고요함과 평화를 선사하고, 파도는 모래에 부딪히며 우리에게 안정감을 줍니다.");
//        // 비교할 문장 2
//        sentenceMap.put("sentence2", "푸른 하늘 아래 한적한 마을, 그곳에 사는 사람들은 서로를 알고 존중합니다. 아침에는 새소리가 귀에 쏙 들며, 밤에는 별들이 하늘을 가득 채웁니다. 자연과 어우러지는 생활은 마음을 풍부하게 만들며, 스트레스로부터 해방시켜줍니다.진짜로");
//
//        bodyMap.put("argument", sentenceMap);
//
//        return webClient
//                .post()
//                .uri("/ParaphraseQA")
//                .bodyValue(bodyMap)
//                .retrieve()
//                .toEntity(Object.class)
//                .block();
//    }

    public List<Post> findByAggressive(){
        return postCustomRepo.findByAggressive();
    }
    public List<Post> findByBiased(){
        return postCustomRepo.findByBiased();
    }
    public List<Post> findByBlock(){
        return postCustomRepo.findByBlock();
    }
    public List<Post> findByEcho(){
        return postCustomRepo.findByEcho();
    }
}
