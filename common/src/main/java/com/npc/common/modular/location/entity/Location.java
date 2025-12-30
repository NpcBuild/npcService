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
     * 名称
     */
    private String name;

    /**
     * 位置名
     */
    private String location;

    /**
     * 类型（case restaurant = "餐厅"
     *     case attraction = "景点"
     *     case hotel = "酒店"
     *     case shopping = "购物"
     *     case entertainment = "娱乐"
     *     case address = "住址"
     *     case other = "其他"）
     */
    private String type;

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
