package com.linkbuddy.global.util;

/**
 * packageName    : com.linkbuddy.global.util
 * fileName       : StatusEnum
 * author         : admin
 * date           : 2024-03-23
 * description    : Response Entity Message StatusEnum
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-23        admin       최초 생성
 */
public enum StatusEnum {
    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    CONFLICT(409, "CONFLICT");

    int statusCode;
    String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}
