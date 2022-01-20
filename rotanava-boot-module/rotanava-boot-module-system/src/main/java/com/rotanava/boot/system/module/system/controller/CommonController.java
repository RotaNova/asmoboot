package com.rotanava.boot.system.module.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.module.system.bo.SysPageDataConfig;
import com.rotanava.boot.system.api.module.system.bo.SysPageModuleType;
import com.rotanava.boot.system.api.module.system.bo.SysPagePermission;
import com.rotanava.boot.system.api.module.system.bo.SysTableConfig;
import com.rotanava.boot.system.api.module.system.bo.SysTableField;
import com.rotanava.boot.system.api.module.system.bo.SysTableUser;
import com.rotanava.boot.system.api.module.system.dto.DuplicateCheckDTO;
import com.rotanava.boot.system.api.module.system.vo.UserTableConfigVO;
import com.rotanava.boot.system.api.module.system.vo.WelcomePageVO;
import com.rotanava.boot.system.api.module.system.vo.WelcomePagesVO;
import com.rotanava.boot.system.module.dao.SysPageDataConfigMapper;
import com.rotanava.boot.system.module.dao.SysPageModuleTypeMapper;
import com.rotanava.boot.system.module.dao.SysPagePermissionMapper;
import com.rotanava.boot.system.module.dao.SysTableConfigMapper;
import com.rotanava.boot.system.module.dao.SysTableFieldMapper;
import com.rotanava.boot.system.module.dao.SysTableUserMapper;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.common.api.CommonApi;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.SysDictItem;
import com.rotanava.framework.model.bo.SysSearchConfig;
import com.rotanava.framework.model.vo.SearchOptionVO;
import com.rotanava.framework.model.vo.SearchRuleVO;
import com.rotanava.framework.module.dao.SysSearchConfigMapper;
import com.rotanava.framework.util.RedisUtil;
import com.rotanava.framework.util.StringUtil;
import com.rotanava.framework.util.SysUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-15 11:38
 **/
@RestController
@RequestMapping("/v1/common")
public class CommonController {

    @Autowired
    SysSearchConfigMapper sysSearchConfigMapper;

    @DubboReference
    CommonApi commonApi;

    @Autowired
    SysTableFieldMapper sysTableFieldMapper;

    @Autowired
    SysTableConfigMapper sysTableConfigMapper;

    @Autowired
    SysTableUserMapper sysTableUserMapper;

    @Autowired
    private SysPagePermissionMapper sysPagePermissionMapper;

    @Autowired
    private SysPageModuleTypeMapper sysPageModuleTypeMapper;

    @Autowired
    private SysPageDataConfigMapper sysPageDataConfigMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @AdviceResponseBody
    @GetMapping("/getSearchConfigByPageId")
    @AutoLog(value = "获取搜索条件", operateType = OperateTypeEnum.SELECT)
    public RetData getSearchConfigByPageId(Integer pageId) {

//        List<SysSearchConfig> configByPageId = sysSearchConfigMapper.getConfigByPageId(pageId);

//        List<SearchRuleVO> ruleList = new ArrayList<>();
//        for (SysSearchConfig sysSearchConfig : configByPageId) {
//            SearchRuleVO searchRuleVO = new SearchRuleVO();
//            searchRuleVO.setMaxSize(sysSearchConfig.getMaxSize());
//            searchRuleVO.setMinSize(sysSearchConfig.getMinSize());
//            searchRuleVO.setId(sysSearchConfig.getId());
//            searchRuleVO.setReqName(sysSearchConfig.getReqName());
//            searchRuleVO.setTitle(sysSearchConfig.getTitle());
//            searchRuleVO.setInputType(sysSearchConfig.getInputType());
//            searchRuleVO.setDefaultRule(sysSearchConfig.getDefaultRule());
//
//            if (!StringUtil.isNullOrEmpty(sysSearchConfig.getDictName())){
//                //取字典项数据
//                List<SysDictItem> dictItem = commonApi.getDictItem(sysSearchConfig.getDictName());
//                for (SysDictItem sysDictItem : dictItem) {
//                    SearchOptionVO searchOptionVO = new SearchOptionVO();
//                    searchOptionVO.setValue(sysDictItem.getItemValue());
//                    searchOptionVO.setTitle(sysDictItem.getItemText());
//                    searchRuleVO.addOption(searchOptionVO);
//                }
//            }
//
//            //有预搜索功能
//            if (!StringUtil.isNullOrEmpty(sysSearchConfig.getSqlText())){
//                searchRuleVO.setIspPreview(true);
//                searchRuleVO.setOption(new ArrayList<>());
//            }
//
//            ruleList.add(searchRuleVO);
//        }
//        return new RetData(ruleList);
        return null;
    }


