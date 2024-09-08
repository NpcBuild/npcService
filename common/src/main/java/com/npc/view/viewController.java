package com.npc.view;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.modular.dailySchedule.entity.DailySchedule;
import com.npc.common.modular.dailySchedule.mapper.DailyScheduleMapper;
import com.npc.common.todo.entity.Todo;
import com.npc.common.todo.mapper.TodoMapper;
import com.npc.common.todo.service.ITodoService;
import com.npc.common.todo.vo.TodoVO;
import com.npc.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.ITemplateEngine;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2023/11/24 21:42
 */
@Slf4j
@Controller
public class viewController {
    @Autowired
    private TodoMapper todoMapper;
    @Resource
    private DailyScheduleMapper scheduleMapper;
    @Autowired
    private ITemplateEngine templateEngine;
    @Resource
    private ITodoService todoService;

    @GetMapping("/todoView")
    public String todoView(Model model) {
        DailySchedule schedule = new DailySchedule();
        Todo habit = new Todo();
        habit.setType("2");
        QueryWrapper queryWrapper = new QueryWrapper(schedule);
        QueryWrapper queryWrapper2 = new QueryWrapper(habit);
//        List<Todo> todoList = todoMapper.selectList(queryWrapper);
        List<DailySchedule> scheduleList = scheduleMapper.selectList(queryWrapper);
        List<Todo> habits = todoMapper.selectList(queryWrapper2);
        List<TodoVO> habitList = todoService.getMoreInfo(habits, DateUtils.getDate());
        model.addAttribute("scheduleList",scheduleList);
        model.addAttribute("habitList",habitList);
        model.addAttribute("nowTime", DateUtils.dateTimeNow(DateUtils.VIEW));
        model.addAttribute("pageTitle","好好生活");
//        model.addAttribute("taskList",habitList);
        return "todoV";
    }
//    @GetMapping("/downTodoView")
//    public byte[] downloadPageAsImage(Model model) throws IOException {
//        Context context = new Context();
//        String renderedHtml = templateEngine.process("todoV",context);
//        BufferedImage bufferedImage = convertImageToImage(renderedHtml);
//        return convertImagetoByteArray(bufferedImage);
//    }
//
//    private BufferedImage convertImageToImage(String html) {
//
//    }
//    private
}
