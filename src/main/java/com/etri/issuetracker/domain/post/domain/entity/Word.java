package com.etri.issuetracker.domain.post.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Word")
@Table(name="WORD_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String word;

    @Column
    private int count;

    public Word(Long id, int count) {
        this.id = id;
        this.count = count;
    }

    public Word(String word, int count) {
        this.word = word;
        this.count = count;
    }
}
