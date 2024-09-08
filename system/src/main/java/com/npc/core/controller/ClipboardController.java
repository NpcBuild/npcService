package com.npc.core.controller;

import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NPC
 * @description 通过接口把文本复制到系统剪切板中
 * todo java.lang.IllegalStateException: Toolkit not initialized
 * @create 2024/7/28 9:55
 */
@CrossOrigin
@RestController
public class ClipboardController {
    @PostMapping("/copyToClipboard")
    public String copyToClipboard(@RequestBody String text) {
        Platform.runLater(() -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            clipboard.setContent(content);
        });
        return "复制到剪切板成功！";
    }
}
