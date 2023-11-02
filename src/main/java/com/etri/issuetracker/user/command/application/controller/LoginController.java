package com.etri.issuetracker.user.command.application.controller;

import com.etri.issuetracker.user.command.application.dto.LoginDTO;
import com.etri.issuetracker.user.command.application.dto.SignUpInfoDTO;
import com.etri.issuetracker.user.command.application.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "로그인", description = "사용자가 로그인을 함")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!, 로그인 성공", content = @Content(schema = @Schema(implementation = LoginDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!, 존재하지 않는 id입니다.", content = @Content()),
            @ApiResponse(responseCode = "406", description = "NOT FOUND !!, password가 틀렸습니다.", content = @Content()),
    })
    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpSession httpSession){
        return loginService.checkUser(loginDTO,httpSession);
    }
}
