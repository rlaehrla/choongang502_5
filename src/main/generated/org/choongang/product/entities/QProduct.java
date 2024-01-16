package org.choongang.product.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 713231389L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    public final BooleanPath active = createBoolean("active");

    public final QCategory category;

    public final NumberPath<Integer> consumerPrice = createNumber("consumerPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> deliveryPrice = createNumber("deliveryPrice", Integer.class);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final EnumPath<org.choongang.product.constants.DiscountType> discountType = createEnum("discountType", org.choongang.product.constants.DiscountType.class);

    public final StringPath extraInfo = createString("extraInfo");

    public final org.choongang.member.entities.QFarmer farmer;

    public final StringPath gid = createString("gid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath optionName = createString("optionName");

    public final BooleanPath packageDelivery = createBoolean("packageDelivery");

    public final NumberPath<Integer> salePrice = createNumber("salePrice", Integer.class);

    public final NumberPath<Float> score = createNumber("score", Float.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final EnumPath<org.choongang.product.constants.ProductStatus> status = createEnum("status", org.choongang.product.constants.ProductStatus.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final BooleanPath useOption = createBoolean("useOption");

    public final BooleanPath useStock = createBoolean("useStock");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.farmer = inits.isInitialized("farmer") ? new org.choongang.member.entities.QFarmer(forProperty("farmer")) : null;
    }

}

