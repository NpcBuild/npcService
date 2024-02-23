package com.npc.common.modular.user.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wow
 * @createTime 2022/9/15 21:15
 * @descripttion 用户表
 */
@Data
@TableName("t_user")
public class User implements Serializable {
    @ExcelProperty("主键")  //指定属性对应的Excel中列的名称
    @TableField("id")
    private Integer id;
    @ExcelProperty("用户名")
    @TableField("userName")
    private String userName;
    @ExcelProperty("密码")
    @TableField("userPwd")
    private String userPwd;
    @ExcelProperty("账号名")
    @TableField("account")
    private String account;
    @TableLogic
    @ExcelProperty("逻辑删除")
    @TableField("flag")
    private Integer flag;
}
