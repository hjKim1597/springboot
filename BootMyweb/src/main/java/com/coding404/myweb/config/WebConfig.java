package com.coding404.myweb.config;

import com.coding404.myweb.Interceptor.MenuHandler;
import com.coding404.myweb.Interceptor.UserAuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //설정 파일
public class WebConfig implements WebMvcConfigurer {

    //인터셉터 빈으로 등록
    @Bean
    public UserAuthHandler userAuthHandler() {
        return new UserAuthHandler();
    }
    
    //메뉴처리 인터셉터 빈 등록
    @Bean
    public MenuHandler menuHandler() {
        return new MenuHandler();
    }

    //인터셉터 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthHandler())
                .addPathPatterns("/product/*") //상품관련 내역에 모두 세션없을 경우 접근x 모두추가
                .addPathPatterns("/"); // 메인페이지
                
        
        registry.addInterceptor(menuHandler())
                .addPathPatterns("/product/*"); //메뉴 post 핸들러가 동작할 주소
        
    }
    
    
    
}
