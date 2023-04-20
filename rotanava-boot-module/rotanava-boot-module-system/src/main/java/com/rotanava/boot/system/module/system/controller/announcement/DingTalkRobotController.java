package com.rotanava.boot.system.module.system.controller.announcement;

import com.rotanava.boot.system.api.DingTalkRobotService;
import com.rotanava.boot.system.api.module.system.dto.AddDingTalkRobotDTO;
import com.rotanava.boot.system.api.module.system.dto.DeleteDingTalkRobotDTO;
import com.rotanava.boot.system.api.module.system.dto.TestDingTalkRobotDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateDingTalkRobotDTO;
import com.rotanava.boot.system.api.module.system.vo.DingTalkRobotVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "钉钉机器人")
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/dingTalkRobot")
public class DingTalkRobotController {


    @Autowired
    private DingTalkRobotService dingTalkRobotService;


    @PostMapping("addDingTalkRobot")
    @AutoLog(value = "添加钉钉机器人", operateType = OperateTypeEnum.ADD)
    @AdviceResponseBody
    public RetData<Void> addDingTalkRobot(@RequestBody AddDingTalkRobotDTO addDingTalkRobotDTO) {
        dingTalkRobotService.addDingTalkRobot(addDingTalkRobotDTO);
        return RetData.ok();
    }

    @PostMapping("deleteDingTalkRobot")
    @AutoLog(value = "删除钉钉机器人", operateType = OperateTypeEnum.DELETE)
    @AdviceResponseBody
    public RetData<Void> deleteDingTalkRobot(@RequestBody DeleteDingTalkRobotDTO deleteDingTalkRobotDTO) {
        dingTalkRobotService.deleteDingTalkRobot(deleteDingTalkRobotDTO);
        return RetData.ok();
    }


    @PostMapping("updateDingTalkRobot")
    @AutoLog(value = "编辑钉钉机器人", operateType = OperateTypeEnum.UPDATE)
    @AdviceResponseBody
    public RetData<Void> updateDingTalkRobot(@RequestBody UpdateDingTalkRobotDTO updateDingTalkRobotDTO) {
        dingTalkRobotService.updateDingTalkRobot(updateDingTalkRobotDTO);
        return RetData.ok();
    }


    @GetMapping("listDingTalkRobot")
    @AutoLog(value = "获取钉钉机器人列表", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<List<DingTalkRobotVO>> listDingTalkRobot(Integer sysAnnConfigId) {
        return RetData.ok(dingTalkRobotService.listDingTalkRobot(sysAnnConfigId));
    }



    @PostMapping("testDingTalk")
    @AutoLog(value = "测试钉钉机器人", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    public RetData<Void> testDingTalk(@RequestBody TestDingTalkRobotDTO testDingTalkRobotDTO) {
        dingTalkRobotService.testDingTalk(testDingTalkRobotDTO);
        return RetData.ok();
    }

}
