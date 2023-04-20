package com.rotanava.boot.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rotanava.boot.system.api.module.system.bo.SysUser;
import com.rotanava.boot.system.api.module.system.dto.AddSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.CheckRetrievePwCaptchaDTO;
import com.rotanava.boot.system.api.module.system.dto.DingDingAuthDTO;
import com.rotanava.boot.system.api.module.system.dto.FirstPasswordDTO;
import com.rotanava.boot.system.api.module.system.dto.GetAccessInfoDTO;
import com.rotanava.boot.system.api.module.system.dto.GetAccessTokenDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListDeptUserAddDTO;
import com.rotanava.boot.system.api.module.system.dto.GetListSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.IntegrityVerificationLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.ListUserLoginInfoDTO;
import com.rotanava.boot.system.api.module.system.dto.PassWordLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.PhoneLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.RetrievePwDTO;
import com.rotanava.boot.system.api.module.system.dto.SecondaryVerificationLoginDTO;
import com.rotanava.boot.system.api.module.system.dto.UpdateSysUserDTO;
import com.rotanava.boot.system.api.module.system.dto.UserAccountNameDTO;
import com.rotanava.boot.system.api.module.system.dto.VerifyPhoneDTO;
import com.rotanava.boot.system.api.module.system.vo.AccessInfoVO;
import com.rotanava.boot.system.api.module.system.vo.AccessTokenVO;
import com.rotanava.boot.system.api.module.system.vo.AccountSafeSettingVO;
import com.rotanava.boot.system.api.module.system.vo.DdScanLoginParamVO;
import com.rotanava.boot.system.api.module.system.vo.GetBindInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserInfoVO;
import com.rotanava.boot.system.api.module.system.vo.SysUserItemVO;
import com.rotanava.boot.system.api.module.system.vo.UserLoginInfoVO;
import com.rotanava.boot.system.api.module.system.vo.UserLoginVO;
import com.rotanava.framework.model.BaseDTO;
import com.rotanava.framework.model.BaseVO;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.mail.Message;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @description: 系统用户类
 * @author: richenLi
 * @create: 2021-03-01 16:59
 **/
@Validated
public interface SysUserService extends IService<SysUser> {
    
    /**
     * 功能: 添加系统用户
     * 作者: zjt
     * 日期: 2021/3/4 18:21
     * 版本: 1.0
     * @return
     */
    Integer addSysUser(@Validated AddSysUserDTO addSysUserDTO, @Nullable byte[] photoBytes, int userId, boolean isEncryptPawword) throws Exception;
    
    
    /**
     * 功能: 修改用户
     * 作者: zjt
     * 日期: 2021/3/4 17:23
     * 版本: 1.0
     */
    void updateSysUser(@Validated UpdateSysUserDTO updateSysUserDTO, @Nullable byte[] photoBytes, int userId);
    
    /**
     * 功能: 获取用户信息
     * 作者: zjt
     * 日期: 2021/3/5 10:13
     * 版本: 1.0
     */
    @Nullable
    SysUserInfoVO getSysUserInfo(int sysUserId, @Nullable Integer deptId);
    
    /**
     * 功能: 获取系统用户分页列表
     * 作者: zjt
     * 日期: 2021/3/4 18:14
     * 版本: 1.0
     */
    IPage<SysUserItemVO> getListSysUser(GetListSysUserDTO listSysUserDTO);
    
    /**
     * 功能: 获取未添加的人员
     * 作者: zjt
     * 日期: 2021/5/19 15:43
     * 版本: 1.0
     */
    IPage<SysUserItemVO> getNotAddListSysUser(GetListDeptUserAddDTO getListSysUser);
    
    /**
     * 功能: 根据sysUserIPage 获取IPage<SysUserItemVO>
     * 作者: zjt
     * 日期: 2021/3/26 15:35
     * 版本: 1.0
     */
    IPage<SysUserItemVO> getUserItemVOIPage(IPage<SysUser> sysUserIPage, @Nullable Integer deptId);
    
    /**
     * 功能: 逻辑删除用户
     * 作者: zjt
     * 日期: 2021/3/4 17:57
     * 版本: 1.0
     */
    void deleteSysUser(List<Integer> sysUserIdList, int userId);
    
    /**
     * 功能: 彻底删除
     * 作者: zjt
     * 日期: 2021/3/24 14:17
     * 版本: 1.0
     */
    void thoroughDeleteSysUser(List<Integer> sysUserIdList, int userId);
    
    /**
     * 功能: 还原用户
     * 作者: zjt
     * 日期: 2021/3/24 14:18
     * 版本: 1.0
     */
    void restoreUser(List<Integer> sysUserIdList, int userId);
    
