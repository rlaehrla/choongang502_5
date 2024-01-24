package org.choongang.admin.banner.repository;

import org.choongang.admin.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BannerRepository extends JpaRepository<Banner, Long>, QuerydslPredicateExecutor<Banner> {

}
