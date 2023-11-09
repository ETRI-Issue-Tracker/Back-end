package com.etri.issuetracker.domain.post.domain.repository;

import com.etri.issuetracker.domain.post.domain.entity.Post;

import java.util.List;

public interface PostCustomRepository {

    // 공격적인 글
    List<Post> findByAggressive();

    // 편향적인 글
    List<Post> findByBiased();

    // 유해 콘텐츠
    List<Post> findByBlock();

    // 동조적 현상 글
    List<Post> findByEcho();
}
