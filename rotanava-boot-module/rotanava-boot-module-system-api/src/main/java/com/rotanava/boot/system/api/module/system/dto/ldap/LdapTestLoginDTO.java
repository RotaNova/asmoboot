package com.rotanava.boot.system.api.module.system.dto.ldap;

import lombok.Data;

/**
 * @description:
 * @author: richenLi
 * @create: 2022-05-06 10:13
 **/
@Data
public class LdapTestLoginDTO {

    private String password;

    private String userName;
}
