package com.simple.basic.controller;

import com.simple.basic.command.MemberVO;
import com.simple.basic.command.ValidVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/valid")
public class QuizController {


    @GetMapping("/quiz01")
    public String quiz01(Model model) {
        model.addAttribute("vo2", new MemberVO() );

        return "valid/quiz01";
    }

    @PostMapping("/quizForm")
    public String quizForm(@Valid @ModelAttribute("vo2") MemberVO vo2, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
//            System.out.println("유효성 검사 실패입니다!!!!!!!!!");
//            List<FieldError> list2 = bindingResult.getFieldErrors();
//            for(FieldError err : list2) {
//                System.out.println(err.getField());s
//                System.out.println(err.getDefaultMessage());
//            }
        }

        return "valid/quiz01";
    }



}
