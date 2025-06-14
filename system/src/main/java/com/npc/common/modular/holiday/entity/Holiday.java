package com.npc.common.modular.holiday.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2025-02-16
 */
@Data
@TableName("t_holiday")
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 节假日的日期
     */
    @TableId(type = IdType.INPUT) // 表明手动输入主键值
    private String date;



    /**
     * 节假日的中文名
     */
    private String name;



    /**
     * 是否节假日
     */
    private Boolean holiday;



    /**
     * 薪资倍数，3表示是3倍工资
     */
    private Integer wage;



    /**
     * true表示放完假后调休，false表示先调休再放假
     */
    private String after;



    /**
     * 表示调休的节假日
     */
    private String target;

}
