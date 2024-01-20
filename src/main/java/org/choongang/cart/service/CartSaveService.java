package org.choongang.cart.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.cart.constants.CartType;
import org.choongang.cart.controllers.RequestCart;
import org.choongang.cart.entities.CartInfo;
import org.choongang.cart.repositories.CartInfoRepository;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Member;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartSaveService {

    private final ProductInfoService productInfoService;
    private final FileInfoService fileInfoService;
    private final CartInfoRepository cartInfoRepository;
    private final CartInfoService cartInfoService;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;
    private final Utils utils;

    @Transactional
    public void save(RequestCart form) {

        Long seq = form.getSeq(); // 상품 번호
        String mode = form.getMode(); //
        int uid = memberUtil.isLogin() ? 0 : utils.cartUid();
        Member member = (Member)memberUtil.getMember();
        Product product = productInfoService.get(seq); // 상품 엔티티
        FileInfo mainImage = null;
        FileInfo listImage = null;

        if(fileInfoService.getListDone(product.getGid(), "product-main") != null){
            mainImage = fileInfoService.getListDone(product.getGid(), "product-main").get(0);
        }
        if(fileInfoService.getListDone(product.getGid(), "product-list") != null){
            listImage = fileInfoService.getListDone(product.getGid(), "product-list").get(0);
        }
        // mode - DIRECT -> 기존 바로 구매 상품 삭제
        if (mode.equals("DIRECT")) {
            List<CartInfo> directItems = cartInfoService.getList(CartType.DIRECT);
            cartInfoRepository.deleteAll(directItems);
            cartInfoRepository.flush();
        }

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
                    .mainImage(mainImage)
                    .listImage(listImage)
                    .build();

            items.add(item);
        }

        cartInfoRepository.saveAllAndFlush(items);
    }

    /**
     * 장바구니 목록 수정
     *
     * @param chks
     */
    public void saveList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException(Utils.getMessage("상품을_선택_하세요."), HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            Long seq = Long.valueOf(utils.getParam("seq_" + chk));
            int ea = Integer.parseInt(utils.getParam("ea_" + chk));

            CartInfo item = cartInfoRepository.findById(seq).orElse(null);
            if (item == null) {
                continue;
            }

            item.setEa(ea);
        }

        cartInfoRepository.flush();
    }
}