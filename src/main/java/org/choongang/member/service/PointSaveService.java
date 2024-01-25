package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.Point;
import org.choongang.member.repositories.PointRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointSaveService {

    private final PointRepository pointRepository;


    /**
     * 포인트 적립
     *
     * @param member
     * @param num : 적립할 포인트
     */
    public void save(Member member, int num, String message, Long orderNo){

        Point point = new Point();
        point.setMember(member);
        point.setPoint(num);
        point.setMessage(message);
        point.setOrderNo(orderNo);

        pointRepository.saveAndFlush(point);

    }

    public void save(Member member, int num, Long orderNo){
        save(member, num, null, orderNo);
    }

    public void save(Member member, int num, String message) {
        save(member, num, message, null);
    }

    public void save(Member member, int num) {
        save(member, num, null, null);
    }

}
