package org.choongang.cart.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCartInfo is a Querydsl query type for CartInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartInfo extends EntityPathBase<CartInfo> {

    private static final long serialVersionUID = 2021729449L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCartInfo cartInfo = new QCartInfo("cartInfo");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> ea = createNumber("ea", Integer.class);

    public final org.choongang.member.entities.QMember member;

    public final EnumPath<org.choongang.cart.constants.CartType> mode = createEnum("mode", org.choongang.cart.constants.CartType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final org.choongang.product.entities.QProduct product;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Integer> uid = createNumber("uid", Integer.class);

    public QCartInfo(String variable) {
        this(CartInfo.class, forVariable(variable), INITS);
    }

    public QCartInfo(Path<? extends CartInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCartInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCartInfo(PathMetadata metadata, PathInits inits) {
        this(CartInfo.class, metadata, inits);
    }

    public QCartInfo(Class<? extends CartInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new org.choongang.member.entities.QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new org.choongang.product.entities.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

