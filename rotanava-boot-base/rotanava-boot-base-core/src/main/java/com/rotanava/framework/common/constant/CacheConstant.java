package com.rotanava.framework.common.constant;

/**
 * 缓存常量
 *
 * @Author richenLi
 * @Date 2021年3月3日
 */
public interface CacheConstant {

	/**
	 * 字典信息缓存
	 */
    public static final String SYS_DICT_CACHE = "sys:cache:dict";
	/**
	 * 表字典信息缓存
	 */
    public static final String SYS_DICT_TABLE_CACHE = "sys:cache:dictTable";
	public static final String SYS_DICT_TABLE_BY_KEYS_CACHE = SYS_DICT_TABLE_CACHE + "ByKeys";

	/**
	 * 数据权限配置缓存
	 */
    public static final String SYS_DATA_PERMISSIONS_CACHE = "sys:cache:permission:datarules";

	/**
	 * 缓存用户信息
	 */
	public static final String SYS_USERS_CACHE = "sys:cache:user";

	/**
	 * 全部部门信息缓存
	 */
	public static final String SYS_DEPARTS_CACHE = "sys:cache:depart:alldata";


	/**
	 * 全部部门ids缓存
	 */
	public static final String SYS_DEPART_IDS_CACHE = "sys:cache:depart:allids";


	/**
	 * 测试缓存key
	 */
	public static final String TEST_DEMO_CACHE = "test:system";

	/**
	 * 字典信息缓存
	 */
	public static final String SYS_DYNAMICDB_CACHE = "sys:cache:dbconnect:dynamic:";

	/**
	 * gateway路由缓存
	 */
	public static final String GATEWAY_ROUTES = "geteway_routes";


	/**
	 * gateway路由 reload key
	 */
	public static final String ROUTE_JVM_RELOAD_TOPIC = "gateway_jvm_route_reload_topic";

	/**
	 * TODO 冗余代码 待删除
	 *插件商城排行榜
	 */
	public static final String PLUGIN_MALL_RANKING = "pluginMall::rankingList";
	/**
	 * TODO 冗余代码 待删除
	 *插件商城排行榜
	 */
	public static final String PLUGIN_MALL_PAGE_LIST = "pluginMall::queryPageList";


	/**
	 * 邮件验证码更新缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_EMAIL_UPDATE = "verification:code:cache:email:update";


	/**
	 * 邮件验证码绑定缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_EMAIL_BIND = "verification:code:cache:email:bind";

	/**
	 * 邮件验证码解绑缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_EMAIL_UNBIND = "verification:code:cache:email:unbind";

	/**
	 * 验证邮件验证码更新成功缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_EMAIL_SUCCESS_UPDATE = "verification:Code:cache:email:success:update";


	/**
	 * 手机验证码更新缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_PHONE_UPDATE = "verification:code:cache:phone:update";

	/**
	 * 手机验证码绑定缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_PHONE_BIND = "verification:code:cache:phone:bind";

	/**
	 * 手机验证码解绑缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_PHONE_UNBIND = "verification:code:cache:phone:unbind";

	/**
	 * 验证手机验证码更新成功缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_PHONE_SUCCESS_UPDATE = "verification:code:cache:phone:update:success";

	/**
	 * 手机验证码登录
	 */
	public static final String VERIFICATION_CODE_CACHE_PHONE_LOGIN = "verification:code:cache:phone:login";

	/**
	 * 二次验证邮件验证码缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_SECONDARY_EMAIL = "verification:code:cache:secondary:email";

	/**
	 * 二次验证手机验证码缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_SECONDARY_PHONE = "verification:code:cache:secondary:phone";

	/**
	 * 验证码失效频率
	 */
	public static final String VERIFICATION_CODE_INVALIDATION_FREQUENCY = "%s:frequency:%s";


	/**
	 * 邮件验证码找回密码缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_EMAIL_RETRIEVE = "verification:code:cache:email:retrieve";

	/**
	 * 验证邮件验证码成功找回密码缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_EMAIL_SUCCESS_RETRIEVE = "verification:Code:cache:email:success:retrieve";

	/**
	 * 手机验证码找回密码缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_PHONE_RETRIEVE = "verification:code:cache:phone:retrieve";

	/**
	 * 验证手机验证码成功找回密码缓存
	 */
	public static final String VERIFICATION_CODE_CACHE_PHONE_SUCCESS_RETRIEVE = "verification:code:cache:phone:update:retrieve";

	/**
	 * 找回密码token验证码
	 */
	public static final String RETRIEVE_PASS_WORD_STRING = "retrieve:password:string";

	public static final String APP_ACCESS_INFO = "app:access:info:";

	public static final String APP_ACCESS_TOKEN = "app:access:token:";

	public static final String CLUSTER_GRANT_TYPE = "password";

	public static final String CLUSTER_USERNAME = "richenli";

	public static final String CLUSTER_PASSWORD = "RotaNova@2020";

	/**
	 * 人群逗留
	 */
	public static final String JET_LINK_TOKEN = "jet:link:token";

	/**
	 * 摔倒
	 */
	public static final String DETECTION_FALL  = "detection:fall";

	/**
	 * 离岗
	 */
	public static final String DETECTION_LEAVE_POST  = "detection:leave:post";

	/**
	 * 违停
	 */
	public static final String DETECTION_ILLEGAL_STOP  = "detection:illegal:stop";

	/**
	 * 违规作业
	 */
	public static final String DETECTION_ILLEGAL_WORK  = "detection:illegal:work";

	/**
	 * 人群聚集
	 */
	public static final String DETECTION_CROWD_GATHERED  = "detection:crowd:gathered";

	/**
	 * 消防
	 */
	public static final String DETECTION_FIRE_EXITS  = "detection:fire:exits";

	/**
	 * 检测疲劳
	 */
	public static final String DETECTION_FATIGUE  = "detection:fatigue";


	/**
	 * 区域入侵
	 */
	public static final String DETECTION_AREA_INVASION  = "detection:area:invasion";

	/**
	 * 离床
	 */
	public static final String DETECTION_LEAVE_LATHE  = "detection:leave:lathe";

	/**
	 * 值岗
	 */
	public static final String DETECTION_ON_DUTY  = "detection:on:duty";

	/**
	 * 人群逗留
	 */
	public static final String DETECTION_STAFF_STAY = "detection:staff:stay";

	/**
	 * 吸烟
	 */
	public static final String DETECTION_SMOKER = "detection:smoker";

	/**
	 * 睡岗
	 */
	public static final String SLEEP_POST = "detection:sleep:post";
}
