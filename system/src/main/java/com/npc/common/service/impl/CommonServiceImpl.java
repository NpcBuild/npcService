package com.npc.common.service.impl;

import com.npc.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wow
 * @createTime 2022/10/30 18:48
 * @descripttion 通用类
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public Map getById(int sid) {
        return new HashMap(sid);
    }
}
