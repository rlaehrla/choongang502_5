package org.choongang.admin.banner.repository;

import org.choongang.admin.banner.entity.BannerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BannerGroupRepository extends JpaRepository<BannerGroup, String>, QuerydslPredicateExecutor<BannerGroup> {

}
