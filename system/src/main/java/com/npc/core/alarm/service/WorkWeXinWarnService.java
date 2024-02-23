package com.npc.core.alarm.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.npc.core.alarm.WorkWeXinSendMsgTypeEnum;
import com.npc.core.alarm.request.WorkWeXinSendRequest;
import com.npc.core.alarm.service.BaseWarnService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author NPC
 * @description 发送企业微信告警信息的逻辑
 * @create 2023/4/9 15:32
 */
@Slf4j
public class WorkWeXinWarnService extends BaseWarnService {

    private static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=%s";
    private final String key;

    private final String toUser;

    /**
     * 删除
     */
    public WorkWeXinWarnService() {
        this.key = "";
        this.toUser = "";
    }
    public WorkWeXinWarnService(String key, String toUser) {
        this.key = key;
        this.toUser = toUser;
    }

    private String createPostData(WorkWeXinSendMsgTypeEnum messageTye, String contentValue) {
        WorkWeXinSendRequest wcd = new WorkWeXinSendRequest();
        wcd.setMsgtype(messageTye.getType());
        List<String> toUsers = Arrays.asList("@all");
        if (StringUtils.isNotEmpty(toUser)) {
            String[] split = toUser.split("\\|");
            toUsers = Arrays.asList(split);
        }
        if (messageTye.equals(WorkWeXinSendMsgTypeEnum.TEXT)) {
            WorkWeXinSendRequest.Text text = new WorkWeXinSendRequest.Text(contentValue, toUsers);
            wcd.setText(text);
        } else if (messageTye.equals(WorkWeXinSendMsgTypeEnum.MD)) {
            WorkWeXinSendRequest.Markdown markdown = new WorkWeXinSendRequest.Markdown(contentValue, toUsers);
            wcd.setMarkdown(markdown);
        }
        return JSONUtil.toJsonStr(wcd);
    }

    @Override
    protected void doSendText(String message) {
        String data = createPostData(WorkWeXinSendMsgTypeEnum.TEXT, message);
        String url = String.format(SEND_MESSAGE_URL, key);
        String resp = HttpRequest.post(url).body(data).execute().body();
        log.info("send work weixin message call [{}], param:{}, resp:{}", url, data, resp);
    }

    @Override
    protected void doSendMarkdown(String title, String message) {
        String data = createPostData(WorkWeXinSendMsgTypeEnum.MD, message);
        String url = String.format(SEND_MESSAGE_URL, key);
        String resp = HttpRequest.post(url).body(data).execute().body();
        log.info("send work weixin message call [{}], param:{}, resp:{}", url, data, resp);
    }

}
