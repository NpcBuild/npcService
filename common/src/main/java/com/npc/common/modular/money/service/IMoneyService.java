package com.npc.common.modular.money.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.npc.common.modular.money.dto.MoneyDto;
import com.npc.common.modular.money.entity.Money;
import com.npc.common.modular.money.vo.MoneyReport;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfei
 * @since 2023-12-25
 */
public interface IMoneyService extends IService<Money> {
    MoneyReport getInfo();

    IPage<Money> getListPage(MoneyDto dto);
}
