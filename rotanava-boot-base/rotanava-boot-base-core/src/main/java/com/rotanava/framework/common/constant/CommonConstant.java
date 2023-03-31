package com.rotanava.framework.common.constant;

import org.crazycake.shiro.RedisCacheManager;

public interface CommonConstant {

    public final static String SYSTEM_CLUSTER = "system:cluster:";

	/**
	 * 正常状态
	 */
	public static final Integer STATUS_NORMAL = 0;

	/**
	 * 禁用状态
	 */
	public static final Integer STATUS_DISABLE = -1;

    public static final String LOGINUSERIDS = "loginUserIds";

	/**
	 * 删除标志
	 */
	public static final Integer DEL_FLAG_0 = 0;

	/**
	 * 未删除
	 */
	public static final Integer DEL_FLAG_1 = 1;

	/**
	 * 系统日志类型： 登录
	 */
	public static final int LOG_TYPE_0 = 0;
	
	/**
	 * 系统日志类型： 操作
	 */
	public static final int LOG_TYPE_1 = 1;

    /**
     * 系统日志类型： 定时
     */
    public static final int LOG_TYPE_2 = 2;

	/**
	 * 操作日志类型： 查询
	 */
	public static final int OPERATE_TYPE_SELECT = 1;
	
	/**
	 * 操作日志类型： 添加
	 */
	public static final int OPERATE_TYPE_ADD = 2;
	
	/**
	 * 操作日志类型： 更新
	 */
	public static final int OPERATE_TYPE_UPDATE = 3;
	
	/**
	 * 操作日志类型： 删除
	 */
	public static final int OPERATE_TYPE_DELETE = 4;
	
	/**
	 * 操作日志类型： 倒入
	 */
	public static final int OPERATE_TYPE_5 = 5;
	
	/**
	 * 操作日志类型： 导出
	 */
	public static final int OPERATE_TYPE_6 = 6;
	
	
	/** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
    public static final Integer SC_OK_200 = 200;
    
    /**访问权限认证未通过 510*/
    public static final Integer SC_JEECG_NO_AUTHZ=510;


    /** 登录用户Shiro权限缓存KEY前缀 */
    public static String AUTHORIZATION_CACHE = "authorizationCache:user:id";

    public static String CACHE_KEY_PREFIX  = RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX + AUTHORIZATION_CACHE + ":";

    /** 登录用户Token令牌缓存KEY前缀 */
    public static final String PREFIX_USER_TOKEN  = "prefix:online:user:token:token_";

    /** 登录用户活跃缓存KEY前缀 */
    public static final String PREFIX_ACTIVE_USER_TOKEN = "prefix:active:user:token";

    /** 登录用户在线token令牌缓存KEY前缀 */
    public static final String PREFIX_ONLINE_USER_TOKEN = "prefix:online:user:token:user_account_name_";

    /** 登录用户在线token令牌缓存KEY前缀 */
    public static final String PREFIX_ONLINE_USER_PROHIBIT_TOKEN = "prefix:online:user:token:prohibit_user_account_name_";

    /** 登录用户在线信息KEY前缀 */
    public static final String PREFIX_ONLINE_USER_INFO = "prefix:online:user:info:user_account_name_";

    /** 登录用户锁定令牌缓存KEY前缀 */
    public static final String PREFIX_LOCKING_USER  = "prefix:locking:user:user_account_name_";
    /** 登录用户锁定次数令牌缓存KEY前缀 */
    public static final String PREFIX_LOCKING_USER_FREQUENCY  = "prefix:locking:frequency:user_account_name_";
    /** 登录用户手机KEY前缀 */
    public static final String PREFIX_CLEAR_USER_PHONE_FREQUENCY  = "prefix:clear:frequency:phone";
    /** 登录用户手机KEY前缀 */
    public static final String PREFIX_CLEAR_USER_EMAIL_FREQUENCY  = "prefix:clear:frequency:email";
    /** 登录用户管理员令牌缓存KEY前缀 */
    public static final String PREFIX_ADMIN_USER  = "prefix:admin:user_account_name_";
    /** 平台安全管理KEY前缀 */
    public static final String PREFIX_MANAGE_SECURITY  = "prefix:manage:security";
    /** 前缀mqtt集 */
    public static final String PREFIX_MQTT_SET  = "prefix:mqtt:set";

    /** 文件KEY前缀 */
    public static final String PREFIX_USER_FILE_TOKEN = "prefix:user:file:token_";
    /** 文件KE业务前缀 */
    public static final String PREFIX_USER_FILE_BUSINESS = "prefix:user:file:business";

