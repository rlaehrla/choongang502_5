package org.choongang.order.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderItem is a Querydsl query type for OrderItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderItem extends EntityPathBase<OrderItem> {

    private static final long serialVersionUID = -264714896L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderItem orderItem = new QOrderItem("orderItem");

    public final StringPath deliveryCompany = createString("deliveryCompany");

    public final StringPath deliveryInvoice = createString("deliveryInvoice");

    public final NumberPath<Integer> ea = createNumber("ea", Integer.class);

    public final org.choongang.product.entities.QProductOption option;

    public final StringPath optionName = createString("optionName");

    public final StringPath optionValue = createString("optionValue");

    public final QOrderInfo orderInfo;

    public final org.choongang.product.entities.QProduct product;

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> salePrice = createNumber("salePrice", Integer.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final EnumPath<org.choongang.order.constants.OrderStatus> status = createEnum("status", org.choongang.order.constants.OrderStatus.class);

    public final NumberPath<Integer> totalDiscount = createNumber("totalDiscount", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public QOrderItem(String variable) {
        this(OrderItem.class, forVariable(variable), INITS);
    }

    public QOrderItem(Path<? extends OrderItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderItem(PathMetadata metadata, PathInits inits) {
        this(OrderItem.class, metadata, inits);
    }

    public QOrderItem(Class<? extends OrderItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.option = inits.isInitialized("option") ? new org.choongang.product.entities.QProductOption(forProperty("option"), inits.get("option")) : null;
        this.orderInfo = inits.isInitialized("orderInfo") ? new QOrderInfo(forProperty("orderInfo"), inits.get("orderInfo")) : null;
        this.product = inits.isInitialized("product") ? new org.choongang.product.entities.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

