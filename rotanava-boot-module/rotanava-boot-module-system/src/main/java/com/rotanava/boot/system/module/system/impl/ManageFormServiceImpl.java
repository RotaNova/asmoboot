package com.rotanava.boot.system.module.system.impl;
import java.util.Date;


import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.ManageFormService;
import com.rotanava.boot.system.api.module.system.bo.SysTableConfig;
import com.rotanava.boot.system.api.module.system.bo.SysTableField;
import com.rotanava.boot.system.api.module.system.dto.*;
import com.rotanava.boot.system.api.module.system.vo.GetFormVO;
import com.rotanava.boot.system.api.module.system.vo.SysTableFieldVO;
import com.rotanava.boot.system.module.dao.SysTableConfigMapper;
import com.rotanava.boot.system.module.dao.SysTableFieldMapper;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理形式服务impl
 *
 * @author weiqiangmiao
 * @date 2021/06/25
 */
@Service
public class ManageFormServiceImpl implements ManageFormService {

    @Autowired
    private SysTableConfigMapper sysTableConfigMapper;

    @Autowired
    private SysTableFieldMapper sysTableFieldMapper;


    @Override
    public BaseVO<GetFormVO> listForms(BaseDTO baseDTO) {
        QueryWrapper<SysTableConfig> queryWrapper = QueryGenerator.initQueryWrapper(baseDTO);
        IPage<SysTableConfig> page = sysTableConfigMapper.selectPage(PageUtils.startPage(baseDTO), queryWrapper);
        List<GetFormVO> formVOList = new ArrayList<>();
        if (page.getTotal() != 0) {
            Map<Integer, ArrayList<String>> map = sysTableFieldMapper.findByTableConfigIdIn(page.getRecords().stream().map(SysTableConfig::getId).collect(Collectors.toList()))
                    .stream().collect(Collectors.toMap(SysTableField::getTableConfigId, sysTableField -> Lists.newArrayList(sysTableField.getFieldName()),
                            (oldVal, currVal) -> {
                                oldVal.addAll(currVal);
                                return oldVal;
                            }));
            for (SysTableConfig sysTableConfig : page.getRecords()) {
                GetFormVO getFormVO = new GetFormVO();
                getFormVO.setTableConfigId(sysTableConfig.getId());
                getFormVO.setTableName(sysTableConfig.getTableName());
                getFormVO.setTableCode(sysTableConfig.getTableCode());
                getFormVO.setFieldNames(map.get(sysTableConfig.getId()));
                formVOList.add(getFormVO);
            }


        }
        BaseVO<GetFormVO> baseVO = new BaseVO<>();
        baseVO.setTotal(page.getTotal());
        baseVO.setRecords(formVOList);
        return baseVO;
    }

    @Override
    public List<SysTableFieldVO> getAttributes(Integer tableConfigId) {

        List<SysTableFieldVO> sysTableFieldVOList = new ArrayList<>();
        List<SysTableField> sysTableFields = sysTableFieldMapper.findByTableConfigId(tableConfigId);
        for (SysTableField sysTableField : sysTableFields) {
            SysTableFieldVO sysTableFieldVO = new SysTableFieldVO();
            sysTableFieldVO.setTableFieldId(sysTableField.getId());
            sysTableFieldVO.setFieldName(sysTableField.getFieldName());
            sysTableFieldVO.setFieldCode(sysTableField.getFieldCode());
            sysTableFieldVO.setFixed(sysTableField.getFixed());
            sysTableFieldVO.setWidth(sysTableField.getWidth());
            sysTableFieldVO.setAlign(sysTableField.getAlign());
            sysTableFieldVO.setScopedSlots(sysTableField.getScopedSlots());
            sysTableFieldVOList.add(sysTableFieldVO);
        }
        return sysTableFieldVOList;
    }

    @Override
    public void saveForm(AddFormDTO addFormDTO) {

        SysTableConfig sysTableConfig = new SysTableConfig();
        sysTableConfig.setTableName(addFormDTO.getTableName());
        sysTableConfig.setTableCode(addFormDTO.getTableCode());
        sysTableConfig.setCreateTime(new DateTime());
        sysTableConfigMapper.insert(sysTableConfig);
    }


    @Override
    public void addAttributes(AddTableFieldDTO addTableFieldDTO) {

        SysTableField sysTableField = new SysTableField();

        sysTableField.setFieldName(sysTableField.getFieldName());
        sysTableField.setFieldCode(addTableFieldDTO.getFieldCode());
        sysTableField.setFixed(addTableFieldDTO.getFixed());
        sysTableField.setWidth(addTableFieldDTO.getWidth());
        sysTableField.setAlign(addTableFieldDTO.getAlign());
        sysTableField.setScopedSlots(addTableFieldDTO.getScopedSlots());
        sysTableField.setCreateTime(new DateTime());
        sysTableField.setTableConfigId(addTableFieldDTO.getTableConfigId());

        sysTableFieldMapper.insert(sysTableField);
    }

    @Override
    public void updateForm(UpdateFormDTO updateFormDTO) {

        SysTableConfig sysTableConfig = new SysTableConfig();
        sysTableConfig.setId(updateFormDTO.getTableConfigId());
        sysTableConfig.setTableName(updateFormDTO.getTableName());
        sysTableConfig.setTableCode(updateFormDTO.getTableCode());
        sysTableConfigMapper.updateById(sysTableConfig);

        if(!CollectionUtils.isEmpty(updateFormDTO.getTableFields())){
            List<SysTableField> sysTableFields = new ArrayList<>();
            for (SysTableFieldDTO tableField : updateFormDTO.getTableFields()) {
                SysTableField sysTableField = new SysTableField();
                sysTableField.setId(tableField.getTableFieldId());
                sysTableField.setFieldName(tableField.getFieldName());
                sysTableField.setFieldCode(tableField.getFieldCode());
                sysTableField.setFixed(tableField.getFixed());
                sysTableField.setWidth(tableField.getWidth());
                sysTableField.setAlign(tableField.getAlign());
                sysTableField.setScopedSlots(tableField.getScopedSlots());
                sysTableField.setTableConfigId(sysTableConfig.getId());
                sysTableField.setCreateTime(new DateTime());
                sysTableFields.add(sysTableField);
            }
            sysTableFieldMapper.insertListOrUpdate(sysTableFields);
        }

        if(!CollectionUtils.isEmpty(updateFormDTO.getRemoveTableFieldIds())){
            sysTableFieldMapper.deleteBatchIds(updateFormDTO.getRemoveTableFieldIds());
        }

    }

    @Override
    public void deleteForm(List<Integer> tableConfigIds) {

        if(!CollectionUtils.isEmpty(tableConfigIds)){
            sysTableConfigMapper.deleteBatchIds(tableConfigIds);
        }
    }


}
