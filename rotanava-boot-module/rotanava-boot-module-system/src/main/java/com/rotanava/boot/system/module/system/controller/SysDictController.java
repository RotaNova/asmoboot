package com.rotanava.boot.system.module.system.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.rotanava.boot.system.api.SysAnnouncementSenderService;
import com.rotanava.boot.system.api.SysDictItemService;
import com.rotanava.boot.system.api.SysDictService;
import com.rotanava.boot.system.api.module.constant.*;
import com.rotanava.boot.system.api.module.system.bean.ExportDictExcel;
import com.rotanava.boot.system.api.module.system.bean.ExportPageExcel;
import com.rotanava.boot.system.api.module.system.bean.ImportDictExcel;
import com.rotanava.boot.system.api.module.system.bean.ImportPageExcel;
import com.rotanava.boot.system.api.module.system.bo.SysPagePermission;
import com.rotanava.boot.system.api.module.system.dto.AddSysPagePermissionDTO;
import com.rotanava.boot.system.api.module.system.dto.GetDictItemDTO;
import com.rotanava.boot.system.api.module.system.vo.SysPageModuleTypeVO;
import com.rotanava.boot.system.module.dao.SysDictItemMapper;
import com.rotanava.framework.code.ErrorCode;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.constant.CacheConstant;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.config.mybatis.query.QueryGenerator;
import com.rotanava.framework.exception.code.DBErrorCode;
import com.rotanava.framework.exception.code.FileErrorCode;
import com.rotanava.framework.exception.code.ParamErrorCode;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.SysDict;
import com.rotanava.framework.model.SysDictItem;
import com.rotanava.framework.common.aspect.annotation.AdviceResponseBody;
import com.rotanava.framework.common.aspect.annotation.AutoLog;
import com.rotanava.framework.common.constant.enums.OperateTypeEnum;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.code.RetData;
import com.rotanava.framework.util.*;
import com.rotanava.framework.util.excel.ExcelUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.SecurityUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: ?????????????????????
 * @author: richenLi
 * @create: 2021-03-08 17:51
 **/
@RestController
@RequestMapping("/v1/sysDict")
public class SysDictController {


    @Autowired
    SysDictService sysDictService;

    @Autowired
    SysDictItemService sysDictItemService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    FileUploadUtil fileUploadUtil;

    @Autowired
    private SysAnnouncementSenderService sysAnnouncementSenderService;

