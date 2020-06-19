package com.cy.core.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: security
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
