package org.choongang.admin.banner.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBanner is a Querydsl query type for Banner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBanner extends EntityPathBase<Banner> {

    private static final long serialVersionUID = -1398346736L;

    public static final QBanner banner = new QBanner("banner");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    public final StringPath bannerWhere = createString("bannerWhere");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath gid = createString("gid");

    public final StringPath horizontal = createString("horizontal");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath vertical = createString("vertical");

    public QBanner(String variable) {
        super(Banner.class, forVariable(variable));
    }

    public QBanner(Path<? extends Banner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBanner(PathMetadata metadata) {
        super(Banner.class, metadata);
    }

}

