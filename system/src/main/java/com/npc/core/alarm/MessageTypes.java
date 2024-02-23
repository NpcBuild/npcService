package com.npc.core.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageTypes {
    MD("markdown"),
    TEXT("text");
    String type;
}
