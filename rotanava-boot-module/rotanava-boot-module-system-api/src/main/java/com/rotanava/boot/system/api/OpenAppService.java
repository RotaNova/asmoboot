package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rotanava.boot.system.api.module.system.bo.OpenApp;
import com.rotanava.boot.system.api.module.system.dto.CreateApplicationDTO;
import com.rotanava.boot.system.api.module.system.dto.EnableAppDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateApplicationDTO;
import com.rotanava.boot.system.api.module.system.vo.OpenAppItemVO;
import com.rotanava.boot.system.api.module.system.vo.OpenAppVO;
import com.rotanava.framework.model.BaseDTO;
import org.springframework.validation.annotation.Validated;

/**
 * @description: 开发应用api
 * @author: jintengzhou
 * @date: 2021-04-19 15:05
 */
@Validated
public interface OpenAppService {

    /**
     * 功能: 创建应用
     * 作者: zjt
     * 日期: 2021/4/19 15:06
     * 版本: 1.0
     */
    void createOpenApp(CreateApplicationDTO createApplication, int userId);

    /**
     * 功能: 更新应用
     * 作者: zjt
     * 日期: 2021/4/19 15:42
     * 版本: 1.0
     */
    void updateOpenApp(UpdateApplicationDTO updateApplication, byte[] imageByte);

    /**
     * 功能: 删除应用
     * 作者: zjt
     * 日期: 2021/4/19 16:13
     * 版本: 1.0
     */
    void deleteOpenApp(int openAppId);

    /**
     * 功能: 获取应用详情
     * 作者: zjt
     * 日期: 2021/4/19 16:15
     * 版本: 1.0
     */
    OpenAppVO getOpenApp(int openAppId);

    /**
     * 功能: 分页获取应用列表
     * 作者: zjt
     * 日期: 2021/4/19 17:34
     * 版本: 1.0
     */
    IPage<OpenAppItemVO> getOpenAppPageList(BaseDTO baseDTO);

     /**
      * 功能: 是否启用该应用
      * 作者: zjt
      * 日期: 2021/7/6 15:48
      * 版本: 1.0
      */
    void enableApp(EnableAppDTO enableAppDTO);



    /**
     * @description : 根据key和secret获取应用信息
     * @Author : richenLi
     * @version :  1.0
     */
    OpenApp getAppByKeyAndSecret(String appKey,String appSecret);
}
