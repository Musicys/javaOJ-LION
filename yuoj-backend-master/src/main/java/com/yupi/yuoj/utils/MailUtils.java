package com.yupi.yuoj.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

@Component
public class MailUtils {

    // 静态字段
    private static String host;
    private static int port;
    private static String username;
    private static String password;

    // 实例字段，用于注入配置
    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    // 使用 @PostConstruct 初始化静态字段
    @PostConstruct
    public void init() {
        host = this.mailHost;
        port = this.mailPort;
        username = this.mailUsername;
        password = this.mailPassword;
    }

    /**
     * 生成验证码
     */
    public static int generateVerificationCode() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    /**
     * 发送验证码邮件
     */
    public static void sendMail(String to, int vcode) throws Exception {
        Properties props = new Properties();

        // 设置主机地址
        props.setProperty("mail.smtp.host", host);

        // 设置端口
        props.setProperty("mail.smtp.port", String.valueOf(port));

        // 开启认证
        props.put("mail.smtp.auth", "true");

        // SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        // 创建会话
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            // 设置发件人
            message.setFrom(new InternetAddress(username));

            // 设置收件人
            message.setRecipient(RecipientType.TO, new InternetAddress(to));

            // 设置邮件主题
            message.setSubject("来自OJ平台注册验证码");

            // 设置邮件正文
            message.setContent(
                    "<h1>请接收你的验证码：</h1><h3>你的验证码是：<strong><b>" + vcode + "</b></strong>，请妥善保管好你的验证码！</h3>",
                    "text/html;charset=UTF-8"
            );

            // 发送邮件
            Transport.send(message);

        } catch (MessagingException mex) {
            mex.printStackTrace();
            throw new Exception("邮件发送失败：" + mex.getMessage());
        }
    }
}