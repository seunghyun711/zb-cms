package com.zb.cms.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다." ),
    ALREADY_VERIFY(HttpStatus.BAD_REQUEST, " 이미 인증이 완료되었습니다." ),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못된 인증 처리입니다." ),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST,"인증 시간이 만료되었습니다." ),

    // login
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "로그인 실패(아이디 혹은 패스워드를 확인하세요."),

    CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 인증 시도입니다."),
    DATE_EXPIRED(HttpStatus.BAD_REQUEST, "인증 유효기간이 지났습니다."),

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),

    SIGN_IN_ERROR(HttpStatus.BAD_REQUEST, "로그인 정보 오류"),
    NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}
