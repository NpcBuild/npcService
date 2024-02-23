package com.npc.core.alarm.request;

import lombok.Data;

import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 17:16
 */
@Data
public class WorkWeXinSendRequest {
    private String msgtype;
    private Text text;
    private Markdown markdown;

    public static class Markdown {
        private String message;
        private List<String> toUsers;
        public Markdown(String message, List<String> toUsers) {
            this.message = message;
            this.toUsers = toUsers;
        }
    }

    public static class Text {
        private String message;
        private List<String> toUsers;
        public Text(String message, List<String> toUsers) {
            this.message = message;
            this.toUsers = toUsers;
        }
    }
}
