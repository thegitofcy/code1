package com.cy.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: demo
 * @description:
 * @author: cy
 */
@Controller
public class DemoController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }



}
