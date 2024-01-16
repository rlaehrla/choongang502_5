package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.*;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final FarmerRepository farmerRepository;
    private final FileInfoService fileInfoService;
    private final EntityManager em ;
    private final HttpServletRequest request ;
    private final MemberUtil memberUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AbstractMember member = memberRepository.findByEmail(username) // 이메일 조회
                .orElseGet(() -> memberRepository.findByUserId(username) // 아이디로 조회
                        .orElseThrow(() -> new UsernameNotFoundException(username)));

        // 회원 유형에 따라 member 형 변환 처리
        String mType = request.getParameter("mType") ;
        member = mType.equals("M") ? (Member)member : (Farmer)member ;

        if (member instanceof Farmer) {
            member = farmerRepository.findByEmail(username)
                    .orElseGet(() -> farmerRepository.findByUserId(username)
                            .orElseThrow(() -> new UsernameNotFoundException(username))) ;
        }

        List<SimpleGrantedAuthority> authorities = null;
        List<Authorities> tmp = member.getAuthorities();
        if (tmp != null) {
            authorities = tmp.stream()
                    .map(s -> new SimpleGrantedAuthority(s.getAuthority().name()))
                    .toList();
        }

        /* 프로필 이미지 처리 */
        List<FileInfo> files = fileInfoService.getListDone(member.getGid());
        if (files != null && !files.isEmpty()) {
            member.setProfileImage(files.get(0));
        }

        /* 프로필 이미지 처리 */
        System.out.println(member.getAddress());
        System.out.println(member.getClass().getSimpleName());
        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }

    /**
     * 농부목록 반환
     * @return
     */
    public List<Farmer> getFarmerList(){
        if(!memberUtil.isAdmin()){
            throw new UnAuthorizedException();
        }
        QFarmer farmer = QFarmer.farmer;
        PathBuilder pathBuilder = new PathBuilder(Farmer.class, "farmer");

        List<Farmer> farmers = new JPAQueryFactory(em)
                .selectFrom(farmer)
                .orderBy(new OrderSpecifier(Order.ASC, pathBuilder.get("username")))    // 정렬
                .fetch() ;

        return farmers;

    }

    /**
     * 회원 목록 반환
     */
    public ListData<AbstractMember> getList(MemberSearch search) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1) ;    // 현재 페이지 번호
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20) ;    // 한 페이지 당 노출할 레코드 개수
        int offset = (page - 1) * limit ;    // 레코드 시작 위치 번호

        BooleanBuilder andBuilder = new BooleanBuilder() ;
        QAbstractMember member = QAbstractMember.abstractMember;

        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member") ;

        List<AbstractMember> items = new JPAQueryFactory(em)
                .selectFrom(member)
                .leftJoin(member.authorities)
                .fetchJoin()
                .where(andBuilder)    // 조건식
                .limit(limit)
                .offset(offset)
                .orderBy(new OrderSpecifier(Order.DESC, pathBuilder.get("createdAt")))    // 정렬
                .fetch() ;

        /* 페이징 처리 S */
        int total = (int) memberRepository.count(andBuilder) ;    // 총 레코드 개수
        Pagination pagination = new Pagination(page, total, 10, limit, request) ;
        /* 페이징 처리 E */

        return new ListData<>(items, pagination) ;
    }
}