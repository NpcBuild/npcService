package com.npc.common.modular.assets.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.AssetLocationDto;


import com.npc.common.modular.assets.entity.AssetLocation;
import com.npc.common.modular.assets.mapper.AssetLocationMapper;
import com.npc.common.modular.assets.service.IAssetLocationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 资产位置表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@Service
public class AssetLocationServiceImpl extends ServiceImpl<AssetLocationMapper, AssetLocation> implements IAssetLocationService {

    private static final Logger logger = LoggerFactory.getLogger(AssetLocationServiceImpl.class);
}
