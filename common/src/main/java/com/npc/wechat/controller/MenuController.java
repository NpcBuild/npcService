package com.npc.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/wx/menu")
public class MenuController {

    @Resource
    private WxMpService wxMpService;

    @PostMapping("/create")
    public String createMenu() {
        WxMenu menu = new WxMenu();

        WxMenuButton btn1 = new WxMenuButton();
        btn1.setType("click");
        btn1.setName("点击我");
        btn1.setKey("CLICK_HELLO");

        WxMenuButton btn2 = new WxMenuButton();
        btn2.setType("view");
        btn2.setName("访问官网");
        btn2.setUrl("https://example.com");

        menu.setButtons(Arrays.asList(btn1, btn2));

        try {
            wxMpService.getMenuService().menuCreate(menu);
            return "菜单创建成功";
        } catch (WxErrorException e) {
            log.error("创建菜单失败", e);
            return "菜单创建失败: " + e.getMessage();
        }
    }

    @GetMapping("/get")
    public WxMpMenu getMenu() throws WxErrorException {
        return wxMpService.getMenuService().menuGet();
    }

    @DeleteMapping("/delete")
    public String deleteMenu() throws WxErrorException {
        wxMpService.getMenuService().menuDelete();
        return "菜单已删除";
    }
}
