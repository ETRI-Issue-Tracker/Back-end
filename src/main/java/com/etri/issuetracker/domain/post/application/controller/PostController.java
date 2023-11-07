package com.etri.issuetracker.domain.post.application.controller;

import com.etri.issuetracker.domain.post.application.dto.PostDTO;
import com.etri.issuetracker.domain.post.application.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
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
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    // ===========================test=====================================

    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        Object result = postService.test();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
