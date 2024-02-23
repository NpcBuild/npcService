package com.npc.common.monitor.server;

import com.npc.common.monitor.server.Server;
import com.npc.core.ServerResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wow
 * @createTime 2022/9/25 18:56
 * @descripttion 服务器监控
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {
    @GetMapping("")
    public ServerResponseVO getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return ServerResponseVO.success(server);
    }
}
