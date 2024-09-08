package com.npc.common.modular.setting.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2024-06-09
 */
@Data
@TableName("t_setting")
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;



    /**
     * 用户Id
     */
    private Integer userId;



    /**
     * 语音助手
     */
    private Boolean voiceAssistant;

}
