package com.npc.common.modular.career.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.career.dto.SocialProblemDto;
import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.npc.common.modular.career.service.ISocialProblemService;
import com.npc.common.modular.career.entity.SocialProblem;

import java.util.Arrays;

/**
 * <p>
 * 社会问题池 前端控制器
 * </p>
 *
 * @author yangfei
 * @since 2025-12-17
 */
@RestController
@RequestMapping("/socialProblem")
// @Api(value = "/socialProblem", description = "社会问题池 相关接口")
public class SocialProblemController {
    
    private static final Logger logger = LoggerFactory.getLogger(SocialProblemController.class);

    @Autowired
    public ISocialProblemService socialProblemService;


    /**
     * 保存、修改 【区分id即可】
     * @param socialProblem 传递的实体
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    // @ApiOperation(response = ServerResponseVO.class, value = "社会问题池 添加 修改接口, 填入Id为更新, 不填Id为新增")
    public ServerResponseVO<?> save(@RequestBody @Validated SocialProblem socialProblem) {
        try {
            Boolean obj = socialProblemService.saveOrUpdate(socialProblem);
            return ServerResponseVO.success(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.SAVE_FAILED);
        }
    }


    /**
     * 通过Id 删除对象
     * @param id 要删除的实体
     * @return ServerResponseVO转换结果
     */
    @GetMapping("deleteSocialProblemById")
    // @ApiOperation(response = ServerResponseVO.class, value = "通过id 删除对象")
    public ServerResponseVO<?> delete(@RequestParam("id") Integer id) {

        try {
            Boolean socialProblem =socialProblemService.removeById(id);
            return ServerResponseVO.success(socialProblem);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.DELETE_FAILED);
        }
    }

    /**
     * 批量删除 社会问题池
     * @param ids
     * @return
     */
    @GetMapping("batchDeleteSocialProblemByIdList")
    @Transactional(rollbackFor = Exception.class)
    // @ApiOperation(response = ServerResponseVO.class, value = "批量删除 社会问题池")
    public ServerResponseVO<?> batchDeleteSocialProblemByIdList(@RequestParam("ids") Integer[] ids) {
        socialProblemService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseVO.success();
    }


    /**
     * 通过Id 获取对象
     * @param id
     * @return ServerResponseVO转换结果
     */
    @RequestMapping(value = "/getSocialProblemById", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "通过Id 获取 社会问题池 ")
    public ServerResponseVO<?> get(@RequestParam("id") Long id) {
        SocialProblem socialProblem =socialProblemService.getById(id);
        return ServerResponseVO.success(socialProblem);
    }


    /**
     * 分页查询数据：
     * @param socialProblemDto 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/getSocialProblemList", method = RequestMethod.GET)
    // @ApiOperation(response = ServerResponseVO.class, value = "社会问题池 分页查询数据")
    public ServerResponseVO<?> getSocialProblemList(@Validated SocialProblemDto socialProblemDto) {
        Page page = new Page(socialProblemDto.getPageNum(), socialProblemDto.getPageSize());
        QueryWrapper<SocialProblem> queryWrapper = new QueryWrapper(socialProblemDto);
        Page<SocialProblem> pages = socialProblemService.page(page, queryWrapper);
        return ServerResponseVO.success(pages);
    }
}
