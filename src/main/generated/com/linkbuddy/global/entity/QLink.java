package com.linkbuddy.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLink is a Querydsl query type for Link
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLink extends EntityPathBase<Link> {

    private static final long serialVersionUID = 323667177L;

    public static final QLink link = new QLink("link");

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final BooleanPath deleteTf = createBoolean("deleteTf");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> linkGroupId = createNumber("linkGroupId", Long.class);

    public final StringPath linkUrl = createString("linkUrl");

    public final StringPath name = createString("name");

    public final DateTimePath<java.sql.Timestamp> updatedAt = createDateTime("updatedAt", java.sql.Timestamp.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QLink(String variable) {
        super(Link.class, forVariable(variable));
    }

    public QLink(Path<? extends Link> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLink(PathMetadata metadata) {
        super(Link.class, metadata);
    }

}

