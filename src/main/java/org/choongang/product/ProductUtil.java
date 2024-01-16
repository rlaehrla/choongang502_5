package org.choongang.product;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductUtil {

    private final MemberUtil memberUtil;

    public String getCateCd(String cateCd) {
        if (memberUtil.isLogin() && !memberUtil.isAdmin()) {
            AbstractMember member = memberUtil.getMember();
            String userId = member.getUserId();
            cateCd = cateCd.replace(userId + "_", "");
        }

        return cateCd;
    }
}
