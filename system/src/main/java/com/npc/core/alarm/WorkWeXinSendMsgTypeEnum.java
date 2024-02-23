package com.npc.core.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WorkWeXinSendMsgTypeEnum {
    TEXT("text"),
    MD("markdown");
    String type;
}
