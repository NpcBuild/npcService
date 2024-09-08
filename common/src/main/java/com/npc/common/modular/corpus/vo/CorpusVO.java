package com.npc.common.modular.corpus.vo;

import com.npc.core.PageSearch;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author NPC
 * @description
 * @create 2023/10/15 10:55
 */
@Data
public class CorpusVO extends PageSearch {
    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签
     */
    private String tag;

    /**
     * 数据打分
     */
    private Integer score;

    /**
     * 创建时间
     */
    private LocalDateTime creTime;

    /**
     * 逻辑删除
     */
    private Integer flag;
}
