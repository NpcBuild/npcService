package com.npc.core.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DingTalkSendMsgTypeEnum {
    TEXT("text"),
    MD("markdown");
    String type;

}
