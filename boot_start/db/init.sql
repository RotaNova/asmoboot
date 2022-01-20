
-- 创建mysql库
DROP DATABASE IF EXISTS `rn_base_sys`;
create database `rn_base_sys` default character set utf8mb4 collate utf8mb4_general_ci;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE `rn_base_sys`;


-- ----------------------------
-- Table structure for account_safe_setting
-- ----------------------------
DROP TABLE IF EXISTS `account_safe_setting`;
CREATE TABLE `account_safe_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '安全设置信息ID',
  `sys_user_id` int(11) NULL DEFAULT NULL COMMENT '系统用户标识ID',
  `phone_safe_type` int(11) NOT NULL COMMENT '手机安全保护类型:0-无:1-验证号码完整性;2-验证验证码',
  `account_mfa` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MFA信息',
  `email_safe_type` int(11) NOT NULL COMMENT '邮件安全保护类型:0-无;1-验证邮件完整性;2-验证验证码',
  `account_time_out_mins` int(11) NULL DEFAULT NULL COMMENT '超时退出时间(分钟)',
  `account_time_out_on` int(1) NULL DEFAULT NULL COMMENT '超时退出是否开启:0-关闭;1-开启',
  `account_pass_out_mins` int(11) NULL DEFAULT NULL COMMENT '密码过期时间(分钟)',
  `account_pass_out_on` int(1) NULL DEFAULT NULL COMMENT '密码过期是否开启:0-关闭;1-开启',
  `account_pre_passwd` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次密码',
  `account_pre_passwd_time` datetime(0) NULL DEFAULT NULL COMMENT '上次密码修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_id`(`sys_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account_safe_setting
-- ----------------------------
INSERT INTO `account_safe_setting` VALUES (1, 1, 0, NULL, 0, NULL, NULL, NULL, NULL, '58f836bca9e709a6', '2021-12-10 10:31:12');

-- ----------------------------
-- Table structure for data_set
-- ----------------------------
DROP TABLE IF EXISTS `data_set`;
CREATE TABLE `data_set`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据集ID',
  `data_set_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据集名称',
  `data_set_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据集标识',
  `data_set_create_by` int(11) NOT NULL COMMENT '数据集创建人',
  `data_set_create_time` datetime(0) NOT NULL COMMENT '数据集创建时间',
  `data_set_update_by` int(11) NULL DEFAULT NULL COMMENT '数据集信息更新人',
  `data_set_update_time` datetime(0) NULL DEFAULT NULL COMMENT '数据集信息更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_set
-- ----------------------------

-- ----------------------------
-- Table structure for mq_transactional_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_transactional_message`;
CREATE TABLE `mq_transactional_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exchange` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交换机',
  `routingKey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路由建',
  `msg_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息提',
  `status` int(255) NOT NULL COMMENT '状态 0未发送 1发送',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `delay_time` bigint(11) NULL DEFAULT NULL COMMENT '延迟时间 ms',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mq_transactional_message
-- ----------------------------

-- ----------------------------
-- Table structure for network_setting
-- ----------------------------
DROP TABLE IF EXISTS `network_setting`;
CREATE TABLE `network_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统网络配置ID',
  `ntk_service_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统网络服务名称',
  `ntk_service_port` int(11) NULL DEFAULT NULL COMMENT '系统网络服务端口',
  `ntk_service_default_port` int(11) NOT NULL COMMENT '系统网络默认服务端口',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '网络服务更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '网络服务信息更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统网络参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of network_setting
-- ----------------------------

-- ----------------------------
-- Table structure for open_api
-- ----------------------------
DROP TABLE IF EXISTS `open_api`;
CREATE TABLE `open_api`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'api名称',
  `api_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'api路径',
  `request_method` int(2) NULL DEFAULT NULL COMMENT '请求方式 1GET 2POST',
  `result_type` int(2) NULL DEFAULT NULL COMMENT '返回类型 1json',
  `sql_text` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'sql配置',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `datasource_id` int(11) NULL DEFAULT NULL COMMENT '数据源id',
  `charge_person` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态 1正常 2锁定',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of open_api
-- ----------------------------

-- ----------------------------
-- Table structure for open_api_app
-- ----------------------------
DROP TABLE IF EXISTS `open_api_app`;
CREATE TABLE `open_api_app`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NULL DEFAULT NULL COMMENT '应用id',
  `api_id` int(11) NULL DEFAULT NULL COMMENT '接口id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `app_id`(`app_id`, `api_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of open_api_app
-- ----------------------------

-- ----------------------------
-- Table structure for open_api_param
-- ----------------------------
DROP TABLE IF EXISTS `open_api_param`;
CREATE TABLE `open_api_param`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数编码',
  `code_alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码别名',
  `is_fill` int(2) NOT NULL COMMENT '是否必填 1必填 2非必填',
  `param_type` int(2) NOT NULL COMMENT '请求类型 1文本 2数值 3数组',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `api_id` int(11) NOT NULL COMMENT '接口id',
  `type` int(2) NOT NULL COMMENT '类型 1请求参数 2返回参数',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of open_api_param
-- ----------------------------

-- ----------------------------
-- Table structure for open_app
-- ----------------------------
DROP TABLE IF EXISTS `open_app`;
CREATE TABLE `open_app`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名',
  `logo_bucket_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `logo_object_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `charge_person` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `contact_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `agent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'agentId',
  `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AppKey',
  `app_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Appsecret',
  `module_id` int(11) NULL DEFAULT NULL COMMENT '模块id',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of open_app
-- ----------------------------

-- ----------------------------
-- Table structure for open_datasource
-- ----------------------------
DROP TABLE IF EXISTS `open_datasource`;
CREATE TABLE `open_datasource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据源id',
  `datasource_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名',
  `db_type` int(11) NULL DEFAULT NULL COMMENT '数据库类型',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url',
  `driver` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驱动类',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `table_sql` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '查表sql',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of open_datasource
-- ----------------------------

-- ----------------------------
-- Table structure for sys_announcement
-- ----------------------------
DROP TABLE IF EXISTS `sys_announcement`;
CREATE TABLE `sys_announcement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统通告编号',
  `ann_title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统通告标题',
  `ann_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '系统通告内容',
  `ann_start_time` datetime(0) NOT NULL COMMENT '系统通告起始时间',
  `ann_end_time` datetime(0) NULL DEFAULT NULL COMMENT '系统通告结束时间',
  `ann_sender_id` int(11) NOT NULL COMMENT '系统通告发布人ID',
  `ann_priority` int(11) NOT NULL COMMENT '系统通告优先级',
  `ann_category` int(11) NOT NULL COMMENT '类型  0-通知消息;1-系统消息;2-告警消息',
  `ann_target` int(11) NOT NULL COMMENT '系统通告对象类型:0-全体用户;1-指定用户;2-指定部门',
  `ann_send_time` datetime(0) NULL DEFAULT NULL COMMENT '系统通告发布时间',
  `ann_send_status` int(11) NOT NULL COMMENT '系统通告发布状态:0-未发布;1-已发布;2-已撤销',
  `ann_cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '系统通告撤销时间',
  `ann_cancel_id` int(11) NULL DEFAULT NULL COMMENT '系统通告撤销人id',
  `ann_del_status` int(11) NULL DEFAULT NULL COMMENT '系统通告删除状态:0-已删除:1-未删除',
  `ann_bus_type` int(11) NULL DEFAULT NULL COMMENT '系统通告业务类型',
  `ann_bus_id` int(11) NULL DEFAULT NULL COMMENT '系统通告业务ID',
  `ann_open_type` int(11) NOT NULL COMMENT '系统通告打开方式:0-跳转;1-新开页',
  `ann_open_page` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统通告组件路由',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '系统通告创建人ID',
  `create_time` datetime(0) NOT NULL COMMENT '系统通告创建时间',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '系统通告更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '系统通告更新时间',
  `ann_user_ids` json NOT NULL COMMENT '系统通告通知用户名单',
  `ann_msg_abstract` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '系统通告摘要信息',
  `ann_dept_ids` json NULL COMMENT '系统通告通知部门名单',
  `ann_config_Id` int(11) NOT NULL COMMENT 'sys_announcement_config id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统通告发送清单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_announcement
-- ----------------------------
INSERT INTO `sys_announcement` VALUES (1, '上次登录信息', '<h1 style=\"font-size: 18px;\"><p>IP地址：192.168.0.113</p><p>时间：2022-01-12 17:13:43</p><p>地点：福建省</p></h1>', '2022-01-12 17:16:41', NULL, -1, 2, 0, 1, NULL, 0, NULL, NULL, 1, NULL, NULL, 0, NULL, -1, '2022-01-12 17:16:41', -1, '2022-01-12 17:16:41', '[1]', '上次登录信息', NULL, 1);
INSERT INTO `sys_announcement` VALUES (2, '上次登录信息', '<h1 style=\"font-size: 18px;\"><p>IP地址：192.168.0.42</p><p>时间：2022-01-12 17:16:41</p><p>地点：福建省</p></h1>', '2022-01-12 17:39:18', NULL, -1, 2, 0, 1, NULL, 0, NULL, NULL, 1, NULL, NULL, 0, NULL, -1, '2022-01-12 17:39:18', -1, '2022-01-12 17:39:18', '[1]', '上次登录信息', NULL, 1);
INSERT INTO `sys_announcement` VALUES (3, '上次登录信息', '<h1 style=\"font-size: 18px;\"><p>IP地址：192.168.0.42</p><p>时间：2022-01-12 17:39:18</p><p>地点：福建省</p></h1>', '2022-01-12 17:40:03', NULL, -1, 2, 0, 1, NULL, 0, NULL, NULL, 1, NULL, NULL, 0, NULL, -1, '2022-01-12 17:40:03', -1, '2022-01-12 17:40:03', '[1]', '上次登录信息', NULL, 1);
INSERT INTO `sys_announcement` VALUES (4, '上次登录信息', '<h1 style=\"font-size: 18px;\"><p>IP地址：192.168.0.42</p><p>时间：2022-01-12 17:40:03</p><p>地点：福建省</p></h1>', '2022-01-13 14:41:00', NULL, -1, 2, 0, 1, NULL, 0, NULL, NULL, 1, NULL, NULL, 0, NULL, -1, '2022-01-13 14:41:00', -1, '2022-01-13 14:41:00', '[1]', '上次登录信息', NULL, 1);

-- ----------------------------
-- Table structure for sys_announcement_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_announcement_config`;
CREATE TABLE `sys_announcement_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置名称',
  `type` int(255) NOT NULL COMMENT '类型  0-通知消息;1-系统消息;2-告警消息',
  `sys_notice` int(255) NOT NULL COMMENT '系统通知 0-不通知 1-通知',
  `sms_notice` int(255) NOT NULL COMMENT '短信通知 0-不通知 1-通知',
  `wechat_notice` int(255) NOT NULL COMMENT '微信通知 0-不通知 1-通知',
  `email_notice` int(255) NOT NULL COMMENT '邮件通知 0-不通知 1-通知',
  `phone_notice` int(255) NOT NULL COMMENT '电话通知 0-不通知 1-通知',
  `allow_close_notice` int(255) NOT NULL COMMENT '允许用户关闭消息通知  1-允许 0-不允许',
  `ann_target` int(11) NOT NULL COMMENT '系统通告对象类型:0-全体用户;1-指定用户;2-指定部门',
  `ann_user_ids` json NULL COMMENT '系统通告通知用户名单',
  `ann_dept_ids` json NULL COMMENT '系统通告通知部门名单',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `config_name`(`config_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_announcement_config
-- ----------------------------
INSERT INTO `sys_announcement_config` VALUES (1, '通知消息', 0, 1, 0, 0, 0, 0, 0, 0, '[]', NULL);
INSERT INTO `sys_announcement_config` VALUES (2, '系统消息', 1, 1, 0, 0, 0, 0, 0, 0, '[]', NULL);
INSERT INTO `sys_announcement_config` VALUES (3, '告警消息', 2, 1, 0, 0, 0, 0, 0, 0, '[]', NULL);

-- ----------------------------
-- Table structure for sys_api_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_api_permission`;
CREATE TABLE `sys_api_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '接口ID',
  `page_id` int(11) NULL DEFAULT NULL COMMENT '页面资源ID',
  `api_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口名称',
  `api_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口编码',
  `api_url` varchar(666) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口路径',
  `api_method` int(2) NOT NULL COMMENT '接口请求方式 1get 2post 3put 4delete',
  `create_by` int(11) NOT NULL COMMENT '接口创建人',
  `create_time` datetime(0) NOT NULL COMMENT '接口创建时间',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '接口更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '接口更新时间',
  `api_auth_type` int(11) NOT NULL COMMENT '接口鉴权方式:0-不鉴权;1-Token鉴权',
  `api_ability_type` int(11) NULL DEFAULT NULL COMMENT '接口能力类型',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `api_url`(`api_url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1159 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_api_permission
-- ----------------------------
INSERT INTO `sys_api_permission` VALUES (1, NULL, '密码登录', '/v1/manageUser/passWordLogin', '/v1/manageUser/passWordLogin', 2, 1, '2021-03-05 15:51:45', 1, '2021-03-20 15:59:40', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (2, NULL, '保存系统接口权限', 'sys_save_api+6', '/v1/managePermission/saveSysApiPermission', 2, 1, '2021-03-08 18:39:54', 1, '2021-05-17 11:45:20', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (3, NULL, '保存系统页面权限', '/v1/managePermission/saveSysPagePermission', '/v1/managePermission/saveSysPagePermission', 2, 1, '2021-03-08 18:39:54', 1, '2021-03-22 17:49:37', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (4, NULL, '删除系统页面权限', '/v1/managePermission/deleteSysPagePermission', '/v1/managePermission/deleteSysPagePermission', 2, 1, '2021-03-08 19:02:18', 1, '2021-03-18 09:57:17', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (5, NULL, '更新系统页面权限', '/v1/managePermission/updateSysPagePermission', '/v1/managePermission/updateSysPagePermission', 3, 1, '2021-03-08 19:43:48', 1, '2021-03-18 09:57:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (6, NULL, '获取系统页面权限', '/v1/managePermission/getSysPagePermission', '/v1/managePermission/getSysPagePermission', 1, 1, '2021-03-08 19:50:59', 1, '2021-03-18 09:57:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (7, NULL, '获取系统页面权限列表', '/v1/managePermission/listSysPagePermissions', '/v1/managePermission/listSysPagePermissions', 2, 1, '2021-03-08 19:52:47', 1, '2021-03-18 11:20:34', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (8, NULL, '删除系统接口权限', '/v1/managePermission/deleteSysApiPermission', '/v1/managePermission/deleteSysApiPermission', 2, 1, '2021-03-09 09:19:48', 1, '2021-03-18 10:00:17', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (9, NULL, '更新系统接口权限', '/v1/managePermission/updateSysApiPermission', '/v1/managePermission/updateSysApiPermission', 3, 1, '2021-03-09 09:20:49', 1, '2021-03-18 10:00:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (10, NULL, '获取系统接口权限', '/v1/managePermission/getSysApiPermission', '/v1/managePermission/getSysApiPermission', 1, 1, '2021-03-09 09:36:55', 1, '2021-03-18 10:00:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (11, NULL, '获取系统接口权限列表', '/v1/managePermission/listSysApiPermissions', '/v1/managePermission/listSysApiPermissions', 2, 1, '2021-03-09 09:37:48', 1, '2021-03-18 11:22:22', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (12, NULL, '注销', '/v1/manageUser/logout', '/v1/manageUser/logout', 2, 1, '2021-03-10 10:30:48', 1, '2021-03-10 10:30:48', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (13, NULL, '下线用户', '/v1/manageUser/kickOffTheAssemblyLine', '/v1/manageUser/kickOffTheAssemblyLine', 2, 1, '2021-03-10 10:31:31', 1, '2021-03-10 10:31:31', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (14, NULL, '获取在线记录列表', '/v1/manageUser/listOnlineRecord', '/v1/manageUser/listOnlineRecord', 2, 1, '2021-03-10 10:31:50', 1, '2021-03-18 10:35:46', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (15, NULL, '接口资源用：获取系统页面权限列表', '/v1/managePermission/listSysPagePermissionsUseApi', '/v1/managePermission/listSysPagePermissionsUseApi', 2, 1, '2021-03-08 19:52:47', 1, '2021-03-18 11:22:07', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (16, NULL, '获取系统基本信息', '/v1/systemMonitoring/getBasicInfo', '/v1/systemMonitoring/getBasicInfo', 1, 1, '2021-03-11 14:00:55', 1, '2021-03-18 10:35:38', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (17, NULL, '获取性能', '/v1/systemMonitoring/getPerformance', '/v1/systemMonitoring/getPerformance', 1, 1, '2021-03-11 14:51:36', 1, '2021-03-18 10:35:03', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (21, NULL, '保存系统反馈', '/v1/manageReport/saveSysReport', '/v1/manageReport/saveSysReport', 2, 1, '2021-03-12 18:22:16', 1, '2021-03-18 11:31:05', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (22, NULL, '撤销系统反馈', '/v1/manageReport/cancelSysReport', '/v1/manageReport/cancelSysReport', 3, 1, '2021-03-12 18:28:07', 1, '2021-03-18 11:31:32', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (23, NULL, '编辑系统反馈', '/v1/manageReport/updateSysReport', '/v1/manageReport/updateSysReport', 3, 1, '2021-03-12 18:28:27', 1, '2021-03-18 11:31:54', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (24, NULL, '确认系统反馈', '/v1/manageReport/confirmSysReport', '/v1/manageReport/confirmSysReport', 3, 1, '2021-03-12 18:29:10', 1, '2021-03-18 11:32:22', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (25, NULL, '获取个人系统报告', '/v1/manageReport/getPersonalSystemReport', '/v1/manageReport/getPersonalSystemReport', 1, 1, '2021-03-12 18:29:24', 1, '2021-03-18 10:21:37', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (26, NULL, '获取个人系统报告列表', '/v1/manageReport/listPersonalSystemReport', '/v1/manageReport/listPersonalSystemReport', 2, 1, '2021-03-12 18:29:49', 1, '2021-03-18 11:30:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (27, NULL, '获取管理系统报告', '/v1/manageReport/getAdminSystemReport', '/v1/manageReport/getAdminSystemReport', 1, 1, '2021-03-12 18:30:35', 1, '2021-03-18 10:06:14', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (28, NULL, '获取管理系统报告列表', '/v1/manageReport/listAdminSystemReport', '/v1/manageReport/listAdminSystemReport', 2, 1, '2021-03-12 18:30:57', 1, '2021-03-18 10:06:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (29, NULL, '驳回系统反馈', '/v1/manageReport/rejectSystemReport', '/v1/manageReport/rejectSystemReport', 3, 1, '2021-03-12 18:31:21', 1, '2021-03-18 11:33:05', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (30, NULL, '回复系统反馈', '/v1/manageReport/replySysReport', '/v1/manageReport/replySysReport', 3, 1, '2021-03-12 18:31:27', 1, '2021-03-18 11:33:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (41, NULL, '获取系统页面权限导航栏', '/v1/managePermission/listSysPagePermissionsNavigationBar', '/v1/managePermission/listSysPagePermissionsNavigationBar', 2, 1, '2021-03-17 15:16:25', 1, '2021-03-18 11:36:00', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (42, NULL, '更新数据字典', '/v1/sysDict/updateDict', '/v1/sysDict/updateDict', 2, 1, '2021-03-18 10:53:22', 1, '2021-03-18 10:54:42', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (43, NULL, '查询数据字典', '/v1/sysDict/getDictList', '/v1/sysDict/getDictList', 1, 1, '2021-03-18 10:55:08', 1, '2021-03-18 10:55:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (44, NULL, '添加数据字典', '/v1/sysDict/addDict', '/v1/sysDict/addDict', 2, 1, '2021-03-18 10:56:14', 1, '2021-03-18 10:56:14', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (45, NULL, '删除数据字典', '/v1/sysDict/deleteDict', '/v1/sysDict/deleteDict', 2, 1, '2021-03-18 10:56:40', 1, '2021-03-18 10:56:40', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (46, NULL, '查询数据字典项', '/v1/sysDict/getDictItemByDict', '/v1/sysDict/getDictItemByDict', 2, 1, '2021-03-18 11:11:01', 1, '2021-03-18 11:11:01', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (47, NULL, '添加数据字典项', '/v1/sysDict/addDictItem', '/v1/sysDict/addDictItem', 2, 1, '2021-03-18 11:11:26', 1, '2021-03-18 11:11:26', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (48, NULL, '更新数据字典项', '/v1/sysDict/updateDictItem', '/v1/sysDict/updateDictItem', 2, 1, '2021-03-18 11:11:55', 1, '2021-03-18 11:11:55', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (49, NULL, '删除数据字典项', '/v1/sysDict/deleteDictItem', '/v1/sysDict/deleteDictItem', 2, 1, '2021-03-18 15:08:14', 1, '2021-03-18 15:08:14', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (50, NULL, '查询高级搜索', '/v1/managePermission/getSearchConfigList', '/v1/managePermission/getSearchConfigList', 1, 1, '2021-03-18 15:53:47', 1, '2021-03-18 15:53:47', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (51, NULL, '修改高级搜索', '/v1/managePermission/updateSearchConfig', '/v1/managePermission/updateSearchConfig', 2, 1, '2021-03-18 15:59:13', 1, '2021-03-18 15:59:13', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (52, NULL, '新增高级搜索', '/v1/managePermission/addSearchConfig', '/v1/managePermission/addSearchConfig', 2, 1, '2021-03-18 15:59:30', 1, '2021-03-18 15:59:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (53, NULL, '删除高级搜索', '/v1/managePermission/deleteSearchConfig', '/v1/managePermission/deleteSearchConfig', 1, 1, '2021-03-18 16:00:07', 1, '2021-03-18 16:00:07', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (54, NULL, '获取登录日志列表', '/v1/sysLog/listUserLoginInfoLog', '/v1/sysLog/listUserLoginInfoLog', 2, 1, '2021-03-22 17:34:43', 1, '2021-03-30 18:09:19', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (55, NULL, '获取安全设置', '/v1/setAccount/getSecuritySettings', '/v1/setAccount/getSecuritySettings', 1, 1, '2021-03-22 17:43:22', 1, '2021-03-29 17:57:45', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (56, NULL, '获取基本信息', '/v1/setAccount/getUserBasicInfo', '/v1/setAccount/getUserBasicInfo', 1, 1, '2021-03-22 17:45:00', 1, '2021-07-14 09:06:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (57, NULL, '基本信息更新', '/v1/setAccount/updateUserBasicInfo', '/v1/setAccount/updateUserBasicInfo', 2, 1, '2021-03-22 17:45:14', 1, '2021-03-22 18:11:29', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (58, NULL, '登录二次认证', '/v1/setAccount/loginSecondaryAuthentication', '/v1/setAccount/loginSecondaryAuthentication', 3, 1, '2021-03-22 17:45:34', 1, '2021-03-22 18:11:34', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (59, NULL, '发送绑定邮箱验证码', '/v1/setAccount/sendBindEmailVerificationCode', '/v1/setAccount/sendBindEmailVerificationCode', 2, 1, '2021-03-22 17:46:10', 1, '2021-03-22 18:11:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (60, NULL, '发送绑定手机验证码', '/v1/setAccount/sendBindPhoneVerificationCode', '/v1/setAccount/sendBindPhoneVerificationCode', 2, 1, '2021-03-22 17:50:53', 1, '2021-03-22 18:11:55', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (61, NULL, '发送修改手机验证码', '/v1/setAccount/sendUpdatePhoneVerificationCode', '/v1/setAccount/sendUpdatePhoneVerificationCode', 2, 1, '2021-03-22 17:52:08', 1, '2021-03-22 18:12:00', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (62, NULL, '发送修改邮箱验证码', '/v1/setAccount/sendUpdateEmailVerificationCode', '/v1/setAccount/sendUpdateEmailVerificationCode', 2, 1, '2021-03-22 17:52:24', 1, '2021-03-22 18:12:04', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (63, NULL, '发送解绑手机验证码', '/v1/setAccount/sendUnbindPhoneVerificationCode', '/v1/setAccount/sendUnbindPhoneVerificationCode', 2, 1, '2021-03-22 17:52:51', 1, '2021-03-22 18:12:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (64, NULL, '发送解绑邮箱验证码', '/v1/setAccount/sendUnbindEmailVerificationCode', '/v1/setAccount/sendUnbindEmailVerificationCode', 2, 1, '2021-03-22 17:53:02', 1, '2021-03-22 18:12:12', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (65, NULL, '校验手机验证码', '/v1/setAccount/checkPhoneVerificationCode', '/v1/setAccount/checkPhoneVerificationCode', 2, 1, '2021-03-22 17:53:25', 1, '2021-03-22 18:12:25', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (66, NULL, '校验邮箱验证码', '/v1/setAccount/checkEmailVerificationCode', '/v1/setAccount/checkEmailVerificationCode', 2, 1, '2021-03-22 17:53:47', 1, '2021-03-22 18:12:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (67, NULL, '校验手机验证码成功修改密码', '/v1/setAccount/updatePhonePasswordByCode', '/v1/setAccount/updatePhonePasswordByCode', 3, 1, '2021-03-22 17:54:00', 1, '2021-03-22 18:12:34', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (68, NULL, '校验邮件验证码成功修改密码', '/v1/setAccount/updateEmailPasswordByCode', '/v1/setAccount/updateEmailPasswordByCode', 3, 1, '2021-03-22 17:54:29', 1, '2021-03-22 18:12:38', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (69, NULL, '绑定手机', '/v1/setAccount/bindPhone', '/v1/setAccount/bindPhone', 3, 1, '2021-03-22 17:54:50', 1, '2021-03-22 18:12:42', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (70, NULL, '解绑手机', '/v1/setAccount/unbindPhone', '/v1/setAccount/unbindPhone', 3, 1, '2021-03-22 17:55:06', 1, '2021-03-22 18:12:45', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (71, NULL, '绑定邮箱', '/v1/setAccount/bindMailbox', '/v1/setAccount/bindMailbox', 3, 1, '2021-03-22 17:55:21', 1, '2021-03-22 18:12:49', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (72, NULL, '解绑邮箱', '/v1/setAccount/unbindMailbox', '/v1/setAccount/unbindMailbox', 3, 1, '2021-03-22 17:55:39', 1, '2021-03-22 18:12:52', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (90, NULL, '添加系统用户', '/v1/sysUserManage/addSysUser', '/v1/sysUserManage/addSysUser', 2, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (91, NULL, '删除用户', '/v1/sysUserManage/deleteSysUser', '/v1/sysUserManage/deleteSysUser', 2, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (92, NULL, '冻结用户', '/v1/sysUserManage/freezeSysUser', '/v1/sysUserManage/freezeSysUser', 3, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (93, NULL, '获取回收站用户分页列表', '/v1/sysUserManage/getCycleBinListSysUser', '/v1/sysUserManage/getCycleBinListSysUser', 2, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (94, NULL, '获取部门列表', '/v1/sysUserManage/getDepartmentList', '/v1/sysUserManage/getDepartmentList', 1, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (95, NULL, '获取用户分页列表', '/v1/sysUserManage/getListSysUser', '/v1/sysUserManage/getListSysUser', 2, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (96, NULL, '获取全部用户角色', '/v1/sysUserManage/getSysRoleList', '/v1/sysUserManage/getSysRoleList', 1, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (97, NULL, '获取用户信息', '/v1/sysUserManage/getSysUserInfo', '/v1/sysUserManage/getSysUserInfo', 1, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (98, NULL, '用户密码重置', '/v1/sysUserManage/restPassword', '/v1/sysUserManage/restPassword', 3, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (99, NULL, '还原用户', '/v1/sysUserManage/restoreUser', '/v1/sysUserManage/restoreUser', 2, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (100, NULL, '彻底删除用户', '/v1/sysUserManage/thoroughDeleteSysUser', '/v1/sysUserManage/thoroughDeleteSysUser', 2, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (101, NULL, '解绑邮箱', '/v1/sysUserManage/unbindEmail', '/v1/sysUserManage/unbindEmail', 1, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (102, NULL, '解绑手机号', '/v1/sysUserManage/unbindPhone', '/v1/sysUserManage/unbindPhone', 1, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (103, NULL, '解冻用户', '/v1/sysUserManage/unfreezeSysUser', '/v1/sysUserManage/unfreezeSysUser', 3, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (104, NULL, '修改用户', '/v1/sysUserManage/updateSysUser', '/v1/sysUserManage/updateSysUser', 3, 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (105, NULL, '获取数据驾驶舱信息', '/v1/dataCockpit/getDataCockpit', '/v1/dataCockpit/getDataCockpit', 1, 1, '2021-04-06 15:10:34', 1, '2021-04-06 15:10:34', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (106, NULL, '添加部门角色', '/v1/sysRoleManage/addDeptRole', '/v1/sysRoleManage/addDeptRole', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (107, NULL, '新增系统角色', '/v1/sysRoleManage/addSysRole', '/v1/sysRoleManage/addSysRole', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (108, NULL, '部门角色批量删除', '/v1/sysRoleManage/deleteDeptRole', '/v1/sysRoleManage/deleteDeptRole', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (109, NULL, '删除部门角色授权的用户', '/v1/sysRoleManage/deleteDeptRoleAuthorizedUser', '/v1/sysRoleManage/deleteDeptRoleAuthorizedUser', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (110, NULL, '系统角色批量删除', '/v1/sysRoleManage/deleteSysRole', '/v1/sysRoleManage/deleteSysRole', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (111, NULL, '删除系统角色授权的用户', '/v1/sysRoleManage/deleteSysRoleAuthorizedUser', '/v1/sysRoleManage/deleteSysRoleAuthorizedUser', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (112, NULL, '部门角色授权用户', '/v1/sysRoleManage/deptRoleAuthorizedUser', '/v1/sysRoleManage/deptRoleAuthorizedUser', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (113, NULL, '部门角色获取部门列表', '/v1/sysRoleManage/getDeptRoleDepartmentList', '/v1/sysRoleManage/getDeptRoleDepartmentList', 1, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (114, NULL, '获取部门角色详情', '/v1/sysRoleManage/getDeptRoleInfo', '/v1/sysRoleManage/getDeptRoleInfo', 1, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (115, NULL, '获取部门角色接口集权限', '/v1/sysRoleManage/getDeptRoleSysApiPermission', '/v1/sysRoleManage/getDeptRoleSysApiPermission', 1, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (116, NULL, '获取部门角色菜单资源', '/v1/sysRoleManage/getDeptRoleSysPageTree', '/v1/sysRoleManage/getDeptRoleSysPageTree', 1, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (117, NULL, '分页获取部门用户授权的分页列表', '/v1/sysRoleManage/getListDeptUser', '/v1/sysRoleManage/getListDeptUser', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (118, NULL, '分页获取部门用户未授权分页列表', '/v1/sysRoleManage/getListDeptUserNoAuth', '/v1/sysRoleManage/getListDeptUserNoAuth', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (119, NULL, '分页获取系统授权用户列表', '/v1/sysRoleManage/getListSysUser', '/v1/sysRoleManage/getListSysUser', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (120, NULL, '分页获取系统授权用户列表', '/v1/sysRoleManage/getListSysUserNotAuth', '/v1/sysRoleManage/getListSysUserNotAuth', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (121, NULL, '系统角色获取部门列表', '/v1/sysRoleManage/getSysRoleDepartmentList', '/v1/sysRoleManage/getSysRoleDepartmentList', 1, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (122, NULL, '获取系统角色详情', '/v1/sysRoleManage/getSysRoleInfo', '/v1/sysRoleManage/getSysRoleInfo', 1, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (123, NULL, '获取系统角色接口集权限', '/v1/sysRoleManage/getSysRoleSysApiPermission', '/v1/sysRoleManage/getSysRoleSysApiPermission', 1, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (124, NULL, '获取系统角色菜单资源', '/v1/sysRoleManage/getSysRoleSysPageTree', '/v1/sysRoleManage/getSysRoleSysPageTree', 1, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (125, NULL, '获取部门角色列表', '/v1/sysRoleManage/listDeptRoleItem', '/v1/sysRoleManage/listDeptRoleItem', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (126, NULL, '查询角色列表', '/v1/sysRoleManage/listSysRoleItem', '/v1/sysRoleManage/listSysRoleItem', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (127, NULL, '保存部门角色系统权限配置', '/v1/sysRoleManage/saveDeptPermission', '/v1/sysRoleManage/saveDeptPermission', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (128, NULL, '保存系统角色系统权限配置', '/v1/sysRoleManage/saveSysPermission', '/v1/sysRoleManage/saveSysPermission', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (129, NULL, '系统角色授权用户', '/v1/sysRoleManage/sysRoleAuthorizedUser', '/v1/sysRoleManage/sysRoleAuthorizedUser', 2, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (130, NULL, '编辑部门角色', '/v1/sysRoleManage/updateDeptRole', '/v1/sysRoleManage/updateDeptRole', 3, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (131, NULL, '编辑系统角色', '/v1/sysRoleManage/updateSysRole', '/v1/sysRoleManage/updateSysRole', 3, 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (132, NULL, '删除通告', '/v1/sysAnnouncementWarn/deleteAnnouncement', '/v1/sysAnnouncementWarn/deleteAnnouncement', 2, 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (133, NULL, '获取通告详情', '/v1/sysAnnouncementWarn/getAnnouncementInfo', '/v1/sysAnnouncementWarn/getAnnouncementInfo', 1, 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (134, NULL, '分页获取通告', '/v1/sysAnnouncementWarn/getAnnouncementItemPage', '/v1/sysAnnouncementWarn/getAnnouncementItemPage', 2, 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (135, NULL, '部门角色获取部门列表', '/v1/sysAnnouncementWarn/getDeptDepartmentList', '/v1/sysAnnouncementWarn/getDeptDepartmentList', 1, 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (136, NULL, '获取用户分页列表', '/v1/sysAnnouncementWarn/getListSysUser', '/v1/sysAnnouncementWarn/getListSysUser', 2, 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (137, NULL, '发布通告', '/v1/sysAnnouncementWarn/publishAnnouncement', '/v1/sysAnnouncementWarn/publishAnnouncement', 2, 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (138, NULL, '撤销通告', '/v1/sysAnnouncementWarn/revokeAnnouncement', '/v1/sysAnnouncementWarn/revokeAnnouncement', 2, 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (139, NULL, '编辑通告', '/v1/sysAnnouncementWarn/updateAnnouncement', '/v1/sysAnnouncementWarn/updateAnnouncement', 2, 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (140, NULL, '删除通告', '/v1/sysAnnouncementSys/deleteAnnouncement', '/v1/sysAnnouncementSys/deleteAnnouncement', 2, 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (141, NULL, '获取通告详情', '/v1/sysAnnouncementSys/getAnnouncementInfo', '/v1/sysAnnouncementSys/getAnnouncementInfo', 1, 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (142, NULL, '分页获取通告', '/v1/sysAnnouncementSys/getAnnouncementItemPage', '/v1/sysAnnouncementSys/getAnnouncementItemPage', 2, 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (143, NULL, '部门角色获取部门列表', '/v1/sysAnnouncementSys/getDeptDepartmentList', '/v1/sysAnnouncementSys/getDeptDepartmentList', 1, 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (144, NULL, '获取用户分页列表', '/v1/sysAnnouncementSys/getListSysUser', '/v1/sysAnnouncementSys/getListSysUser', 2, 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (145, NULL, '发布通告', '/v1/sysAnnouncementSys/publishAnnouncement', '/v1/sysAnnouncementSys/publishAnnouncement', 2, 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (146, NULL, '撤销通告', '/v1/sysAnnouncementSys/revokeAnnouncement', '/v1/sysAnnouncementSys/revokeAnnouncement', 2, 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (147, NULL, '编辑通告', '/v1/sysAnnouncementSys/updateAnnouncement', '/v1/sysAnnouncementSys/updateAnnouncement', 2, 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (148, NULL, '新增通告', '/v1/sysAnnouncementNotic/addAnnouncement', '/v1/sysAnnouncementNotic/addAnnouncement', 2, 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (149, NULL, '删除通告', '/v1/sysAnnouncementNotic/deleteAnnouncement', '/v1/sysAnnouncementNotic/deleteAnnouncement', 2, 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (150, NULL, '获取通告详情', '/v1/sysAnnouncementNotic/getAnnouncementInfo', '/v1/sysAnnouncementNotic/getAnnouncementInfo', 1, 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (151, NULL, '分页获取通告', '/v1/sysAnnouncementNotic/getAnnouncementItemPage', '/v1/sysAnnouncementNotic/getAnnouncementItemPage', 2, 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (152, NULL, '部门角色获取部门列表', '/v1/sysAnnouncementNotic/getDeptDepartmentList', '/v1/sysAnnouncementNotic/getDeptDepartmentList', 1, 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (153, NULL, '获取用户分页列表', '/v1/sysAnnouncementNotic/getListSysUser', '/v1/sysAnnouncementNotic/getListSysUser', 2, 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (154, NULL, '发布通告', '/v1/sysAnnouncementNotic/publishAnnouncement', '/v1/sysAnnouncementNotic/publishAnnouncement', 2, 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (155, NULL, '撤销通告', '/v1/sysAnnouncementNotic/revokeAnnouncement', '/v1/sysAnnouncementNotic/revokeAnnouncement', 2, 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (156, NULL, '编辑通告', '/v1/sysAnnouncementNotic/updateAnnouncement', '/v1/sysAnnouncementNotic/updateAnnouncement', 2, 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (164, NULL, '获取消息通知配置', '/v1/sysAnnouncementConfig/getAnnouncementConfigList', '/v1/sysAnnouncementConfig/getAnnouncementConfigList', 2, 1, '2021-04-07 10:46:10', 1, '2021-04-07 10:46:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (165, NULL, '保存消息通知配置', '/v1/sysAnnouncementConfig/saveAnnouncementConfig', '/v1/sysAnnouncementConfig/saveAnnouncementConfig', 2, 1, '2021-04-07 10:46:10', 1, '2021-04-07 10:46:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (166, NULL, '分页获取消息窗口消息', '/v1/announcementWindows/getAnnouncementNoticItemPage', '/v1/announcementWindows/getAnnouncementNoticItemPage', 2, 1, '2021-04-07 10:52:54', 1, '2021-11-12 14:55:56', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (167, NULL, '添加部门', '/v1/departmentManage/addDepartment', '/v1/departmentManage/addDepartment', 2, 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (168, NULL, '添加部门角色', '/v1/departmentManage/addDeptRole', '/v1/departmentManage/addDeptRole', 2, 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (169, NULL, '添加系统用户', '/v1/departmentManage/addSysUser', '/v1/departmentManage/addSysUser', 2, 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (170, NULL, '批量删除部门', '/v1/departmentManage/deleteBatchDepartment', '/v1/departmentManage/deleteBatchDepartment', 2, 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (171, NULL, '部门角色批量删除', '/v1/departmentManage/deleteDeptRole', '/v1/departmentManage/deleteDeptRole', 2, 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (172, NULL, '解除角色', '/v1/departmentManage/deleteDeptRoleAuthorizedUser', '/v1/departmentManage/deleteDeptRoleAuthorizedUser', 2, 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (173, NULL, '删除用户', '/v1/departmentManage/deleteSysUser', '/v1/departmentManage/deleteSysUser', 2, 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (174, NULL, '部门成员添加已有成员', '/v1/departmentManage/departmentLinkUser', '/v1/departmentManage/departmentLinkUser', 2, 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (175, NULL, '部门取消关联用户', '/v1/departmentManage/departmentUnlinkUser', '/v1/departmentManage/departmentUnlinkUser', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (176, NULL, '部门角色授权用户', '/v1/departmentManage/deptRoleAuthorizedUser', '/v1/departmentManage/deptRoleAuthorizedUser', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (177, NULL, '冻结用户', '/v1/departmentManage/freezeSysUser', '/v1/departmentManage/freezeSysUser', 3, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (178, NULL, '获取部门列表', '/v1/departmentManage/getDepartmentList', '/v1/departmentManage/getDepartmentList', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (179, NULL, '获取部门成员用户分页列表', '/v1/departmentManage/getDepartmentMemberListSysUser', '/v1/departmentManage/getDepartmentMemberListSysUser', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (180, NULL, '获取部门成员添加已有用户分页列表', '/v1/departmentManage/getDepartmentMemberListSysUserAdd', '/v1/departmentManage/getDepartmentMemberListSysUserAdd', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (181, NULL, '部门角色获取部门列表', '/v1/departmentManage/getDeptDepartmentList', '/v1/departmentManage/getDeptDepartmentList', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (182, NULL, '获取部门详情', '/v1/departmentManage/getDeptInfo', '/v1/departmentManage/getDeptInfo', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (183, NULL, '获取部门角色详情', '/v1/departmentManage/getDeptRoleInfo', '/v1/departmentManage/getDeptRoleInfo', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (184, NULL, '根据部门id获取部门下的角色', '/v1/departmentManage/getDeptRoleList', '/v1/departmentManage/getDeptRoleList', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (185, NULL, '获取部门角色接口集权限', '/v1/departmentManage/getDeptRoleSysApiPermission', '/v1/departmentManage/getDeptRoleSysApiPermission', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (186, NULL, '获取部门角色菜单资源', '/v1/departmentManage/getDeptRoleSysPageTree', '/v1/departmentManage/getDeptRoleSysPageTree', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (187, NULL, '获取部门权限接口集权限', '/v1/departmentManage/getDeptSysApiPermission', '/v1/departmentManage/getDeptSysApiPermission', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (188, NULL, '获取部门权限菜单资源', '/v1/departmentManage/getDeptSysPageTree', '/v1/departmentManage/getDeptSysPageTree', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (189, NULL, '获取部门负责人列表', '/v1/departmentManage/getHeadDepartmentUserList', '/v1/departmentManage/getHeadDepartmentUserList', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (190, NULL, '分页获取部门用户分页列表', '/v1/departmentManage/getListDeptRoleUserList', '/v1/departmentManage/getListDeptRoleUserList', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (191, NULL, '分页获取部门角色-添加已有用户', '/v1/departmentManage/getListDeptRoleUserListAdd', '/v1/departmentManage/getListDeptRoleUserListAdd', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (192, NULL, '获取全部用户角色', '/v1/departmentManage/getSysRoleList', '/v1/departmentManage/getSysRoleList', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (193, NULL, '获取用户信息', '/v1/departmentManage/getSysUserInfo', '/v1/departmentManage/getSysUserInfo', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (194, NULL, '获取部门列表', '/v1/departmentManage/getUserDepartmentList', '/v1/departmentManage/getUserDepartmentList', 1, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (195, NULL, '获取部门角色列表', '/v1/departmentManage/listDeptRoleItem', '/v1/departmentManage/listDeptRoleItem', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (196, NULL, '用户密码重置', '/v1/departmentManage/restPassword', '/v1/departmentManage/restPassword', 3, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (197, NULL, '保存部门权限配置', '/v1/departmentManage/saveDeptPermission', '/v1/departmentManage/saveDeptPermission', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (198, NULL, '保存部门角色系统权限配置', '/v1/departmentManage/saveDeptRolePermission', '/v1/departmentManage/saveDeptRolePermission', 2, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (199, NULL, '解冻用户', '/v1/departmentManage/unfreezeSysUser', '/v1/departmentManage/unfreezeSysUser', 3, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (200, NULL, '修改部门', '/v1/departmentManage/updateDepartment', '/v1/departmentManage/updateDepartment', 3, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (201, NULL, '编辑部门角色', '/v1/departmentManage/updateDeptRole', '/v1/departmentManage/updateDeptRole', 3, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (202, NULL, '修改用户', '/v1/departmentManage/updateSysUser', '/v1/departmentManage/updateSysUser', 3, 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (203, NULL, '添加部门角色', '/v1/myDepartment/addDeptRole', '/v1/myDepartment/addDeptRole', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (204, NULL, '添加系统用户', '/v1/myDepartment/addSysUser', '/v1/myDepartment/addSysUser', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (205, NULL, '部门角色批量删除', '/v1/myDepartment/deleteDeptRole', '/v1/myDepartment/deleteDeptRole', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (206, NULL, '删除部门角色授权的用户', '/v1/myDepartment/deleteDeptRoleAuthorizedUser', '/v1/myDepartment/deleteDeptRoleAuthorizedUser', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (207, NULL, '删除用户', '/v1/myDepartment/deleteSysUser', '/v1/myDepartment/deleteSysUser', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (208, NULL, '部门成员添加已有成员', '/v1/myDepartment/departmentLinkUser', '/v1/myDepartment/departmentLinkUser', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (209, NULL, '部门取消关联用户', '/v1/myDepartment/departmentUnlinkUser', '/v1/myDepartment/departmentUnlinkUser', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (210, NULL, '部门角色授权用户', '/v1/myDepartment/deptRoleAuthorizedUser', '/v1/myDepartment/deptRoleAuthorizedUser', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (211, NULL, '冻结用户', '/v1/myDepartment/freezeSysUser', '/v1/myDepartment/freezeSysUser', 3, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (212, NULL, '获取部门列表', '/v1/myDepartment/getDepartmentList', '/v1/myDepartment/getDepartmentList', 1, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (213, NULL, '获取部门详情', '/v1/myDepartment/getDeptInfo', '/v1/myDepartment/getDeptInfo', 1, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (214, NULL, '部门角色获取部门列表', '/v1/myDepartment/getDeptRoleDepartmentList', '/v1/myDepartment/getDeptRoleDepartmentList', 1, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (215, NULL, '获取部门角色详情', '/v1/myDepartment/getDeptRoleInfo', '/v1/myDepartment/getDeptRoleInfo', 1, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (216, NULL, '获取部门角色接口集权限', '/v1/myDepartment/getDeptRoleSysApiPermission', '/v1/myDepartment/getDeptRoleSysApiPermission', 1, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (217, NULL, '获取部门角色菜单资源', '/v1/myDepartment/getDeptRoleSysPageTree', '/v1/myDepartment/getDeptRoleSysPageTree', 1, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (218, NULL, '部门角色添加已有用户', '/v1/myDepartment/getHaveListSysUser', '/v1/myDepartment/getHaveListSysUser', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (219, NULL, '分页获取部门用户分页列表', '/v1/myDepartment/getListDeptUser', '/v1/myDepartment/getListDeptUser', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (220, NULL, '获取已有的部门成员', '/v1/myDepartment/getListDeptUserAdd', '/v1/myDepartment/getListDeptUserAdd', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (221, NULL, '部门成员获取用户分页列表', '/v1/myDepartment/getListSysUser', '/v1/myDepartment/getListSysUser', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (222, NULL, '获取全部用户角色', '/v1/myDepartment/getSysRoleList', '/v1/myDepartment/getSysRoleList', 1, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (223, NULL, '获取用户信息', '/v1/myDepartment/getSysUserInfo', '/v1/myDepartment/getSysUserInfo', 1, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (224, NULL, '获取部门角色列表', '/v1/myDepartment/listDeptRoleItem', '/v1/myDepartment/listDeptRoleItem', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (225, NULL, '用户密码重置', '/v1/myDepartment/restPassword', '/v1/myDepartment/restPassword', 3, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (226, NULL, '保存部门角色系统权限配置', '/v1/myDepartment/saveDeptRolePermission', '/v1/myDepartment/saveDeptRolePermission', 2, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (227, NULL, '解冻用户', '/v1/myDepartment/unfreezeSysUser', '/v1/myDepartment/unfreezeSysUser', 3, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (228, NULL, '修改部门', '/v1/myDepartment/updateDepartment', '/v1/myDepartment/updateDepartment', 3, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (229, NULL, '编辑部门角色', '/v1/myDepartment/updateDeptRole', '/v1/myDepartment/updateDeptRole', 3, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (230, NULL, '修改用户', '/v1/myDepartment/updateSysUser', '/v1/myDepartment/updateSysUser', 3, 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (231, NULL, '获取使用时间统计', '/v1/dataCockpit/getUsageTimeStatistics', '/v1/dataCockpit/getUsageTimeStatistics', 2, 1, '2021-04-08 17:59:45', 1, '2021-04-08 17:59:45', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (232, NULL, '获取访问量统计', '/v1/dataCockpit/getVisitorVolumeStatistics', '/v1/dataCockpit/getVisitorVolumeStatistics', 2, 1, '2021-04-08 17:59:45', 1, '2021-04-08 17:59:45', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (233, NULL, '获取页面模块类型列表', '/v1/managePermission/listSysPageModuleType', '/v1/managePermission/listSysPageModuleType', 1, 1, '2021-04-16 11:36:15', 1, '2021-04-16 11:36:15', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (234, NULL, '获取ldap', '/v1/ldap/getLdap', '/v1/ldap/getLdap', 1, 1, '2021-04-23 16:31:13', 1, '2022-01-19 16:14:15', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (235, NULL, '更新ldap', '/v1/ldap/updateLdap', '/v1/ldap/updateLdap', 3, 1, '2021-04-23 16:31:13', 1, '2022-01-19 16:14:11', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (236, NULL, '保存系统角色系统权限配置 api级别', '/v1/myDepartment/saveDeptRolePermissionApi', '/v1/myDepartment/saveDeptRolePermissionApi', 2, 1, '2021-04-27 10:01:42', 1, '2021-04-27 10:01:42', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (237, NULL, '重复校验接口', 'ZhongFuXiaoYanJieKou', '/v1/common/check', 2, 1, '2021-05-08 17:25:11', 1, '2021-05-08 17:27:39', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (238, NULL, '保存部门权限配置 api', '/v1/departmentManage/saveDeptPermissionApi', '/v1/departmentManage/saveDeptPermissionApi', 2, 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (239, NULL, '保存部门角色系统权限配置 api', '/v1/departmentManage/saveDeptRolePermissionApi', '/v1/departmentManage/saveDeptRolePermissionApi', 2, 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (240, NULL, '解绑邮箱', '/v1/departmentManage/unbindEmail', '/v1/departmentManage/unbindEmail', 1, 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (241, NULL, '解绑手机号', '/v1/departmentManage/unbindPhone', '/v1/departmentManage/unbindPhone', 1, 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (242, NULL, '保存部门角色系统权限配置 api级别', '/v1/sysRoleManage/saveDeptPermissionApi', '/v1/sysRoleManage/saveDeptPermissionApi', 2, 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (243, NULL, '保存系统角色系统权限配置 api级别', '/v1/sysRoleManage/saveSysPermissionApi', '/v1/sysRoleManage/saveSysPermissionApi', 2, 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (244, NULL, '分页获取api管理列表', '/v1/appApiRegistered/getOpenApiPageList', '/v1/appApiRegistered/getOpenApiPageList', 2, 1, '2021-05-11 10:24:34', 1, '2021-05-11 10:24:34', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (245, NULL, '获取sql配置', '/v1/appApiRegistered/getSqlConfiguration', '/v1/appApiRegistered/getSqlConfiguration', 1, 1, '2021-05-11 10:24:34', 1, '2021-05-11 10:24:34', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (246, NULL, '锁定api', '/v1/appApiRegistered/lockOpenAppApi', '/v1/appApiRegistered/lockOpenAppApi', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (247, NULL, '注册api', '/v1/appApiRegistered/registeredOpenAppApi', '/v1/appApiRegistered/registeredOpenAppApi', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (248, NULL, '移除api', '/v1/appApiRegistered/removeOpenAppApi', '/v1/appApiRegistered/removeOpenAppApi', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (249, NULL, '保存sql配置', '/v1/appApiRegistered/saveSqlConfiguration', '/v1/appApiRegistered/saveSqlConfiguration', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (250, NULL, '解锁api', '/v1/appApiRegistered/unLockOpenAppApi', '/v1/appApiRegistered/unLockOpenAppApi', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (251, NULL, '修改api', '/v1/appApiRegistered/updateOpenAppApi', '/v1/appApiRegistered/updateOpenAppApi', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (252, NULL, '添加已有接口权限', '/v1/appRegistered/apiBindApp', '/v1/appRegistered/apiBindApp', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (253, NULL, '移除接口权限', '/v1/appRegistered/apiUnBindApp', '/v1/appRegistered/apiUnBindApp', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (254, NULL, '创建应用', '/v1/appRegistered/createOpenApp', '/v1/appRegistered/createOpenApp', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (255, NULL, '删除应用', '/v1/appRegistered/deleteOpenApp', '/v1/appRegistered/deleteOpenApp', 1, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (256, NULL, '分页获取权限接口列表', '/v1/appRegistered/getOpenApiPageList', '/v1/appRegistered/getOpenApiPageList', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (257, NULL, '获取应用详情', '/v1/appRegistered/getOpenApp', '/v1/appRegistered/getOpenApp', 1, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (258, NULL, '分页获取应用列表', '/v1/appRegistered/getOpenAppPageList', '/v1/appRegistered/getOpenAppPageList', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (259, NULL, '更新应用', '/v1/appRegistered/updateOpenApp', '/v1/appRegistered/updateOpenApp', 2, 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (260, NULL, '网关数据获取', '/v1/networkConfig/getGatewayInfo', '/v1/networkConfig/getGatewayInfo', 1, 1, '2021-05-11 14:53:48', 1, '2021-05-11 14:53:48', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (261, NULL, '修改网关信息', '/v1/networkConfig/updateGatewayConfig', '/v1/networkConfig/updateGatewayConfig', 3, 1, '2021-05-11 14:53:48', 1, '2021-05-11 14:53:48', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (263, NULL, '校验更新手机验证码', 'XiaoYanGengXinShouJiYanZhengMa', '/v1/setAccount/checkUpdatePhoneVerificationCode', 2, 1, '2021-05-14 16:07:36', 1, '2022-01-19 10:53:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (264, NULL, '校验更新邮箱验证码', 'XiaoYanGengXinYouXiangYanZheng', '/v1/setAccount/checkUpdateEmailVerificationCode', 2, 1, '2021-05-14 16:07:52', 1, '2022-01-19 10:53:03', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (265, NULL, '通过旧密码更新密码', 'TongGuoJiuMiMaGengXinMiMa', '/v1/setAccount/updatePasswordByOldPassword', 3, 1, '2021-05-14 16:21:51', 1, '2022-01-19 10:52:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (267, NULL, '获取平台配置信息', '/v1/platformSetting/getPlatfor', '/v1/platformSetting/getPlatformSetting', 1, 1, '2021-05-17 14:52:35', 1, '2021-05-17 14:52:35', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (268, NULL, '授权用户部门角色', '/v1/departmentManage/authUserDeptRole', '/v1/departmentManage/authUserDeptRole', 2, 1, '2021-05-18 13:49:27', 1, '2021-05-18 13:49:27', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (269, NULL, '获取全部搜索条件', 'HuoQuQuanBuSouSuoTiaoJian', '/v1/common/getAllSearchConfig', 1, 52, '2021-05-19 09:16:57', 52, '2021-05-19 09:17:11', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (270, NULL, '获取系统日志列表', 'HuoQuXiTongRiZhiLieBiao', '/v1/sysLog/getBehaviorLogList', 2, 52, '2021-05-19 09:18:52', 1, '2021-05-19 13:56:41', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (271, NULL, '获取数据字典回收站', 'HuoQuShuJuZiDianHuiShouZhan', '/v1/sysDict/getRecycleDict', 1, 1, '2021-05-19 14:01:12', 1, '2021-05-19 14:03:01', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (272, NULL, '还原数据字典', 'HuaiYuanShuJuZiDian', '/v1/sysDict/restoreDict', 1, 1, '2021-05-19 14:02:30', 1, '2021-05-19 14:02:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (273, NULL, '刷新字典缓存', 'ShuaXinZiDianHuanCun', '/v1/sysDict/refreshDictCache', 1, 1, '2021-05-19 14:02:54', 1, '2021-05-19 14:02:54', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (274, NULL, '修改平台配置', 'XiuGaiPingTaiPeiZhi', '/v1/platformSetting/savePlatformSetting', 2, 1, '2021-05-19 14:04:17', 1, '2021-05-19 14:04:17', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (275, NULL, '重置平台配置', 'ZhongZhiPingTaiPeiZhi', '/v1/platformSetting/resetPlatformSetting', 2, 1, '2021-05-19 14:04:42', 1, '2021-05-19 14:04:42', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (281, NULL, '导出部门', '/v1/departmentManage/batchExportSysDepartment', '/v1/departmentManage/batchExportSysDepartment', 2, 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (282, NULL, '导入部门', '/v1/departmentManage/batchImportSysDepartment', '/v1/departmentManage/batchImportSysDepartment', 2, 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (283, NULL, '获取部门导入模板链接', '/v1/departmentManage/getImportExcelFile', '/v1/departmentManage/getImportExcelFile', 1, 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (284, NULL, '导出用户', '/v1/sysUserManage/batchExportSysUser', '/v1/sysUserManage/batchExportSysUser', 2, 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (285, NULL, '导入用户', '/v1/sysUserManage/batchImportSysUser', '/v1/sysUserManage/batchImportSysUser', 2, 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (286, NULL, '获取用户导入模板链接', '/v1/sysUserManage/getImportExcelFile', '/v1/sysUserManage/getImportExcelFile', 1, 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (330, NULL, '获取项目概述列表', '/v1/projectManage/listProjectOverview', '/v1/projectManage/listProjectOverview', 2, 1, '2021-06-30 16:03:30', 1, '2021-06-30 16:03:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (331, NULL, '添加项目关联设备分组', '/v1/project/addDeviceGroup', '/v1/project/addDeviceGroup', 2, 1, '2021-07-01 11:54:26', 1, '2021-07-01 11:54:26', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (332, NULL, '添加项目', '/v1/project/addSysProject', '/v1/project/addSysProject', 2, 1, '2021-07-01 11:54:26', 1, '2021-07-01 11:54:26', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (333, NULL, '添加设备关联项目', '/v1/project/changeProjectDevice', '/v1/project/changeProjectDevice', 2, 1, '2021-07-01 11:54:26', 1, '2021-07-01 11:54:26', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (343, NULL, '删除定时重启', '/v1/sysSetting/deleteRestartRegularly', '/v1/sysSetting/deleteRestartRegularly', 4, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (344, NULL, '获取日志备份配置信息', '/v1/sysSetting/getLogBackupConfig', '/v1/sysSetting/getLogBackupConfig', 1, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (345, NULL, '获取日志备份列表', '/v1/sysSetting/getLogBackupList', '/v1/sysSetting/getLogBackupList', 1, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (346, NULL, '获取系统备份列表', '/v1/sysSetting/getSysBackupList', '/v1/sysSetting/getSysBackupList', 1, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (347, NULL, '获取系统备份配置信息', '/v1/sysSetting/getSystemBackupsConfig', '/v1/sysSetting/getSystemBackupsConfig', 2, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (348, NULL, '获取定时重启列表', '/v1/sysSetting/listRestartRegularly', '/v1/sysSetting/listRestartRegularly', 2, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (349, NULL, '日志备份立即备份', '/v1/sysSetting/logBackupNow', '/v1/sysSetting/logBackupNow', 2, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (350, NULL, '保存日志备份配置信息', '/v1/sysSetting/saveLogBackupConfig', '/v1/sysSetting/saveLogBackupConfig', 2, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (351, NULL, '保存ntp服务器信息', '/v1/sysSetting/saveNtpInfo', '/v1/sysSetting/saveNtpInfo', 2, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (352, NULL, '保存定时重启', '/v1/sysSetting/saveRestartRegularly', '/v1/sysSetting/saveRestartRegularly', 2, 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (353, NULL, '删除系统备份', '/v1/sysSetting/deleteBackup', '/v1/sysSetting/deleteBackup', 2, 1, '2021-07-01 14:21:08', 1, '2021-07-01 14:21:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (354, NULL, '恢复备份', '/v1/sysSetting/backupRecovery', '/v1/sysSetting/backupRecovery', 2, 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (355, NULL, '获取NTF信息', '/v1/sysSetting/getNtpInfo', '/v1/sysSetting/getNtpInfo', 1, 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (356, NULL, '系统备份立即备份', '/v1/sysSetting/systemBackupNow', '/v1/sysSetting/systemBackupNow', 2, 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (357, NULL, '系统备份配置信息保存', '/v1/sysSetting/systemBackupsConfig', '/v1/sysSetting/systemBackupsConfig', 2, 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (358, NULL, '恢复系统出厂设置', '/v1/sysSetting/systemFactoryReset', '/v1/sysSetting/systemFactoryReset', 2, 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (359, NULL, '系统重启', '/v1/sysSetting/systemReboot', '/v1/sysSetting/systemReboot', 2, 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (360, NULL, '测试NTP服务器', '/v1/sysSetting/testNtpServer', '/v1/sysSetting/testNtpServer', 1, 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (361, NULL, '读该条消息', 'DuGaiTiaoXiaoXi', '/v1/announcementWindows/readAnnouncement', 2, 1, '2021-07-02 18:38:14', 67, '2021-11-24 14:31:48', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (362, NULL, '添加属性', '/v1/manageForm/addAttributes', '/v1/manageForm/addAttributes', 2, 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (363, NULL, '添加表格', '/v1/manageForm/addForm', '/v1/manageForm/addForm', 2, 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (364, NULL, '删除表格', '/v1/manageForm/deleteForm', '/v1/manageForm/deleteForm', 2, 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (365, NULL, '获取属性', '/v1/manageForm/getAttributes', '/v1/manageForm/getAttributes', 1, 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (366, NULL, '获取表格列表', '/v1/manageForm/listForms', '/v1/manageForm/listForms', 2, 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (367, NULL, '更新表格', '/v1/manageForm/updateForm', '/v1/manageForm/updateForm', 2, 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (368, NULL, '获取访问令牌', 'HuoQuFangWenLingPai', '/v1/manageUser/getAccessInfoDTO', 2, 1, '2021-07-08 10:29:03', 1, '2021-07-08 10:29:03', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (369, NULL, '获取访问信息', 'HuoQuFangWenXinXi', '/v1/manageUser/getAccessInfo', 2, 1, '2021-07-08 10:29:59', 1, '2021-07-08 10:30:48', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (371, NULL, '查找项目动态信息', '/v1/project/findProjectDynamicInfoByType', '/v1/project/findProjectDynamicInfoByType', 2, 1, '2021-07-08 11:28:53', 1, '2021-07-08 11:28:53', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (372, NULL, '欢迎页面', 'HuanYingYeMian', '/v1/common/welcomePage', 1, 1, '2021-07-13 17:02:23', 1, '2021-07-13 17:02:23', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (377, NULL, '获取用户信息', 'HuoQuYongHuXinXi', '/v1/manageUser/getUserInfo', 1, 1, '2021-07-15 10:55:22', 1, '2021-07-15 10:55:22', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (451, NULL, '是否启用该应用', '/v1/appRegistered/enableApp', '/v1/appRegistered/enableApp', 2, 1, '2021-07-19 15:46:18', 1, '2021-07-19 15:46:18', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (725, NULL, '获取自动生成Json', 'HuoQuZiDongShengChengJson', '/v1/managePermission/getPageJson', 1, 1, '2021-09-24 15:03:19', 1, '2021-09-24 15:16:08', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (838, NULL, '批量导出Excel', '/v1/managePermission/batchExportExcel', '/v1/managePermission/batchExportExcel', 2, 1, '2021-10-19 11:31:08', 1, '2021-10-19 11:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (839, NULL, '导出Excel', '/v1/managePermission/exportExcel', '/v1/managePermission/exportExcel', 2, 1, '2021-10-19 11:31:08', 1, '2021-10-19 11:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (840, NULL, '获取模板', '/v1/managePermission/getMould', '/v1/managePermission/getMould', 1, 1, '2021-10-19 11:31:08', 1, '2021-10-19 11:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (841, NULL, '导入Excel', '/v1/managePermission/importExcel', '/v1/managePermission/importExcel', 2, 1, '2021-10-19 11:31:08', 1, '2021-10-19 11:31:08', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (937, NULL, '获取用户搜索配置', '/HuoQuYongHuSouSuoPeiZhi', '/v1/common/getTableConfigByCode', 2, 1, '2021-11-12 14:03:57', 1, '2021-11-12 14:03:57', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (939, NULL, '导出Excel', '/v1/sysDict/exportExcel', '/v1/sysDict/exportExcel', 2, 1, '2021-11-12 14:19:16', 1, '2021-11-12 14:19:16', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (940, NULL, '获取模板', '/v1/sysDict/getMould', '/v1/sysDict/getMould', 1, 1, '2021-11-12 14:19:16', 1, '2021-11-12 14:19:16', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (941, NULL, '导入Excel', '/v1/sysDict/importExcel', '/v1/sysDict/importExcel', 2, 1, '2021-11-12 14:19:16', 1, '2021-11-12 14:19:16', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (943, NULL, '获取消息详情', 'HuoQuXiaoXiXiangQing', '/v1/announcementWindows/getUserAnnouncementInfo', 1, 1, '2021-11-15 14:10:23', 67, '2021-11-24 14:32:08', 0, NULL);
INSERT INTO `sys_api_permission` VALUES (944, NULL, '获取通告详情', '/v1/myMessage/getUserAnnouncementInfo', '/v1/myMessage/getUserAnnouncementInfo', 1, 1, '2021-11-15 14:21:03', 1, '2021-11-15 14:21:03', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (945, NULL, '分页获取用户通告', '/v1/myMessage/getUserAnnouncementItemPage', '/v1/myMessage/getUserAnnouncementItemPage', 2, 1, '2021-11-15 14:21:03', 1, '2021-11-15 14:21:03', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (946, NULL, '设置通知全部已读', '/v1/myMessage/readAllAnnouncement', '/v1/myMessage/readAllAnnouncement', 2, 1, '2021-11-15 14:21:03', 1, '2021-11-15 14:21:03', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (947, NULL, '设置通知已读', '/v1/myMessage/readAnnouncement', '/v1/myMessage/readAnnouncement', 2, 1, '2021-11-15 14:21:03', 1, '2021-11-15 14:21:03', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1000, NULL, '获取安全管理', '/v1/manageSecurity/getManageSecurity', '/v1/manageSecurity/getManageSecurity', 1, 1, '2021-11-15 15:20:30', 1, '2021-11-15 15:20:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1001, NULL, '更新安全管理', '/v1/manageSecurity/updateManageSecurity', '/v1/manageSecurity/updateManageSecurity', 3, 1, '2021-11-15 15:20:30', 1, '2021-11-15 15:20:30', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1013, NULL, '数据源测试连接', '/v1/openDatasource/connect', '/v1/openDatasource/connect', 2, 1, '2021-11-17 12:00:38', 1, '2021-11-17 12:00:38', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1014, NULL, '添加数据源', '/v1/openDatasource/createDatasource', '/v1/openDatasource/createDatasource', 2, 1, '2021-11-17 12:00:39', 1, '2021-11-17 12:00:39', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1015, NULL, '删除数据源', '/v1/openDatasource/deleteDatasource', '/v1/openDatasource/deleteDatasource', 2, 1, '2021-11-17 12:00:39', 1, '2021-11-17 12:00:39', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1016, NULL, '查找数据源列表', '/v1/openDatasource/findDataSourceList', '/v1/openDatasource/findDataSourceList', 2, 1, '2021-11-17 12:00:39', 1, '2021-11-17 12:00:39', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1017, NULL, '修改数据源', '/v1/openDatasource/updateDatasource', '/v1/openDatasource/updateDatasource', 2, 1, '2021-11-17 12:00:39', 1, '2021-11-17 12:00:39', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1090, NULL, '添加通知配置', '/v1/sysAnnouncementReceiveConfig/addAnnouncementConfig', '/v1/sysAnnouncementReceiveConfig/addAnnouncementConfig', 2, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1091, NULL, '批量删除消息通知配置', '/v1/sysAnnouncementReceiveConfig/deleteAnnouncementConfig', '/v1/sysAnnouncementReceiveConfig/deleteAnnouncementConfig', 2, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1092, NULL, '编辑消息通知配置', '/v1/sysAnnouncementReceiveConfig/editAnnouncementConfig', '/v1/sysAnnouncementReceiveConfig/editAnnouncementConfig', 2, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1093, NULL, '获取通告配置详情', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigInfo', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigInfo', 4, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1094, NULL, '获取消息通知配置', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigList', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigList', 2, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1095, NULL, '获取消息接收配置', '/v1/sysAnnouncementReceiveConfig/getAnnouncementReceiveConfigList', '/v1/sysAnnouncementReceiveConfig/getAnnouncementReceiveConfigList', 2, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1096, NULL, '部门角色获取部门列表', '/v1/sysAnnouncementReceiveConfig/getDeptDepartmentList', '/v1/sysAnnouncementReceiveConfig/getDeptDepartmentList', 1, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1097, NULL, '获取用户分页列表', '/v1/sysAnnouncementReceiveConfig/getListSysUser', '/v1/sysAnnouncementReceiveConfig/getListSysUser', 2, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1098, NULL, '恢复默认', '/v1/sysAnnouncementReceiveConfig/resetReceiveConfig', '/v1/sysAnnouncementReceiveConfig/resetReceiveConfig', 2, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1099, NULL, '保存消息通知配置', '/v1/sysAnnouncementReceiveConfig/saveAnnouncementConfig', '/v1/sysAnnouncementReceiveConfig/saveAnnouncementConfig', 2, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1100, NULL, '保存消息接收配置', '/v1/sysAnnouncementReceiveConfig/saveAnnouncementReceiveConfig', '/v1/sysAnnouncementReceiveConfig/saveAnnouncementReceiveConfig', 2, 1, '2021-11-23 16:41:28', 1, '2021-11-23 16:41:28', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1140, NULL, '获取配置', '/v1/sysDingDingConfig/getSysDingDingConfigVO', '/v1/sysDingDingConfig/getSysDingDingConfigVO', 1, 1, '2021-12-29 16:46:03', 1, '2021-12-29 16:46:03', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1141, NULL, '同步', '/v1/sysDingDingConfig/syncDingDingUserData', '/v1/sysDingDingConfig/syncDingDingUserData', 1, 1, '2021-12-29 16:46:03', 1, '2021-12-29 16:46:03', 1, NULL);
INSERT INTO `sys_api_permission` VALUES (1142, NULL, '保存配置', '/v1/sysDingDingConfig/saveConfig', '/v1/sysDingDingConfig/saveConfig', 2, 1, '2021-12-29 16:46:03', 1, '2021-12-29 16:46:03', 1, NULL);

-- ----------------------------
-- Table structure for sys_backup
-- ----------------------------
DROP TABLE IF EXISTS `sys_backup`;
CREATE TABLE `sys_backup`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统备份ID',
  `bak_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统备份名称',
  `bak_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统备份编码',
  `bak_type` int(11) NOT NULL COMMENT '系统备份类型 1自动备份 2手动备份',
  `bak_time` datetime(0) NOT NULL COMMENT '系统备份完成时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '系统备份创建人ID',
  `create_time` datetime(0) NOT NULL COMMENT '系统备份创建时间',
  `file_size` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份文件大小',
  `bucket_name` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件桶名',
  `object_name` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `service_type` int(2) NULL DEFAULT NULL COMMENT '业务类型 1系统备份 2日志备份',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统备份信息情况' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_backup
-- ----------------------------

-- ----------------------------
-- Table structure for sys_backup_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_backup_record`;
CREATE TABLE `sys_backup_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `backup_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份名称',
  `backup_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份编号',
  `backup_type` int(11) NULL DEFAULT NULL COMMENT '备份类型 1自动备份 2手动备份',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '备份创建时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '备份完成时间',
  `file_size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份文件大小',
  `service_type` int(2) NULL DEFAULT NULL COMMENT '业务类型 1系统备份 2日志备份',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_backup_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_basic_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_basic_info`;
CREATE TABLE `sys_basic_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统信息ID',
  `sysinfo_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统信息变量名称',
  `sysinfo_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统信息变量编码',
  `sysinfo_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统信息描述',
  `sysinfo_value` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统信息变量',
  `create_time` datetime(0) NOT NULL COMMENT '系统信息创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '系统信息更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统的基础信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_basic_info
-- ----------------------------
INSERT INTO `sys_basic_info` VALUES (1, 'system_name', 'system_name', '\r\n系统名称', '综合管理系统', '2021-03-11 10:20:50', '2021-03-11 10:20:50');
INSERT INTO `sys_basic_info` VALUES (2, 'system_number', 'system_number', '系统编号', 'XH2022', '2021-03-11 10:21:59', '2021-03-11 10:22:01');
INSERT INTO `sys_basic_info` VALUES (3, 'system_model', 'system_model', '系统型号', 'XH2022', '2021-03-11 10:22:43', '2021-03-11 10:22:46');
INSERT INTO `sys_basic_info` VALUES (4, 'system_service_version', 'system_service_version', '系统服务版本', '1.0.6', '2021-03-11 10:23:58', '2021-03-11 10:24:00');
INSERT INTO `sys_basic_info` VALUES (5, 'system_service_provider', 'system_service_provider', '系统服务商', '福建新航物联网科技有限公司', '2021-03-11 10:24:23', '2021-03-11 10:24:24');
INSERT INTO `sys_basic_info` VALUES (6, 'production_date', 'production_date', '生产日期', '2022-01-18', '2021-03-11 10:25:55', '2021-03-11 10:25:58');
INSERT INTO `sys_basic_info` VALUES (7, 'origin', 'origin', '产地', '福建福州', '2021-03-11 10:26:31', '2021-03-11 10:26:34');
INSERT INTO `sys_basic_info` VALUES (8, 'web_version', 'web_version', 'WEB版本', 'v1.0.85.3', '2021-03-11 10:27:11', '2021-03-11 10:27:14');

-- ----------------------------
-- Table structure for sys_behavior_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_behavior_log`;
CREATE TABLE `sys_behavior_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统日志ID',
  `syslog_behavior_type` int(11) NULL DEFAULT NULL COMMENT '系统行为类型:0-基本信息;1-业务操作',
  `syslog_type` int(11) NOT NULL COMMENT '系统日志类型:0-登录日志;1-操作日志;2-定时日志',
  `syslog_time` datetime(0) NOT NULL COMMENT '系统日志记录时间',
  `syslog_content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统日志记录内容',
  `operate_by` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人名称',
  `operate_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人IP地址',
  `operate_type` int(11) NOT NULL COMMENT '操作类型:0-查询:1-修改;2-增加;3-删除',
  `operate_cost_time` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作耗时(ms)',
  `operate_create_time` datetime(0) NOT NULL COMMENT '操作时间',
  `operate_method` varchar(1280) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作请求java方法',
  `operate_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作请求路径',
  `operate_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '操作请求参数',
  `operate_request_type` int(11) NULL DEFAULT NULL COMMENT '操作请求类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 311 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '记录系统操作行为的日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_behavior_log
-- ----------------------------
INSERT INTO `sys_behavior_log` VALUES (1, 0, 1, '2022-01-12 16:58:25', '获取前端页面title', NULL, NULL, 1, '10ms', '2022-01-12 16:58:25', 'com.rotanava.boot.system.module.system.controller.index.IndexController.getIndexTitle()', '/v1/index/getIndexTitle', '', 1);
INSERT INTO `sys_behavior_log` VALUES (2, 0, 1, '2022-01-12 17:13:44', '密码登录', NULL, NULL, 1, '281ms', '2022-01-12 17:13:44', 'com.rotanava.boot.system.module.system.controller.ManageUserController.passWordLogin()', '/v1/manageUser/passWordLogin', '[{\"loginLocation\":\"福建省\",\"userAccountName\":\"admin\",\"userPassword\":\"admin\"},null]', 2);
INSERT INTO `sys_behavior_log` VALUES (3, 0, 1, '2022-01-12 17:13:44', '获取全部搜索条件', 'admin', NULL, 1, '8ms', '2022-01-12 17:13:44', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (4, 0, 1, '2022-01-12 17:13:44', '获取用户信息', 'admin', NULL, 1, '19ms', '2022-01-12 17:13:44', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@726119e9', 1);
INSERT INTO `sys_behavior_log` VALUES (5, 0, 1, '2022-01-12 17:13:44', '欢迎页面', 'admin', NULL, 1, '36ms', '2022-01-12 17:13:44', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (6, 0, 1, '2022-01-12 17:13:44', '欢迎页面', 'admin', NULL, 1, '37ms', '2022-01-12 17:13:44', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (7, 0, 1, '2022-01-12 17:13:44', '分页获取消息窗口消息', 'admin', NULL, 1, '138ms', '2022-01-12 17:13:44', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (8, 0, 1, '2022-01-12 17:13:44', '列出系统页面权限导航栏', 'admin', NULL, 1, '14ms', '2022-01-12 17:13:44', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (9, 0, 1, '2022-01-12 17:13:44', '列出系统页面权限导航栏', 'admin', NULL, 1, '102ms', '2022-01-12 17:13:44', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (10, 0, 1, '2022-01-12 17:13:58', '获取基本信息', 'admin', NULL, 1, '18ms', '2022-01-12 17:13:58', 'com.rotanava.boot.system.module.system.controller.SetAccountController.getUserBasicInfo()', '/v1/setAccount/getUserBasicInfo', '', 1);
INSERT INTO `sys_behavior_log` VALUES (11, 0, 1, '2022-01-12 17:13:58', '获取安全设置', 'admin', NULL, 1, '35ms', '2022-01-12 17:13:58', 'com.rotanava.boot.system.module.system.controller.SetAccountController.getSecuritySettings()', '/v1/setAccount/getSecuritySettings', '', 1);
INSERT INTO `sys_behavior_log` VALUES (12, 0, 1, '2022-01-12 17:14:28', '获取基本信息', 'admin', NULL, 1, '12ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.SetAccountController.getUserBasicInfo()', '/v1/setAccount/getUserBasicInfo', '', 1);
INSERT INTO `sys_behavior_log` VALUES (13, 0, 1, '2022-01-12 17:14:28', '获取全部搜索条件', 'admin', NULL, 1, '7ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (14, 0, 1, '2022-01-12 17:14:28', '获取用户信息', 'admin', NULL, 1, '25ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@2edcaef8', 1);
INSERT INTO `sys_behavior_log` VALUES (15, 0, 1, '2022-01-12 17:14:28', '获取安全设置', 'admin', NULL, 1, '28ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.SetAccountController.getSecuritySettings()', '/v1/setAccount/getSecuritySettings', '', 1);
INSERT INTO `sys_behavior_log` VALUES (16, 0, 1, '2022-01-12 17:14:28', '分页获取消息窗口消息', 'admin', NULL, 1, '17ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (17, 0, 1, '2022-01-12 17:14:28', '欢迎页面', 'admin', NULL, 1, '44ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (18, 0, 1, '2022-01-12 17:14:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '14ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (19, 0, 1, '2022-01-12 17:14:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '14ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (20, 0, 1, '2022-01-12 17:14:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '14ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[2]', 2);
INSERT INTO `sys_behavior_log` VALUES (21, 0, 1, '2022-01-12 17:14:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '13ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[6]', 2);
INSERT INTO `sys_behavior_log` VALUES (22, 0, 1, '2022-01-12 17:14:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '15ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[4]', 2);
INSERT INTO `sys_behavior_log` VALUES (23, 0, 1, '2022-01-12 17:14:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '14ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[5]', 2);
INSERT INTO `sys_behavior_log` VALUES (24, 0, 1, '2022-01-12 17:14:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '19ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[3]', 2);
INSERT INTO `sys_behavior_log` VALUES (25, 0, 1, '2022-01-12 17:14:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[8]', 2);
INSERT INTO `sys_behavior_log` VALUES (26, 0, 1, '2022-01-12 17:14:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:14:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[7]', 2);
INSERT INTO `sys_behavior_log` VALUES (27, 0, 1, '2022-01-12 17:14:39', '分页获取通告', 'admin', NULL, 1, '16ms', '2022-01-12 17:14:39', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementNoticController.getAnnouncementItemPage()', '/v1/sysAnnouncementNotic/getAnnouncementItemPage', '[{\"annCategory\":0,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (28, 0, 1, '2022-01-12 17:14:40', '分页获取通告', 'admin', NULL, 1, '14ms', '2022-01-12 17:14:40', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementSysController.getAnnouncementItemPage()', '/v1/sysAnnouncementSys/getAnnouncementItemPage', '[{\"annCategory\":1,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (29, 0, 1, '2022-01-12 17:14:41', '分页获取通告', 'admin', NULL, 1, '10ms', '2022-01-12 17:14:41', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementWarnController.getAnnouncementItemPage()', '/v1/sysAnnouncementWarn/getAnnouncementItemPage', '[{\"annCategory\":2,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (30, 0, 1, '2022-01-12 17:14:46', '获取登录日志列表', 'admin', NULL, 1, '48ms', '2022-01-12 17:14:46', 'com.rotanava.boot.system.module.system.controller.SysLogController.listUserLoginInfoLog()', '/v1/sysLog/listUserLoginInfoLog', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"log_management_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (31, 0, 1, '2022-01-12 17:14:50', '获取定时重启列表', 'admin', NULL, 1, '9ms', '2022-01-12 17:14:50', 'com.rotanava.boot.system.module.system.controller.SysSettingController.listRestartRegularly()', '/v1/sysSetting/listRestartRegularly', '[{\"pageNum\":1,\"pageSize\":5}]', 2);
INSERT INTO `sys_behavior_log` VALUES (32, 0, 1, '2022-01-12 17:14:50', '获取系统备份列表', 'admin', NULL, 1, '9ms', '2022-01-12 17:14:50', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getSysBackupList()', '/v1/sysSetting/getSysBackupList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (33, 0, 1, '2022-01-12 17:14:50', '获取系统备份配置信息', 'admin', NULL, 1, '4ms', '2022-01-12 17:14:50', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getSystemBackupsConfig()', '/v1/sysSetting/getSystemBackupsConfig', '[]', 2);
INSERT INTO `sys_behavior_log` VALUES (34, 0, 1, '2022-01-12 17:14:52', '获取安全管理', 'admin', NULL, 1, '27ms', '2022-01-12 17:14:52', 'com.rotanava.boot.system.module.system.controller.ManageSecurityController.getManageSecurity()', '/v1/manageSecurity/getManageSecurity', '', 1);
INSERT INTO `sys_behavior_log` VALUES (35, 0, 1, '2022-01-12 17:14:57', '获取登录日志列表', 'admin', NULL, 1, '40ms', '2022-01-12 17:14:57', 'com.rotanava.boot.system.module.system.controller.SysLogController.listUserLoginInfoLog()', '/v1/sysLog/listUserLoginInfoLog', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"log_management_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (36, 0, 1, '2022-01-12 17:15:00', '获取日志备份配置信息', 'admin', NULL, 1, '4ms', '2022-01-12 17:15:00', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getLogBackupConfig()', '/v1/sysSetting/getLogBackupConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (37, 0, 1, '2022-01-12 17:15:00', '获取日志备份列表', 'admin', NULL, 1, '6ms', '2022-01-12 17:15:00', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getLogBackupList()', '/v1/sysSetting/getLogBackupList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (38, 0, 1, '2022-01-12 17:15:04', '获取平台配置', 'admin', NULL, 1, '9ms', '2022-01-12 17:15:04', 'com.rotanava.boot.system.module.system.controller.PlatformSettingController.getPlatformSetting()', '/v1/platformSetting/getPlatformSetting', '', 1);
INSERT INTO `sys_behavior_log` VALUES (39, 0, 1, '2022-01-12 17:15:06', '部门角色获取部门列表', 'admin', NULL, 1, '7ms', '2022-01-12 17:15:06', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getDeptRoleDepartmentList()', '/v1/sysAnnouncementReceiveConfig/getDeptDepartmentList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (40, 0, 1, '2022-01-12 17:15:06', '获取消息通知配置', 'admin', NULL, 1, '15ms', '2022-01-12 17:15:06', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getAnnouncementConfigList()', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigList', '[{\"pageNum\":1,\"pageSize\":9999,\"queryRuleList\":[],\"searchCode\":\"announcement_config_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (41, 0, 1, '2022-01-12 17:15:09', '获取平台配置', 'admin', NULL, 1, '5ms', '2022-01-12 17:15:09', 'com.rotanava.boot.system.module.system.controller.PlatformSettingController.getPlatformSetting()', '/v1/platformSetting/getPlatformSetting', '', 1);
INSERT INTO `sys_behavior_log` VALUES (42, 0, 1, '2022-01-12 17:15:10', '部门角色获取部门列表', 'admin', NULL, 1, '4ms', '2022-01-12 17:15:10', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getDeptRoleDepartmentList()', '/v1/sysAnnouncementReceiveConfig/getDeptDepartmentList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (43, 0, 1, '2022-01-12 17:15:10', '获取消息通知配置', 'admin', NULL, 1, '8ms', '2022-01-12 17:15:10', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getAnnouncementConfigList()', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigList', '[{\"pageNum\":1,\"pageSize\":9999,\"queryRuleList\":[],\"searchCode\":\"announcement_config_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (44, 0, 1, '2022-01-12 17:15:28', '获取用户信息', 'admin', NULL, 1, '24ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@c4fe7ad', 1);
INSERT INTO `sys_behavior_log` VALUES (45, 0, 1, '2022-01-12 17:15:28', '部门角色获取部门列表', 'admin', NULL, 1, '3ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getDeptRoleDepartmentList()', '/v1/sysAnnouncementReceiveConfig/getDeptDepartmentList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (46, 0, 1, '2022-01-12 17:15:28', '获取全部搜索条件', 'admin', NULL, 1, '3ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (47, 0, 1, '2022-01-12 17:15:28', '欢迎页面', 'admin', NULL, 1, '30ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (48, 0, 1, '2022-01-12 17:15:28', '分页获取消息窗口消息', 'admin', NULL, 1, '10ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (49, 0, 1, '2022-01-12 17:15:28', '获取消息通知配置', 'admin', NULL, 1, '27ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getAnnouncementConfigList()', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigList', '[{\"pageNum\":1,\"pageSize\":9999,\"queryRuleList\":[],\"searchCode\":\"announcement_config_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (50, 0, 1, '2022-01-12 17:15:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '10ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (51, 0, 1, '2022-01-12 17:15:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '12ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[2]', 2);
INSERT INTO `sys_behavior_log` VALUES (52, 0, 1, '2022-01-12 17:15:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '13ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[3]', 2);
INSERT INTO `sys_behavior_log` VALUES (53, 0, 1, '2022-01-12 17:15:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '13ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (54, 0, 1, '2022-01-12 17:15:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '9ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[4]', 2);
INSERT INTO `sys_behavior_log` VALUES (55, 0, 1, '2022-01-12 17:15:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '6ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[5]', 2);
INSERT INTO `sys_behavior_log` VALUES (56, 0, 1, '2022-01-12 17:15:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '13ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[6]', 2);
INSERT INTO `sys_behavior_log` VALUES (57, 0, 1, '2022-01-12 17:15:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '10ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[8]', 2);
INSERT INTO `sys_behavior_log` VALUES (58, 0, 1, '2022-01-12 17:15:28', '列出系统页面权限导航栏', 'admin', NULL, 1, '8ms', '2022-01-12 17:15:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[7]', 2);
INSERT INTO `sys_behavior_log` VALUES (59, 0, 1, '2022-01-12 17:16:37', '获取前端页面title', NULL, NULL, 1, '5ms', '2022-01-12 17:16:37', 'com.rotanava.boot.system.module.system.controller.index.IndexController.getIndexTitle()', '/v1/index/getIndexTitle', '', 1);
INSERT INTO `sys_behavior_log` VALUES (60, 0, 1, '2022-01-12 17:16:41', '密码登录', NULL, NULL, 1, '58ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManageUserController.passWordLogin()', '/v1/manageUser/passWordLogin', '[{\"loginLocation\":\"福建省\",\"userAccountName\":\"admin\",\"userPassword\":\"admin\"},null]', 2);
INSERT INTO `sys_behavior_log` VALUES (61, 0, 1, '2022-01-12 17:16:41', '获取全部搜索条件', 'admin', NULL, 1, '6ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (62, 0, 1, '2022-01-12 17:16:41', '分页获取消息窗口消息', 'admin', NULL, 1, '9ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (63, 0, 1, '2022-01-12 17:16:41', '获取用户信息', 'admin', NULL, 1, '11ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@7330b1b0', 1);
INSERT INTO `sys_behavior_log` VALUES (64, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '9ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (65, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '9ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (66, 0, 1, '2022-01-12 17:16:41', '欢迎页面', 'admin', NULL, 1, '16ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (67, 0, 1, '2022-01-12 17:16:41', '欢迎页面', 'admin', NULL, 1, '10ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (68, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '5ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[2]', 2);
INSERT INTO `sys_behavior_log` VALUES (69, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '6ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[3]', 2);
INSERT INTO `sys_behavior_log` VALUES (70, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (71, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '3ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[5]', 2);
INSERT INTO `sys_behavior_log` VALUES (72, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '4ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[4]', 2);
INSERT INTO `sys_behavior_log` VALUES (73, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '5ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[6]', 2);
INSERT INTO `sys_behavior_log` VALUES (74, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '3ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[7]', 2);
INSERT INTO `sys_behavior_log` VALUES (75, 0, 1, '2022-01-12 17:16:41', '列出系统页面权限导航栏', 'admin', NULL, 1, '4ms', '2022-01-12 17:16:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[8]', 2);
INSERT INTO `sys_behavior_log` VALUES (76, 0, 1, '2022-01-12 17:16:50', '保存消息通知配置', 'admin', NULL, 3, '32ms', '2022-01-12 17:16:50', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.saveAnnouncementConfig()', '/v1/sysAnnouncementReceiveConfig/saveAnnouncementConfig', '[[{\"allowCloseNotice\":0,\"emailNotice\":0,\"phoneNotice\":0,\"smsNotice\":0,\"sysAnnConfigId\":1,\"sysNotice\":1,\"wechatNotice\":0},{\"allowCloseNotice\":0,\"emailNotice\":0,\"phoneNotice\":0,\"smsNotice\":0,\"sysAnnConfigId\":2,\"sysNotice\":1,\"wechatNotice\":0},{\"allowCloseNotice\":0,\"emailNotice\":0,\"phoneNotice\":0,\"smsNotice\":0,\"sysAnnConfigId\":3,\"sysNotice\":1,\"wechatNotice\":0}]]', 2);
INSERT INTO `sys_behavior_log` VALUES (77, 0, 1, '2022-01-12 17:16:50', '获取消息通知配置', 'admin', NULL, 1, '10ms', '2022-01-12 17:16:50', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getAnnouncementConfigList()', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigList', '[{\"pageNum\":1,\"pageSize\":9999,\"queryRuleList\":[],\"searchCode\":\"announcement_config_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (78, 0, 1, '2022-01-12 17:17:14', '获取系统页面权限列表', 'admin', NULL, 1, '227ms', '2022-01-12 17:17:14', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (79, 0, 1, '2022-01-12 17:20:56', '删除系统页面权限', 'admin', NULL, 4, '26ms', '2022-01-12 17:20:56', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"353\",\"354\",\"360\",\"364\",\"366\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (80, 0, 1, '2022-01-12 17:20:57', '获取系统页面权限列表', 'admin', NULL, 1, '192ms', '2022-01-12 17:20:57', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (81, 0, 1, '2022-01-12 17:21:08', '删除系统页面权限', 'admin', NULL, 4, '12ms', '2022-01-12 17:21:08', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"358\",\"359\",\"362\",\"363\",\"365\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (82, 0, 1, '2022-01-12 17:21:08', '获取系统页面权限列表', 'admin', NULL, 1, '186ms', '2022-01-12 17:21:08', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (83, 0, 1, '2022-01-12 17:21:15', '删除系统页面权限', 'admin', NULL, 4, '13ms', '2022-01-12 17:21:15', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"367\",\"368\",\"369\",\"373\",\"374\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (84, 0, 1, '2022-01-12 17:21:15', '获取系统页面权限列表', 'admin', NULL, 1, '198ms', '2022-01-12 17:21:15', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (85, 0, 1, '2022-01-12 17:21:28', '删除系统页面权限', 'admin', NULL, 4, '13ms', '2022-01-12 17:21:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"370\",\"371\",\"372\",\"377\",\"426\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (86, 0, 1, '2022-01-12 17:21:28', '获取系统页面权限列表', 'admin', NULL, 1, '178ms', '2022-01-12 17:21:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (87, 0, 1, '2022-01-12 17:21:35', '删除系统页面权限', 'admin', NULL, 4, '13ms', '2022-01-12 17:21:35', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"378\",\"379\",\"380\",\"427\",\"428\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (88, 0, 1, '2022-01-12 17:21:35', '获取系统页面权限列表', 'admin', NULL, 1, '174ms', '2022-01-12 17:21:35', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (89, 0, 1, '2022-01-12 17:21:42', '删除系统页面权限', 'admin', NULL, 4, '20ms', '2022-01-12 17:21:42', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"429\",\"434\",\"435\",\"436\",\"437\",\"438\",\"439\",\"440\",\"441\",\"442\",\"472\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (90, 0, 1, '2022-01-12 17:21:42', '获取系统页面权限列表', 'admin', NULL, 1, '200ms', '2022-01-12 17:21:42', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (91, 0, 1, '2022-01-12 17:21:46', '删除系统页面权限', 'admin', NULL, 4, '11ms', '2022-01-12 17:21:46', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"476\",\"477\",\"478\",\"479\",\"480\",\"481\",\"482\",\"483\",\"484\",\"485\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (92, 0, 1, '2022-01-12 17:21:46', '获取系统页面权限列表', 'admin', NULL, 1, '177ms', '2022-01-12 17:21:46', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (93, 0, 1, '2022-01-12 17:21:50', '删除系统页面权限', 'admin', NULL, 4, '11ms', '2022-01-12 17:21:50', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"515\",\"516\",\"517\",\"518\",\"519\",\"520\",\"521\",\"522\",\"523\",\"524\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (94, 0, 1, '2022-01-12 17:21:50', '获取系统页面权限列表', 'admin', NULL, 1, '187ms', '2022-01-12 17:21:50', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (95, 0, 1, '2022-01-12 17:21:55', '删除系统页面权限', 'admin', NULL, 4, '11ms', '2022-01-12 17:21:55', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"525\",\"526\",\"527\",\"528\",\"529\",\"530\",\"531\",\"532\",\"533\",\"534\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (96, 0, 1, '2022-01-12 17:21:55', '获取系统页面权限列表', 'admin', NULL, 1, '167ms', '2022-01-12 17:21:55', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (97, 0, 1, '2022-01-12 17:22:01', '删除系统页面权限', 'admin', NULL, 4, '11ms', '2022-01-12 17:22:01', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"535\",\"536\",\"1178\",\"1179\",\"1180\",\"1181\",\"1182\",\"1183\",\"1184\",\"1185\",\"1186\",\"1187\",\"559\",\"560\",\"561\",\"562\",\"563\",\"564\",\"565\",\"566\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (98, 0, 1, '2022-01-12 17:22:01', '获取系统页面权限列表', 'admin', NULL, 1, '161ms', '2022-01-12 17:22:01', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (99, 0, 1, '2022-01-12 17:22:05', '删除系统页面权限', 'admin', NULL, 4, '27ms', '2022-01-12 17:22:05', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{}]', 2);
INSERT INTO `sys_behavior_log` VALUES (100, 0, 1, '2022-01-12 17:22:05', '获取系统页面权限列表', 'admin', NULL, 1, '124ms', '2022-01-12 17:22:05', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (101, 0, 1, '2022-01-12 17:22:11', '删除系统页面权限', 'admin', NULL, 4, '32ms', '2022-01-12 17:22:11', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{}]', 2);
INSERT INTO `sys_behavior_log` VALUES (102, 0, 1, '2022-01-12 17:22:11', '获取系统页面权限列表', 'admin', NULL, 1, '87ms', '2022-01-12 17:22:11', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (103, 0, 1, '2022-01-12 17:22:15', '删除系统页面权限', 'admin', NULL, 4, '18ms', '2022-01-12 17:22:15', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"989\",\"990\",\"1059\",\"1060\",\"1061\",\"991\",\"1062\",\"1063\",\"1064\",\"992\",\"1055\",\"1056\",\"1057\",\"1058\",\"993\",\"1065\",\"1066\",\"1067\",\"1068\",\"1069\",\"1070\",\"1071\",\"1000\",\"1001\",\"1113\",\"1114\",\"1115\",\"1116\",\"1117\",\"1118\",\"1119\",\"1120\",\"1121\",\"1122\",\"1123\",\"1124\",\"1125\",\"1126\",\"1127\",\"1128\",\"1129\",\"1130\",\"1131\",\"1132\",\"1133\",\"1134\",\"1314\",\"1315\",\"1316\",\"1317\",\"1318\",\"1319\",\"1021\",\"1101\",\"1102\",\"1103\",\"1104\",\"1105\",\"1106\",\"1107\",\"1022\",\"1081\",\"1082\",\"1083\",\"1084\",\"1085\",\"1086\",\"1087\",\"1088\",\"1089\",\"1090\",\"1091\",\"1092\",\"1023\",\"1093\",\"1094\",\"1095\",\"1096\",\"1097\",\"1098\",\"1099\",\"1100\",\"1024\",\"1108\",\"1109\",\"1110\",\"1111\",\"1025\",\"1304\",\"1305\",\"1306\",\"1307\",\"1308\",\"1309\",\"1310\",\"1311\",\"1312\",\"1313\",\"1026\",\"1112\",\"1037\",\"1038\",\"1039\",\"1040\",\"1041\",\"1042\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (104, 0, 1, '2022-01-12 17:22:15', '获取系统页面权限列表', 'admin', NULL, 1, '69ms', '2022-01-12 17:22:15', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (105, 0, 1, '2022-01-12 17:22:21', '删除系统页面权限', 'admin', NULL, 4, '9ms', '2022-01-12 17:22:21', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"1043\",\"1044\",\"1045\",\"1053\",\"1054\",\"1046\",\"1047\",\"1048\",\"1050\",\"1051\",\"1052\",\"1049\",\"1139\",\"1188\",\"1189\",\"1190\",\"1191\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (106, 0, 1, '2022-01-12 17:22:21', '获取系统页面权限列表', 'admin', NULL, 1, '75ms', '2022-01-12 17:22:21', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (107, 0, 1, '2022-01-12 17:22:25', '删除系统页面权限', 'admin', NULL, 4, '9ms', '2022-01-12 17:22:25', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.deleteSysPagePermission()', '/v1/managePermission/deleteSysPagePermission', '[{\"sysPageIds\":[\"1192\",\"1193\",\"1194\",\"1195\"]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (108, 0, 1, '2022-01-12 17:22:25', '获取系统页面权限列表', 'admin', NULL, 1, '67ms', '2022-01-12 17:22:25', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (109, 0, 1, '2022-01-12 17:22:27', '获取系统接口权限列表', 'admin', NULL, 1, '39ms', '2022-01-12 17:22:27', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (110, 0, 1, '2022-01-12 17:22:28', '获取系统接口权限列表', 'admin', NULL, 1, '22ms', '2022-01-12 17:22:28', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":32,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (111, 0, 1, '2022-01-12 17:22:29', '获取系统接口权限列表', 'admin', NULL, 1, '11ms', '2022-01-12 17:22:29', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (112, 0, 1, '2022-01-12 17:22:34', '获取系统接口权限列表', 'admin', NULL, 1, '8ms', '2022-01-12 17:22:34', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":32,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (113, 0, 1, '2022-01-12 17:22:35', '获取系统接口权限列表', 'admin', NULL, 1, '24ms', '2022-01-12 17:22:35', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":31,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (114, 0, 1, '2022-01-12 17:22:36', '获取系统接口权限列表', 'admin', NULL, 1, '21ms', '2022-01-12 17:22:36', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":30,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (115, 0, 1, '2022-01-12 17:22:37', '获取系统接口权限列表', 'admin', NULL, 1, '20ms', '2022-01-12 17:22:37', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":29,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (116, 0, 1, '2022-01-12 17:22:42', '获取系统接口权限列表', 'admin', NULL, 1, '21ms', '2022-01-12 17:22:42', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":28,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (117, 0, 1, '2022-01-12 17:22:43', '获取系统页面权限列表', 'admin', NULL, 1, '66ms', '2022-01-12 17:22:43', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (118, 0, 1, '2022-01-12 17:22:53', '获取系统接口权限列表', 'admin', NULL, 1, '8ms', '2022-01-12 17:22:53', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (119, 0, 1, '2022-01-12 17:22:55', '获取系统接口权限列表', 'admin', NULL, 1, '7ms', '2022-01-12 17:22:55', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":32,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (120, 0, 1, '2022-01-12 17:23:01', '获取系统接口权限列表', 'admin', NULL, 1, '7ms', '2022-01-12 17:23:01', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":31,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (121, 0, 1, '2022-01-12 17:23:04', '获取系统接口权限列表', 'admin', NULL, 1, '8ms', '2022-01-12 17:23:04', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":30,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (122, 0, 1, '2022-01-12 17:23:20', '获取系统接口权限列表', 'admin', NULL, 1, '7ms', '2022-01-12 17:23:20', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":32,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (123, 0, 1, '2022-01-12 17:23:21', '获取系统接口权限列表', 'admin', NULL, 1, '7ms', '2022-01-12 17:23:21', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":30,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (124, 0, 1, '2022-01-12 17:23:30', '获取字典项值', 'admin', NULL, 1, '13ms', '2022-01-12 17:23:30', 'com.rotanava.boot.system.module.system.controller.SysDictController.getDictList()', '/v1/sysDict/getDictList', '  dictName:   dictCode: ', 1);
INSERT INTO `sys_behavior_log` VALUES (125, 0, 1, '2022-01-12 17:24:15', '分页获取api管理列表', 'admin', NULL, 1, '20ms', '2022-01-12 17:24:15', 'com.rotanava.boot.system.module.system.controller.datasevice.AppApiRegisteredController.getOpenApiPageList()', '/v1/appApiRegistered/getOpenApiPageList', '[{\"pageNum\":1,\"pageSize\":5}]', 2);
INSERT INTO `sys_behavior_log` VALUES (126, 0, 1, '2022-01-12 17:24:16', '分页获取应用列表', 'admin', NULL, 1, '11ms', '2022-01-12 17:24:16', 'com.rotanava.boot.system.module.system.controller.datasevice.AppRegisteredController.getOpenAppPageList()', '/v1/appRegistered/getOpenAppPageList', '[{\"pageNum\":1,\"pageSize\":5}]', 2);
INSERT INTO `sys_behavior_log` VALUES (127, 0, 1, '2022-01-12 17:24:19', '获取系统接口权限列表', 'admin', NULL, 1, '8ms', '2022-01-12 17:24:19', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (128, 0, 1, '2022-01-12 17:24:21', '获取系统接口权限列表', 'admin', NULL, 1, '6ms', '2022-01-12 17:24:21', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":32,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (129, 0, 1, '2022-01-12 17:24:22', '获取系统接口权限列表', 'admin', NULL, 1, '8ms', '2022-01-12 17:24:22', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (130, 0, 1, '2022-01-12 17:24:29', '获取系统接口权限列表', 'admin', NULL, 1, '6ms', '2022-01-12 17:24:29', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":32,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (131, 0, 1, '2022-01-12 17:24:31', '获取系统接口权限列表', 'admin', NULL, 1, '7ms', '2022-01-12 17:24:31', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":31,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (132, 0, 1, '2022-01-12 17:24:32', '获取系统接口权限列表', 'admin', NULL, 1, '16ms', '2022-01-12 17:24:32', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (133, 0, 1, '2022-01-12 17:24:40', '获取系统页面权限列表', 'admin', NULL, 1, '68ms', '2022-01-12 17:24:40', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (134, 0, 1, '2022-01-12 17:24:41', '获取系统接口权限列表', 'admin', NULL, 1, '20ms', '2022-01-12 17:24:41', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (135, 0, 1, '2022-01-12 17:24:42', '获取系统页面权限列表', 'admin', NULL, 1, '67ms', '2022-01-12 17:24:42', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (136, 0, 1, '2022-01-12 17:24:48', '获取用户信息', 'admin', NULL, 1, '15ms', '2022-01-12 17:24:48', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@4545eff6', 1);
INSERT INTO `sys_behavior_log` VALUES (137, 0, 1, '2022-01-12 17:24:48', '欢迎页面', 'admin', NULL, 1, '14ms', '2022-01-12 17:24:48', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (138, 0, 1, '2022-01-12 17:24:48', '获取全部搜索条件', 'admin', NULL, 1, '4ms', '2022-01-12 17:24:48', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (139, 0, 1, '2022-01-12 17:24:48', '分页获取消息窗口消息', 'admin', NULL, 1, '9ms', '2022-01-12 17:24:48', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (140, 0, 1, '2022-01-12 17:24:48', '列出系统页面权限导航栏', 'admin', NULL, 1, '11ms', '2022-01-12 17:24:48', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (141, 0, 1, '2022-01-12 17:24:48', '获取系统页面权限列表', 'admin', NULL, 1, '92ms', '2022-01-12 17:24:48', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (142, 0, 1, '2022-01-12 17:24:48', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:24:48', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (143, 0, 1, '2022-01-12 17:24:54', '查询角色列表', 'admin', NULL, 1, '35ms', '2022-01-12 17:24:54', 'com.rotanava.boot.system.module.system.controller.SysRoleManageController.listSysRoleItem()', '/v1/sysRoleManage/listSysRoleItem', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"sys_role_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (144, 0, 1, '2022-01-12 17:24:55', '获取全部用户角色', 'admin', NULL, 1, '9ms', '2022-01-12 17:24:55', 'com.rotanava.boot.system.module.system.controller.SysUserManageController.getSysRoleList()', '/v1/sysUserManage/getSysRoleList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (145, 0, 1, '2022-01-12 17:24:55', '获取用户分页列表', 'admin', NULL, 1, '29ms', '2022-01-12 17:24:55', 'com.rotanava.boot.system.module.system.controller.SysUserManageController.getListSysUser()', '/v1/sysUserManage/getListSysUser', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"sys_user_search\",\"userDeleteStatus\":1}]', 2);
INSERT INTO `sys_behavior_log` VALUES (146, 0, 1, '2022-01-12 17:24:57', '获取部门列表', 'admin', NULL, 1, '10ms', '2022-01-12 17:24:57', 'com.rotanava.boot.system.module.system.controller.SysDepartmentManageController.getDepartmentList()', '/v1/departmentManage/getDepartmentList', '  deptId: null', 1);
INSERT INTO `sys_behavior_log` VALUES (147, 0, 1, '2022-01-12 17:24:59', '获取部门列表', 'admin', NULL, 1, '12ms', '2022-01-12 17:24:59', 'com.rotanava.boot.system.module.system.controller.MyDepartmentController.getDepartmentList()', '/v1/myDepartment/getDepartmentList', '  deptId: null', 1);
INSERT INTO `sys_behavior_log` VALUES (148, 0, 1, '2022-01-12 17:25:00', '获取部门列表', 'admin', NULL, 1, '2ms', '2022-01-12 17:25:00', 'com.rotanava.boot.system.module.system.controller.SysDepartmentManageController.getDepartmentList()', '/v1/departmentManage/getDepartmentList', '  deptId: null', 1);
INSERT INTO `sys_behavior_log` VALUES (149, 0, 1, '2022-01-12 17:39:03', '获取前端页面title', NULL, NULL, 1, '2ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.index.IndexController.getIndexTitle()', '/v1/index/getIndexTitle', '', 1);
INSERT INTO `sys_behavior_log` VALUES (150, 0, 1, '2022-01-12 17:39:03', '获取用户信息', 'admin', NULL, 1, '8ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@56e5c44b', 1);
INSERT INTO `sys_behavior_log` VALUES (151, 0, 1, '2022-01-12 17:39:03', '获取全部搜索条件', 'admin', NULL, 1, '2ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (152, 0, 1, '2022-01-12 17:39:03', '欢迎页面', 'admin', NULL, 1, '9ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (153, 0, 1, '2022-01-12 17:39:03', '分页获取消息窗口消息', 'admin', NULL, 1, '8ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (154, 0, 1, '2022-01-12 17:39:03', '获取用户信息', 'admin', NULL, 1, '10ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@15f39d51', 1);
INSERT INTO `sys_behavior_log` VALUES (155, 0, 1, '2022-01-12 17:39:03', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (156, 0, 1, '2022-01-12 17:39:03', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (157, 0, 1, '2022-01-12 17:39:03', '欢迎页面', 'admin', NULL, 1, '5ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (158, 0, 1, '2022-01-12 17:39:03', '列出系统页面权限导航栏', 'admin', NULL, 1, '5ms', '2022-01-12 17:39:03', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (159, 0, 1, '2022-01-12 17:39:09', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:39:09', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (160, 0, 1, '2022-01-12 17:39:09', '分页获取消息窗口消息', 'admin', NULL, 1, '9ms', '2022-01-12 17:39:09', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (161, 0, 1, '2022-01-12 17:39:09', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:39:09', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (162, 0, 1, '2022-01-12 17:39:09', '欢迎页面', 'admin', NULL, 1, '10ms', '2022-01-12 17:39:09', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (163, 0, 1, '2022-01-12 17:39:09', '获取用户信息', 'admin', NULL, 1, '10ms', '2022-01-12 17:39:09', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@17da4dce', 1);
INSERT INTO `sys_behavior_log` VALUES (164, 0, 1, '2022-01-12 17:39:09', '获取全部搜索条件', 'admin', NULL, 1, '2ms', '2022-01-12 17:39:09', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (165, 0, 1, '2022-01-12 17:39:09', '欢迎页面', 'admin', NULL, 1, '6ms', '2022-01-12 17:39:09', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (166, 0, 1, '2022-01-12 17:39:09', '列出系统页面权限导航栏', 'admin', NULL, 1, '6ms', '2022-01-12 17:39:09', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (167, 0, 1, '2022-01-12 17:39:10', '列出系统页面权限导航栏', 'admin', NULL, 1, '6ms', '2022-01-12 17:39:10', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (168, 0, 1, '2022-01-12 17:39:11', '获取使用时间统计', 'admin', NULL, 1, '68ms', '2022-01-12 17:39:11', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getUsageTimeStatistics()', '/v1/dataCockpit/getUsageTimeStatistics', '[{\"endTime\":1641830400000,\"startTime\":1641312000000}]', 2);
INSERT INTO `sys_behavior_log` VALUES (169, 0, 1, '2022-01-12 17:39:11', '获取访问量统计', 'admin', NULL, 1, '69ms', '2022-01-12 17:39:11', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getVisitorVolumeStatistics()', '/v1/dataCockpit/getVisitorVolumeStatistics', '[{\"endTime\":1641830400000,\"startTime\":1640620800000,\"unit\":1}]', 2);
INSERT INTO `sys_behavior_log` VALUES (170, 0, 1, '2022-01-12 17:39:14', '退出登录', NULL, NULL, 3, '21ms', '2022-01-12 17:39:14', 'com.rotanava.boot.system.module.system.controller.ManageUserController.logout()', '/v1/manageUser/logout', '[null]', 2);
INSERT INTO `sys_behavior_log` VALUES (171, 0, 1, '2022-01-12 17:39:14', '获取前端页面title', NULL, NULL, 1, '2ms', '2022-01-12 17:39:14', 'com.rotanava.boot.system.module.system.controller.index.IndexController.getIndexTitle()', '/v1/index/getIndexTitle', '', 1);
INSERT INTO `sys_behavior_log` VALUES (172, 0, 1, '2022-01-12 17:39:18', '密码登录', NULL, NULL, 1, '57ms', '2022-01-12 17:39:18', 'com.rotanava.boot.system.module.system.controller.ManageUserController.passWordLogin()', '/v1/manageUser/passWordLogin', '[{\"loginLocation\":\"福建省\",\"userAccountName\":\"admin\",\"userPassword\":\"admin\"},null]', 2);
INSERT INTO `sys_behavior_log` VALUES (173, 0, 1, '2022-01-12 17:39:19', '获取全部搜索条件', 'admin', NULL, 1, '3ms', '2022-01-12 17:39:19', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (174, 0, 1, '2022-01-12 17:39:19', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:39:19', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (175, 0, 1, '2022-01-12 17:39:19', '分页获取消息窗口消息', 'admin', NULL, 1, '13ms', '2022-01-12 17:39:19', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (176, 0, 1, '2022-01-12 17:39:19', '获取用户信息', 'admin', NULL, 1, '15ms', '2022-01-12 17:39:19', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@64f68a97', 1);
INSERT INTO `sys_behavior_log` VALUES (177, 0, 1, '2022-01-12 17:39:19', '列出系统页面权限导航栏', 'admin', NULL, 1, '12ms', '2022-01-12 17:39:19', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (178, 0, 1, '2022-01-12 17:39:19', '欢迎页面', 'admin', NULL, 1, '17ms', '2022-01-12 17:39:19', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (179, 0, 1, '2022-01-12 17:39:19', '欢迎页面', 'admin', NULL, 1, '6ms', '2022-01-12 17:39:19', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (180, 0, 1, '2022-01-12 17:39:19', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:39:19', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (181, 0, 1, '2022-01-12 17:39:20', '列出系统页面权限导航栏', 'admin', NULL, 1, '6ms', '2022-01-12 17:39:20', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (182, 0, 1, '2022-01-12 17:39:20', '获取管理系统报告列表', 'admin', NULL, 1, '30ms', '2022-01-12 17:39:20', 'com.rotanava.boot.system.module.system.controller.ManageReportController.listAdminSystemReport()', '/v1/manageReport/listAdminSystemReport', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"admin_feedback_search\",\"sort\":\"DESC\",\"sortColumn\":\"report_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (183, 0, 1, '2022-01-12 17:39:23', '分页获取通告', 'admin', NULL, 1, '3ms', '2022-01-12 17:39:23', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementSysController.getAnnouncementItemPage()', '/v1/sysAnnouncementSys/getAnnouncementItemPage', '[{\"annCategory\":1,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (184, 0, 1, '2022-01-12 17:39:25', '获取使用时间统计', 'admin', NULL, 1, '5ms', '2022-01-12 17:39:25', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getUsageTimeStatistics()', '/v1/dataCockpit/getUsageTimeStatistics', '[{\"endTime\":1641830400000,\"startTime\":1641312000000}]', 2);
INSERT INTO `sys_behavior_log` VALUES (185, 0, 1, '2022-01-12 17:39:25', '获取访问量统计', 'admin', NULL, 1, '5ms', '2022-01-12 17:39:25', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getVisitorVolumeStatistics()', '/v1/dataCockpit/getVisitorVolumeStatistics', '[{\"endTime\":1641830400000,\"startTime\":1640620800000,\"unit\":1}]', 2);
INSERT INTO `sys_behavior_log` VALUES (186, 0, 1, '2022-01-12 17:39:52', '退出登录', NULL, NULL, 3, '15ms', '2022-01-12 17:39:52', 'com.rotanava.boot.system.module.system.controller.ManageUserController.logout()', '/v1/manageUser/logout', '[null]', 2);
INSERT INTO `sys_behavior_log` VALUES (187, 0, 1, '2022-01-12 17:39:52', '获取前端页面title', NULL, NULL, 1, '2ms', '2022-01-12 17:39:52', 'com.rotanava.boot.system.module.system.controller.index.IndexController.getIndexTitle()', '/v1/index/getIndexTitle', '', 1);
INSERT INTO `sys_behavior_log` VALUES (188, 0, 1, '2022-01-12 17:40:03', '密码登录', NULL, NULL, 1, '35ms', '2022-01-12 17:40:03', 'com.rotanava.boot.system.module.system.controller.ManageUserController.passWordLogin()', '/v1/manageUser/passWordLogin', '[{\"loginLocation\":\"福建省\",\"userAccountName\":\"admin\",\"userPassword\":\"admin\"},null]', 2);
INSERT INTO `sys_behavior_log` VALUES (189, 0, 1, '2022-01-12 17:40:03', '欢迎页面', 'admin', NULL, 1, '9ms', '2022-01-12 17:40:03', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (190, 0, 1, '2022-01-12 17:40:03', '获取全部搜索条件', 'admin', NULL, 1, '3ms', '2022-01-12 17:40:03', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (191, 0, 1, '2022-01-12 17:40:03', '欢迎页面', 'admin', NULL, 1, '9ms', '2022-01-12 17:40:03', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (192, 0, 1, '2022-01-12 17:40:03', '分页获取消息窗口消息', 'admin', NULL, 1, '7ms', '2022-01-12 17:40:03', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (193, 0, 1, '2022-01-12 17:40:03', '列出系统页面权限导航栏', 'admin', NULL, 1, '7ms', '2022-01-12 17:40:03', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (194, 0, 1, '2022-01-12 17:40:03', '获取用户信息', 'admin', NULL, 1, '10ms', '2022-01-12 17:40:03', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@605b974e', 1);
INSERT INTO `sys_behavior_log` VALUES (195, 0, 1, '2022-01-12 17:40:03', '列出系统页面权限导航栏', 'admin', NULL, 1, '5ms', '2022-01-12 17:40:03', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (196, 0, 1, '2022-01-12 17:40:03', '列出系统页面权限导航栏', 'admin', NULL, 1, '5ms', '2022-01-12 17:40:03', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (197, 0, 1, '2022-01-12 17:40:08', '退出登录', NULL, NULL, 3, '13ms', '2022-01-12 17:40:08', 'com.rotanava.boot.system.module.system.controller.ManageUserController.logout()', '/v1/manageUser/logout', '[null]', 2);
INSERT INTO `sys_behavior_log` VALUES (198, 0, 1, '2022-01-12 17:40:08', '获取前端页面title', NULL, NULL, 1, '2ms', '2022-01-12 17:40:08', 'com.rotanava.boot.system.module.system.controller.index.IndexController.getIndexTitle()', '/v1/index/getIndexTitle', '', 1);
INSERT INTO `sys_behavior_log` VALUES (199, 0, 1, '2022-01-12 18:25:49', '获取前端页面title', NULL, NULL, 1, '3ms', '2022-01-12 18:25:49', 'com.rotanava.boot.system.module.system.controller.index.IndexController.getIndexTitle()', '/v1/index/getIndexTitle', '', 1);
INSERT INTO `sys_behavior_log` VALUES (200, 0, 1, '2022-01-13 14:40:55', '获取前端页面title', NULL, NULL, 1, '8ms', '2022-01-13 14:40:55', 'com.rotanava.boot.system.module.system.controller.index.IndexController.getIndexTitle()', '/v1/index/getIndexTitle', '', 1);
INSERT INTO `sys_behavior_log` VALUES (201, 0, 1, '2022-01-13 14:41:00', '密码登录', NULL, NULL, 1, '205ms', '2022-01-13 14:41:00', 'com.rotanava.boot.system.module.system.controller.ManageUserController.passWordLogin()', '/v1/manageUser/passWordLogin', '[{\"loginLocation\":\"福建省\",\"userAccountName\":\"admin\",\"userPassword\":\"admin\"},null]', 2);
INSERT INTO `sys_behavior_log` VALUES (202, 0, 1, '2022-01-13 14:41:01', '获取全部搜索条件', 'admin', NULL, 1, '8ms', '2022-01-13 14:41:01', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (203, 0, 1, '2022-01-13 14:41:01', '获取用户信息', 'admin', NULL, 1, '22ms', '2022-01-13 14:41:01', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@26aef2fe', 1);
INSERT INTO `sys_behavior_log` VALUES (204, 0, 1, '2022-01-13 14:41:01', '欢迎页面', 'admin', NULL, 1, '41ms', '2022-01-13 14:41:01', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (205, 0, 1, '2022-01-13 14:41:01', '欢迎页面', 'admin', NULL, 1, '29ms', '2022-01-13 14:41:01', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (206, 0, 1, '2022-01-13 14:41:01', '分页获取消息窗口消息', 'admin', NULL, 1, '94ms', '2022-01-13 14:41:01', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (207, 0, 1, '2022-01-13 14:41:01', '列出系统页面权限导航栏', 'admin', NULL, 1, '71ms', '2022-01-13 14:41:01', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (208, 0, 1, '2022-01-13 14:41:01', '列出系统页面权限导航栏', 'admin', NULL, 1, '30ms', '2022-01-13 14:41:01', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (209, 0, 1, '2022-01-13 14:41:04', '获取使用时间统计', 'admin', NULL, 1, '57ms', '2022-01-13 14:41:04', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getUsageTimeStatistics()', '/v1/dataCockpit/getUsageTimeStatistics', '[{\"endTime\":1641916800000,\"startTime\":1641398400000}]', 2);
INSERT INTO `sys_behavior_log` VALUES (210, 0, 1, '2022-01-13 14:41:04', '获取访问量统计', 'admin', NULL, 1, '58ms', '2022-01-13 14:41:04', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getVisitorVolumeStatistics()', '/v1/dataCockpit/getVisitorVolumeStatistics', '[{\"endTime\":1641916800000,\"startTime\":1640707200000,\"unit\":1}]', 2);
INSERT INTO `sys_behavior_log` VALUES (211, 0, 1, '2022-01-13 14:41:08', '分页获取消息窗口消息', 'admin', NULL, 1, '8ms', '2022-01-13 14:41:08', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (212, 0, 1, '2022-01-13 14:41:08', '获取全部搜索条件', 'admin', NULL, 1, '5ms', '2022-01-13 14:41:08', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (213, 0, 1, '2022-01-13 14:41:08', '获取用户信息', 'admin', NULL, 1, '16ms', '2022-01-13 14:41:08', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@39f50963', 1);
INSERT INTO `sys_behavior_log` VALUES (214, 0, 1, '2022-01-13 14:41:08', '欢迎页面', 'admin', NULL, 1, '18ms', '2022-01-13 14:41:08', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (215, 0, 1, '2022-01-13 14:41:08', '列出系统页面权限导航栏', 'admin', NULL, 1, '14ms', '2022-01-13 14:41:08', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (216, 0, 1, '2022-01-13 14:41:08', '获取访问量统计', 'admin', NULL, 1, '8ms', '2022-01-13 14:41:08', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getVisitorVolumeStatistics()', '/v1/dataCockpit/getVisitorVolumeStatistics', '[{\"endTime\":1641916800000,\"startTime\":1640707200000,\"unit\":1}]', 2);
INSERT INTO `sys_behavior_log` VALUES (217, 0, 1, '2022-01-13 14:41:08', '获取使用时间统计', 'admin', NULL, 1, '8ms', '2022-01-13 14:41:08', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getUsageTimeStatistics()', '/v1/dataCockpit/getUsageTimeStatistics', '[{\"endTime\":1641916800000,\"startTime\":1641398400000}]', 2);
INSERT INTO `sys_behavior_log` VALUES (218, 0, 1, '2022-01-13 14:41:08', '列出系统页面权限导航栏', 'admin', NULL, 1, '10ms', '2022-01-13 14:41:08', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (219, 0, 1, '2022-01-13 14:45:30', '获取全部搜索条件', 'admin', NULL, 1, '15ms', '2022-01-13 14:45:30', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (220, 0, 1, '2022-01-13 14:45:30', '欢迎页面', 'admin', NULL, 1, '59ms', '2022-01-13 14:45:30', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (221, 0, 1, '2022-01-13 14:45:30', '获取用户信息', 'admin', NULL, 1, '55ms', '2022-01-13 14:45:30', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@38844cd2', 1);
INSERT INTO `sys_behavior_log` VALUES (222, 0, 1, '2022-01-13 14:45:30', '列出系统页面权限导航栏', 'admin', NULL, 1, '193ms', '2022-01-13 14:45:30', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (223, 0, 1, '2022-01-13 14:45:30', '列出系统页面权限导航栏', 'admin', NULL, 1, '19ms', '2022-01-13 14:45:30', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (224, 0, 1, '2022-01-13 14:45:30', '分页获取消息窗口消息', 'admin', NULL, 1, '120ms', '2022-01-13 14:45:30', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (225, 0, 1, '2022-01-13 14:45:30', '获取数据驾驶舱信息', 'admin', NULL, 1, '290ms', '2022-01-13 14:45:30', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getDataCockpit()', '/v1/dataCockpit/getDataCockpit', '', 1);
INSERT INTO `sys_behavior_log` VALUES (226, 0, 1, '2022-01-13 14:45:30', '获取使用时间统计', 'admin', NULL, 1, '69ms', '2022-01-13 14:45:30', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getUsageTimeStatistics()', '/v1/dataCockpit/getUsageTimeStatistics', '[{\"endTime\":1641916800000,\"startTime\":1641398400000}]', 2);
INSERT INTO `sys_behavior_log` VALUES (227, 0, 1, '2022-01-13 14:45:30', '获取访问量统计', 'admin', NULL, 1, '70ms', '2022-01-13 14:45:30', 'com.rotanava.boot.system.module.system.controller.DataCockpitController.getVisitorVolumeStatistics()', '/v1/dataCockpit/getVisitorVolumeStatistics', '[{\"endTime\":1641916800000,\"startTime\":1640707200000,\"unit\":1}]', 2);
INSERT INTO `sys_behavior_log` VALUES (228, 0, 1, '2022-01-13 14:45:42', '查询角色列表', 'admin', NULL, 1, '29ms', '2022-01-13 14:45:42', 'com.rotanava.boot.system.module.system.controller.SysRoleManageController.listSysRoleItem()', '/v1/sysRoleManage/listSysRoleItem', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"sys_role_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (229, 0, 1, '2022-01-13 14:45:43', '获取全部用户角色', 'admin', NULL, 1, '12ms', '2022-01-13 14:45:43', 'com.rotanava.boot.system.module.system.controller.SysUserManageController.getSysRoleList()', '/v1/sysUserManage/getSysRoleList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (230, 0, 1, '2022-01-13 14:45:43', '获取用户分页列表', 'admin', NULL, 1, '34ms', '2022-01-13 14:45:43', 'com.rotanava.boot.system.module.system.controller.SysUserManageController.getListSysUser()', '/v1/sysUserManage/getListSysUser', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"sys_user_search\",\"userDeleteStatus\":1}]', 2);
INSERT INTO `sys_behavior_log` VALUES (231, 0, 1, '2022-01-13 14:45:44', '获取部门列表', 'admin', NULL, 1, '13ms', '2022-01-13 14:45:44', 'com.rotanava.boot.system.module.system.controller.SysDepartmentManageController.getDepartmentList()', '/v1/departmentManage/getDepartmentList', '  deptId: null', 1);
INSERT INTO `sys_behavior_log` VALUES (232, 0, 1, '2022-01-13 14:45:45', '获取部门列表', 'admin', NULL, 1, '12ms', '2022-01-13 14:45:45', 'com.rotanava.boot.system.module.system.controller.MyDepartmentController.getDepartmentList()', '/v1/myDepartment/getDepartmentList', '  deptId: null', 1);
INSERT INTO `sys_behavior_log` VALUES (233, 0, 1, '2022-01-13 14:45:46', '获取基本信息', 'admin', NULL, 1, '27ms', '2022-01-13 14:45:46', 'com.rotanava.boot.system.module.system.controller.SetAccountController.getUserBasicInfo()', '/v1/setAccount/getUserBasicInfo', '', 1);
INSERT INTO `sys_behavior_log` VALUES (234, 0, 1, '2022-01-13 14:45:46', '获取安全设置', 'admin', NULL, 1, '32ms', '2022-01-13 14:45:46', 'com.rotanava.boot.system.module.system.controller.SetAccountController.getSecuritySettings()', '/v1/setAccount/getSecuritySettings', '', 1);
INSERT INTO `sys_behavior_log` VALUES (235, 0, 1, '2022-01-13 14:45:48', '分页获取通告', 'admin', NULL, 1, '37ms', '2022-01-13 14:45:48', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementNoticController.getAnnouncementItemPage()', '/v1/sysAnnouncementNotic/getAnnouncementItemPage', '[{\"annCategory\":0,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (236, 0, 1, '2022-01-13 14:45:48', '分页获取通告', 'admin', NULL, 1, '8ms', '2022-01-13 14:45:48', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementSysController.getAnnouncementItemPage()', '/v1/sysAnnouncementSys/getAnnouncementItemPage', '[{\"annCategory\":1,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (237, 0, 1, '2022-01-13 14:45:49', '分页获取通告', 'admin', NULL, 1, '8ms', '2022-01-13 14:45:49', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementWarnController.getAnnouncementItemPage()', '/v1/sysAnnouncementWarn/getAnnouncementItemPage', '[{\"annCategory\":2,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (238, 0, 1, '2022-01-13 14:45:49', '分页获取通告', 'admin', NULL, 1, '27ms', '2022-01-13 14:45:49', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementNoticController.getAnnouncementItemPage()', '/v1/sysAnnouncementNotic/getAnnouncementItemPage', '[{\"annCategory\":0,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (239, 0, 1, '2022-01-13 14:45:53', '分页获取通告', 'admin', NULL, 1, '3ms', '2022-01-13 14:45:53', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementSysController.getAnnouncementItemPage()', '/v1/sysAnnouncementSys/getAnnouncementItemPage', '[{\"annCategory\":1,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (240, 0, 1, '2022-01-13 14:45:54', '分页获取通告', 'admin', NULL, 1, '3ms', '2022-01-13 14:45:54', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementWarnController.getAnnouncementItemPage()', '/v1/sysAnnouncementWarn/getAnnouncementItemPage', '[{\"annCategory\":2,\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"\",\"sort\":\"descend\",\"sortColumn\":\"a.create_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (241, 0, 1, '2022-01-13 14:45:57', '获取性能', 'admin', NULL, 1, '18ms', '2022-01-13 14:45:57', 'com.rotanava.boot.system.module.system.controller.SystemMonitoringController.getPerformance()', '/v1/systemMonitoring/getPerformance', '', 1);
INSERT INTO `sys_behavior_log` VALUES (242, 0, 1, '2022-01-13 14:45:57', '获取系统基本信息', 'admin', NULL, 1, '21ms', '2022-01-13 14:45:57', 'com.rotanava.boot.system.module.system.controller.SystemMonitoringController.getBasicInfo()', '/v1/systemMonitoring/getBasicInfo', '', 1);
INSERT INTO `sys_behavior_log` VALUES (243, 0, 1, '2022-01-13 14:45:57', '获取在线记录列表', 'admin', NULL, 1, '30ms', '2022-01-13 14:45:57', 'com.rotanava.boot.system.module.system.controller.ManageUserController.listOnlineRecord()', '/v1/manageUser/listOnlineRecord', '[{\"beginTime\":\"\",\"endTime\":\"\",\"loginIp\":\"\",\"loginLocation\":\"\",\"loginName\":\"\",\"pageNum\":1,\"pageSize\":10}]', 2);
INSERT INTO `sys_behavior_log` VALUES (244, 0, 1, '2022-01-13 14:46:06', '获取全部搜索条件', 'admin', NULL, 1, '2ms', '2022-01-13 14:46:06', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (245, 0, 1, '2022-01-13 14:46:06', '获取用户信息', 'admin', NULL, 1, '26ms', '2022-01-13 14:46:06', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@7a4a0c00', 1);
INSERT INTO `sys_behavior_log` VALUES (246, 0, 1, '2022-01-13 14:46:06', '获取性能', 'admin', NULL, 1, '15ms', '2022-01-13 14:46:06', 'com.rotanava.boot.system.module.system.controller.SystemMonitoringController.getPerformance()', '/v1/systemMonitoring/getPerformance', '', 1);
INSERT INTO `sys_behavior_log` VALUES (247, 0, 1, '2022-01-13 14:46:06', '欢迎页面', 'admin', NULL, 1, '27ms', '2022-01-13 14:46:06', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (248, 0, 1, '2022-01-13 14:46:06', '获取在线记录列表', 'admin', NULL, 1, '14ms', '2022-01-13 14:46:06', 'com.rotanava.boot.system.module.system.controller.ManageUserController.listOnlineRecord()', '/v1/manageUser/listOnlineRecord', '[{\"beginTime\":\"\",\"endTime\":\"\",\"loginIp\":\"\",\"loginLocation\":\"\",\"loginName\":\"\",\"pageNum\":1,\"pageSize\":10}]', 2);
INSERT INTO `sys_behavior_log` VALUES (249, 0, 1, '2022-01-13 14:46:06', '获取系统基本信息', 'admin', NULL, 1, '20ms', '2022-01-13 14:46:06', 'com.rotanava.boot.system.module.system.controller.SystemMonitoringController.getBasicInfo()', '/v1/systemMonitoring/getBasicInfo', '', 1);
INSERT INTO `sys_behavior_log` VALUES (250, 0, 1, '2022-01-13 14:46:07', '分页获取消息窗口消息', 'admin', NULL, 1, '14ms', '2022-01-13 14:46:07', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (251, 0, 1, '2022-01-13 14:46:07', '列出系统页面权限导航栏', 'admin', NULL, 1, '16ms', '2022-01-13 14:46:07', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (252, 0, 1, '2022-01-13 14:46:07', '列出系统页面权限导航栏', 'admin', NULL, 1, '9ms', '2022-01-13 14:46:07', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (253, 0, 1, '2022-01-13 14:46:20', '获取系统备份配置信息', 'admin', NULL, 1, '4ms', '2022-01-13 14:46:20', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getSystemBackupsConfig()', '/v1/sysSetting/getSystemBackupsConfig', '[]', 2);
INSERT INTO `sys_behavior_log` VALUES (254, 0, 1, '2022-01-13 14:46:20', '获取定时重启列表', 'admin', NULL, 1, '2ms', '2022-01-13 14:46:20', 'com.rotanava.boot.system.module.system.controller.SysSettingController.listRestartRegularly()', '/v1/sysSetting/listRestartRegularly', '[{\"pageNum\":1,\"pageSize\":5}]', 2);
INSERT INTO `sys_behavior_log` VALUES (255, 0, 1, '2022-01-13 14:46:20', '获取系统备份列表', 'admin', NULL, 1, '9ms', '2022-01-13 14:46:20', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getSysBackupList()', '/v1/sysSetting/getSysBackupList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (256, 0, 1, '2022-01-13 14:46:23', '获取登录日志列表', 'admin', NULL, 1, '28ms', '2022-01-13 14:46:23', 'com.rotanava.boot.system.module.system.controller.SysLogController.listUserLoginInfoLog()', '/v1/sysLog/listUserLoginInfoLog', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"log_management_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (257, 0, 1, '2022-01-13 14:47:44', '欢迎页面', 'admin', NULL, 1, '15ms', '2022-01-13 14:47:44', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (258, 0, 1, '2022-01-13 14:47:44', '获取用户信息', 'admin', NULL, 1, '13ms', '2022-01-13 14:47:44', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@e547dde', 1);
INSERT INTO `sys_behavior_log` VALUES (259, 0, 1, '2022-01-13 14:47:44', '分页获取消息窗口消息', 'admin', NULL, 1, '18ms', '2022-01-13 14:47:44', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (260, 0, 1, '2022-01-13 14:47:44', '获取登录日志列表', 'admin', NULL, 1, '37ms', '2022-01-13 14:47:44', 'com.rotanava.boot.system.module.system.controller.SysLogController.listUserLoginInfoLog()', '/v1/sysLog/listUserLoginInfoLog', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"log_management_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (261, 0, 1, '2022-01-13 14:47:44', '列出系统页面权限导航栏', 'admin', NULL, 1, '9ms', '2022-01-13 14:47:44', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (262, 0, 1, '2022-01-13 14:47:44', '获取系统日志列表', 'admin', NULL, 1, '58ms', '2022-01-13 14:47:44', 'com.rotanava.boot.system.module.system.controller.SysLogController.getBehaviorLogList()', '/v1/sysLog/getBehaviorLogList', '[{\"content\":\"\",\"dateArray\":[],\"operateBy\":\"\",\"operateType\":\"\",\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[{\"filed\":\"begin_time\",\"rule\":\"ge\",\"value\":\"2022-01-13 00:00:00\"},{\"filed\":\"end_time\",\"rule\":\"le\",\"value\":\"2022-01-13 14:47:43\"}],\"searchCode\":\"sys_log_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (263, 0, 1, '2022-01-13 14:47:44', '列出系统页面权限导航栏', 'admin', NULL, 1, '10ms', '2022-01-13 14:47:44', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (264, 0, 1, '2022-01-13 14:47:45', '获取全部搜索条件', 'admin', NULL, 1, '1006ms', '2022-01-13 14:47:45', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (265, 0, 1, '2022-01-13 14:47:46', '获取日志备份配置信息', 'admin', NULL, 1, '5ms', '2022-01-13 14:47:46', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getLogBackupConfig()', '/v1/sysSetting/getLogBackupConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (266, 0, 1, '2022-01-13 14:47:46', '获取日志备份列表', 'admin', NULL, 1, '4ms', '2022-01-13 14:47:46', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getLogBackupList()', '/v1/sysSetting/getLogBackupList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (267, 0, 1, '2022-01-13 14:51:26', '列出系统页面权限导航栏', 'admin', NULL, 1, '9ms', '2022-01-13 14:51:26', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (268, 0, 1, '2022-01-13 14:51:26', '分页获取消息窗口消息', 'admin', NULL, 1, '13ms', '2022-01-13 14:51:26', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (269, 0, 1, '2022-01-13 14:51:26', '获取登录日志列表', 'admin', NULL, 1, '17ms', '2022-01-13 14:51:26', 'com.rotanava.boot.system.module.system.controller.SysLogController.listUserLoginInfoLog()', '/v1/sysLog/listUserLoginInfoLog', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"log_management_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (270, 0, 1, '2022-01-13 14:51:26', '获取用户信息', 'admin', NULL, 1, '20ms', '2022-01-13 14:51:26', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@576d3c7b', 1);
INSERT INTO `sys_behavior_log` VALUES (271, 0, 1, '2022-01-13 14:51:26', '欢迎页面', 'admin', NULL, 1, '21ms', '2022-01-13 14:51:26', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (272, 0, 1, '2022-01-13 14:51:26', '列出系统页面权限导航栏', 'admin', NULL, 1, '6ms', '2022-01-13 14:51:26', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (273, 0, 1, '2022-01-13 14:51:26', '获取全部搜索条件', 'admin', NULL, 1, '539ms', '2022-01-13 14:51:26', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (274, 0, 1, '2022-01-13 14:51:30', '获取用户信息', 'admin', NULL, 1, '12ms', '2022-01-13 14:51:30', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@2ce7af3f', 1);
INSERT INTO `sys_behavior_log` VALUES (275, 0, 1, '2022-01-13 14:51:30', '欢迎页面', 'admin', NULL, 1, '15ms', '2022-01-13 14:51:30', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (276, 0, 1, '2022-01-13 14:51:30', '分页获取消息窗口消息', 'admin', NULL, 1, '11ms', '2022-01-13 14:51:30', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (277, 0, 1, '2022-01-13 14:51:30', '获取登录日志列表', 'admin', NULL, 1, '15ms', '2022-01-13 14:51:30', 'com.rotanava.boot.system.module.system.controller.SysLogController.listUserLoginInfoLog()', '/v1/sysLog/listUserLoginInfoLog', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"log_management_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (278, 0, 1, '2022-01-13 14:51:30', '列出系统页面权限导航栏', 'admin', NULL, 1, '6ms', '2022-01-13 14:51:30', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (279, 0, 1, '2022-01-13 14:51:30', '列出系统页面权限导航栏', 'admin', NULL, 1, '5ms', '2022-01-13 14:51:30', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (280, 0, 1, '2022-01-13 14:51:30', '获取全部搜索条件', 'admin', NULL, 1, '560ms', '2022-01-13 14:51:30', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (281, 0, 1, '2022-01-13 14:52:33', '获取用户信息', 'admin', NULL, 1, '16ms', '2022-01-13 14:52:33', 'com.rotanava.boot.system.module.system.controller.ManageUserController.getUserInfo()', '/v1/manageUser/getUserInfo', '  request: org.apache.shiro.web.servlet.ShiroHttpServletRequest@2cff2fec', 1);
INSERT INTO `sys_behavior_log` VALUES (282, 0, 1, '2022-01-13 14:52:33', '欢迎页面', 'admin', NULL, 1, '21ms', '2022-01-13 14:52:33', 'com.rotanava.boot.system.module.system.controller.CommonController.welcomePage()', '/v1/common/welcomePage', '', 1);
INSERT INTO `sys_behavior_log` VALUES (283, 0, 1, '2022-01-13 14:52:33', '分页获取消息窗口消息', 'admin', NULL, 1, '14ms', '2022-01-13 14:52:33', 'com.rotanava.boot.system.module.system.controller.announcement.AnnouncementWindowsController.getAnnouncementWinItemPage()', '/v1/announcementWindows/getAnnouncementNoticItemPage', '[{\"pageNum\":0,\"pageSize\":0,\"queryRuleList\":[]}]', 2);
INSERT INTO `sys_behavior_log` VALUES (284, 0, 1, '2022-01-13 14:52:33', '获取登录日志列表', 'admin', NULL, 1, '23ms', '2022-01-13 14:52:33', 'com.rotanava.boot.system.module.system.controller.SysLogController.listUserLoginInfoLog()', '/v1/sysLog/listUserLoginInfoLog', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"log_management_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (285, 0, 1, '2022-01-13 14:52:33', '获取系统日志列表', 'admin', NULL, 1, '32ms', '2022-01-13 14:52:33', 'com.rotanava.boot.system.module.system.controller.SysLogController.getBehaviorLogList()', '/v1/sysLog/getBehaviorLogList', '[{\"content\":\"\",\"dateArray\":[],\"operateBy\":\"\",\"operateType\":\"\",\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[{\"filed\":\"begin_time\",\"rule\":\"ge\",\"value\":\"2022-01-13 00:00:00\"},{\"filed\":\"end_time\",\"rule\":\"le\",\"value\":\"2022-01-13 14:52:33\"}],\"searchCode\":\"sys_log_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (286, 0, 1, '2022-01-13 14:52:33', '列出系统页面权限导航栏', 'admin', NULL, 1, '6ms', '2022-01-13 14:52:33', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (287, 0, 1, '2022-01-13 14:52:33', '列出系统页面权限导航栏', 'admin', NULL, 1, '8ms', '2022-01-13 14:52:33', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissionsNavigationBar()', '/v1/managePermission/listSysPagePermissionsNavigationBar', '[1]', 2);
INSERT INTO `sys_behavior_log` VALUES (288, 0, 1, '2022-01-13 14:52:34', '获取全部搜索条件', 'admin', NULL, 1, '647ms', '2022-01-13 14:52:34', 'com.rotanava.boot.system.module.system.controller.CommonController.getAllSearchConfig()', '/v1/common/getAllSearchConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (289, 0, 1, '2022-01-13 14:52:36', '获取日志备份列表', 'admin', NULL, 1, '2ms', '2022-01-13 14:52:36', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getLogBackupList()', '/v1/sysSetting/getLogBackupList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (290, 0, 1, '2022-01-13 14:52:36', '获取日志备份配置信息', 'admin', NULL, 1, '2ms', '2022-01-13 14:52:36', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getLogBackupConfig()', '/v1/sysSetting/getLogBackupConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (291, 0, 1, '2022-01-13 14:52:49', '保存日志备份配置信息', 'admin', NULL, 3, '12ms', '2022-01-13 14:52:49', 'com.rotanava.boot.system.module.system.controller.SysSettingController.saveLogBackupConfig()', '/v1/sysSetting/saveLogBackupConfig', '[{\"logBackupFrequency\":30,\"logBackupSaveDays\":90,\"logInfoSaveDays\":90,\"logScheduledBackupOption\":1}]', 2);
INSERT INTO `sys_behavior_log` VALUES (292, 0, 1, '2022-01-13 14:52:49', '获取日志备份配置信息', 'admin', NULL, 1, '2ms', '2022-01-13 14:52:49', 'com.rotanava.boot.system.module.system.controller.SysSettingController.getLogBackupConfig()', '/v1/sysSetting/getLogBackupConfig', '', 1);
INSERT INTO `sys_behavior_log` VALUES (293, 0, 1, '2022-01-13 14:53:14', '日志备份立即备份', 'admin', NULL, 2, '18ms', '2022-01-13 14:53:14', 'com.rotanava.boot.system.module.system.controller.SysSettingController.logBackupNow()', '/v1/sysSetting/logBackupNow', '[]', 2);
INSERT INTO `sys_behavior_log` VALUES (294, 0, 1, '2022-01-13 14:54:31', '获取安全管理', 'admin', NULL, 1, '23ms', '2022-01-13 14:54:31', 'com.rotanava.boot.system.module.system.controller.ManageSecurityController.getManageSecurity()', '/v1/manageSecurity/getManageSecurity', '', 1);
INSERT INTO `sys_behavior_log` VALUES (295, 0, 1, '2022-01-13 14:54:34', '获取ldap', 'admin', NULL, 1, '7ms', '2022-01-13 14:54:34', 'com.rotanava.boot.system.module.system.controller.ldap.LDAPController.getLdap()', '/v1/ldap/getLdap', '', 1);
INSERT INTO `sys_behavior_log` VALUES (296, 0, 1, '2022-01-13 14:54:41', '获取ldap', 'admin', NULL, 1, '3ms', '2022-01-13 14:54:41', 'com.rotanava.boot.system.module.system.controller.ldap.LDAPController.getLdap()', '/v1/ldap/getLdap', '', 1);
INSERT INTO `sys_behavior_log` VALUES (297, 0, 1, '2022-01-13 14:57:28', '获取平台配置', 'admin', NULL, 1, '5ms', '2022-01-13 14:57:28', 'com.rotanava.boot.system.module.system.controller.PlatformSettingController.getPlatformSetting()', '/v1/platformSetting/getPlatformSetting', '', 1);
INSERT INTO `sys_behavior_log` VALUES (298, 0, 1, '2022-01-13 14:57:30', '部门角色获取部门列表', 'admin', NULL, 1, '6ms', '2022-01-13 14:57:30', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getDeptRoleDepartmentList()', '/v1/sysAnnouncementReceiveConfig/getDeptDepartmentList', '', 1);
INSERT INTO `sys_behavior_log` VALUES (299, 0, 1, '2022-01-13 14:57:30', '获取消息通知配置', 'admin', NULL, 1, '15ms', '2022-01-13 14:57:30', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getAnnouncementConfigList()', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigList', '[{\"pageNum\":1,\"pageSize\":9999,\"queryRuleList\":[],\"searchCode\":\"announcement_config_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (300, 0, 1, '2022-01-13 14:57:32', '分页获取用户通告', 'admin', NULL, 1, '12ms', '2022-01-13 14:57:32', 'com.rotanava.boot.system.module.system.controller.announcement.MyMessageController.getUserAnnouncementItemPage()', '/v1/myMessage/getUserAnnouncementItemPage', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[{\"filed\":\"beginTime\",\"rule\":\"ge\",\"value\":\"2022-01-06 00:00:00\"},{\"filed\":\"endTime\",\"rule\":\"le\",\"value\":\"2022-01-13 23:59:59\"}],\"searchCode\":\"my_message_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (301, 0, 1, '2022-01-13 14:57:33', '获取消息接收配置', 'admin', NULL, 1, '14ms', '2022-01-13 14:57:33', 'com.rotanava.boot.system.module.system.controller.announcement.SysAnnouncementReceiveConfigController.getAnnouncementReceiveConfigList()', '/v1/sysAnnouncementReceiveConfig/getAnnouncementReceiveConfigList', '[{\"pageNum\":1,\"pageSize\":9999,\"queryRuleList\":[],\"searchCode\":\"announcement_receive_config_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (302, 0, 1, '2022-01-13 14:57:35', '获取系统页面权限列表', 'admin', NULL, 1, '89ms', '2022-01-13 14:57:35', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysPagePermissions()', '/v1/managePermission/listSysPagePermissions', '[{\"queryRuleList\":[],\"searchCode\":\"page_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (303, 0, 1, '2022-01-13 14:57:36', '获取系统接口权限列表', 'admin', NULL, 1, '17ms', '2022-01-13 14:57:36', 'com.rotanava.boot.system.module.system.controller.ManagePermissionController.listSysApiPermissions()', '/v1/managePermission/listSysApiPermissions', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"api_resources_search\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (304, 0, 1, '2022-01-13 14:57:37', '获取字典项值', 'admin', NULL, 1, '16ms', '2022-01-13 14:57:37', 'com.rotanava.boot.system.module.system.controller.SysDictController.getDictList()', '/v1/sysDict/getDictList', '  dictName:   dictCode: ', 1);
INSERT INTO `sys_behavior_log` VALUES (305, 0, 1, '2022-01-13 14:57:40', '分页获取api管理列表', 'admin', NULL, 1, '14ms', '2022-01-13 14:57:40', 'com.rotanava.boot.system.module.system.controller.datasevice.AppApiRegisteredController.getOpenApiPageList()', '/v1/appApiRegistered/getOpenApiPageList', '[{\"pageNum\":1,\"pageSize\":5}]', 2);
INSERT INTO `sys_behavior_log` VALUES (306, 0, 1, '2022-01-13 14:57:40', '分页获取应用列表', 'admin', NULL, 1, '12ms', '2022-01-13 14:57:40', 'com.rotanava.boot.system.module.system.controller.datasevice.AppRegisteredController.getOpenAppPageList()', '/v1/appRegistered/getOpenAppPageList', '[{\"pageNum\":1,\"pageSize\":5}]', 2);
INSERT INTO `sys_behavior_log` VALUES (307, 0, 1, '2022-01-13 14:57:41', '查找数据源列表', 'admin', NULL, 1, '11ms', '2022-01-13 14:57:41', 'com.rotanava.boot.system.module.system.controller.datasevice.OpenDataSourceController.findDataSourceList()', '/v1/openDatasource/findDataSourceList', '[{\"pageNum\":1,\"pageSize\":5}]', 2);
INSERT INTO `sys_behavior_log` VALUES (308, 0, 1, '2022-01-13 14:57:43', '获取表格列表', 'admin', NULL, 1, '8ms', '2022-01-13 14:57:43', 'com.rotanava.boot.system.module.system.controller.ManageFormController.listForms()', '/v1/manageForm/listForms', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"listformsearch\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (309, 0, 1, '2022-01-13 14:57:44', '获取个人系统报告列表', 'admin', NULL, 1, '22ms', '2022-01-13 14:57:44', 'com.rotanava.boot.system.module.system.controller.ManageReportController.listPersonalSystemReport()', '/v1/manageReport/listPersonalSystemReport', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"personal_feedback_search\",\"sort\":\"DESC\",\"sortColumn\":\"report_time\"}]', 2);
INSERT INTO `sys_behavior_log` VALUES (310, 0, 1, '2022-01-13 14:57:46', '获取管理系统报告列表', 'admin', NULL, 1, '7ms', '2022-01-13 14:57:46', 'com.rotanava.boot.system.module.system.controller.ManageReportController.listAdminSystemReport()', '/v1/manageReport/listAdminSystemReport', '[{\"pageNum\":1,\"pageSize\":10,\"queryRuleList\":[],\"searchCode\":\"admin_feedback_search\",\"sort\":\"DESC\",\"sortColumn\":\"report_time\"}]', 2);

-- ----------------------------
-- Table structure for sys_depart_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_depart_permission`;
CREATE TABLE `sys_depart_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `depart_id` int(11) NOT NULL COMMENT '部门id',
  `page_permission_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_depart_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_depart_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_depart_role`;
CREATE TABLE `sys_depart_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_department_id` int(11) NOT NULL COMMENT '系统部门ID',
  `depart_role_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门角色名称',
  `role_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门角色编码',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `assign_dept_ids` json NOT NULL COMMENT '指定部门（本部门及下属部门）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_depart_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_depart_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_depart_role_permission`;
CREATE TABLE `sys_depart_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `depart_role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `page_permission_id` int(11) NULL DEFAULT NULL COMMENT '页面权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_depart_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_depart_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_depart_role_user`;
CREATE TABLE `sys_depart_role_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` int(11) NOT NULL COMMENT '用户d',
  `depart_role_id` int(11) NOT NULL COMMENT '部门角色id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_id_and_depart_role_id`(`sys_user_id`, `depart_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_depart_role_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统部门ID',
  `dept_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统部门编码',
  `parent_dept_id` int(11) NULL DEFAULT NULL COMMENT '系统父级部门ID',
  `dept_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统部门名称',
  `dept_name_en` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统部门英文名称',
  `dept_order` int(11) NOT NULL COMMENT '系统部门排序号',
  `dept_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统部门描述',
  `dept_type` int(11) NOT NULL COMMENT '系统部门类型:0-一级部门;1-子部门',
  `dept_manager` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统部门负责人信息',
  `dept_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统部门地址信息',
  `dept_fax` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统部门传真号码',
  `dept_manager_phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统部门负责人联系方式',
  `dept_status` int(11) NOT NULL COMMENT '系统部门状态:0-未激活;1-正常:2-冻结:3-过期',
  `dept_valid_time` datetime(0) NOT NULL COMMENT '系统部门有效日期',
  `create_by` int(11) NOT NULL COMMENT '系统部门创建人ID',
  `create_time` datetime(0) NOT NULL COMMENT '系统部门创建时间',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '系统部门信息更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '系统部门信息更新时间',
  `dept_delete_status` int(1) NULL DEFAULT NULL COMMENT '系统部门删除状态  0删除 1未删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dept_name`(`dept_name`) USING BTREE,
  INDEX `dept_code`(`dept_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_department
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `dict_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典编码',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `sort_type` int(1) NULL DEFAULT NULL COMMENT '排序类型 0不排序 1排序',
  `delete_status` int(1) NULL DEFAULT NULL COMMENT ':0-已删除;1-未删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_code`(`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '性别', 'sex', '性别', '2021-03-08 15:06:05', '2021-05-19 14:14:40', 0, 1);
INSERT INTO `sys_dict` VALUES (2, '接口请求方式', 'api_method', '接口请求方式', '2021-03-09 11:59:53', '2021-04-15 15:21:58', 0, 1);
INSERT INTO `sys_dict` VALUES (3, '接口鉴权方式', 'api_auth_type', '接口鉴权方式', '2021-03-09 13:48:22', '2021-03-09 13:48:22', 0, 1);
INSERT INTO `sys_dict` VALUES (4, '页面资源类型', 'page_type', '页面资源类型', '2021-03-09 13:48:22', '2021-03-09 13:48:22', 0, 1);
INSERT INTO `sys_dict` VALUES (5, '页面资源是否可见权限设置', 'page_show', '页面资源是否可见权限设置', '2021-03-09 13:48:22', '2021-03-09 13:48:22', 0, 1);
INSERT INTO `sys_dict` VALUES (6, '页面资源是否为叶子节点', 'page_leaf_flag', '页面资源是否为叶子节点', '2021-03-09 13:48:22', '2021-03-09 13:48:22', 0, 1);
INSERT INTO `sys_dict` VALUES (7, '页面资源状态', 'page_status', '页面资源状态', '2021-03-09 13:48:22', '2021-03-09 13:48:22', 0, 1);
INSERT INTO `sys_dict` VALUES (8, '页面类型', 'ability_type', '页面类型', '2021-03-09 13:48:22', '2021-03-09 13:48:22', 0, 1);
INSERT INTO `sys_dict` VALUES (9, '登录鉴权方式', 'login_access_type', '登录鉴权方式', '2021-03-10 11:02:09', '2021-03-10 11:02:11', 0, 1);
INSERT INTO `sys_dict` VALUES (14, '系统日志类型', 'syslog_type', '', '2021-03-10 17:40:23', '2021-03-10 17:40:23', NULL, 1);
INSERT INTO `sys_dict` VALUES (15, '日志操作类型', 'log_operate_type', '', '2021-03-10 17:42:34', '2021-03-10 17:42:34', NULL, 1);
INSERT INTO `sys_dict` VALUES (16, '通告类型', 'ann_category', '', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict` VALUES (18, '报告类型', 'report_type', '报告类型', '2021-03-15 10:40:46', '2021-03-15 10:40:48', 0, 1);
INSERT INTO `sys_dict` VALUES (19, '报告状态', 'report_stat', '报告状态', '2021-03-15 10:40:46', '2021-03-15 10:40:48', 0, 1);
INSERT INTO `sys_dict` VALUES (20, '页面模块类型', 'page_module_type', '页面模块类型', '2021-03-16 17:31:46', '2021-03-16 17:31:48', 0, 0);
INSERT INTO `sys_dict` VALUES (21, '用户性别', 'user_sex', '用户性别', '2021-03-08 15:06:05', '2021-03-19 14:06:52', 0, 1);
INSERT INTO `sys_dict` VALUES (22, '密码强度', 'password_strength', '密码强度', '2021-03-22 09:37:46', '2021-03-22 09:37:48', 0, 1);
INSERT INTO `sys_dict` VALUES (23, '手机安全保护类型', 'phone_safe_type', '手机安全保护类型', '2021-03-22 09:37:46', '2021-03-22 09:37:48', 0, 1);
INSERT INTO `sys_dict` VALUES (24, '邮件安全保护类型', 'email_safe_type', '邮件安全保护类型', '2021-03-22 09:38:31', '2021-03-22 09:38:33', 0, 1);
INSERT INTO `sys_dict` VALUES (25, '密码过期是否开启', 'account_pass_out_on', '密码过期是否开启', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (26, '账户锁定策略', 'account_lockout_strategy_on', '账户锁定策略', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (27, '管理员登录IP过滤是否开启', 'admin_login_ip_filtering_on', '管理员登录IP过滤是否开启', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (28, '成员登录IP过滤是否开启', 'member_login_ip_filtering_on', '成语登录IP过滤是否开启', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (29, '高级搜索输入框类型', 'searchInputType', '', '2021-03-24 10:13:24', '2021-03-24 10:13:24', NULL, 1);
INSERT INTO `sys_dict` VALUES (30, '账号身份', 'userSysrole', '账号身份:0-成员;1-管理员', '2021-03-26 17:39:20', '2021-03-26 17:39:20', NULL, 1);
INSERT INTO `sys_dict` VALUES (31, '通告状态', 'ann_send_status', '系统通告发布状态:0-未发布;1-已发布;2-已撤销', '2021-03-26 18:10:28', '2021-03-26 18:10:28', NULL, 1);
INSERT INTO `sys_dict` VALUES (32, '系统通告阅读状态:0-未读;1-已读', 'annReadFlag', '系统通告阅读状态:0-未读;1-已读', '2021-03-30 16:18:38', '2021-03-30 16:18:38', NULL, 1);
INSERT INTO `sys_dict` VALUES (33, '系统通告优先级', 'annPriority', '系统通告优先级', '2021-04-01 15:32:36', '2021-04-01 15:32:36', NULL, 1);
INSERT INTO `sys_dict` VALUES (34, '用户状态', 'userStatus', '系统用户状态:0-未激活;1-正常:2-冻结:3-过期', '2021-04-09 17:26:05', '2021-04-09 17:26:05', NULL, 1);
INSERT INTO `sys_dict` VALUES (35, '系统通告打开方式', 'annOpenType', '系统通告打开方式:0-跳转;1-新开页', '2021-04-20 11:29:08', '2021-04-20 11:29:08', NULL, 1);
INSERT INTO `sys_dict` VALUES (36, '系统通告对象类型', 'annTarget', '系统通告对象类型:0-全体用户;1-指定用户;2-指定部门', '2021-04-20 11:32:13', '2021-04-20 11:32:13', NULL, 1);
INSERT INTO `sys_dict` VALUES (37, '222222', '22222', '2222222222', '2021-04-21 16:19:22', '2021-04-21 16:19:22', NULL, 0);
INSERT INTO `sys_dict` VALUES (38, '请求方式 1GET 2POST', 'requestMethod', '请求方式 1GET 2POST', '2021-04-22 16:18:06', '2021-04-22 16:18:06', NULL, 1);
INSERT INTO `sys_dict` VALUES (39, '状态 1正常 2锁定', 'openApiStatus', '状态 1正常 2锁定', '2021-04-26 14:27:03', '2021-04-26 14:27:03', NULL, 1);
INSERT INTO `sys_dict` VALUES (40, '12312', '32132131', '', '2021-05-08 09:25:19', '2021-05-08 09:25:19', NULL, 0);
INSERT INTO `sys_dict` VALUES (41, '是否全员可见', 'visible_to_all', '是否全员可见 0-否 1-是', '2021-05-13 09:41:48', '2021-05-13 09:42:12', 0, 1);
INSERT INTO `sys_dict` VALUES (42, '设备状态', 'device_status', '设备状态：1-设备在线；2-设备离线；3-设备未激活；4设备已禁用', '2021-06-17 17:21:48', '2021-06-17 17:21:50', 0, 1);
INSERT INTO `sys_dict` VALUES (43, '设备上下线日志状态', 'behavior', '', '2021-06-23 10:48:12', '2021-06-23 10:48:12', NULL, 1);
INSERT INTO `sys_dict` VALUES (44, '设备行为', 'info_type', '', '2021-06-23 10:50:11', '2021-06-23 10:51:16', 0, 1);
INSERT INTO `sys_dict` VALUES (45, '设备操作行为', 'operation_type', '', '2021-06-24 10:50:08', '2021-06-24 10:50:08', NULL, 1);
INSERT INTO `sys_dict` VALUES (46, '项目状态', 'project_status', '', '2021-06-25 09:16:59', '2021-06-25 09:16:59', NULL, 1);
INSERT INTO `sys_dict` VALUES (48, '报警状态', 'warning_status', '报警状态 1首次报警 2重复报警 3恢复报警', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (49, '报警类型', 'warning_type', '报警类型 1告警 2故障', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (50, '报警类型', 'treatment_status', '处理状态 1待处理 2处理中 3已处理 4忽略 5已转报修', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (53, '登录状态', 'login_status', '登录的状态:0-失败:1-成功', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (54, '固定位置', 'fixed', '固定位置 1无 2left 3 right', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (55, 'align', 'align', 'align 1居中 2不居中', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (56, 'scopedSlots', 'scoped_slots', 'scopedSlots 1需要 2不需要', NULL, NULL, NULL, 1);
INSERT INTO `sys_dict` VALUES (57, '处理状态', 'hazard_status', '处理状态 1待处理 2已转报修 3已处理 4已忽略', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (60, '单位类型', 'corp_category', '【0-监理单位，1-总包单位，2-分包单位】 默认为 总包单位', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (61, '单位性质', 'corp_type', '单位性质', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (62, '证件类型', 'id_card_type', '证件类型', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (63, '项目分类', 'project_category', '项目分类', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (64, '项目状态', 'prj_size', '项目状态', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (65, '立项级别', 'approval_level_num', '立项级别', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (66, '建设性质', 'property_num', '建设性质', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (67, '工程用途', 'function_num', '工程用途', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (68, '是否字典表', 'whether_dict', '是否字典表', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (69, '工人工种', 'work_type', '工人工种', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (70, '工人类型', 'work_role', '工人类型', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (71, '工人进退场类型', 'project_worker_status', '工人进退场类型', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (72, '银行代码', 'pay_roll_top_bank_code', '银行代码', '2021-06-25 10:44:42', '2021-06-25 10:44:42', NULL, 1);
INSERT INTO `sys_dict` VALUES (73, '名单类型', 'personType', '', '2021-08-26 17:35:16', '2021-08-26 17:35:16', NULL, 1);
INSERT INTO `sys_dict` VALUES (74, '人员状态', 'personStatus', '人员状态', '2021-08-26 17:37:06', '2021-08-26 17:37:06', NULL, 1);
INSERT INTO `sys_dict` VALUES (75, '方向', 'direction', '方向', '2021-08-28 15:25:43', '2021-08-28 15:25:45', NULL, 1);
INSERT INTO `sys_dict` VALUES (76, '处理状态', 'abnormal_status', '处理状态', '2021-08-28 15:25:43', '2021-08-28 15:25:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (77, '文件类型', 'abnormal_file_type', '文件类型', '2021-08-28 15:25:43', '2021-08-28 15:25:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (78, '限制类型', 'BlacklistStatus', '限制类型', '2021-08-28 15:25:43', '2021-08-28 15:25:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (79, '车牌颜色', 'EmployeePlateColor', '车牌颜色', '2021-08-28 15:25:43', '2021-08-28 15:25:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (80, '状态', 'EmployeePlateState', '车辆状态', '2021-08-28 15:25:43', '2021-08-28 15:25:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (81, '基础类型', 'BaseTypeId', '基础类型', '2021-08-28 15:25:43', '2021-08-28 15:25:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (82, '过期', 'OverdueToTemp', '过期', '2021-08-28 15:25:43', '2021-08-28 15:25:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (83, '车位占用', 'LotOccupy', '车位占用', '2021-08-28 15:25:43', '2021-08-28 15:25:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (86, '考勤分组类型', 'attendanceType', '考勤组类型 1 FIXED为固定排班  2 TURN为轮班排班   3 NONE为无班次', '2021-10-26 14:32:30', '2021-10-26 14:32:30', NULL, 1);
INSERT INTO `sys_dict` VALUES (87, '数据流传输模式', 'streamMode', '数据流传输模式', '2021-11-01 17:46:42', '2021-11-01 17:46:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (88, '在线', 'wvpOnline', 'wvp在线', '2021-11-01 17:52:56', '2021-11-01 17:52:57', NULL, 1);
INSERT INTO `sys_dict` VALUES (89, '是否有子设备', 'parental', '1有, 0没有', '2021-11-02 09:29:21', '2021-11-02 09:29:22', NULL, 1);
INSERT INTO `sys_dict` VALUES (90, '云台类型', 'PTZType', '云台类型', '2021-11-02 10:09:46', '2021-11-02 10:09:47', NULL, 1);
INSERT INTO `sys_dict` VALUES (91, 'wvp启用', 'wvpEnable', 'wvp启用', '2021-11-02 15:56:42', '2021-11-02 15:56:43', NULL, 1);
INSERT INTO `sys_dict` VALUES (92, '信令传输模式', 'transport', '信令传输模式', '2021-11-02 16:21:34', '2021-11-02 16:21:35', NULL, 1);
INSERT INTO `sys_dict` VALUES (93, '选择状态', 'selectStatus', '选择状态', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (94, '拉流代理', 'streamType', '拉流代理', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (95, '是否推流', 'isPush', '是否推流', NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (96, '数据库类型', 'db_type', '', '2021-11-10 16:15:31', '2021-11-10 16:15:31', NULL, 1);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_id` int(11) NULL DEFAULT NULL COMMENT '字典id',
  `item_text` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典项文本',
  `item_value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典项值',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1启用 0不启用）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 592 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (1, 1, '女', '0', NULL, 0, 1, '2021-03-08 15:07:00', '2021-03-08 15:07:02');
INSERT INTO `sys_dict_item` VALUES (2, 1, '男', '1', NULL, 1, 1, '2021-03-08 15:07:21', '2021-03-08 15:07:23');
INSERT INTO `sys_dict_item` VALUES (3, 2, 'GET', '1', NULL, 0, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (4, 2, 'POST', '2', NULL, 1, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (5, 2, 'PUT', '3', NULL, 2, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (6, 2, 'DELETE', '4', NULL, 3, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (7, 3, '不鉴权', '0', NULL, 0, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (8, 3, 'Token鉴权', '1', NULL, 1, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (9, 4, '一级菜单', '0', NULL, 0, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (10, 4, '子菜单', '1', NULL, 1, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (11, 4, '接口权限', '2', NULL, 2, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (12, 5, '不可见', '0', NULL, 0, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (13, 5, '可见', '1', NULL, 1, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (14, 6, '不是', '0', NULL, 0, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (15, 6, '是', '1', NULL, 1, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (16, 7, '未激活', '0', NULL, 0, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (17, 7, '正常', '1', NULL, 1, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (18, 7, '过期', '2', NULL, 2, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (19, 8, '增', '0', NULL, 0, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (20, 8, '删', '1', NULL, 1, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (21, 8, '改', '2', NULL, 2, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (22, 8, '查', '3', NULL, 3, 1, '2021-03-09 12:00:57', '2021-03-09 12:00:57');
INSERT INTO `sys_dict_item` VALUES (23, 9, '匿名', '0', '', 0, 1, '2021-03-10 11:03:39', '2021-03-10 11:03:37');
INSERT INTO `sys_dict_item` VALUES (24, 9, '账号密码', '1', NULL, 1, 1, '2021-03-10 11:05:01', '2021-03-10 11:05:01');
INSERT INTO `sys_dict_item` VALUES (25, 9, '验证码', '2', NULL, 2, 1, '2021-03-10 11:05:01', '2021-03-10 11:05:01');
INSERT INTO `sys_dict_item` VALUES (27, 10, 'test', 'test', 'null', 1, 1, '2021-03-09 16:03:58', '2021-03-09 16:03:58');
INSERT INTO `sys_dict_item` VALUES (28, 14, '登录日志', '0', NULL, 1, 1, '2021-03-10 17:41:25', '2021-03-10 17:41:27');
INSERT INTO `sys_dict_item` VALUES (29, 14, '操作日志', '1', NULL, 1, 1, '2021-03-10 17:41:25', '2021-03-10 17:41:27');
INSERT INTO `sys_dict_item` VALUES (30, 15, '查询', '1', NULL, 1, 1, '2021-03-10 17:43:03', '2021-05-19 10:19:17');
INSERT INTO `sys_dict_item` VALUES (31, 15, '修改', '2', NULL, 1, 1, '2021-03-10 17:43:03', '2021-05-19 10:19:19');
INSERT INTO `sys_dict_item` VALUES (32, 15, '增加', '3', NULL, 1, 1, '2021-03-10 17:43:03', '2021-05-19 10:19:22');
INSERT INTO `sys_dict_item` VALUES (33, 15, '删除', '4', NULL, 1, 1, '2021-03-10 17:43:03', '2021-05-19 10:19:26');
INSERT INTO `sys_dict_item` VALUES (34, 16, '通知消息', '0', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (35, 16, '系统消息', '1', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (36, 16, '告警消息', '2', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (37, 17, '未发布', '0', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (38, 17, '已发布', '1', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (39, 17, '已撤销', '2', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (40, 18, '问题反馈', '0', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (41, 18, '改进意见', '1', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (42, 19, '待受理', '0', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (43, 19, '待确认', '1', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (44, 19, '已处理', '2', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (45, 19, '已驳回', '3', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (46, 19, '已撤销', '4', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (47, 20, '基础模块', '0', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (48, 20, '业务模块', '1', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (50, 1, '未知', '2', '', 3, 1, '2021-03-19 14:33:39', '2021-05-19 14:14:55');
INSERT INTO `sys_dict_item` VALUES (51, 21, '不透露', '2', '', 0, 1, '2021-03-19 14:33:39', '2021-03-19 14:37:38');
INSERT INTO `sys_dict_item` VALUES (52, 21, '女', '0', NULL, 1, 1, '2021-03-08 15:07:00', '2021-03-08 15:07:02');
INSERT INTO `sys_dict_item` VALUES (53, 21, '男', '1', NULL, 2, 1, '2021-03-08 15:07:21', '2021-03-08 15:07:23');
INSERT INTO `sys_dict_item` VALUES (54, 22, '低', '1', NULL, 0, 1, '2021-03-22 09:39:13', '2021-03-22 09:39:14');
INSERT INTO `sys_dict_item` VALUES (55, 22, '中', '2', NULL, 1, 1, '2021-03-22 09:39:34', '2021-03-22 09:39:36');
INSERT INTO `sys_dict_item` VALUES (56, 22, '高', '3', NULL, 2, 1, '2021-03-22 09:39:52', '2021-03-22 09:39:54');
INSERT INTO `sys_dict_item` VALUES (57, 23, '无', '0', NULL, 0, 1, '2021-03-22 09:40:43', '2021-03-22 09:40:44');
INSERT INTO `sys_dict_item` VALUES (58, 23, '验证号码完整性', '1', NULL, 1, 1, '2021-03-22 09:41:02', '2021-03-22 09:41:04');
INSERT INTO `sys_dict_item` VALUES (59, 23, '验证验证码', '2', NULL, 2, 1, '2021-03-22 09:41:02', '2021-03-22 09:41:04');
INSERT INTO `sys_dict_item` VALUES (60, 24, '无', '0', NULL, 0, 1, '2021-03-22 09:41:02', '2021-03-22 09:41:04');
INSERT INTO `sys_dict_item` VALUES (61, 24, '验证邮件完整性', '1', NULL, 1, 1, '2021-03-22 09:41:02', '2021-03-22 09:41:04');
INSERT INTO `sys_dict_item` VALUES (62, 24, '验证验证码', '2', NULL, 2, 1, '2021-03-22 09:41:02', '2021-03-22 09:41:04');
INSERT INTO `sys_dict_item` VALUES (63, 25, '关闭', '0', NULL, 0, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (64, 25, '开启', '1', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (65, 26, '关闭', '0', NULL, 0, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (66, 26, '开启', '1', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (67, 27, '禁止', '0', NULL, 0, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (68, 27, '允许', '1', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (69, 28, '禁止', '0', NULL, 0, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (70, 28, '允许', '1', NULL, 1, 1, NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (71, 29, '文本输入框', '1', '', 1, 1, '2021-03-24 10:13:40', '2021-03-24 10:13:40');
INSERT INTO `sys_dict_item` VALUES (72, 29, '数值输入框', '2', '', 2, 1, '2021-03-24 10:13:56', '2021-03-24 10:13:56');
INSERT INTO `sys_dict_item` VALUES (73, 29, '时间搜索框', '3', '', 3, 1, '2021-03-24 10:14:06', '2021-03-24 10:14:06');
INSERT INTO `sys_dict_item` VALUES (74, 30, '成员', '0', '成员', 1, 1, '2021-03-26 17:39:40', '2021-03-26 17:39:40');
INSERT INTO `sys_dict_item` VALUES (75, 30, '管理员', '1', '管理员', NULL, 1, '2021-03-26 17:39:46', '2021-03-26 17:39:46');
INSERT INTO `sys_dict_item` VALUES (76, 31, '未发布', '0', '未发布', NULL, 1, '2021-03-26 18:10:43', '2021-03-26 18:10:43');
INSERT INTO `sys_dict_item` VALUES (77, 31, '已发布', '1', '已发布', NULL, 1, '2021-03-26 18:10:49', '2021-03-26 18:10:49');
INSERT INTO `sys_dict_item` VALUES (78, 31, '已撤销', '2', '已撤销', NULL, 1, '2021-03-26 18:10:55', '2021-03-26 18:10:55');
INSERT INTO `sys_dict_item` VALUES (79, 32, '未读', '0', '系统通告阅读状态:0-未读;1-已读', 1, 1, '2021-03-30 16:19:09', '2021-03-30 16:19:09');
INSERT INTO `sys_dict_item` VALUES (80, 32, '已读', '1', '系统通告阅读状态:0-未读;1-已读', 2, 1, '2021-03-30 16:19:24', '2021-03-30 16:19:24');
INSERT INTO `sys_dict_item` VALUES (81, 33, '低', '1', '低', 1, 1, '2021-04-01 15:36:28', '2021-04-01 15:36:28');
INSERT INTO `sys_dict_item` VALUES (82, 33, '中', '2', '中', 2, 1, '2021-04-01 15:36:37', '2021-04-01 15:37:13');
INSERT INTO `sys_dict_item` VALUES (83, 33, '高', '3', '高', 3, 1, '2021-04-01 15:38:08', '2021-04-01 15:38:12');
INSERT INTO `sys_dict_item` VALUES (84, 33, '非常高', '4', '非常高', 4, 1, '2021-04-01 15:38:11', '2021-04-01 15:38:14');
INSERT INTO `sys_dict_item` VALUES (85, 34, '未激活', '0', '0-未激活', 1, 1, '2021-04-09 17:26:24', '2021-04-09 17:26:24');
INSERT INTO `sys_dict_item` VALUES (86, 34, '正常', '1', '1-正常', 2, 1, '2021-04-09 17:26:39', '2021-04-09 17:26:39');
INSERT INTO `sys_dict_item` VALUES (87, 34, '冻结', '2', '2-冻结', 3, 1, '2021-04-09 17:26:50', '2021-04-09 17:26:50');
INSERT INTO `sys_dict_item` VALUES (88, 34, '过期', '3', '3-过期', 4, 1, '2021-04-09 17:27:29', '2021-04-09 17:27:29');
INSERT INTO `sys_dict_item` VALUES (89, 35, '跳转', '0', '系统通告打开方式:0-跳转;1-新开页', 1, 1, '2021-04-20 11:29:31', '2021-04-20 11:29:31');
INSERT INTO `sys_dict_item` VALUES (90, 35, '新开页', '1', '系统通告打开方式:0-跳转;1-新开页', 2, 1, '2021-04-20 11:29:38', '2021-04-20 11:29:50');
INSERT INTO `sys_dict_item` VALUES (91, 36, '全体用户', '0', '全体用户', 1, 1, '2021-04-20 11:32:29', '2021-04-20 11:32:29');
INSERT INTO `sys_dict_item` VALUES (92, 36, '指定用户', '1', '指定用户', 2, 1, '2021-04-20 11:32:35', '2021-04-20 11:32:35');
INSERT INTO `sys_dict_item` VALUES (93, 36, '指定部门', '2', '指定部门', 3, 1, '2021-04-20 11:32:43', '2021-04-20 11:32:43');
INSERT INTO `sys_dict_item` VALUES (94, 38, 'GET', '1', 'GET', NULL, 1, '2021-04-22 16:18:19', '2021-04-22 16:18:19');
INSERT INTO `sys_dict_item` VALUES (95, 38, 'POST', '2', '2', NULL, 1, '2021-04-22 16:18:26', '2021-04-22 16:18:32');
INSERT INTO `sys_dict_item` VALUES (96, 39, '正常', '1', '1正常', NULL, 1, '2021-04-26 14:27:23', '2021-04-26 14:27:23');
INSERT INTO `sys_dict_item` VALUES (97, 39, '锁定', '2', '2锁定', NULL, 1, '2021-04-26 14:27:36', '2021-04-26 14:27:36');
INSERT INTO `sys_dict_item` VALUES (98, 41, '否', '0', '', 1, 1, '2021-05-13 09:42:36', '2021-05-13 09:43:04');
INSERT INTO `sys_dict_item` VALUES (99, 41, '是', '1', '', 2, 1, '2021-05-13 09:42:48', '2021-05-13 09:42:55');
INSERT INTO `sys_dict_item` VALUES (100, 42, '在线', '1', NULL, 1, 1, '2021-06-17 17:23:13', '2021-06-17 17:23:16');
INSERT INTO `sys_dict_item` VALUES (101, 42, '离线', '2', NULL, 2, 1, '2021-06-17 17:23:34', '2021-06-17 17:23:36');
INSERT INTO `sys_dict_item` VALUES (102, 42, '未激活', '3', NULL, 3, 1, '2021-06-17 17:23:34', '2021-06-17 17:23:36');
INSERT INTO `sys_dict_item` VALUES (103, 42, '已禁用', '4', NULL, 4, 1, '2021-06-17 17:23:34', '2021-06-17 17:23:36');
INSERT INTO `sys_dict_item` VALUES (104, 43, '下线', '1', '', 1, 1, '2021-06-23 10:48:41', '2021-06-23 10:48:41');
INSERT INTO `sys_dict_item` VALUES (105, 43, '上线', '2', '', NULL, 1, '2021-06-23 10:48:47', '2021-06-23 10:48:47');
INSERT INTO `sys_dict_item` VALUES (106, 44, '属性', '1', '', NULL, 1, '2021-06-23 10:50:25', '2021-06-23 10:50:25');
INSERT INTO `sys_dict_item` VALUES (107, 44, '事件', '2', '', NULL, 1, '2021-06-23 10:50:30', '2021-06-23 10:51:25');
INSERT INTO `sys_dict_item` VALUES (108, 45, '属性', '1', '', 1, 1, '2021-06-24 10:50:34', '2021-06-24 10:50:34');
INSERT INTO `sys_dict_item` VALUES (109, 45, '服务', '2', '', 2, 1, '2021-06-24 10:50:38', '2021-06-24 10:50:38');
INSERT INTO `sys_dict_item` VALUES (110, 46, '设计阶段', '1', '', 1, 1, '2021-06-25 09:17:13', '2021-06-25 09:17:13');
INSERT INTO `sys_dict_item` VALUES (111, 46, '在建阶段', '2', '', 2, 1, '2021-06-25 09:17:32', '2021-06-25 09:17:32');
INSERT INTO `sys_dict_item` VALUES (112, 46, '调试阶段', '3', '', 3, 1, '2021-06-25 09:17:41', '2021-06-25 09:17:41');
INSERT INTO `sys_dict_item` VALUES (113, 46, '正式使用', '4', '', 4, 1, '2021-06-25 09:17:52', '2021-06-25 09:17:52');
INSERT INTO `sys_dict_item` VALUES (114, 46, '运维阶段', '5', '', 5, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (115, 48, '首次报警', '1', '', 1, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (116, 48, '重复报警', '2', '', 2, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (117, 48, '恢复报警', '3', '', 3, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (118, 49, '告警', '1', '', 1, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (119, 49, '故障', '2', '', 2, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (120, 50, '待处理', '1', '', 1, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (121, 50, '处理中', '2', '', 2, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (122, 50, '已处理', '3', '', 3, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (123, 50, '已忽略', '4', '', 4, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (124, 50, '已转报修', '5', '', 5, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (125, 53, '失败', '0', NULL, 1, 1, '2021-07-01 15:13:38', '2021-07-01 15:13:40');
INSERT INTO `sys_dict_item` VALUES (126, 53, '成功', '1', NULL, 2, 1, '2021-07-01 15:14:01', '2021-07-01 15:14:02');
INSERT INTO `sys_dict_item` VALUES (127, 54, '无', '1', NULL, 1, 1, '2021-07-01 15:14:01', '2021-07-01 15:14:02');
INSERT INTO `sys_dict_item` VALUES (128, 54, 'left', '2', NULL, 2, 1, '2021-07-01 15:14:01', '2021-07-01 15:14:02');
INSERT INTO `sys_dict_item` VALUES (129, 54, 'right', '3', NULL, 3, 1, '2021-07-01 15:14:01', '2021-07-01 15:14:02');
INSERT INTO `sys_dict_item` VALUES (130, 55, '居中', '1', NULL, 1, 1, '2021-07-01 15:14:01', '2021-07-01 15:14:02');
INSERT INTO `sys_dict_item` VALUES (131, 55, '不居中', '2', NULL, 2, 1, '2021-07-01 15:14:01', '2021-07-01 15:14:02');
INSERT INTO `sys_dict_item` VALUES (132, 56, '需要', '1', NULL, 1, 1, '2021-07-01 15:14:01', '2021-07-01 15:14:02');
INSERT INTO `sys_dict_item` VALUES (133, 56, '不需要', '2', NULL, 2, 1, '2021-07-01 15:14:01', '2021-07-01 15:14:02');
INSERT INTO `sys_dict_item` VALUES (134, 57, '待处理', '1', '', 1, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (135, 57, '已转报修', '2', '', 2, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (136, 57, '已处理', '3', '', 3, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (137, 57, '已忽略', '4', '', 4, 1, '2021-06-25 09:18:01', '2021-06-25 09:18:01');
INSERT INTO `sys_dict_item` VALUES (138, 60, '监理单位', '0', NULL, 1, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (139, 60, '总包单位', '1', NULL, 2, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (140, 60, '分包单位', '2', NULL, 3, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (141, 61, '内资企业', '100', NULL, 1, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (142, 61, '国有企业', '110', NULL, 2, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (143, 61, '集体企业', '120', NULL, 3, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (144, 61, '股份合作企业', '130', NULL, 4, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (145, 61, '联营企业', '140', NULL, 5, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (146, 61, '国有联营企业', '141', NULL, 6, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (147, 61, '集体联营企业', '142', NULL, 7, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (148, 61, '国有与集体联营企业', '143', NULL, 8, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (149, 61, '其他联营企业', '149', NULL, 9, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (150, 61, '有限责任公司', '150', NULL, 10, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (151, 61, '国有独资公司', '151', NULL, 11, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (152, 61, '其他有限责任公司', '159', NULL, 12, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (153, 61, '股份有限公司', '160', NULL, 13, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (154, 61, '私营企业', '170', NULL, 14, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (155, 61, '私营独资企业', '171', NULL, 15, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (156, 61, '私营合伙企业', '172', NULL, 16, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (157, 61, '私营有限责任公司', '173', NULL, 17, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (158, 61, '私营股份有限公司', '174', NULL, 18, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (159, 61, '其他企业', '190', NULL, 19, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (160, 61, '港、澳、台商投资企业', '200', NULL, 20, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (161, 61, '合资经营企业（港或澳、台资）', '210', NULL, 21, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (162, 61, '合作经营企业（港或澳、台资）', '220', NULL, 22, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (163, 61, '港、澳、台商独资经营企业', '230', NULL, 23, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (164, 61, '港、澳、台商投资股份有限公司', '240', NULL, 24, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (165, 61, '其他港、澳、台商投资企业', '290', NULL, 25, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (166, 61, '外商投资企业', '300', NULL, 26, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (167, 61, '中外合资经营企业', '310', NULL, 27, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (168, 61, '中外合作经营企业', '320', NULL, 28, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (169, 61, '外资企业', '330', NULL, 29, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (170, 61, '外商投资股份有限公司', '340', NULL, 30, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (171, 61, '其他外商投资企业', '390', NULL, 31, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (172, 61, '军队单位', '810', NULL, 32, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (173, 62, '居民身份证', '01', NULL, 1, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (174, 62, '军官证', '02', NULL, 2, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (175, 62, '武警警官证', '03', NULL, 3, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (176, 62, '士兵证', '04', NULL, 4, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (177, 62, '军队离退休干部证', '05', NULL, 5, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (178, 62, '残疾人证', '06', NULL, 6, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (179, 62, '残疾军人证（1-8 级）', '07', NULL, 7, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (180, 62, '护照', '08', NULL, 8, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (181, 62, '港澳同胞回乡证', '09', NULL, 9, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (182, 62, '港澳居民来往内地通行证', '10', NULL, 10, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (183, 62, '中华人民共和国往来港澳通行证', '11', NULL, 11, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (184, 62, '台湾居民来往大陆通行证', '12', NULL, 12, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (185, 62, '大陆居民往来台湾通行证', '13', NULL, 13, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (186, 62, '外交官证', '14', NULL, 14, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (187, 62, '领事馆证', '15', NULL, 15, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (188, 62, '海员证', '16', NULL, 16, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (189, 62, '香港身份证', '17', NULL, 17, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (190, 62, '台湾身份证', '18', NULL, 18, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (191, 62, '澳门身份证', '19', NULL, 19, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (192, 62, '外国人身份证件', '20', NULL, 20, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (193, 62, '高校毕业生自主创业证', '21', NULL, 21, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (194, 62, '就业失业登记证', '22', NULL, 22, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (195, 62, '台胞证', '23', NULL, 23, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (196, 62, '退休证', '24', NULL, 24, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (197, 62, '离休证', '25', NULL, 25, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (198, 62, '其它证件', '99', NULL, 26, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (199, 63, '房屋建筑工程', '01', NULL, 1, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (200, 63, '市政公用工程', '02', NULL, 2, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (201, 63, '机电安装工程', '03', NULL, 3, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (202, 63, '铁路工程', '04', NULL, 4, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (203, 63, '公路工程', '05', NULL, 5, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (204, 63, '港口与航道工程', '06', NULL, 6, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (205, 63, '水利水电工程', '07', NULL, 7, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (206, 63, '电力工程', '08', NULL, 8, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (207, 63, '矿山工程', '09', NULL, 9, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (208, 63, '冶炼工程', '10', NULL, 10, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (209, 63, '化工石油工程', '11', NULL, 11, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (210, 63, '通信工程', '12', NULL, 12, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (211, 63, '其他', '99', NULL, 13, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (212, 64, '筹备', '001', NULL, 1, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (213, 64, '立项', '002', NULL, 2, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (214, 64, '在建', '003', NULL, 3, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (215, 64, '完工', '004', NULL, 4, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (216, 64, '停工', '005', NULL, 5, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (217, 65, '部级', '01', NULL, 1, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (218, 65, '省级', '02', NULL, 2, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (219, 65, '地市级', '03', NULL, 3, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (220, 65, '区县级', '04', NULL, 4, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (221, 65, '国家级', '05', NULL, 5, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (222, 65, '其他', '06', NULL, 6, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (223, 66, '大型', '01', NULL, 1, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (224, 66, '中型', '02', NULL, 2, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (225, 66, '小型', '03', NULL, 3, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (226, 67, '新建', '001', NULL, 1, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (227, 67, '改建', '002', NULL, 2, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (228, 67, '扩建', '003', NULL, 3, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (229, 67, '恢复', '004', NULL, 4, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (230, 67, '迁建', '005', NULL, 5, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (231, 67, '拆除', '006', NULL, 6, 1, '2021-08-19 15:47:53', '2021-08-19 15:47:53');
INSERT INTO `sys_dict_item` VALUES (232, 67, '其他', '099', NULL, 7, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:55');
INSERT INTO `sys_dict_item` VALUES (233, 68, '是', '1', NULL, 1, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:55');
INSERT INTO `sys_dict_item` VALUES (234, 68, '否', '0', NULL, 2, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (235, 69, '砌筑工', '010', NULL, 1, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (236, 69, '钢筋工', '020', NULL, 2, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (237, 69, '架子工', '030', NULL, 3, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (238, 69, '混凝土工', '040', NULL, 4, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (239, 69, '模板工', '050', NULL, 5, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (240, 69, '机械设备安装工', '060', NULL, 6, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (241, 69, '通风工', '070', NULL, 7, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (242, 69, '安装起重工', '080', NULL, 8, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (243, 69, '安装钳工', '090', NULL, 9, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (244, 69, '电气设备安装调试工', '100', NULL, 10, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (245, 69, '管道工', '110', NULL, 11, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (246, 69, '变电安装工', '120', NULL, 12, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (247, 69, '建筑电工', '130', NULL, 13, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (248, 69, '司泵工', '140', NULL, 14, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (249, 69, '挖掘铲运和桩工机械司机', '150', NULL, 15, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (250, 69, '桩机操作工', '160', NULL, 16, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (251, 69, '起重信号工', '170', NULL, 17, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (252, 69, '建筑起重机械安装拆卸工', '180', NULL, 18, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (253, 69, '装饰装修工', '190', NULL, 19, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (254, 69, '室内成套设施安装工', '200', NULL, 20, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (255, 69, '建筑门窗幕墙安装工', '210', NULL, 21, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (256, 69, '幕墙制作工', '220', NULL, 22, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (257, 69, '防水工', '230', NULL, 23, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (258, 69, '木工', '240', NULL, 24, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (259, 69, '石工', '250', NULL, 25, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (260, 69, '电焊工', '270', NULL, 26, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (261, 69, '爆破工', '280', NULL, 27, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (262, 69, '除尘工', '290', NULL, 28, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (263, 69, '测量放线工', '300', NULL, 29, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (264, 69, '线路架设工', '310', NULL, 30, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (265, 69, '古建筑传统石工', '320', NULL, 31, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (266, 69, '古建筑传统瓦工', '330', NULL, 32, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (267, 69, '古建筑传统彩画工', '340', NULL, 33, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (268, 69, '古建筑传统木工', '350', NULL, 34, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (269, 69, '古建筑传统油工', '360', NULL, 35, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (270, 69, '金属工', '380', NULL, 36, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (271, 69, '管理人员', '900', NULL, 37, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (272, 69, '杂工', '390', NULL, 38, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (273, 69, '其它', '1000', NULL, 39, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (274, 70, '管理人员', '10', NULL, 1, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (275, 70, '建筑工人', '20', NULL, 2, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (276, 71, '进场', '1', NULL, 1, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (277, 71, '退场', '0', NULL, 2, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (278, 72, '中国邮政储蓄银行（收单）-新表', '00', NULL, 1, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (279, 72, '中国工商银行', '02', NULL, 2, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (280, 72, '中国农业银行', '03', NULL, 3, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (281, 72, '中国银行', '04', NULL, 4, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (282, 72, '中国建设银行', '05', NULL, 5, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (283, 72, '国家开发银行', '01', NULL, 6, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (284, 72, '中国进出口银行', '02', NULL, 7, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (285, 72, '中国农业发展银行', '03', NULL, 8, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (286, 72, '交通银行', '01', NULL, 9, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (287, 72, '中信银行', '02', NULL, 10, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (288, 72, '中国光大银行', '03', NULL, 11, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (289, 72, '华夏银行', '04', NULL, 12, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (290, 72, '中国民生银行', '05', NULL, 13, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (291, 72, '广东发展银行', '06', NULL, 14, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (292, 72, '深圳发展银行', '07', NULL, 15, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (293, 72, '深圳发展银行', '07', NULL, 16, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (294, 72, '招商银行', '08', NULL, 17, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (295, 72, '兴业银行', '09', NULL, 18, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (296, 72, '上海浦东发展银行', '10', NULL, 19, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (297, 72, '上海银联商务/东莞商业银行', '11', NULL, 20, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (298, 72, '农村商业银行（江苏）', '14', NULL, 21, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (299, 72, '恒丰银行', '15', NULL, 22, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (300, 72, '浙商银行', '16', NULL, 23, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (301, 72, '农村合作银行', '17', NULL, 24, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (302, 72, '渤海银行股份有限公司', '18', NULL, 25, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (303, 72, '徽商银行股份有限公司', '19', NULL, 26, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (304, 72, '镇银行有限责任公司', '20', NULL, 27, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (305, 72, '城市信用社', '01', NULL, 28, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (306, 72, '农村信用社（含北京农村商业银行）、东莞农信', '02', NULL, 29, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (307, 72, '中国邮政储蓄银行（代收付）', '03', NULL, 30, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (308, 72, '汇丰银行', '01', NULL, 31, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (309, 72, '东亚银行', '02', NULL, 32, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (310, 72, '南洋商业银行', '03', NULL, 33, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (311, 72, '恒生银行(中国)有限公司', '04', NULL, 34, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (312, 72, '中国银行（香港）有限公司', '05', NULL, 35, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (313, 72, '创兴银行有限公司', '07', NULL, 36, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (314, 72, '星展银行（中国）有限公司', '09', NULL, 37, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (315, 72, '永亨银行（中国）有限公司', '10', NULL, 38, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (316, 72, '永隆银行', '12', NULL, 39, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (317, 72, '花旗银行（中国）有限公司', '31', NULL, 40, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (318, 72, '美国银行有限公司', '32', NULL, 41, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (319, 72, '摩根大通银行(中国)有限公司', '33', NULL, 42, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (320, 72, '三菱东京日联银行(中国）有限公司', '61', NULL, 43, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (321, 72, '日本三井住友银行股份有限公司', '63', NULL, 44, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (322, 72, '瑞穗实业银行（中国）有限公司', '64', NULL, 45, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (323, 72, '日本山口银行股份有限公司', '65', NULL, 46, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (324, 72, '韩国外换银行股份有限公司', '91', NULL, 47, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (325, 72, '友利银行（中国）有限公司', '93', NULL, 48, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (326, 72, '韩国产业银行', '94', NULL, 49, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (327, 72, '新韩银行(中国)有限公司', '95', NULL, 50, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (328, 72, '韩国中小企业银行有限公司', '96', NULL, 51, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (329, 72, '韩亚银行（中国）有限公司', '97', NULL, 52, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (330, 72, '华侨银行（中国）有限公司', '21', NULL, 53, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (331, 72, '大华银行（中国）有限公司', '22', NULL, 54, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (332, 72, '星展银行（中国）有限公司', '23', NULL, 55, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (333, 72, '泰国盘谷银行(大众有限公司)', '31', NULL, 56, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (334, 72, '奥地利中央合作银行股份有限公司', '41', NULL, 57, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (335, 72, '比利时联合银行股份有限公司', '51', NULL, 58, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (336, 72, '比利时富通银行有限公司', '52', NULL, 59, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (337, 72, '荷兰银行', '61', NULL, 60, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (338, 72, '荷兰安智银行股份有限公司', '62', NULL, 61, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (339, 72, '渣打银行', '71', NULL, 62, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (340, 72, '英国苏格兰皇家银行公众有限公司', '72', NULL, 63, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (341, 72, '法国兴业银行（中国)有限公司', '91', NULL, 64, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (342, 72, '法国东方汇理银行股份有限公司', '94', NULL, 65, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (343, 72, '法国外贸银行股份有限公司', '95', NULL, 66, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (344, 72, '德国德累斯登银行股份公司', '11', NULL, 67, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (345, 72, '德意志银行（中国）有限公司', '12', NULL, 68, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (346, 72, '德国商业银行股份有限公司', '13', NULL, 69, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (347, 72, '德国西德银行股份有限公司', '14', NULL, 70, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (348, 72, '德国巴伐利亚州银行', '15', NULL, 71, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (349, 72, '德国北德意志州银行', '16', NULL, 72, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (350, 72, '意大利联合圣保罗银行股份有限公司', '32', NULL, 73, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (351, 72, '瑞士信贷银行股份有限公司', '41', NULL, 74, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (352, 72, '瑞士银行', '42', NULL, 75, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (353, 72, '加拿大丰业银行有限公司', '51', NULL, 76, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (354, 72, '加拿大蒙特利尔银行有限公司', '52', NULL, 77, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (355, 72, '澳大利亚和新西兰银行集团有限公司', '61', NULL, 78, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (356, 72, '摩根士丹利国际银行（中国）有限公司', '71', NULL, 79, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (357, 72, '联合银行(中国)有限公司', '75', NULL, 80, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (358, 72, '荷兰合作银行有限公司', '76', NULL, 81, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (359, 72, '厦门国际银行', '81', NULL, 82, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (360, 72, '法国巴黎银行（中国）有限公司', '82', NULL, 83, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (361, 72, '华商银行', '85', NULL, 84, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (362, 72, '华一银行', '87', NULL, 85, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (363, 72, '广东农村信用合作社（收单）', '97', NULL, 86, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (364, 72, '银联商务（收单）', '98', NULL, 87, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (365, 72, '(澳门地区)银行', '69', NULL, 88, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (366, 72, '(香港地区)银行', '89', NULL, 89, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (367, 72, '安徽省农村信用社', 'ARCU', NULL, 90, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (368, 72, '鞍山银行', 'ASCB', NULL, 91, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (369, 72, '安阳银行', 'AYCB', NULL, 92, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (370, 72, '潍坊银行', 'BANKWF', NULL, 93, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (371, 72, '广西北部湾银行', 'BGB', NULL, 94, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (372, 72, '河北银行', 'BHB', NULL, 95, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (373, 72, '北京银行', 'BJBANK', NULL, 96, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (374, 72, '北京农村商业银行', 'BJRCB', NULL, 97, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (375, 72, '承德银行', 'BOCD', NULL, 98, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (376, 72, '朝阳银行', 'BOCY', NULL, 99, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (377, 72, '东莞银行', 'BOD', NULL, 100, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (378, 72, '丹东银行', 'BODD', NULL, 101, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (379, 72, '渤海银行', 'BOHAIB', NULL, 102, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (380, 72, '锦州银行', 'BOJZ', NULL, 103, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (381, 72, '平顶山银行', 'BOP', NULL, 104, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (382, 72, '青海银行', 'BOQH', NULL, 105, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (383, 72, '苏州银行', 'BOSZ', NULL, 106, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (384, 72, '营口银行', 'BOYK', NULL, 107, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (385, 72, '周口银行', 'BOZK', NULL, 108, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (386, 72, '包商银行', 'BSB', NULL, 109, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (387, 72, '驻马店银行', 'BZMD', NULL, 110, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (388, 72, '城市商业银行资金清算中心', 'CBBQS', NULL, 111, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (389, 72, '开封市商业银行', 'CBKF', NULL, 112, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (390, 72, '重庆三峡银行', 'CCQTGB', NULL, 113, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (391, 72, '成都银行', 'CDCB', NULL, 114, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (392, 72, '成都农商银行', 'CDRCB', NULL, 115, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (393, 72, '南充市商业银行', 'CGNB', NULL, 116, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (394, 72, '重庆银行', 'CQBANK', NULL, 117, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (395, 72, '重庆农村商业银行', 'CRCBANK', NULL, 118, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (396, 72, '长沙银行', 'CSCB', NULL, 119, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (397, 72, '常熟农村商业银行', 'CSRCB', NULL, 120, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (398, 72, '浙江稠州商业银行', 'CZCB', NULL, 121, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (399, 72, '常州农村信用联社', 'CZRCB', NULL, 122, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (400, 72, '龙江银行', 'DAQINGB', NULL, 123, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (401, 72, '大连银行', 'DLB', NULL, 124, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (402, 72, '东莞农村商业银行', 'DRCBCL', NULL, 125, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (403, 72, '德阳商业银行', 'DYCB', NULL, 126, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (404, 72, '东营市商业银行', 'DYCCB', NULL, 127, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (405, 72, '德州银行', 'DZBANK', NULL, 128, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (406, 72, '富滇银行', 'FDB', NULL, 129, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (407, 72, '福建海峡银行', 'FJHXBC', NULL, 130, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (408, 72, '抚顺银行', 'FSCB', NULL, 131, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (409, 72, '阜新银行', 'FXCB', NULL, 132, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (410, 72, '广州银行', 'GCB', NULL, 133, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (411, 72, '广东省农村信用社联合社', 'GDRCC', NULL, 134, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (412, 72, '桂林银行', 'GLBANK', NULL, 135, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (413, 72, '广州农商银行', 'GRCB', NULL, 136, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (414, 72, '甘肃省农村信用', 'GSRCU', NULL, 137, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (415, 72, '广西省农村信用', 'GXRCU', NULL, 138, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (416, 72, '贵阳市商业银行', 'GYCB', NULL, 139, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (417, 72, '赣州银行', 'GZB', NULL, 140, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (418, 72, '贵州省农村信用社', 'GZRCU', NULL, 141, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (419, 72, '内蒙古银行', 'H3CB', NULL, 142, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (420, 72, '韩亚银行', 'HANABANK', NULL, 143, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (421, 72, '湖北银行', 'HBC', NULL, 144, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (422, 72, '湖北银行黄石分行', 'HBHSBANK', NULL, 145, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (423, 72, '河北省农村信用社', 'HBRCU', NULL, 146, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (424, 72, '湖北银行宜昌分行', 'HBYCBANK', NULL, 147, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (425, 72, '邯郸银行', 'HDBANK', NULL, 148, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (426, 72, '汉口银行', 'HKB', NULL, 149, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (427, 72, '湖南省农村信用社', 'HNRCC', NULL, 150, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (428, 72, '河南省农村信用', 'HNRCU', NULL, 151, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (429, 72, '华融湘江银行', 'HRXJB', NULL, 152, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (430, 72, '徽商银行', 'HSBANK', NULL, 153, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (431, 72, '衡水银行', 'HSBK', NULL, 154, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (432, 72, '湖北省农村信用社', 'HURCB', NULL, 155, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (433, 72, '杭州银行', 'HZCB', NULL, 156, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (434, 72, '湖州市商业银行', 'HZCCB', NULL, 157, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (435, 72, '金华银行', 'JHBANK', NULL, 158, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (436, 72, '晋城银行 JCBANK', 'JINCHB', NULL, 159, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (437, 72, '九江银行', 'JJBANK', NULL, 160, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (438, 72, '吉林银行', 'JLBANK', NULL, 161, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (439, 72, '吉林农信', 'JLRCU', NULL, 162, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (440, 72, '济宁银行', 'JNBANK', NULL, 163, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (441, 72, '江苏江阴农村商业银行', 'JRCB', NULL, 164, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (442, 72, '晋商银行', 'JSB', NULL, 165, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (443, 72, '江苏银行', 'JSBANK', NULL, 166, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (444, 72, '江苏省农村信用联合社', 'JSRCU', NULL, 167, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (445, 72, '嘉兴银行', 'JXBANK', NULL, 168, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (446, 72, '江西省农村信用', 'JXRCU', NULL, 169, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (447, 72, '晋中市商业银行', 'JZBANK', NULL, 170, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (448, 72, '昆仑银行', 'KLB', NULL, 171, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (449, 72, '库尔勒市商业银行', 'KORLABANK', NULL, 172, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (450, 72, '昆山农村商业银行', 'KSRB', NULL, 173, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (451, 72, '廊坊银行', 'LANGFB', NULL, 174, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (452, 72, '莱商银行', 'LSBANK', NULL, 175, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (453, 72, '临商银行', 'LSBC', NULL, 176, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (454, 72, '乐山市商业银行', 'LSCCB', NULL, 177, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (455, 72, '洛阳银行', 'LYBANK', NULL, 178, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (456, 72, '辽阳市商业银行', 'LYCB', NULL, 179, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (457, 72, '兰州银行', 'LZYH', NULL, 180, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (458, 72, '浙江民泰商业银行', 'MTBANK', NULL, 181, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (459, 72, '宁波银行', 'NBBANK', NULL, 182, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (460, 72, '鄞州银行', 'NBYZ', NULL, 183, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (461, 72, '南昌银行', 'NCB', NULL, 184, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (462, 72, '南海农村信用联社', 'NHB', NULL, 185, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (463, 72, '农信银清算中心', 'NHQS', NULL, 186, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (464, 72, '南京银行', 'NJCB', NULL, 187, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (465, 72, '宁夏银行', 'NXBANK', NULL, 188, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (466, 72, '宁夏黄河农村商业银行', 'NXRCU', NULL, 189, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (467, 72, '广东南粤银行', 'NYNB', NULL, 190, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (468, 72, '鄂尔多斯银行', 'ORBANK', NULL, 191, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (469, 72, '中国邮政储蓄银行', 'PSBC', NULL, 192, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (470, 72, '青岛银行', 'QDCCB', NULL, 193, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (471, 72, '齐鲁银行', 'QLBANK', NULL, 194, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (472, 72, '三门峡银行', 'SCCB', NULL, 195, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (473, 72, '四川省农村信用', 'SCRCU', NULL, 196, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (474, 72, '顺德农商银行', 'SDEB', NULL, 197, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (475, 72, '山东农信', 'SDRCU', NULL, 198, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (476, 72, '上海银行', 'SHBANK', NULL, 199, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (477, 72, '上海农村商业银行', 'SHRCB', NULL, 200, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (478, 72, '盛京银行', 'SJBANK', NULL, 201, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (479, 72, '平安银行', 'SPABANK', NULL, 202, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (480, 72, '上饶银行', 'SRBANK', NULL, 203, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (481, 72, '深圳农村商业银行', 'SRCB', NULL, 204, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (482, 72, '绍兴银行', 'SXCB', NULL, 205, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (483, 72, '陕西信合', 'SXRCCU', NULL, 206, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (484, 72, '石嘴山银行', 'SZSBK', NULL, 207, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (485, 72, '泰安市商业银行', 'TACCB', NULL, 208, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (486, 72, '天津银行', 'TCCB', NULL, 209, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (487, 72, '江苏太仓农村商业银行', 'TCRCB', NULL, 210, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (488, 72, '天津农商银行', 'TRCB', NULL, 211, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (489, 72, '台州银行', 'TZCB', NULL, 212, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (490, 72, '乌鲁木齐市商业银行', 'URMQCCB', NULL, 213, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (491, 72, '威海市商业银行', 'WHCCB', NULL, 214, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (492, 72, '武汉农村商业银行', 'WHRCB', NULL, 215, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (493, 72, '吴江农商银行', 'WJRCB', NULL, 216, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (494, 72, '无锡农村商业银行', 'WRCB', NULL, 217, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (495, 72, '温州银行', 'WZCB', NULL, 218, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (496, 72, '西安银行', 'XABANK', NULL, 219, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (497, 72, '许昌银行', 'XCYH', NULL, 220, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (498, 72, '中山小榄村镇银行', 'XLBANK', NULL, 221, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (499, 72, '邢台银行', 'XTB', NULL, 222, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (500, 72, '新乡银行', 'XXBANK', NULL, 223, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (501, 72, '信阳银行', 'XYBANK', NULL, 224, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (502, 72, '宜宾市商业银行', 'YBCCB', NULL, 225, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (503, 72, '尧都农商行', 'YDRCB', NULL, 226, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (504, 72, '云南省农村信用社', 'YNRCC', NULL, 227, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (505, 72, '阳泉银行', 'YQCCB', NULL, 228, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (506, 72, '玉溪市商业银行', 'YXCCB', NULL, 229, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (507, 72, '齐商银行', 'ZBCB', NULL, 230, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (508, 72, '自贡市商业银行', 'ZGCCB', NULL, 231, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (509, 72, '张家口市商业银行', 'ZJKCCB', NULL, 232, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (510, 72, '浙江省农村信用社联合社', 'ZJNX', NULL, 233, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (511, 72, '浙江泰隆商业银行', 'ZJTLCB', NULL, 234, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (512, 72, '张家港农村商业银行', 'ZRCBANK', NULL, 235, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (513, 72, '遵义市商业银行', 'ZYCBANK', NULL, 236, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (514, 72, '郑州银行', 'ZZBANK', NULL, 237, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (515, 72, '其它', '000 ', NULL, 238, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (516, 72, '支付宝', '9997 ', NULL, 239, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (517, 72, '浙江网商银行股份有限公司', '9998', NULL, 240, 1, '2021-08-19 16:39:54', '2021-08-19 16:39:54');
INSERT INTO `sys_dict_item` VALUES (518, 73, '白名单', '1', '白名单', 1, 1, '2021-08-26 17:35:42', '2021-08-26 17:35:42');
INSERT INTO `sys_dict_item` VALUES (519, 73, '黑名单', '2', '黑名单', 2, 1, '2021-08-26 17:35:51', '2021-08-26 17:35:51');
INSERT INTO `sys_dict_item` VALUES (520, 73, '访客', '3', '访客', 3, 1, '2021-08-26 17:35:59', '2021-08-26 17:35:59');
INSERT INTO `sys_dict_item` VALUES (521, 73, '系统归档陌生人', '4', '系统归档陌生人', 4, 1, '2021-08-26 17:36:09', '2021-08-26 17:36:09');
INSERT INTO `sys_dict_item` VALUES (522, 74, '未生效', '0', '未生效', 1, 1, '2021-08-26 17:37:29', '2021-08-26 17:37:29');
INSERT INTO `sys_dict_item` VALUES (523, 74, '正常', '1', '正常', 2, 1, '2021-08-26 17:37:37', '2021-08-26 17:37:37');
INSERT INTO `sys_dict_item` VALUES (524, 74, '过期', '2', '过期', 3, 1, '2021-08-26 17:37:47', '2021-08-26 17:37:47');
INSERT INTO `sys_dict_item` VALUES (525, 75, '进', '1', '进', 1, 1, '2021-08-28 15:26:36', '2021-08-28 15:26:38');
INSERT INTO `sys_dict_item` VALUES (526, 75, '出', '2', '出', 2, 1, '2021-08-28 15:26:36', '2021-08-28 15:26:38');
INSERT INTO `sys_dict_item` VALUES (527, 75, '通用', '3', '通用', 3, 1, '2021-08-28 15:26:36', '2021-08-28 15:26:38');
INSERT INTO `sys_dict_item` VALUES (528, 76, '待处理', '1', '待处理', 1, 1, '2021-08-28 15:26:36', '2021-08-28 15:26:38');
INSERT INTO `sys_dict_item` VALUES (529, 76, '已忽略', '2', '忽略', 2, 1, '2021-08-28 15:26:36', '2021-08-28 15:26:36');
INSERT INTO `sys_dict_item` VALUES (530, 76, '已处理', '3', '已处理', 3, 1, '2021-08-28 15:26:36', '2021-08-28 15:26:36');
INSERT INTO `sys_dict_item` VALUES (531, 77, '图片', '1', '图片', 1, 1, '2021-08-28 15:26:36', '2021-08-28 15:26:36');
INSERT INTO `sys_dict_item` VALUES (532, 77, '视频', '2', '视频', 2, 1, '2021-08-28 15:26:36', '2021-08-28 15:26:36');
INSERT INTO `sys_dict_item` VALUES (533, 78, '不可进不可出', '1', '不可进不可出', 1, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (534, 78, '可进不可出', '2', '可进不可出', 2, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (535, 78, '可进可出', '3', '可进可出', 3, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (536, 79, '蓝色', '0', '蓝色', 1, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (537, 79, '黄色', '1', '黄色', 2, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (538, 79, '黑色', '2', '黑色', 3, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (539, 79, '白色', '3', '白色', 4, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (540, 79, '绿色', '4', '绿色', 5, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (541, 79, '未知', '5', '未知', 6, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (542, 80, '正常', '1', '正常', 1, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (543, 80, '挂失', '2', '挂失', 2, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (544, 80, '停用', '3', '停用', 3, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (545, 80, '注销', '4', '注销', 4, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (546, 81, '贵宾卡', '0', '贵宾卡', 1, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (547, 81, '储值卡', '1', '储值卡', 2, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (548, 81, '月卡', '2', '月卡', 3, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (549, 81, '临时卡', '3', '临时卡', 4, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (550, 81, '工作卡', '4', '工作卡', 5, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (551, 82, '过期转临停', '0', '过期转临停', 1, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (552, 82, '禁止进出', '1', '禁止进出', 2, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (553, 83, '转临停', '0', '转临', 1, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (554, 83, '禁止进出', '1', '禁止进出', 2, 1, '2021-09-18 10:59:02', '2021-09-18 10:59:02');
INSERT INTO `sys_dict_item` VALUES (560, 86, '固定排班', '1', '固定排班', 1, 1, '2021-10-26 14:32:54', '2021-10-26 14:32:54');
INSERT INTO `sys_dict_item` VALUES (561, 86, '轮班排班', '2', '轮班排班  ', 2, 1, '2021-10-26 14:33:07', '2021-10-26 14:33:07');
INSERT INTO `sys_dict_item` VALUES (562, 86, '无班次', '3', '无班次', 3, 1, '2021-10-26 14:33:18', '2021-10-26 14:33:18');
INSERT INTO `sys_dict_item` VALUES (563, 87, 'udp传输', 'UDP', 'UDP传输', 1, 1, '2021-11-01 17:48:11', '2021-11-01 17:48:11');
INSERT INTO `sys_dict_item` VALUES (564, 87, 'tcp主动模式', 'TCP-ACTIVE', 'TCP主动模式', 2, 1, '2021-11-01 17:48:11', '2021-11-01 17:48:11');
INSERT INTO `sys_dict_item` VALUES (565, 87, 'tcp被动模式', 'TCP-PASSIVE', 'TCP被动模式', 3, 1, '2021-11-01 17:48:11', '2021-11-01 17:48:11');
INSERT INTO `sys_dict_item` VALUES (566, 88, '离线', '0', '离线', 1, 1, '2021-11-01 17:48:11', '2021-11-01 17:48:11');
INSERT INTO `sys_dict_item` VALUES (567, 88, '在线', '1', '在线', 2, 1, '2021-11-01 17:48:11', '2021-11-01 17:48:11');
INSERT INTO `sys_dict_item` VALUES (568, 89, '设备', '0', '设备', 1, 1, '2021-11-02 09:32:35', '2021-11-02 09:32:35');
INSERT INTO `sys_dict_item` VALUES (569, 89, '子目录', '1', '子目录', 2, 1, '2021-11-02 09:32:35', '2021-11-02 09:32:35');
INSERT INTO `sys_dict_item` VALUES (570, 90, '未知', '0', '未知', 1, 1, '2021-11-02 10:11:50', '2021-11-02 10:11:50');
INSERT INTO `sys_dict_item` VALUES (571, 90, '球机', '1', '球机', 2, 1, '2021-11-02 10:11:50', '2021-11-02 10:11:50');
INSERT INTO `sys_dict_item` VALUES (572, 90, '半球', '2', '半球', 3, 1, '2021-11-02 10:11:50', '2021-11-02 10:11:50');
INSERT INTO `sys_dict_item` VALUES (573, 90, '固定枪机', '3', '固定枪机', 4, 1, '2021-11-02 10:11:50', '2021-11-02 10:11:50');
INSERT INTO `sys_dict_item` VALUES (574, 90, '遥控枪机', '4', '遥控枪机', 5, 1, '2021-11-02 10:11:50', '2021-11-02 10:11:50');
INSERT INTO `sys_dict_item` VALUES (575, 91, '已启用', '1', '已启用', 2, 1, '2021-11-02 10:11:50', '2021-11-02 10:11:50');
INSERT INTO `sys_dict_item` VALUES (576, 91, '未启用', '0', '未启用', 1, 1, '2021-11-02 10:11:50', '2021-11-02 10:11:50');
INSERT INTO `sys_dict_item` VALUES (577, 92, 'UDP', 'UDP', 'UDP', 1, 1, '2021-11-02 10:11:50', '2021-11-02 10:11:50');
INSERT INTO `sys_dict_item` VALUES (578, 92, 'TCP', 'TCP', 'TCP', 2, 1, '2021-11-02 10:11:50', '2021-11-02 10:11:50');
INSERT INTO `sys_dict_item` VALUES (579, 93, '已选择', '1', '已选择', 2, 1, '2021-11-05 15:32:09', '2021-11-05 15:32:09');
INSERT INTO `sys_dict_item` VALUES (580, 93, '未选择', '0', '未选择', 1, 1, '2021-11-05 15:32:09', '2021-11-05 15:32:09');
INSERT INTO `sys_dict_item` VALUES (581, 94, '拉流代理', 'proxy', '拉流代理', 1, 1, '2021-11-05 15:32:09', '2021-11-05 15:32:09');
INSERT INTO `sys_dict_item` VALUES (582, 94, '推流', 'push', '推流', 2, 1, '2021-11-05 15:32:09', '2021-11-05 15:32:09');
INSERT INTO `sys_dict_item` VALUES (583, 95, '否', '0', '否', 1, 1, '2021-11-05 15:32:09', '2021-11-05 15:32:09');
INSERT INTO `sys_dict_item` VALUES (584, 95, '是', '1', '是', 2, 1, '2021-11-05 15:32:09', '2021-11-05 15:32:09');
INSERT INTO `sys_dict_item` VALUES (585, 96, 'mysql', '1', '', 1, 1, '2021-11-10 16:16:20', '2021-11-10 16:16:20');
INSERT INTO `sys_dict_item` VALUES (586, 96, 'postgresql', '2', '', 2, 1, '2021-11-10 16:16:36', '2021-11-10 16:16:36');
INSERT INTO `sys_dict_item` VALUES (587, 96, 'hive', '3', '', 3, 1, '2021-11-10 16:16:48', '2021-11-10 16:16:48');
INSERT INTO `sys_dict_item` VALUES (588, 96, 'sqlserver', '4', '', 4, 1, '2021-11-10 16:17:10', '2021-11-10 16:17:10');
INSERT INTO `sys_dict_item` VALUES (589, 96, 'clickhouse', '5', '', 5, 1, '2021-11-10 16:17:21', '2021-11-10 16:17:21');
INSERT INTO `sys_dict_item` VALUES (590, 96, 'kylin', '6', '', 6, 1, '2021-11-10 16:17:30', '2021-11-10 16:17:30');
INSERT INTO `sys_dict_item` VALUES (591, 96, 'oracle', '7', '', 7, 1, '2021-11-10 16:17:43', '2021-11-10 16:17:43');

-- ----------------------------
-- Table structure for sys_mapping_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_mapping_info`;
CREATE TABLE `sys_mapping_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_mapping_info
-- ----------------------------
INSERT INTO `sys_mapping_info` VALUES (1, 'tenant_url', 'http://47.99.186.225:7037', '2022-01-04 09:25:42');

-- ----------------------------
-- Table structure for sys_online_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_online_user`;
CREATE TABLE `sys_online_user`  (
  `id` bigint(20) NULL DEFAULT NULL,
  `user_login_info_id` bigint(20) NULL DEFAULT NULL COMMENT '登录用户信息id',
  `sys_user_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录token',
  `login_user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  UNIQUE INDEX `sys_user_token`(`sys_user_token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_online_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_page_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_page_api`;
CREATE TABLE `sys_page_api`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_api_id` int(11) NULL DEFAULT NULL COMMENT '接口id',
  `sys_page_id` int(11) NULL DEFAULT NULL COMMENT '页面id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1149 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_page_api
-- ----------------------------
INSERT INTO `sys_page_api` VALUES (1, 1, 73);
INSERT INTO `sys_page_api` VALUES (2, 2, 33);
INSERT INTO `sys_page_api` VALUES (3, 3, 32);
INSERT INTO `sys_page_api` VALUES (4, 4, 34);
INSERT INTO `sys_page_api` VALUES (5, 5, 35);
INSERT INTO `sys_page_api` VALUES (6, 6, 36);
INSERT INTO `sys_page_api` VALUES (7, 7, 56);
INSERT INTO `sys_page_api` VALUES (8, 8, 37);
INSERT INTO `sys_page_api` VALUES (9, 9, 38);
INSERT INTO `sys_page_api` VALUES (10, 10, 39);
INSERT INTO `sys_page_api` VALUES (11, 11, 57);
INSERT INTO `sys_page_api` VALUES (12, 12, 1);
INSERT INTO `sys_page_api` VALUES (13, 13, 1);
INSERT INTO `sys_page_api` VALUES (14, 14, 50);
INSERT INTO `sys_page_api` VALUES (15, 15, 58);
INSERT INTO `sys_page_api` VALUES (16, 16, 48);
INSERT INTO `sys_page_api` VALUES (17, 17, 49);
INSERT INTO `sys_page_api` VALUES (18, 21, 46);
INSERT INTO `sys_page_api` VALUES (19, 22, 42);
INSERT INTO `sys_page_api` VALUES (20, 23, 60);
INSERT INTO `sys_page_api` VALUES (21, 24, 61);
INSERT INTO `sys_page_api` VALUES (22, 25, 47);
INSERT INTO `sys_page_api` VALUES (23, 26, 63);
INSERT INTO `sys_page_api` VALUES (24, 27, 40);
INSERT INTO `sys_page_api` VALUES (25, 28, 40);
INSERT INTO `sys_page_api` VALUES (26, 29, 41);
INSERT INTO `sys_page_api` VALUES (27, 30, 59);
INSERT INTO `sys_page_api` VALUES (30, 41, 1);
INSERT INTO `sys_page_api` VALUES (31, 42, 44);
INSERT INTO `sys_page_api` VALUES (32, 43, 31);
INSERT INTO `sys_page_api` VALUES (33, 44, 43);
INSERT INTO `sys_page_api` VALUES (34, 45, 45);
INSERT INTO `sys_page_api` VALUES (35, 46, 53);
INSERT INTO `sys_page_api` VALUES (36, 47, 52);
INSERT INTO `sys_page_api` VALUES (37, 48, 54);
INSERT INTO `sys_page_api` VALUES (38, 49, 55);
INSERT INTO `sys_page_api` VALUES (39, 50, 65);
INSERT INTO `sys_page_api` VALUES (40, 51, 67);
INSERT INTO `sys_page_api` VALUES (41, 52, 66);
INSERT INTO `sys_page_api` VALUES (42, 53, 68);
INSERT INTO `sys_page_api` VALUES (43, 54, 107);
INSERT INTO `sys_page_api` VALUES (44, 55, 74);
INSERT INTO `sys_page_api` VALUES (45, 56, 3);
INSERT INTO `sys_page_api` VALUES (46, 57, 76);
INSERT INTO `sys_page_api` VALUES (47, 58, 77);
INSERT INTO `sys_page_api` VALUES (48, 59, 79);
INSERT INTO `sys_page_api` VALUES (49, 60, 78);
INSERT INTO `sys_page_api` VALUES (50, 61, 80);
INSERT INTO `sys_page_api` VALUES (51, 62, 81);
INSERT INTO `sys_page_api` VALUES (52, 63, 82);
INSERT INTO `sys_page_api` VALUES (53, 64, 83);
INSERT INTO `sys_page_api` VALUES (54, 65, 84);
INSERT INTO `sys_page_api` VALUES (55, 66, 85);
INSERT INTO `sys_page_api` VALUES (56, 67, 86);
INSERT INTO `sys_page_api` VALUES (57, 68, 87);
INSERT INTO `sys_page_api` VALUES (58, 69, 88);
INSERT INTO `sys_page_api` VALUES (59, 70, 89);
INSERT INTO `sys_page_api` VALUES (60, 71, 90);
INSERT INTO `sys_page_api` VALUES (61, 72, 91);
INSERT INTO `sys_page_api` VALUES (62, 73, 2);
INSERT INTO `sys_page_api` VALUES (63, 75, 16);
INSERT INTO `sys_page_api` VALUES (64, 76, 16);
INSERT INTO `sys_page_api` VALUES (65, 77, 16);
INSERT INTO `sys_page_api` VALUES (66, 78, 16);
INSERT INTO `sys_page_api` VALUES (67, 79, 16);
INSERT INTO `sys_page_api` VALUES (68, 80, 16);
INSERT INTO `sys_page_api` VALUES (69, 81, 16);
INSERT INTO `sys_page_api` VALUES (70, 82, 16);
INSERT INTO `sys_page_api` VALUES (71, 83, 16);
INSERT INTO `sys_page_api` VALUES (72, 84, 16);
INSERT INTO `sys_page_api` VALUES (73, 85, 16);
INSERT INTO `sys_page_api` VALUES (74, 86, 16);
INSERT INTO `sys_page_api` VALUES (75, 87, 16);
INSERT INTO `sys_page_api` VALUES (76, 88, 16);
INSERT INTO `sys_page_api` VALUES (77, 89, 16);
INSERT INTO `sys_page_api` VALUES (78, 90, 92);
INSERT INTO `sys_page_api` VALUES (79, 91, 93);
INSERT INTO `sys_page_api` VALUES (80, 92, 94);
INSERT INTO `sys_page_api` VALUES (81, 93, 95);
INSERT INTO `sys_page_api` VALUES (82, 94, 96);
INSERT INTO `sys_page_api` VALUES (83, 95, 97);
INSERT INTO `sys_page_api` VALUES (84, 96, 98);
INSERT INTO `sys_page_api` VALUES (85, 97, 99);
INSERT INTO `sys_page_api` VALUES (86, 98, 100);
INSERT INTO `sys_page_api` VALUES (87, 99, 101);
INSERT INTO `sys_page_api` VALUES (88, 100, 102);
INSERT INTO `sys_page_api` VALUES (89, 101, 103);
INSERT INTO `sys_page_api` VALUES (90, 102, 104);
INSERT INTO `sys_page_api` VALUES (91, 103, 105);
INSERT INTO `sys_page_api` VALUES (92, 104, 106);
INSERT INTO `sys_page_api` VALUES (93, 105, 110);
INSERT INTO `sys_page_api` VALUES (94, 106, 111);
INSERT INTO `sys_page_api` VALUES (95, 107, 112);
INSERT INTO `sys_page_api` VALUES (96, 108, 113);
INSERT INTO `sys_page_api` VALUES (97, 109, 114);
INSERT INTO `sys_page_api` VALUES (98, 110, 115);
INSERT INTO `sys_page_api` VALUES (99, 111, 116);
INSERT INTO `sys_page_api` VALUES (100, 112, 117);
INSERT INTO `sys_page_api` VALUES (101, 113, 118);
INSERT INTO `sys_page_api` VALUES (102, 114, 119);
INSERT INTO `sys_page_api` VALUES (103, 115, 120);
INSERT INTO `sys_page_api` VALUES (104, 116, 121);
INSERT INTO `sys_page_api` VALUES (105, 117, 122);
INSERT INTO `sys_page_api` VALUES (106, 118, 123);
INSERT INTO `sys_page_api` VALUES (107, 119, 124);
INSERT INTO `sys_page_api` VALUES (108, 120, 125);
INSERT INTO `sys_page_api` VALUES (109, 121, 126);
INSERT INTO `sys_page_api` VALUES (110, 122, 127);
INSERT INTO `sys_page_api` VALUES (111, 123, 128);
INSERT INTO `sys_page_api` VALUES (112, 124, 129);
INSERT INTO `sys_page_api` VALUES (113, 125, 130);
INSERT INTO `sys_page_api` VALUES (114, 126, 131);
INSERT INTO `sys_page_api` VALUES (115, 127, 132);
INSERT INTO `sys_page_api` VALUES (116, 128, 133);
INSERT INTO `sys_page_api` VALUES (117, 129, 134);
INSERT INTO `sys_page_api` VALUES (118, 130, 135);
INSERT INTO `sys_page_api` VALUES (119, 131, 136);
INSERT INTO `sys_page_api` VALUES (120, 132, 137);
INSERT INTO `sys_page_api` VALUES (121, 133, 138);
INSERT INTO `sys_page_api` VALUES (122, 134, 139);
INSERT INTO `sys_page_api` VALUES (123, 135, 140);
INSERT INTO `sys_page_api` VALUES (124, 136, 141);
INSERT INTO `sys_page_api` VALUES (125, 137, 142);
INSERT INTO `sys_page_api` VALUES (126, 138, 143);
INSERT INTO `sys_page_api` VALUES (127, 139, 144);
INSERT INTO `sys_page_api` VALUES (128, 140, 145);
INSERT INTO `sys_page_api` VALUES (129, 141, 146);
INSERT INTO `sys_page_api` VALUES (130, 142, 147);
INSERT INTO `sys_page_api` VALUES (131, 143, 148);
INSERT INTO `sys_page_api` VALUES (132, 144, 149);
INSERT INTO `sys_page_api` VALUES (133, 145, 150);
INSERT INTO `sys_page_api` VALUES (134, 146, 151);
INSERT INTO `sys_page_api` VALUES (135, 147, 152);
INSERT INTO `sys_page_api` VALUES (136, 148, 153);
INSERT INTO `sys_page_api` VALUES (137, 149, 154);
INSERT INTO `sys_page_api` VALUES (138, 150, 155);
INSERT INTO `sys_page_api` VALUES (139, 151, 156);
INSERT INTO `sys_page_api` VALUES (140, 152, 157);
INSERT INTO `sys_page_api` VALUES (141, 153, 158);
INSERT INTO `sys_page_api` VALUES (142, 154, 159);
INSERT INTO `sys_page_api` VALUES (143, 155, 160);
INSERT INTO `sys_page_api` VALUES (144, 156, 161);
INSERT INTO `sys_page_api` VALUES (152, 164, 169);
INSERT INTO `sys_page_api` VALUES (153, 165, 170);
INSERT INTO `sys_page_api` VALUES (154, 166, 1);
INSERT INTO `sys_page_api` VALUES (155, 167, 173);
INSERT INTO `sys_page_api` VALUES (156, 168, 174);
INSERT INTO `sys_page_api` VALUES (157, 169, 175);
INSERT INTO `sys_page_api` VALUES (158, 170, 176);
INSERT INTO `sys_page_api` VALUES (159, 171, 177);
INSERT INTO `sys_page_api` VALUES (160, 172, 178);
INSERT INTO `sys_page_api` VALUES (161, 173, 179);
INSERT INTO `sys_page_api` VALUES (162, 174, 180);
INSERT INTO `sys_page_api` VALUES (163, 175, 181);
INSERT INTO `sys_page_api` VALUES (164, 176, 182);
INSERT INTO `sys_page_api` VALUES (165, 177, 183);
INSERT INTO `sys_page_api` VALUES (166, 178, 184);
INSERT INTO `sys_page_api` VALUES (167, 179, 185);
INSERT INTO `sys_page_api` VALUES (168, 180, 186);
INSERT INTO `sys_page_api` VALUES (169, 181, 187);
INSERT INTO `sys_page_api` VALUES (170, 182, 188);
INSERT INTO `sys_page_api` VALUES (171, 183, 189);
INSERT INTO `sys_page_api` VALUES (172, 184, 190);
INSERT INTO `sys_page_api` VALUES (173, 185, 191);
INSERT INTO `sys_page_api` VALUES (174, 186, 192);
INSERT INTO `sys_page_api` VALUES (175, 187, 193);
INSERT INTO `sys_page_api` VALUES (176, 188, 194);
INSERT INTO `sys_page_api` VALUES (177, 189, 195);
INSERT INTO `sys_page_api` VALUES (178, 190, 196);
INSERT INTO `sys_page_api` VALUES (179, 191, 197);
INSERT INTO `sys_page_api` VALUES (180, 192, 198);
INSERT INTO `sys_page_api` VALUES (181, 193, 199);
INSERT INTO `sys_page_api` VALUES (182, 194, 200);
INSERT INTO `sys_page_api` VALUES (183, 195, 201);
INSERT INTO `sys_page_api` VALUES (184, 196, 202);
INSERT INTO `sys_page_api` VALUES (185, 197, 203);
INSERT INTO `sys_page_api` VALUES (186, 198, 204);
INSERT INTO `sys_page_api` VALUES (187, 199, 205);
INSERT INTO `sys_page_api` VALUES (188, 200, 206);
INSERT INTO `sys_page_api` VALUES (189, 201, 207);
INSERT INTO `sys_page_api` VALUES (190, 202, 208);
INSERT INTO `sys_page_api` VALUES (191, 203, 209);
INSERT INTO `sys_page_api` VALUES (192, 204, 210);
INSERT INTO `sys_page_api` VALUES (193, 205, 211);
INSERT INTO `sys_page_api` VALUES (194, 206, 212);
INSERT INTO `sys_page_api` VALUES (195, 207, 213);
INSERT INTO `sys_page_api` VALUES (196, 208, 214);
INSERT INTO `sys_page_api` VALUES (197, 209, 215);
INSERT INTO `sys_page_api` VALUES (198, 210, 216);
INSERT INTO `sys_page_api` VALUES (199, 211, 217);
INSERT INTO `sys_page_api` VALUES (200, 212, 218);
INSERT INTO `sys_page_api` VALUES (201, 213, 219);
INSERT INTO `sys_page_api` VALUES (202, 214, 220);
INSERT INTO `sys_page_api` VALUES (203, 215, 221);
INSERT INTO `sys_page_api` VALUES (204, 216, 222);
INSERT INTO `sys_page_api` VALUES (205, 217, 223);
INSERT INTO `sys_page_api` VALUES (206, 218, 224);
INSERT INTO `sys_page_api` VALUES (207, 219, 225);
INSERT INTO `sys_page_api` VALUES (208, 220, 226);
INSERT INTO `sys_page_api` VALUES (209, 221, 227);
INSERT INTO `sys_page_api` VALUES (210, 222, 228);
INSERT INTO `sys_page_api` VALUES (211, 223, 229);
INSERT INTO `sys_page_api` VALUES (212, 224, 230);
INSERT INTO `sys_page_api` VALUES (213, 225, 231);
INSERT INTO `sys_page_api` VALUES (214, 226, 232);
INSERT INTO `sys_page_api` VALUES (215, 227, 233);
INSERT INTO `sys_page_api` VALUES (216, 228, 234);
INSERT INTO `sys_page_api` VALUES (217, 229, 235);
INSERT INTO `sys_page_api` VALUES (218, 230, 236);
INSERT INTO `sys_page_api` VALUES (219, 231, 237);
INSERT INTO `sys_page_api` VALUES (220, 232, 238);
INSERT INTO `sys_page_api` VALUES (221, 233, 1);
INSERT INTO `sys_page_api` VALUES (224, 236, 283);
INSERT INTO `sys_page_api` VALUES (225, 237, 1);
INSERT INTO `sys_page_api` VALUES (226, 238, 289);
INSERT INTO `sys_page_api` VALUES (227, 239, 290);
INSERT INTO `sys_page_api` VALUES (228, 240, 291);
INSERT INTO `sys_page_api` VALUES (229, 241, 292);
INSERT INTO `sys_page_api` VALUES (230, 242, 293);
INSERT INTO `sys_page_api` VALUES (231, 243, 294);
INSERT INTO `sys_page_api` VALUES (232, 244, 295);
INSERT INTO `sys_page_api` VALUES (233, 245, 296);
INSERT INTO `sys_page_api` VALUES (234, 246, 297);
INSERT INTO `sys_page_api` VALUES (235, 247, 298);
INSERT INTO `sys_page_api` VALUES (236, 248, 299);
INSERT INTO `sys_page_api` VALUES (237, 249, 300);
INSERT INTO `sys_page_api` VALUES (238, 250, 301);
INSERT INTO `sys_page_api` VALUES (239, 251, 302);
INSERT INTO `sys_page_api` VALUES (240, 252, 303);
INSERT INTO `sys_page_api` VALUES (241, 253, 304);
INSERT INTO `sys_page_api` VALUES (242, 254, 305);
INSERT INTO `sys_page_api` VALUES (243, 255, 306);
INSERT INTO `sys_page_api` VALUES (244, 256, 307);
INSERT INTO `sys_page_api` VALUES (245, 257, 308);
INSERT INTO `sys_page_api` VALUES (246, 258, 309);
INSERT INTO `sys_page_api` VALUES (247, 259, 310);
INSERT INTO `sys_page_api` VALUES (248, 260, 312);
INSERT INTO `sys_page_api` VALUES (249, 261, 313);
INSERT INTO `sys_page_api` VALUES (252, 267, 326);
INSERT INTO `sys_page_api` VALUES (253, 268, 330);
INSERT INTO `sys_page_api` VALUES (254, 269, 1);
INSERT INTO `sys_page_api` VALUES (255, 270, 332);
INSERT INTO `sys_page_api` VALUES (256, 271, 333);
INSERT INTO `sys_page_api` VALUES (257, 272, 334);
INSERT INTO `sys_page_api` VALUES (258, 273, 335);
INSERT INTO `sys_page_api` VALUES (259, 274, 327);
INSERT INTO `sys_page_api` VALUES (260, 275, 328);
INSERT INTO `sys_page_api` VALUES (266, 281, 346);
INSERT INTO `sys_page_api` VALUES (267, 282, 347);
INSERT INTO `sys_page_api` VALUES (268, 283, 348);
INSERT INTO `sys_page_api` VALUES (269, 284, 349);
INSERT INTO `sys_page_api` VALUES (270, 285, 350);
INSERT INTO `sys_page_api` VALUES (271, 286, 351);
INSERT INTO `sys_page_api` VALUES (315, 330, 430);
INSERT INTO `sys_page_api` VALUES (316, 331, 431);
INSERT INTO `sys_page_api` VALUES (317, 332, 432);
INSERT INTO `sys_page_api` VALUES (318, 333, 433);
INSERT INTO `sys_page_api` VALUES (328, 343, 444);
INSERT INTO `sys_page_api` VALUES (329, 344, 445);
INSERT INTO `sys_page_api` VALUES (330, 345, 446);
INSERT INTO `sys_page_api` VALUES (331, 346, 447);
INSERT INTO `sys_page_api` VALUES (332, 347, 448);
INSERT INTO `sys_page_api` VALUES (333, 348, 449);
INSERT INTO `sys_page_api` VALUES (334, 349, 450);
INSERT INTO `sys_page_api` VALUES (335, 350, 451);
INSERT INTO `sys_page_api` VALUES (336, 351, 452);
INSERT INTO `sys_page_api` VALUES (337, 352, 453);
INSERT INTO `sys_page_api` VALUES (338, 353, 456);
INSERT INTO `sys_page_api` VALUES (339, 354, 459);
INSERT INTO `sys_page_api` VALUES (340, 355, 460);
INSERT INTO `sys_page_api` VALUES (341, 356, 461);
INSERT INTO `sys_page_api` VALUES (342, 357, 462);
INSERT INTO `sys_page_api` VALUES (343, 358, 463);
INSERT INTO `sys_page_api` VALUES (344, 359, 464);
INSERT INTO `sys_page_api` VALUES (345, 360, 465);
INSERT INTO `sys_page_api` VALUES (346, 361, 1);
INSERT INTO `sys_page_api` VALUES (347, 362, 466);
INSERT INTO `sys_page_api` VALUES (348, 363, 467);
INSERT INTO `sys_page_api` VALUES (349, 364, 468);
INSERT INTO `sys_page_api` VALUES (350, 365, 469);
INSERT INTO `sys_page_api` VALUES (351, 366, 470);
INSERT INTO `sys_page_api` VALUES (352, 367, 471);
INSERT INTO `sys_page_api` VALUES (353, 368, 1);
INSERT INTO `sys_page_api` VALUES (354, 369, 1);
INSERT INTO `sys_page_api` VALUES (356, 371, 473);
INSERT INTO `sys_page_api` VALUES (357, 372, 1);
INSERT INTO `sys_page_api` VALUES (362, 377, 1);
INSERT INTO `sys_page_api` VALUES (436, 451, 555);
INSERT INTO `sys_page_api` VALUES (710, 725, 1);
INSERT INTO `sys_page_api` VALUES (823, 838, 1002);
INSERT INTO `sys_page_api` VALUES (824, 839, 1003);
INSERT INTO `sys_page_api` VALUES (825, 840, 1004);
INSERT INTO `sys_page_api` VALUES (826, 841, 1005);
INSERT INTO `sys_page_api` VALUES (922, 937, 1);
INSERT INTO `sys_page_api` VALUES (924, 939, 1136);
INSERT INTO `sys_page_api` VALUES (925, 940, 1137);
INSERT INTO `sys_page_api` VALUES (926, 941, 1138);
INSERT INTO `sys_page_api` VALUES (928, 943, 1);
INSERT INTO `sys_page_api` VALUES (929, 944, 1140);
INSERT INTO `sys_page_api` VALUES (930, 945, 1141);
INSERT INTO `sys_page_api` VALUES (931, 946, 1142);
INSERT INTO `sys_page_api` VALUES (932, 947, 1143);
INSERT INTO `sys_page_api` VALUES (985, 1000, 1196);
INSERT INTO `sys_page_api` VALUES (986, 1001, 1197);
INSERT INTO `sys_page_api` VALUES (998, 1013, 1209);
INSERT INTO `sys_page_api` VALUES (999, 1014, 1210);
INSERT INTO `sys_page_api` VALUES (1000, 1015, 1211);
INSERT INTO `sys_page_api` VALUES (1001, 1016, 1212);
INSERT INTO `sys_page_api` VALUES (1002, 1017, 1213);
INSERT INTO `sys_page_api` VALUES (1075, 1090, 1287);
INSERT INTO `sys_page_api` VALUES (1076, 1091, 1288);
INSERT INTO `sys_page_api` VALUES (1077, 1092, 1289);
INSERT INTO `sys_page_api` VALUES (1078, 1093, 1290);
INSERT INTO `sys_page_api` VALUES (1079, 1094, 1291);
INSERT INTO `sys_page_api` VALUES (1080, 1095, 1292);
INSERT INTO `sys_page_api` VALUES (1081, 1096, 1293);
INSERT INTO `sys_page_api` VALUES (1082, 1097, 1294);
INSERT INTO `sys_page_api` VALUES (1083, 1098, 1295);
INSERT INTO `sys_page_api` VALUES (1084, 1099, 1296);
INSERT INTO `sys_page_api` VALUES (1085, 1100, 1297);
INSERT INTO `sys_page_api` VALUES (1125, 1140, 1327);
INSERT INTO `sys_page_api` VALUES (1126, 1141, 1328);
INSERT INTO `sys_page_api` VALUES (1127, 1142, 1329);
INSERT INTO `sys_page_api` VALUES (1144, 265, 318);
INSERT INTO `sys_page_api` VALUES (1145, 264, 316);
INSERT INTO `sys_page_api` VALUES (1146, 263, 317);
INSERT INTO `sys_page_api` VALUES (1147, 235, 1299);
INSERT INTO `sys_page_api` VALUES (1148, 234, 1298);

-- ----------------------------
-- Table structure for sys_page_data_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_page_data_config`;
CREATE TABLE `sys_page_data_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `page_id` int(11) NULL DEFAULT NULL COMMENT '页面id',
  `data_prefix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据前缀中文名',
  `data_suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后缀单位值',
  `data_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '唯一标识码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_page_data_config
-- ----------------------------
INSERT INTO `sys_page_data_config` VALUES (1, 13, '反馈问题', '个', 'numOfProblems', '2021-09-14 16:51:40');
INSERT INTO `sys_page_data_config` VALUES (2, 14, '已解决', '个', 'numOfSolved', '2021-09-14 16:52:10');
INSERT INTO `sys_page_data_config` VALUES (3, 12, '页面资源', '页', 'numOfPages', '2021-09-14 16:54:04');

-- ----------------------------
-- Table structure for sys_page_module_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_page_module_type`;
CREATE TABLE `sys_page_module_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面模块类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_page_module_type
-- ----------------------------
INSERT INTO `sys_page_module_type` VALUES (1, '平台配置');

-- ----------------------------
-- Table structure for sys_page_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_page_permission`;
CREATE TABLE `sys_page_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '页面资源ID',
  `parent_page_id` int(11) NULL DEFAULT NULL COMMENT '父页面资源ID',
  `page_title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面资源标题',
  `page_url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面资源路径',
  `page_redirect` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面重定向地址',
  `page_type` int(11) NOT NULL COMMENT '页面资源类型：0-一级菜单;1-子菜单;2-接口权限',
  `page_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面资源编码',
  `page_show` int(1) NOT NULL COMMENT '页面资源是否可见权限设置:0-不可见:1-可见',
  `page_leaf_flag` int(1) NOT NULL COMMENT '页面资源是否为叶子节点:0-不是;1-是',
  `page_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '页面资源描述',
  `create_by` int(11) NOT NULL COMMENT '页面资源创建人ID',
  `create_time` datetime(0) NOT NULL COMMENT '页面资源创建时间',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '页面资源更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '页面资源更新时间',
  `page_delete_status` int(1) NOT NULL COMMENT '页面资源删除状态 0-已删除;1-未删除',
  `page_status` int(11) NOT NULL COMMENT '页面资源状态:0-未激活:1-正常;2-过期',
  `ability_type` int(1) NULL DEFAULT NULL COMMENT '页面类型 0增 1删 2改 3查',
  `sort` int(11) NOT NULL COMMENT '排序',
  `sys_page_module_type_id` int(1) NOT NULL COMMENT '页面模块类型id 取sys_page_module_type的id',
  `visible_to_all` int(1) NOT NULL COMMENT '是否全员可见 0-否 1-是',
  `is_jump` int(1) NULL DEFAULT 0 COMMENT '是否跳转外部应用 0-否 1-是',
  `page_icon` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `page_json` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '自动生成json',
  `is_auto` int(1) NULL DEFAULT NULL COMMENT '是否自动生成',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1347 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_page_permission
-- ----------------------------
INSERT INTO `sys_page_permission` VALUES (1, 0, '首页', '/welcome', 'welcome', 0, '/welcome', 0, 0, '首页', 1, '2021-03-15 14:18:04', 78, '2021-10-27 15:08:12', 1, 1, NULL, 1, 1, 1, 0, 'iconhuaban10', '', 0);
INSERT INTO `sys_page_permission` VALUES (2, 0, '账户管理', 'system', 'roleManage', 0, 'system', 1, 0, '账户管理', 1, '2021-03-15 14:18:04', 78, '2021-10-27 15:08:52', 1, 1, NULL, 2, 1, 0, 0, 'iconhuaban3', '', 0);
INSERT INTO `sys_page_permission` VALUES (3, 0, '账号设置', '/accountSetting', 'accountSetting', 0, '/accountSetting', 1, 0, '账号设置', 1, '2021-03-08 18:53:00', 1, '2021-10-27 15:42:52', 1, 1, NULL, 3, 1, 1, 0, 'iconzhanghaoshezhi_huaban1', '', 0);
INSERT INTO `sys_page_permission` VALUES (4, 0, '通告管理', '/noticeManage', 'noticeManage', 0, '/noticeManage', 1, 0, '通告管理', 1, '2021-03-08 18:53:00', 1, '2021-10-27 15:43:22', 1, 1, NULL, 4, 1, 0, 0, 'iconhuaban11', '', 0);
INSERT INTO `sys_page_permission` VALUES (5, 0, '网络配置', '/networkConfiguration', 'networkConfiguration', 0, '/networkConfiguration', 1, 0, '网络配置', 1, '2021-03-08 18:53:00', 1, '2021-10-27 15:46:48', 1, 1, NULL, 5, 1, 0, 0, 'iconhuaban6', '', 0);
INSERT INTO `sys_page_permission` VALUES (6, 0, '系统监控', '/systemMonitoring', 'systemMonitoring', 0, '/systemMonitoring', 1, 0, '系统监控', 1, '2021-03-08 18:53:00', 1, '2021-10-27 15:47:26', 1, 1, NULL, 6, 1, 0, 0, 'iconhuaban5', '', 0);
INSERT INTO `sys_page_permission` VALUES (7, 0, '系统维护', '/systemMaintenance', 'timeAllocation', 0, '/systemMaintenance', 1, 0, '系统维护', 1, '2021-03-08 18:53:00', 1, '2021-07-21 15:30:22', 1, 1, NULL, 7, 1, 0, 0, 'iconhuaban7', NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (8, 0, '安全管理', '/safetyManage', 'safetyManage', 0, '/safetyManage', 1, 0, '安全管理', 1, '2021-03-08 18:53:00', 1, '2021-07-21 15:30:56', 1, 1, NULL, 8, 1, 0, 0, 'iconhuaban4', NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (9, 9, '数据服务', '/advanconfig', 'advanconfig', 1, '/advanconfig', 1, 1, '高级配置', 1, '2021-03-08 18:53:00', 1, '2021-04-20 13:51:31', 1, 1, NULL, 9, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (10, 0, '平台配置', '/platformConfiguration', 'styleRelated', 0, '/platformConfiguration', 1, 0, '平台配置', 1, '2021-03-08 18:53:00', 1, '2021-07-21 15:31:58', 1, 1, NULL, 10, 1, 0, 0, 'iconhuaban12', NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (11, 0, '消息中心', '/messageCenter', 'messManage', 0, '/messageCenter', 1, 0, '消息中心', 1, '2021-03-08 18:53:00', 1, '2021-11-11 14:42:53', 1, 1, NULL, 11, 1, 1, 0, 'icona-8', '', 0);
INSERT INTO `sys_page_permission` VALUES (12, 0, '资源管理', '/resource', 'managePage', 0, '/resource', 1, 0, '资源管理', 1, '2021-03-08 18:53:00', 1, '2021-10-27 15:49:45', 1, 1, NULL, 12, 1, 0, 0, 'iconhuaban23', '', 0);
INSERT INTO `sys_page_permission` VALUES (13, 0, '问题反馈', '/messageCenter/problemFeedback', 'problemFeedback', 0, '/messageCenter/problemFeedback', 1, 0, '问题反馈', 1, '2021-03-12 18:11:18', 1, '2021-11-11 14:34:16', 1, 1, NULL, 13, 1, 1, 0, 'iconhuaban9', '', 0);
INSERT INTO `sys_page_permission` VALUES (14, 0, '反馈处理', '/messageCenter/processStunt', 'processStunt', 0, '/messageCenter/processStunt', 1, 0, '反馈处理', 1, '2021-03-08 18:53:00', 1, '2021-10-27 16:08:53', 1, 1, NULL, 14, 1, 0, 0, 'icona-huaban80', '', 0);
INSERT INTO `sys_page_permission` VALUES (15, 2, '角色管理', '/system/roleManage', 'roleManage', 1, '/system/roleManage', 1, 1, '角色管理', 1, '2021-03-17 14:48:49', 1, '2021-11-11 14:15:40', 1, 1, NULL, 1, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (16, 2, '用户管理', '/system/userControl', 'userControl', 1, '/system/userControl', 1, 0, '用户管理', 1, '2021-03-17 15:57:58', 1, '2021-04-12 15:47:13', 1, 1, NULL, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (17, 2, '部门管理', '/system/manageDepart', 'manageDepart', 1, '/system/manageDepart', 1, 0, '部门管理', 1, '2021-03-17 15:58:27', 1, '2021-04-12 15:47:19', 1, 1, NULL, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (18, 2, '我的部门', '/system/myDepartment', 'myDepartment', 1, '/system/myDepartment', 1, 0, '我的部门', 1, '2021-03-08 16:12:39', 1, '2021-04-12 15:47:21', 1, 1, NULL, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (19, 4, '通知消息', '/noticeManage/messageNotification', 'messageNotification', 1, '/noticeManage/messageNotification', 1, 0, '通知消息', 1, '2021-03-11 13:58:07', 1, '2021-03-19 17:27:09', 1, 1, NULL, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (20, 4, '系统消息', '/noticeManage/systemMessages', 'systemMessages', 1, '/noticeManage/systemMessages', 1, 0, '系统消息', 1, '2021-03-16 08:44:52', 1, '2021-03-19 17:27:26', 1, 1, NULL, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (21, 4, '告警消息', '/noticeManage/alarmMessage', 'alarmMessage', 1, '/noticeManage/alarmMessage', 1, 0, '告警消息', 1, '2021-03-08 18:53:00', 1, '2021-03-19 17:27:40', 1, 1, NULL, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (22, 7, '时间配置', '/systemMaintenance/timeAllocation', 'timeAllocation', 1, '/systemMaintenance/timeAllocation', 1, 0, '时间配置', 1, '2021-03-08 18:53:00', 1, '2021-03-19 17:30:55', 1, 1, NULL, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (23, 7, '升级维护', '/systemMaintenance/upgradeMaintenance', 'upgradeMaintenance', 1, '/systemMaintenance/upgradeMaintenance', 1, 0, '升级维护', 1, '2021-03-08 18:53:00', 1, '2021-03-19 17:31:10', 1, 1, NULL, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (24, 7, '日志管理', '/systemMaintenance/logManagement', 'logManagement', 1, '/systemMaintenance/logManagement', 1, 0, '日志管理', 1, '2021-03-08 18:53:00', 1, '2021-03-19 17:31:27', 1, 1, NULL, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (25, 10, '样式配置', '/platformConfiguration/styleRelated', 'styleRelated', 1, '/platformConfiguration/styleRelated', 1, 0, '样式配置', 1, '2021-03-08 18:53:00', 1, '2021-03-20 11:06:19', 1, 1, NULL, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (26, 11, '我的消息', '/messageCenter/messManage', 'messManage', 1, '/messageCenter/messManage', 1, 1, '我的消息', 1, '2021-03-08 18:53:00', 1, '2021-07-08 14:45:27', 1, 1, NULL, 1, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (27, 11, '消息接收配置', '/messageCenter/messageReceived', 'messageReceived', 1, '/messageCenter/messageReceived', 1, 1, '消息接收配置', 1, '2021-03-08 18:53:00', 1, '2021-05-25 08:57:57', 1, 1, NULL, 2, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (28, 12, '页面管理', '/resource/managePage', 'managePage', 1, '/resource/managePage', 1, 0, '页面管理', 1, '2021-03-08 18:53:00', 1, '2021-03-19 17:35:35', 1, 1, NULL, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (29, 12, '接口管理', '/resource/interfaceManage', 'interfaceManage', 1, '/resource/interfaceManage', 1, 0, '接口管理', 1, '2021-03-08 18:53:00', 1, '2021-03-19 17:36:06', 1, 1, NULL, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (30, 12, '数据字典', '/dataDictionary', 'dataDictionary', 1, '/dataDictionary', 1, 1, '数据字典', 1, '2021-03-08 18:53:00', 1, '2021-03-20 11:55:38', 1, 1, NULL, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (31, 30, '查询数据字典', '/v1/sysDict/getDictList', '/', 2, '/v1/sysDict/getDictList', 1, 0, '查询数据字典', 1, '2021-03-17 17:08:02', 1, '2021-03-17 17:08:02', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (32, 28, '保存系统页面权限', '/v1/managePermission/saveSysPagePermission', '/', 2, '/v1/managePermission/saveSysPagePermission', 1, 1, '保存系统页面权限', 1, '2021-03-18 09:50:59', 1, '2021-11-24 15:26:48', 1, 1, 0, 1, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (33, 29, '保存系统接口权限', '/v1/managePermission/saveSysApiPermission', '/', 2, '/v1/managePermission/saveSysApiPermission', 1, 1, '保存系统接口权限', 1, '2021-03-18 09:51:37', 1, '2021-11-24 15:26:51', 1, 1, 0, 1, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (34, 28, '删除系统页面权限', '/v1/managePermission/deleteSysPagePermission', '/', 2, '/v1/managePermission/deleteSysPagePermission', 1, 1, '删除系统页面权限', 1, '2021-03-18 09:55:22', 1, '2021-11-24 15:26:56', 1, 1, 1, 2, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (35, 28, '更新系统页面权限', '/v1/managePermission/updateSysPagePermission', '/', 2, '/v1/managePermission/updateSysPagePermission', 1, 1, '更新系统页面权限', 1, '2021-03-18 09:55:59', 1, '2021-11-24 15:27:03', 1, 1, 2, 3, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (36, 28, '获取系统页面权限', '/v1/managePermission/getSysPagePermission', '/', 2, '/v1/managePermission/getSysPagePermission', 1, 1, '获取系统页面权限', 1, '2021-03-18 09:56:33', 1, '2021-11-24 15:27:08', 1, 1, 3, 4, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (37, 29, '删除系统接口权限', '/v1/managePermission/deleteSysApiPermission', '/', 2, '/v1/managePermission/deleteSysApiPermission', 1, 1, '删除系统接口权限', 1, '2021-03-18 09:58:48', 1, '2021-11-24 15:27:11', 1, 1, 1, 2, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (38, 29, '更新系统接口权限', '/v1/managePermission/updateSysApiPermission', '/', 2, '/v1/managePermission/updateSysApiPermission', 1, 1, '更新系统接口权限', 1, '2021-03-18 09:59:20', 1, '2021-11-24 15:27:18', 1, 1, 2, 3, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (39, 29, '获取系统接口权限', '/v1/managePermission/getSysApiPermission', '/', 2, '/v1/managePermission/getSysApiPermission', 1, 1, '获取系统接口权限', 1, '2021-03-18 09:59:50', 1, '2021-11-24 15:27:24', 1, 1, 3, 4, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (40, 14, '获取管理系统反馈', '/huoQuGuanLiXiTongFanKui', '/', 2, '/huoQuGuanLiXiTongFanKui', 1, 0, '获取管理系统反馈', 1, '2021-03-18 10:02:48', 1, '2021-03-18 11:28:56', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (41, 14, '驳回系统反馈', '/boHuiXiTongFanKui', '/', 2, '/boHuiXiTongFanKui', 1, 0, '驳回系统反馈', 1, '2021-03-18 10:04:23', 1, '2021-03-18 11:33:14', 1, 1, 2, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (42, 13, '撤销系统反馈', '/cheXiaoXiTongFanKui', '/', 2, '/cheXiaoXiTongFanKui', 1, 1, '撤销系统反馈', 1, '2021-03-18 10:08:21', 1, '2021-11-11 14:34:29', 1, 1, 2, 5, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (43, 30, '添加数据字典', '/tianJiaShuJuZiDian', '/', 2, '/tianJiaShuJuZiDian', 1, 1, '添加数据字典', 1, '2021-03-18 10:09:41', 1, '2021-03-18 10:09:41', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (44, 30, '修改数据字典', '/xiuGaiShuJuZiDian', '/', 2, '/xiuGaiShuJuZiDian', 1, 1, '修改数据字典', 1, '2021-03-18 10:10:18', 1, '2021-03-18 10:10:18', 1, 1, 2, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (45, 30, '删除数据字典', '/shanChuShuJuZiDian', '/', 2, '/shanChuShuJuZiDian', 1, 1, '删除数据字典', 1, '2021-03-18 10:11:24', 1, '2021-03-18 10:11:24', 1, 1, 1, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (46, 13, '保存系统反馈', '/baoCunXiTongFanKui', '/', 2, '/baoCunXiTongFanKui', 1, 1, '保存系统反馈', 1, '2021-03-18 10:18:31', 1, '2021-11-11 14:34:39', 1, 1, 0, 4, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (47, 13, '获取个人系统反馈', '/huoQuGeRenXiTongFanKui', '/', 2, '/huoQuGeRenXiTongFanKui', 1, 1, '获取个人系统反馈列表', 1, '2021-03-18 10:20:00', 1, '2021-11-11 14:34:48', 1, 1, 3, 2, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (48, 6, '获取基本信息', '/huoQuJiBenXinXi', '/', 2, '/huoQuJiBenXinXi', 1, 1, '获取基本信息', 1, '2021-03-18 10:33:27', 1, '2021-03-18 10:33:27', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (49, 6, '获取性能监控', '/huoQuXingNengJianKong', '/', 2, '/huoQuXingNengJianKong', 1, 1, '获取性能监控', 1, '2021-03-18 10:34:01', 1, '2021-03-18 10:34:01', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (50, 6, '获取在线信息', '/huoQuZaiXianXinXi', '/', 2, '/huoQuZaiXianXinXi', 1, 1, '获取在线信息', 1, '2021-03-18 10:34:30', 1, '2021-03-18 10:34:30', 1, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (52, 30, '添加数据字典项', '/tianJiaShuJuZiDianXiang', '/', 2, '/tianJiaShuJuZiDianXiang', 1, 1, '添加数据字典项', 1, '2021-03-18 10:58:26', 1, '2021-03-18 10:58:26', 1, 1, 0, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (53, 30, '查询数据字典项', '/chaXunShuJuZiDianXiang', '/', 2, '/chaXunShuJuZiDianXiang', 1, 1, '查询数据字典项', 1, '2021-03-18 10:59:56', 1, '2021-03-18 11:03:33', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (54, 30, '修改数据字典项', '/xiuGaiShuJuZiDianXiang', '/', 2, '/xiuGaiShuJuZiDianXiang', 1, 1, '修改数据字典项', 1, '2021-03-18 11:03:09', 1, '2021-03-18 11:03:09', 1, 1, 2, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (55, 30, '删除数据字典项', '/shanChuShuJuZiDianXiang', '/', 2, '/shanChuShuJuZiDianXiang', 1, 1, '删除数据字典项', 1, '2021-03-18 11:05:04', 1, '2021-03-18 11:05:04', 1, 1, 1, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (56, 28, '获取系统页面权限列表', '/huoQuXiTongYeMianQuanXianLieBiao', '/', 2, '/huoQuXiTongYeMianQuanXianLieBiao', 1, 0, '获取系统页面权限列表', 1, '2021-03-18 11:17:41', 1, '2021-03-18 11:21:06', 1, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (57, 29, '获取系统接口权限列表', '/huoQuXiTongJieKouQuanXianLieBiao', '/', 2, '/huoQuXiTongJieKouQuanXianLieBiao', 1, 0, '获取系统接口权限列表', 1, '2021-03-18 11:20:05', 1, '2021-03-18 11:20:57', 1, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (58, 29, '接口资源用：获取系统页面权限列表', '/jieKouZiYuanYong：HuoQuXiTongYeMianQuanXianLieBiao', '/', 2, '/jieKouZiYuanYong：HuoQuXiTongYeMianQuanXianLieBiao', 1, 0, '接口资源用：获取系统页面权限列表', 1, '2021-03-18 11:21:52', 1, '2021-04-15 15:47:40', 1, 1, 3, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (59, 14, '回复系统反馈', '/huiFuXiTongFanKui', '/', 2, '/huiFuXiTongFanKui', 1, 0, '回复系统反馈', 1, '2021-03-18 11:23:30', 1, '2021-03-18 11:33:32', 1, 1, 2, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (60, 13, '编辑系统反馈', '/bianJiXiTongFanKui', '/', 2, '/bianJiXiTongFanKui', 1, 1, '编辑系统反馈', 1, '2021-03-18 11:24:51', 1, '2021-11-11 14:34:58', 1, 1, 2, 3, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (61, 13, '确认系统反馈', '/queRenXiTongFanKui', '/', 2, '/queRenXiTongFanKui', 1, 1, '确认系统反馈', 1, '2021-03-18 11:25:29', 1, '2021-11-11 14:35:15', 1, 1, 2, 6, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (62, 14, '获取管理系统反馈列表', '/huoQuGuanLiXiTongFanKuiLieBiao', '/', 2, '/huoQuGuanLiXiTongFanKuiLieBiao', 1, 0, '获取管理系统反馈列表', 1, '2021-03-18 11:27:00', 1, '2021-03-18 11:28:46', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (63, 13, '获取个人系统反馈列表', '/huoQuGeRenXiTongFanKuiLieBiao', '/', 2, '/huoQuGeRenXiTongFanKuiLieBiao', 1, 1, '获取个人系统反馈列表', 1, '2021-03-18 11:28:20', 1, '2021-11-11 14:35:28', 1, 1, 3, 2, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (65, 28, '查询高级搜索', '/chaXunGaoJiSouSuo', '/', 2, '/chaXunGaoJiSouSuo', 1, 1, NULL, 1, '2021-03-18 15:48:19', 1, '2021-03-18 15:48:19', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (66, 28, '新增高级搜索', '/xinZengGaoJiSouSuo', '/', 2, '/xinZengGaoJiSouSuo', 1, 1, NULL, 1, '2021-03-18 15:49:15', 1, '2021-03-18 15:49:15', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (67, 28, '修改高级搜索', '/xiuGaiGaoJiSouSuo', '/', 2, '/xiuGaiGaoJiSouSuo', 1, 1, NULL, 1, '2021-03-18 15:51:16', 1, '2021-03-18 15:51:16', 1, 1, 2, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (68, 28, '删除高级搜索', '/shanChuGaoJiSouSuo', '/', 2, '/shanChuGaoJiSouSuo', 1, 1, NULL, 1, '2021-03-18 15:52:45', 1, '2021-03-18 15:52:45', 1, 1, 1, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (74, 3, '获取安全设置', '/huoQuAnQuanSheZhi', '/', 2, '/huoQuAnQuanSheZhi', 1, 1, NULL, 1, '2021-03-22 17:56:50', 1, '2021-05-13 09:43:43', 1, 1, 3, 1, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (75, 3, '获取基本信息', '/huoQuJiBenXinXi', '/', 2, '/huoQuJiBenXinXi', 1, 1, NULL, 1, '2021-03-22 17:57:27', 1, '2021-07-14 09:27:36', 1, 1, 3, 2, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (76, 3, '基本信息更新', '/jiBenXinXiGengXin', '/', 2, '/jiBenXinXiGengXin', 1, 1, NULL, 1, '2021-03-22 17:58:10', 1, '2021-07-14 09:27:39', 1, 1, 2, 3, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (77, 3, '登录二次认证', '/dengLuErCiRenZheng', '/', 2, '/dengLuErCiRenZheng', 1, 1, NULL, 1, '2021-03-22 17:58:48', 1, '2021-07-14 09:27:45', 1, 1, 2, 4, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (78, 3, '发送绑定手机验证码', '/faSongBangDingShouJiYanZhengMa', '/', 2, '/faSongBangDingShouJiYanZhengMa', 1, 1, NULL, 1, '2021-03-22 17:59:58', 1, '2021-07-14 09:27:58', 1, 1, 3, 5, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (79, 3, '发送绑定邮箱验证码', '/faSongBangDingYouXiangYanZhengMa', '/', 2, '/faSongBangDingYouXiangYanZhengMa', 1, 1, NULL, 1, '2021-03-22 18:00:39', 1, '2021-07-14 09:28:04', 1, 1, 3, 6, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (80, 3, '发送修改手机验证码', '/faSongXiuGaiShouJiYanZhengMa', '/', 2, '/faSongXiuGaiShouJiYanZhengMa', 1, 1, NULL, 1, '2021-03-22 18:01:18', 1, '2021-07-14 09:28:15', 1, 1, 3, 7, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (81, 3, '发送修改邮箱验证码', '/faSongXiuGaiYouXiangYanZhengMa', '/', 2, '/faSongXiuGaiYouXiangYanZhengMa', 1, 1, NULL, 1, '2021-03-22 18:01:43', 1, '2021-07-14 09:28:19', 1, 1, 3, 8, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (82, 3, '发送解绑手机验证码', '/faSongJieBangShouJiYanZhengMa', '/', 2, '/faSongJieBangShouJiYanZhengMa', 1, 1, NULL, 1, '2021-03-22 18:02:10', 1, '2021-07-14 09:28:23', 1, 1, 3, 9, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (83, 3, '发送解绑邮箱验证码', '/faSongJieBangYouXiangYanZhengMa', '/', 2, '/faSongJieBangYouXiangYanZhengMa', 1, 1, NULL, 1, '2021-03-22 18:02:41', 1, '2021-07-14 09:28:33', 1, 1, 3, 10, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (84, 3, '校验手机验证码', '/xiaoYanShouJiYanZhengMa', '/', 2, '/xiaoYanShouJiYanZhengMa', 1, 1, NULL, 1, '2021-03-22 18:03:28', 1, '2021-07-14 09:28:39', 1, 1, 3, 11, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (85, 3, '校验邮箱验证码', '/xiaoYanYouXiangYanZhengMa', '/', 2, '/xiaoYanYouXiangYanZhengMa', 1, 1, NULL, 1, '2021-03-22 18:03:47', 1, '2021-07-14 09:28:46', 1, 1, 3, 12, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (86, 3, '校验手机验证码成功修改密码', '/xiaoYanShouJiYanZhengMaChengGongXiuGaiMiMa', '/', 2, '/xiaoYanShouJiYanZhengMaChengGongXiuGaiMiMa', 1, 1, NULL, 1, '2021-03-22 18:04:10', 1, '2021-07-14 09:28:53', 1, 1, 2, 13, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (87, 3, '校验邮件验证码成功修改密码', '/xiaoYanYouJianYanZhengMaChengGongXiuGaiMiMa', '/', 2, '/xiaoYanYouJianYanZhengMaChengGongXiuGaiMiMa', 1, 1, NULL, 1, '2021-03-22 18:04:35', 1, '2021-07-14 09:28:57', 1, 1, 2, 14, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (88, 3, '绑定手机', '/bangDingShouJi', '/', 2, '/bangDingShouJi', 1, 1, NULL, 1, '2021-03-22 18:05:06', 1, '2021-07-14 09:29:01', 1, 1, 2, 15, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (89, 3, '解绑手机', '/jieBangShouJi', '/', 2, '/jieBangShouJi', 1, 1, NULL, 1, '2021-03-22 18:05:23', 1, '2021-07-14 09:29:04', 1, 1, 2, 16, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (90, 3, '绑定邮箱', '/bangDingYouXiang', '/', 2, '/bangDingYouXiang', 1, 1, NULL, 1, '2021-03-22 18:06:21', 1, '2021-07-14 09:29:08', 1, 1, 2, 17, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (91, 3, '解绑邮箱', '/jieBangYouXiang', '/', 2, '/jieBangYouXiang', 1, 1, NULL, 1, '2021-03-22 18:06:49', 1, '2021-07-14 09:29:19', 1, 1, 2, 18, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (92, 16, '添加系统用户', '/v1/sysUserManage/addSysUser', NULL, 2, '/v1/sysUserManage/addSysUser', 1, 1, '添加系统用户', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 0, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (93, 16, '删除用户', '/v1/sysUserManage/deleteSysUser', NULL, 2, '/v1/sysUserManage/deleteSysUser', 1, 1, '删除用户', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 1, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (94, 16, '冻结用户', '/v1/sysUserManage/freezeSysUser', NULL, 2, '/v1/sysUserManage/freezeSysUser', 1, 1, '冻结用户', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 2, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (95, 16, '获取回收站用户分页列表', '/v1/sysUserManage/getCycleBinListSysUser', NULL, 2, '/v1/sysUserManage/getCycleBinListSysUser', 1, 1, '获取回收站用户分页列表', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 3, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (96, 16, '获取部门列表', '/v1/sysUserManage/getDepartmentList', NULL, 2, '/v1/sysUserManage/getDepartmentList', 1, 1, '获取部门列表', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (97, 16, '获取用户分页列表', '/v1/sysUserManage/getListSysUser', NULL, 2, '/v1/sysUserManage/getListSysUser', 1, 1, '获取用户分页列表', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 3, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (98, 16, '获取全部用户角色', '/v1/sysUserManage/getSysRoleList', NULL, 2, '/v1/sysUserManage/getSysRoleList', 1, 1, '获取全部用户角色', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 3, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (99, 16, '获取用户信息', '/v1/sysUserManage/getSysUserInfo', NULL, 2, '/v1/sysUserManage/getSysUserInfo', 1, 1, '获取用户信息', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 3, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (100, 16, '用户密码重置', '/v1/sysUserManage/restPassword', NULL, 2, '/v1/sysUserManage/restPassword', 1, 1, '用户密码重置', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 2, 9, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (101, 16, '还原用户', '/v1/sysUserManage/restoreUser', NULL, 2, '/v1/sysUserManage/restoreUser', 1, 1, '还原用户', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 2, 10, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (102, 16, '彻底删除用户', '/v1/sysUserManage/thoroughDeleteSysUser', NULL, 2, '/v1/sysUserManage/thoroughDeleteSysUser', 1, 1, '彻底删除用户', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 1, 11, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (103, 16, '解绑邮箱', '/v1/sysUserManage/unbindEmail', NULL, 2, '/v1/sysUserManage/unbindEmail', 1, 1, '解绑邮箱', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 2, 12, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (104, 16, '解绑手机号', '/v1/sysUserManage/unbindPhone', NULL, 2, '/v1/sysUserManage/unbindPhone', 1, 1, '解绑手机号', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 2, 13, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (105, 16, '解冻用户', '/v1/sysUserManage/unfreezeSysUser', NULL, 2, '/v1/sysUserManage/unfreezeSysUser', 1, 1, '解冻用户', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 2, 14, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (106, 16, '修改用户', '/v1/sysUserManage/updateSysUser', NULL, 2, '/v1/sysUserManage/updateSysUser', 1, 1, '修改用户', 1, '2021-03-30 15:09:11', 1, '2021-03-30 15:09:11', 1, 1, 2, 15, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (107, 24, '获取登录日志列表', '/huoQuDengLuRiZhiLieBiao ', '/', 2, '/huoQuDengLuRiZhiLieBiao ', 1, 1, '获取登录日志列表', 1, '2021-03-30 18:08:48', 1, '2021-03-30 18:08:48', 1, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (108, 10, '消息通知配置', '/platformConfiguration/configurationMessagin', 'configurationMessagin', 1, '/platformConfiguration/configurationMessagin', 1, 1, '消息通知配置', 1, '2021-04-02 14:12:43', 1, '2021-04-02 14:12:43', 1, 1, NULL, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (109, 0, '数据驾驶舱', '/home', 'home', 0, '/home', 1, 0, NULL, 1, '2021-04-06 15:04:55', 1, '2021-07-21 15:28:35', 1, 1, NULL, 1, 1, 0, 0, 'iconhuaban10', NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (110, 109, '获取数据驾驶舱信息', '/v1/dataCockpit/getDataCockpit', NULL, 2, '/v1/dataCockpit/getDataCockpit', 1, 1, '获取数据驾驶舱信息', 1, '2021-04-06 15:10:34', 1, '2021-04-06 15:10:34', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (111, 15, '添加部门角色', '/v1/sysRoleManage/addDeptRole', NULL, 2, '/v1/sysRoleManage/addDeptRole', 1, 1, '添加部门角色', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 0, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (112, 15, '新增系统角色', '/v1/sysRoleManage/addSysRole', NULL, 2, '/v1/sysRoleManage/addSysRole', 1, 1, '新增系统角色', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (113, 15, '部门角色批量删除', '/v1/sysRoleManage/deleteDeptRole', NULL, 2, '/v1/sysRoleManage/deleteDeptRole', 1, 1, '部门角色批量删除', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 1, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (114, 15, '删除部门角色授权的用户', '/v1/sysRoleManage/deleteDeptRoleAuthorizedUser', NULL, 2, '/v1/sysRoleManage/deleteDeptRoleAuthorizedUser', 1, 1, '删除部门角色授权的用户', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 1, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (115, 15, '系统角色批量删除', '/v1/sysRoleManage/deleteSysRole', NULL, 2, '/v1/sysRoleManage/deleteSysRole', 1, 1, '系统角色批量删除', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 1, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (116, 15, '删除系统角色授权的用户', '/v1/sysRoleManage/deleteSysRoleAuthorizedUser', NULL, 2, '/v1/sysRoleManage/deleteSysRoleAuthorizedUser', 1, 1, '删除系统角色授权的用户', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 1, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (117, 15, '部门角色授权用户', '/v1/sysRoleManage/deptRoleAuthorizedUser', NULL, 2, '/v1/sysRoleManage/deptRoleAuthorizedUser', 1, 1, '部门角色授权用户', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 0, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (118, 15, '部门角色获取部门列表', '/v1/sysRoleManage/getDeptRoleDepartmentList', NULL, 2, '/v1/sysRoleManage/getDeptRoleDepartmentList', 1, 1, '部门角色获取部门列表', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (119, 15, '获取部门角色详情', '/v1/sysRoleManage/getDeptRoleInfo', NULL, 2, '/v1/sysRoleManage/getDeptRoleInfo', 1, 1, '获取部门角色详情', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 9, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (120, 15, '获取部门角色接口集权限', '/v1/sysRoleManage/getDeptRoleSysApiPermission', NULL, 2, '/v1/sysRoleManage/getDeptRoleSysApiPermission', 1, 1, '获取部门角色接口集权限', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 10, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (121, 15, '获取部门角色菜单资源', '/v1/sysRoleManage/getDeptRoleSysPageTree', NULL, 2, '/v1/sysRoleManage/getDeptRoleSysPageTree', 1, 1, '获取部门角色菜单资源', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 11, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (122, 15, '分页获取部门用户授权的分页列表', '/v1/sysRoleManage/getListDeptUser', NULL, 2, '/v1/sysRoleManage/getListDeptUser', 1, 1, '分页获取部门用户授权的分页列表', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 12, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (123, 15, '分页获取部门用户未授权分页列表', '/v1/sysRoleManage/getListDeptUserNoAuth', NULL, 2, '/v1/sysRoleManage/getListDeptUserNoAuth', 1, 1, '分页获取部门用户未授权分页列表', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 13, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (124, 15, '分页获取系统授权用户列表', '/v1/sysRoleManage/getListSysUser', NULL, 2, '/v1/sysRoleManage/getListSysUser', 1, 1, '分页获取系统授权用户列表', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 14, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (125, 15, '分页获取系统授权用户列表', '/v1/sysRoleManage/getListSysUserNotAuth', NULL, 2, '/v1/sysRoleManage/getListSysUserNotAuth', 1, 1, '分页获取系统授权用户列表', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 15, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (126, 15, '系统角色获取部门列表', '/v1/sysRoleManage/getSysRoleDepartmentList', NULL, 2, '/v1/sysRoleManage/getSysRoleDepartmentList', 1, 1, '系统角色获取部门列表', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 16, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (127, 15, '获取系统角色详情', '/v1/sysRoleManage/getSysRoleInfo', NULL, 2, '/v1/sysRoleManage/getSysRoleInfo', 1, 1, '获取系统角色详情', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 17, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (128, 15, '获取系统角色接口集权限', '/v1/sysRoleManage/getSysRoleSysApiPermission', NULL, 2, '/v1/sysRoleManage/getSysRoleSysApiPermission', 1, 1, '获取系统角色接口集权限', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 18, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (129, 15, '获取系统角色菜单资源', '/v1/sysRoleManage/getSysRoleSysPageTree', NULL, 2, '/v1/sysRoleManage/getSysRoleSysPageTree', 1, 1, '获取系统角色菜单资源', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 19, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (130, 15, '获取部门角色列表', '/v1/sysRoleManage/listDeptRoleItem', NULL, 2, '/v1/sysRoleManage/listDeptRoleItem', 1, 1, '获取部门角色列表', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 20, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (131, 15, '查询角色列表', '/v1/sysRoleManage/listSysRoleItem', NULL, 2, '/v1/sysRoleManage/listSysRoleItem', 1, 1, '查询角色列表', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 3, 21, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (132, 15, '保存部门角色系统权限配置', '/v1/sysRoleManage/saveDeptPermission', NULL, 2, '/v1/sysRoleManage/saveDeptPermission', 1, 1, '保存部门角色系统权限配置', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 0, 22, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (133, 15, '保存系统角色系统权限配置', '/v1/sysRoleManage/saveSysPermission', NULL, 2, '/v1/sysRoleManage/saveSysPermission', 1, 1, '保存系统角色系统权限配置', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 0, 23, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (134, 15, '系统角色授权用户', '/v1/sysRoleManage/sysRoleAuthorizedUser', NULL, 2, '/v1/sysRoleManage/sysRoleAuthorizedUser', 1, 1, '系统角色授权用户', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 0, 24, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (135, 15, '编辑部门角色', '/v1/sysRoleManage/updateDeptRole', NULL, 2, '/v1/sysRoleManage/updateDeptRole', 1, 1, '编辑部门角色', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 2, 25, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (136, 15, '编辑系统角色', '/v1/sysRoleManage/updateSysRole', NULL, 2, '/v1/sysRoleManage/updateSysRole', 1, 1, '编辑系统角色', 1, '2021-04-06 17:59:09', 1, '2021-04-06 17:59:09', 1, 1, 2, 26, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (137, 21, '删除通告', '/v1/sysAnnouncementWarn/deleteAnnouncement', NULL, 2, '/v1/sysAnnouncementWarn/deleteAnnouncement', 1, 1, '删除通告', 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, 1, 1, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (138, 21, '获取通告详情', '/v1/sysAnnouncementWarn/getAnnouncementInfo', NULL, 2, '/v1/sysAnnouncementWarn/getAnnouncementInfo', 1, 1, '获取通告详情', 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (139, 21, '分页获取通告', '/v1/sysAnnouncementWarn/getAnnouncementItemPage', NULL, 2, '/v1/sysAnnouncementWarn/getAnnouncementItemPage', 1, 1, '分页获取通告', 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (140, 21, '部门角色获取部门列表', '/v1/sysAnnouncementWarn/getDeptDepartmentList', NULL, 2, '/v1/sysAnnouncementWarn/getDeptDepartmentList', 1, 1, '部门角色获取部门列表', 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, 1, 3, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (141, 21, '获取用户分页列表', '/v1/sysAnnouncementWarn/getListSysUser', NULL, 2, '/v1/sysAnnouncementWarn/getListSysUser', 1, 1, '获取用户分页列表', 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (142, 21, '发布通告', '/v1/sysAnnouncementWarn/publishAnnouncement', NULL, 2, '/v1/sysAnnouncementWarn/publishAnnouncement', 1, 1, '发布通告', 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, 1, 0, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (143, 21, '撤销通告', '/v1/sysAnnouncementWarn/revokeAnnouncement', NULL, 2, '/v1/sysAnnouncementWarn/revokeAnnouncement', 1, 1, '撤销通告', 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, 1, 1, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (144, 21, '编辑通告', '/v1/sysAnnouncementWarn/updateAnnouncement', NULL, 2, '/v1/sysAnnouncementWarn/updateAnnouncement', 1, 1, '编辑通告', 1, '2021-04-07 10:40:27', 1, '2021-04-07 10:40:27', 1, 1, 2, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (145, 20, '删除通告', '/v1/sysAnnouncementSys/deleteAnnouncement', NULL, 2, '/v1/sysAnnouncementSys/deleteAnnouncement', 1, 1, '删除通告', 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, 1, 1, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (146, 20, '获取通告详情', '/v1/sysAnnouncementSys/getAnnouncementInfo', NULL, 2, '/v1/sysAnnouncementSys/getAnnouncementInfo', 1, 1, '获取通告详情', 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (147, 20, '分页获取通告', '/v1/sysAnnouncementSys/getAnnouncementItemPage', NULL, 2, '/v1/sysAnnouncementSys/getAnnouncementItemPage', 1, 1, '分页获取通告', 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (148, 20, '部门角色获取部门列表', '/v1/sysAnnouncementSys/getDeptDepartmentList', NULL, 2, '/v1/sysAnnouncementSys/getDeptDepartmentList', 1, 1, '部门角色获取部门列表', 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, 1, 3, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (149, 20, '获取用户分页列表', '/v1/sysAnnouncementSys/getListSysUser', NULL, 2, '/v1/sysAnnouncementSys/getListSysUser', 1, 1, '获取用户分页列表', 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (150, 20, '发布通告', '/v1/sysAnnouncementSys/publishAnnouncement', NULL, 2, '/v1/sysAnnouncementSys/publishAnnouncement', 1, 1, '发布通告', 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, 1, 0, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (151, 20, '撤销通告', '/v1/sysAnnouncementSys/revokeAnnouncement', NULL, 2, '/v1/sysAnnouncementSys/revokeAnnouncement', 1, 1, '撤销通告', 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, 1, 1, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (152, 20, '编辑通告', '/v1/sysAnnouncementSys/updateAnnouncement', NULL, 2, '/v1/sysAnnouncementSys/updateAnnouncement', 1, 1, '编辑通告', 1, '2021-04-07 10:41:18', 1, '2021-04-07 10:41:18', 1, 1, 2, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (153, 19, '新增通告', '/v1/sysAnnouncementNotic/addAnnouncement', NULL, 2, '/v1/sysAnnouncementNotic/addAnnouncement', 1, 1, '新增通告', 1, '2021-04-07 10:41:35', 1, '2021-04-07 10:41:35', 1, 1, 0, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (154, 19, '删除通告', '/v1/sysAnnouncementNotic/deleteAnnouncement', NULL, 2, '/v1/sysAnnouncementNotic/deleteAnnouncement', 1, 1, '删除通告', 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, 1, 1, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (155, 19, '获取通告详情', '/v1/sysAnnouncementNotic/getAnnouncementInfo', NULL, 2, '/v1/sysAnnouncementNotic/getAnnouncementInfo', 1, 1, '获取通告详情', 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (156, 19, '分页获取通告', '/v1/sysAnnouncementNotic/getAnnouncementItemPage', NULL, 2, '/v1/sysAnnouncementNotic/getAnnouncementItemPage', 1, 1, '分页获取通告', 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, 1, 3, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (157, 19, '部门角色获取部门列表', '/v1/sysAnnouncementNotic/getDeptDepartmentList', NULL, 2, '/v1/sysAnnouncementNotic/getDeptDepartmentList', 1, 1, '部门角色获取部门列表', 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (158, 19, '获取用户分页列表', '/v1/sysAnnouncementNotic/getListSysUser', NULL, 2, '/v1/sysAnnouncementNotic/getListSysUser', 1, 1, '获取用户分页列表', 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, 1, 3, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (159, 19, '发布通告', '/v1/sysAnnouncementNotic/publishAnnouncement', NULL, 2, '/v1/sysAnnouncementNotic/publishAnnouncement', 1, 1, '发布通告', 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, 1, 2, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (160, 19, '撤销通告', '/v1/sysAnnouncementNotic/revokeAnnouncement', NULL, 2, '/v1/sysAnnouncementNotic/revokeAnnouncement', 1, 1, '撤销通告', 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, 1, 1, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (161, 19, '编辑通告', '/v1/sysAnnouncementNotic/updateAnnouncement', NULL, 2, '/v1/sysAnnouncementNotic/updateAnnouncement', 1, 1, '编辑通告', 1, '2021-04-07 10:41:36', 1, '2021-04-07 10:41:36', 1, 1, 2, 9, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (169, 108, '获取消息通知配置', '/v1/sysAnnouncementConfig/getAnnouncementConfigList', NULL, 2, '/v1/sysAnnouncementConfig/getAnnouncementConfigList', 1, 1, '获取消息通知配置', 1, '2021-04-07 10:46:09', 1, '2021-04-07 10:46:09', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (170, 108, '保存消息通知配置', '/v1/sysAnnouncementConfig/saveAnnouncementConfig', NULL, 2, '/v1/sysAnnouncementConfig/saveAnnouncementConfig', 1, 1, '保存消息通知配置', 1, '2021-04-07 10:46:10', 1, '2021-04-07 10:46:10', 1, 1, 2, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (173, 17, '添加部门', '/v1/departmentManage/addDepartment', NULL, 2, '/v1/departmentManage/addDepartment', 1, 1, '添加部门', 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, 1, 0, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (174, 17, '添加部门角色', '/v1/departmentManage/addDeptRole', NULL, 2, '/v1/departmentManage/addDeptRole', 1, 1, '添加部门角色', 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (175, 17, '添加系统用户', '/v1/departmentManage/addSysUser', NULL, 2, '/v1/departmentManage/addSysUser', 1, 1, '添加系统用户', 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, 1, 0, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (176, 17, '批量删除部门', '/v1/departmentManage/deleteBatchDepartment', NULL, 2, '/v1/departmentManage/deleteBatchDepartment', 1, 1, '批量删除部门', 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, 1, 1, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (177, 17, '部门角色批量删除', '/v1/departmentManage/deleteDeptRole', NULL, 2, '/v1/departmentManage/deleteDeptRole', 1, 1, '部门角色批量删除', 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, 1, 1, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (178, 17, '解除角色', '/v1/departmentManage/deleteDeptRoleAuthorizedUser', NULL, 2, '/v1/departmentManage/deleteDeptRoleAuthorizedUser', 1, 1, '解除角色', 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, 1, 1, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (179, 17, '删除用户', '/v1/departmentManage/deleteSysUser', NULL, 2, '/v1/departmentManage/deleteSysUser', 1, 1, '删除用户', 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, 1, 1, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (180, 17, '部门成员添加已有成员', '/v1/departmentManage/departmentLinkUser', NULL, 2, '/v1/departmentManage/departmentLinkUser', 1, 1, '部门成员添加已有成员', 1, '2021-04-07 16:29:50', 1, '2021-04-07 16:29:50', 1, 1, 0, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (181, 17, '部门取消关联用户', '/v1/departmentManage/departmentUnlinkUser', NULL, 2, '/v1/departmentManage/departmentUnlinkUser', 1, 1, '部门取消关联用户', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 2, 9, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (182, 17, '部门角色授权用户', '/v1/departmentManage/deptRoleAuthorizedUser', NULL, 2, '/v1/departmentManage/deptRoleAuthorizedUser', 1, 1, '部门角色授权用户', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 0, 10, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (183, 17, '冻结用户', '/v1/departmentManage/freezeSysUser', NULL, 2, '/v1/departmentManage/freezeSysUser', 1, 1, '冻结用户', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 2, 11, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (184, 17, '获取部门列表', '/v1/departmentManage/getDepartmentList', NULL, 2, '/v1/departmentManage/getDepartmentList', 1, 1, '获取部门列表', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 12, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (185, 17, '获取部门成员用户分页列表', '/v1/departmentManage/getDepartmentMemberListSysUser', NULL, 2, '/v1/departmentManage/getDepartmentMemberListSysUser', 1, 1, '获取部门成员用户分页列表', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 13, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (186, 17, '获取部门成员添加已有用户分页列表', '/v1/departmentManage/getDepartmentMemberListSysUserAdd', NULL, 2, '/v1/departmentManage/getDepartmentMemberListSysUserAdd', 1, 1, '获取部门成员添加已有用户分页列表', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 14, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (187, 17, '部门角色获取部门列表', '/v1/departmentManage/getDeptDepartmentList', NULL, 2, '/v1/departmentManage/getDeptDepartmentList', 1, 1, '部门角色获取部门列表', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 15, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (188, 17, '获取部门详情', '/v1/departmentManage/getDeptInfo', NULL, 2, '/v1/departmentManage/getDeptInfo', 1, 1, '获取部门详情', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 16, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (189, 17, '获取部门角色详情', '/v1/departmentManage/getDeptRoleInfo', NULL, 2, '/v1/departmentManage/getDeptRoleInfo', 1, 1, '获取部门角色详情', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 17, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (190, 17, '根据部门id获取部门下的角色', '/v1/departmentManage/getDeptRoleList', NULL, 2, '/v1/departmentManage/getDeptRoleList', 1, 1, '根据部门id获取部门下的角色', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 18, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (191, 17, '获取部门角色接口集权限', '/v1/departmentManage/getDeptRoleSysApiPermission', NULL, 2, '/v1/departmentManage/getDeptRoleSysApiPermission', 1, 1, '获取部门角色接口集权限', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 19, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (192, 17, '获取部门角色菜单资源', '/v1/departmentManage/getDeptRoleSysPageTree', NULL, 2, '/v1/departmentManage/getDeptRoleSysPageTree', 1, 1, '获取部门角色菜单资源', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 20, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (193, 17, '获取部门权限接口集权限', '/v1/departmentManage/getDeptSysApiPermission', NULL, 2, '/v1/departmentManage/getDeptSysApiPermission', 1, 1, '获取部门权限接口集权限', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 21, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (194, 17, '获取部门权限菜单资源', '/v1/departmentManage/getDeptSysPageTree', NULL, 2, '/v1/departmentManage/getDeptSysPageTree', 1, 1, '获取部门权限菜单资源', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 22, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (195, 17, '获取部门负责人列表', '/v1/departmentManage/getHeadDepartmentUserList', NULL, 2, '/v1/departmentManage/getHeadDepartmentUserList', 1, 1, '获取部门负责人列表', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 23, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (196, 17, '分页获取部门用户分页列表', '/v1/departmentManage/getListDeptRoleUserList', NULL, 2, '/v1/departmentManage/getListDeptRoleUserList', 1, 1, '分页获取部门用户分页列表', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 24, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (197, 17, '分页获取部门角色-添加已有用户', '/v1/departmentManage/getListDeptRoleUserListAdd', NULL, 2, '/v1/departmentManage/getListDeptRoleUserListAdd', 1, 1, '分页获取部门角色-添加已有用户', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 25, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (198, 17, '获取全部用户角色', '/v1/departmentManage/getSysRoleList', NULL, 2, '/v1/departmentManage/getSysRoleList', 1, 1, '获取全部用户角色', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 26, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (199, 17, '获取用户信息', '/v1/departmentManage/getSysUserInfo', NULL, 2, '/v1/departmentManage/getSysUserInfo', 1, 1, '获取用户信息', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 27, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (200, 17, '获取部门列表', '/v1/departmentManage/getUserDepartmentList', NULL, 2, '/v1/departmentManage/getUserDepartmentList', 1, 1, '获取部门列表', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 28, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (201, 17, '获取部门角色列表', '/v1/departmentManage/listDeptRoleItem', NULL, 2, '/v1/departmentManage/listDeptRoleItem', 1, 1, '获取部门角色列表', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 3, 29, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (202, 17, '用户密码重置', '/v1/departmentManage/restPassword', NULL, 2, '/v1/departmentManage/restPassword', 1, 1, '用户密码重置', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 2, 30, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (203, 17, '保存部门权限配置', '/v1/departmentManage/saveDeptPermission', NULL, 2, '/v1/departmentManage/saveDeptPermission', 1, 1, '保存部门权限配置', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 0, 31, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (204, 17, '保存部门角色系统权限配置', '/v1/departmentManage/saveDeptRolePermission', NULL, 2, '/v1/departmentManage/saveDeptRolePermission', 1, 1, '保存部门角色系统权限配置', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 0, 32, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (205, 17, '解冻用户', '/v1/departmentManage/unfreezeSysUser', NULL, 2, '/v1/departmentManage/unfreezeSysUser', 1, 1, '解冻用户', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 2, 33, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (206, 17, '修改部门', '/v1/departmentManage/updateDepartment', NULL, 2, '/v1/departmentManage/updateDepartment', 1, 1, '修改部门', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 2, 34, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (207, 17, '编辑部门角色', '/v1/departmentManage/updateDeptRole', NULL, 2, '/v1/departmentManage/updateDeptRole', 1, 1, '编辑部门角色', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 2, 35, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (208, 17, '修改用户', '/v1/departmentManage/updateSysUser', NULL, 2, '/v1/departmentManage/updateSysUser', 1, 1, '修改用户', 1, '2021-04-07 16:29:51', 1, '2021-04-07 16:29:51', 1, 1, 2, 36, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (209, 18, '添加部门角色', '/v1/myDepartment/addDeptRole', NULL, 2, '/v1/myDepartment/addDeptRole', 1, 1, '添加部门角色', 1, '2021-04-07 16:31:07', 1, '2021-04-07 16:31:07', 0, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (210, 18, '添加系统用户', '/v1/myDepartment/addSysUser', NULL, 2, '/v1/myDepartment/addSysUser', 1, 1, '添加系统用户', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (211, 18, '部门角色批量删除', '/v1/myDepartment/deleteDeptRole', NULL, 2, '/v1/myDepartment/deleteDeptRole', 1, 1, '部门角色批量删除', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (212, 18, '删除部门角色授权的用户', '/v1/myDepartment/deleteDeptRoleAuthorizedUser', NULL, 2, '/v1/myDepartment/deleteDeptRoleAuthorizedUser', 1, 1, '删除部门角色授权的用户', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (213, 18, '删除用户', '/v1/myDepartment/deleteSysUser', NULL, 2, '/v1/myDepartment/deleteSysUser', 1, 1, '删除用户', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (214, 18, '部门成员添加已有成员', '/v1/myDepartment/departmentLinkUser', NULL, 2, '/v1/myDepartment/departmentLinkUser', 1, 1, '部门成员添加已有成员', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (215, 18, '部门取消关联用户', '/v1/myDepartment/departmentUnlinkUser', NULL, 2, '/v1/myDepartment/departmentUnlinkUser', 1, 1, '部门取消关联用户', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (216, 18, '部门角色授权用户', '/v1/myDepartment/deptRoleAuthorizedUser', NULL, 2, '/v1/myDepartment/deptRoleAuthorizedUser', 1, 1, '部门角色授权用户', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (217, 18, '冻结用户', '/v1/myDepartment/freezeSysUser', NULL, 2, '/v1/myDepartment/freezeSysUser', 1, 1, '冻结用户', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 9, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (218, 18, '获取部门列表', '/v1/myDepartment/getDepartmentList', NULL, 2, '/v1/myDepartment/getDepartmentList', 1, 1, '获取部门列表', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 10, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (219, 18, '获取部门详情', '/v1/myDepartment/getDeptInfo', NULL, 2, '/v1/myDepartment/getDeptInfo', 1, 1, '获取部门详情', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 11, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (220, 18, '部门角色获取部门列表', '/v1/myDepartment/getDeptRoleDepartmentList', NULL, 2, '/v1/myDepartment/getDeptRoleDepartmentList', 1, 1, '部门角色获取部门列表', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 12, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (221, 18, '获取部门角色详情', '/v1/myDepartment/getDeptRoleInfo', NULL, 2, '/v1/myDepartment/getDeptRoleInfo', 1, 1, '获取部门角色详情', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 13, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (222, 18, '获取部门角色接口集权限', '/v1/myDepartment/getDeptRoleSysApiPermission', NULL, 2, '/v1/myDepartment/getDeptRoleSysApiPermission', 1, 1, '获取部门角色接口集权限', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 14, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (223, 18, '获取部门角色菜单资源', '/v1/myDepartment/getDeptRoleSysPageTree', NULL, 2, '/v1/myDepartment/getDeptRoleSysPageTree', 1, 1, '获取部门角色菜单资源', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 15, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (224, 18, '部门角色添加已有用户', '/v1/myDepartment/getHaveListSysUser', NULL, 2, '/v1/myDepartment/getHaveListSysUser', 1, 1, '部门角色添加已有用户', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 16, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (225, 18, '分页获取部门用户分页列表', '/v1/myDepartment/getListDeptUser', NULL, 2, '/v1/myDepartment/getListDeptUser', 1, 1, '分页获取部门用户分页列表', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 17, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (226, 18, '获取已有的部门成员', '/v1/myDepartment/getListDeptUserAdd', NULL, 2, '/v1/myDepartment/getListDeptUserAdd', 1, 1, '获取已有的部门成员', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 18, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (227, 18, '部门成员获取用户分页列表', '/v1/myDepartment/getListSysUser', NULL, 2, '/v1/myDepartment/getListSysUser', 1, 1, '部门成员获取用户分页列表', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 19, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (228, 18, '获取全部用户角色', '/v1/myDepartment/getSysRoleList', NULL, 2, '/v1/myDepartment/getSysRoleList', 1, 1, '获取全部用户角色', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 20, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (229, 18, '获取用户信息', '/v1/myDepartment/getSysUserInfo', NULL, 2, '/v1/myDepartment/getSysUserInfo', 1, 1, '获取用户信息', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 21, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (230, 18, '获取部门角色列表', '/v1/myDepartment/listDeptRoleItem', NULL, 2, '/v1/myDepartment/listDeptRoleItem', 1, 1, '获取部门角色列表', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 22, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (231, 18, '用户密码重置', '/v1/myDepartment/restPassword', NULL, 2, '/v1/myDepartment/restPassword', 1, 1, '用户密码重置', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 23, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (232, 18, '保存部门角色系统权限配置', '/v1/myDepartment/saveDeptRolePermission', NULL, 2, '/v1/myDepartment/saveDeptRolePermission', 1, 1, '保存部门角色系统权限配置', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 24, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (233, 18, '解冻用户', '/v1/myDepartment/unfreezeSysUser', NULL, 2, '/v1/myDepartment/unfreezeSysUser', 1, 1, '解冻用户', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 25, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (234, 18, '修改部门', '/v1/myDepartment/updateDepartment', NULL, 2, '/v1/myDepartment/updateDepartment', 1, 1, '修改部门', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 26, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (235, 18, '编辑部门角色', '/v1/myDepartment/updateDeptRole', NULL, 2, '/v1/myDepartment/updateDeptRole', 1, 1, '编辑部门角色', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 27, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (236, 18, '修改用户', '/v1/myDepartment/updateSysUser', NULL, 2, '/v1/myDepartment/updateSysUser', 1, 1, '修改用户', 1, '2021-04-07 16:31:08', 1, '2021-04-07 16:31:08', 0, 1, 3, 28, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (237, 109, '获取使用时间统计', '/v1/dataCockpit/getUsageTimeStatistics', NULL, 2, '/v1/dataCockpit/getUsageTimeStatistics', 1, 1, '获取使用时间统计', 1, '2021-04-08 17:59:45', 1, '2021-04-08 17:59:45', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (238, 109, '获取访问量统计', '/v1/dataCockpit/getVisitorVolumeStatistics', NULL, 2, '/v1/dataCockpit/getVisitorVolumeStatistics', 1, 1, '获取访问量统计', 1, '2021-04-08 17:59:45', 1, '2021-04-08 17:59:45', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (239, 18, '添加部门角色', '/v1/myDepartment/addDeptRole', NULL, 2, '/v1/myDepartment/addDeptRole', 1, 1, '添加部门角色', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 0, 1, 0, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (240, 18, '添加系统用户', '/v1/myDepartment/addSysUser', NULL, 2, '/v1/myDepartment/addSysUser', 1, 1, '添加系统用户', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (241, 18, '部门角色批量删除', '/v1/myDepartment/deleteDeptRole', NULL, 2, '/v1/myDepartment/deleteDeptRole', 1, 1, '部门角色批量删除', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 1, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (242, 18, '删除部门角色授权的用户', '/v1/myDepartment/deleteDeptRoleAuthorizedUser', NULL, 2, '/v1/myDepartment/deleteDeptRoleAuthorizedUser', 1, 1, '删除部门角色授权的用户', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 1, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (243, 18, '删除用户', '/v1/myDepartment/deleteSysUser', NULL, 2, '/v1/myDepartment/deleteSysUser', 1, 1, '删除用户', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 1, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (244, 18, '部门成员添加已有成员', '/v1/myDepartment/departmentLinkUser', NULL, 2, '/v1/myDepartment/departmentLinkUser', 1, 1, '部门成员添加已有成员', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 0, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (245, 18, '部门取消关联用户', '/v1/myDepartment/departmentUnlinkUser', NULL, 2, '/v1/myDepartment/departmentUnlinkUser', 1, 1, '部门取消关联用户', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 2, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (246, 18, '部门角色授权用户', '/v1/myDepartment/deptRoleAuthorizedUser', NULL, 2, '/v1/myDepartment/deptRoleAuthorizedUser', 1, 1, '部门角色授权用户', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 0, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (247, 18, '冻结用户', '/v1/myDepartment/freezeSysUser', NULL, 2, '/v1/myDepartment/freezeSysUser', 1, 1, '冻结用户', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 2, 9, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (248, 18, '获取部门列表', '/v1/myDepartment/getDepartmentList', NULL, 2, '/v1/myDepartment/getDepartmentList', 1, 1, '获取部门列表', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 10, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (249, 18, '获取部门详情', '/v1/myDepartment/getDeptInfo', NULL, 2, '/v1/myDepartment/getDeptInfo', 1, 1, '获取部门详情', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 11, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (250, 18, '部门角色获取部门列表', '/v1/myDepartment/getDeptRoleDepartmentList', NULL, 2, '/v1/myDepartment/getDeptRoleDepartmentList', 1, 1, '部门角色获取部门列表', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 12, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (251, 18, '获取部门角色详情', '/v1/myDepartment/getDeptRoleInfo', NULL, 2, '/v1/myDepartment/getDeptRoleInfo', 1, 1, '获取部门角色详情', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 13, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (252, 18, '获取部门角色接口集权限', '/v1/myDepartment/getDeptRoleSysApiPermission', NULL, 2, '/v1/myDepartment/getDeptRoleSysApiPermission', 1, 1, '获取部门角色接口集权限', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 14, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (253, 18, '获取部门角色菜单资源', '/v1/myDepartment/getDeptRoleSysPageTree', NULL, 2, '/v1/myDepartment/getDeptRoleSysPageTree', 1, 1, '获取部门角色菜单资源', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 15, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (254, 18, '部门角色添加已有用户', '/v1/myDepartment/getHaveListSysUser', NULL, 2, '/v1/myDepartment/getHaveListSysUser', 1, 1, '部门角色添加已有用户', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 0, 16, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (255, 18, '分页获取部门用户分页列表', '/v1/myDepartment/getListDeptUser', NULL, 2, '/v1/myDepartment/getListDeptUser', 1, 1, '分页获取部门用户分页列表', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 17, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (256, 18, '获取已有的部门成员', '/v1/myDepartment/getListDeptUserAdd', NULL, 2, '/v1/myDepartment/getListDeptUserAdd', 1, 1, '获取已有的部门成员', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 18, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (257, 18, '部门成员获取用户分页列表', '/v1/myDepartment/getListSysUser', NULL, 2, '/v1/myDepartment/getListSysUser', 1, 1, '部门成员获取用户分页列表', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 19, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (258, 18, '获取全部用户角色', '/v1/myDepartment/getSysRoleList', NULL, 2, '/v1/myDepartment/getSysRoleList', 1, 1, '获取全部用户角色', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 20, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (259, 18, '获取用户信息', '/v1/myDepartment/getSysUserInfo', NULL, 2, '/v1/myDepartment/getSysUserInfo', 1, 1, '获取用户信息', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 21, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (260, 18, '获取部门角色列表', '/v1/myDepartment/listDeptRoleItem', NULL, 2, '/v1/myDepartment/listDeptRoleItem', 1, 1, '获取部门角色列表', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 22, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (261, 18, '用户密码重置', '/v1/myDepartment/restPassword', NULL, 2, '/v1/myDepartment/restPassword', 1, 1, '用户密码重置', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 2, 23, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (262, 18, '保存部门角色系统权限配置', '/v1/myDepartment/saveDeptRolePermission', NULL, 2, '/v1/myDepartment/saveDeptRolePermission', 1, 1, '保存部门角色系统权限配置', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 3, 24, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (263, 18, '解冻用户', '/v1/myDepartment/unfreezeSysUser', NULL, 2, '/v1/myDepartment/unfreezeSysUser', 1, 1, '解冻用户', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 2, 25, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (264, 18, '修改部门', '/v1/myDepartment/updateDepartment', NULL, 2, '/v1/myDepartment/updateDepartment', 1, 1, '修改部门', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 2, 26, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (265, 18, '编辑部门角色', '/v1/myDepartment/updateDeptRole', NULL, 2, '/v1/myDepartment/updateDeptRole', 1, 1, '编辑部门角色', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 2, 27, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (266, 18, '修改用户', '/v1/myDepartment/updateSysUser', NULL, 2, '/v1/myDepartment/updateSysUser', 1, 1, '修改用户', 1, '2021-04-13 09:42:19', 1, '2021-04-13 09:42:19', 1, 1, 2, 28, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (270, 9, 'API配置', '/advanconfig/apiConfig', 'apiConfig', 1, '452', 1, 1, 'API配置', 1, '2021-04-19 16:06:39', 1, '2021-04-19 16:06:39', 1, 1, NULL, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (271, 270, '应用注册', '/advanconfig/appRegister', 'appRegister', 1, '542452', 1, 1, '应用注册', 1, '2021-04-19 16:07:12', 1, '2021-04-20 13:48:25', 1, 1, NULL, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (272, 0, '高级配置', '/advanconfig', 'ldapConfig', 0, '/', 1, 0, '', 1, '2021-04-20 13:53:42', 1, '2021-10-27 16:09:41', 1, 1, NULL, 9, 1, 0, 0, 'iconhuaban13', '', 0);
INSERT INTO `sys_page_permission` VALUES (273, 272, 'LDAP', '/advanconfig/ldapConfig', 'ldapConfig', 1, '91', 1, 1, '', 1, '2021-04-20 13:58:56', 1, '2021-04-20 13:58:56', 1, 1, NULL, 9, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (274, 12, '数据服务', '/advanconfig/dataService', 'dataService', 1, '921', 1, 1, '数据服务', 1, '2021-04-20 14:00:19', 1, '2021-05-12 15:42:06', 1, 1, NULL, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (275, 274, '应用注册', '/advanconfig/dataService/appRegister', 'appRegister', 1, '932', 1, 1, '应用注册', 1, '2021-04-20 14:02:55', 1, '2021-04-20 14:59:53', 1, 1, NULL, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (276, 274, 'API管理', '/advanconfig/dataService/apiConfig', 'apiConfig', 1, '94', 1, 1, 'API配置', 1, '2021-04-20 14:03:36', 1, '2021-04-20 17:05:32', 1, 1, NULL, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (283, 18, '保存系统角色系统权限配置 api级别', '/v1/myDepartment/saveDeptRolePermissionApi', NULL, 2, '/v1/myDepartment/saveDeptRolePermissionApi', 1, 1, '保存系统角色系统权限配置 api级别', 1, '2021-04-27 10:01:42', 1, '2021-04-27 10:01:42', 1, 1, 2, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (288, 12, '后端API管理', '/resource/apiManage', 'apiManage', 1, 'HouDuanAPIGuanLi11', 1, 1, '后端API管理', 1, '2021-05-11 10:08:40', 1, '2021-05-11 10:08:40', 1, 1, NULL, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (289, 17, '保存部门权限配置 api', '/v1/departmentManage/saveDeptPermissionApi', NULL, 2, '/v1/departmentManage/saveDeptPermissionApi', 1, 1, '保存部门权限配置 api', 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, 1, 0, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (290, 17, '保存部门角色系统权限配置 api', '/v1/departmentManage/saveDeptRolePermissionApi', NULL, 2, '/v1/departmentManage/saveDeptRolePermissionApi', 1, 1, '保存部门角色系统权限配置 api', 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (291, 17, '解绑邮箱', '/v1/departmentManage/unbindEmail', NULL, 2, '/v1/departmentManage/unbindEmail', 1, 1, '解绑邮箱', 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, 1, 2, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (292, 17, '解绑手机号', '/v1/departmentManage/unbindPhone', NULL, 2, '/v1/departmentManage/unbindPhone', 1, 1, '解绑手机号', 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, 1, 2, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (293, 15, '保存部门角色系统权限配置 api级别', '/v1/sysRoleManage/saveDeptPermissionApi', NULL, 2, '/v1/sysRoleManage/saveDeptPermissionApi', 1, 1, '保存部门角色系统权限配置 api级别', 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, 1, 2, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (294, 15, '保存系统角色系统权限配置 api级别', '/v1/sysRoleManage/saveSysPermissionApi', NULL, 2, '/v1/sysRoleManage/saveSysPermissionApi', 1, 1, '保存系统角色系统权限配置 api级别', 1, '2021-05-11 10:22:23', 1, '2021-05-11 10:22:23', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (295, 276, '分页获取api管理列表', '/v1/appApiRegistered/getOpenApiPageList', NULL, 2, '/v1/appApiRegistered/getOpenApiPageList', 1, 1, '分页获取api管理列表', 1, '2021-05-11 10:24:34', 1, '2021-05-11 10:24:34', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (296, 276, '获取sql配置', '/v1/appApiRegistered/getSqlConfiguration', NULL, 2, '/v1/appApiRegistered/getSqlConfiguration', 1, 1, '获取sql配置', 1, '2021-05-11 10:24:34', 1, '2021-05-11 10:24:34', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (297, 276, '锁定api', '/v1/appApiRegistered/lockOpenAppApi', NULL, 2, '/v1/appApiRegistered/lockOpenAppApi', 1, 1, '锁定api', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 2, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (298, 276, '注册api', '/v1/appApiRegistered/registeredOpenAppApi', NULL, 2, '/v1/appApiRegistered/registeredOpenAppApi', 1, 1, '注册api', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 0, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (299, 276, '移除api', '/v1/appApiRegistered/removeOpenAppApi', NULL, 2, '/v1/appApiRegistered/removeOpenAppApi', 1, 1, '移除api', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 1, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (300, 276, '保存sql配置', '/v1/appApiRegistered/saveSqlConfiguration', NULL, 2, '/v1/appApiRegistered/saveSqlConfiguration', 1, 1, '保存sql配置', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 2, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (301, 276, '解锁api', '/v1/appApiRegistered/unLockOpenAppApi', NULL, 2, '/v1/appApiRegistered/unLockOpenAppApi', 1, 1, '解锁api', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 2, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (302, 276, '修改api', '/v1/appApiRegistered/updateOpenAppApi', NULL, 2, '/v1/appApiRegistered/updateOpenAppApi', 1, 1, '修改api', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 0, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (303, 275, '添加已有接口权限', '/v1/appRegistered/apiBindApp', NULL, 2, '/v1/appRegistered/apiBindApp', 1, 1, '添加已有接口权限', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 2, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (304, 275, '移除接口权限', '/v1/appRegistered/apiUnBindApp', NULL, 2, '/v1/appRegistered/apiUnBindApp', 1, 1, '移除接口权限', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 1, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (305, 275, '创建应用', '/v1/appRegistered/createOpenApp', NULL, 2, '/v1/appRegistered/createOpenApp', 1, 1, '创建应用', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 0, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (306, 275, '删除应用', '/v1/appRegistered/deleteOpenApp', NULL, 2, '/v1/appRegistered/deleteOpenApp', 1, 1, '删除应用', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 1, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (307, 275, '分页获取权限接口列表', '/v1/appRegistered/getOpenApiPageList', NULL, 2, '/v1/appRegistered/getOpenApiPageList', 1, 1, '分页获取权限接口列表', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (308, 275, '获取应用详情', '/v1/appRegistered/getOpenApp', NULL, 2, '/v1/appRegistered/getOpenApp', 1, 1, '获取应用详情', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 3, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (309, 275, '分页获取应用列表', '/v1/appRegistered/getOpenAppPageList', NULL, 2, '/v1/appRegistered/getOpenAppPageList', 1, 1, '分页获取应用列表', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 3, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (310, 275, '更新应用', '/v1/appRegistered/updateOpenApp', NULL, 2, '/v1/appRegistered/updateOpenApp', 1, 1, '更新应用', 1, '2021-05-11 10:24:35', 1, '2021-05-11 10:24:35', 1, 1, 2, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (311, 12, '接口服务注册', '/resource/interfaceServiceReg', 'interfaceServiceReg', 1, 'JieKouFuWuZhuCe12', 1, 1, '接口服务注册', 1, '2021-05-11 11:41:53', 1, '2021-05-11 11:42:31', 1, 1, NULL, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (312, 5, '网关数据获取', '/v1/networkConfig/getGatewayInfo', NULL, 2, '/v1/networkConfig/getGatewayInfo', 1, 1, '网关数据获取', 1, '2021-05-11 14:53:48', 1, '2021-05-11 14:53:48', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (313, 5, '修改网关信息', '/v1/networkConfig/updateGatewayConfig', NULL, 2, '/v1/networkConfig/updateGatewayConfig', 1, 1, '修改网关信息', 1, '2021-05-11 14:53:48', 1, '2021-05-11 14:53:48', 1, 1, 2, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (316, 3, '校验更新邮箱验证码', '/v1/setAccount/checkUpdateEmailVerificationCode', '/v1/setAccount/checkUpdateEmailVerificationCode', 2, 'XiaoYanGengXinYouXiangYanZheng', 1, 1, '', 1, '2021-05-14 16:02:10', 1, '2021-05-14 16:02:10', 1, 1, 3, 10, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (317, 3, '校验更新手机验证码', '/v1/setAccount/checkUpdatePhoneVerificationCode', '/v1/setAccount/checkUpdatePhoneVerificationCode', 2, 'XiaoYanGengXinShouJiYanZhengMa', 1, 1, '', 1, '2021-05-14 16:02:56', 1, '2021-05-14 16:03:37', 1, 1, 3, 10, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (318, 3, '通过旧密码更新密码', '/v1/setAccount/updatePasswordByOldPassword', '/v1/setAccount/updatePasswordByOldPassword', 2, 'TongGuoJiuMiMaGengXinMiMa', 1, 1, '', 1, '2021-05-14 16:20:58', 1, '2021-05-14 16:20:58', 1, 1, 2, 10, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (326, 25, '获取平台配置信息', '/v1/platformSetting/getPlatformSetting', '/v1/platformSetting/getPlatformSetting', 2, '/v1/platformSetting/getPlatfor', 1, 1, '', 1, '2021-05-17 11:35:43', 1, '2021-05-17 11:35:43', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (327, 25, '保存平台配置信息', '/v1/platformSetting/savePlatformSetting', '/v1/platformSetting/savePlatformSetting', 2, '/v1/platformSetting/savePlatfo', 1, 1, '', 1, '2021-05-17 11:36:09', 1, '2021-05-17 11:36:09', 1, 1, 2, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (328, 25, '重置平台配置信息', '/v1/platformSetting/resetPlatformSetting', '/v1/platformSetting/resetPlatformSetting', 2, '/v1/platformSetting/resetPlatf', 1, 1, '', 1, '2021-05-17 11:36:49', 1, '2021-05-17 11:36:49', 1, 1, 2, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (330, 17, '授权用户部门角色', '/v1/departmentManage/authUserDeptRole', NULL, 2, '/v1/departmentManage/authUserDeptRole', 1, 1, '授权用户部门角色', 1, '2021-05-18 13:49:27', 1, '2021-05-18 13:49:27', 1, 1, 0, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (331, 1, '获取搜索条件', 'HuoQuSouSuoTiaoJian', '/', 2, 'HuoQuSouSuoTiaoJian', 1, 1, '', 52, '2021-05-19 09:13:44', 52, '2021-05-19 09:13:44', 1, 1, 3, 2, 1, 1, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (332, 24, '获取系统操作日志', 'HuoQuXiTongCaoZuoRiZhi', '/', 2, 'HuoQuXiTongCaoZuoRiZhi', 1, 1, '', 52, '2021-05-19 09:27:27', 52, '2021-05-19 09:27:27', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (333, 30, '获取数据字典回收站', 'HuoQuShuJuZiDianHuiShouZhan', '/', 2, 'HuoQuShuJuZiDianHuiShouZhan', 1, 1, '', 1, '2021-05-19 13:58:51', 1, '2021-05-19 13:58:51', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (334, 30, '还原数据字典', 'HuaiYuanShuJuZiDian', '/', 2, 'HuaiYuanShuJuZiDian', 1, 1, '', 1, '2021-05-19 13:59:16', 1, '2021-05-19 13:59:16', 1, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (335, 30, '刷新字典缓存', 'ShuaXinZiDianHuanCun', '/', 2, 'ShuaXinZiDianHuanCun', 1, 1, '', 1, '2021-05-19 13:59:34', 1, '2021-05-19 13:59:47', 1, 1, 2, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (346, 17, '导出部门', '/v1/departmentManage/batchExportSysDepartment', NULL, 2, '/v1/departmentManage/batchExportSysDepartment', 1, 1, '导出部门', 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (347, 17, '导入部门', '/v1/departmentManage/batchImportSysDepartment', NULL, 2, '/v1/departmentManage/batchImportSysDepartment', 1, 1, '导入部门', 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (348, 17, '获取部门导入模板链接', '/v1/departmentManage/getImportExcelFile', NULL, 2, '/v1/departmentManage/getImportExcelFile', 1, 1, '获取部门导入模板链接', 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (349, 16, '导出用户', '/v1/sysUserManage/batchExportSysUser', NULL, 2, '/v1/sysUserManage/batchExportSysUser', 1, 1, '导出用户', 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (350, 16, '导入用户', '/v1/sysUserManage/batchImportSysUser', NULL, 2, '/v1/sysUserManage/batchImportSysUser', 1, 1, '导入用户', 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (351, 16, '获取用户导入模板链接', '/v1/sysUserManage/getImportExcelFile', NULL, 2, '/v1/sysUserManage/getImportExcelFile', 1, 1, '获取用户导入模板链接', 1, '2021-05-31 10:47:19', 1, '2021-05-31 10:47:19', 1, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (420, 12, '表格管理', '/resource/formsControl', 'formsControl', 1, 'BiaoGeGuanLi', 1, 1, '', 1, '2021-06-28 10:23:57', 1, '2021-07-05 09:38:50', 1, 1, NULL, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (430, 373, '获取项目概述列表', '/v1/projectManage/listProjectOverview', NULL, 2, '/v1/projectManage/listProjectOverview', 1, 1, '获取项目概述列表', 1, '2021-06-30 16:03:30', 1, '2021-06-30 16:03:30', 1, 1, 3, 2, 2, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (431, 365, '添加项目关联设备分组', '/v1/project/addDeviceGroup', NULL, 2, '/v1/project/addDeviceGroup', 1, 1, '添加项目关联设备分组', 1, '2021-07-01 11:54:26', 1, '2021-07-01 11:54:26', 1, 1, 0, 1, 2, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (432, 365, '添加项目', '/v1/project/addSysProject', NULL, 2, '/v1/project/addSysProject', 1, 1, '添加项目', 1, '2021-07-01 11:54:26', 1, '2021-07-01 11:54:26', 1, 1, 0, 2, 2, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (433, 365, '添加设备关联项目', '/v1/project/changeProjectDevice', NULL, 2, '/v1/project/changeProjectDevice', 1, 1, '添加设备关联项目', 1, '2021-07-01 11:54:26', 1, '2021-07-01 11:54:26', 1, 1, 2, 3, 2, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (444, 23, '删除定时重启', '/v1/sysSetting/deleteRestartRegularly', NULL, 2, '/v1/sysSetting/deleteRestartRegularly', 1, 1, '删除定时重启', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, 1, 1, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (445, 24, '获取日志备份配置信息', '/v1/sysSetting/getLogBackupConfig', '/', 2, '/v1/sysSetting/getLogBackupConfig', 1, 1, '获取日志备份配置信息', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:50:24', 1, 1, 3, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (446, 24, '获取日志备份列表', '/v1/sysSetting/getLogBackupList', '/', 2, '/v1/sysSetting/getLogBackupList', 1, 1, '获取日志备份列表', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:50:10', 1, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (447, 23, '获取系统备份列表', '/v1/sysSetting/getSysBackupList', NULL, 2, '/v1/sysSetting/getSysBackupList', 1, 1, '获取系统备份列表', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, 1, 3, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (448, 23, '获取系统备份配置信息', '/v1/sysSetting/getSystemBackupsConfig', NULL, 2, '/v1/sysSetting/getSystemBackupsConfig', 1, 1, '获取系统备份配置信息', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, 1, 3, 8, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (449, 23, '获取定时重启列表', '/v1/sysSetting/listRestartRegularly', NULL, 2, '/v1/sysSetting/listRestartRegularly', 1, 1, '获取定时重启列表', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, 1, 3, 9, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (450, 24, '日志备份立即备份', '/v1/sysSetting/logBackupNow', '、', 2, '/v1/sysSetting/logBackupNow', 1, 1, '日志备份立即备份', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:49:28', 1, 1, 0, 10, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (451, 24, '保存日志备份配置信息', '/v1/sysSetting/saveLogBackupConfig', '/', 2, '/v1/sysSetting/saveLogBackupConfig', 1, 1, '保存日志备份配置信息', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:49:38', 1, 1, 2, 11, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (452, 22, '保存ntp服务器信息', '/v1/sysSetting/saveNtpInfo', '/', 2, '/v1/sysSetting/saveNtpInfo', 1, 1, '保存ntp服务器信息', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:48:25', 1, 1, 2, 12, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (453, 23, '保存定时重启', '/v1/sysSetting/saveRestartRegularly', NULL, 2, '/v1/sysSetting/saveRestartRegularly', 1, 1, '保存定时重启', 1, '2021-07-01 14:13:10', 1, '2021-07-01 14:13:10', 1, 1, 0, 13, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (456, 23, '删除系统备份', '/v1/sysSetting/deleteBackup', NULL, 2, '/v1/sysSetting/deleteBackup', 1, 1, '删除系统备份', 1, '2021-07-01 14:21:08', 1, '2021-07-01 14:21:08', 1, 1, 1, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (459, 23, '恢复备份', '/v1/sysSetting/backupRecovery', NULL, 2, '/v1/sysSetting/backupRecovery', 1, 1, '恢复备份', 1, '2021-07-01 14:45:26', 1, '2021-07-01 14:45:26', 1, 1, 2, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (460, 22, '获取NTF信息', '/v1/sysSetting/getNtpInfo', '/', 2, '/v1/sysSetting/getNtpInfo', 1, 1, '获取NTF信息', 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:48:42', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (461, 23, '系统备份立即备份', '/v1/sysSetting/systemBackupNow', NULL, 2, '/v1/sysSetting/systemBackupNow', 1, 1, '系统备份立即备份', 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, 1, 0, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (462, 23, '系统备份配置信息保存', '/v1/sysSetting/systemBackupsConfig', NULL, 2, '/v1/sysSetting/systemBackupsConfig', 1, 1, '系统备份配置信息保存', 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, 1, 2, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (463, 23, '恢复系统出厂设置', '/v1/sysSetting/systemFactoryReset', NULL, 2, '/v1/sysSetting/systemFactoryReset', 1, 1, '恢复系统出厂设置', 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, 1, 2, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (464, 23, '系统重启', '/v1/sysSetting/systemReboot', NULL, 2, '/v1/sysSetting/systemReboot', 1, 1, '系统重启', 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:45:30', 1, 1, 2, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (465, 22, '测试NTP服务器', '/v1/sysSetting/testNtpServer', '/', 2, '/v1/sysSetting/testNtpServer', 1, 1, '测试NTP服务器', 1, '2021-07-01 14:45:30', 1, '2021-07-01 14:48:50', 1, 1, 3, 7, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (466, 420, '添加属性', '/v1/manageForm/addAttributes', NULL, 2, '/v1/manageForm/addAttributes', 1, 1, '添加属性', 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, 1, 0, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (467, 420, '添加表格', '/v1/manageForm/addForm', NULL, 2, '/v1/manageForm/addForm', 1, 1, '添加表格', 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (468, 420, '删除表格', '/v1/manageForm/deleteForm', NULL, 2, '/v1/manageForm/deleteForm', 1, 1, '删除表格', 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, 1, 1, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (469, 420, '获取属性', '/v1/manageForm/getAttributes', NULL, 2, '/v1/manageForm/getAttributes', 1, 1, '获取属性', 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, 1, 3, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (470, 420, '获取表格列表', '/v1/manageForm/listForms', NULL, 2, '/v1/manageForm/listForms', 1, 1, '获取表格列表', 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, 1, 3, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (471, 420, '更新表格', '/v1/manageForm/updateForm', NULL, 2, '/v1/manageForm/updateForm', 1, 1, '更新表格', 1, '2021-07-08 09:26:38', 1, '2021-07-08 09:26:38', 1, 1, 2, 6, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (473, 365, '查找项目动态信息', '/v1/project/findProjectDynamicInfoByType', NULL, 2, '/v1/project/findProjectDynamicInfoByType', 1, 1, '查找项目动态信息', 1, '2021-07-08 11:28:53', 1, '2021-07-08 11:28:53', 1, 1, 3, 10, 2, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (555, 275, '是否启用该应用', '/v1/appRegistered/enableApp', NULL, 2, '/v1/appRegistered/enableApp', 1, 1, '是否启用该应用', 1, '2021-07-19 15:46:18', 1, '2021-07-19 15:46:18', 1, 1, 2, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1002, 28, '批量导出Excel', '/v1/managePermission/batchExportExcel', '/', 2, '/v1/managePermission/batchExportExcel', 1, 1, '批量导出Excel', 1, '2021-10-19 11:31:08', 1, '2021-11-24 15:27:27', 1, 1, 3, 1, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1003, 28, '导出Excel', '/v1/managePermission/exportExcel', '/', 2, '/v1/managePermission/exportExcel', 1, 1, '导出Excel', 1, '2021-10-19 11:31:08', 1, '2021-11-24 15:27:31', 1, 1, 3, 2, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1004, 28, '获取模板', '/v1/managePermission/getMould', '/', 2, '/v1/managePermission/getMould', 1, 1, '获取模板', 1, '2021-10-19 11:31:08', 1, '2021-11-24 15:27:37', 1, 1, 3, 3, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1005, 28, '导入Excel', '/v1/managePermission/importExcel', '/', 2, '/v1/managePermission/importExcel', 1, 1, '导入Excel', 1, '2021-10-19 11:31:08', 1, '2021-11-24 15:27:41', 1, 1, 3, 4, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1135, 274, '数据源管理', '/advanconfig/dataService/databaseManger', 'databaseManger', 1, 'ShuJuYuanGuanLi', 1, 1, '', 1, '2021-11-11 14:50:38', 1, '2021-11-24 15:25:46', 1, 1, NULL, 7, 1, 0, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1136, 30, '导出Excel', '/v1/sysDict/exportExcel', NULL, 2, '/v1/sysDict/exportExcel', 1, 1, '导出Excel', 1, '2021-11-12 14:19:16', 1, '2021-11-12 14:19:16', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1137, 30, '获取模板', '/v1/sysDict/getMould', NULL, 2, '/v1/sysDict/getMould', 1, 1, '获取模板', 1, '2021-11-12 14:19:16', 1, '2021-11-12 14:19:16', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1138, 30, '导入Excel', '/v1/sysDict/importExcel', NULL, 2, '/v1/sysDict/importExcel', 1, 1, '导入Excel', 1, '2021-11-12 14:19:16', 1, '2021-11-12 14:19:16', 1, 1, 3, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1140, 26, '获取通告详情', '/v1/myMessage/getUserAnnouncementInfo', '/v1/myMessage/getUserAnnouncementInfo', 2, '/v1/myMessage/getUserAnnouncementInfo', 1, 1, '获取通告详情', 1, '2021-11-15 14:21:03', 1, '2021-11-24 14:10:44', 1, 1, 3, 1, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1141, 26, '分页获取用户通告', '/v1/myMessage/getUserAnnouncementItemPage', '/v1/myMessage/getUserAnnouncementItemPage', 2, '/v1/myMessage/getUserAnnouncementItemPage', 1, 1, '分页获取用户通告', 1, '2021-11-15 14:21:03', 1, '2021-11-24 14:11:04', 1, 1, 3, 2, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1142, 26, '设置通知全部已读', '/v1/myMessage/readAllAnnouncement', '/v1/myMessage/readAllAnnouncement', 2, '/v1/myMessage/readAllAnnouncement', 1, 1, '设置通知全部已读', 1, '2021-11-15 14:21:03', 1, '2021-11-24 14:11:14', 1, 1, 2, 3, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1143, 26, '设置通知已读', '/v1/myMessage/readAnnouncement', '/v1/myMessage/readAnnouncement', 2, '/v1/myMessage/readAnnouncement', 1, 1, '设置通知已读', 1, '2021-11-15 14:21:03', 1, '2021-11-24 14:11:22', 1, 1, 2, 4, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1196, 8, '获取安全管理', '/v1/manageSecurity/getManageSecurity', NULL, 2, '/v1/manageSecurity/getManageSecurity', 1, 1, '获取安全管理', 1, '2021-11-15 15:20:30', 1, '2021-11-15 15:20:30', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1197, 8, '更新安全管理', '/v1/manageSecurity/updateManageSecurity', NULL, 2, '/v1/manageSecurity/updateManageSecurity', 1, 1, '更新安全管理', 1, '2021-11-15 15:20:30', 1, '2021-11-15 15:20:30', 1, 1, 2, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1209, 1135, '数据源测试连接', '/v1/openDatasource/connect', NULL, 2, '/v1/openDatasource/connect', 1, 1, '数据源测试连接', 1, '2021-11-17 12:00:38', 1, '2021-11-17 12:00:38', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1210, 1135, '添加数据源', '/v1/openDatasource/createDatasource', NULL, 2, '/v1/openDatasource/createDatasource', 1, 1, '添加数据源', 1, '2021-11-17 12:00:39', 1, '2021-11-17 12:00:39', 1, 1, 0, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1211, 1135, '删除数据源', '/v1/openDatasource/deleteDatasource', NULL, 2, '/v1/openDatasource/deleteDatasource', 1, 1, '删除数据源', 1, '2021-11-17 12:00:39', 1, '2021-11-17 12:00:39', 1, 1, 1, 3, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1212, 1135, '查找数据源列表', '/v1/openDatasource/findDataSourceList', NULL, 2, '/v1/openDatasource/findDataSourceList', 1, 1, '查找数据源列表', 1, '2021-11-17 12:00:39', 1, '2021-11-17 12:00:39', 1, 1, 3, 4, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1213, 1135, '修改数据源', '/v1/openDatasource/updateDatasource', NULL, 2, '/v1/openDatasource/updateDatasource', 1, 1, '修改数据源', 1, '2021-11-17 12:00:39', 1, '2021-11-17 12:00:39', 1, 1, 2, 5, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1287, 27, '添加通知配置', '/v1/sysAnnouncementReceiveConfig/addAnnouncementConfig', '/', 2, '/v1/sysAnnouncementReceiveConfig/addAnnouncementConfig', 1, 1, '添加通知配置', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:16:31', 1, 1, 0, 1, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1288, 27, '批量删除消息通知配置', '/v1/sysAnnouncementReceiveConfig/deleteAnnouncementConfig', '/', 2, '/v1/sysAnnouncementReceiveConfig/deleteAnnouncementConfig', 1, 1, '批量删除消息通知配置', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:16:36', 1, 1, 1, 2, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1289, 27, '编辑消息通知配置', '/v1/sysAnnouncementReceiveConfig/editAnnouncementConfig', '/', 2, '/v1/sysAnnouncementReceiveConfig/editAnnouncementConfig', 1, 1, '编辑消息通知配置', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:16:44', 1, 1, 2, 3, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1290, 27, '获取通告配置详情', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigInfo', '/', 2, '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigInfo', 1, 1, '获取通告配置详情', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:17:00', 1, 1, 3, 4, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1291, 27, '获取消息通知配置', '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigList', '/', 2, '/v1/sysAnnouncementReceiveConfig/getAnnouncementConfigList', 1, 1, '获取消息通知配置', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:17:07', 1, 1, 3, 5, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1292, 27, '获取消息接收配置', '/v1/sysAnnouncementReceiveConfig/getAnnouncementReceiveConfigList', '/', 2, '/v1/sysAnnouncementReceiveConfig/getAnnouncementReceiveConfigList', 1, 1, '获取消息接收配置', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:17:14', 1, 1, 3, 6, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1293, 27, '部门角色获取部门列表', '/v1/sysAnnouncementReceiveConfig/getDeptDepartmentList', '/', 2, '/v1/sysAnnouncementReceiveConfig/getDeptDepartmentList', 1, 1, '部门角色获取部门列表', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:17:23', 1, 1, 3, 7, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1294, 27, '获取用户分页列表', '/v1/sysAnnouncementReceiveConfig/getListSysUser', '/', 2, '/v1/sysAnnouncementReceiveConfig/getListSysUser', 1, 1, '获取用户分页列表', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:17:36', 1, 1, 3, 8, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1295, 27, '恢复默认', '/v1/sysAnnouncementReceiveConfig/resetReceiveConfig', '/', 2, '/v1/sysAnnouncementReceiveConfig/resetReceiveConfig', 1, 1, '恢复默认', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:17:28', 1, 1, 2, 9, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1296, 27, '保存消息通知配置', '/v1/sysAnnouncementReceiveConfig/saveAnnouncementConfig', '/', 2, '/v1/sysAnnouncementReceiveConfig/saveAnnouncementConfig', 1, 1, '保存消息通知配置', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:17:44', 1, 1, 2, 10, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1297, 27, '保存消息接收配置', '/v1/sysAnnouncementReceiveConfig/saveAnnouncementReceiveConfig', '/', 2, '/v1/sysAnnouncementReceiveConfig/saveAnnouncementReceiveConfig', 1, 1, '保存消息接收配置', 1, '2021-11-23 16:41:28', 1, '2021-11-24 14:17:50', 1, 1, 2, 11, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1298, 273, '获取ldap', '/v1/ldap/getLdap', NULL, 2, '/v1/ldap/getLdap', 1, 1, '获取ldap', 1, '2021-11-24 10:22:27', 1, '2021-11-24 10:22:27', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1299, 273, '更新ldap', '/v1/ldap/updateLdap', NULL, 2, '/v1/ldap/updateLdap', 1, 1, '更新ldap', 1, '2021-11-24 10:22:28', 1, '2021-11-24 10:22:28', 1, 1, 2, 2, 1, 0, 0, NULL, NULL, NULL);
INSERT INTO `sys_page_permission` VALUES (1326, 272, '钉钉对接配置', '/advanconfig/dingDingConfig', 'ldapConfigServe', 1, 'DingDingDuiJiePeiZhis', 1, 1, 'gaoji ', 1, '2021-12-23 15:18:34', 1, '2021-12-23 15:18:34', 1, 1, NULL, 2, 1, 1, 0, '', '', 0);
INSERT INTO `sys_page_permission` VALUES (1327, 1326, '获取配置', '/v1/sysDingDingConfig/getSysDingDingConfigVO', '/', 2, '/v1/sysDingDingConfig/getSysDingDingConfigVO', 1, 1, '获取配置', 1, '2021-12-29 16:46:02', 1, '2021-12-29 16:46:02', 1, 1, 3, 1, 1, 0, 0, NULL, NULL, 0);
INSERT INTO `sys_page_permission` VALUES (1328, 1326, '同步', '/v1/sysDingDingConfig/syncDingDingUserData', '/', 2, '/v1/sysDingDingConfig/syncDingDingUserData', 1, 1, '同步', 1, '2021-12-29 16:46:03', 1, '2021-12-29 16:46:03', 1, 1, 3, 2, 1, 0, 0, NULL, NULL, 0);
INSERT INTO `sys_page_permission` VALUES (1329, 1326, '保存配置', '/v1/sysDingDingConfig/saveConfig', '/', 2, '/v1/sysDingDingConfig/saveConfig', 1, 1, '保存配置', 1, '2021-12-29 16:46:03', 1, '2021-12-29 16:46:03', 1, 1, 0, 3, 1, 0, 0, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_report
-- ----------------------------
DROP TABLE IF EXISTS `sys_report`;
CREATE TABLE `sys_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '报告ID',
  `report_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报告编码',
  `report_tile` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报告名称',
  `reporter` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报告人',
  `report_body` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '报告内容',
  `report_phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告人联系方式',
  `report_type` int(11) NOT NULL COMMENT '报告类型:0-问题反馈;1-改进意见',
  `report_time` datetime(0) NOT NULL COMMENT '报告日期',
  `report_address` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告地址',
  `report_stat` int(11) NOT NULL COMMENT '报告状态:0-待受理;1-待确认;2-已处理;3-驳回;4-撤销',
  `create_by` int(11) NOT NULL COMMENT '报告创建人ID',
  `create_time` datetime(0) NOT NULL COMMENT '报告创建时间',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '报告信息更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '报告信息更新时间',
  `reply_body` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '回复内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_report
-- ----------------------------

-- ----------------------------
-- Table structure for sys_request_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_request_log`;
CREATE TABLE `sys_request_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统请求日志ID',
  `request_method` int(11) NOT NULL COMMENT '接口请求方式',
  `request_time` datetime(0) NOT NULL COMMENT '接口请求时间',
  `request_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口请求IP',
  `request_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口请求路径',
  `request_stat` int(11) NOT NULL COMMENT '接口请求状态',
  `request_cost_time` int(11) NULL DEFAULT NULL COMMENT '接口请求耗时',
  `request_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口请求人',
  `request_param` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口请求参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统请求信息日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_request_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统角色ID',
  `sys_department_id` int(11) NULL DEFAULT NULL COMMENT '系统部门ID',
  `role_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统角色名称',
  `role_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统角色编码',
  `role_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统角色描述',
  `create_by` int(11) NOT NULL COMMENT '系统角色创建人ID',
  `create_time` datetime(0) NOT NULL COMMENT '系统角色创建时间',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '系统角色信息更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '系统角色信息更新时间',
  `role_data_scope` int(11) NOT NULL COMMENT '角色数据权限范围:0-仅本人;1-本部门;2-本部门及下属部门;3-全公司;4-指定部门',
  `ip_filter_type` int(1) NULL DEFAULT NULL COMMENT '角色登录IP过滤方式:0-禁止;1-允许',
  `ip_list` json NULL COMMENT '系统角色登录过滤IP名单',
  `assign_dept_ids` json NULL COMMENT '指定部门（本部门及下属部门）role_data_scope为4时候',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色信息，直接权限分配主体' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, NULL, '超级管理员', 'top_admin', NULL, 1, '2021-04-15 15:52:48', NULL, NULL, 3, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data`;
CREATE TABLE `sys_role_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `data_set_id` int(11) NOT NULL COMMENT '数据集ID',
  `sys_role_id` int(11) NOT NULL COMMENT '系统角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_data
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sys_role_id` int(11) NOT NULL COMMENT '系统角色ID',
  `page_id` int(11) NOT NULL COMMENT '页面资源ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_search_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_search_config`;
CREATE TABLE `sys_search_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端标题',
  `req_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端请求名',
  `db_column` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据字段',
  `page_id` int(11) NULL DEFAULT NULL COMMENT '页面id',
  `search_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '高级搜索编码',
  `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典名 此字段不为空，则为选择框',
  `sql_text` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '自定义预搜索sql查询语句 此字段不为空时，则提供预搜索功能',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `input_type` int(2) NULL DEFAULT 1 COMMENT '输入框类型 1文本输入框 2数字输入框 3日期输入框 4下拉框类型',
  `default_rule` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认搜索规则',
  `max_size` int(11) NULL DEFAULT 100 COMMENT '最大输入数',
  `min_size` int(11) NULL DEFAULT 1 COMMENT '最小输入数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 655 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_search_config
-- ----------------------------
INSERT INTO `sys_search_config` VALUES (6, '报告状态', 'reportStat', 'report_stat', 13, 'personal_feedback_search', 'report_stat', NULL, 2, '报告状态', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (7, '报告类型', 'reportType', 'report_type', 13, 'personal_feedback_search', 'report_type', NULL, 3, '报告类型', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (8, '标题', 'reportTile', 'report_tile', 13, 'personal_feedback_search', NULL, NULL, 1, '报告名称', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (9, '开始时间', 'beginTime', 'report_time', 13, 'personal_feedback_search', NULL, NULL, 4, '开始时间', 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (10, '结束时间', 'endTime', 'report_time', 13, 'personal_feedback_search', NULL, NULL, 5, '结束时间', 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (11, '报告人', 'reporter', 'reporter', 14, 'admin_feedback_search', NULL, NULL, 2, '报告人', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (12, '报告人帐号', 'reportUserAccountName', 'u.user_account_name', 14, 'admin_feedback_search', NULL, NULL, 3, '报告帐户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (13, '报告状态', 'reportStat', 'report_stat', 14, 'admin_feedback_search', 'report_stat', NULL, 4, '报告状态', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (14, '报告类型', 'reportType', 'report_type', 14, 'admin_feedback_search', 'report_type', NULL, 2, '报告类型', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (15, '标题', 'reportTile', 'report_tile', 14, 'admin_feedback_search', NULL, NULL, 1, '报告名称', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (16, '开始时间', 'beginTime', 'report_time', 14, 'admin_feedback_search', NULL, NULL, 5, '开始时间', 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (17, '结束时间', 'endTime', 'report_time', 14, 'admin_feedback_search', NULL, NULL, 6, '结束时间', 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (18, '字典名', 'dictName', 'dict_name', 30, 'data_dictionary_search', NULL, NULL, 1, '字典名搜索', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (19, '字典编码', 'dictCode', 'dict_code', 30, 'data_dictionary_search', NULL, 'SELECT dict_code FROM sys_dict WHERE dict_code LIKE \'%#{text}%\'', 2, '字典编码搜索', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (20, '登录的用户名', 'loginName', 'login_name', 24, 'log_management_search', NULL, NULL, 1, '登录的用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (21, '登录的IP地址', 'loginIp', 'login_ip', 24, 'log_management_search', NULL, NULL, 2, '登录的IP地址', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (22, '登录的物理地址', 'loginLocation', 'login_location', 24, 'log_management_search', NULL, NULL, 3, '登录的物理地址', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (23, '开始时间', 'beginTime', 'login_time', 24, 'log_management_search', NULL, NULL, 4, '开始时间', 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (24, '结束时间', 'endTime', 'login_time', 24, 'log_management_search', NULL, NULL, 5, '结束时间', 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (25, '角色名称', 'roleName', 'role_name', 15, 'sys_role_search', NULL, NULL, 1, '角色名称', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (26, '角色编号', 'roleCode', 'role_code', 15, 'sys_role_search', NULL, NULL, 2, '角色编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (27, '角色描述', 'roleDescription', 'role_description', 15, 'sys_role_search', NULL, NULL, 3, '角色描述', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (29, '角色名称', 'departRoleName', 'a.depart_role_name', 15, 'sys_dept_search', NULL, NULL, 1, '角色名称', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (30, '角色编号', 'roleCode', 'a.role_code', 15, 'sys_dept_search', NULL, NULL, 2, '角色编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (31, '角色描述', 'description', 'a.description', 15, 'sys_dept_search', NULL, NULL, 3, '角色描述', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (32, '角色所属部门', 'deptName', 'b.dept_name', 15, 'sys_dept_search', NULL, NULL, 4, '角色所属部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (33, '登录账户', 'userAccountName', 'a.user_account_name', 16, 'sys_user_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (34, '用户名', 'userName', 'a.user_name', 16, 'sys_user_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (35, '编号', 'userCode', 'a.user_code', 16, 'sys_user_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (36, '登录账户', 'userAccountName', 'user_account_name', 17, 'dept_user_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (37, '用户名', 'userName', 'user_name', 17, 'dept_user_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (38, '编号', 'userCode', 'user_code', 17, 'dept_user_search', NULL, NULL, 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (39, '角色名称', 'departRoleName', 'a.depart_role_name', 17, 'dept_role_search', NULL, NULL, 1, '角色名称', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (40, '角色编号', 'roleCode', 'a.role_code', 17, 'dept_role_search', NULL, NULL, 2, '角色编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (41, '角色描述', 'description', 'a.description', 17, 'dept_role_search', NULL, NULL, 3, '角色描述', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (42, '登录账户', 'userAccountName', 'b.user_account_name', 15, 'sys_user_sys_auth_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (43, '用户名', 'userName', 'b.user_name', 15, 'sys_user_sys_auth_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (44, '编号', 'userCode', 'b.user_code', 15, 'sys_user_sys_auth_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (45, '登录账户', 'userAccountName', 'a.user_account_name', 15, 'sys_user_sys_not_auth_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (46, '用户名', 'userName', 'a.user_name', 15, 'sys_user_sys_not_auth_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (47, '编号', 'userCode', 'a.user_code', 15, 'sys_user_sys_not_auth_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (48, '登录账户', 'userAccountName', 'user_account_name', 15, 'sys_user_dept_not_auth_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (49, '用户名', 'userName', 'user_name', 15, 'sys_user_dept_not_auth_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (50, '编号', 'userCode', 'user_code', 15, 'sys_user_dept_not_auth_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (51, '登录账户', 'userAccountName', 'user_account_name', 15, 'sys_user_dept_auth_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (52, '用户名', 'userName', 'user_name', 15, 'sys_user_dept_auth_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (53, '编号', 'userCode', 'user_code', 15, 'sys_user_dept_auth_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (54, '性别', 'user_sex', 'a.user_sex', 15, 'sys_user_sys_not_auth_search', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (55, '用户所属部门', 'dept_name', 'c.dept_name', 15, 'sys_user_sys_not_auth_search', NULL, NULL, 5, '用户所属部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (56, '手机号', 'user_phone', 'a.user_phone', 15, 'sys_user_sys_not_auth_search', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (57, '角色所属部门', 'dept_name', 'b.dept_name', 15, 'sys_user_dept_auth_search', NULL, NULL, 4, '角色所属部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (58, '性别', 'user_sex', 'b.user_sex', 15, 'sys_user_dept_not_auth_search', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (59, '手机号', 'user_phone', 'b.user_phone', 15, 'sys_user_dept_not_auth_search', NULL, NULL, 5, '手机号', 1, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (60, '登录账户', 'userAccountName', 'user_account_name', 18, 'my_dept_sys_user_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (61, '用户名', 'userName', 'user_name', 18, 'my_dept_sys_user_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (62, '编号', 'userCode', 'user_code', 18, 'my_dept_sys_user_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (63, '角色名称', 'departRoleName', 'a.depart_role_name', 18, 'my_dept_role_search', NULL, NULL, 1, '角色名称', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (64, '角色编号', 'roleCode', 'a.role_code', 18, 'my_dept_role_search', NULL, NULL, 2, '角色编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (65, '角色描述', 'description', 'a.description', 18, 'my_dept_role_search', NULL, NULL, 3, '角色描述', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (66, '角色所属部门', 'dept_name', 'a.dept_name', 18, 'my_dept_role_search', NULL, NULL, 3, '角色所属部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (67, '标题', 'ann_title', 'a.ann_title', 19, 'notic_msg', NULL, NULL, 1, '标题', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (68, '通告状态', 'ann_send_status', 'a.ann_send_status', 19, 'notic_msg', 'ann_send_status', NULL, 2, '通告状态', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (69, '发布人', 'user_name', 'b.user_name', 19, 'notic_msg', NULL, NULL, 3, '发布人', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (70, '标题', 'ann_title', 'a.ann_title', 20, 'sys_msg', NULL, NULL, 1, '标题', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (71, '通告状态', 'ann_send_status', 'a.ann_send_status', 20, 'sys_msg', 'ann_send_status', NULL, 2, '通告状态', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (72, '标题', 'ann_title', 'a.ann_title', 21, 'warn_msg', NULL, NULL, 1, '标题', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (73, '通告状态', 'ann_send_status', 'a.ann_send_status', 21, 'warn_msg', 'ann_send_status', NULL, 2, '通告状态', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (75, '登录账户', 'userAccountName', 'a.user_account_name', 19, 'sys_user_sys_search_sys', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (76, '用户名', 'userName', 'a.user_name', 19, 'sys_user_sys_search_sys', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (77, '编号', 'userCode', 'a.user_code', 19, 'sys_user_sys_search_sys', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (78, '性别', 'user_sex', 'a.user_sex', 19, 'sys_user_sys_search_sys', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (79, '用户所属部门', 'dept_name', 'c.dept_name', 19, 'sys_user_sys_search_sys', NULL, NULL, 5, '用户所属部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (80, '手机号', 'user_phone', 'a.user_phone', 19, 'sys_user_sys_search_sys', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (81, '登录账户', 'userAccountName', 'a.user_account_name', 20, 'sys_user_sys_search_notic', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (82, '用户名', 'userName', 'a.user_name', 20, 'sys_user_sys_search_notic', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (83, '编号', 'userCode', 'a.user_code', 20, 'sys_user_sys_search_notic', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (84, '性别', 'user_sex', 'a.user_sex', 20, 'sys_user_sys_search_notic', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (85, '用户所属部门', 'dept_name', 'c.dept_name', 20, 'sys_user_sys_search_notic', NULL, NULL, 5, '用户所属部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (86, '手机号', 'user_phone', 'a.user_phone', 20, 'sys_user_sys_search_notic', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (87, '登录账户', 'userAccountName', 'a.user_account_name', 21, 'sys_user_sys_search_warn', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (88, '用户名', 'userName', 'a.user_name', 21, 'sys_user_sys_search_warn', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (89, '编号', 'userCode', 'a.user_code', 21, 'sys_user_sys_search_warn', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (90, '性别', 'user_sex', 'a.user_sex', 21, 'sys_user_sys_search_warn', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (91, '用户所属部门', 'dept_name', 'c.dept_name', 21, 'sys_user_sys_search_warn', NULL, NULL, 5, '用户所属部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (92, '手机号', 'user_phone', 'a.user_phone', 21, 'sys_user_sys_search_warn', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (93, '登录账户', 'userAccountName', 'a.user_account_name', 18, 'my_dept_user_add_has_user', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (94, '用户名', 'userName', 'a.user_name', 18, 'my_dept_user_add_has_user', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (95, '编号', 'userCode', 'a.user_code', 18, 'my_dept_user_add_has_user', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (96, '性别', 'user_sex', 'a.user_sex', 18, 'my_dept_user_add_has_user', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (97, '手机号', 'user_phone', 'a.user_phone', 18, 'my_dept_user_add_has_user', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (98, '登录账户', 'userAccountName', 'a.user_account_name', 18, 'my_dept_role_add_has_user', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (99, '用户名', 'userName', 'a.user_name', 18, 'my_dept_role_add_has_user', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (100, '编号', 'userCode', 'a.user_code', 18, 'my_dept_role_add_has_user', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (101, '性别', 'user_sex', 'a.user_sex', 18, 'my_dept_role_add_has_user', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (102, '手机号', 'user_phone', 'a.user_phone', 18, 'my_dept_role_add_has_user', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (103, '登录账户', 'userAccountName', 'b.user_account_name', 18, 'my_dept_role_user_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (104, '用户名', 'userName', 'b.user_name', 18, 'my_dept_role_user_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (105, '编号', 'userCode', 'b.user_code', 18, 'my_dept_role_user_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (106, '账号身份', 'user_sex', 'b.user_sysrole', 18, 'my_dept_role_user_search', 'userSysrole', NULL, 4, '账号身份', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (107, '登录账户', 'userAccountName', 'a.user_account_name', 17, 'sys_user_sys_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (108, '用户名', 'userName', 'a.user_name', 17, 'sys_user_sys_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (109, '编号', 'userCode', 'a.user_code', 17, 'sys_user_sys_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (110, '性别', 'user_sex', 'a.user_sex', 17, 'sys_user_sys_search', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (111, '手机号', 'user_phone', 'a.user_phone', 17, 'sys_user_sys_search', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (112, '登录账户', 'userAccountName', 'a.user_account_name', 17, 'dept_user_search_add', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (113, '用户名', 'userName', 'a.user_name', 17, 'dept_user_search_add', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (114, '编号', 'userCode', 'a.user_code', 17, 'dept_user_search_add', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (115, '性别', 'user_sex', 'a.user_sex', 17, 'dept_user_search_add', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (116, '手机号', 'user_phone', 'a.user_phone', 17, 'dept_user_search_add', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (117, '登录账户', 'userAccountName', 'a.user_account_name', 17, 'dept_role_user_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (118, '用户名', 'userName', 'a.user_name', 17, 'dept_role_user_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (119, '编号', 'userCode', 'a.user_code', 17, 'dept_role_user_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (120, '性别', 'user_sex', 'a.user_sex', 17, 'dept_role_user_search', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (121, '登录账户', 'userAccountName', 'a.user_account_name', 17, 'head_department_user_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (122, '用户名', 'userName', 'a.user_name', 17, 'head_department_user_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (123, '编号', 'userCode', 'a.user_code', 17, 'head_department_user_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (124, '性别', 'user_sex', 'a.user_sex', 17, 'head_department_user_search', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (125, '手机号', 'user_phone', 'a.user_phone', 17, 'head_department_user_search', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (126, '标题', 'ann_title', 'b.ann_title', 26, 'my_message_search', NULL, NULL, 1, '标题', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (127, '通告类型', 'ann_category', 'b.ann_category', 26, 'my_message_search', 'ann_category', NULL, 4, '通告状态', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (128, '阅读状态', 'ann_read_flag', 'a.ann_read_flag', 26, 'my_message_search', 'annReadFlag', NULL, 3, '阅读状态', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (129, '发布人', 'user_name', 'c.user_name', 26, 'my_message_search', NULL, NULL, 2, '发布人', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (130, '通知提醒项', 'config_name', 'config_name', 27, 'announcement_receive_config_search', NULL, NULL, 1, '通知提醒项', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (131, '通告类型', 'type', 'type', 27, 'announcement_receive_config_search', 'ann_category', NULL, 2, '通告类型', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (132, '通知提醒项', 'config_name', 'config_name', 108, 'announcement_config_search', NULL, NULL, 1, '通知提醒项', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (133, '通告类型', 'type', 'type', 108, 'announcement_config_search', 'ann_category', NULL, 2, '通告类型', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (134, '部门', 'dept_name', 'c.dept_name', 16, 'sys_user_search', NULL, '', 3, '部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (135, '系统角色', 'role_name', 'sf.role_name', 16, 'sys_user_search', NULL, '', 3, '系统角色', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (136, '状态', 'userStatus', 'a.user_status', 16, 'sys_user_search', 'userStatus', '', 3, '状态', 4, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (137, '部门', 'dept_name', 'c.dept_name', 17, 'dept_user_search', NULL, '', 3, '部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (138, '部门角色', 'depart_role_name', 'sdf.depart_role_name', 17, 'dept_user_search', NULL, '', 3, '部门角色', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (139, '状态', 'userStatus', 'a.user_status', 17, 'dept_user_search', 'userStatus', '', 3, '状态', 4, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (140, '部门', 'dept_name', 'c.dept_name', 18, 'my_dept_sys_user_search', NULL, '', 3, '部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (141, '部门角色', 'depart_role_name', 'sdf.depart_role_name', 18, 'my_dept_sys_user_search', NULL, '', 3, '部门角色', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (142, '状态', 'userStatus', 'a.user_status', 18, 'my_dept_sys_user_search', 'userStatus', '', 3, '状态', 4, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (143, '页面名称', 'pageTitle', 'p.page_title', 28, 'page_resources_search', NULL, NULL, 1, '页面名称', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (144, '页面编码', 'pageCode', 'p.page_code', 28, 'page_resources_search', NULL, NULL, 2, '页面编码', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (145, '页面状态', 'pageStatus', 'p.page_status', 28, 'page_resources_search', 'page_status', NULL, 3, '页面状态', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (146, '页面类型', 'pageType', 'p.page_type', 28, 'page_resources_search', 'page_type', NULL, 4, '页面类型', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (147, '模块名称', 'sysPageModuleTypeName', 't.name', 28, 'page_resources_search', '', 'SELECT 	p.sys_page_module_type_id  FROM 	sys_page_permission p 	JOIN sys_page_module_type t ON t.id = p.sys_page_module_type_id  WHERE 	t.NAME LIKE \'%text%\'  GROUP BY 	p.sys_page_module_type_id', 5, '模块名称', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (148, '接口名称', 'apiName', 'api_name', 29, 'api_resources_search', NULL, NULL, 1, '接口名称', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (149, '接口编码', 'apiCode', 'api_code', 29, 'api_resources_search', NULL, NULL, 2, '接口编码', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (150, '接口路径', 'apiUrl', 'api_url', 29, 'api_resources_search', NULL, NULL, 3, '接口路径', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (151, '关联页面', 'pageTitle', 'p.page_title', 29, 'api_resources_search', NULL, NULL, 4, '关联页面', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (152, '接口请求方式', 'apiMethod', 'api_method', 29, 'api_resources_search', 'api_method', NULL, 5, '接口请求方式', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (153, '接口鉴权方式', 'apiAuthType', 'api_auth_type', 29, 'api_resources_search', 'api_auth_type', NULL, 6, '接口鉴权方式', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (154, '是否全员可见 ', 'visibleToAll', 'p.visible_to_all', 28, 'page_resources_search', 'visible_to_all', NULL, 6, '是否全员可见 0-否 1-是', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (155, '日志内容', 'syslogContent', 'syslog_content', 332, 'sys_log_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (156, '操作人', 'operateBy', 'operate_by', 332, 'sys_log_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (157, '操作类型', 'operate_type', 'operate_type', 332, 'sys_log_search', 'log_operate_type', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (158, '设备名称', 'nickName', 'nick_name', 358, 'device_list_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (159, '设备编码', 'deviceCode', 'device_code', 358, 'device_list_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (160, '设备名称', 'nickName', 'd.nick_name', 358, 'add_device_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (161, '设备编码', 'deviceCode', 'd.device_code', 358, 'add_device_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (162, '设备序号', 'deviceName', 'd.device_name', 358, 'add_device_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (163, '设备种类', 'productName', 'sp.product_name', 358, 'add_device_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (164, '负责人', 'manager', 'd.manager', 358, 'add_device_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (165, '联系方式', 'contactInfo', 'd.contact_info', 358, 'add_device_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (166, '分组名称', 'groupName', 'd1.group_name', 359, 'device_group_overview_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (167, '上级分组', 'groupDescription', 'd2.group_name', 359, 'device_group_overview_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (168, '设备名称', 'nickName', 'd.nick_name', 359, 'product_property_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (169, '设备编码', 'deviceCode', 'd.device_code', 359, 'product_property_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (170, '设备序号', 'deviceName', 'd.device_name', 359, 'product_property_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (171, '设备种类', 'productName', 'sp.product_name', 359, 'product_property_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (172, '负责人', 'manager', 'd.manager', 359, 'product_property_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (173, '联系方式', 'contactInfo', 'd.contact_info', 359, 'product_property_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (174, '设备序号', 'deviceName', 'device_name', 362, 'deviceLoginLog', NULL, NULL, 2, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (175, '设备名称', 'nickName', 'nike_name', 362, 'deviceLoginLog', NULL, NULL, 1, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (176, '产品名称', 'productName', 'product_name', 362, 'deviceLoginLog', NULL, NULL, 3, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (177, '设备行为', 'behavior', 'behavior', 362, 'deviceLoginLog', 'behavior', NULL, 4, NULL, 4, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (178, '记录时间从', 'logBegintime', 'log_time', 362, 'deviceLoginLog', '', NULL, 5, NULL, 3, 'ge', 1, 10);
INSERT INTO `sys_search_config` VALUES (179, '到', 'logEndTime', 'log_time', 362, 'deviceLoginLog', '', NULL, 6, NULL, 3, 'le', 1, 10);
INSERT INTO `sys_search_config` VALUES (180, '设备序号', 'deviceName', 'device_name', 362, 'devicePropertyLog', NULL, NULL, 2, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (181, '设备名称', 'nickName', 'nike_name', 362, 'devicePropertyLog', NULL, NULL, 1, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (182, '产品名称', 'productName', 'product_name', 362, 'devicePropertyLog', NULL, NULL, 3, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (183, '上报信息类型', 'info_type', 'info_type', 362, 'devicePropertyLog', 'info_type', NULL, 4, NULL, 4, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (184, '上报信息', 'contentJSON', 'content_json', 362, 'devicePropertyLog', '', NULL, 5, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (185, '记录时间从', 'logBegintime', 'log_time', 362, 'devicePropertyLog', '', NULL, 6, NULL, 3, 'ge', 1, 10);
INSERT INTO `sys_search_config` VALUES (186, '到', 'logEndTime', 'log_time', 362, 'devicePropertyLog', '', NULL, 7, NULL, 3, 'le', 1, 10);
INSERT INTO `sys_search_config` VALUES (187, '设备序号', 'deviceName', 'device_name', 363, 'deviceOperationLog', NULL, NULL, 2, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (188, '设备名称', 'nickName', 'nike_name', 363, 'deviceOperationLog', NULL, NULL, 1, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (189, '产品名称', 'productName', 'product_name', 363, 'deviceOperationLog', NULL, NULL, 3, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (190, '操作类型', 'operation_type', 'operation_type', 363, 'deviceOperationLog', 'operation_type', NULL, 4, NULL, 4, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (191, '操作人登录账号', 'operation_user', 'operation_user_name', 363, 'deviceOperationLog', NULL, NULL, 7, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (192, '下行信息', 'command_info', 'command_info', 363, 'deviceOperationLog', NULL, NULL, 5, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (193, '记录时间从', 'logBegintime', 'create_time', 363, 'deviceOperationLog', '', NULL, 8, NULL, 3, 'ge', 1, 10);
INSERT INTO `sys_search_config` VALUES (194, '到', 'logEndTime', 'create_time', 363, 'deviceOperationLog', '', NULL, 9, NULL, 3, 'le', 1, 10);
INSERT INTO `sys_search_config` VALUES (195, '操作人用户名', 'operation_user_nike_name', 'operation_user_nike_name', 363, 'deviceOperationLog', NULL, NULL, 6, NULL, 1, 'like', 1, 10);
INSERT INTO `sys_search_config` VALUES (196, '设备名称', 'deviceNickName', 'device_nick_name', 378, 'list_fault_alarms_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (197, '设备序号', 'deviceName', 'device_name', 378, 'list_fault_alarms_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (198, '报警名称', 'warningName', 'warning_name', 378, 'list_fault_alarms_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (199, '报警类型', 'warningType', 'warning_type', 378, 'list_fault_alarms_search', 'warning_type', NULL, 4, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (200, '报警状态', 'warningStatus', 'warning_status', 378, 'list_fault_alarms_search', 'warning_status', NULL, 5, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (201, '报警时间从', 'beginTime', 'warning_time', 378, 'list_fault_alarms_search', NULL, NULL, 6, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (202, '到', 'endTime', 'warning_time', 378, 'list_fault_alarms_search', NULL, NULL, 7, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (203, '设备名称', 'deviceNickName', 'device_nick_name', 378, 'list_all_fault_alarms_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (204, '设备序号', 'deviceName', 'device_name', 378, 'list_all_fault_alarms_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (205, '报警名称', 'warningName', 'warning_name', 378, 'list_all_fault_alarms_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (206, '报警类型', 'warningType', 'warning_type', 378, 'list_all_fault_alarms_search', 'warning_type', NULL, 5, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (207, '报警状态', 'warningStatus', 'warning_status', 378, 'list_all_fault_alarms_search', 'warning_status', NULL, 6, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (208, '报警时间从', 'beginTime', 'warning_time', 378, 'list_all_fault_alarms_search', NULL, NULL, 7, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (209, '到', 'endTime', 'warning_time', 378, 'list_all_fault_alarms_search', NULL, NULL, 8, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (210, '处理状态', 'treatmentStatus', 'treatment_status', 378, 'list_all_fault_alarms_search', 'treatment_status', NULL, 4, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (211, '表格名称', 'tableName', 'table_name', 420, 'list_form_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (212, '表格编码', 'tableCode', 'table_code', 364, 'list_form_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (213, '项目名称', 'projectName', 'project_name', 364, 'list_project_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (214, '项目编码', 'projectCode', 'project_code', 364, 'list_project_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (215, '项目状态', 'projectStatus', 'project_status', 364, 'list_project_search', 'project_status', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (216, '客户全称', 'customerName', 'customer_name', 364, 'list_project_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (217, '项目负责人', 'manager', 'manager', 364, 'list_project_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (218, '设备名称', 'nick_name', 'nick_name', 353, 'basic_device_manager_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (219, '设备编码', 'device_name', 'device_name', 353, 'basic_device_manager_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (220, '设备序号', 'device_code', 'device_code', 353, 'basic_device_manager_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (221, '登录账户', 'userAccountName', 'a.user_account_name', 353, 'basic_sys_share_search', NULL, NULL, 1, '登录账户', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (222, '用户名', 'userName', 'a.user_name', 353, 'basic_sys_share_search', NULL, NULL, 2, '用户名', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (223, '编号', 'userCode', 'a.user_code', 353, 'basic_sys_share_search', NULL, '', 3, '编号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (224, '性别', 'user_sex', 'a.user_sex', 353, 'basic_sys_share_search', 'sex', NULL, 4, '性别', 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (225, '用户所属部门', 'dept_name', 'c.dept_name', 353, 'basic_sys_share_search', NULL, NULL, 5, '用户所属部门', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (226, '手机号', 'user_phone', 'a.user_phone', 353, 'basic_sys_share_search', NULL, NULL, 6, '手机号', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (227, '分组名称', 'groupName', 'dg.group_name', 365, 'ProjectRelatedDeviceGroup', NULL, NULL, 1, NULL, 1, 'like', 1, 15);
INSERT INTO `sys_search_config` VALUES (228, '分组编码', 'groupCode', 'dg.group_code', 365, 'ProjectRelatedDeviceGroup', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (229, '设备名称', 'deviceName', 'd.device_name', 365, 'ProjectRelatedDevice', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (230, '设备编码', 'deviceCode', 'd.device_code', 365, 'ProjectRelatedDevice', NULL, NULL, 2, NULL, 1, 'like', 1, 15);
INSERT INTO `sys_search_config` VALUES (231, '设备状态', 'deviceStatus', 'd.status', 365, 'ProjectRelatedDevice', 'device_status', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (232, '设备名称', 'deviceName', 'd.device_name', 365, 'ProjectFindAllDevice', NULL, NULL, 1, NULL, 1, 'like', 1, 15);
INSERT INTO `sys_search_config` VALUES (233, '设备编码', 'deviceCode', 'd.device_code', 365, 'ProjectFindAllDevice', NULL, NULL, 2, NULL, 1, 'like', 1, 15);
INSERT INTO `sys_search_config` VALUES (234, '隐患名称', 'hazardName', 'shm.hazard_name', 380, 'list_hazard_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (235, '报告人用户名', 'reportUserNickName', 'shm.report_user_nick_name', 380, 'list_hazard_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (236, '报告人登录账号', 'reportUserName', 'shm.report_user_name', 380, 'list_hazard_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (237, '关联设备名称', 'deviceName', 'shd.device_nick_name', 380, 'list_hazard_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (238, '关联设备编码', 'deviceCode', 'shd.device_name', 380, 'list_hazard_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (239, '隐患编码', 'hazardCode', 'shm.hazard_code', 380, 'list_hazard_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (240, '隐患内容', 'hazardContent', 'shm.hazard_content', 380, 'list_hazard_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (241, '报告时间从', 'beginTime', 'shm.report_time', 380, 'list_hazard_search', NULL, NULL, 8, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (242, '到', 'endTime', 'shm.report_time', 380, 'list_hazard_search', NULL, NULL, 9, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (243, '处理人用户名', 'processingUserName', 'processing_user_name', 378, 'list_all_fault_alarms_search', NULL, NULL, 9, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (244, '处理人登录账号', 'processingUserNickName', 'processing_user_nick_name', 378, 'list_all_fault_alarms_search', NULL, NULL, 10, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (255, '隐患名称', 'hazardName', 'shm.hazard_name', 380, 'list_all_hazard_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (256, '处理状态', 'hazard_status', 'shm.status', 380, 'list_all_hazard_search', 'hazard_status', NULL, 2, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (257, '报告人用户名', 'reportUserNickName', 'shm.report_user_nick_name', 380, 'list_all_hazard_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (258, '报告人登录账号', 'reportUserName', 'shm.report_user_name', 380, 'list_all_hazard_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (259, '关联设备名称', 'deviceCode', 'shd.device_name', 380, 'list_all_hazard_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (260, '关联设备编码', 'deviceName', 'shd.device_code', 380, 'list_all_hazard_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (261, '处理人用户名', 'processingUserNickName', 'shm.processing_user_nick_name', 380, 'list_all_hazard_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (262, '处理人登录账号', 'processingUserName', 'shm.processing_user_name', 380, 'list_all_hazard_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (263, '报告时间从', 'beginTime', 'shm.report_time', 380, 'list_all_hazard_search', NULL, NULL, 9, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (264, '到', 'endTime', 'shm.report_time', 380, 'list_all_hazard_search', NULL, NULL, 10, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (265, '项目名称', 'projectName', 'project_name', 365, 'sysProjectList', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (266, '客户全称', 'customer_name', 'customer_name', 365, 'sysProjectList', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (267, '项目编码', 'projectCode', 'project_code', 365, 'sysProjectList', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (268, '策略名称', 'strategy_name', 'strategy_name', 367, 'strategy_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (269, '策略编码', 'strategy_code', 'strategy_code', 367, 'strategy_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (270, '创建时间从', 'beg_time', 'create_time', 367, 'strategy_search', NULL, NULL, 3, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (271, '到', 'end_time', 'create_time', 367, 'strategy_search', NULL, NULL, 4, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (272, '设备名称', 'nick_name', 'b.nick_name', 367, 'device_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (273, '设备编码', 'device_code', 'b.device_code', 367, 'device_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (274, '场景名称', 'scenes_name', 'scenes_name', 367, 'scenes_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (275, '场景编码', 'scenes_code', 'scenes_code', 367, 'scenes_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (276, '告警规则名称', 'alert_name', 'alert_name', 366, 'alert_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (277, '告警规则编码', 'alert_code', 'alert_code', 366, 'alert_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (278, '创建时间从', 'beg_time', 'create_time', 366, 'alert_search', NULL, NULL, 3, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (279, '到', 'end_time', 'create_time', 366, 'alert_search', NULL, NULL, 4, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (281, '设备名称', 'nick_name', 'nick_name', 536, 'basic_device_alert_rule_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (282, '设备编码', 'device_name', 'device_name', 536, 'basic_device_alert_rule_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (283, '设备序号', 'device_code', 'device_code', 536, 'basic_device_alert_rule_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (284, '企业名称', 'corp_name', 'corp_name', 605, 'comp_list_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (285, '组织机构代码', 'corp_code', 'corp_code', 605, 'comp_list_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (286, '单位类型', 'corp_category', 'corp_category', 605, 'comp_list_search', 'corp_category', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (287, '企业注册地区编码', 'area_code', 'area_code', 605, 'comp_list_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (288, '项目名称', 'project_name', 'name', 606, 'project_list_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (289, '项目编码', 'project_code', 'project_code', 606, 'project_list_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (290, '项目分类', 'project_category', 'category', 606, 'project_list_search', 'project_category', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (291, '总包企业名称', 'contractor_corp_name', 'contractor_corp_name', 606, 'project_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (292, '总包统一社会代码', 'contractor_corp_code', 'contractor_corp_code', 606, 'project_list_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (293, '班组名称', 'team_name', 'team_name', 607, 'team_list_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (294, '班组编号', 'team_sys_no', 'team_sys_no', 607, 'team_list_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (295, '项目编码', 'project_code', 'project_code', 607, 'team_list_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (296, '统一社会信用代码', 'corp_code', 'corp_code', 607, 'team_list_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (297, '班组所在企业名称', 'corp_name', 'corp_name', 607, 'team_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (298, '工人姓名', 'worker_name', 'worker_name', 607, 'project_worker_list_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (299, '证件类型', 'id_card_type', 'id_card_type', 607, 'project_worker_list_search', 'id_card_type', NULL, 2, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (300, '证件号码', 'id_card_number', 'id_card_number', 607, 'project_worker_list_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (301, '当前工种', 'work_type', 'work_type', 607, 'project_worker_list_search', 'work_type', NULL, 4, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (302, '工人类型', 'work_role', 'work_role', 607, 'project_worker_list_search', 'work_role', NULL, 5, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (303, '是否班组长', 'is_team_leader', 'is_team_leader', 607, 'project_worker_list_search', 'whether_dict', NULL, 6, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (304, '班组名称', 'team_name', 'team_name', 607, 'project_worker_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (305, '班组编号', 'team_sys_no', 'team_sys_no', 607, 'project_worker_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (306, '项目编码', 'project_code', 'project_code', 607, 'project_worker_list_search', NULL, NULL, 9, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (307, '企业名称', 'corp_name', 'corp_name', 607, 'project_worker_list_search', NULL, NULL, 10, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (308, '工人所在企业统一社会信用代码', 'corp_code', 'corp_code', 607, 'project_worker_list_search', NULL, NULL, 11, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (309, '人员进退场状态', 'project_worker_status', 'status', 607, 'project_worker_list_search', 'project_worker_status', NULL, 12, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (310, '是否有劳动合同', 'has_contract', 'has_contract', 607, 'project_worker_list_search', 'whether_dict', NULL, 13, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (311, '详细地址', 'address_detail', 'address_detail', 353, 'basic_device_manager_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (312, '负责人', 'manager', 'manager', 353, 'basic_device_manager_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (313, '联系方式', 'contact_info', 'contact_info', 353, 'basic_device_manager_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (314, '抓拍时间从', 'beginTime', 'scr.capture_time', 630, 'pass_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (315, '到', 'endTime', 'scr.capture_time', 630, 'pass_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (316, '方向', 'direction', 'scr.direction', 630, 'pass_list_search', 'direction', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (318, '性别', 'person_sex', 'fl.person_sex', 630, 'pass_list_search', 'user_sex', NULL, 4, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (319, '姓名', 'person_name', 'fl.person_name', 630, 'pass_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (320, '编号', 'face_no', 'fl.face_no', 630, 'pass_list_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (321, '人脸分组', 'face_group_name', 'fg.group_name', 630, 'pass_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (322, '抓拍设备', 'device_name', 'scr.device_name', 630, 'pass_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (323, '设备分组', 'device_group_name', 'sdg.group_name', 630, 'pass_list_search', NULL, NULL, 9, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (324, '手机号', 'contact_phone', 'fl.contact_phone', 630, 'pass_list_search', NULL, NULL, 11, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (325, '身份证号', 'person_card', 'fl.person_card', 630, 'pass_list_search', NULL, NULL, 12, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (326, '抓拍时间从', 'beginTime', 'scr.capture_time', 631, 'comparison_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (327, '到', 'endTime', 'scr.capture_time', 631, 'comparison_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (328, '方向', 'direction', 'scr.direction', 631, 'comparison_list_search', 'direction', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (330, '性别', 'person_sex', 'fl.person_sex', 631, 'comparison_list_search', 'user_sex', NULL, 4, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (331, '姓名', 'person_name', 'fl.person_name', 631, 'comparison_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (332, '编号', 'face_no', 'fl.face_no', 631, 'comparison_list_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (333, '人脸分组', 'face_group_name', 'fg.group_name', 631, 'comparison_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (334, '抓拍设备', 'device_name', 'scr.device_name', 631, 'comparison_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (335, '设备分组', 'device_group_name', 'sdg.group_name', 631, 'comparison_list_search', NULL, NULL, 9, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (336, '手机号', 'contact_phone', 'fl.contact_phone', 631, 'comparison_list_search', NULL, NULL, 11, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (337, '身份证号', 'person_card', 'fl.person_card', 631, 'comparison_list_search', NULL, NULL, 12, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (338, '抓拍时间从', 'beginTime', 'scr.capture_time', 632, 'stranger_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (339, '到', 'endTime', 'scr.capture_time', 632, 'stranger_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (340, '方向', 'direction', 'scr.direction', 632, 'stranger_list_search', 'direction', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (341, '性别', 'person_sex', 'fl.person_sex', 632, 'stranger_list_search', 'user_sex', NULL, 4, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (342, '抓拍设备', 'device_name', 'scr.device_name', 632, 'stranger_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (343, '设备分组', 'device_group_name', 'sdg.group_name', 632, 'stranger_list_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (344, '抓拍时间从', 'beginTime', 'scr.capture_time', 633, 'visitorsAndArchives_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (345, '到', 'endTime', 'scr.capture_time', 633, 'visitorsAndArchives_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (346, '方向', 'direction', 'scr.direction', 633, 'visitorsAndArchives_list_search', 'direction', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (348, '性别', 'person_sex', 'fl.person_sex', 633, 'visitorsAndArchives_list_search', 'user_sex', NULL, 4, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (349, '姓名', 'person_name', 'fl.person_name', 633, 'visitorsAndArchives_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (352, '抓拍设备', 'device_name', 'scr.device_name', 633, 'visitorsAndArchives_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (353, '设备分组', 'device_group_name', 'sdg.group_name', 633, 'visitorsAndArchives_list_search', NULL, NULL, 9, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (354, '手机号', 'contact_phone', 'fl.contact_phone', 633, 'visitorsAndArchives_list_search', NULL, NULL, 11, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (355, '身份证号', 'person_card', 'fl.person_card', 633, 'visitorsAndArchives_list_search', NULL, NULL, 12, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (356, '抓拍时间从', 'beginTime', 'scr.capture_time', 634, 'blacklist_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (357, '到', 'endTime', 'scr.capture_time', 634, 'blacklist_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (358, '方向', 'direction', 'scr.direction', 634, 'blacklist_list_search', 'direction', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (359, '性别', 'person_sex', 'fl.person_sex', 634, 'blacklist_list_search', 'user_sex', NULL, 4, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (360, '姓名', 'person_name', 'fl.person_name', 634, 'blacklist_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (361, '编号', 'face_no', 'fl.face_no', 634, 'blacklist_list_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (362, '人脸分组', 'face_group_name', 'fg.group_name', 634, 'blacklist_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (363, '抓拍设备', 'device_name', 'scr.device_name', 634, 'blacklist_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (364, '设备分组', 'device_group_name', 'sdg.group_name', 634, 'blacklist_list_search', NULL, NULL, 9, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (365, '手机号', 'contact_phone', 'fl.contact_phone', 634, 'blacklist_list_search', NULL, NULL, 11, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (366, '身份证号', 'person_card', 'fl.person_card', 634, 'blacklist_list_search', NULL, NULL, 12, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (368, '人员属性', 'name', 'pl.name', 630, 'pass_list_search', NULL, NULL, 10, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (369, '人员属性', 'name', 'pl.name', 631, 'comparison_list_search', NULL, NULL, 10, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (370, '人员属性', 'name', 'pl.name', 633, 'visitorsAndArchives_list_search', NULL, NULL, 10, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (371, '人员属性', 'name', 'pl.name', 634, 'blacklist_list_search', NULL, NULL, 10, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (372, '编号', 'face_no', 'fl.face_no', 634, 'visitorsAndArchives_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (373, '人脸分组', 'face_group_name', 'fg.group_name', 634, 'visitorsAndArchives_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (374, '姓名', 'personName', 'a.person_name', 624, 'faceListWhileandBlackSearch', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (375, '编号', 'personNo', 'a.face_no', 624, 'faceListWhileandBlackSearch', '', NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (376, '性别', 'sex', 'a.person_sex', 624, 'faceListWhileandBlackSearch', 'user_sex', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (377, '人脸分组', 'faceGroup', 'a.group_name', 624, 'faceListWhileandBlackSearch', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (378, '人员属性', 'personLabel', 'b.person_label_name_list', 624, 'faceListWhileandBlackSearch', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (379, '手机号', 'phone', 'a.contact_phone', 624, 'faceListWhileandBlackSearch', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (380, '身份证号', 'personCard', 'a.person_card', 624, 'faceListWhileandBlackSearch', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (381, '姓名', 'personName', 'a.person_name', 626, 'faceListVistorSearch', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (382, '编号', 'personNo', 'a.face_no', 626, 'faceListVistorSearch', '', NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (383, '性别', 'sex', 'a.person_sex', 626, 'faceListVistorSearch', 'user_sex', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (384, '人员属性', 'personLabel', 'b.person_label_name_list', 626, 'faceListVistorSearch', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (385, '手机号', 'phone', 'a.contact_phone', 626, 'faceListVistorSearch', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (386, '身份证号', 'personCard', 'a.person_card', 626, 'faceListVistorSearch', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (387, '拜访人员', 'visitor_person_name', 'a.visitor_person_name', 626, 'faceListVistorSearch', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (388, '姓名', 'personName', 'a.person_name', 627, 'faceListArchivesStrangersSearch', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (389, '编号', 'personNo', 'a.face_no', 627, 'faceListArchivesStrangersSearch', '', NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (390, '性别', 'sex', 'a.person_sex', 627, 'faceListArchivesStrangersSearch', 'user_sex', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (391, '人员属性', 'personLabel', 'b.person_label_name_list', 627, 'faceListArchivesStrangersSearch', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (392, '手机号', 'phone', 'a.contact_phone', 627, 'faceListArchivesStrangersSearch', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (393, '身份证号', 'personCard', 'a.person_card', 627, 'faceListArchivesStrangersSearch', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (394, '姓名', 'personName', 'a.person_name', 623, 'faceListInfoSearch', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (395, '编号', 'personNo', 'a.face_no', 623, 'faceListInfoSearch', '', NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (396, '性别', 'sex', 'a.person_sex', 623, 'faceListInfoSearch', 'user_sex', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (397, '人员属性', 'personLabel', 'b.person_label_name_list', 623, 'faceListInfoSearch', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (398, '手机号', 'phone', 'a.contact_phone', 623, 'faceListInfoSearch', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (399, '身份证号', 'personCard', 'a.person_card', 623, 'faceListInfoSearch', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (400, '人员类型', 'person_type', 'a.person_type', 623, 'faceListInfoSearch', 'personType', NULL, 8, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (401, '状态', 'status', 'a.status', 623, 'faceListInfoSearch', 'personStatus', NULL, 9, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (402, '姓名', 'personName', 'a.person_name', 623, 'faceListAddAlreadySearch', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (403, '编号', 'personNo', 'a.face_no', 623, 'faceListAddAlreadySearch', '', NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (404, '性别', 'sex', 'a.person_sex', 623, 'faceListAddAlreadySearch', 'user_sex', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (405, '人员属性', 'personLabel', 'b.person_label_name_list', 623, 'faceListAddAlreadySearch', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (406, '手机号', 'phone', 'a.contact_phone', 623, 'faceListAddAlreadySearch', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (407, '身份证号', 'personCard', 'a.person_card', 623, 'faceListAddAlreadySearch', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (408, '人员类型', 'person_type', 'a.person_type', 623, 'faceListAddAlreadySearch', 'personType', NULL, 7, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (409, '状态', 'status', 'a.status', 623, 'faceListAddAlreadySearch', 'personStatus', NULL, 7, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (410, '人脸分组', 'faceGroup', 'a.group_name', 623, 'faceListAddAlreadySearch', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (411, '触发时间从', 'beginTime', 'ae.triggering_time', 777, 'abnormal_behavior_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (412, '到', 'endTime', 'ae.triggering_time', 777, 'abnormal_behavior_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (413, '事件内容', 'event_name', 'ae.event_name', 777, 'abnormal_behavior_list_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (414, '关联人员姓名', 'person_name', 'fl.person_name', 777, 'abnormal_behavior_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (415, '关联人员编号', 'face_no', 'fl.face_no', 777, 'abnormal_behavior_list_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (416, '行为标签', 'label_list', 'ae.label_list', 777, 'abnormal_behavior_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (417, '触发设备', 'device_nick_name', 'ae.device_nick_name', 777, 'abnormal_behavior_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (418, '设备分组', 'device_group_name', 'ae.device_group_name', 777, 'abnormal_behavior_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (419, '设备详细地址', 'device_address_detail', 'ae.device_address_detail', 777, 'abnormal_behavior_list_search', NULL, NULL, 9, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (420, '触发时间从', 'beginTime', 'ae.triggering_time', 778, 'fire_warning_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (421, '到', 'endTime', 'ae.triggering_time', 778, 'fire_warning_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (422, '关联人员姓名', 'person_name', 'fl.person_name', 778, 'fire_warning_list_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (423, '关联人员编号', 'face_no', 'fl.face_no', 778, 'fire_warning_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (424, '行为标签', 'label_list', 'ae.label_list', 778, 'fire_warning_list_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (425, '触发设备', 'device_nick_name', 'ae.device_nick_name', 778, 'fire_warning_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (426, '设备分组', 'device_group_name', 'ae.device_group_name', 778, 'fire_warning_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (427, '设备详细地址', 'device_address_detail', 'ae.device_address_detail', 778, 'fire_warning_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (428, '触发时间从', 'beginTime', 'ae.triggering_time', 779, 'illegal_smoking_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (429, '到', 'endTime', 'ae.triggering_time', 779, 'illegal_smoking_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (430, '关联人员姓名', 'person_name', 'fl.person_name', 779, 'illegal_smoking_list_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (431, '关联人员编号', 'face_no', 'fl.face_no', 779, 'illegal_smoking_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (432, '行为标签', 'label_list', 'ae.label_list', 779, 'illegal_smoking_list_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (433, '触发设备', 'device_nick_name', 'ae.device_nick_name', 779, 'illegal_smoking_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (434, '设备分组', 'device_group_name', 'ae.device_group_name', 779, 'illegal_smoking_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (435, '设备详细地址', 'device_address_detail', 'ae.device_address_detail', 779, 'illegal_smoking_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (436, '触发时间从', 'beginTime', 'ae.triggering_time', 780, 'helmet_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (437, '到', 'endTime', 'ae.triggering_time', 780, 'helmet_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (438, '关联人员姓名', 'person_name', 'fl.person_name', 780, 'helmet_list_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (439, '关联人员编号', 'face_no', 'fl.face_no', 780, 'helmet_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (440, '行为标签', 'label_list', 'ae.label_list', 780, 'helmet_list_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (441, '触发设备', 'device_nick_name', 'ae.device_nick_name', 780, 'helmet_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (442, '设备分组', 'device_group_name', 'ae.device_group_name', 780, 'helmet_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (443, '设备详细地址', 'device_address_detail', 'ae.device_address_detail', 780, 'helmet_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (444, '触发时间从', 'beginTime', 'ae.triggering_time', 781, 'emotion_detection_list_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (445, '到', 'endTime', 'ae.triggering_time', 781, 'emotion_detection_list_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (446, '关联人员姓名', 'person_name', 'fl.person_name', 781, 'emotion_detection_list_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (447, '关联人员编号', 'face_no', 'fl.face_no', 781, 'emotion_detection_list_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (448, '行为标签', 'label_list', 'ae.label_list', 781, 'emotion_detection_list_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (449, '触发设备', 'device_nick_name', 'ae.device_nick_name', 781, 'emotion_detection_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (450, '设备分组', 'device_group_name', 'ae.device_group_name', 781, 'emotion_detection_list_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (451, '设备详细地址', 'device_address_detail', 'ae.device_address_detail', 781, 'emotion_detection_list_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (452, '触发时间从', 'beginTime', 'CaptureTime', 840, 'car_record_whitelist_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (453, '到', 'endTime', 'CaptureTime', 840, 'car_record_whitelist_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (454, '方向', 'Direction', 'Direction', 840, 'car_record_whitelist_search', 'direction', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (455, '车辆类型', 'CarTypeName', 'CarTypeName', 840, 'car_record_whitelist_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (456, '车型类型', 'CarModelName', 'CarModelName', 840, 'car_record_whitelist_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (457, '车牌号码', 'PlateNo', 'PlateNo', 840, 'car_record_whitelist_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (458, '车主姓名', 'EmployeeName', 'EmployeeName', 840, 'car_record_whitelist_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (460, '车主电话', 'MobilePhone', 'MobilePhone', 840, 'car_record_whitelist_search', NULL, NULL, 9, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (461, '抓拍设备', 'DeviceName', 'DeviceName', 840, 'car_record_whitelist_search', NULL, NULL, 10, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (462, '抓拍设备编码', 'DeviceCode', 'DeviceCode', 840, 'car_record_whitelist_search', NULL, NULL, 11, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (463, '设备分组', 'DeviceGroupName', 'DeviceGroupName', 840, 'car_record_whitelist_search', NULL, NULL, 12, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (464, '触发时间从', 'beginTime', 'CaptureTime', 841, 'car_record_blacklist_search', NULL, NULL, 1, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (465, '到', 'endTime', 'CaptureTime', 841, 'car_record_blacklist_search', NULL, NULL, 2, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (466, '方向', 'Direction', 'Direction', 841, 'car_record_blacklist_search', 'direction', NULL, 3, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (467, '限制类型', 'BlacklistStatus', 'BlacklistStatus', 841, 'car_record_blacklist_search', 'BlacklistStatus', NULL, 4, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (468, '车牌号码', 'PlateNo', 'PlateNo', 841, 'car_record_blacklist_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (469, '限制原因', 'BlacklistRemark', 'BlacklistRemark', 841, 'car_record_blacklist_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (470, '抓拍设备', 'DeviceName', 'DeviceName', 841, 'car_record_blacklist_search', NULL, NULL, 7, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (471, '抓拍设备编码', 'DeviceCode', 'DeviceCode', 841, 'car_record_blacklist_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (472, '设备分组', 'DeviceGroupName', 'DeviceGroupName', 841, 'car_record_blacklist_search', NULL, NULL, 9, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (473, '车类名称', 'CarTypeName', 'CarTypeName', 842, 'car_type_list_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (474, '基础类型', 'BaseTypeID', 'BaseTypeID', 842, 'car_type_list_search', 'BaseTypeId', NULL, 2, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (475, '车型名称', 'CarModelName', 'CarModelName', 843, 'car_model_list_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (476, '车牌号码', 'PlateNo', 'ep.PlateNo', 844, 'car_whitelist_manage_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (477, '车牌颜色', 'Color', 'ep.Color', 844, 'car_whitelist_manage_search', 'EmployeePlateColor', NULL, 2, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (478, '车辆类型', 'CarTypeName', 'pct.CarTypeName', 844, 'car_whitelist_manage_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (479, '车型类型', 'CarModelName', 'pcm.CarModelName', 844, 'car_whitelist_manage_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (480, '车辆编码', 'PlateID', 'ep.PlateID', 844, 'car_whitelist_manage_search', NULL, NULL, 5, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (481, '车主姓名', 'EmployeeName', 'be.EmployeeName', 844, 'car_whitelist_manage_search', NULL, NULL, 6, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (483, '车主电话', 'MobilePhone', 'be.MobilePhone', 844, 'car_whitelist_manage_search', NULL, NULL, 8, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (484, '状态', 'State', 'ep.State', 844, 'car_whitelist_manage_search', 'EmployeePlateState', NULL, 9, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (485, '限制类型', 'Status', 'Status', 845, 'car_blacklist_manage_search', 'BlacklistStatus', NULL, 1, NULL, 4, 'eq', 100, 1);
INSERT INTO `sys_search_config` VALUES (486, '车牌号码', 'PlateNumber', 'PlateNumber', 845, 'car_blacklist_manage_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (487, '车辆编码', 'RecordID', 'RecordID', 845, 'car_blacklist_manage_search', NULL, NULL, 3, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (488, '限制原因', 'Remark', 'Remark', 845, 'car_blacklist_manage_search', NULL, NULL, 4, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (489, '日志参数', 'operate_param', 'operate_param', 24, 'sys_log_search', NULL, NULL, NULL, '日志参数', 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (490, '开始时间', 'begin_time', 'operate_create_time', 332, 'sys_log_search', NULL, NULL, 4, NULL, 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (491, '结束时间', 'end_time', 'operate_create_time', 332, 'sys_log_search', NULL, NULL, 5, NULL, 3, 'le', 100, 1);
INSERT INTO `sys_search_config` VALUES (611, '数据名称', 'itemText', 'item_text', 30, 'dicti_item_search', NULL, NULL, 1, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (612, '数据值', 'itemValue', 'item_value', 30, 'dicti_item_search', NULL, NULL, 2, NULL, 1, 'like', 100, 1);
INSERT INTO `sys_search_config` VALUES (633, '开始时间', 'beginTime', 'b.ann_send_time', 26, 'my_message_search', NULL, NULL, 4, '开始时间', 3, 'ge', 100, 1);
INSERT INTO `sys_search_config` VALUES (634, '结束时间', 'endTime', 'b.ann_send_time', 26, 'my_message_search', NULL, NULL, 5, '结束时间', 3, 'le', 100, 1);

-- ----------------------------
-- Table structure for sys_service_setting
-- ----------------------------
DROP TABLE IF EXISTS `sys_service_setting`;
CREATE TABLE `sys_service_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统服务ID',
  `sys_service_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统服务编码',
  `sys_service_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统服务名称',
  `sys_service_type` int(11) NOT NULL COMMENT '系统服务类型',
  `sys_service_value` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统服务变量',
  `sys_service_default_value` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统服务变量默认值',
  `sys_service_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统服务描述',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '系统服务信息更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '系统服务信息更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统服务配置信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_service_setting
-- ----------------------------
INSERT INTO `sys_service_setting` VALUES (1, 'password_strength', 'password_strength', 1, '2', '1', '密码强度:1-低;2-中;3-高', 1, '2021-03-20 11:51:38');
INSERT INTO `sys_service_setting` VALUES (2, 'platform_logo_bucket_name', '平台logo桶名', 2, 'rn-common-resources', 'rn-common-resources', '平台logo桶名', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (3, 'platform_logo_object_name', '平台logo文件名', 2, 'logo.png', 'logo.png', '平台logo文件名', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (4, 'platform_logo_option', '平台logo 选项', 2, '2', '1', '平台logo选项 1默认 2自定义', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (5, 'platform_banner_option', 'banner 选项', 2, '1', '1', 'banner选项 1默认 2自定义', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (6, 'platform_banner_bucket_name', 'banner 桶名', 2, 'rn-common-resources', 'rn-common-resources', 'banner 桶名', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (7, 'platform_banner_object_name', 'banner 文件名', 2, '6EC207DF-8BB9-4cf0-854B-F0DCE66AE809.jpg', '6EC207DF-8BB9-4cf0-854B-F0DCE66AE809.jpg', 'banner 文件名', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (8, 'platform_banner_close', 'banner是否可以关闭', 2, '1', '1', 'banner是否可以关闭 1可以 2不可以', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (9, 'platform_banner_frequency', 'banner显示频率', 2, '2', '1', 'banner显示频率 1每日首次登陆 2一直', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (10, 'account_pass_out_mins', '密码过期', 1, '100', ' 30', '密码过期时间(天)', 1, '2021-03-23 15:34:57');
INSERT INTO `sys_service_setting` VALUES (11, 'site_name', '站点名称', 2, '新航物联网开源版本', '新航物联网开源版本', '站点名称', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (12, 'site_name_option', '站点名称选项', 2, '2', '1', '站点名称选项 1默认 2自定义', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (13, 'account_pass_out_on', '密码过期是否开启', 1, '1', '0', '密码过期是否开启:0-关闭;1-开启', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (14, 'account_lockout_strategy_on', '账户锁定策略是否开启', 1, '1', '0', '账户锁定策略: 0-关闭;1-开启', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (15, 'account_lockout_strategy_frequency', '账户锁定策略设置次数', 1, '5', '5', '账户锁定策略设置次数', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (16, 'account_lockout_strategy_mins', '账户锁定策略时间', 1, '10', '30', '账户锁定策略时间(分钟)', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (17, 'admin_login_ip_filtering_on', '管理员登录IP过滤是否开启', 1, '0', '0', '管理员登录IP过滤是否开启：0-禁止;1-允许', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (18, 'admin_login_ip_filtering', '管理员登录IP过滤', 1, '', '', '管理员登录IP过滤', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (19, 'member_login_ip_filtering_on', '成员登录IP过滤是否开启', 1, '0', '0', '成员登录IP过滤是否开启：0-禁止;1-允许', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (20, 'member_login_ip_filtering', '成员登录IP过滤', 1, '', ' ', '成员登录IP过滤', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (21, 'ntp_address', 'ntp服务器地址', 3, 'time.windows.com', 'time.windows.com', 'ntp服务器地址', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (22, 'ntp_port', 'ntp服务器端口', 3, '123', '123', 'ntp服务器端口', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (23, 'ntp_interval', 'ntp校准间隔', 3, '7', '1', 'ntp校准间隔', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (24, 'ntp_option', 'ntp选项 1ntp校时 2手动校时', 3, '1', '1', 'ntp选项 1ntp校时 2手动校时', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (25, 'ntp_time_zone', '系统时区', 3, 'Asia/Hong_Kong', 'Asia/Shanghai', '系统时区', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (26, 'scheduled_backup_option', '系统备份选项', 4, '1', '1', '系统备份选项 0关闭 1开启', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (27, 'backup_frequency', '备份频率', 4, '29', '30', '备份频率', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (28, 'backup_save_days', '备份保存天数', 4, '31', '30', '备份保存天数', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (29, 'log_scheduled_backup_option', '日志备份选项', 5, '1', '1', '日志备份选项 0关闭 1开启', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (30, 'log_backup_frequency', '日志备份频率', 5, '30', '30', '日志备份频率', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (31, 'log_backup_save_days', '日志备份保存天数', 5, '90', '30', '日志备份保存天数', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (32, 'log_info_save_days', '日志信息保存天数', 5, '90', '31', '日志信息保存天数', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (33, 'ldap_address', 'LDAP地址', 6, '1', ' ', 'LDAP地址', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (34, 'ldap_bind_dn', '绑定DN', 6, '1', ' ', '绑定DN', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (35, 'ldap_password', '密码', 6, '1', ' ', '密码', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (36, 'ldap_user_ou', '用户OU', 6, '1', ' ', '用户OU', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (37, 'ldap_user_filter', '用户过滤器', 6, '1', ' ', '用户过滤器', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (38, 'ldap_attribute_mapping', 'LDAP属性映射', 6, '1', ' ', 'LDAP属性映射', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (39, 'ldap_enable_auth', '启用LDAP认证', 6, '1', '0', '启用LDAP认证: 0=关闭; 1-开启', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (40, 'last_login_info_prompt_on', '上次登录信息提示开关', 1, '1', '0', '上次登录信息提示开关:0-关闭;1-开启', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (41, 'single_sign_on', '单点登录开关', 1, '0', '0', '单点登录开关:0-关闭;1-踢下线;2-禁止登录', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (42, 'pass_mandatory_modification_on', '初始密码强制修改开关', 1, '1', '0', '初始密码强制修改开关:0-关闭;1-开启', NULL, NULL);
INSERT INTO `sys_service_setting` VALUES (43, 'single_login_valid_time', '单次登录有效时间', 1, '60', '5', '单次登录有效时间', NULL, NULL);

-- ----------------------------
-- Table structure for sys_table_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_table_config`;
CREATE TABLE `sys_table_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格名',
  `table_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格编码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_table_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_table_cotalog
-- ----------------------------
DROP TABLE IF EXISTS `sys_table_cotalog`;
CREATE TABLE `sys_table_cotalog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表单信息ID',
  `table_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单名称',
  `table_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单描述',
  `table_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单编码',
  `table_link` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单关联表编码',
  `table_relationship` int(11) NULL DEFAULT NULL COMMENT '表单关联关系：0-1:1;1-1:n;2-n:n',
  `create_by` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单创建人',
  `create_time` datetime(0) NOT NULL COMMENT '表单创建时间',
  `update_by` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '表单更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统具备的表单总览目录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_table_cotalog
-- ----------------------------

-- ----------------------------
-- Table structure for sys_table_field
-- ----------------------------
DROP TABLE IF EXISTS `sys_table_field`;
CREATE TABLE `sys_table_field`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段名',
  `field_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段编码',
  `fixed` int(2) NULL DEFAULT NULL COMMENT '固定位置 1无 2left 3 right',
  `width` int(11) NULL DEFAULT NULL COMMENT '宽度',
  `align` int(2) NULL DEFAULT NULL COMMENT '1居中 2不居中',
  `scoped_slots` int(2) NULL DEFAULT NULL COMMENT '1需要 2不需要',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `table_config_id` int(11) NULL DEFAULT NULL COMMENT '表配置id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `fieId_tableId`(`id`, `table_config_id`) USING BTREE COMMENT '关联'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_table_field
-- ----------------------------

-- ----------------------------
-- Table structure for sys_table_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_table_user`;
CREATE TABLE `sys_table_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `table_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格编码',
  `table_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '前端json数据',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_table_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_timing_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_timing_task`;
CREATE TABLE `sys_timing_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `timing` datetime(0) NULL DEFAULT NULL COMMENT '定时时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_timing_task
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统用户标识ID',
  `user_account_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统用户登录账户名',
  `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统显示的用户昵称',
  `user_password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统账户密码',
  `user_passwd_salt` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统用户密码的加密盐',
  `photo_bucket_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像桶名',
  `photo_object_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像文件名',
  `user_photo_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户头像',
  `user_birthday` datetime(0) NULL DEFAULT NULL COMMENT '系统用户生日信息',
  `user_sex` int(11) NOT NULL COMMENT '系统用户性别:0-女;1-男;2-不透露',
  `user_email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户邮件',
  `user_phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户联系手机号',
  `user_safe_phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户绑定的手机号',
  `user_safe_email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户绑定的邮箱',
  `user_status` int(12) NOT NULL COMMENT '系统用户状态:0-未激活;1-正常:2-冻结:3-过期',
  `bef_user_status` int(12) NULL DEFAULT NULL COMMENT '之前的系统用户状态:0-未激活;1-正常:2-冻结:3-过期',
  `user_code` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统用户的编号/工号',
  `user_occupation` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户的职务/职称',
  `create_by` int(11) NOT NULL COMMENT '系统用户创建人ID',
  `create_time` datetime(0) NOT NULL COMMENT '系统用户创建时间',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '系统用户信息更新人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '系统用户信息更新时间',
  `user_sysrole` int(11) NOT NULL COMMENT '系统用户的账号身份:0-成员;1-管理员',
  `user_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户描述',
  `user_address` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统用户地址信息',
  `user_valid_time` datetime(0) NOT NULL COMMENT '系统用户有效日期',
  `user_is_online` int(1) NOT NULL COMMENT '系统用户当前是否在线:0-离线:1-在线',
  `user_delete_status` int(1) NOT NULL COMMENT '系统用户当前是否被删除:0-已删除;1-未删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_account_name`(`user_account_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '管理员', '58f836bca9e709a6', 'GNynQvnp', 'rn-platform-user-resources', 'DEFAULT.jpg', NULL, '2021-03-24 00:00:00', 1, '', '', '', '', 1, 1, 'admin', 'admin', 1, '2021-03-23 09:37:00', 1, '2021-12-31 15:27:32', 1, NULL, '福建', '2099-11-25 08:00:00', 0, 1);

-- ----------------------------
-- Table structure for sys_user_announcement
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_announcement`;
CREATE TABLE `sys_user_announcement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户标识ID',
  `sys_announcement_id` int(11) NOT NULL COMMENT '系统通告编号',
  `ann_read_flag` int(1) NOT NULL COMMENT '系统通告阅读状态:0-未读;1-已读',
  `ann_read_time` datetime(0) NULL DEFAULT NULL COMMENT '系统通告阅读时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_user_id_and_sys_announcement_id`(`sys_user_id`, `sys_announcement_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_announcement
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_announcement_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_announcement_config`;
CREATE TABLE `sys_user_announcement_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` int(255) NOT NULL COMMENT '配置名称',
  `sys_ann_config_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_id_and_sys_ann_config_id`(`sys_user_id`, `sys_ann_config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_announcement_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_config`;
CREATE TABLE `sys_user_config`  (
  `id` int(11) NULL DEFAULT NULL COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `background_color` int(1) NULL DEFAULT 1 COMMENT '背景颜色 1蓝白 2夜间灰',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户标识ID',
  `sys_department_id` int(11) NOT NULL COMMENT '系统部门ID',
  `dept_manage` int(1) NOT NULL COMMENT '系统用户是否为部门管理员:0-否;1-是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_id_and_sys_department_id`(`sys_user_id`, `sys_department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_department
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户标识ID',
  `sys_role_id` int(11) NOT NULL COMMENT '系统角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_id_and_sys_role_id`(`sys_user_id`, `sys_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for user_login_info
-- ----------------------------
DROP TABLE IF EXISTS `user_login_info`;
CREATE TABLE `user_login_info`  (
  `id` bigint(20) NOT NULL COMMENT '用户登录信息ID',
  `login_user_id` int(11) NULL DEFAULT NULL COMMENT '登录用户的ID',
  `login_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录的用户名',
  `login_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录的IP地址',
  `login_location` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的物理地址',
  `login_browser` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的浏览器媒介',
  `login_os` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的操作系统',
  `login_status` int(11) NULL DEFAULT NULL COMMENT '登录的状态:0-失败:1-成功',
  `login_description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录信息的描述',
  `login_time` datetime(0) NOT NULL COMMENT '登录时间',
  `login_access_type` int(11) NOT NULL COMMENT '登录鉴权方式：0-匿名;1-账号密码;2-验证码;',
  `offline_time` datetime(0) NULL DEFAULT NULL COMMENT '下线时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_login_info
-- ----------------------------

-- ----------------------------
-- Table structure for visitor_backup
-- ----------------------------
DROP TABLE IF EXISTS `visitor_backup`;
CREATE TABLE `visitor_backup`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `visitor_volume` int(11) NULL DEFAULT NULL COMMENT '访客量',
  `average_usage_time` int(11) NULL DEFAULT NULL COMMENT '平均使用时长',
  `min_usage_time` int(11) NULL DEFAULT NULL COMMENT '最小时长时长',
  `max_usage_time` int(11) NULL DEFAULT NULL COMMENT '最长使用时长',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of visitor_backup
-- ----------------------------

-- ----------------------------
-- Table structure for wechat_sso
-- ----------------------------
DROP TABLE IF EXISTS `wechat_sso`;
CREATE TABLE `wechat_sso`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '微信登录信息ID',
  `sys_user_id` int(11) NULL DEFAULT NULL COMMENT '系统用户标识ID',
  `wc_open_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '微信OpenID',
  `wc_uni_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信UniID',
  `wc_photo_url` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信头像',
  `wc_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信昵称',
  `wc_phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信手机号',
  `wc_bind_time` datetime(0) NULL DEFAULT NULL COMMENT '微信绑定时间',
  `wc_bind_status` int(1) NULL DEFAULT NULL COMMENT '微信绑定状态:0-解绑;1-绑定',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wechat_sso
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

-- 创建mysql库
DROP DATABASE IF EXISTS `xxl_job`;
create database `xxl_job` default character set utf8mb4 collate utf8mb4_general_ci;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE `xxl_job`;

-- ----------------------------
-- Table structure for xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_group`;
CREATE TABLE `xxl_job_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行器名称',
  `address_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_group
-- ----------------------------
INSERT INTO `xxl_job_group` VALUES (2, 'rotanava-boot-module-system', 'module-syste', 0, 'http://10.233.96.158:30007/');
INSERT INTO `xxl_job_group` VALUES (3, 'rotanava-boot-module-basics', 'module-basic', 0, 'http://10.233.90.101:30008/');
INSERT INTO `xxl_job_group` VALUES (4, 'rotanava-boot-module-pythonjob', 'pythonjob', 0, 'http://10.233.70.18:30012/,http://10.233.70.38:30012/');

-- ----------------------------
-- Table structure for xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_info`;
CREATE TABLE `xxl_job_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT 0 COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime(0) NULL DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT 0 COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT 0 COMMENT '下次调度时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_info
-- ----------------------------
INSERT INTO `xxl_job_info` VALUES (2, 2, '0/1 * * * * ?', '发送本地mq表里的数据到mq', '2021-03-22 18:30:35', '2021-04-13 10:29:43', '周金滕', '', 'ROUND', 'MqTransactionalTask', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-03-22 18:30:35', '', 1, 1642058928000, 1642058929000);
INSERT INTO `xxl_job_info` VALUES (4, 2, '0 */1 * * * ?', '定期清理不活动的用户', '2021-03-30 15:32:54', '2021-04-12 14:25:54', '缪伟强', '', 'ROUND', 'regularlyCleanUpInactiveUsers', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-03-30 15:32:54', '', 1, 1642058880000, 1642058940000);
INSERT INTO `xxl_job_info` VALUES (5, 2, '0 1 0 * * ?', '定时访客统计', '2021-04-06 16:56:34', '2021-04-06 17:01:00', '缪伟强', '', 'FIRST', 'timingStatistics', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-04-06 16:56:34', '', 1, 1642003260000, 1642089660000);
INSERT INTO `xxl_job_info` VALUES (6, 2, '* 0/6 * * * ?', '删除已经发送mq表里的数据', '2021-04-13 10:06:39', '2021-04-13 10:07:01', '周金滕', '', 'RANDOM', 'deleteMqTransactionalTask', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-04-13 10:06:39', '', 1, 1642058699000, 1642059000000);
INSERT INTO `xxl_job_info` VALUES (7, 2, '0 0 1 * * ?', '定时系统备份', '2021-04-13 15:00:24', '2021-04-13 15:59:56', '李日晨', '', 'ROUND', 'sysBackupTask', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-04-13 15:00:24', '', 1, 1642006800000, 1642093200000);
INSERT INTO `xxl_job_info` VALUES (8, 3, '0 */10 * * * ?', '定期功率计算任务', '2021-06-23 14:37:14', '2021-07-16 13:50:26', '缪伟强', '', 'FIRST', 'regularlyPowerCalculationTask', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-06-23 14:37:14', '', 1, 1642058400000, 1642059000000);
INSERT INTO `xxl_job_info` VALUES (11, 3, '0 0 0/6 * * ?', '定时扫描阿里物联网的新增产品', '2021-06-28 16:29:30', '2021-07-30 18:13:06', '周金滕', '', 'ROUND', 'saveAliNewProduct', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-06-28 16:29:30', '', 1, 1642046400000, 1642068000000);
INSERT INTO `xxl_job_info` VALUES (18, 4, '0 0 * * * ? *', 'isc平台模块概览信息更新', '2021-09-16 13:44:17', '2021-09-16 18:58:08', 'Qilin YOU', '', 'FIRST', '', '', 'SERIAL_EXECUTION', 0, 0, 'GLUE_PYTHON3', '# -*- coding: UTF-8 -*-\n# Date:2021-09-15\n# Author:Qilin YOU\n\nimport redis\nimport pymysql\nimport time\n\n\ndef elapsed_time(func):\n    def run(*args, **kwargs):\n        start = time.time()\n        res = func(*args, **kwargs)\n        print(\"{}程序运行时间为:{}秒\".format(func.__name__, time.time() - start))\n        return res\n\n    return run\n\n\nclass umysql:\n    def __init__(self, host, port, user, passwd, database, charset=\'utf8\'):\n        \"\"\"连接mysql数据库\"\"\"\n        self.database = database  # 记录连接的数据库名称\n        self.db = pymysql.connect(host=host, port=port, user=user, passwd=passwd, db=database, charset=charset)\n\n    def query(self, query):\n        cursor = self.db.cursor()\n        cursor.execute(query)\n        return cursor\n\n    @elapsed_time\n    def get_number_of_users(self):\n        \"\"\"获取系统中已注册的人数（不包含已删除人员）\"\"\"\n        cursor = self.query(\"select count(id) from rn_base_sys.sys_user where user_delete_status = 1\")\n        data = cursor.fetchone()\n        return data[0]\n\n    @elapsed_time\n    def get_number_of_pages(self):\n        \"\"\"获取平台页面资源数（不包含api接口资源数）\"\"\"\n        cursor = self.query(\"select count(id) from rn_base_sys.sys_page_permission where page_type <> 2\")\n        data = cursor.fetchone()\n        return data[0]\n\n    @elapsed_time\n    def get_number_of_reported_problems(self):\n        \"\"\"获取平台上接收到的问题反馈数\"\"\"\n        cursor = self.query(\"select count(id) from rn_base_sys.sys_report\")\n        data = cursor.fetchone()\n        return data[0]\n\n    @elapsed_time\n    def get_number_of_solved_problems(self):\n        \"\"\"获取平台上已处理的问题数量\"\"\"\n        cursor = self.query(\"select count(id) from rn_base_sys.sys_report where report_stat <> 0\")\n        data = cursor.fetchone()\n        return data[0]\n\n    @elapsed_time\n    def get_numebr_of_devices(self):\n        \"\"\"获取平台已注册的设备数\"\"\"\n        cursor = self.query(\"select count(id) from basic_server.sys_devices\")\n        data = cursor.fetchone()\n        return data[0]\n\n    @elapsed_time\n    def get_numebr_of_projects(self):\n        \"\"\"获取平台的项目数量\"\"\"\n        cursor = self.query(\"select count(id) from basic_server.sys_project\")\n        data = cursor.fetchone()\n        return data[0]\n\n    @elapsed_time\n    def get_numebr_of_rules(self):\n        \"\"\"获取平台的规则数量\"\"\"\n        cursor = self.query(\n            \"select sum(c) from (select count(id) c from basic_server.strategy_config union select count(id) c from basic_server.scenes_config union select count(id) c from basic_server.sys_device_warning) as ctb\")\n        data = cursor.fetchone()\n        return round(data[0])\n\n    @elapsed_time\n    def get_numebr_of_businesses(self):\n        \"\"\"获取平台的工地实名制企业数量\"\"\"\n        cursor = self.query(\"select count(id) from smart_building.builder_comp\")\n        data = cursor.fetchone()\n        return round(data[0])\n\n    @elapsed_time\n    def get_numebr_of_ConProjects(self):\n        \"\"\"获取平台的工地实名制项目数量\"\"\"\n        cursor = self.query(\"select count(id) from smart_building.builder_project\")\n        data = cursor.fetchone()\n        return round(data[0])\n\n    @elapsed_time\n    def get_numebr_of_ComTeams(self):\n        \"\"\"获取平台的工地实名制班组数量\"\"\"\n        cursor = self.query(\"select count(id) from smart_building.builder_team\")\n        data = cursor.fetchone()\n        return round(data[0])\n\n    @elapsed_time\n    def get_numebr_of_Faces(self):\n        \"\"\"获取平台的注册的人脸名单数\"\"\"\n        cursor = self.query(\"select count(id) from basic_server.face_list\")\n        data = cursor.fetchone()\n        return round(data[0])\n\n    def close(self):\n        self.db.close()\n\n\n\n\n\nclass uredis:\n    def __init__(self, host, port, passwd=\"\", db=0):\n        \"\"\"连接redis数据库\"\"\"\n        self.db = redis.StrictRedis(host=host, port=port, password=passwd, db=db)\n\n    def set(self, key, value):\n        self.db.set(key, value)\n\n    def get(self, key):\n        return self.db.get(key)\n\n    def close(self):\n        self.db.close()\n\n\nif __name__ == \'__main__\':\n    rotanavamysql = umysql(host=\'rotanavamysql.isc\', port=3306, user=\'root\', passwd=\'RN2020.\', database=\'rn_base_sys\')\n    rotanavaredis = uredis(host=\'rotanavaredis.isc\', port=6379, db=1)\n    # rotanavamysql = umysql(host=\'192.168.0.226\', port=30006, user=\'root\', passwd=\'RN2020.\', database=\'rn_base_sys\')\n    # rotanavaredis = uredis(host=\'192.168.0.226\', port=30872, db=1)\n\n    \"\"\"---------------------------------------------平台配置-----------------------------------------\"\"\"\n\n    # 更新系统已注册人数（不包含删除人员）\n    rotanavaredis.set(\"numOfUsers\", rotanavamysql.get_number_of_users())\n    # 更新系统已有页面资源数\n    rotanavaredis.set(\"numOfPages\", rotanavamysql.get_number_of_pages())\n    # 更新平台问题反馈数\n    rotanavaredis.set(\"numOfProblems\", rotanavamysql.get_number_of_reported_problems())\n    # 更新平台已处理反馈问题数量\n    rotanavaredis.set(\"numOfSolved\", rotanavamysql.get_number_of_solved_problems())\n\n    \"\"\"---------------------------------------------设备配置-----------------------------------------\"\"\"\n\n    # 更新平台注册设备数量\n    rotanavaredis.set(\"numOfDevices\", rotanavamysql.get_numebr_of_devices())\n    # 更新平台项目数量\n    rotanavaredis.set(\"numOfProjects\", rotanavamysql.get_numebr_of_projects())\n    # 更新平台策略规则数量\n    rotanavaredis.set(\"numOfRules\", rotanavamysql.get_numebr_of_rules())\n\n    \"\"\"---------------------------------------------工地实名制-----------------------------------------\"\"\"\n\n    # 更新平台工地注册企业数量\n    rotanavaredis.set(\"numOfConBusinesses\", rotanavamysql.get_numebr_of_businesses())\n    # 更新平台工地项目注册数量\n    rotanavaredis.set(\"numOfConProjects\", rotanavamysql.get_numebr_of_ConProjects())\n    # 更新平台工地班组数量\n    rotanavaredis.set(\"numOfConTeams\", rotanavamysql.get_numebr_of_ComTeams())\n\n    \"\"\"---------------------------------------------人脸名单库-----------------------------------------\"\"\"\n    # 更新平台的人脸名单数\n    rotanavaredis.set(\"totalOfRegister\", rotanavamysql.get_numebr_of_Faces())\n\n    # 关闭数据库连接\n    rotanavamysql.close()\n    rotanavaredis.close()\n    # xxl-job中需要释放终端线程（xxl-job缺陷\"）\n    exit()\n', 'V1.0.0', '2021-09-16 18:58:08', '', 1, 1642057200000, 1642060800000);
INSERT INTO `xxl_job_info` VALUES (19, 4, '0 0 * * * ?', '车辆模块概览信息更新', '2021-10-13 10:14:28', '2021-10-13 10:15:13', 'Asmo', '', 'FIRST', '', '', 'SERIAL_EXECUTION', 0, 0, 'GLUE_PYTHON3', '# -*- coding: UTF-8 -*-\n# Date:2021-10-13\n# Author:Qilin YOU\n\nimport redis\nimport pymysql\nimport time\n\n\ndef elapsed_time(func):\n    def run(*args, **kwargs):\n        start = time.time()\n        res = func(*args, **kwargs)\n        print(\"{}程序运行时间为:{}秒\".format(func.__name__, time.time() - start))\n        return res\n\n    return run\n\n\nclass umysql:\n    def __init__(self, host, port, user, passwd, database, charset=\'utf8\'):\n        \"\"\"连接mysql数据库\"\"\"\n        self.database = database  # 记录连接的数据库名称\n        self.db = pymysql.connect(host=host, port=port, user=user, passwd=passwd, db=database, charset=charset)\n\n    def query(self, query):\n        cursor = self.db.cursor()\n        cursor.execute(query)\n        return cursor\n\n    @elapsed_time\n    def get_number_of_plates(self):\n        \"\"\"获取系统中登记车辆数量\"\"\"\n        cursor = self.query(\"select count(id) from smartsystem2.employeeplate where DataStatus < 2\")\n        data = cursor.fetchone()\n        return data[0]\n\n    @elapsed_time\n    def get_number_of_events(self):\n        \"\"\"获取系统中当日车辆通行记录数量\"\"\"\n        cursor = self.query(\"select count(id) from smartsystem2.parkcarrecord\")\n        data = cursor.fetchone()\n        return data[0]\n\n    def close(self):\n        self.db.close()\n\n\n\n\n\nclass uredis:\n    def __init__(self, host, port, passwd=\"\", db=0):\n        \"\"\"连接redis数据库\"\"\"\n        self.db = redis.StrictRedis(host=host, port=port, password=passwd, db=db)\n\n    def set(self, key, value):\n        self.db.set(key, value)\n\n    def get(self, key):\n        return self.db.get(key)\n\n    def close(self):\n        self.db.close()\n\n\nif __name__ == \'__main__\':\n    # smartsystemmysql = umysql(host=\'192.168.1.14\', port=3308, user=\'root\', passwd=\'123456\', database=\'smartsystem2\')\n    # rotanavaredis = uredis(host=\'rotanavaredis.isc\', port=6379, db=1)\n    smartsystemmysql = umysql(host=\'192.168.1.14\', port=3308, user=\'root\', passwd=\'123456\', database=\'smartsystem2\')\n    rotanavaredis = uredis(host=\'192.168.0.226\', port=30872, db=1)\n\n    \"\"\"---------------------------------------------车辆信息-----------------------------------------\"\"\"\n\n    rotanavaredis.set(\"numOfCars\", smartsystemmysql.get_number_of_plates())\n    rotanavaredis.set(\"numOfCarRecords\", smartsystemmysql.get_number_of_events())\n    # 关闭数据库连接\n    smartsystemmysql.close()\n    rotanavaredis.close()\n    # xxl-job中需要释放终端线程（xxl-job缺陷\"）\n    exit()\n', 'init', '2021-10-13 10:15:03', '', 1, 1642057200000, 1642060800000);

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock`  (
  `lock_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_lock
-- ----------------------------
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');

-- ----------------------------
-- Table structure for xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log`;
CREATE TABLE `xxl_job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `trigger_time` datetime(0) NULL DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '调度-日志',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE,
  INDEX `I_handle_code`(`handle_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11047025 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_log_report
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_report`;
CREATE TABLE `xxl_job_log_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime(0) NULL DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT 0 COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT 0 COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT 0 COMMENT '执行失败-日志数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_trigger_day`(`trigger_day`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_log_report
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_logglue`;
CREATE TABLE `xxl_job_logglue`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_logglue
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_registry`;
CREATE TABLE `xxl_job_registry`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `registry_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `registry_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_g_k_v`(`registry_group`, `registry_key`, `registry_value`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54284 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_registry
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_user`;
CREATE TABLE `xxl_job_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `role` tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of xxl_job_user
-- ----------------------------
INSERT INTO `xxl_job_user` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
