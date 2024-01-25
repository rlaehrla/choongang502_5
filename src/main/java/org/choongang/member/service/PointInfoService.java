package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Point;
import org.choongang.member.entities.QPoint;
import org.choongang.member.repositories.PointRepository;
import org.choongang.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class PointInfoService {
    private final PointRepository pointRepository;
    private final MemberUtil memberUtil;
    private final Utils utils;
    private final HttpServletRequest request;

    public ListData<Point> getList(){

        QPoint point = QPoint.point1;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(point.member.seq.eq(memberUtil.getMember().getSeq()));

        int page = 1;
        int limit = utils.isMobile()? 5 : 10;

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(asc("createdAt")));

        Page<Point> data = pointRepository.findAll(builder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Point> items = data.getContent();


        return new ListData<>(items, pagination);
    }


}
