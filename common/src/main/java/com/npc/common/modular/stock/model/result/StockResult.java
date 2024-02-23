package com.npc.common.modular.stock.model.result;

import lombok.Data;

/**
 * @Author wow
 * @createTime 2022/10/24 22:44
 * @descripttion
 */
@Data
public class StockResult {
    private Integer id;

    private String name;
    private Integer count;
    private Integer sale;
    private Integer version;
}
