package com.npc.common.scheduled.job;

import com.npc.common.modular.corpus.entity.Corpus;
import com.npc.common.modular.corpus.service.ICorpusService;
import com.npc.core.net.RestTemplateBuilder;
import com.npc.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NPC
 * @description 说话的艺术
 * @create 2024/4/20 14:15
 */
@Component
public class SpeakArt {
    @Resource
    public ICorpusService corpusService;

    /**
     * 官网：https://lovelive.tools/
     * Url 变量说明：serializationType：返回内容的格式，可以选择 Text 或 Json 格式。Text 格式会根据 count 的值以换行为分隔返回内容，Json 格式会在 returnObj 中包含返回一个 Array<string>。
     * Url 变量说明：count：要获取的数量。如果不在 Url 中使用这个参数 ，将默认获取 1 个句子。
     * Query 变量说明：genderType：内容的身份分类，可以选择 M 或 F 。M 为 “渣男”，F 为 “绿茶”。
     * @return
     */
    public boolean getCorpus() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
//        String url = "https://api.lovelive.tools/api/SweetNothings/10/Serialization/Json?genderType=M";
        String url = "https://api.lovelive.tools/api/SweetNothings/10/Serialization/Json";
        Map var = new HashMap();
        var.put("genderType", "F");
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class, var);
        List<String> returnObj = (List)responseEntity.getBody().get("returnObj");
        List batch = new ArrayList();
        for (String s : returnObj) {
            Corpus corpus = new Corpus();
//            corpus.setFlag();
            corpus.setContent(s);
            corpus.setTag("10");
            corpus.setCreTime(LocalDateTime.now());
            batch.add(corpus);
        }
        corpusService.saveBatch(batch);
        return true;
    }

    public void main(String[] args) {
        getCorpus();
    }
}
