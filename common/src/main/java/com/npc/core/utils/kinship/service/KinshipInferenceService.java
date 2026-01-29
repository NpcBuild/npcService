package com.npc.core.utils.kinship.service;

import com.npc.common.modular.chat.entity.ChatBuddyRelations;
import com.npc.core.utils.kinship.enums.RelationType;
import com.npc.core.utils.kinship.graph.RelationshipGraph;
import com.npc.core.utils.kinship.model.KinshipAtom;
import com.npc.core.utils.kinship.rule.*;
import com.npc.core.utils.kinship.engine.KinshipConflictResolver;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: npcService
 * @description
 * @author: feiyang
 * @create: 2026/01/27 22:49
 **/
@Service
public class KinshipInferenceService {

    private final KinshipConflictResolver resolver = new KinshipConflictResolver();

    // 图遍历算法，通过构建关系图并搜索路径来推断两个人之间的亲属关系类型
    public Optional<KinshipResult> infer(
            Integer sourceId,
            Integer targetId,
            List<ChatBuddyRelations> relations
    ) {
        RelationshipGraph graph = new RelationshipGraph(relations);
        Queue<List<ChatBuddyRelations>> queue = new LinkedList<>();

        graph.next(sourceId).forEach(r -> queue.offer(Arrays.asList(r)));

        List<KinshipRule> matched = new ArrayList<>();
        Map<KinshipRule, List<ChatBuddyRelations>> rulePathMap = new HashMap<>();

        while (!queue.isEmpty()) {
            List<ChatBuddyRelations> path = queue.poll();
            ChatBuddyRelations last = path.get(path.size() - 1);

            if (last.getToId().equals(targetId)) {
                // 添加安全检查
                List<KinshipAtom> atoms = path.stream()
                    .map(r -> {
                        try {
                            RelationType relationType = RelationType.from(r.getTypeIds());
                            return relationType != null ? relationType.atom : null;
                        } catch (Exception e) {
                            // 记录日志或处理异常情况
                            System.err.println("Invalid relation type: " + r.getTypeIds());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

                if (!atoms.isEmpty()) { // 确保原子列表不为空
                    for (KinshipRule rule : ChineseKinshipRules.RULES) {
                        if (atoms.equals(rule.pattern)) {
                            matched.add(rule);
                            rulePathMap.put(rule, path);
                        }
                    }
                }
            }

            if (path.size() < 3) {
                graph.next(last.getToId()).forEach(next -> {
                    List<ChatBuddyRelations> p = new ArrayList<>(path);
                    p.add(next);
                    queue.offer(p);
                });
            }
        }

        if (matched.isEmpty()) return Optional.empty();

        KinshipRule best = resolver.resolve(matched);
        return Optional.of(new KinshipResult(
                best.name,
                rulePathMap.get(best),
                explain(rulePathMap.get(best))
        ));
    }

    private String explain(List<ChatBuddyRelations> path) {
        StringBuilder sb = new StringBuilder("关系推导：");
        for (ChatBuddyRelations r : path) {
            sb.append(r.getFromId())
                    .append(" 的 ")
//                    .append(r.getTypeIds())
                    .append(RelationType.from(r.getTypeIds()).name())
                    .append(" 是 ")
                    .append(r.getToId())
                    .append("；");
        }
        return sb.toString();
    }
}
