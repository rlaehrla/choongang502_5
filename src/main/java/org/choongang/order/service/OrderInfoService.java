package org.choongang.order.service;

import lombok.RequiredArgsConstructor;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.order.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderInfoService {
    private final OrderInfoRepository orderInfoRepository;

    /**
     *  주문서 조회
     *
     * @Param seq
     * @return
     */
    public OrderInfo get(Long seq){
        OrderInfo orderInfo = orderInfoRepository.findById(seq).orElseThrow(OrderNotFoundException::new);

        return orderInfo;
    }
}
