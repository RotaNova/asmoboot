package com.rotanava.boot.system.util;

import com.rotanava.framework.common.constant.CommonConstant;
import com.rotanava.framework.code.CommonException;
import com.rotanava.framework.exception.code.CommonErrorCode;
import com.rotanava.framework.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;


/**
 * 邮件跑龙套
 *
 * @author WeiQiangMiao
 * @date 2021-03-19
 */
@Component
public class MailUtil {

    public static final long EXPIRE_TIME = 5 * 60;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 使用配置文件中设置的账户发送邮件<br>
     *
     * @param to      收件人
     * @param subject 标题
     */
    public void send(String to, String subject, String cache) {
        to = to.replaceAll("\\s*", "");

        final int code = random();
        final String key = String.format("%s:%s", cache, to);
        final String text = String.format("尊敬的用户您好！\n 本次请求的邮件验证码为: %s,本验证码 5 分钟内有效，请及时输入。（请勿泄露此验证码）如非本人操作，请忽略该邮件。(这是一封自动发送的邮件，请不要直接回复）", code);

        long date = CommonConstant.VERIFICATION_CODE_DATE - CommonConstant.VERIFICATION_CODE_EXPIRE_DATE;
        long expire = redisUtil.getExpire(key);
        if (expire > date) {
            throw new CommonException(CommonErrorCode.COMMON_ERROR_29);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("新航物联网" + '<' + "service@rotanovaiot.com" + '>');
        //接受者
        message.setTo(to);
        //设置邮件标题
        message.setSubject(subject);
        //设置邮件内容
        message.setText(text);
        mailSender.send(message);
        redisUtil.set(key, code, CommonConstant.VERIFICATION_CODE_DATE);
    }


    /**
     * 功能: 发送邮件
     * 作者: zjt
     * 日期: 2021/4/2 18:11
     * 版本: 1.0
     */
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(to);
        helper.setFrom("新航物联网" + '<' + "service@rotanovaiot.com" + '>');
        helper.setSubject(subject);
        helper.setText(text, true);
        mailSender.send(message);
    }


    public static int random() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        int[] c = new int[6];
        for (int i = 0; i < 6; i++) {
            c[i] = r.nextInt(9) + 1;
            sb.append(c[i]);
        }
        return Integer.parseInt(sb.toString());

    }
}
