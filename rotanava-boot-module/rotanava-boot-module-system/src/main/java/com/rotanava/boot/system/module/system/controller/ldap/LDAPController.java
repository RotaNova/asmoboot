package com.rotanava.boot.system.module.system.controller.ldap;

import com.rotanava.boot.system.api.LDAPService;
import com.rotanava.boot.system.api.module.system.dto.UpdateLdapDTO;
import com.rotanava.boot.system.api.module.system.vo.GetLdapVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.code.RetData;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * ldap控制器
 *
 * @author WeiQiangMiao
 * @date 2021-04-23
 */
@Api(tags = "LDAP")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/ldap")
public class LDAPController {

    @Autowired
    private LDAPService ldapService;

    @AutoLog(value = "获取ldap",operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getLdap")
    public RetData<GetLdapVO> getLdap() {

        return RetData.ok(ldapService.getLdap());
    }


    @AutoLog(value = "更新ldap",operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/updateLdap")
    public RetData<Void> updateLdap(@RequestBody UpdateLdapDTO updateLdapDTO) {
        ldapService.updateLdap(updateLdapDTO);
        return RetData.ok();
    }


}
