package com.rotanava.framework.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
//import com.google.api.client.util.Maps;
import com.google.common.collect.Maps;
import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.AuthErrorCode;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * JWT工具类
 *
 * @author WeiQiangMiao
 * @date 2021-03-04
 */
public class JwtUtil {


	/**
	 * Token过期时间20分钟（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）
	 */
	public static final long EXPIRE_TIME = 20 * 60;

	/**
	 * 在线过期时间5分钟
	 */
	public static final long ONLINE_EXPIRE_TIME = 5 * 60;

	private static final String ACCOUNT_NAME_STR = "userAccountName";

	private static final String USER_ID_STR = "userId";



	/**
	 * 校验token是否正确
	 *
	 * @param token  密钥
	 * @param userAccountName  账号
	 * @param userId 用户id
	 * @param secret 用户的密码
	 * @return 是否正确
	 * @author WeiQiangMiao
	 * @date 2021-03-04
	 * @version 1.0.0
	 * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static boolean verify(String token, String userAccountName,Integer userId, String secret) {
		try {
			// 根据密码生成JWT效验器
			Algorithm algorithm = Algorithm.HMAC256(secret);

			JWTVerifier verifier = JWT.require(algorithm)
					.withClaim(ACCOUNT_NAME_STR, userAccountName)
					.withClaim(USER_ID_STR, userId)
					.build();
			// 效验TOKEN
			verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @param token 令牌
	 * @return token中包含的用户名
	 * @author WeiQiangMiao
	 * @date 2021-03-04
	 * @version 1.0.0
	 * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String getUserAccountName(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(ACCOUNT_NAME_STR).asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	public static Integer getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(USER_ID_STR).asInt();
		} catch (JWTDecodeException e) {
			return null;
		}
	}


	/**
	 * 生成签名,5min后过期
	 *
	 * @param userAccountName 用户名
	 * @param userId 用户Id
	 * @param secret   用户的密码
	 * @return 加密的token
	 * @author WeiQiangMiao
	 * @date 2021-03-04
	 * @version 1.0.0
	 * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String sign(String userAccountName,Integer userId, String secret) {
		Algorithm algorithm = Algorithm.HMAC256(secret);

		HashMap<String, Object> map = Maps.newHashMap();
		map.put("date",System.currentTimeMillis());
		// 附带username信息
		return JWT.create()
				.withClaim(ACCOUNT_NAME_STR, userAccountName)
				.withClaim(USER_ID_STR, userId)
				.withHeader(map).sign(algorithm);

	}


	/**
	 * 根据request中的token获取用户账号
	 *
	 * @param request 请求
	 * @return {@link String }
	 * @author WeiQiangMiao
	 * @date 2021-03-04
	 * @version 1.0.0
	 * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String getUserNameByToken(HttpServletRequest request) throws CommonException {
		String accessToken = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
		String userAccountName = getUserAccountName(accessToken);
		if (StringUtils.isEmpty(userAccountName)) {
			throw new CommonException(AuthErrorCode.AUTH_ERROR_06);
		}
		return userAccountName;
	}
}
