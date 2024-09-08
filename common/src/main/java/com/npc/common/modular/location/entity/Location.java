package com.npc.common.modular.location.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Blob;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2024-06-23
 */
@Data
@TableName("t_location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Long id;



    /**
     * 位置名
     */
    private String location;



    /**
     * 坐标（经纬度）
     */
    private String coordinates;



    /**
     * 是否去过
     */
    private Integer visited;



    /**
     * 描述
     */
    private String description;



    /**
     * 评分（星级）
     */
    private Integer stars;

}