    /**
     *  0：一级菜单
     */
    public static final Integer MENU_TYPE_0  = 0;
   /**
    *  1：子菜单 
    */
    public static final Integer MENU_TYPE_1  = 1;
    /**
     *  2：按钮权限
     */
    public static final Integer MENU_TYPE_2  = 2;
    
    /**通告对象类型（USER:指定用户，ALL:全体用户）*/
    public static final String MSG_TYPE_UESR  = "USER";
    public static final String MSG_TYPE_ALL  = "ALL";
    
    /**发布状态（0未发布，1已发布，2已撤销）*/
    public static final String NO_SEND  = "0";
    public static final String HAS_SEND  = "1";
    public static final String HAS_CANCLE  = "2";
    
    /**阅读状态（0未读，1已读）*/
    public static final String HAS_READ_FLAG  = "1";
    public static final String NO_READ_FLAG  = "0";
    
    /**优先级（L低，M中，H高）*/
    public static final String PRIORITY_L  = "L";
    public static final String PRIORITY_M  = "M";
    public static final String PRIORITY_H  = "H";
    
    /**
     * 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
     */
    public static final String SMS_TPL_TYPE_0  = "0";
    public static final String SMS_TPL_TYPE_1  = "1";
    public static final String SMS_TPL_TYPE_2  = "2";
    
    /**
     * 状态(0无效1有效)
     */
    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";
    
    /**
     * 同步工作流引擎1同步0不同步
     */
    public static final Integer ACT_SYNC_1 = 1;
    public static final Integer ACT_SYNC_0 = 0;

    /**
     * 消息类型1:通知公告2:系统消息
     */
    public static final String MSG_CATEGORY_1 = "1";
    public static final String MSG_CATEGORY_2 = "2";
    
    /**
     * 是否配置菜单的数据权限 1是0否
     */
    public static final Integer RULE_FLAG_0 = 0;
    public static final Integer RULE_FLAG_1 = 1;

    /**
     * 是否用户已被冻结 1正常(解冻) 2冻结
     */
    public static final Integer USER_UNFREEZE = 1;
    public static final Integer USER_FREEZE = 2;
    
    /**字典翻译文本后缀*/
    public static final String DICT_TEXT_SUFFIX = "_dictText";

    /**
     * 表单设计器主表类型
     */
    public static final Integer DESIGN_FORM_TYPE_MAIN = 1;

    /**
     * 表单设计器子表表类型
     */
    public static final Integer DESIGN_FORM_TYPE_SUB = 2;

    /**
     * 表单设计器URL授权通过
     */
    public static final Integer DESIGN_FORM_URL_STATUS_PASSED = 1;

    /**
     * 表单设计器URL授权未通过
     */
    public static final Integer DESIGN_FORM_URL_STATUS_NOT_PASSED = 2;

    /**
     * 表单设计器新增 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_ADD = "add";
    /**
     * 表单设计器修改 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_EDIT = "edit";
    /**
     * 表单设计器详情 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_DETAIL = "detail";
    /**
     * 表单设计器复用数据 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_REUSE = "reuse";
    /**
     * 表单设计器编辑 Flag （已弃用）
     */
    public static final String DESIGN_FORM_URL_TYPE_VIEW = "view";

    /**
     * online参数值设置（是：Y, 否：N）
     */
    public static final String ONLINE_PARAM_VAL_IS_TURE = "Y";
    public static final String ONLINE_PARAM_VAL_IS_FALSE = "N";

    /**
     * 文件上传类型（本地：local，Minio：minio，阿里云：alioss）
     */
    public static final String UPLOAD_TYPE_LOCAL = "local";
    public static final String UPLOAD_TYPE_MINIO = "minio";
    public static final String UPLOAD_TYPE_OSS = "oss";

    /**
     * 文档上传自定义桶名称
     */
    public static final String UPLOAD_CUSTOM_BUCKET = "eoafile";
    /**
     * 文档上传自定义路径
     */
    public static final String UPLOAD_CUSTOM_PATH = "eoafile";
    /**
     * 文件外链接有效天数
     */
    public static final Integer UPLOAD_EFFECTIVE_DAYS = 1;

    /**
     * 员工身份 （1:普通员工  2:上级）
     */
    public static final Integer USER_IDENTITY_1 = 1;
    public static final Integer USER_IDENTITY_2 = 2;

    /**
     * 部门:DEPARTMENT 或 个人:PERSONAL
     */
    public static final String DEPARTMENT = "department";
    public static final String PERSONAL = "personal";

