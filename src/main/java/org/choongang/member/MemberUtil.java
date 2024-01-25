package org.choongang.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.member.constants.Authority;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.AbstractMember;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.order.service.OrderInfoService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;
    private final OrderInfoRepository orderInfoRepository;

    public boolean isAdmin() {

        if (isLogin()) {
            return getMember().getAuthorities()
                    .stream().map(Authorities::getAuthority)
                    .anyMatch(a -> a == Authority.ADMIN || a == Authority.MANAGER);
        }

        return false;
    }

    public boolean isFarmer(){

        if(isLogin()){
            return getMember().getAuthorities()
                    .stream().map(Authorities::getAuthority)
                    .anyMatch(a -> a == Authority.FARMER);
        }

        return false;
    }

    public boolean isMember() {
        if(isLogin()) {
            return getMember().getAuthorities()
                    .stream().map(Authorities::getAuthority)
                    .anyMatch(a -> a == Authority.USER);

        }
        return false;
    }


    public boolean isLogin() {
        return getMember() != null;
    }

    public AbstractMember getMember() {
        AbstractMember member = (AbstractMember) session.getAttribute("member");

        return member;
    }

    public static void clearLoginData(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("NotBlank_username");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("Global_error");
    }

    /**
     * orderInfo의 seq 구하기
     * @param orderNo
     * @return
     */
    public Long findSeq(Long orderNo){
        OrderInfo orderInfo =  orderInfoRepository.findByOrderNo(orderNo).orElse(null);

        if (orderInfo != null) {
            return orderInfo.getSeq();
        }
        return 0L;
    }

}
