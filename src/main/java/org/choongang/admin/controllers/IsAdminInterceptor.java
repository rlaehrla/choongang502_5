package org.choongang.admin.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class IsAdminInterceptor implements HandlerInterceptor, ExceptionProcessor {

    private final MemberUtil memberUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!memberUtil.isAdmin()) {
            throw new UnAuthorizedException("접근 권한이 없습니다.") ;
        }

        return true ;
    }

}
