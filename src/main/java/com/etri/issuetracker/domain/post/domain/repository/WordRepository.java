package com.etri.issuetracker.domain.post.domain.repository;

import com.etri.issuetracker.domain.post.domain.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
}
