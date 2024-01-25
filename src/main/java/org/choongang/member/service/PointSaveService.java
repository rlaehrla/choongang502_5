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
    public void save(Member member, int num){

        Point point = new Point();
        point.setMember(member);
        point.setPoint(num);

        pointRepository.saveAndFlush(point);

    }


}
