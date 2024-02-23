package com.npc.common.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.npc.common.excel.listener.ExcelListener;
import com.npc.common.modular.user.entity.User;
import org.apache.poi.ss.usermodel.TableStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <功能简要> <br>
 * <easyExcel导入excel>
 *
 * @Author wow
 * @createTime 2022/9/15 20:31
 * @since 1.0.0
 */
@CrossOrigin
@RestController
@RequestMapping("")
public class TImport {
    @GetMapping("/import")
    public void importUser(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), User.class, new ExcelListener()).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/export")
    public void exportUser(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "user";
        response.setHeader("Content-disposition","attachment:filename="+fileName+".xlsx");
        List<User> users = new ArrayList<>();
        //查询语句
        User s = new User();
        s.setAccount("2");
        s.setUserName("w");
        users.add(s);
        try {
            EasyExcel.write(response.getOutputStream(), User.class).sheet().doWrite(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
