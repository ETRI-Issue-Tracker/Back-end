package com.etri.issuetracker.domain.user.command.application.service;

import com.etri.issuetracker.domain.user.command.domain.repository.MemberRepository;
import com.etri.issuetracker.domain.user.command.domain.service.MemberSerivce;
import com.etri.issuetracker.domain.user.command.application.dto.SignUpInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final MemberRepository memberRepository ;
    private final MemberSerivce memberSerivce;
    public ResponseEntity<?> signUp(SignUpInfoDTO signUpInfoDTO) {
        if(memberRepository.findByUid(signUpInfoDTO.getUid()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 id입니다.");
        }else{
            memberRepository.save(memberSerivce.DtotoEntity(signUpInfoDTO));
            return ResponseEntity.ok("정상적으로 회원가입 되었습니다.");
        }
    }
}
