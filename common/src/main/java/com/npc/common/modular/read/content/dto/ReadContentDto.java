package com.npc.common.modular.read.content.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Year;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2025-10-12
 */
@Data
public class ReadContentDto extends PageSearch {

    private Integer id; 

    private String type;  // 类型：书籍 / 电影 

    private String title;  // 书名 / 电影名 

    private String author;  // 创作者 

    private Year releaseYear;  // 出版 / 上映年份 

    private Double rating;  // 评分 

    private String genre;  // 类别（如 科幻 / 悬疑） 

    private String coverUrl;  // 封面图片 URL 

    private String description;  // 简介 

    private String status;  // 状态（想读 / 已读） 

    private LocalDateTime createdAt; 

}
