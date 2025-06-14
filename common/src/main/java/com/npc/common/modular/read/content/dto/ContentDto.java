package com.npc.common.modular.read.content.dto;

import com.npc.core.PageSearch;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Year;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Data
public class ContentDto extends PageSearch {

    private Integer id; 

    private String type;  // 类型：书籍 / 电影 

    private String title;  // 书名 / 电影名 

    private String author;  // 创作者 

    private Year releaseYear;  // 出版 / 上映年份 

    private String genre;  // 类别（如 科幻 / 悬疑） 

    private String coverUrl;  // 封面图片 URL 

    private String description;  // 简介 

    private String status;  // 状态（想读 / 已读） 

    private LocalDateTime createdAt; 

}
