package com.npc.common.modular.corpus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 语料
 * </p>
 *
 * @author yangfei
 * @since 2023-10-15
 */
@Data
@TableName("t_corpus")
public class Corpus implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 创建时间
     */
    private LocalDateTime creTime;

    /**
     * 逻辑删除
     */
    private Integer flag;

}
