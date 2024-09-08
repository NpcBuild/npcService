package com.npc.common.picBed;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.net.www.content.image.png;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author NPC
 * @description
 * @create 2024/5/9 11:55
 */
@Service
public class ImagesService {
    private static String[] arrImg = {".jpg",".png"};
    @Value("${qiniuyun.upload.accessKey}")
    private String accessKey;
    @Value("${qiniuyun.upload.secretKey}")
    private String secretKey;
    @Value("${qiniuyun.upload.bucket}")
    private String bucket;
    @Value("${qiniuyun.upload.mkdir}")
    private String mkdir;
    @Value("${qiniuyun.upload.domain}")
    private String qiNiuDomain;

    /**
     * 上传七牛云
     * @param file 文件
     * @return 文件链接
     */
    public String imageUploadGetUrl(MultipartFile file) {
        String url = null;
        // 获取文件的源文件名
        String originalFilename = file.getOriginalFilename();
        // 截取后缀
        String postFix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 生成新的文件名
        String fileNewName = UUID.randomUUID().toString().replace("-", "") + postFix;
        //  判断上传的文件后缀是否符合要求
        if (!Arrays.asList(arrImg).contains(postFix)) {
            throw new RuntimeException("上传失败,类型暂不支持");
//            result.setCode(-1).setMsg("上传失败,类型暂不支持");
//            return null;
        }
        // 创建七牛云Auth
        Auth auth = Auth.create(accessKey, secretKey);
        // 创建上传对象，Region.region1()表示华北
        Configuration cfg = new Configuration(Region.region1());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        cfg.useHttpsDomains = false;
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            // 调用put方法上传
            String token = auth.uploadToken(bucket, null, 3600, new StringMap().put("insertOnly", 1));
            if(!StringUtils.hasLength(token)) {
                throw new RuntimeException("上传失败");
//                result.setCode(-1).setMsg("上传失败");
//                return null;
            }
            String fullName = mkdir + fileNewName;
            // 解析出文件的key
            Response res = uploadManager.put(file.getBytes(), fullName, token);
            DefaultPutRet putRet = JSON.parseObject(res.bodyString(), DefaultPutRet.class);
            if (res.isOK()) {
//                result.setData(putRet.key);
                // 拼接图片访问路径
                url = qiNiuDomain + fullName;
            } else {
                throw new RuntimeException("上传失败");
//                result.setCode(-1).setMsg("上传失败");
            }
        } catch (Exception e) {
//            log.error("", e);
            throw new RuntimeException("上传失败");
//            result.setCode(-1).setMsg("上传失败");
        }
        return url;
    }

    public void removeCloudImage(String key) {
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            // 创建上传对象
            Configuration cfg = new Configuration(Region.region1());
            BucketManager bucketManager = new BucketManager(auth, cfg);
            Response res = bucketManager.delete(bucket, key);
            if (!res.isOK()) {
                throw new RuntimeException("删除失败");
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}
