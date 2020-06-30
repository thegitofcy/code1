package com.cy.template.ssm.service.impl;

import com.cy.template.ssm.entry.User;
import com.cy.template.ssm.mapper.UserMapper;
import com.cy.template.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: template
 * @description:
 * @author: cy
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public void saveOrUpdate(User user) {
        userMapper.saveOrUpdate(user);
    }

    @Override
    public void batchSaveOrUpdate(List<User> users) {
        userMapper.batchSaveOrUpdate(users);
    }
}
