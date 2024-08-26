package com.simple.basic.controller;

import com.simple.basic.command.TestVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ExampleController {

    //화면 띄우기
    @GetMapping("/ex00")
    public String ex00(Model model) {

        TestVO vo = TestVO.builder()
                .num(3)
                .name("철수")
                .age(50)
                .addr("서울")
                .build();

        model.addAttribute("vo", vo);


        return "view/ex00";
    }

    @RequestMapping("/ex00_result")
    public String ex00_result() {

        return "view/ex00_result";
    }


}