    /**
     * 功能: 冻结用户
     * 作者: zjt
     * 日期: 2021/3/4 17:51
     * 版本: 1.0
     */
    void freezeSysUser(List<Integer> sysUserIdList, int userId);
    
    /**
     * 功能: 解冻用户
     * 作者: zjt
     * 日期: 2021/3/4 17:51
     * 版本: 1.0
     */
    void unfreezeSysUser(List<Integer> sysUserIdList, int userId);
    
    /**
     * 功能: 用户密码重置
     * 作者: zjt
     * 日期: 2021/3/4 18:19
     * 版本: 1.0
     */
    void restPassword(int sysUserId, @NotBlank String password, int userId);
    
    /**
     * 密码登录
     *
     * @param passWordLoginDTO 密码登录dto
     * @param header           头
     * @param ipAddr           ip addr
     * @return {@link UserLoginVO }
     * @author WeiQiangMiao
     * @date 2021-03-06
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    UserLoginVO passWordLogin(@Validated PassWordLoginDTO passWordLoginDTO, String header, String ipAddr);
    
    /**
     * 注销
     *
     * @param token 令牌
     * @author WeiQiangMiao
     * @date 2021-03-09
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void logout(@NotBlank(message = "token不能为空") String token);
    
    /**
     * 功能: 根据用户id获取名字
     * 作者: zjt
     * 日期: 2021/3/11 16:09
     * 版本: 1.0
     *
     * @param userId
     */
    @Nullable
    String findUserNameById(Integer userId);
    
    /**
     * 在线记录
     *
     * @param token 令牌
     * @author WeiQiangMiao
     * @date 2021-03-09
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void onlineRecord(@NotBlank(message = "token不能为空") String token);
    
    /**
     * 在线记录列表
     *
     * @param listUserLoginInfoDTO 在线列表记录dto
     * @return {@link BaseVO<UserLoginInfoVO> }
     * @author WeiQiangMiao
     * @date 2021-03-09
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<UserLoginInfoVO> listOnlineRecord(@Validated ListUserLoginInfoDTO listUserLoginInfoDTO);
    
    /**
     * 下线
     *
     * @param loginName 用户帐户名
     * @author WeiQiangMiao
     * @date 2021-03-10
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void kickOffTheAssemblyLine(String loginName);
    
    
    /**
     * 功能: 解绑手机号
     * 作者: zjt
     * 日期: 2021/3/19 11:25
     * 版本: 1.0
     */
    void unbindPhone(int userId);
    
    /**
     * 功能: 解绑邮箱
     * 作者: zjt
     * 日期: 2021/3/19 11:25
     * 版本: 1.0
     */
    void unbindEmail(int userId);
    
    /**
     * 功能: 查找用户 不存在抛出异常
     * 作者: zjt
     * 日期: 2021/3/31 14:09
     * 版本: 1.0
     */
    SysUser findSysUserById(int userId);
    
