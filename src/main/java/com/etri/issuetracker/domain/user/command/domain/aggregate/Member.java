package com.etri.issuetracker.domain.user.command.domain.aggregate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER_TB")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UID")
    private String uid;

    @Column(nullable = false)
    private String password;

    public Member(String uid, String password, String nickname) {
        this.uid = uid;
        this.password = password;
        this.nickname = nickname;
    }

    @Column(nullable = false)
    private String nickname;
}
