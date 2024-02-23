package com.npc.common.modular.file.service;

import com.npc.common.modular.file.entity.Files;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * <p>
 * 缓存文件 服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-08-05
 */
public interface IFileService extends IService<Files> {
    Boolean cacheFiles(Integer id);
}
