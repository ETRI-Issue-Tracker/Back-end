package com.etri.issuetracker.domain.post.application.service;

import com.etri.issuetracker.domain.post.application.dto.PostDTO;
import com.etri.issuetracker.domain.post.domain.entity.Post;
import com.etri.issuetracker.domain.post.domain.entity.vo.MemberVO;
import com.etri.issuetracker.domain.post.domain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Create
    public PostDTO createPost(PostDTO createDTO) {
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
        System.out.println("통과!");
        List<Post> posts = postRepository.findAll();
        System.out.println("posts = " + posts);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            postDTOs.add(PostDTO.entityToDto(post));
        }
        return postDTOs;
    }

    public PostDTO findById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            return PostDTO.entityToDto(post.get());
        }else {
            return null;
        }
    }


    // 추가 기능 (유해 콘텐츠 판별 및 동조적 현상 판별)


}
