package com.npc.common.modular.serverErrorLog.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class ServerErrorLogDto extends PageSearch {

    private Long id; 

    private LocalDateTime errorTime; 

    private String errorLevel; 

    private String serviceName; 

    private String requestUri; 

    private Long userId; 

    private String errorMessage; 

    private String stackTrace; 

    private String extraInfo; 

    private LocalDateTime createdAt; 

    private LocalDateTime updatedAt; 

}
