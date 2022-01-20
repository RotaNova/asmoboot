package com.rotanava.framework.config.shiro;
 
import org.apache.shiro.authc.AuthenticationToken;


/**
 * jwt令牌
 *
 * @author WeiQiangMiao
 * @date 2021-03-04
 */
public class JwtToken implements AuthenticationToken {
	
	private static final long serialVersionUID = 1L;
	private final String token;
 
    public JwtToken(String token) {
        this.token = token;
    }
 
    @Override
    public Object getPrincipal() {
        return token;
    }
 
    @Override
    public Object getCredentials() {
        return token;
    }
}
