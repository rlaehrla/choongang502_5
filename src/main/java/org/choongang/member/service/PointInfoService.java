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

import java.util.Iterator;
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

    /**
     * 본인의 포인트 조회
     *
     * @return
     */
    public int pointSum(){
        QPoint point = QPoint.point1;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(point.member.seq.eq(memberUtil.getMember().getSeq()));

        int sum = 0;

        Iterator<Point> points =  pointRepository.findAll(builder).iterator();

        while(points.hasNext()){
            Point pt = points.next();
            sum += pt.getPoint();
        }

        return sum;
    }

    /**
     * 본인의 포인트 상세 출력
     * @return
     */
    public ListData<Point> getList(){

        QPoint point = QPoint.point1;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(point.member.seq.eq(memberUtil.getMember().getSeq()));

        int page = 1;
        int limit = utils.isMobile()? 5 : 10;

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Point> data = pointRepository.findAll(builder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Point> items = data.getContent();

        for(Point item : items){

            System.out.println(item);

        }


        return new ListData<>(items, pagination);
    }




}
