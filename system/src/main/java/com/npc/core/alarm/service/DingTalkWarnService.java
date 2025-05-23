package com.npc.core.alarm.service;

import com.npc.core.alarm.DingTalkSendMsgTypeEnum;
import com.npc.core.alarm.request.DingTalkSendRequest;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 15:44
 */
@Slf4j
public class DingTalkWarnService extends BaseWarnService {

    private static final String ROBOT_SEND_URL = "https://oapi.dingtalk.com/robot/send?access_token=";
    private final String token;

    private final String secret;

    public DingTalkWarnService(String token, String secret) {
        this.token = token;
        this.secret = secret;
    }

    public void sendRobotMessage(DingTalkSendRequest dingTalkSendRequest) throws Exception {
        String json = JSONUtil.toJsonStr(dingTalkSendRequest);
        String sign = getSign();
        String body = HttpRequest.post(sign).contentType(ContentType.JSON.getValue()).body(json).execute().body();
        log.info("钉钉机器人通知结果：{}", body);
    }

    /**
     * 获取签名
     *
     * @return 返回签名
     */
    private String getSign() throws Exception {
        long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return ROBOT_SEND_URL + token + "&timestamp=" + timestamp + "&sign=" + URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), StandardCharsets.UTF_8.toString());
    }

    @Override
    protected void doSendText(String message) throws Exception {
        DingTalkSendRequest param = new DingTalkSendRequest();
        param.setMsgtype(DingTalkSendMsgTypeEnum.TEXT.getType());
        param.setText(new DingTalkSendRequest.Text(message));
        sendRobotMessage(param);
    }

    @Override
    protected void doSendMarkdown(String title, String message) throws Exception {
        DingTalkSendRequest param = new DingTalkSendRequest();
        param.setMsgtype(DingTalkSendMsgTypeEnum.MD.getType());
        DingTalkSendRequest.Markdown markdown = new DingTalkSendRequest.Markdown(title, message);
        param.setMarkdown(markdown);
        sendRobotMessage(param);
    }
}
