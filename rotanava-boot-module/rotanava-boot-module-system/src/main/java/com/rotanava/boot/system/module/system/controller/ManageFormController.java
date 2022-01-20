package com.rotanava.boot.system.module.system.controller;

import com.rotanava.boot.system.api.ManageFormService;
import com.rotanava.boot.system.api.module.system.dto.AddFormDTO;
import com.rotanava.boot.system.api.module.system.dto.AddTableFieldDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateFormDTO;
import com.rotanava.boot.system.api.module.system.vo.GetFormVO;
import com.rotanava.boot.system.api.module.system.vo.SysTableFieldVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理形式控制器
 *
 * @author weiqiangmiao
 * @date 2021/06/25
 */
@Api(tags = "表格管理")
@RestController
@RequestMapping("/v1/manageForm")
public class ManageFormController {

    @Autowired
    private ManageFormService manageFormService;

    @AutoLog(value = "获取表格列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @PostMapping("/listForms")
    public RetData<BaseVO<GetFormVO>> listForms(@RequestBody BaseDTO baseDTO) {
        return RetData.ok(manageFormService.listForms(baseDTO));
    }

    @AutoLog(value = "获取属性", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/getAttributes")
    public RetData<List<SysTableFieldVO>> getAttributes(Integer tableConfigId) {
        return RetData.ok(manageFormService.getAttributes(tableConfigId));
    }



    @AutoLog(value = "添加表格", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/addForm")
    public RetData<Void> saveForm(@RequestBody AddFormDTO addFormDTO) {
        manageFormService.saveForm(addFormDTO);
        return RetData.ok();
    }

    @AutoLog(value = "添加属性", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    @PostMapping("/addAttributes")
    public RetData<Void> addAttributes(@RequestBody AddTableFieldDTO addTableFieldDTO) {
        manageFormService.addAttributes(addTableFieldDTO);
        return RetData.ok();
    }

    @AutoLog(value = "更新表格", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    @PostMapping("/updateForm")
    public RetData<Void> updateForm(@RequestBody UpdateFormDTO updateFormDTO) {
        manageFormService.updateForm(updateFormDTO);
        return RetData.ok();
    }

    @AutoLog(value = "删除表格", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    @PostMapping("/deleteForm")
    public RetData<Void> deleteForm(@RequestBody List<Integer> tableConfigIds) {
        manageFormService.deleteForm(tableConfigIds);
        return RetData.ok();
    }

}
