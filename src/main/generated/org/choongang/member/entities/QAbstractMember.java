package org.choongang.member.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAbstractMember is a Querydsl query type for AbstractMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAbstractMember extends EntityPathBase<AbstractMember> {

    private static final long serialVersionUID = 427444305L;

    public static final QAbstractMember abstractMember = new QAbstractMember("abstractMember");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    public final ListPath<Address, QAddress> address = this.<Address, QAddress>createList("address", Address.class, QAddress.class, PathInits.DIRECT2);

    public final ListPath<Authorities, QAuthorities> authorities = this.<Authorities, QAuthorities>createList("authorities", Authorities.class, QAuthorities.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath gid = createString("gid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath tel = createString("tel");

    public final StringPath userId = createString("userId");

    public final StringPath username = createString("username");

    public QAbstractMember(String variable) {
        super(AbstractMember.class, forVariable(variable));
    }

    public QAbstractMember(Path<? extends AbstractMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractMember(PathMetadata metadata) {
        super(AbstractMember.class, metadata);
    }

}

