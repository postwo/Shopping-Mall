package com.example.shopping.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberErrorCode implements ErrorCodeIfs{

    MEMBER_NOT_FOUND(400 , 1404 , "사용자를 찾을 수 없음."),
    MEMBER_ALREADY_EXISTS(409, 1409, "이미 사용 중인 아이디입니다.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
