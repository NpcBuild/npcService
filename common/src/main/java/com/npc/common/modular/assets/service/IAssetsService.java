package com.npc.common.modular.assets.service;

import com.npc.common.modular.assets.entity.Assets;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 物品表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
public interface IAssetsService extends IService<Assets> {
    List<Assets> getMyAssetsList();
}
