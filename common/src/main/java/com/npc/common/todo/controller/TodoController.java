package com.npc.common.todo.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mchange.lang.IntegerUtils;
import com.npc.common.modular.points.service.IPointsService;
import com.npc.common.modular.quartzJob.entity.QuartzJob;
import com.npc.common.modular.quartzJob.service.IQuartzJobService;
import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import com.npc.common.modular.setting.mapper.SettingMapper;
import com.npc.common.modular.setting.service.ISettingService;
import com.npc.common.modular.todoCompleted.entity.TodoCompleted;
import com.npc.common.modular.todoCompleted.mapper.TodoCompletedMapper;
import com.npc.common.modular.todoCompleted.service.ITodoCompletedService;
import com.npc.common.quartz.Quartz;
import com.npc.common.quartz.job.ToDoNoticeJob;
import com.npc.common.todo.entity.Todo;
import com.npc.common.todo.service.ITodoService;
import com.npc.common.todo.vo.TodoVO;
import com.npc.common.todo.vo.TodoViewVO;
import com.npc.core.ServerResponseVO;
import com.npc.core.net.query.Workday;
import com.npc.redis.utils.RedisUtil;
import com.npc.utils.DateUtils;
import com.npc.core.utils.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author NPC
 * @description 待办任务
 * @create 2023/9/10 8:15
 */
@Tag(name = "TodoController", description = "待办任务相关接口")
@RestController
@RequestMapping("/todo")
public class TodoController {
    @Resource
    private Quartz quartz;
    @Resource
    private IQuartzJobService quartzJobService;
    @Resource
    public ITodoService todoService;
    @Resource
    private ITodoCompletedService completedService;
    @Resource
    private TodoCompletedMapper completedMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SettingMapper settingMapper;
    @Autowired
    private IPointsService pointsService;

