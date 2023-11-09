package com.etri.issuetracker.domain.post.application.service;

import com.etri.issuetracker.domain.post.domain.entity.Word;
import com.etri.issuetracker.domain.post.domain.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {

    private final WordRepository wordRepository;

    @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> findByWords(){
        return wordRepository.findAll();
    }
}
