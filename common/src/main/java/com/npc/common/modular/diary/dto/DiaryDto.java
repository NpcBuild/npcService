package com.npc.common.modular.diary.dto;

import com.npc.core.PageSearch;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @program: ChatBuddy.java
 * @description 日记查询参数Dto
 * @author: feiyang
 * @create: 2025/06/27 11:48
 **/
@Data
public class DiaryDto extends PageSearch {
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
}
