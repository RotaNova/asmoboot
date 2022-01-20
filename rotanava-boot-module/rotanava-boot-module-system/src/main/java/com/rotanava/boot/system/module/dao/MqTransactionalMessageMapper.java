package com.rotanava.boot.system.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rotanava.boot.system.api.module.system.bo.MqTransactionalMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2020-11-17 10:27
 */
public interface MqTransactionalMessageMapper extends BaseMapper<MqTransactionalMessage> {

    List<MqTransactionalMessage> findAllByStatus(@Param("status") Integer status);

    int deleteByStatus(@Param("status")Integer status);


}