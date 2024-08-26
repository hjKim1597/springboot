package com.simple.basic.config;

import com.simple.basic.command.TestVO;
import com.simple.basic.controller.HomeController;
import com.simple.basic.interceptor.UserAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;


@Configuration // 스프링 설정 파일이라는 것을 나타냄 
//@PropertySource("classpath:/hello.properties") //특정 properties 파일을 참조받고 싶다면..
public class WebConfig implements WebMvcConfigurer {

    //userAuthHandler를 자바 빈으로 등록
    @Bean
    public UserAuthHandler userAuthHandler() {
        return new UserAuthHandler();
    }

    //인터셉트로 userAuthHandler를 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor( userAuthHandler() ) //어떤 인터셉터를 등록할 것인지
                //.addPathPatterns("/user/mypage") //어떤 경로에 인터셉터를 등록할 것인지
                //.addPathPatterns("/user/success")
                .addPathPatterns("/user/*") //유저로 시작하는 모든 경로에 적용
                .excludePathPatterns(Arrays.asList("/user/login", "/user/loginForm", "/user/logout")) //인증을 제외할 경로
                ;
        
        //만약 종류별로 다양한 인터셉터를 추가할 것이라면 또 추가하면 됨
        //registry.addInterceptor(~~~~~)
    }




    /*
    @Value("${server.port}") //"${키} 8181" // application.properties 파일의 키값을 읽어서 받아옴
    String port;

    @Value("${hello}")
    String hello;

    @Value("${bye}")
    String bye;

    @Autowired
    ApplicationContext applicationContext; //ioc 컨테이너 역할을 함

    //자바코드로 빈생성
    @Bean
    public TestVO testVO() {
        return new TestVO(); //bean으로 등록
    }

    @Bean // 스프링이 이 코드를 실행시켜서, 리턴에 담기는 값을 bean 으로 등록
    public void test() {

//        System.out.println("스프링 설정 파일이 실행됨");
//
//        int result = applicationContext.getBeanDefinitionCount(); //어노테이션들도 bean으로 관리
//        System.out.println("context 안에 bean의 갯수: " + result);
//
//        HomeController home = applicationContext.getBean(HomeController.class);
//        System.out.println("context안에 home 컨트롤러 빈:" + home);

        TestVO vo = applicationContext.getBean(TestVO.class);
        System.out.println("context안에 testVO빈" + vo);

        System.out.println("properties server.port 값:" + port); //application.propeprties 파일에서 받아옴
        System.out.println("properties hello 값:" + hello); //application.propeprties 파일에서 받아옴
        System.out.println("properties bye 값:" + bye); //hello.propeprties 파일에서 받아옴
    }*/

}
