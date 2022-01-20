package com.rotanava.framework.common.api;


import com.rotanava.framework.code.RetData;
import com.rotanava.framework.model.LoginUser;
import com.rotanava.framework.model.SysDictItem;
import com.rotanava.framework.model.bo.ManageSecurity;
import com.rotanava.framework.model.bo.SysSearchConfig;
import org.apache.shiro.authz.Permission;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

/**
 * 通用的api
 *
 * @author WeiQiangMiao
 * @date 2021-03-04
 */
public interface CommonApi {
    /**
     * 1查询用户角色信息
     *
     * @param userAccountName 用户名
     * @return {@link Set<String> }
     * @author WeiQiangMiao
     * @date 2021-03-04
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    Set<String> queryUserRoles(String userAccountName);


    /**
     * 2查询用户权限信息
     *
     * @param userAccountName 用户名
     * @author WeiQiangMiao
     * @date 2021-03-04
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    Set<Permission> queryUserAuths(String userAccountName);


    String getMappingValue(String key);

    /**
     * 5根据用户账号查询用户信息
     *
     * @param accountName 用户名
     * @return {@link LoginUser }
     * @author WeiQiangMiao
     * @date 2021-03-04
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    LoginUser getUserByAccountName(String accountName);

     /**
      * 功能: 根据用户id查询用户信息
      * 作者: zjt
      * 日期: 2021/10/11 14:34
      * 版本: 1.0
      */
    LoginUser getUserById(Integer userId);

    /**
     * 更新的帐户名称
     *
     * @param accountName 帐户名称
     * @return {@link LoginUser }
     * @author WeiQiangMiao
     * @date 2021-05-14
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void deleteByAccountName(String accountName);

    /**
     * 普通字典的翻译
     *
     * @param code
     * @param key
     * @return
     */
    String translateDict(String code, String key);


    /**
     * 字典表的 翻译
     *
     * @param table
     * @param text
     * @param code
     * @param key
     * @return
     */
    String translateDictFromTable(String table, String text, String code, String key);


    List<SysDictItem> getDictItem(String code);


    List<String> doSearchSql(Integer pageNum, Integer pageSize, String content);


    Long doSearchSqlCount(String sql);

    /**
     * 做重复检查
     *
     * @param tableName 表名
     * @param fieldName 字段名
     * @param fieldVal  场瓦尔
     * @return boolean
     * @author WeiQiangMiao
     * @date 2021-05-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    RetData<Boolean> doDuplicateCheck(@Valid @NotBlank(message = "表名不能为空") String tableName, @Valid @NotBlank(message = "字段名不能为空") String fieldName, String fieldVal, String tableId);


    /**
     * 是管理
     *
     * @param loginUserId id
     * @return boolean
     * @author WeiQiangMiao
     * @date 2021-05-10
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    boolean isAdmin(Integer loginUserId, String loginUserName);

    void deleteIsAdmin(Integer loginUserId, String loginUserName);

    /**
     * 得到管理安全签证官
     *
     * @return {@link ManageSecurity }
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    ManageSecurity getManageSecurity();

    void logout (String token);

    List<SysSearchConfig> getConfigByPageId(String searchCode);


}
