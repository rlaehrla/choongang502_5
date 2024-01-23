package org.choongang.order.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderInfo is a Querydsl query type for OrderInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderInfo extends EntityPathBase<OrderInfo> {

    private static final long serialVersionUID = -264720629L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderInfo orderInfo = new QOrderInfo("orderInfo");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    public final StringPath address = createString("address");

    public final StringPath addressSub = createString("addressSub");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath deliveryMemo = createString("deliveryMemo");

    public final StringPath depositor = createString("depositor");

    public final org.choongang.member.entities.QAbstractMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath orderCellphone = createString("orderCellphone");

    public final StringPath orderEmail = createString("orderEmail");

    public final ListPath<OrderItem, QOrderItem> orderItems = this.<OrderItem, QOrderItem>createList("orderItems", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final StringPath orderName = createString("orderName");

    public final NumberPath<Long> orderNo = createNumber("orderNo", Long.class);

    public final NumberPath<Integer> payPrice = createNumber("payPrice", Integer.class);

    public final EnumPath<org.choongang.order.constants.PayType> payType = createEnum("payType", org.choongang.order.constants.PayType.class);

    public final StringPath receiverCellphone = createString("receiverCellphone");

    public final StringPath receiverName = createString("receiverName");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final EnumPath<org.choongang.order.constants.OrderStatus> status = createEnum("status", org.choongang.order.constants.OrderStatus.class);

    public final NumberPath<Integer> totalDeliveryPrice = createNumber("totalDeliveryPrice", Integer.class);

    public final NumberPath<Integer> totalDiscount = createNumber("totalDiscount", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public final StringPath zoneCode = createString("zoneCode");

    public QOrderInfo(String variable) {
        this(OrderInfo.class, forVariable(variable), INITS);
    }

    public QOrderInfo(Path<? extends OrderInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderInfo(PathMetadata metadata, PathInits inits) {
        this(OrderInfo.class, metadata, inits);
    }

    public QOrderInfo(Class<? extends OrderInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new org.choongang.member.entities.QAbstractMember(forProperty("member")) : null;
    }

}

