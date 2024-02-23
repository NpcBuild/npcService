package com.npc.common.file.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author NPC
 * @description 文件详情
 * @create 2023/3/5 17:21
 */
@Data
@NoArgsConstructor  //无参构造
public class FileInfor {
    private String name;
    private String img;
    private long size;
    private Date createTime;
    private Date updateTime;

    public FileInfor(String name,long size,Date createTime,Date updateTime) {
        this.name = name;
        this.size = size;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    public FileInfor(String name,String img,long size,Date createTime,Date updateTime) {
        this.name = name;
        this.img = img;
        this.size = size;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
