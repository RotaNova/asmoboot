package com.rotanava.boot.system.api.module.system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 欢迎页面
 *
 * @author weiqiangmiao
 * @date 2021/07/07
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WelcomePageVO extends SysPageModuleTypeVO{

    private List<WelcomePagesVO> sysPages;

}
