package com.etri.issuetracker.domain.user.command.domain.service;

import com.etri.issuetracker.domain.user.command.domain.aggregate.Member;
import com.etri.issuetracker.domain.user.command.application.dto.SignUpInfoDTO;
import org.springframework.stereotype.Service;

@Service
public class MemberSerivce {
    public Member DtotoEntity(SignUpInfoDTO signUpInfoDTO){
        return new Member(signUpInfoDTO.getUid(), signUpInfoDTO.getPassword(), signUpInfoDTO.getNickname());
    }
}
