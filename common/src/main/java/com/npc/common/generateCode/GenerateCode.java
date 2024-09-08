package com.npc.common.generateCode;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.*;

/**
 * @Author wow
 * @createTime 2022/10/15 20:38
 * @descripttion 基于mybatis-plus生成代码
 */

public class GenerateCode {
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/yf?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT","root","root");

    /**
     * 交互式生成
     */
    public static void generate() {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称？")).fileOverride())
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("create_time", FieldFill.INSERT)
                        ).build())
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }
    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }


    /**
     * 快速生成
     */
    public static void fastGenerate() {
        // 获取项目路径 这里只会获取到idea工作目录 例 C:\private-idea-workspace\5-21
//        String projectPath = System.getProperty("user.dir");
//        String filePath = "\\common\\src\\main\\java\\com\\npc\\common\\modular";
        String projectPath = "D:\\Code";
        String filePath = "\\Generate";
        String moduleName = "todo";
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/yf?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT", "root", "root")
                .globalConfig(builder -> {
                    builder.author("yangfei") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + filePath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.npc.common.modular") // 设置父包名
//                    builder.parent("com.npc.auth.admin") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + filePath + "\\" + moduleName + "\\mapper\\mapping")); // 设置mapperXml生成路径
                })
                .templateConfig(builder -> {
                    builder.entity("templates/entity.java.vm");
                    builder.service("templates/service.java.vm");
                    builder.serviceImpl("templates/serviceImpl.java.vm");
                    builder.mapper("templates/mapper.java.vm");
                    builder.controller("templates/controller.java.vm");
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.controllerBuilder()
                                    .enableRestStyle();
                    builder.entityBuilder().enableLombok(); //使用lombok
                    builder.controllerBuilder().enableHyphenStyle().enableRestStyle(); // 开启RestController
                    builder.addInclude("t_todo") // 设置需要生成的表名
                            .addTablePrefix("c_","t_"); // 设置过滤表前缀
                })
                // 自定义配置：用来生成前端部分的Vue页面
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = new HashMap<>();
                    // 根据指定的模板，生成对应的文件
                    customFile.put("a.vue", "/templates/a.vue.vm");
                    customFile.put(moduleName + "Dao.java", "/templates/entityDao.java.vm");
                    consumer.customFile(customFile);
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
