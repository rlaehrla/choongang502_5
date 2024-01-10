package org.choongang.member.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1096527217L;

    public static final QMember member = new QMember("member1");

    public final QAbstractMember _super = new QAbstractMember(this);

    //inherited
    public final ListPath<Address, QAddress> address = _super.address;

    //inherited
    public final ListPath<Authorities, QAuthorities> authorities = _super.authorities;

    public final DatePath<java.time.LocalDate> birthdate = createDate("birthdate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath email = _super.email;

    public final EnumPath<org.choongang.member.constants.Gender> gender = createEnum("gender", org.choongang.member.constants.Gender.class);

    //inherited
    public final StringPath gid = _super.gid;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath nickname = _super.nickname;

    //inherited
    public final StringPath password = _super.password;

    //inherited
    public final NumberPath<Long> seq = _super.seq;

    //inherited
    public final StringPath tel = _super.tel;

    //inherited
    public final StringPath userId = _super.userId;

    //inherited
    public final StringPath username = _super.username;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

