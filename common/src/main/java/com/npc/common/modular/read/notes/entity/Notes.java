package com.npc.common.modular.read.notes.entity;

import java.io.Serializable;
//import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Data
@TableName("read_notes")
public class Notes implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
//    @ApiModelProperty(value = "id")
    private Integer id;



    /**
     * 用户 ID
     */
//    @ApiModelProperty(value = "用户 ID")
    private Integer userId;



    /**
     * 关联的书籍 / 电影
     */
//    @ApiModelProperty(value = "关联的书籍 / 电影")
    private Integer contentId;



    /**
     * 评分（可选）
     */
//    @ApiModelProperty(value = "评分（可选）")
    private BigDecimal rating;



    /**
     * 读书 / 观影笔记
     */
//    @ApiModelProperty(value = "读书 / 观影笔记")
    private String notes;
//    @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;

}
