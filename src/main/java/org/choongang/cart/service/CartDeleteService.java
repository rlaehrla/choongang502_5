package org.choongang.cart.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.cart.constants.CartType;
import org.choongang.cart.entities.CartInfo;
import org.choongang.cart.entities.QCartInfo;
import org.choongang.cart.repositories.CartInfoRepository;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.member.MemberUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartDeleteService {

    private final CartInfoRepository cartInfoRepository;
    private final CartSaveService cartSaveService;
    private final HttpServletRequest request;
    private final Utils utils;
    private final MemberUtil memberUtil;


    public void deleteList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException(Utils.getMessage("상품을_선택_하세요."), HttpStatus.BAD_REQUEST);
        }

        List<Long> seq = new ArrayList<>();

        for (int chk : chks) {
            seq.add(Long.valueOf(utils.getParam("seq_" + chk)));
        }

        cartInfoRepository.deleteAllById(seq);

        cartInfoRepository.flush();

    }

    /**
     * 구매상품 장바구니에서 삭제
     * @param cartData
     */
    public void deleteCart(CartData cartData){
        List<CartInfo> items = cartData.getItems();
        for(CartInfo item : items){
            cartInfoRepository.delete(item);
        }

        cartInfoRepository.flush();

    }


}