package org.choongang.cart.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.cart.repositories.CartInfoRepository;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartDeleteService {

    private final CartInfoRepository cartInfoRepository;
    private final HttpServletRequest request;
    private final Utils utils;

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
}