package com.rotanava.boot.system.api.module.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 接口资源VO
 *
 * @author WeiQiangMiao
 * @date 2021-03-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysApiAndCountVO implements Serializable {

    /**
     * 系统接口权限集合
     */
    List<SysApiPermissionVO> sysApiPermissions;

    /**
     * 数
     */
    Integer count;

}