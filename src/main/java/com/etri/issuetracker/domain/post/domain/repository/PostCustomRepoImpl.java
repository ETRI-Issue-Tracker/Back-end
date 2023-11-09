package com.etri.issuetracker.domain.post.domain.repository;

import com.etri.issuetracker.domain.post.domain.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PostCustomRepoImpl implements PostCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Post> findByAggressive() {
        List<Post> reslutList = entityManager.createQuery("SELECT p FROM Post p WHERE p.block = 1", Post.class).getResultList();
        return reslutList;
    }

    @Override
    public List<Post> findByBiased() {
        List<Post> reslutList = entityManager.createQuery("SELECT p FROM Post p WHERE p.block = 2", Post.class).getResultList();
        return reslutList;
    }

    @Override
    public List<Post> findByBlock() {
        List<Post> reslutList = entityManager.createQuery("SELECT p FROM Post p WHERE p.block = 3", Post.class).getResultList();
        return reslutList;
    }

    @Override
    public List<Post> findByEcho() {
        List<Post> reslutList = entityManager.createQuery("SELECT p FROM Post p WHERE p.echo = 1", Post.class).getResultList();
        return reslutList;
    }
}
