package com.npc.common.modular.assets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.AssetsFoodDto;


import com.npc.common.modular.assets.entity.AssetsFood;
import com.npc.common.modular.assets.mapper.AssetsFoodMapper;
import com.npc.common.modular.assets.service.IAssetsFoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 食材资产扩展表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@Service
public class AssetsFoodServiceImpl extends ServiceImpl<AssetsFoodMapper, AssetsFood> implements IAssetsFoodService {

    private static final Logger logger = LoggerFactory.getLogger(AssetsFoodServiceImpl.class);
}
