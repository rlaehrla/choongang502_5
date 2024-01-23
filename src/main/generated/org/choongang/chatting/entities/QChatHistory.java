package org.choongang.chatting.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatHistory is a Querydsl query type for ChatHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatHistory extends EntityPathBase<ChatHistory> {

    private static final long serialVersionUID = 938146331L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatHistory chatHistory = new QChatHistory("chatHistory");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final org.choongang.member.entities.QAbstractMember member;

    public final StringPath message = createString("message");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickName = createString("nickName");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QChatHistory(String variable) {
        this(ChatHistory.class, forVariable(variable), INITS);
    }

    public QChatHistory(Path<? extends ChatHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatHistory(PathMetadata metadata, PathInits inits) {
        this(ChatHistory.class, metadata, inits);
    }

    public QChatHistory(Class<? extends ChatHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new org.choongang.member.entities.QAbstractMember(forProperty("member")) : null;
    }

}

