package com.simple.basic.controller;

import com.simple.basic.command.ValidPracVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/valid") //상위폴더 경로
public class ValidPracController {


    @GetMapping("/validPrac")
    public String validPrac(Model model) {

        model.addAttribute("vo", new ValidPracVO() );

        return "valid/validPrac";
    }

    @PostMapping("/validPracForm")
    public String validPracForm(@Valid @ModelAttribute("vo") ValidPracVO vo, BindingResult binding ) {

        if(binding.hasErrors()) {

        }

        return "valid/result";
    }




}
