package org.choongang.order.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.controllers.BasicConfig;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.commons.Utils;
import org.choongang.email.service.EmailMessage;
import org.choongang.email.service.EmailSendService;
import org.choongang.member.entities.Farmer;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.service.MemberEditService;
import org.choongang.order.constants.OrderStatus;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.entities.OrderStatusHistory;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.order.repositories.OrderItemRepository;
import org.choongang.order.repositories.OrderStatusHistoryRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderInfoRepository orderInfoRepository;
    private final OrderItemRepository orderItemRepository;
    private final EmailSendService emailSendService;
    private final OrderInfoService orderInfoService;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final ConfigInfoService configInfoService;
    private final OrderItemInfoService orderItemInfoService;
    private final FarmerRepository farmerRepository;

    /**
     * 주문 상태 변경
     * @param orderSeq : OrderInfo Seq
     * @param orderItemSeq : OrderItem Seq
     * @param status : 변경 후 상태
     * @param manualSendEmail : 기본으로 이메일 보낼지 여부
     */
    public void change(Long orderSeq, List<Long> orderItemSeq, OrderStatus status, boolean manualSendEmail){
        OrderInfo orderInfo = orderInfoService.get(orderSeq);
        List<OrderItem> items = orderInfo.getOrderItems();

        OrderStatus prevStatus= orderInfo.getStatus(); // 변경 전 상태


        int cnt = 0;

        for(OrderItem item : items){

            if(orderItemSeq == null || orderItemSeq.isEmpty() || orderItemSeq.contains(item.getSeq())){
                item.setStatus(status);
            }

            if(orderInfo.getStatus().ordinal() < item.getStatus().ordinal()){
                cnt++;
            }

            Farmer farmer = item.getProduct().getFarmer();
            Long salePoint = orderItemInfoService.sumFarmersSale(farmer);
            farmer.setSalePoint(salePoint);

            farmerRepository.saveAndFlush(farmer);
        }

        orderItemRepository.saveAllAndFlush(items);

        OrderStatus nextStatus = null; // 변경 후 상태
        // 주문 상품 상태 모두 동일 -> 주문서 상태 변경
        if(cnt == items.size()){
            orderInfo.setStatus(status);
            nextStatus = orderInfo.getStatus();
        }

        orderInfoRepository.flush();


        boolean emailSent = orderStatusHistoryRepository.isEmailSent(orderSeq, status);
        if(manualSendEmail) emailSent = false;
        if(!emailSent && status.isEmailStatus()){
            // 메인 전송 필요 상태
            BasicConfig config = configInfoService.get("config", BasicConfig.class).orElseGet(BasicConfig::new);

            String subject = String.format("[%s][%s] %s", config.getSiteTitle(), Utils.getMessage("OrderStatus."+status.name(), "commons"), Utils.getMessage("안내_메일", "commons"));

            EmailMessage emailMessage = new EmailMessage(orderInfo.getOrderEmail(), subject, subject);

            Map<String, Object> tplData = new HashMap<>();
            tplData.put("orderInfo", orderInfo);

            emailSendService.sendMail(emailMessage, "order/" + status.name().toLowerCase(), tplData);

            emailSent = true;

        }

        if(nextStatus != null){

            OrderStatusHistory history = OrderStatusHistory.builder()
                    .emailSent(emailSent)
                    .status(nextStatus)
                    .prevStatus(prevStatus)
                    .orderSeq(orderInfo.getSeq())
                    .build();

            orderStatusHistoryRepository.saveAndFlush(history);

        }
    }

    public void change(Long orderSeq, List<Long> orderItemSeq, OrderStatus status ){
        change(orderSeq, orderItemSeq, status, false);
    }

    public void change(Long orderSeq, OrderStatus orderStatus, boolean manualEmailSent){
        change(orderSeq, null, orderStatus, manualEmailSent);
    }


    public void change(Long orderSeq, OrderStatus status){
        change(orderSeq, null, status, false);
    }

}
