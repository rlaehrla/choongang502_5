package org.choongang.member.repositories;

import org.choongang.member.entities.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PointRepository extends JpaRepository<Point, Long>, QuerydslPredicateExecutor<Point> {
}
