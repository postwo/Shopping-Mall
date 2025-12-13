package com.example.shopping.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponse {
    private String accessToken;

    private String username;

    private String email;

    public static LoginResponse loginMember(String accessToken, String username, String email){
        return LoginResponse.builder()
                .accessToken(accessToken)
                .username(username)
                .email(email)
                .build();
    }
}
