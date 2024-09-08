package com.npc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.client.Elasticsearch.service.EsDocumentDemoService;
import com.npc.common.generateCode.GenerateCode;
import com.npc.common.modular.user.entity.User;
import com.npc.common.modular.user.service.UserService;
import com.npc.common.modular.user.service.impl.UserServiceImpl;
import com.npc.core.encrypt.RSA.RsaCryptUtil;
import com.npc.core.net.mulitDown.MulitDown;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Resource
    private EsDocumentDemoService esDocumentDemoService;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test (){
//        // double精度问题
//        Double a = -14d;
//        Double b = 6.022212d;
//        Double c = -20.022212d;
//        System.out.println(c.doubleValue());
//        System.out.println(c.toString());
//        System.out.println(a.doubleValue()-b.doubleValue()-c.doubleValue());
////        int d = new BigDecimal(a.toString()) - new BigDecimal(b.toString()) - new BigDecimal(c.toString());
//        BigDecimal aa = new BigDecimal(a.toString());
//        BigDecimal bb = new BigDecimal(b.toString());
//        BigDecimal cc = new BigDecimal(c.toString());
//        BigDecimal dd = aa.subtract(bb).subtract(cc);
//        if (dd.compareTo(BigDecimal.ZERO)==0) {
//            System.out.println("1");
//        }
        GenerateCode.fastGenerate();
    }
    @Test
    public void testw() throws IOException {
//        esDocumentDemoService.getById("1","1");
        URL url = new URL("https://www.baidu.com");
        try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter writer = new BufferedWriter(new FileWriter("page.html"));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
            System.out.println("页面已下载。");
        }
    }

    @Test
    public void test2() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("account","root");
        UserService userService = new UserServiceImpl();
        User user = userService.getOne(userQueryWrapper);
    }

    /**
     * 适合下载小文件
     * @throws IOException
     */
    @Test
    public void downloadToMemory() throws IOException {
//        // 单线程
//        long start = System.currentTimeMillis();
//        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        FileResponseExtractor extractor = new FileResponseExtractor("D:\\Data\\tmp" + ".download");
//        File tmpFile = (File) restTemplate.execute("https://image.baidu.com/search/down?url=https://tvax2.sinaimg.cn/large/006BNqYCly1hgfpxtpnz4j30j6159tfv.jpg", HttpMethod.GET,null,extractor);
//        tmpFile.renameTo(new File("D:\\Data\\tmp.png"));
//        System.out.println("总共下载文件耗时：" + (System.currentTimeMillis() - start) / 1000 + "S");
//        byte[] body = restTemplate.execute("https://image.baidu.com/search/down?url=https://tvax2.sinaimg.cn/large/006BNqYCly1hgfpxtpnz4j30j6159tfv.jpg", HttpMethod.GET,null,new ByteArrayResponseExtractor());
//        Files.write(Paths.get("D:\\Data\\tmp2.png"), Objects.requireNonNull(body));

        // 多线程
        MulitDown mulitDown = new MulitDown();
        try {
            mulitDown.multiThreadDownload("https://dldir1.qq.com/wework/work_weixin/WeCom_4.1.22.6009.exe","D:\\Data\\tmp.jpg",3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//    RAS测试
//    密钥生成测试
    @Test
    public void testCreateKey () throws Exception {
        HashMap<String, String> map = RsaCryptUtil.getTheKeys();
        String privateKeyStr=map.get("privateKey");
        String publicKeyStr=map.get("publicKey");
        System.out.println("私钥："+privateKeyStr);
        System.out.println("公钥："+publicKeyStr);
        //消息发送方
        String originData="cicada-smile";
        System.out.println("原文："+originData);
        String encryptData = RsaCryptUtil.encrypt(RsaCryptUtil.createPublicKey(publicKeyStr),
                originData.getBytes());
        System.out.println("加密："+encryptData);
        //消息接收方
        String decryptData=RsaCryptUtil.decrypt(RsaCryptUtil.createPrivateKey(privateKeyStr),
                RsaCryptUtil.parseBase64Binary(encryptData));
        System.out.println("解密："+decryptData);
    }

    // 密钥读取测试
    @Test
    public void testReadKey () throws Exception {
        String value = RsaCryptUtil.getKey("rsaKey/public.key");
        System.out.println(value);
        String privateKeyStr = RsaCryptUtil.getKey(RsaCryptUtil.PRI_KEY) ;
        String publicKeyStr = RsaCryptUtil.getKey(RsaCryptUtil.PUB_KEY) ;
        //消息发送方
        String originData="cicada-smile";
        System.out.println("原文："+originData);
        String encryptData = RsaCryptUtil.encrypt(RsaCryptUtil.createPublicKey(publicKeyStr),
                originData.getBytes());
        System.out.println("加密："+encryptData);
        //消息接收方
        String decryptData=RsaCryptUtil.decrypt(RsaCryptUtil.createPrivateKey(privateKeyStr),
                RsaCryptUtil.parseBase64Binary(encryptData));
        System.out.println("解密："+decryptData);
    }

    // 签名验签测试
    @Test
    public void testSignVerify () throws Exception {
        String signData = "cicada-smile" ;
        String privateKeyStr = RsaCryptUtil.getKey(RsaCryptUtil.PRI_KEY) ;
        String publicKeyStr = RsaCryptUtil.getKey(RsaCryptUtil.PUB_KEY) ;
        String signValue = RsaCryptUtil.sign(signData,RsaCryptUtil.createPrivateKey(privateKeyStr)) ;
        boolean flag = RsaCryptUtil.verify(signData,RsaCryptUtil.createPublicKey(publicKeyStr),signValue);
        System.out.println("原文:"+signData);
        System.out.println("签名:"+signValue);
        System.out.println("验签:"+flag);
    }

}
