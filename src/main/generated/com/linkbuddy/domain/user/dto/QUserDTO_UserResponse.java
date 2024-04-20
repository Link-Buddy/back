package com.linkbuddy.domain.user.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.linkbuddy.domain.user.dto.QUserDTO_UserResponse is a Querydsl Projection type for UserResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QUserDTO_UserResponse extends ConstructorExpression<UserDTO.UserResponse> {

    private static final long serialVersionUID = 2066098813L;

    public QUserDTO_UserResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> email) {
        super(UserDTO.UserResponse.class, new Class<?>[]{long.class, String.class, String.class}, id, name, email);
    }

}

