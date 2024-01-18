package org.choongang.cart.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.cart.constants.CartType;
import org.choongang.cart.controllers.RequestCart;
import org.choongang.cart.entities.CartInfo;
import org.choongang.cart.repositories.CartInfoRepository;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Member;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartSaveService {

    private final ProductInfoService productInfoService;
    private final CartInfoRepository cartInfoRepository;
    private final CartInfoService cartInfoService;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;
    private final Utils utils;

    public void save(RequestCart form) {

        Long seq = form.getSeq(); // 상품 번호
        String mode = form.getMode(); //
        int uid = memberUtil.isLogin() ? 0 : utils.cartUid();
        Member member = (Member)memberUtil.getMember();
        Product product = productInfoService.get(seq); // 상품 엔티티

        List<Integer> nums = form.getSelectedNums();

        // 장바구니에 담겨 있는 상품 조회
        List<CartInfo> items = Objects.requireNonNullElse(cartInfoService.getList(CartType.valueOf(mode)), new ArrayList<>());

        for (int num : nums) {

            int ea = Integer.parseInt(utils.getParam("ea_" + num));

            boolean exist = false;
            for (CartInfo item : items) {
                if (item.getProduct().getSeq().equals(product.getSeq()) ) {
                    item.setEa(item.getEa() + ea);
                    exist = true;
                    break;
                }
            }

            if (exist) { // 장바구니에 이미 상품이 있는 경우는 수량만 증가, 추가 X
                continue;
            }

            CartInfo item = CartInfo.builder()
                    .mode(CartType.valueOf(mode))
                    .product(product)
                    .uid(uid)
                    .ea(ea)
                    .member(member)
                    .build();

            items.add(item);
        }

        cartInfoRepository.saveAllAndFlush(items);
    }
}