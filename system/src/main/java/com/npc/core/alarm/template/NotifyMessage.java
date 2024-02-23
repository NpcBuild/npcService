package com.npc.core.alarm.template;

import com.npc.core.alarm.MessageTypes;
import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2023/4/9 16:38
 */
@Data
public class NotifyMessage {
    private String title;
    private String message;
    private MessageTypes messageTye;
}
