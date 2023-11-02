package com.etri.issuetracker.domain.post.domain.entity;

import com.etri.issuetracker.domain.post.domain.entity.enumType.Block;
import com.etri.issuetracker.domain.post.domain.entity.vo.MemberVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Post")
@Table(name = "POST_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Embedded
    private MemberVO memberId;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    // 유해 컨텐츠
    @Column
    private Block block;

    // 동조적 현상
    @Column
    private boolean echo;

    public Post(String title, String content, MemberVO memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.createdDate = LocalDateTime.now();
        this.block = Block.NORMAL;
        this.echo = false;
    }

}
