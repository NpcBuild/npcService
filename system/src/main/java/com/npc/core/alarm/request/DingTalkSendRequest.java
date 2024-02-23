package com.npc.core.alarm.request;

import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 17:03
 */
@Data
public class DingTalkSendRequest {
    private String msgtype;
    private Text text;
    private Markdown markdown;

    public static class Markdown {
        private String title;
        private String message;
        public Markdown(String title, String message) {
            this.title = title;
            this.message = message;
        }
    }

    public static class Text {
        private String message;
        public Text(String message) {
            this.message = message;
        }
    }
}
