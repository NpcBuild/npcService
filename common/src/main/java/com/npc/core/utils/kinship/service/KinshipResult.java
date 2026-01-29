package com.npc.core.utils.kinship.service;

import com.npc.common.modular.chat.entity.ChatBuddyRelations;

import java.util.List;
/**
 * @program: npcService
 * @description
 * @author: feiyang
 * @create: 2026/01/27 22:50
 **/
public class KinshipResult {

    public String relationName;
    public List<ChatBuddyRelations> path;
    public String explanation;

    public KinshipResult(String relationName,
                         List<ChatBuddyRelations> path,
                         String explanation) {
        this.relationName = relationName;
        this.path = path;
        this.explanation = explanation;
    }
}
