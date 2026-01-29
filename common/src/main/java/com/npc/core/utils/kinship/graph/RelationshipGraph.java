package com.npc.core.utils.kinship.graph;

import com.npc.common.modular.chat.entity.ChatBuddyRelations;
import java.util.*;
/**
 * @program: npcService
 * @description 关系图结构，建关系网络
 * @author: feiyang
 * @create: 2026/01/27 22:47
 **/
public class RelationshipGraph {

    private final Map<Integer, List<ChatBuddyRelations>> graph = new HashMap<>();

    public RelationshipGraph(List<ChatBuddyRelations> relations) {
        for (ChatBuddyRelations r : relations) {
            graph.computeIfAbsent(r.getFromId(), k -> new ArrayList<>()).add(r);
        }
    }

    public List<ChatBuddyRelations> next(Integer id) {
        return graph.getOrDefault(id, Arrays.asList());
    }
}
