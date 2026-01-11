package com.npc.common.modular.assets.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.assets.dto.AssetsClothingDto;
import com.npc.common.modular.assets.entity.AssetsClothing;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.assets.vo.AssetsClothingVO;
import com.npc.common.modular.dailySchedule.vo.DailyScheduleVO;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.example.demo.service.db.dto.AssetsClothingDto;
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.lk.common.model.PageDTO;

/**
 * <p>
 * 衣物资产扩展表 服务类
 * </p>
 *
 * @author yangfei
 * @since 2026-01-04
 */
public interface IAssetsClothingService extends IService<AssetsClothing> {
    IPage<AssetsClothingVO> getList(AssetsClothingDto assetsClothingDto);
}
