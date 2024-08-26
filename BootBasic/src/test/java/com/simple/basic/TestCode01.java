package com.simple.basic;

import com.simple.basic.command.BuilderVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCode01 {

    //테스트 코드가 위에서부터 아래로 완료되는 것은 아님

//    @Test
//    @Order(2) //순서 지정 어노테이션 위의 TestMethodOrder어노테이션과 함께 사용해야함
//    public void testCode01() {
//
//        System.out.println("test Code 실행됨");
//    }
//
//    @Test
//    @Order(1)
//    public void testCode02() {
//        System.out.println("test code2번 실행됨");
//    }

    @Test
    public void testCode03() {
        
        //한번 값이 지정되며느 변수 값을 바꿀 수 없는 불변성을 제공
        //값의 타입을 명확하게 확인할 수 있음
        //getter, setter 를 쓰지 말라는 것은 아님
        BuilderVO vo = BuilderVO.builder().name("홍길동").age(10).build();
        System.out.println(vo.toString());

    }

}
