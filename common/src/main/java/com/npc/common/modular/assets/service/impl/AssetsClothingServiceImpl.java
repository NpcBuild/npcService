package com.npc.common.modular.assets.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.npc.common.modular.assets.dto.AssetsClothingDto;
import com.npc.common.modular.assets.vo.AssetsClothingVO;
import com.npc.common.modular.dailySchedule.entity.DailySchedule;
import com.npc.common.modular.dailySchedule.vo.DailyScheduleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.example.demo.service.db.dto.AssetsClothingDto;


import com.npc.common.modular.assets.entity.AssetsClothing;
import com.npc.common.modular.assets.mapper.AssetsClothingMapper;
import com.npc.common.modular.assets.service.IAssetsClothingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 衣物资产扩展表 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2026-01-04
 */
@Service
public class AssetsClothingServiceImpl extends ServiceImpl<AssetsClothingMapper, AssetsClothing> implements IAssetsClothingService {

    private static final Logger logger = LoggerFactory.getLogger(AssetsClothingServiceImpl.class);


    @Override
    public IPage<AssetsClothingVO> getList(AssetsClothingDto assetsClothingDto) {
        // 创建分页对象
        Page<AssetsClothing> page = new Page<>(assetsClothingDto.getPageNum(), assetsClothingDto.getPageSize());
        return this.baseMapper.getList(page, assetsClothingDto);
    }
}