    /** sys_user 表 username 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_USER_USERNAME = "uniq_sys_user_username";
    /** sys_user 表 work_no 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_USER_WORK_NO = "uniq_sys_user_work_no";
    /** sys_user 表 phone 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_USER_PHONE = "uniq_sys_user_phone";
    /** sys_user 表 email 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_USER_EMAIL = "uniq_sys_user_email";
    /** sys_quartz_job 表 job_class_name 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_JOB_CLASS_NAME = "uniq_job_class_name";
    /** sys_position 表 code 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_CODE = "uniq_code";
    /** sys_role 表 code 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_SYS_ROLE_CODE = "uniq_sys_role_role_code";
    /** sys_depart 表 code 唯一键索引 */
    public static final String SQL_INDEX_UNIQ_DEPART_ORG_CODE = "uniq_depart_org_code";
    /**
     * 在线聊天 是否为默认分组
     */
    public static final String IM_DEFAULT_GROUP = "1";
    /**
     * 在线聊天 图片文件保存路径
     */
    public static final String IM_UPLOAD_CUSTOM_PATH = "imfile";
    /**
     * 在线聊天 用户状态
     */
    public static final String IM_STATUS_ONLINE = "online";

    /**
     * 在线聊天 SOCKET消息类型
     */
    public static final String IM_SOCKET_TYPE = "chatMessage";

    /**
     * 在线聊天 是否开启默认添加好友 1是 0否
     */
    public static final String IM_DEFAULT_ADD_FRIEND = "1";

    /**
     * 在线聊天 用户好友缓存前缀
     */
    public static final String IM_PREFIX_USER_FRIEND_CACHE = "im_prefix_user_friend_";

    /**
     * 考勤补卡业务状态 （1：同意  2：不同意）
     */
    public static final String SIGN_PATCH_BIZ_STATUS_1 = "1";
    public static final String SIGN_PATCH_BIZ_STATUS_2 = "2";

    /**
     * 公文文档上传自定义路径
     */
    public static final String UPLOAD_CUSTOM_PATH_OFFICIAL = "officialdoc";
     /**
     * 公文文档下载自定义路径
     */
    public static final String DOWNLOAD_CUSTOM_PATH_OFFICIAL = "officaldown";

    /**
     * WPS存储值类别(1 code文号 2 text（WPS模板还是公文发文模板）)
     */
    public static final String WPS_TYPE_1="1";
    public static final String WPS_TYPE_2="2";


    public final static String X_ACCESS_TOKEN = "token";

    public final static String USER_AGENT = "User-Agent";

    /**
     * 多租户 请求头
     */
    public final static String TENANT_ID = "tenant_id";

    /**
     * 微服务读取配置文件属性 服务地址
     */
    public final static String CLOUD_SERVER_KEY = "spring.cloud.nacos.discovery.server-addr";

    /**
     * 第三方登录 验证密码/创建用户 都需要设置一个操作码 防止被恶意调用
     */
    public final static String THIRD_LOGIN_CODE = "third_login_code";

    /**
     * 手机模板id
     */
    public final static String PHONE_TEMPLATE_ID = "SMS_183805545";


    /**
     * 小程序的签名
     */
    public static final String APPLET_SMS_SIGN = "新航科技";

    /**
     * 密码强度
     */
    public static final String PASSWORD_STRENGTH = "password_strength";


    /**
     * 照片大小限制
     */
    public static final float PICTURE_UNIVERSITY_RESTRICTIONS = 2;

    /**
     * 发送验证码过期时间
     */
    public static final long VERIFICATION_CODE_EXPIRE_DATE = 60;

    /**
     * 验证码时间
     */
    public static final long VERIFICATION_CODE_DATE = 300;

    /**
     * AES解密key
     */
    public final static String AES_DECRYPTION_KEY = "COM.RN20@AIMAGEP";

    public final static String SYSTEM_BASIC_INFO = "/v1/systemMonitoring/getBasicInfo";
    public final static String SYSTEM_PERFORMANCE = "/v1/systemMonitoring/getPerformance";
    public final static String SYSTEM_DISK_USAGE = "/v1/systemMonitoring/getDiskUsage";

    public final static String SYSTEM_OAUTH_TOKEN = "/oauth/token";
    public final static String SYSTEM_API = "/kapis/monitoring.kubesphere.io/v1alpha3/cluster";

    /**
     * 验证码失效频率
     */
    public final static int VERIFICATION_CODE_INVALIDATION_FREQUENCY = 3;

//    public final static String SYSTEM_CLUSTER = "system:cluster:";

}
