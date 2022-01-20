package com.rotanava.boot.system.api.module.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author WeiQiangMiao
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class VisitorBackup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 访客量
     */
    private Long visitorVolume;

    /**
     * 平均使用时长
     */
    private Long averageUsageTime;

    /**
     * 最小时长时长
     */
    private Long minUsageTime;

    /**
     * 最长使用时长
     */
    private Long maxUsageTime;

    /**
     * 创建时间
     */
    private Date createTime;

    public VisitorBackup(Long visitorVolume, Long averageUsageTime, Long minUsageTime, Long maxUsageTime, Date createTime) {
        this.visitorVolume = visitorVolume;
        this.averageUsageTime = averageUsageTime;
        this.minUsageTime = minUsageTime;
        this.maxUsageTime = maxUsageTime;
        this.createTime = createTime;
    }
}
