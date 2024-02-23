package com.npc.common.modular.diary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2023-12-17
 */
@Data
@TableName("t_diary")
public class Diary implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 日记唯一标识符，自增长
     */
	@TableId(value="id", type= IdType.AUTO)
    private Integer id;



    /**
     * 作者/用户唯一标识符
     */
    private Integer userId;



    /**
     * 日记日期
     */
    private LocalDate date;



    /**
     * 日记标题
     */
    private String title;



    /**
     * 日记内容
     */
    private String content;



    /**
     * 作者当时心情/情绪状态
     */
    private String mood;



    /**
     * 日记记录的位置信息
     */
    private String location;



    /**
     * 标签，用于标记关键主题或类别
     */
    private String tags;



    /**
     * 天气情况
     */
    private String weather;



    /**
     * 是否设置提醒
     */
    private Boolean reminder;



    /**
     * 是否为私密日记
     */
    private Boolean pub;



    /**
     * 阅读状态，标记是否已读
     */
    private Boolean readStatus;



    /**
     * 评分
     */
    private Integer rating;



    /**
     * 创建或最后修改时间
     */
    private LocalDateTime timestamp;

}
