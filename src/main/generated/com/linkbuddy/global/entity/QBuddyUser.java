package com.linkbuddy.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBuddyUser is a Querydsl query type for BuddyUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuddyUser extends EntityPathBase<BuddyUser> {

    private static final long serialVersionUID = 1814816802L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBuddyUser buddyUser = new QBuddyUser("buddyUser");

    public final DateTimePath<java.sql.Timestamp> acceptDt = createDateTime("acceptDt", java.sql.Timestamp.class);

    public final BooleanPath acceptTf = createBoolean("acceptTf");

    public final BooleanPath alertTf = createBoolean("alertTf");

    public final QBuddy buddy;

    public final NumberPath<Long> buddyId = createNumber("buddyId", Long.class);

    public final DateTimePath<java.sql.Timestamp> created_at = createDateTime("created_at", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath pinTf = createBoolean("pinTf");

    public final DateTimePath<java.sql.Timestamp> updated_at = createDateTime("updated_at", java.sql.Timestamp.class);

    public final QUser user;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBuddyUser(String variable) {
        this(BuddyUser.class, forVariable(variable), INITS);
    }

    public QBuddyUser(Path<? extends BuddyUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBuddyUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBuddyUser(PathMetadata metadata, PathInits inits) {
        this(BuddyUser.class, metadata, inits);
    }

    public QBuddyUser(Class<? extends BuddyUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buddy = inits.isInitialized("buddy") ? new QBuddy(forProperty("buddy")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

