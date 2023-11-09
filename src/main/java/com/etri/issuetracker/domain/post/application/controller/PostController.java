package com.etri.issuetracker.domain.post.application.controller;

import com.etri.issuetracker.domain.post.Infra.APIService;
import com.etri.issuetracker.domain.post.application.dto.PostDTO;
import com.etri.issuetracker.domain.post.application.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "http://192.168.0.2:3000/*")
public class PostController {

    private final PostService postService;

    private final APIService apiService;

    @Autowired
    public PostController(PostService postService, APIService apiService) {
        this.postService = postService;
        this.apiService = apiService;
    }


    // Create
    @Operation(summary = "글 등록", description = "사용자가 만든 글 저장")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "게시글 저장 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "게시글 저장 실패"
            )
    })
    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> createPost(@RequestBody PostDTO createDTO) {
        PostDTO post = postService.createPost(createDTO);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    // Read
    @Operation(summary = "글 전체 조회", description = "게시글 전부 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 전체 조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "게시글 전체 조회 실패"
            )
    })
    @GetMapping("/all")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> findAll() {
        List<PostDTO> posts = postService.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(summary = "postId로 글 조회", description = "게시글 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 상세 조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "게시글 상세 조회 실패"
            )
    })
    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        PostDTO findPost = postService.findById(id);
        return new ResponseEntity<>(findPost, HttpStatus.OK);
    }

    // Update
    @Operation(summary = "제목, 내용 수정", description = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "게시글 수정 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "게시글 수정 실패"
            )
    })
    @PutMapping("/update")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updatePost(@RequestBody PostDTO updateDTO) {
        boolean result = postService.updatePost(updateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Delete
    @Operation(summary = "postId로 글 삭제", description = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 삭제 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "게시글 삭제 실패"
            )
    })
    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    // ===========================test=====================================

//    @GetMapping("/test")
//    public ResponseEntity<Object> test() {
//        Object result = apiService.analyze();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        String jsonString = null;
//        try {
//            jsonString = objectMapper.writeValueAsString(result);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("jsonString = " + jsonString);
//        // JSON 객체를 생성합니다.
//        JSONObject jsonObject = new JSONObject(jsonString);
//
//        JSONObject body = jsonObject.getJSONObject("body");
//        // "sentence" 키의 배열 값을 가져옵니다.
//        JSONArray sentences = body.getJSONObject("return_object").getJSONArray("sentence");
//
//        // 각 문장에서 NNP 및 NNG 단어를 추출하고 출력합니다.
//        for (int i = 0; i < sentences.length(); i++) {
//            JSONObject sentence = sentences.getJSONObject(i);
//            JSONArray morp = sentence.getJSONArray("morp");
//
//            System.out.println("2");
//            for (int j = 0; j < morp.length(); j++) {
//                JSONObject morpInfo = morp.getJSONObject(j);
//                String lemma = morpInfo.getString("lemma");
//                String type = morpInfo.getString("type");
//
//                // NNP 및 NNG 단어인 경우 출력
//                if (type.equals("NNP") || type.equals("NNG")) {
//                    System.out.println("단어: " + lemma + ", 형태: " + type);
//                }
//            }
//        }
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }


}
