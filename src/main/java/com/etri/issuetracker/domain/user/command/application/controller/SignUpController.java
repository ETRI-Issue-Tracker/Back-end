package com.etri.issuetracker.domain.user.command.application.controller;

import com.etri.issuetracker.domain.user.command.application.dto.SignUpInfoDTO;
import com.etri.issuetracker.domain.user.command.application.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
@CrossOrigin(origins = "http://192.168.0.2:3000/*")
public class SignUpController {

    private final SignUpService signUpService;
    @Operation(summary = "회원가입", description = "사용자가 회원가입을 함")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!, 회원가입 성공", content = @Content()),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!, 이미 존재하는 id입니다.", content = @Content()),
    })
    @PostMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<?>  signUp(
            @Parameter(description = "SignUpInfoDTO", required = true, example = "{\n" +
                    "    \"uid\" : \"uid\",\n" +
                    "    \"password\" : \"password\",\n" +
                    "    \"nickname\" : \"nickname\"\n" +
                    "}")
            @RequestBody SignUpInfoDTO signUpInfoDTO){
        return signUpService.signUp(signUpInfoDTO);
    }
}
