package com.etri.issuetracker.user.command.domain.service;

import com.etri.issuetracker.user.command.application.dto.SignUpInfoDTO;
import com.etri.issuetracker.user.command.domain.aggregate.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberSerivce {
    public Member DtotoEntity(SignUpInfoDTO signUpInfoDTO){
        return new Member(signUpInfoDTO.getUid(), signUpInfoDTO.getPassword(), signUpInfoDTO.getNickname());
    }
}
