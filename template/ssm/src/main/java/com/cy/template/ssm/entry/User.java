package com.cy.template.ssm.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: template
 * @description:
 * @author: cy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String cusName;
    private Date birthday;
    private String cusSex;
    private String cusAddr;
    private String cusIphone;
}
