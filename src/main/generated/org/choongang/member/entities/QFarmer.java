package org.choongang.member.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFarmer is a Querydsl query type for Farmer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFarmer extends EntityPathBase<Farmer> {

    private static final long serialVersionUID = -1300465832L;

    public static final QFarmer farmer = new QFarmer("farmer");

    public final QAbstractMember _super = new QAbstractMember(this);

    //inherited
    public final ListPath<Authorities, QAuthorities> authorities = _super.authorities;

    public final StringPath businessPermitNum = createString("businessPermitNum");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath email = _super.email;

    public final StringPath farmAddress = createString("farmAddress");

    public final StringPath farmAddressSub = createString("farmAddressSub");

    public final StringPath farmTitle = createString("farmTitle");

    public final StringPath farmZonecode = createString("farmZonecode");

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

