package com.rotanava.boot.system.module.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rotanava.boot.system.api.SysBackUpService;
import com.rotanava.boot.system.api.module.system.bean.SysBackupResult;
import com.rotanava.boot.system.api.module.system.bo.SysBackup;
import com.rotanava.boot.system.api.module.system.vo.SysBackupVO;
import com.rotanava.boot.system.module.dao.SysBackupMapper;
import com.rotanava.framework.common.constant.BucketNamePool;
import com.rotanava.framework.common.oss.FileUploadUtil;
import com.rotanava.framework.common.oss.model.UploadResultBean;
import com.rotanava.framework.global.GlobalClass;
import com.rotanava.framework.model.BaseVO;
import com.rotanava.framework.util.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 系统备份
 * @author: richenLi
 * @create: 2021-04-06 10:19
 **/
@Log4j2
@Service
public class SysBackUpServiceImpl extends ServiceImpl<SysBackupMapper, SysBackup> implements SysBackUpService {
    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private FileUploadUtil fileUploadUtil;




    @Override
    public void sysBackUp(Integer backupType, Integer serviceType) {
        try {
            SysBackup sysBackup = new SysBackup();
            sysBackup.setCreateTime(new Date());
            sysBackup.setBakCode(BaseUtil.getSnowflakeId().toString());
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式
            String backupName = String.format("%s_%s", getNameByBackupType(backupType), DateUtil.getNowTime(df));
            sysBackup.setBakName(backupName);
            sysBackup.setServiceType(serviceType);
            sysBackup.setBakType(backupType);
            SysBackupResult sysBackupResult;
            //执行备份
            if (sysBackUpType == serviceType) {
                sysBackupResult = mysqlBackup();
            } else if (logBackUpType == serviceType) {
                sysBackupResult = mysqlLogBackup();
            } else {
                return;
            }

            //获取备份的文件名及桶名
            sysBackup.setBucketName(sysBackupResult.getBucketName());
            sysBackup.setObjectName(sysBackupResult.getObjectName());
            sysBackup.setFileSize(sysBackupResult.getFileSize());
            sysBackup.setBakTime(new Date());
            //数据库保存
            save(sysBackup);
        } catch (Exception e) {
            log.error("备份日志异常", e);
        }
    }


    private String getNameByBackupType(Integer backUpType) {
        if (autoBackupType == backUpType) {
            return "AutoBackup";
        } else if (manualBackupType == backUpType) {
            return "ManualBackup";
        } else {
            return "UnknownBackUp";
        }
    }


    /**
     * @description :数据库备份
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2021/4/6 10:51
     */
    public SysBackupResult mysqlBackup() throws Exception {
        Process process;
        File file = null;

        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式
        String fileName = String.format("%s.sql", DateUtil.getNowTime(df));
        String filePath = String.format("/home/mysqlBackup/%s", fileName);
        try {
            String command = String.format("mysqldump -u%s -p%s -h rotanavamysql -P 3306  rn_base_sys  -r%s ", userName, password, filePath);
            log.info("command={}", command);
            process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                log.info("mysqlBackup[" + line + "]");
            }
            file = new File(filePath);
            log.info("file exits = {}", file.exists());
            FileInputStream fileInputStream = new FileInputStream(filePath);
            UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.BACKUP_BUCKENT, fileName, fileInputStream);
            SysBackupResult sysBackupResult = new SysBackupResult();
            sysBackupResult.setBucketName(uploadResultBean.getBucketName());
            sysBackupResult.setObjectName(uploadResultBean.getObjName());
            sysBackupResult.setFileSize(FileUtil.FormetFileSize(file.length()));
            return sysBackupResult;
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }


    /**
     * @description : 系统日志备份
     * @Author : richenLi
     * @version :  1.0
     * @Date : 2020/9/1 13:51
     */
    private SysBackupResult mysqlLogBackup() throws Exception {
        Process process;
        File file = null;

        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式
        String fileName = String.format("%s.sql", DateUtil.getNowTime(df));
        String filePath = String.format("/home/mysqlBackup/%s", fileName);
        try {
            String command = String.format("mysqldump -u%s -p%s -h rotanavamysql -P 3306  rn_base_sys sys_behavior_log user_login_info -r%s ", userName, password, filePath);
            log.info("command={}", command);
            process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                log.info("sysLogBackup[" + line + "]");
            }
            file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            UploadResultBean uploadResultBean = fileUploadUtil.uploadFile(BucketNamePool.BACKUP_BUCKENT, fileName, fileInputStream);
            SysBackupResult sysBackupResult = new SysBackupResult();
            sysBackupResult.setBucketName(uploadResultBean.getBucketName());
            sysBackupResult.setObjectName(uploadResultBean.getObjName());
            sysBackupResult.setFileSize(FileUtil.FormetFileSize(file.length()));
            return sysBackupResult;
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }


    @Override
    public BaseVO<SysBackupVO> getSysBackupList(Integer serviceType) {
        QueryWrapper<SysBackup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_type", serviceType);
        queryWrapper.orderByDesc("id");

        List<SysBackupVO> list = new ArrayList<>();
        IPage<SysBackup> page = page(PageUtils.startPage(), queryWrapper);

        for (SysBackup record : page.getRecords()) {
            SysBackupVO sysBackupVO = new SysBackupVO();
            BeanUtils.copyProperties(record, sysBackupVO);
            String fileUrl = fileUploadUtil.getObjUrl(record.getBucketName(), record.getObjectName(), 999);
            sysBackupVO.setFileUrl(fileUrl);
            list.add(sysBackupVO);
        }
        BaseVO<SysBackupVO> baseVO = BuildUtil.buildListVO(page.getTotal(), list);
        return baseVO;
    }


    @Override
    public void systemFactoryReset(){

        Process process = null;
        ProcessBuilder builder = new ProcessBuilder();
        try {
            String command = String.format("mysql -h rotanavamysql -u %s -P 3306 -p%s  -Drn_base_sys  </docker-entrypoint-initdb.d/c_rn_base_sys.sql ", userName, password);
            log.info("systemFactoryReset command={}", command);
            process = builder.command("bash", "-c", command).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                log.info("systemFactoryReset[" + line + "]");
            }
        }catch (Exception e){
            log.error("恢复出厂设置异常",e);
        }finally {
            if (process!=null){
                try {
                    process.destroy();
                }catch (Exception e){
                    log.error(e);
                }
            }
        }

    }
}
