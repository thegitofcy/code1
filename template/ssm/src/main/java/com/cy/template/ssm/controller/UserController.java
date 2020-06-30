package com.cy.template.ssm.controller;

import com.cy.template.ssm.entry.User;
import com.cy.template.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: template
 * @description:
 * @author: cy
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAll() {
        List<User> all = userService.findAll();
        return all;
    }


    /**
     * 已存在就更新, 不存在就插入
     */
    @RequestMapping("/editInfo")
    public void editInfo() {
        User user = new User(1, "tom", new Date(), "9", "北京", "156565656");
        userService.saveOrUpdate(user);
    }

    /**
     * 批量更新
     *  已存在就更新, 不存在就插入
     */
    @RequestMapping("/batchEditInfo")
    public void batchEditInfo() {
        User user1 = new User(1, "tom", new Date(), "9", "北京", "156565656");
        User user2 = new User(2, "jim", new Date(), "9", "北京", "156565656");
        User user3 = new User(20, "lilei", new Date(), "9", "北京", "156565656");
        List<User> users = Arrays.asList(user1, user2, user3);
        userService.batchSaveOrUpdate(users);
    }
}
