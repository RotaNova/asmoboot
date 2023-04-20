package com.rotanava.boot.system.api.module.system.bean;

import com.rotanava.boot.system.api.module.system.bo.SysDepartPermission;
import com.rotanava.boot.system.api.module.system.bo.SysDepartRolePermission;
import com.rotanava.boot.system.api.module.system.bo.SysRolePermission;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermissionListAndId {

    List<SysRolePermission> sysRolePermissionList ;
    List<SysDepartPermission> sysDepartPermissionList ;
    List<SysDepartRolePermission> sysDepartRolePermissionList ;

    public PermissionListAndId(List<SysRolePermission> sysRolePermissionList, List<SysDepartPermission> sysDepartPermissionList, List<SysDepartRolePermission> sysDepartRolePermissionList) {

        this.sysRolePermissionList = !CollectionUtils.isEmpty(sysRolePermissionList) ? sysRolePermissionList : new ArrayList<>();
        this.sysDepartPermissionList = !CollectionUtils.isEmpty(sysDepartPermissionList) ? sysDepartPermissionList : new ArrayList<>();
        this.sysDepartRolePermissionList = !CollectionUtils.isEmpty(sysDepartRolePermissionList) ? sysDepartRolePermissionList : new ArrayList<>();
    }

    public PermissionListAndId() {
    }
}
