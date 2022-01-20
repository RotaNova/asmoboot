package com.rotanava.dingding.aspect;

import cn.hutool.core.convert.Convert;
import com.rotanava.framework.code.BaseException;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.code.ErrorCode;
import com.taobao.api.TaobaoResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-12-21 10:28
 */
@Aspect
@Component
public class DingTalkAspect {
    
    @Autowired
    private RedissonClient redissonClient;
    
    @Pointcut("@annotation(com.rotanava.dingding.aspect.annotation.DingTalk)")
    public void dingTalkPointCut() {
    
    }
    
    @Around("dingTalkPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //执行方法
        TaobaoResponse taobaoResponse = null;
        try {
            
            String key = "dingTalkLimiter";
            
            //限流
            final RRateLimiter dingTalkLimiter = redissonClient.getRateLimiter(key);
            long rate = 20L;
            final boolean trySetRate = dingTalkLimiter.trySetRate(RateType.OVERALL, rate, 1, RateIntervalUnit.SECONDS);
            
            if (!trySetRate) {
                if (rate != dingTalkLimiter.getConfig().getRate()) {
                    redissonClient.getBucket(key).delete();
                    dingTalkLimiter.trySetRate(RateType.OVERALL, rate, 1, RateIntervalUnit.SECONDS);
                }
                
            }
            
            //限流等待
            dingTalkLimiter.acquire();
            
            taobaoResponse = (TaobaoResponse) point.proceed();
            
            final int errorCode = Convert.toInt(taobaoResponse.getErrorCode());
            final String msg = taobaoResponse.getMsg();
            
            if (errorCode != 0) {
                throw new CommonException(new ErrorCode(errorCode, "钉钉返回错误信息:" + msg));
            }
        } catch (Exception e) {
            String msg = "";
            
            if (e instanceof BaseException) {
                msg = ((BaseException) e).getErrorCode().getMsg();
            } else {
                msg = e.getMessage();
            }
            throw new CommonException(new ErrorCode(400, "钉钉调用失败:" + msg));
        }
        
        return taobaoResponse;
    }
    
}