package com.npc.common.todo.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.npc.common.modular.quartzJob.entity.QuartzJob;
import com.npc.common.modular.quartzJob.service.IQuartzJobService;
import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import com.npc.common.modular.todoCompleted.entity.TodoCompleted;
import com.npc.common.modular.todoCompleted.mapper.TodoCompletedMapper;
import com.npc.common.modular.todoCompleted.service.ITodoCompletedService;
import com.npc.common.quartz.Quartz;
import com.npc.common.quartz.job.ToDoNoticeJob;
import com.npc.common.todo.entity.Todo;
import com.npc.common.todo.service.ITodoService;
import com.npc.common.todo.vo.TodoVO;
import com.npc.core.ServerResponseVO;
import com.npc.redis.utils.RedisUtil;
import com.npc.utils.DateUtils;
import com.npc.core.utils.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NPC
 * @description
 * @create 2023/9/10 8:15
 */
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

    @GetMapping("/list")
    public IPage<QuartzJob> list(TodoVO vo) {
        return quartz.list(vo);
    }

    /**
     * 分页查询数据：
     * @param todoVO 查询对象
     * @return PageList 分页对象
     */
    @GetMapping("/getTodoList")
    public ServerResponseVO getTodoList(@Validated TodoVO todoVO) {
        IPage<Todo> page = todoService.getListPage(todoVO);
        if (StringUtils.isNotEmpty(todoVO.getDate())) {
            List<Todo> records = page.getRecords();
            List<Integer> todoIds = new ArrayList<>();
            records.forEach((item) -> {
                todoIds.add(item.getId());
            });
            TodoCompleted completed = new TodoCompleted();
            QueryWrapper queryWrapper = new QueryWrapper(completed);
            queryWrapper.likeRight("finish_time",todoVO.getDate());
            queryWrapper.in("todo_id",todoIds);
            List<TodoCompleted> completedList = completedService.list(queryWrapper);

            // 结果处理
            Map<Integer,String> map = new HashMap<>();
            for (TodoCompleted todoCompleted : completedList) {
                map.put(todoCompleted.getTodoId(), todoCompleted.getStatus());
            }
            for (Todo todo : records) {
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
    @GetMapping("getCompletedList")
    public String getCompletedList(String date) {
        List<TodoCompleted> completedList = completedMapper.getCompletedList(date);
        String[] list = new String[completedList.size()];
        for (int i = 0; i < completedList.size(); i++) {
            list[i] = completedList.get(i).getTodoId().toString();
        }
        return String.join(",",list);
    }

    /**
     * 获取指定日历区间的任务列表
     * @param todoVO 查询的参数
     * @return
     */
    @GetMapping("getTodoCalendar")
    public ServerResponseVO getTodoCalendar(@Validated TodoVO todoVO) {
        String startDate = todoVO.getStartDate();
        String endDate = todoVO.getEndDate();
        List<Todo> todos = todoService.getList(todoVO);
        List<TodoCompleted> completedList = completedMapper.getCompletedListSE(startDate,endDate);
        Map<String, List> res = new HashMap<>();
        LocalDate nextDay = LocalDate.parse(startDate);
        LocalDate startDay = LocalDate.parse(startDate);
        LocalDate endDay = LocalDate.parse(endDate);
        while (nextDay.isBefore(endDay) || nextDay.equals(endDay)) {
            List oneDay = new ArrayList();
            for (Todo todo : todos) {
                Todo resTodo = new Todo();
                BeanUtil.copyProperties(todo,resTodo);
                if ((startDay.isBefore(nextDay) || startDay.equals(nextDay)) && (endDay.isAfter(nextDay) || endDay.equals(nextDay))) {
                    for (TodoCompleted completed : completedList) {
                        LocalDate finishDate = completed.getFinishTime().toLocalDate();
                        if (completed.getTodoId().equals(resTodo.getId()) && finishDate.equals(nextDay)) {
                            resTodo.setCompletedStatus("1");
                        }
                    }
                    oneDay.add(resTodo);
                }
            }
            res.put(nextDay.toString(),oneDay);
            nextDay = DateUtils.getNextDay(nextDay);
        }

        return ServerResponseVO.success(res);
    }

    /**
     * 添加定时任务
     * @param jobVO
     * @param todoName
     * @return
     */
    @PostMapping("/add")
    public ServerResponseVO addTodo(@RequestBody QuartzJobVO jobVO,String todoName) {
        Integer quartzId = Integer.valueOf(quartzJobService.addJob(jobVO).getData().toString());
        Todo todo = new Todo();
        todo.setTodoName(todoName);
        todo.setQuartzId(quartzId);
        todo.setStatus("1");
        todo.setStartTime(LocalDateTime.now());
        todoService.save(todo);
        return ServerResponseVO.success();
    }

    /**
     * 删除定时任务
     * @param jobVO
     * @return
     */
    @PostMapping("/del")
    public ServerResponseVO delTodo(@RequestBody QuartzJobVO jobVO) {
        quartzJobService.delete(jobVO);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("quartz_id",jobVO.getId());
        todoService.remove(queryWrapper);
        return ServerResponseVO.success();
    }

    @GetMapping("/edit")
    public ServerResponseVO add(TodoVO vo) {
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
    @PostMapping("done")
    public Boolean done(@RequestBody TodoVO todoVO) {
        if (StringUtils.isNotEmpty(todoVO.getIds())) {
            String[] split = todoVO.getIds().split(",");
            for (String id : split) {

                QueryWrapper<TodoCompleted> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("todo_id", id);
                queryWrapper.likeRight("finish_time",todoVO.getDate());
                boolean removed = completedService.remove(queryWrapper);
                if (!removed) {
                    TodoCompleted completed = new TodoCompleted();
                    completed.setTodoId(Integer.valueOf(id));
                    completed.setStatus("2");
                    completed.setFinishTime(LocalDateTime.now());
                    completedService.save(completed);
                    System.out.println("完成了任务：" + id);
                } else {
                    System.out.println("取消了任务：" + id);
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
    @GetMapping("getUnDone")
    public ServerResponseVO<?> getUnDone(int subtract) {
        String num = redisUtil.get(ToDoNoticeJob.UN_DONE);
        if (!ObjectUtils.isEmpty(subtract)) {
            String res = String.valueOf((Integer.parseInt(num) - subtract));
            redisUtil.set(ToDoNoticeJob.UN_DONE, res);
            num = res;
        }
        return ServerResponseVO.success(num);
    }
}
