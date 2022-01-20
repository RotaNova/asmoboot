package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.system.dto.CheckUrlResPerDTO;
import com.rotanava.framework.code.RetData;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * shiro许可服务
 *
 * @author weiqiangmiao
 * @date 2021/06/10
 */
public interface ShiroPermissionService {

    /**
     * 根据token 校验该用户是否具路径权限

     * @author WeiQiangMiao
     * @date 2020/8/18
     * @return boolean
     * @version 1.0.0
     */
    RetData<Void> checkUrlResPerByToken(@RequestBody CheckUrlResPerDTO checkUrlResPerDTO);

}
