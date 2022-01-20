package com.rotanava.boot.system.api.module.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统定时任务
 *
 * @author WeiQiangMiao
 * @date 2021-04-20
 */
@Data
public class SysTimingTaskVO implements Serializable {


    /**
     * 主键
     */
    private Long sysTimingTaskId;

    /**
     * 定时时间
     */
    private Long timing;


}
