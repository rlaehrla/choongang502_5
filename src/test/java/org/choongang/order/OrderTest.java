package org.choongang.order;

import org.choongang.order.constants.OrderStatus;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.service.OrderInfoService;
import org.choongang.order.service.OrderSaveService;
import org.choongang.order.service.OrderStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "spring.profiles.active=dev")
public class OrderTest {

    @Autowired
    private OrderSaveService orderSaveService;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Test
    void test1(){
        Long seq = 1252L;

        orderStatusService.change(seq, OrderStatus.ORDER);

        OrderInfo orderInfo = orderInfoService.get(seq);
        System.out.println(orderInfo);
    }

    @Test
    void test2(){
        Long seq = 602L;
        List<Long> orderItemSeq = Arrays.asList(1152L);

        orderStatusService.change(seq, orderItemSeq, OrderStatus.ORDER);

        OrderInfo orderInfo = orderInfoService.get(seq);
        List<OrderItem> orderItem = orderInfo.getOrderItems();

        orderItem.stream().forEach(s -> System.out.println(s.getStatus()));
        System.out.println(orderInfo);

    }

}