    @Operation(summary = "获取定时任务列表", description = "根据查询条件获取定时任务列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取定时任务列表",
                    content = @Content(schema = @Schema(implementation = IPage.class)))
    })
    @GetMapping("/list")
    public IPage<QuartzJob> list(@Parameter(description = "查询条件") TodoVO vo) {
        return quartz.list(vo);
    }

    /**
     * 分页查询数据：
     * @param todoVO 查询对象
     * @return PageList 分页对象
     */
    @Operation(summary = "分页查询待办任务列表", description = "根据查询条件分页获取待办任务列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取待办任务列表",
                    content = @Content(schema = @Schema(implementation = ServerResponseVO.class)))
    })
    @GetMapping("/getTodoList")
    public ServerResponseVO<?> getTodoList(@Validated @Parameter(description = "查询对象") TodoVO todoVO) {
        IPage<TodoViewVO> page = todoService.getListPage(todoVO);
        if (StringUtils.isNotEmpty(todoVO.getDate())) {
            List<TodoViewVO> records = page.getRecords();
            List<Integer> todoIds = new ArrayList<>();
            records.forEach((item) -> {
                todoIds.add(item.getId());
            });
            List<TodoCompleted> completedList = completedMapper.getCompletedListIn(todoVO.getDate(), todoIds);

            // 结果处理
            Map<Integer,String> map = new HashMap<>();
            for (TodoCompleted todoCompleted : completedList) {
                map.put(todoCompleted.getTodoId(), todoCompleted.getStatus());
            }
            for (TodoViewVO todo : records) {
                String oldV = todo.getTodoName();
                if (map.containsKey(todo.getId()) && "2".equals(map.get(todo.getId()))) {
                    todo.setCompletedStatus("1");
                } else {
                    todo.setCompletedStatus("0");
                }
            }
        }
        return ServerResponseVO.success(page);
    }

    /**
     * 获取指定日期的已完成任务
     * @param date 查询的日期
     * @return
     */
    @Operation(summary = "获取指定日期的已完成任务", description = "根据日期获取已完成任务的 ID 列表")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取已完成任务列表",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("getCompletedList")
    public String getCompletedList(@Parameter(description = "查询的日期") String date) {
        List<TodoCompleted> completedList = completedMapper.getCompletedList(date);
        String[] list = new String[completedList.size()];
        for (int i = 0; i < completedList.size(); i++) {
            list[i] = completedList.get(i).getTodoId().toString();
        }
        return String.join(",",list);
    }
    @GetMapping("getCompletedListIn")
    public ServerResponseVO<?> getCompletedListIn(@Parameter(description = "查询的日期") String date, @Parameter(description = "ids") String ids) {
        String[] split = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String string : split) {
            idList.add(Integer.parseInt(string));
        }
        List<TodoCompleted> completedList = completedMapper.getCompletedListIn(date, idList);
        String[] list = new String[completedList.size()];
        for (int i = 0; i < completedList.size(); i++) {
            list[i] = completedList.get(i).getTodoId().toString();
        }
        return ServerResponseVO.success(list);
    }

    /**
     * 获取指定日历区间的任务列表
     * @param todoVO 查询的参数
     * @return
     */
    @Operation(summary = "获取指定日历区间的任务列表", description = "根据日期区间获取任务列表及完成情况")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取任务列表",
                    content = @Content(schema = @Schema(implementation = ServerResponseVO.class)))
    })
    @GetMapping("getTodoCalendar")
    public ServerResponseVO<?> getTodoCalendar(@Validated @Parameter(description = "查询的参数") TodoVO todoVO) {
        String startDate = todoVO.getStartDate();
        String endDate = todoVO.getEndDate();
        List<Todo> todos = todoService.getList(todoVO);
        List<TodoCompleted> completedList = completedMapper.getCompletedListSE(startDate,endDate);
        Map<String, Map> res = new HashMap<>();
        List res2 = new ArrayList();
        LocalDate nextDay = LocalDate.parse(startDate);
        LocalDate startDay = LocalDate.parse(startDate);
        LocalDate endDay = LocalDate.parse(endDate);
        while (nextDay.isBefore(endDay) || nextDay.equals(endDay)) {
            List oneDay = new ArrayList();
            int needNum = 0;
            int finishedNum = 0;
            for (Todo todo : todos) {
                Todo resTodo = new Todo();
                BeanUtil.copyProperties(todo,resTodo);
                // 获取必须完成的任务及完成情况
                if (!ObjectUtils.isEmpty(resTodo.getRemind()) && resTodo.getRemind().equals(1)) {
                    ++needNum;
                }
                if ((startDay.isBefore(nextDay) || startDay.equals(nextDay)) && (endDay.isAfter(nextDay) || endDay.equals(nextDay))) {
                    for (TodoCompleted completed : completedList) {
                        LocalDate finishDate = completed.getFinishTime().toLocalDate();
                        if (completed.getTodoId().equals(resTodo.getId()) && finishDate.equals(nextDay)) {
                            resTodo.setCompletedStatus("1");
                            ++finishedNum;
                        }
                    }
                    oneDay.add(resTodo);
                }
            }
            Map dayInfo = new HashMap();
            try {
                dayInfo.put("holiday",Workday.isHoliday(nextDay));
            } catch (Exception e) {
                e.printStackTrace();
            }
            dayInfo.put("list", oneDay);
            dayInfo.put("finished", finishRatio(dayInfo,needNum,finishedNum));
            res.put(nextDay.toString(),dayInfo);
            dayInfo.put("date", nextDay.toString());
            res2.add(dayInfo);
            nextDay = DateUtils.getNextDay(nextDay);
        }

        if ("time".equals(todoVO.getResType())) {
            return ServerResponseVO.success(res);
        }
        return ServerResponseVO.success(res2);
    }

    /**
     * 计算完成率
     * @param dayInfo
     * @param needNum
     * @param finishedNum
     * @return
     */
    @Operation(summary = "计算完成率", description = "根据任务信息计算完成率")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功计算完成率",
                    content = @Content(schema = @Schema(implementation = Boolean.class)))
    })
    private boolean finishRatio(@Parameter(description = "日期信息") Map dayInfo,
                                @Parameter(description = "需要完成的任务数") int needNum,
                                @Parameter(description = "已完成的任务数")int finishedNum) {
        if (needNum == 0) {
            return true;
        }
        return finishedNum/needNum > 0.5;
    }

    /**
     * 添加定时任务
     * @param jobVO
     * @param todoName
     * @return
     */
    @Operation(summary = "添加定时任务", description = "添加一个新的定时任务")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功添加定时任务",
                    content = @Content(schema = @Schema(implementation = ServerResponseVO.class)))
    })
    @PostMapping("/add")
    public ServerResponseVO addTodo(@RequestBody @Parameter(description = "定时任务信息") QuartzJobVO jobVO,
                                    @Parameter(description = "任务名称") String todoName) {
        Integer quartzId = null;
        if (!ObjectUtils.isEmpty(jobVO)) {
            quartzId = Integer.valueOf(quartzJobService.addJob(jobVO).getData().toString());
        }
        Todo todo = new Todo();
        todo.setTodoName(todoName);
        todo.setQuartzId(quartzId);
        todo.setStatus("1");
        todo.setStartTime(LocalDateTime.now());
        todoService.save(todo);
        return ServerResponseVO.success(todo);
    }

    /**
     * 删除定时任务
     * @param jobVO
     * @return
     */
    @Operation(summary = "删除定时任务", description = "删除指定的定时任务")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功删除定时任务",
                    content = @Content(schema = @Schema(implementation = ServerResponseVO.class)))
    })
    @PostMapping("/del")
    public ServerResponseVO delTodo(@RequestBody @Parameter(description = "定时任务信息") QuartzJobVO jobVO) {
        quartzJobService.delete(jobVO);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("quartz_id",jobVO.getId());
        todoService.remove(queryWrapper);
        return ServerResponseVO.success();
    }

    @Operation(summary = "编辑任务", description = "编辑任务信息，根据任务 ID 决定是更新还是添加")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功编辑任务",
                    content = @Content(schema = @Schema(implementation = ServerResponseVO.class)))
    })
    @GetMapping("/edit")
    public ServerResponseVO add(@Parameter(description = "任务信息") TodoVO vo) {
        ServerResponseVO rvo;
        if (vo.getId() != null) {
            rvo = quartz.edit(vo);
        } else {
            rvo = quartz.add(vo);
        }
        return rvo;
    }

    /**
     * 完成（或取消完成）任务
     * @param todoVO
     * @return
     */
    @Operation(summary = "完成（或取消完成）任务", description = "根据任务 ID 和日期完成或取消完成任务")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功处理任务状态",
                    content = @Content(schema = @Schema(implementation = Boolean.class)))
    })
    @PostMapping("done")
    public Boolean done(@RequestBody @Parameter(description = "任务信息") TodoVO todoVO) {
        if (StringUtils.isNotEmpty(todoVO.getIds())) {
            String[] split = todoVO.getIds().split(",");
            for (String id : split) {

                QueryWrapper<TodoCompleted> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("todo_id", id);
                queryWrapper.likeRight("finish_time",todoVO.getDate());
                boolean removed = completedService.remove(queryWrapper);

                Todo byId = todoService.getById(id);
                if (!removed) {
                    TodoCompleted completed = new TodoCompleted();
                    completed.setTodoId(Integer.valueOf(id));
                    completed.setStatus("2");
                    completed.setFinishTime(LocalDateTime.now());
                    completed.setScore(byId.getDoneScore());
                    completedService.save(completed);
                    System.out.println("完成了任务：" + byId.getTodoName());

                    // 更新积分
                    if (byId.getDoneScore() != null) {
                        boolean scored = settingMapper.updateScore("1", byId.getDoneScore());
                        System.out.println((scored ? "更新成功" : "更新失败") + "获得了任务积分：" + byId.getDoneScore());
                    }
                } else {
                    System.out.println("取消了任务：" + byId.getTodoName());
                }
                if (!ObjectUtils.isEmpty(byId.getDoneScore())) {
                    pointsService.changePoints(1L, byId.getDoneScore(), "任务");
                }
            }
        }
        return true;
    }

    /**
     * 获取累计未完成任务数
     * @param subtract 减去任务数
     * @return
     */
    @Operation(summary = "获取累计未完成任务数", description = "获取累计未完成任务数，并可减去指定数量")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取未完成任务数",
                    content = @Content(schema = @Schema(implementation = ServerResponseVO.class)))
    })
    @GetMapping("getUnDone")
    public ServerResponseVO<?> getUnDone(@Parameter(description = "减去任务数") int subtract) {
        String num = redisUtil.get(ToDoNoticeJob.UN_DONE);
        if (!ObjectUtils.isEmpty(subtract)) {
            String res = String.valueOf((Integer.parseInt(num) - subtract));
            redisUtil.set(ToDoNoticeJob.UN_DONE, res);
            num = res;
        }
        return ServerResponseVO.success(num);
    }

    /**
     * 切换任务状态
     */
    @PostMapping("changeStatus")
    public ServerResponseVO<?> changeStatus(@RequestBody TodoVO todoVO) {
        UpdateWrapper<Todo> updateWrapper = new UpdateWrapper<Todo>();
        updateWrapper.eq("id", todoVO.getId());
        updateWrapper.set("status", todoVO.getStatus());
        boolean update = todoService.update(updateWrapper);
        return ServerResponseVO.success(todoService.getById(todoVO.getId()));
    }

    /**
     * 删除任务
     */
    @PostMapping("delete")
    public ServerResponseVO<?> delete(@RequestBody TodoVO todoVO) {
        boolean b = todoService.removeById(todoVO.getId());
        return ServerResponseVO.success(b);
    }
}
