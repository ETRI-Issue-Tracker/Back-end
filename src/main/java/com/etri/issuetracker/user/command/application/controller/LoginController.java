package com.etri.issuetracker.user.command.application.controller;

import com.etri.issuetracker.user.command.application.dto.LoginDTO;
import com.etri.issuetracker.user.command.application.service.LoginService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;
    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpSession httpSession){
        return loginService.checkUser(loginDTO,httpSession);
    }
}
