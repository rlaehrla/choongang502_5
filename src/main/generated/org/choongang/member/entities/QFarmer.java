package org.choongang.member.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFarmer is a Querydsl query type for Farmer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFarmer extends EntityPathBase<Farmer> {

    private static final long serialVersionUID = -1300465832L;

    public static final QFarmer farmer = new QFarmer("farmer");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    public final ListPath<Authorities, QAuthorities> authorities = this.<Authorities, QAuthorities>createList("authorities", Authorities.class, QAuthorities.class, PathInits.DIRECT2);

    public final StringPath certificateNo = createString("certificateNo");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath farmAddress = createString("farmAddress");

    public final StringPath farmTitle = createString("farmTitle");

    public final StringPath gid = createString("gid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath tel = createString("tel");

    public final StringPath userId = createString("userId");

    public QFarmer(String variable) {
        super(Farmer.class, forVariable(variable));
    }

    public QFarmer(Path<? extends Farmer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFarmer(PathMetadata metadata) {
        super(Farmer.class, metadata);
    }

}

