package org.choongang.order.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.order.controllers.RequestOrder;
import org.choongang.order.entities.Order;
import org.choongang.order.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSaveService {
    private final OrderRepository orderRepository;
    private final MemberUtil memberUtil;


    public void save(RequestOrder form){
        Order order = new ModelMapper().map(form, Order.class);
        if(memberUtil.isLogin()){
            order.setMember(memberUtil.getMember());
        }

        orderRepository.saveAndFlush(order);

    }
}
