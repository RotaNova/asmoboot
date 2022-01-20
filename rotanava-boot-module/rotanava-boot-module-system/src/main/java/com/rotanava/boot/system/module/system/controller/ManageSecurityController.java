package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.ManageSecurityService;
import com.rotanava.boot.system.api.module.system.dto.ManageSecurityDTO;
import com.rotanava.boot.system.api.module.system.vo.ManageSecurityVO;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.code.RetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理安全控制器
 *
 * @author WeiQiangMiao
 * @date 2021-03-23
 */
@RestController
@RequestMapping("/v1/manageSecurity")
public class ManageSecurityController {

    @Autowired
    private ManageSecurityService manageSecurityService;


    @AutoLog(value = "获取安全管理")
    @AdviceResponseBody
    @GetMapping("/getManageSecurity")
    public RetData<BaseVO<ManageSecurityVO>> getManageSecurity() {
        return RetData.ok(manageSecurityService.getManageSecurityVO());
    }

    @AutoLog(value = "更新安全管理",operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PutMapping("/updateManageSecurity")
    public RetData<Void> updateManageSecurity(@RequestBody ManageSecurityDTO manageSecurityDTO) {
        manageSecurityService.updateManageSecurity(manageSecurityDTO);
        return RetData.ok();
    }





}
