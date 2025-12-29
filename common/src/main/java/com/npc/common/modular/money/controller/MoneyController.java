package com.npc.common.modular.money.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.assets.entity.Assets;
import com.npc.common.modular.assets.service.IAssetsService;
import com.npc.common.modular.diet.recipes.entity.Recipes;
import com.npc.common.modular.diet.recipes.service.IRecipesService;
import com.npc.common.modular.money.dto.MoneyDto;
import com.npc.common.modular.money.entity.Money;
import com.npc.common.modular.money.entity.MoneyAccount;
import com.npc.common.modular.money.mapper.MoneyMapper;
import com.npc.common.modular.money.service.IMoneyAccountService;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Autowired
    private IRecipesService recipesService;
    @Autowired
    private IAssetsService assetsService;
    @Autowired
    private IMoneyAccountService moneyAccountService;


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
            if (StringUtils.isNotEmpty(money.getNotes()) && money.getNotes().contains("订阅任务")) {
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
//                money.setRecurringTransaction(true);
                Money money1 = new Money();
                BeanUtil.copyProperties(money,money1);
                moneyService.saveOrUpdate(money1);
            }
            // 当 category 包含 "午餐" 时，处理菜谱信息
            if (money.getDescription().contains("午餐")) {
                String recipeInfo = money.getDescription();
                if (recipeInfo.startsWith("午餐 ")) {
                    String[] recipeNames = recipeInfo.substring(3).split("、");
                    for (String recipeName : recipeNames) {
                        // 调用新方法保存菜谱信息
                        recipesService.saveIfNotExists(recipeName.trim());
                    }
                }
            }
            boolean obj = moneyService.saveOrUpdate(money);
            return ServerResponseVO.success(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }


    /**
     * 查询统计信息
     * @return ServerResponseVO转换结果
     */
    @GetMapping("getInfo")
    public ServerResponseVO<?> getInfo(@RequestParam("date") String date) {
        try {
            MoneyReport money =moneyService.getInfo(date);
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
//        String moneyPoint = RedisPoolUtil.get("money_point");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", 1);
        List<MoneyAccount> list = moneyAccountService.list(queryWrapper);
        LocalDateTime latestUpdatedAt = null;
        for (MoneyAccount moneyAccount : list) {
            if (moneyAccount.getSavings() != null) {
                point = point.add(moneyAccount.getSavings());
            }
            if (moneyAccount.getDebt() != null) {
                point = point.subtract(moneyAccount.getDebt());
            }
            // 获取最新的 updatedAt
            if (moneyAccount.getUpdatedAt() != null) {
                if (latestUpdatedAt == null || moneyAccount.getUpdatedAt().isAfter(latestUpdatedAt)) {
                    latestUpdatedAt = moneyAccount.getUpdatedAt();
                }
            }
        }
        logger.info("moneyPoint:{},updatedAt:{}", point, latestUpdatedAt);
        dateStartS = latestUpdatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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

    @GetMapping("/allInfo")
    public ServerResponseVO<?> getAllInfo(@Validated MoneyDto moneyDto) {
        // 负债、资产、净资产【金钱】、物品资产【非金钱】
        String dateStartS = null;
        BigDecimal point = BigDecimal.ZERO;
        // 查询固定时间的资产
//        String moneyPoint = RedisPoolUtil.get("money_point");
//        if (StringUtils.isNotEmpty(moneyPoint)) {
//            String[] split = moneyPoint.split(",");
//            dateStartS = split[0];
//            if (split.length >= 2) {
//                point = new BigDecimal(split[1]);
//            }
//        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", 1);
        List<MoneyAccount> list = moneyAccountService.list(queryWrapper);
        LocalDateTime latestUpdatedAt = null;
        for (MoneyAccount moneyAccount : list) {
            if (moneyAccount.getSavings() != null) {
                point = point.add(moneyAccount.getSavings());
            }
            if (moneyAccount.getDebt() != null) {
                point = point.subtract(moneyAccount.getDebt());
            }
            // 获取最新的 updatedAt
            if (moneyAccount.getUpdatedAt() != null) {
                if (latestUpdatedAt == null || moneyAccount.getUpdatedAt().isAfter(latestUpdatedAt)) {
                    latestUpdatedAt = moneyAccount.getUpdatedAt();
                }
            }
        }
        logger.info("moneyPoint:{},updatedAt:{}", point, latestUpdatedAt);
        dateStartS = latestUpdatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        BigDecimal payedMoney = moneyMapper.getPaiedMoneyCount(dateStartS, moneyDto.getDateEndS());
        if (payedMoney == null) payedMoney = BigDecimal.ZERO;
        BigDecimal willPayMoney = moneyMapper.getSubMoneyCount(ObjectUtils.isEmpty(moneyDto.getId()) ? null : moneyDto.getId().toString(), DateUtils.getTime(), moneyDto.getDateEndS());
        BigDecimal result = point.subtract(payedMoney).subtract(willPayMoney);
        if (moneyDto.getId() == null || moneyDto.getId().equals(2)) {
            // 提前还款
            if (LocalDateTime.now().isBefore(LocalDateTime.parse("2026-11-30T08:59:41"))) {
                result = result.add(new BigDecimal("19212"));
                willPayMoney = willPayMoney.subtract(new BigDecimal("19212"));
            }
        }
        List<Assets> assetsList = assetsService.getMyAssetsList();
        BigDecimal realAssets = assetsList.stream().map(Assets::getPrice).reduce(BigDecimal::add).orElse(new BigDecimal("0"));
        Map<String, Object> res = new HashMap<>();
        res.put("willPayMoney", willPayMoney); // 待还
        res.put("assets", point.subtract(payedMoney)); // 资金
        res.put("netAssets", result); // 净资产
        res.put("realAssets", realAssets); // 物品资产
        return ServerResponseVO.success(res);
    }
}
