package com.etri.issuetracker.user.command.application.controller;

import com.etri.issuetracker.user.command.application.dto.SignUpInfoDTO;
import com.etri.issuetracker.user.command.application.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignUpController {

    private final SignUpService signUpService;
    @PostMapping
    public ResponseEntity<?>  signUp(@RequestBody SignUpInfoDTO signUpInfoDTO){
        return signUpService.signUp(signUpInfoDTO);
    }
}
