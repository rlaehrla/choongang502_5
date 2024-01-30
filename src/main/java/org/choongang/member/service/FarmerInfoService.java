package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.Farmer;
import org.choongang.member.entities.QFarmer;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class FarmerInfoService {

    private final FarmerRepository farmerRepository;
    private final HttpServletRequest request;


    public ListData<Farmer> topFarmer(MemberSearch search){
        QFarmer farmer = QFarmer.farmer;
        BooleanBuilder builder = new BooleanBuilder();

        // 페이징 처리
        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 10);

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("salePoint")));

        Page<Farmer> data = farmerRepository.findAll(builder, pageable);


        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Farmer> items = data.getContent();

        return new ListData<>(items, pagination);
    }
}
