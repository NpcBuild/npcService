package com.npc.core.alarm.service;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 15:33
 */
@Slf4j
public class MailWarnService extends BaseWarnService {

    private final String smtpHost;

    private final String smtpPort;

    private final String to;

    private final String from;

    private final String username;

    private final String password;

    private Boolean ssl = true;

    private Boolean debug = false;

    public MailWarnService(String smtpHost, String smtpPort, String to, String from, String username, String password) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.to = to;
        this.from = from;
        this.username = username;
        this.password = password;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    @Override
    protected void doSendText(String message) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", smtpHost);
        props.setProperty("mail.smtp.port", smtpPort);
        props.put("mail.smtp.ssl.enable", true);
        Session session = Session.getInstance(props);
        session.setDebug(false);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        for (String toUser : to.split(",")) {
            msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toUser));
        }
//        Map<String, String> map = JSONUtil.toBean(message, Map.class);
//        msg.setSubject(map.get("subject"), "UTF-8");
//        msg.setContent(map.get("content"), "text/html;charset=UTF-8");
        /**
         * 删除
         */
        msg.setSubject("登录异常", "UTF-8");
        msg.setContent(message, "text/html;charset=UTF-8");
        msg.setSentDate(new Date());
        Transport transport = session.getTransport();
        transport.connect(username, password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

    @Override
    protected void doSendMarkdown(String title, String message) throws Exception {
        log.warn("暂不支持发送Markdown邮件");
    }
}