    /**
     * 发送手机验证码登录
     *
     * @param verifyPhoneDTO 验证手机dto
     * @return
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendLoginPhoneVerificationCode(VerifyPhoneDTO verifyPhoneDTO);
    
    /**
     * 手机登录
     *
     * @param phoneLoginDTO 手机验证码dto
     * @return {@link UserLoginVO }
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    UserLoginVO phoneLogin(PhoneLoginDTO phoneLoginDTO, String header, String ipAddr);
    
    /**
     * 得到第二次验证
     *
     * @param loginDTO 账户安全设置dto
     * @return {@link BaseVO<AccountSafeSettingVO> }
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    BaseVO<AccountSafeSettingVO> getSecondVerification(UserAccountNameDTO loginDTO);
    
    /**
     * 发送二次手机验证码
     *
     * @param loginDTO 用户账号
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendSecondPhoneVerificationCode(@Valid UserAccountNameDTO loginDTO);
    
    /**
     * 发送二次电子邮件验证码
     *
     * @param loginDTO 用户账号
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendSecondaryEmailVerificationCode(@Valid UserAccountNameDTO loginDTO);
    
    /**
     * 手机完整性验证
     *
     * @param integrityVerificationLoginDTO 完整性验证登录dto
     * @param header                        头
     * @param ipAddr                        ip addr
     * @return {@link UserLoginVO }
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    UserLoginVO mobilePhoneIntegrityVerification(@Valid IntegrityVerificationLoginDTO integrityVerificationLoginDTO, String header, String ipAddr);
    
    /**
     * 移动电子邮件完整性验证
     *
     * @param integrityVerificationLoginDTO 完整性验证登录dto
     * @param header                        头
     * @param ipAddr                        ip addr
     * @return {@link UserLoginVO }
     * @author WeiQiangMiao
     * @date 2021-04-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    UserLoginVO mobileEmailIntegrityVerification(@Valid IntegrityVerificationLoginDTO integrityVerificationLoginDTO, String header, String ipAddr);
    
    /**
     * 第二个手机登录
     *
     * @param secondaryVerificationLoginDTO 二次验证登录dto
     * @param header                        头
     * @param ipAddr                        ip addr
     * @return {@link UserLoginVO }
     * @author WeiQiangMiao
     * @date 2021-04-13
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    UserLoginVO secondPhoneLogin(SecondaryVerificationLoginDTO secondaryVerificationLoginDTO, String header, String ipAddr);
    
    /**
     * 第二个电子邮件登录
     *
     * @param secondaryVerificationLoginDTO 二次验证登录dto
     * @param header                        头
     * @param ipAddr                        ip addr
     * @return {@link UserLoginVO }
     * @author WeiQiangMiao
     * @date 2021-04-13
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    UserLoginVO secondEmailLogin(SecondaryVerificationLoginDTO secondaryVerificationLoginDTO, String header, String ipAddr);
    
    /**
     * 更新第一个密码
     *
     * @param firstPasswordDTO 密码登录dto
     * @author WeiQiangMiao
     * @date 2021-05-12
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateFirstPassword(FirstPasswordDTO firstPasswordDTO);
    
    /**
     * 获取用户信息
     *
     * @return {@link UserLoginVO }
     * @author weiqiangmiao
     * @date 2021/07/07
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    UserLoginVO getUserInfo(String token);
    
    /**
     * 获取访问令牌
     *
     * @param getAccessTokenDTO 获得访问令牌dto
     * @return {@link AccessTokenVO }
     * @author weiqiangmiao
     * @date 2021/07/07
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    AccessTokenVO getAccessToken(GetAccessTokenDTO getAccessTokenDTO);
    
    /**
     * 获得信息
     *
     * @param getAccessInfoDTO 获得信息dto
     * @return {@link AccessInfoVO }
     * @author weiqiangmiao
     * @date 2021/07/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    AccessInfoVO getAccessInfo(GetAccessInfoDTO getAccessInfoDTO);
    
    
    BaseVO<SysUserItemVO> getUserListByQuery(BaseDTO baseDTO);
    
    /**
     * 得到绑定信息
     *
     * @param userAccountNameDTO 用户帐户名dto
     * @return
     * @author weiqiangmiao
     * @date 2021/12/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    GetBindInfoVO getBindInfo(UserAccountNameDTO userAccountNameDTO);
    
    /**
     * 手机号找回密码验证码
     *
     * @param captchaDTO token
     * @return
     * @author weiqiangmiao
     * @date 2021/12/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendRetrievePwPhoneCaptcha(CheckRetrievePwCaptchaDTO captchaDTO);
    
    /**
     * 邮箱找回密码验证码
     *
     * @param captchaDTO token
     * @return
     * @author weiqiangmiao
     * @date 2021/12/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void sendRetrievePwEmailCaptcha(CheckRetrievePwCaptchaDTO captchaDTO);
    
    
    /**
     * 校验手机验证码成功修改密码
     *
     * @param retrievePwDTO 找回密码dto
     * @return
     * @author weiqiangmiao
     * @date 2021/12/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updatePhonePasswordByCode(RetrievePwDTO retrievePwDTO);
    
    /**
     * 校验邮件验证码成功修改密码
     * getVerify
     *
     * @param retrievePwDTO 找回密码dto
     * @return
     * @author weiqiangmiao
     * @date 2021/12/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    void updateEmailPasswordByCode(RetrievePwDTO retrievePwDTO);
    
    /**
     * 得到验证
     *
     * @param userAccountNameDTO 用户
     * @return
     * @author weiqiangmiao
     * @date 2021/12/08
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    String getVerify(UserAccountNameDTO userAccountNameDTO);
    
    /**
     * 功能: 钉钉扫码auth
     * 作者: zjt
     * 日期: 2021/12/28 14:44
     * 版本: 1.0
     */
    UserLoginVO dingDingAuth(DingDingAuthDTO dingDingAuthDTO, String header, String ipAddr);
    
    /**
     * 功能: 获取钉钉扫码登陆参数
     * 作者: zjt
     * 日期: 2021/12/29 10:29
     * 版本: 1.0
     */
    DdScanLoginParamVO getDdScanLoginParam();
}
