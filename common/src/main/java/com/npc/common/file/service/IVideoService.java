package com.npc.common.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.file.entity.Video;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-04-08
 */
public interface IVideoService extends IService<Video> {
    Boolean generateM3U8(Integer videoId);
}
