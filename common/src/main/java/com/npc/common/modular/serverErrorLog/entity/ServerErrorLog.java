package com.npc.common.modular.serverErrorLog.entity;

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
 * 
 * </p>
 *
 * @author yangfei
 * @since 2025-10-25
 */
@Data
@TableName("sys_server_error_log")
public class ServerErrorLog implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "id")
    private Long id;
    // @ApiModelProperty(value = "errorTime")
    private LocalDateTime errorTime;
    // @ApiModelProperty(value = "errorLevel")
    private String errorLevel;
    // @ApiModelProperty(value = "serviceName")
    private String serviceName;
    // @ApiModelProperty(value = "requestUri")
    private String requestUri;
    // @ApiModelProperty(value = "userId")
    private Long userId;
    // @ApiModelProperty(value = "errorMessage")
    private String errorMessage;
    // @ApiModelProperty(value = "stackTrace")
    private String stackTrace;
    // @ApiModelProperty(value = "extraInfo")
    private String extraInfo;
    // @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;
    // @ApiModelProperty(value = "updatedAt")
    private LocalDateTime updatedAt;

}
