package com.npc.common.share;

import com.npc.common.modular.corpus.entity.Corpus;
import com.npc.common.modular.corpus.service.ICorpusService;
import com.npc.core.utils.HttpUtils;
import com.npc.core.utils.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author NPC
 * @description 分享
 * @create 2023/8/14 20:10
 */
@RestController
@RequestMapping("/share")
public class ShareController {
    @Resource
    private ICorpusService corpusService;
    @PostMapping("")
    public Boolean share(@RequestBody ShareVO vo) throws IOException {
        System.out.println(vo.getUrl());
        String[] possiblePaths = {
                "common/src/main/java/com/npc/common/share/url.txt",
                "/home/npc/Desktop/do/url.txt"
        };
        if (HttpUtils.includeURL(vo.getUrl())) {
            File file = null;
            for (String path : possiblePaths) {
                file = new File(path);
                if (file.exists()) {
                    break; // 找到存在的文件，跳出循环
                }
            }
            FileWriter writer = new FileWriter(file,true); //累加
            writer.write(vo.getUrl() + "\n");
            writer.close();
            System.out.println("数据写入成功！");
        } else {
            // 保存内容（纯文本）
            if (StringUtils.isNotEmpty(vo.getUrl())) {
                Corpus corpus = new Corpus();
                corpus.setContent(vo.getUrl());
                corpus.setTag("11"); // 字符串
                corpus.setCreTime(LocalDateTime.now());
                corpusService.save(corpus);
            }
        }
        return true;
    }
}
