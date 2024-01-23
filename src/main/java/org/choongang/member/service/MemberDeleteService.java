package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.service.ConfigDeleteService;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.board.service.config.BoardConfigDeleteService;
import org.choongang.farmer.blog.intro.BlogIntroPost;
import org.choongang.file.service.FileDeleteService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.Farmer;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.product.service.ProductInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDeleteService {

    private final MemberRepository memberRepository ;
    private final FarmerRepository farmerRepository ;
    private final FileDeleteService fileDeleteService ;
    private final AuthoritiesDeleteService authoritiesDeleteService ;
    private final AddressRepository addressRepository;
    private final BoardConfigDeleteService boardDeleteService ;
    private final ConfigInfoService configInfoService ;
    private final ConfigDeleteService configDeleteService ;
    private final ProductInfoService productInfoService ;
    private final MemberUtil memberUtil ;

    /**
     * 회원 탈퇴 처리
     */
    public void deleteMember() {
        if (!memberUtil.isLogin()) {
            return;
        }

        String gid = memberUtil.getMember().getGid() ;  // gid 가져오기
        String userId = memberUtil.getMember().getUserId() ;

        // 회원 삭제
        if (memberUtil.isFarmer()) {
            Farmer farmer = (Farmer) memberUtil.getMember();
            farmerRepository.delete(farmer);
            farmerRepository.flush();

            // 게시판 삭제
            boardDeleteService.delete("sns_" + userId);

            // 소개글 삭제
            BlogIntroPost intro = configInfoService.get(userId, BlogIntroPost.class).orElse(null) ;
            if (intro != null) {
                configDeleteService.delete(userId);
            }

            // 한 줄 소개 가져오기
            BlogIntroPost introSum = configInfoService.get(userId + "_sum", BlogIntroPost.class).orElse(null) ;
            if (introSum != null) {
                configDeleteService.delete(userId + "_sum");
            }

            // 상품 삭제


        } else {
            Member member = (Member) memberUtil.getMember() ;
            memberRepository.delete(member);
            memberRepository.flush();
        }

        // 권한 삭제
        authoritiesDeleteService.deleteList(memberUtil.getMember().getSeq());

        // 주소 삭제
        List<Address> address = memberUtil.getMember().getAddress() ;
        addressRepository.deleteAll(address);

        // 파일 삭제
        fileDeleteService.delete(gid);
    }
}
