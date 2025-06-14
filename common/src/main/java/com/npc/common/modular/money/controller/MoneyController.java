package com.npc.common.modular.money.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.money.dto.MoneyDto;
import com.npc.common.modular.money.entity.Money;
import com.npc.common.modular.money.mapper.MoneyMapper;
import com.npc.common.modular.money.service.IMoneyService;
import com.npc.common.modular.money.vo.MoneyReport;
import com.npc.common.modular.money.vo.MoneyVO;
import com.npc.common.modular.stock.redis.RedisKeysConstant;
import com.npc.common.todo.entity.Todo;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import com.npc.core.utils.StringUtils;
import com.npc.redis.utils.RedisPoolUtil;
import com.npc.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2023-12-25
 */
@RestController
@RequestMapping("/money")
public class MoneyController {
    
    private static final Logger logger = LoggerFactory.getLogger(MoneyController.class);

    @Autowired
    public IMoneyService moneyService;
    @Resource
    private MoneyMapper moneyMapper;


    /**
     * 保存、修改 【区分id即可】
     * @param money 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ServerResponseVO<?> save(@RequestBody @Validated Money money) {
        try {
            if (null != money.getPaymentTime() && null == money.getDate()) {
                money.setDate(money.getPaymentTime().toLocalDate());
            }
            if (money.getNotes().contains("订阅任务")) {
                money.setRecurringTransaction(true);
            }
            // 保证收入金额始终为负数入库
            if (money.getCategory().contains("收入")) {
                BigDecimal number = money.getAmount();
                if (number.compareTo(BigDecimal.ZERO) > 0) {
                    money.setAmount(number.negate());
                }
            }
            if (money.getCategory().contains("通勤") || money.getDescription().contains("通勤")) {
                money.setRecurringTransaction(true);
                Money money1 = new Money();
                BeanUtil.copyProperties(money,money1);
                moneyService.saveOrUpdate(money1);
            }
            boolean obj = moneyService.saveOrUpdate(money);
            return ServerResponseVO.success(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }


    /**
     * 查询信息
     * @return ServerResponseVO转换结果
     */
    @GetMapping("getInfo")
    public ServerResponseVO<?> getInfo() {

        try {
            MoneyReport money =moneyService.getInfo();
            return ServerResponseVO.success(money);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }


    /**
     * 通过Id 删除对象
     * @param id 要删除的实体
     * @return ServerResponseVO转换结果
     */
    @GetMapping("deleteMoneyById")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            boolean money =moneyService.removeById(id);
            return ServerResponseVO.success(money);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteMoneyByIdList")
    @Transactional(rollbackFor = Exception.class)
    public ServerResponseVO<?> batchDeleteMoneyByIdList(@RequestParam("ids") Integer[] ids) {

        moneyService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getMoneyById", method = RequestMethod.GET)
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        Money money =moneyService.getById(id);
        return ServerResponseVO.success(money);
    }


    /**
     * 分页查询数据：
     * @param moneyDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getMoneyList", method = RequestMethod.GET)
    public ServerResponseVO<?> getMoneyList(@Validated MoneyDto moneyDto) {
        IPage<Money> page = moneyService.getListPage(moneyDto);
        return ServerResponseVO.success(page);
    }

    /**
     * 统计信息
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getMoneyChart", method = RequestMethod.GET)
    public ServerResponseVO<?> getMoneyChart(@Validated MoneyDto moneyDto) {
        Object money =moneyService.getChart(moneyDto);
        return ServerResponseVO.success(money);
    }
    /**
     * 总资产
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ServerResponseVO<?> getAll(@Validated MoneyDto moneyDto) {
        String dateStartS = null;
        BigDecimal point = BigDecimal.ZERO;
        // 查询固定时间的资产
        String moneyPoint = RedisPoolUtil.get("money_point");
        logger.info("moneyPoint:{}", moneyPoint);
        if (StringUtils.isNotEmpty(moneyPoint)) {
            String[] split = moneyPoint.split(",");
            dateStartS = split[0];
            if (split.length >= 2) {
                point = new BigDecimal(split[1]);
            }
            logger.info("dateStartS:{}", dateStartS);
        }
        BigDecimal payedMoney = moneyMapper.getPaiedMoneyCount(dateStartS, moneyDto.getDateEndS());
        if (payedMoney == null) payedMoney = BigDecimal.ZERO;
        BigDecimal willPayMoney = moneyMapper.getSubMoneyCount(ObjectUtils.isEmpty(moneyDto.getId()) ? null : moneyDto.getId().toString(), DateUtils.getTime(), moneyDto.getDateEndS());
        BigDecimal result = point.subtract(payedMoney).subtract(willPayMoney);
        if (moneyDto.getId() == null || moneyDto.getId().equals(2)) {
            // 提前还款
            if (LocalDateTime.now().isBefore(LocalDateTime.parse("2026-11-30T08:59:41"))) result = result.add(new BigDecimal("19212"));
        }
        return ServerResponseVO.success(result);
    }
}
