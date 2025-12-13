package com.example.shopping.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCodeIfs{

    // 기존 오류 코드
    INVALID_TOKEN(400 , 2000 , "유효하지 않은 토큰"),
    EXPIRED_TOKEN(400 , 2001 , "만료된 토큰"),
    TOKEN_EXCEPTION(400 , 2002 , "토큰 알수 없는 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(400, 2003, "인증 헤더 토큰 없음"),
    TOKEN_REMOVE_FAIL(500, 2004, "블랙리스트 제거 실패"),
    TOKEN_ADD_FAIL(500, 2005, "블랙리스트 추가 실패"),

    // 새로 추가된 리프레시 토큰 및 인증 관련 오류 코드
    REFRESH_TOKEN_NOT_FOUND(400, 2006, "리프레시 토큰이 없습니다."),
    INVALID_REFRESH_TOKEN(400, 2007, "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(400, 2008, "리프레시 토큰이 만료되었습니다. 다시 로그인해주세요."),
    AUTHORIZATION_HEADER_NOT_FOUND(400, 2009, "인증 헤더가 없습니다."),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}