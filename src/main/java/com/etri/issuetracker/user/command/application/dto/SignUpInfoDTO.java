package com.etri.issuetracker.user.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpInfoDTO {

    private String uid;
    private String password;
    private String nickname;
}
