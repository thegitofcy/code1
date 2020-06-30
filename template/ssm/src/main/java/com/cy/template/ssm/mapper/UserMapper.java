package com.cy.template.ssm.mapper;

import com.cy.template.ssm.entry.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: template
 * @description:
 * @author: cy
 */
@Mapper
public interface UserMapper {
    List<User> findAll();

    void saveOrUpdate(User user);

    void batchSaveOrUpdate(List<User> users);
}