    @AdviceResponseBody
    @GetMapping("/getAllSearchConfig")
    @AutoLog(value = "获取全部搜索条件", operateType = OperateTypeEnum.SELECT)
    public RetData getAllSearchConfig() {

        List<SysSearchConfig> configByPageId = sysSearchConfigMapper.getAllConfig();

        List<SearchRuleVO> ruleList = new ArrayList<>();
        for (SysSearchConfig sysSearchConfig : configByPageId) {
            SearchRuleVO searchRuleVO = new SearchRuleVO();
            searchRuleVO.setMaxSize(sysSearchConfig.getMaxSize());
            searchRuleVO.setMinSize(sysSearchConfig.getMinSize());
            searchRuleVO.setId(sysSearchConfig.getId());
            searchRuleVO.setReqName(sysSearchConfig.getReqName());
            searchRuleVO.setTitle(sysSearchConfig.getTitle());
            searchRuleVO.setInputType(sysSearchConfig.getInputType());
            searchRuleVO.setDefaultRule(sysSearchConfig.getDefaultRule());
            searchRuleVO.setSearchCode(sysSearchConfig.getSearchCode());
            searchRuleVO.setSort(sysSearchConfig.getSort());

            if (!StringUtil.isNullOrEmpty(sysSearchConfig.getDictName())) {
                //取字典项数据
                List<SysDictItem> dictItem = commonApi.getDictItem(sysSearchConfig.getDictName());
                for (SysDictItem sysDictItem : dictItem) {
                    SearchOptionVO searchOptionVO = new SearchOptionVO();
                    searchOptionVO.setValue(sysDictItem.getItemValue());
                    searchOptionVO.setTitle(sysDictItem.getItemText());
                    searchRuleVO.addOption(searchOptionVO);
                }
            }

            //有预搜索功能
            if (!StringUtil.isNullOrEmpty(sysSearchConfig.getSqlText())) {
                searchRuleVO.setIspPreview(true);
                searchRuleVO.setOption(new ArrayList<>());
            }

            ruleList.add(searchRuleVO);
        }
        return new RetData(ruleList);
    }

    @AdviceResponseBody
    @GetMapping("/getPreviewSearchById")
    @AutoLog(value = "获取搜索条件", operateType = OperateTypeEnum.SELECT)
    public RetData getPreviewSearchById(@RequestParam("searchId") Integer searchId
            , @RequestParam("pageNum") Integer pageNum
            , @RequestParam("pageSize") Integer pageSize
            , @RequestParam("content") String content) {
        SysSearchConfig sysSearchConfig = sysSearchConfigMapper.selectById(searchId);
        String sql = sysSearchConfig.getSqlText();
        sql = sql.replace("#{text}", content);
        List<String> list = commonApi.doSearchSql(pageNum, pageSize, sql);
        Long count = commonApi.doSearchSqlCount(sql);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("data", list);
        return new RetData(map);
    }

    @AdviceResponseBody
    @PostMapping("/check")
    public RetData<Boolean> doDuplicateCheck(@RequestBody DuplicateCheckDTO duplicateCheckDTO) {

        return commonApi.doDuplicateCheck(duplicateCheckDTO.getTableName(), duplicateCheckDTO.getFieldName(), duplicateCheckDTO.getFieldVal(), duplicateCheckDTO.getTableId());

    }

    @AdviceResponseBody
    @PostMapping("/getTableConfigByCode")
    public RetData getTableConfigByCode(@RequestBody JSONObject json) {
        String tableCode = json.getString("tableCode");
        QueryWrapper<SysTableConfig> sysTableConfigQueryWrapper = new QueryWrapper<>();
        sysTableConfigQueryWrapper.eq("table_code", tableCode);
        SysTableConfig sysTableConfig = sysTableConfigMapper.selectOne(sysTableConfigQueryWrapper);
        UserTableConfigVO userTableConfigVO = new UserTableConfigVO();
        if (sysTableConfig != null) {
            QueryWrapper<SysTableField> sysTableFieldQueryWrapper = new QueryWrapper<>();
            sysTableFieldQueryWrapper.eq("table_config_id", sysTableConfig.getId());
            List<SysTableField> byTableConfigId = sysTableFieldMapper.selectList(sysTableFieldQueryWrapper);

            QueryWrapper<SysTableUser> sysTableUserQueryWrapper = new QueryWrapper<>();
            sysTableUserQueryWrapper.eq("table_code", tableCode);
            sysTableUserQueryWrapper.eq("user_id", SysUtil.getCurrentReqUserId());
            SysTableUser sysTableUser = sysTableUserMapper.selectOne(sysTableUserQueryWrapper);


            userTableConfigVO.setFieldList(byTableConfigId);
            if (sysTableUser != null) {
                userTableConfigVO.setTableData(JSONObject.parseObject(sysTableUser.getTableData()));
            }

        }

        return RetData.ok(userTableConfigVO);
    }


