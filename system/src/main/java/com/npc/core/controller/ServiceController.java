package com.npc.core.controller;

import com.alipay.api.internal.util.file.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NPC
 * @description
 * @create 2023/3/26 23:59
 */
@CrossOrigin
@RestController
public class ServiceController {
    @Autowired
    private ApplicationContext applicationContext;

    private Map<Integer, Process> processMap = new HashMap<>();
    @GetMapping("/start")
    public String start() throws IOException, InterruptedException {
//        getJavaV();
//        getEnvironment();
        checkPhysicAddress();
        return startService();
    }
    @GetMapping("/stop")
    public String stop(@RequestParam int processId) {
        if (this.processMap.containsKey(processId)) {
            // 获取要停止的进程的Process对象
            Process process = this.processMap.get(processId);
            // 停止进程
            process.destroy();

            // 从Map中移除Process对象
            this.processMap.remove(processId);
            // 返回停止信息
            return "Stopped process " + processId;
        } else {
            // 返回错误信息
            return "Process not found";
        }
    }

    /**
     * 获取java版本
     * @throws IOException
     */
    private void getJavaV() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("java","-version"); // java -version
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
//        long pid = process.pid();// java9 方法
        String result = IOUtils.toString(process.getInputStream()); // 一次性获取运行结果
        int exitCode = process.waitFor(); // 等到运行结束
//        System.out.println("pid:" + pid);
        System.out.println("result:" + result);
        System.out.println("exitCode:" + exitCode);
    }

    /**
     * 环境变量
     */
    private void getEnvironment() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Map<String, String> environment = processBuilder.environment();
        environment.forEach((k,v) -> System.out.println(k + ":" + v)); // 打印当前所有的环境变量
//        processBuilder.environment().put("my_website","www.yf.com"); // 添加一个环境变量
    }
    private String startService() throws InterruptedException, IOException {
        // 获取另一个SpringBoot项目的启动类路径
//        String jarPath = path;
        String classPath = "D:\\Code\\GitHub\\Java\\BilibiliLiveRecorder\\target\\classes";

        /**
         * 每个 ProcessBuilder 实例管理一个进程属性集。它的start() 方法利用这些属性创建一个新的 Process 实例。start() 方法可以从同一实例重复调用，以利用相同的或相关的属性创建新的子进程。
         */
        // 创建ProcessBuilder对象，设置Java命令和启动参数
        ProcessBuilder pb = new ProcessBuilder("java","-classpath",classPath,"nicelee.bilibili.Main","debug=false&check=true&liver=bili&id=697773");
        // 设置工作目录为项目所在的目录
        pb.directory(new File("D:\\Code\\GitHub\\Java\\BilibiliLiveRecorder\\src\\main\\java\\nicelee\\bilibili"));

        // 启动新进程
        Process process = pb.start();

        // 读取进程的输出流
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = reader2.readLine()) != null) {
            System.out.println(line);
            // 如果输出包含指定的文本，则说明进程已经启动
            if (line.contains("Started Application")) {
                System.out.println("Application started.");
                break;
            }
        }
        // 检查进程是否已经退出
        if (process.isAlive()) {
            // 等待新进程完成
            int exitCode = process.waitFor();

            // 打印进程的退出码
            System.out.println("Exit code: " + exitCode);
        } else {
            // 进程已经退出
            System.err.println("Failed to start the application.");
        }

        // 等待新进程完成
        int exitCode = process.waitFor();

        // 获取应用程序的状态端点
//        HealthEndpoint healthEndpoint = applicationContext.getBean(HealthEndpoint.class);
//        Health health = healthEndpoint.health();

        // 将Process对象与进程ID一起存储在Map中
        int processId = process.hashCode();
        this.processMap.put(processId, process);

        // 返回启动信息和应用程序状态
//        return "Started " + jarPath + ", status: " + health.getStatus().toString();
        return "Started " + classPath ;
    }


    /**
     * 查看ip地址【Windows系统下】
     */
    private void checkPhysicAddress() {
        ProcessBuilder processBuilder = new ProcessBuilder("ipconfig", "/all");
        try {
            Process process = processBuilder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.indexOf("IPv4") != -1) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
