package com.npc.common.modular.assets.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.npc.common.modular.assets.entity.Assets;
import com.npc.common.modular.assets.mapper.AssetsMapper;
import com.npc.common.modular.assets.service.IAssetsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 物品表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Service
public class AssetsServiceImpl extends ServiceImpl<AssetsMapper, Assets> implements IAssetsService {

    private static final Logger logger = LoggerFactory.getLogger(AssetsServiceImpl.class);

    @Resource
    private AssetsMapper assetsMapper;

    @Override
    public List<Assets> getMyAssetsList() {
        int userId = 1;
        List<Assets> assetsList = assetsMapper.getMyAssetsList(userId);
        return assetsList;
    }
}
