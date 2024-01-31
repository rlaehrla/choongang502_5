package org.choongang.configs;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.controllers.IsAdminInterceptor;
import org.choongang.commons.interceptors.CommonInterceptor;
import org.choongang.farmer.management.controllers.IsFarmerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final CommonInterceptor commonInterceptor;
    private final IsFarmerInterceptor isFarmerInterceptor;
    private final IsAdminInterceptor isAdminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor);
        registry.addInterceptor(isFarmerInterceptor)
                .addPathPatterns("/farmer/**");
        /*registry.addInterceptor(isAdminInterceptor)
                .addPathPatterns("/admin/**");*/

    }
}
