package com.cy.template.ssm.service;

import com.cy.template.ssm.entry.User;

import java.util.List;

/**
 * @program: template
 * @description:
 * @author: cy
 */
public interface UserService {

    List<User> findAll();

    void saveOrUpdate(User user);

    void batchSaveOrUpdate(List<User> users);
}
