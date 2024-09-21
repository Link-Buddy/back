package com.linkbuddy.global.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.linkbuddy.global.util
 * fileName       : CustomException
 * author         : yl951
 * date           : 2024-09-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-12        yl951       최초 생성
 */

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
  private final StatusEnum statusEnum;
  private final String message;
}