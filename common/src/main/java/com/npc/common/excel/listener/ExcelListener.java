package com.npc.common.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.npc.common.modular.user.entity.User;
import com.npc.common.modular.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author wow
 * @createTime 2022/9/15 21:17
 * @descripttion Excel监听器
 * 不能被spring管理，要每次读取Excel都要new，然后里面用到spring可以构造方法传进去
 */
public class ExcelListener extends AnalysisEventListener<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelListener.class);

    /**
     * 每隔3000条存储数据库，然后清空list，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;

    /**
     * 缓存的数据
     */
    private List<User> list = new ArrayList<>(BATCH_COUNT);

    private UserMapper userMapper;

    public ExcelListener(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ExcelListener() {
    }

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据：{}", JSON.toJSONString(user));
        //获取对应的行数
        int num = analysisContext.readRowHolder().getRowIndex();
        list.add(user);
        if (list.size() >= BATCH_COUNT) {
            insert();
            //存储完成后清理 list
            list = new ArrayList<>(BATCH_COUNT);
        }
        System.out.println(user);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息"+ headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //这里也要保存数据，确保最后遗留的数据也存储到数据库
        insert();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 存储数据库
     */
    private void insert() {
        LOGGER.info("{}条数据，开始存储数据库！",list.size());
        userMapper.insert(list);
        LOGGER.info("存储数据库成功！");
    }
}