    /**
     * @description :????????????????????????
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    @AdviceResponseBody
    @GetMapping("/getDictList")
    @AutoLog("??????????????????")
    public RetData<BaseVO<SysDict>> getDictList(@RequestParam(value = "dictName", required = false) String dictName
            , @RequestParam(value = "dictCode", required = false) String dictCode) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        if (!StringUtil.isNullOrEmpty(dictName)) {
            queryWrapper.like("dict_name", dictName);
        }
        if (!StringUtil.isNullOrEmpty(dictCode)) {
            queryWrapper.like("dict_code", dictCode);
        }
        queryWrapper.eq("delete_status", "1");
        IPage<SysDict> page = sysDictService.page(PageUtils.startPage(), queryWrapper);
        BaseVO<SysDict> baseVo = BuildUtil.buildListVO(page.getTotal(), page.getRecords());
        return new RetData<>(baseVo);
    }


    /**
     * @description : ??????????????????
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    @AdviceResponseBody
    @PostMapping("/updateDict")
    @AutoLog(value = "??????????????????", operateType = OperateTypeEnum.UPDATE)
    public RetData updateDict(@RequestBody SysDict sysDict) {
        sysDict.setUpdateTime(new Date());
        sysDictService.updateById(sysDict);
        return new RetData<>();
    }


    @AdviceResponseBody
    @PostMapping("/addDict")
    @AutoLog(value = "??????????????????", operateType = OperateTypeEnum.ADD)
    public RetData addDict(@RequestBody SysDict sysDict) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDict::getDictCode,sysDict.getDictCode());
        int count = sysDictService.count(queryWrapper);
        if(count > 0){
            throw new CommonException(ParamErrorCode.PARAM_ERROR_92);
        }
        sysDict.setDeleteStatus(1);
        sysDict.setCreateTime(new Date());
        sysDict.setUpdateTime(new Date());
        sysDictService.save(sysDict);
        return new RetData<>();
    }


    @PostMapping("/deleteDict")
    @AutoLog(value = "??????????????????", operateType = OperateTypeEnum.DELETE)
    public RetData deleteDict(@RequestBody List<Integer> list) {


        List<SysDict> sysDicts = sysDictService.listByIds(list);
        for (SysDict sysDict : sysDicts) {
            if (sysDict.getDeleteStatus() == 1) {
                sysDict.setDeleteStatus(0);
                sysDictService.updateById(sysDict);
            } else if (sysDict.getDeleteStatus() == 0) {
                sysDictService.removeById(sysDict.getId());
                //????????????????????????????????????
                QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("dict_id", sysDict.getId());
                sysDictItemService.remove(queryWrapper);
            }
        }
        return BuildUtil.buildSuccessResult();
    }


    /**
     * @description : ??????????????????
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    @AdviceResponseBody
    @PostMapping("/getDictItemByDict")
    @AutoLog("??????????????????")
    public RetData<BaseVO<SysDictItem>> getDictItemByDict(@RequestBody GetDictItemDTO getDictItemDTO) {
        if (StringUtil.isNullOrEmpty(getDictItemDTO.getDictId())) {
            return new RetData<>();
        }
        final QueryWrapper<SysDictItem> queryWrapper = QueryGenerator.initQueryWrapper(getDictItemDTO);

        queryWrapper.eq("dict_id", getDictItemDTO.getDictId());

        queryWrapper.orderByDesc("sort");

        final IPage<SysDictItem> page = sysDictItemService.page(PageUtils.startPage(getDictItemDTO), queryWrapper);
        BaseVO<SysDictItem> baseVo = BuildUtil.buildListVO(page.getTotal(), page.getRecords());
        return new RetData<>(baseVo);
    }


    @AdviceResponseBody
    @PostMapping("/addDictItem")
    @AutoLog(value = "???????????????", operateType = OperateTypeEnum.ADD)
    public RetData addDictItem(@RequestBody SysDictItem sysDictItem) {
        if (sysDictService.getById(sysDictItem.getDictId()) == null) {
            throw new CommonException(ParamErrorCode.PARAM_ERROR_93);
        }
        LambdaQueryWrapper<SysDictItem> sysDictItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictItemLambdaQueryWrapper.eq(SysDictItem::getItemText,sysDictItem.getItemText());
        sysDictItemLambdaQueryWrapper.eq(SysDictItem::getItemValue,sysDictItem.getItemValue());
        sysDictItemLambdaQueryWrapper.eq(SysDictItem::getDictId,sysDictItem.getDictId());
        int count = sysDictItemService.count(sysDictItemLambdaQueryWrapper);
        if(count > 0){
            throw new CommonException(ParamErrorCode.PARAM_ERROR_94);
        }
        sysDictItem.setCreateTime(new Date());
        sysDictItem.setUpdateTime(new Date());
        sysDictItemService.save(sysDictItem);
        return BuildUtil.buildSuccessResult();
    }

    @AdviceResponseBody
    @PostMapping("/updateDictItem")
    @AutoLog(value = "?????????????????????", operateType = OperateTypeEnum.UPDATE)
    public RetData updateDictItem(@RequestBody SysDictItem sysDictItem) {
        if (sysDictService.getById(sysDictItem.getDictId()) == null) {
            throw new CommonException(DBErrorCode.DB_ERROR_08);
        }
        sysDictItem.setUpdateTime(new Date());
        sysDictItemService.updateById(sysDictItem);
        return BuildUtil.buildSuccessResult();
    }


    @AdviceResponseBody
    @PostMapping("/deleteDictItem")
    @AutoLog(value = "?????????????????????", operateType = OperateTypeEnum.DELETE)
    public RetData deleteDictItem(@RequestBody List<Integer> list) {
        sysDictItemService.removeByIds(list);
        return BuildUtil.buildSuccessResult();
    }


    @AdviceResponseBody
    @GetMapping("/getRecycleDict")
    @AutoLog("???????????????????????????")
    public RetData getRecycleDict() {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_status", CommonConstant.DEL_FLAG_0);
        return new RetData(sysDictService.list(queryWrapper));
    }


    @AdviceResponseBody
    @PostMapping("/restoreDict")
    @AutoLog("??????????????????")
    public RetData restoreDict(@RequestBody List<Integer> list) {
        List<SysDict> sysDictList = sysDictService.listByIds(list);
        for (SysDict sysDict : sysDictList) {
            sysDict.setDeleteStatus(CommonConstant.DEL_FLAG_1);
            sysDictService.updateById(sysDict);
        }
        return BuildUtil.buildSuccessResult();
    }


    @AdviceResponseBody
    @GetMapping("/refreshDictCache")
    @AutoLog("??????????????????")
    public RetData refreshDictCache() {
        Set keys = redisTemplate.keys(CacheConstant.SYS_DICT_CACHE + "*");
        Set keys2 = redisTemplate.keys(CacheConstant.SYS_DICT_TABLE_CACHE + "*");
        redisTemplate.delete(keys);
        redisTemplate.delete(keys2);
        return BuildUtil.buildSuccessResult();
    }

    @AdviceResponseBody
    @PostMapping("/importExcel")
    @AutoLog(value = "??????Excel", operateType = OperateTypeEnum.SELECT)
    public RetData<Void> importExcel(@RequestParam(value = "file") MultipartFile file) throws Exception {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        final String originalFilename = file.getOriginalFilename();
        //??????????????????
        final List<ImportDictExcel> errorDictExcels = Lists.newArrayList();
        List<ImportDictExcel> dictExcels;
        try {
            dictExcels = ExcelUtils.parse(file.getInputStream(), ImportDictExcel.class);
        } catch (Exception e) {
            //??????????????????
            throw new CommonException(ParamErrorCode.PARAM_ERROR_60);
        }

        for (ImportDictExcel dictExcel : dictExcels) {
            if (StringUtils.isEmpty(dictExcel.getDictCode())) {
                dictExcel.setFailReason("????????????????????????");
                errorDictExcels.add(dictExcel);
                continue;
            }
            if (DictType.DICTIONARY.getType().equals(dictExcel.getType())) {

                SysDict sysDict = new SysDict();
                sysDict.setDictCode(dictExcel.getDictCode());
                if (!StringUtils.isEmpty(dictExcel.getDictName())) {
                    sysDict.setDictName(dictExcel.getDictName());
                } else {
                    dictExcel.setFailReason("????????????????????????");
                    errorDictExcels.add(dictExcel);
                    continue;
                }
                sysDict.setDescription(dictExcel.getDescription());
                try {
                    addDict(sysDict);
                } catch (CommonException e) {
                    ErrorCode errorCode = e.getErrorCode();
                    dictExcel.setFailReason(errorCode.getMsg());
                    errorDictExcels.add(dictExcel);
                }
            } else if (DictType.DATA.getType().equals(dictExcel.getType())) {
                Integer status = DictStatus.findStatus(dictExcel.getStatus());
                LambdaQueryWrapper<SysDict> sysDictLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sysDictLambdaQueryWrapper.eq(SysDict::getDictCode,dictExcel.getDictCode());
                SysDict sysDict = sysDictService.getOne(sysDictLambdaQueryWrapper);

                SysDictItem sysDictItem = new SysDictItem();
                if(sysDict != null ){
                    sysDictItem.setDictId(Convert.toStr(sysDict.getId()));
                } else {
                    dictExcel.setFailReason("???????????????");
                    errorDictExcels.add(dictExcel);
                    continue;
                }
                if (!StringUtils.isEmpty(dictExcel.getItemText())) {
                    sysDictItem.setItemText(dictExcel.getItemText());
                } else {
                    dictExcel.setFailReason("????????????????????????");
                    errorDictExcels.add(dictExcel);
                    continue;
                }
                if (!StringUtils.isEmpty(dictExcel.getItemValue())) {
                    sysDictItem.setItemValue(dictExcel.getItemValue());
                } else {
                    dictExcel.setFailReason("?????????????????????");
                    errorDictExcels.add(dictExcel);
                    continue;
                }

                sysDictItem.setStatus(status != null ? status : DictStatus.YES.getStatus());
                sysDictItem.setDescription(dictExcel.getDescription());
                sysDictItem.setSort(NumberUtil.isNumber(dictExcel.getSort()) ? Convert.toInt(dictExcel.getSort()) : 1);
                try {
                    addDictItem(sysDictItem);
                } catch (CommonException e) {
                    ErrorCode errorCode = e.getErrorCode();
                    dictExcel.setFailReason(errorCode.getMsg());
                    errorDictExcels.add(dictExcel);
                }
            } else {
                dictExcel.setFailReason("??????????????????");
                errorDictExcels.add(dictExcel);
            }

        }

        if (errorDictExcels.size() > 0) {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ExcelUtils.export(byteArrayOutputStream, errorDictExcels, ImportDictExcel.class);
            String objName = String.format("????????????????????????_%s.xlsx", Date8Util.format(new Date(), "yyyy-MM-dd(HH-mm-ss)"));
            UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.ERROR_FACE_BUCKET, objName, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), ".xlsx");
            String objUrl = fileUploadUtil.getObjUrl(BucketNamePool.ERROR_FACE_BUCKET, uploadResultBean.getObjName());
            String richText = "<h1 style=\"font-size: 18px;\">??????????????????" + errorDictExcels.size() + "???????????????????????????????????????<a style=\"color: #3598db;\" href=\"" + objUrl + "\" download=\"" + objName + "\">????????????</a></h1>";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "????????????????????????", String.format("??????????????????%s?????????????????????????????????????????????", errorDictExcels.size()), richText, Lists.newArrayList(sysUser.getId()), AnnPriorityType.MIDDLE);
        } else {
            //????????????
            String msg = originalFilename + "????????????" + dictExcels.size() + "???";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, msg, msg, msg, Lists.newArrayList(sysUser.getId()), AnnPriorityType.MIDDLE);
        }

        return RetData.ok();
    }

    @AdviceResponseBody
    @PostMapping("/exportExcel")
    @AutoLog(value = "??????Excel", operateType = OperateTypeEnum.SELECT)
    public RetData<Void> exportExcel() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysDict> sysDicts = sysDictService.list();
        Map<Integer, String> dictMap = sysDicts.stream().collect(Collectors.toMap(SysDict::getId, SysDict::getDictCode));
        List<SysDictItem> sysDictItems = sysDictItemService.list();



        List<ExportDictExcel> exportDictExcels = new LinkedList<>();
        for (SysDict sysDict : sysDicts) {
            ExportDictExcel dictExcel = new ExportDictExcel();
            dictExcel.setType(DictType.DICTIONARY.getType());
            dictExcel.setDictName(sysDict.getDictName());
            dictExcel.setDictCode(sysDict.getDictCode());
            dictExcel.setDescription(sysDict.getDescription());
            exportDictExcels.add(dictExcel);
        }
        for (SysDictItem sysDictItem : sysDictItems) {
            ExportDictExcel dictExcel = new ExportDictExcel();
            dictExcel.setType(DictType.DATA.getType());
            dictExcel.setDictCode(dictMap.get(Convert.toInt(sysDictItem.getDictId())));
            dictExcel.setItemText(sysDictItem.getItemText());
            dictExcel.setItemValue(sysDictItem.getItemValue());
            dictExcel.setDescription(sysDictItem.getDescription());
            dictExcel.setSort(Convert.toStr(sysDictItem.getSort()));
            String status = DictStatus.findStatus(sysDictItem.getStatus());
            if (status != null) {
                dictExcel.setStatus(status);
            }
            exportDictExcels.add(dictExcel);
        }


        try {

            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ExcelUtils.export(byteArrayOutputStream, exportDictExcels, ExportDictExcel.class);
            //?????????
            String objName = String.format("????????????_%s", Date8Util.format(new Date(), "yyyy-MM-dd(HH-mm-ss)"));
            UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.ERROR_FACE_BUCKET, objName, new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), ".xlsx");
            String objUrl = fileUploadUtil.getObjUrl(BucketNamePool.ERROR_FACE_BUCKET, uploadResultBean.getObjName());
            String richText = "<h1 style=\"font-size: 18px;\">??????????????????" + exportDictExcels.size() + "???????????????????????????????????????<a style=\"color: #3598db;\" href=\"" + objUrl + "\" download=\"" + objName + "\">????????????</a></h1>";
            sysAnnouncementSenderService.sendAnnouncement(SysAnnConfigIdEnum.SYSANNCONFIGID_1, "????????????????????????", "????????????", richText, Lists.newArrayList(sysUser.getId()), AnnPriorityType.MIDDLE);

        } catch (Exception e) {
            throw new CommonException(FileErrorCode.FILE_ERROR_03);
        }

        return RetData.ok();
    }

    @AdviceResponseBody
    @GetMapping("/getMould")
    @AutoLog(value = "????????????", operateType = OperateTypeEnum.SELECT)
    public RetData<String> getMould() {
        return RetData.ok(fileUploadUtil.getObjUrl(BucketNamePool.COMMON_BUCKET, "??????????????????.xlsx", 10));
    }

}
