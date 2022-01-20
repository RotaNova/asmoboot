package com.rotanava.boot.system.test;

import com.rotanava.boot.system.module.system.mq.SysUserListenter;
import com.rotanava.framework.module.dao.SysSearchConfigMapper;
import com.rotanava.framework.util.MailUtil;
import com.rotanava.framework.util.socket.PcMessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: richenLi
 * @create: 2021-03-19 10:55
 **/
@SpringBootTest()
public class ApplicationTests {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private SysUserListenter sysUserListenter;


    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("新航物联网" + '<' + "service@rotanovaiot.com" + '>');
        message.setTo("李日晨" + '<' + "719583022@qq.com" + '>');
        message.setSubject("验证码");    //设置邮件标题
        message.setText("尊敬的用户,您好:\n"
                + "\n本次请求的邮件验证码为:" + "123456" + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）");    //设置邮件正文

//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        mimeMessage.setFrom(new InternetAddress("service@rotanovaiot.com", "新航物联网")); //设置发件人Email
//        mimeMessage.setSubject("主题：简单邮件"); //设置邮件主题
//        mimeMessage.setText("测试邮件内容");   //设置邮件主题内容
//        mimeMessage.setTo("719583022@qq.com");          //设定收件人Email


        mailSender.send(message);
    }


    @Autowired
    PcMessageUtil pcMessageUtil;
    @Autowired
    SysSearchConfigMapper sysSearchConfigMapper;

    @Test
    public void testsocket() {
//        pcMessageUtil.sendMessageByOnLineUser("111",new StatisticsDTO());
//        System.out.println(GlobalClass.getMappingValue("deployUrl"));
        String sql = "select * from sys_user where user_name = #{name} " +
                "  " +
                " and user_phone in (#{phone}) " +
                "";


        Map<String, String> map = new HashMap<>();
        map.put("name", "admin");
        map.put("phone", "1,2,3");
        map.put("sql", sql);
//        Object o = sysSearchConfigMapper.doSearchSqlByObject(map);
        System.out.println();
    }


    public static void main(String[] args) {
        String sql = "select * from sys_user where user_name = #{name} " +
                "  " +
                " ${ and user_phone =#{phone} }$ " +
                "";


        // 创建 Pattern 对象
        Pattern r = Pattern.compile("\\$\\{([\\s\\S]*?)}\\$");
        // 现在创建 matcher 对象
        Matcher m = r.matcher(sql);
        if (m.find()) {
            System.out.println(m.group(0));
            sql = sql.replace(m.group(), "");

            Pattern r1 = Pattern.compile("\\#\\{([\\s\\S]*?)}");
            // 现在创建 matcher 对象
            Matcher m1 = r1.matcher(sql);
            m1.find();
            System.out.println(m1.group(0));


        }
        System.out.println(sql);
    }

    @Test
    public void testUserExpiration() {
        sysUserListenter.expirationTimeQueueListenter("43");
    }

}
