package com.npc.common.modular.feedback.entity;

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
 * 用户反馈信息表
 * </p>
 *
 * @author yangfei
 * @since 2025-11-06
 */
@Data
@TableName("t_sys_feedback")
public class SysFeedback implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 反馈唯一标识，自增长
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "反馈唯一标识，自增长")
    private Integer id;



    /**
     * 用户ID，可关联用户表
     */
    // @ApiModelProperty(value = "用户ID，可关联用户表")
    private Integer userId;



    /**
     * 反馈类型：BUG=功能问题，SUGGESTION=功能建议，UI=界面优化，PERFORMANCE=性能问题，OTHER=其他
     */
    // @ApiModelProperty(value = "反馈类型：BUG=功能问题，SUGGESTION=功能建议，UI=界面优化，PERFORMANCE=性能问题，OTHER=其他")
    private String feedbackType;



    /**
     * 反馈标题，简短描述问题
     */
    // @ApiModelProperty(value = "反馈标题，简短描述问题")
    private String title;



    /**
     * 详细反馈内容
     */
    // @ApiModelProperty(value = "详细反馈内容")
    private String content;



    /**
     * 截图或图片链接（JSON数组形式）
     */
    // @ApiModelProperty(value = "截图或图片链接（JSON数组形式）")
    private String screenshotUrls;



    /**
     * 联系方式（邮箱/手机号/微信等）
     */
    // @ApiModelProperty(value = "联系方式（邮箱/手机号/微信等）")
    private String contactInfo;



    /**
     * 设备信息（手机型号、浏览器、操作系统等）
     */
    // @ApiModelProperty(value = "设备信息（手机型号、浏览器、操作系统等）")
    private String deviceInfo;



    /**
     * App或网站版本号
     */
    // @ApiModelProperty(value = "App或网站版本号")
    private String appVersion;



    /**
     * 处理状态：待处理、处理中、已解决、已关闭
     */
    // @ApiModelProperty(value = "处理状态：待处理、处理中、已解决、已关闭")
    private String status;



    /**
     * 管理员回复或处理说明
     */
    // @ApiModelProperty(value = "管理员回复或处理说明")
    private String adminReply;



    /**
     * 问题优先级
     */
    // @ApiModelProperty(value = "问题优先级")
    private String priority;



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
