package com.linkbuddy.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBuddy is a Querydsl query type for Buddy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuddy extends EntityPathBase<Buddy> {

    private static final long serialVersionUID = 1434860471L;

    public static final QBuddy buddy = new QBuddy("buddy");

    public final DateTimePath<java.sql.Timestamp> created_at = createDateTime("created_at", java.sql.Timestamp.class);

    public final NumberPath<Long> creator_id = createNumber("creator_id", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final DateTimePath<java.sql.Timestamp> updated_at = createDateTime("updated_at", java.sql.Timestamp.class);

    public QBuddy(String variable) {
        super(Buddy.class, forVariable(variable));
    }

    public QBuddy(Path<? extends Buddy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBuddy(PathMetadata metadata) {
        super(Buddy.class, metadata);
    }

}

