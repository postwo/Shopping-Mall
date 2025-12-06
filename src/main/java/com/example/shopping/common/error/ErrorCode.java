package com.example.shopping.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs {
    //외부코등 ,내부코드(이거는 내가 서비스에 사용할 에러코드 이다,HttpStatus코드 와 일치 할 수도 있지만 일치 하지 않을수도 있다) ,그냥 내용
    OK(200, 200, "성공"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),

    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "null point"),

    ;

    // 변형이 안일어나게 하기 위해 final 붙임
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
