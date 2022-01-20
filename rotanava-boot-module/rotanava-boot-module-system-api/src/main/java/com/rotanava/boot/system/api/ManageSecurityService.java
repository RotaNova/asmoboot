package com.rotanava.boot.system.api;

import com.rotanava.framework.model.bo.ManageSecurity;
import com.rotanava.boot.system.api.module.system.dto.ManageSecurityDTO;
import com.rotanava.boot.system.api.module.system.vo.ManageSecurityVO;
import com.rotanava.framework.model.BaseVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 管理安全服务
 *
 * @author WeiQiangMiao
 * @date 2021-03-23
 */
@Validated
public interface ManageSecurityService {

    /**
     * 得到管理安全签证官
     *
     * @return {@link ManageSecurityVO }
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    ManageSecurity getManageSecurity();

    /**
     * 得到管理安全
     *
     * @return {@link BaseVO<ManageSecurityVO> }
     * @author WeiQiangMiao
     * @date 2021-03-23
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<ManageSecurityVO> getManageSecurityVO();

    /**
     * 更新管理安全
     *
     * @param manageSecurityDTO 管理安全dto
     * @author WeiQiangMiao
     * @date 2021-03-23
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateManageSecurity(@Valid ManageSecurityDTO manageSecurityDTO);

    ManageSecurity updateManageSecurity();
}
