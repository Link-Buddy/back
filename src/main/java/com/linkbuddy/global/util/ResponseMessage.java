package com.linkbuddy.global.util;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.linkbuddy.global.util
 * fileName       : ResponseMessage
 * author         : admin
 * date           : 2024-03-23
 * description    : 공통 ResponseEntity Message
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-23        admin       최초 생성
 */
@Data
@Builder
public class ResponseMessage<T> {
    private StatusEnum status;
    private T data;

    public void Message(StatusEnum status, T data) {
        this.status = status;
        this.data = data;
    }
}
