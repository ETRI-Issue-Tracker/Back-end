package com.etri.issuetracker.user.command.application.service;

import com.etri.issuetracker.user.command.application.dto.LoginDTO;
import com.etri.issuetracker.user.command.application.dto.UserInfoDTO;
import com.etri.issuetracker.user.command.domain.aggregate.Member;
import com.etri.issuetracker.user.command.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public ResponseEntity<?> checkUser(LoginDTO loginDTO, HttpSession httpSession){
        if(memberRepository.findByUid(loginDTO.getUid()).isPresent()){
            Member member = memberRepository.findByUid(loginDTO.getUid()).get();
            if(member.getPassword().equals(loginDTO.getPassword())){
                httpSession.setAttribute("nickname", member.getNickname());
                httpSession.setAttribute("uid", member.getUid());
                return ResponseEntity.ok(new UserInfoDTO(member.getNickname(),member.getUid())) ;
            }else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("password가 틀렸습니다.");
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 id입니다.");
        }

    }

}
