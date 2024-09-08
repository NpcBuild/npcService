package com.npc.common.modular.plan.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 曼陀罗计划表
 * </p>
 *
 * @author yangfei
 * @since 2024-07-20
 */
@Data
@TableName("t_plan")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;



    /**
     * 父级ID
     */
    private Integer parentId;



    /**
     * 内容
     */
    private String content;



    /**
     * 待办事项ID
     */
    private String todoId;



    /**
     * 备注
     */
    private String remark;



    /**
     * 排序
     */
    private Integer sort;



    /**
     * 标签
     */
    private String tag;

}
