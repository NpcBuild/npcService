package com.npc.common.modular.career.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 解决方案 / 创业项目表
 * </p>
 *
 * @author yangfei
 * @since 2025-12-17
 */
@Data
@TableName("t_solution_project")
public class SolutionProject implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 解决方案ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "解决方案ID")
    private Integer id;



    /**
     * 项目标题
     */
    // @ApiModelProperty(value = "项目标题")
    private String title;



    /**
     * 项目描述
     */
    // @ApiModelProperty(value = "项目描述")
    private String description;



    /**
     * 关联的社会问题ID(可为空)
     */
    // @ApiModelProperty(value = "关联的社会问题ID(可为空)")
    private Integer linkedProblemId;



    /**
     * 项目阶段
     */
    // @ApiModelProperty(value = "项目阶段")
    private String stage;



    /**
     * 影响力评分(1-5)
     */
    // @ApiModelProperty(value = "影响力评分(1-5)")
    private Integer impactScore;



    /**
     * 投入评估(1-5)
     */
    // @ApiModelProperty(value = "投入评估(1-5)")
    private Integer effortScore;



    /**
     * 可行性评分(1-5)
     */
    // @ApiModelProperty(value = "可行性评分(1-5)")
    private Integer feasibility;



    /**
     * 关键进展
     */
    // @ApiModelProperty(value = "关键进展")
    private String traction;



    /**
     * 下一步行动
     */
    // @ApiModelProperty(value = "下一步行动")
    private String nextAction;



    /**
     * 标签，逗号分隔
     */
    // @ApiModelProperty(value = "标签，逗号分隔")
    private String tags;



    /**
     * 是否收藏
     */
    // @ApiModelProperty(value = "是否收藏")
    private Boolean favorite;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;



    /**
     * 更新时间
     */
    // @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

}
