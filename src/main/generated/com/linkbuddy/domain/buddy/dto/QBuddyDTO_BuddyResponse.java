package com.linkbuddy.domain.buddy.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.linkbuddy.domain.buddy.dto.QBuddyDTO_BuddyResponse is a Querydsl Projection type for BuddyResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBuddyDTO_BuddyResponse extends ConstructorExpression<BuddyDTO.BuddyResponse> {

    private static final long serialVersionUID = 796109348L;

    public QBuddyDTO_BuddyResponse(com.querydsl.core.types.Expression<Long> buddyId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Boolean> alertTf, com.querydsl.core.types.Expression<Boolean> pinTf) {
        super(BuddyDTO.BuddyResponse.class, new Class<?>[]{long.class, String.class, boolean.class, boolean.class}, buddyId, name, alertTf, pinTf);
    }

}

