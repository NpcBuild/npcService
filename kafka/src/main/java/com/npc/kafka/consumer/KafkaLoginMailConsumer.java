package com.npc.kafka.consumer;

import com.npc.common.mail.server.MailService;
import com.npc.kafka.domain.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wow
 */
@Component
public class KafkaLoginMailConsumer {

    @Autowired
    private JavaMailSender javaMailSender;

    @KafkaListener(topics = {"loginMailTopic"})
    public void receiveMessage(KafkaMessage kafkaMessage) {   //进行消息接收处理
//        /**
//         * 登录成功发送邮件提醒
//         */
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom("1623285565@qq.com");
//        msg.setBcc();
//        msg.setTo("zhazhafei@aliyun.com");
//        msg.setSubject("登录");
//        msg.setText("扫码登录成功提醒");
//        try {
//            javaMailSender.send(msg);
//            System.out.printf("邮件发送成功！");
//        } catch (MailException ex) {
//            System.err.println(ex.getMessage());
//        }
        /**
         * 按照模板发送邮件
         */
        MimeMessage message = javaMailSender.createMimeMessage();
        Map<String, Object> variables = new HashMap<>();
        variables.put("title", "Hello, world!");
        //获取验证码 存入redis todo
        int randomCode = (int) ((Math.random() * 9 + 1) * 100000);
//        redisUtil.set(Constant.CUSTOMER_EMAIL + email, randomCode, 1800);
        variables.put("message", randomCode);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("1623285565@qq.com");
            helper.setTo("zhazhafei@aliyun.com");
            helper.setSubject("Login Email");
            String content = getTemplateContent("email-template", variables);
            helper.setText(content, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(message);
        System.err.println("【*** 接收登录邮件消息 ***】 Id=" + kafkaMessage.getId() + "、name = " + kafkaMessage.getName());
        System.out.printf("topic = %s,offset = %d,message = %s \n","loginMailTopic",000,kafkaMessage.getMessage());
    }
    private String getTemplateContent(String templateName, Map<String, Object> variables) {
        try {
            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setPrefix("templates/");
            resolver.setSuffix(".html");
            resolver.setTemplateMode(TemplateMode.HTML);
            resolver.setCharacterEncoding("UTF-8");
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);
            Context context = new Context();
            context.setVariables(variables);
            return templateEngine.process(templateName, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
