package com.codegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController {
    @GetMapping("/uploadonefile")
    public ModelAndView uploadOneFile(){
        ModelAndView modelAndView = new ModelAndView("onefile");
        return modelAndView;
    }

}
