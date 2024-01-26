package org.choongang.order.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderStatusHistory is a Querydsl query type for OrderStatusHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderStatusHistory extends EntityPathBase<OrderStatusHistory> {

    private static final long serialVersionUID = -429242747L;

    public static final QOrderStatusHistory orderStatusHistory = new QOrderStatusHistory("orderStatusHistory");

    public final BooleanPath emailSent = createBoolean("emailSent");

    public final NumberPath<Long> orderSeq = createNumber("orderSeq", Long.class);

    public final EnumPath<org.choongang.order.constants.OrderStatus> prevStatus = createEnum("prevStatus", org.choongang.order.constants.OrderStatus.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final EnumPath<org.choongang.order.constants.OrderStatus> status = createEnum("status", org.choongang.order.constants.OrderStatus.class);

    public QOrderStatusHistory(String variable) {
        super(OrderStatusHistory.class, forVariable(variable));
    }

    public QOrderStatusHistory(Path<? extends OrderStatusHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderStatusHistory(PathMetadata metadata) {
        super(OrderStatusHistory.class, metadata);
    }

}

