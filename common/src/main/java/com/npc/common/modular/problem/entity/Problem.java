package com.npc.common.modular.problem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 问题及解决方案
 * </p>
 *
 * @author yangfei
 * @since 2023-12-21
 */
@Data
@TableName("t_problem")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 问题唯一标识符，自增长
     */
	@TableId(value="id", type= IdType.AUTO)
    private Integer id;



    /**
     * 问题记录日期
     */
    private LocalDate date;



    /**
     * 问题标题
     */
    private String title;



    /**
     * 问题描述
     */
    private String description;



    /**
     * 问题发生的环境或上下文信息
     */
    private String environment;



    /**
     * 解决方法或建议
     */
    private String solution;



    /**
     * 问题或解决方法的附加注释或笔记
     */
    private String notes;



    /**
     * 问题类别
     */
    private String category;



    /**
     * 标签，用于关键词标记
     */
    private String tags;



    /**
     * 问题状态（未解决、已解决等）
     */
    private String status;



    /**
     * 问题解决的日期和时间
     */
    private LocalDateTime resolutionDate;



    /**
     * 问题严重程度
     */
    private Integer severity;



    /**
     * 负责人
     */
    private String assignedTo;



    /**
     * 问题优先级
     */
    private Integer priority;



    /**
     * 附加文件、图片等
     */
    private String attachments;



    /**
     * 相关联的问题信息
     */
    private String relatedProblems;

}
