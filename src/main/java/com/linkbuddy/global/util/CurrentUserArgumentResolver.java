package com.linkbuddy.global.util;


import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName    : com.linkbuddy.global.util
 * fileName       : CurrentUserArgumentResolver
 * author         : yl951
 * date           : 2024-08-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-26        yl951       최초 생성
 */

//현재 인증된 사용자의 정보 주입
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

  //CurrentUser 라는 어노테이션이 붙어있는가? & 매개변수가 CurrentUser이면 인가? -> resolveArgument 호출
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(CurrentUser.class) != null
            && CustomUserDetails.class.isAssignableFrom(parameter.getParameterType());
  }

  //해당 매개변수에 어떤 값을 전달할지 결정
  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //사용자 정보 get
    if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
      return authentication.getPrincipal(); //인증완료된 사용자면 파라미터 주입
    }
    return null;
  }
}