package com.npc.client.Elasticsearch.dto;

import cn.hutool.core.date.DateUtil;
import com.npc.core.utils.StringUtils;
import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Date;
import java.util.List;

/**
 * @author NPC
 * @description 查询对象实体类
 * @create 2023/2/24 22:24
 */
@Data
public class EsQueryDTO {
    private String indexName;//索引名称
    private String field;//关键字属性
    private String word;//关键字值
    private List<String> words;//关键字值数组
    private Integer from;//起始行
    private Integer index;//当前页
    private Integer size;//分页条数
    private String order;//排序字段
    private String orderType;//排序方式 asc/desc
    private String dateField;//时间字段
    private String startTime;//时间范围-开始时间
    private String endTime;//时间范围-开始时间

    public String getOrderType() {
        if (StringUtils.isBlank(orderType)) {
            orderType = SortOrder.DESC.name();
        }
        return orderType;
    }

    public Integer getSize() {
        return size == 0 ? 30 : size;
    }

    public Integer getFrom() {
        return getIndex() != 0 ? ((getIndex() - 1) * getSize()) : 0;
    }

    public Integer getIndex() {
        return null == index ? 0 : index;
    }

    public String getStartTime(int offset) {
        if (StringUtils.isBlank(startTime)) {
            startTime = DateUtil.format(DateUtil.offsetDay(new Date(), offset), "yyyy-MM-dd 00:00:00");
            return String.valueOf(DateUtil.parse(startTime, "yyyy-MM-dd 00:00:00").getTime());
        }
        return startTime;
    }

    public String getEndTime() {
        if (StringUtils.isBlank(endTime)) {
            endTime = String.valueOf(System.currentTimeMillis());
        }
        return endTime;
    }
}
