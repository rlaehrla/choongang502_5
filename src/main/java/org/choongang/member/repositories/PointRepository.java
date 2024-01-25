package org.choongang.member.repositories;

import org.choongang.member.entities.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long>, QuerydslPredicateExecutor<Point> {

    Optional<List<Point>> findByOrderNo(Long orderNo);
}