    @AdviceResponseBody
    @PostMapping("/addTableConfigByCode")
    public RetData addTableConfigByCode(@RequestBody JSONObject json) {
        JSONObject tableData = json.getJSONObject("tableData");
        String tableCode = json.getString("tableCode");
        QueryWrapper<SysTableUser> sysTableUserQueryWrapper = new QueryWrapper<>();
        sysTableUserQueryWrapper.eq("table_code", tableCode);
        sysTableUserQueryWrapper.eq("user_id", SysUtil.getCurrentReqUserId());
        SysTableUser sysTableUser = sysTableUserMapper.selectOne(sysTableUserQueryWrapper);
        if (sysTableUser != null) {
            sysTableUser.setTableData(tableData.toJSONString());
            sysTableUser.setUpdateTime(new Date());
            sysTableUserMapper.updateById(sysTableUser);
        } else {
            sysTableUser = new SysTableUser();
            sysTableUser.setTableData(tableData.toJSONString());
            sysTableUser.setUserId(SysUtil.getCurrentReqUserId());
            sysTableUser.setCreateTime(new Date());
            sysTableUser.setUpdateTime(new Date());
            sysTableUser.setTableCode(tableCode);
            sysTableUserMapper.insert(sysTableUser);
        }

        return RetData.ok();
    }


    @AutoLog(value = "欢迎页面", operateType = OperateTypeEnum.SELECT)
    @AdviceResponseBody
    @GetMapping("/welcomePage")
    public RetData<List<WelcomePageVO>> welcomePage() {

        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final List<SysPageModuleType> sysPageModuleTypeList = sysPageModuleTypeMapper.selectList(Wrappers.emptyWrapper());
        List<SysPageDataConfig> sysPageDataConfigs = sysPageDataConfigMapper.selectList(Wrappers.emptyWrapper());
        List<SysPagePermission> sysPagePermissions = new ArrayList<>();
        if (loginUser.getIsAdmin()) {
            sysPagePermissions = sysPagePermissionMapper.findAdminWelcomePage();
        } else {
            sysPagePermissions = sysPagePermissionMapper.findWelcomePage(loginUser.getId());
        }


        Map<Integer, String> moduleTypeMap = sysPageModuleTypeList.stream().collect(Collectors.toMap(SysPageModuleType::getId, SysPageModuleType::getName));
        Map<Integer, List<SysPagePermission>> sysPageMap = sysPagePermissions.stream().collect(Collectors.toMap(SysPagePermission::getSysPageModuleTypeId,
                Lists::newArrayList,
                (oldVal, currVal) -> {
                    oldVal.addAll(currVal);
                    return oldVal;
                }));
        Map<Integer, SysPageDataConfig> pageDataConfigMap = sysPageDataConfigs.stream().collect(Collectors.toMap(SysPageDataConfig::getPageId, Function.identity()));


        List<WelcomePageVO> welcomePageVOList = new ArrayList<>();


        for (Map.Entry<Integer, String> entry : moduleTypeMap.entrySet()) {
            WelcomePageVO welcomePageVO = new WelcomePageVO();
            List<WelcomePagesVO> sysPages = new ArrayList<>();
            List<SysPagePermission> sysPagePermissionList = sysPageMap.get(entry.getKey());
            if (!CollectionUtils.isEmpty(sysPagePermissionList)) {
                for (SysPagePermission sysPagePermission : sysPagePermissionList) {
                    SysPageDataConfig sysPageDataConfig = pageDataConfigMap.get(sysPagePermission.getId());
                    String dataValue;

                    WelcomePagesVO welcomePagesVO = new WelcomePagesVO();
                    welcomePagesVO.setSysPageId(sysPagePermission.getId());
                    welcomePagesVO.setParentPageId(sysPagePermission.getParentPageId());
                    welcomePagesVO.setPageTitle(sysPagePermission.getPageTitle());
                    welcomePagesVO.setPageRedirect(sysPagePermission.getPageRedirect());
                    welcomePagesVO.setPageUrl(sysPagePermission.getPageUrl());
                    welcomePagesVO.setIsJump(sysPagePermission.getIsJump());
                    welcomePagesVO.setPageIcon(sysPagePermission.getPageIcon());
                    welcomePagesVO.setSort(sysPagePermission.getSort());
                    welcomePagesVO.setIsAuto(sysPagePermission.getIsAuto());
                    if (sysPageDataConfig != null) {
                        dataValue = stringRedisTemplate.opsForValue().get(sysPageDataConfig.getDataCode());
                        welcomePagesVO.setDataPrefix(sysPageDataConfig.getDataPrefix());
                        welcomePagesVO.setDataSuffix(sysPageDataConfig.getDataSuffix());
                        welcomePagesVO.setDataValue(dataValue);

                    }
                    sysPages.add(welcomePagesVO);
                }
                sysPages = sysPages.stream().sorted(Comparator.comparing(WelcomePagesVO::getSort)).collect(Collectors.toList());
            }

            welcomePageVO.setSysPages(sysPages);
            welcomePageVO.setSysPageModuleTypeId(entry.getKey());
            welcomePageVO.setSysPageModuleTypeName(entry.getValue());
            welcomePageVOList.add(welcomePageVO);
        }

        return RetData.ok(welcomePageVOList);
    }


}
