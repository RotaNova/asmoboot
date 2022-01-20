package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.system.dto.UpdateLdapDTO;
import com.rotanava.boot.system.api.module.system.vo.GetLdapVO;

/**
 * ldapservice
 *
 * @author WeiQiangMiao
 * @date 2021-04-23
 */
public interface LDAPService {

    /**
     * 得到ldap
     *
     * @return {@link GetLdapVO }
     * @author WeiQiangMiao
     * @date 2021-04-23
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    GetLdapVO getLdap();

    /**
     * 更新ldap
     *
     * @param updateLdapDTO 更新ldap dto
     * @author WeiQiangMiao
     * @date 2021-04-23
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateLdap(UpdateLdapDTO updateLdapDTO);
}
