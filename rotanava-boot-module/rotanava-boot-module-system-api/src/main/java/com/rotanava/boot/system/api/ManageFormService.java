package com.rotanava.boot.system.api;

import com.rotanava.boot.system.api.module.system.dto.AddFormDTO;
import com.rotanava.boot.system.api.module.system.dto.AddTableFieldDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateFormDTO;
import com.rotanava.boot.system.api.module.system.vo.GetFormVO;
import com.rotanava.boot.system.api.module.system.vo.SysTableFieldVO;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;

import java.util.List;

/**
 * 管理形式服务
 *
 * @author weiqiangmiao
 * @date 2021/06/25
 */
public interface ManageFormService {


    /**
     * 列表形式
     *
     * @param baseDTO 基地dto
     * @return {@link BaseVO<GetFormVO> }
     * @author weiqiangmiao
     * @date 2021/06/26
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<GetFormVO> listForms(BaseDTO baseDTO);

    /**
     * 获得表单
     *
     * @param tableConfigId 表配置id
     * @return
     * @author weiqiangmiao
     * @date 2021/06/26
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    List<SysTableFieldVO> getAttributes(Integer tableConfigId);

    /**
     * 保存表单
     *
     * @param addFormDTO 添加表单dto
     * @return
     * @author weiqiangmiao
     * @date 2021/06/26
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void saveForm(AddFormDTO addFormDTO);



    /**
     * 添加属性
     *
     * @param addTableFieldDTO 添加表字段签证官
     * @return
     * @author weiqiangmiao
     * @date 2021/06/28
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void addAttributes(AddTableFieldDTO addTableFieldDTO);

    /**
     * 更新表单
     *
     * @param updateFormDTO 更新表单dto
     * @return
     * @author weiqiangmiao
     * @date 2021/06/26
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateForm(UpdateFormDTO updateFormDTO);


    /**
     * 删除表单
     *
     * @return
     * @author weiqiangmiao
     * @date 2021/07/06
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void deleteForm(List<Integer> tableConfigIds);
}
