package com.etri.issuetracker.domain.post.application.dto;

import com.etri.issuetracker.domain.post.domain.entity.Post;
import com.etri.issuetracker.domain.post.domain.entity.enumType.Block;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Schema(description = "Post DTO 객체")
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long id;

    private String title;

    private String content;

    private Long memberId;

    private LocalDateTime createdDate;

    private Block block;

    private boolean echo;


    public static PostDTO entityToDto(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMemberId().getId(),
                post.getCreatedDate(),
                post.getBlock(),
                post.isEcho()
        );
    }
}
