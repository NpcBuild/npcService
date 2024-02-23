package com.npc.common.mail.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;

/**
 * @author NPC
 * @description
 * @create 2023/2/26 16:19
 */
@Slf4j
@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("1623285565@qq.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String content = templateEngine.process(templateName, new Context(Locale.getDefault(), variables));
        helper.setText(content, true);

        mailSender.send(message);
        log.info("已发送邮件给：" + to);
    }
}
