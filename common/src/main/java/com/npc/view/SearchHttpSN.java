package com.npc.view;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author NPC
 * @description
 * @create 2023/12/4 22:27
 */
/**
 * 选择AK使用SN校验：
 */
@Component
public class SearchHttpSN {

    public static String AK = "Kxy4sxFwmycxBmpSusWal96CBOS1Mscn";

    public static String SK = "P8l8F1akKTehmaU8aKymZsDPrYGhSSc9";

    public static String URL = "https://api.map.baidu.com/location/ip?";

    public static void main(String[] args) throws Exception {

        SearchHttpSN snCal = new SearchHttpSN();

        Map params = new LinkedHashMap<String, String>();
        params.put("ip", "111.206.214.37");
        params.put("coor", "bd09ll");
        params.put("ak", AK);

        params.put("sn", snCal.caculateSn("111.206.214.37"));

        snCal.requestGetSN(URL, params);
    }

    public String getInfo(String ip,String coor) throws Exception {
        SearchHttpSN snCal = new SearchHttpSN();

        Map params = new LinkedHashMap<String, String>();
        params.put("ip", ip);
        params.put("coor", "bd09ll");
        params.put("ak", AK);

        params.put("sn", snCal.caculateSn(ip));

        return snCal.requestGetSN(URL, params);
    }

    /**
     * 选择了ak，使用SN校验：
     * 根据您选择的AK已为您生成调用代码
     * 检测您当前的AK设置了sn检验，本示例中已为您生成sn计算代码
     * @param strUrl
     * @param param
     * @throws Exception
     */
    public String requestGetSN(String strUrl, Map<String, String> param) throws Exception {
        if (strUrl == null || strUrl.length() <= 0 || param == null || param.size() <= 0) {
            return "";
        }

        StringBuffer queryString = new StringBuffer();
        queryString.append(strUrl);
        for (Map.Entry<?, ?> pair : param.entrySet()) {
            queryString.append(pair.getKey() + "=");
            //    第一种方式使用的 jdk 自带的转码方式  第二种方式使用的 spring 的转码方法 两种均可
            //    queryString.append(URLEncoder.encode((String) pair.getValue(), "UTF-8").replace("+", "%20") + "&");
            queryString.append(UriUtils.encode((String) pair.getValue(), "UTF-8") + "&");
        }

        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }

        java.net.URL url = new URL(queryString.toString());
        System.out.println(queryString.toString());
        URLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();

        InputStreamReader isr = new InputStreamReader(httpConnection.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        isr.close();
        System.out.println("SN: " + buffer.toString());
        return buffer.toString();
    }

    public String caculateSn(String ip) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        SearchHttpSN snCal = new SearchHttpSN();

        // 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存<key,value>，该方法会自动将key按照字母a-z顺序排序。
        // 所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。
        // 以get请求为例：http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。
        Map paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("ip", ip);
        paramsMap.put("coor", "bd09ll");
        paramsMap.put("ak", AK);


        // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
        String paramsStr = snCal.toQueryString(paramsMap);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
        String wholeStr = new String("/location/ip?" + paramsStr + SK);

        System.out.println(wholeStr);
        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到最后的sn签名
        String sn = snCal.MD5(tempStr);
        System.out.println(sn);
        return sn;
    }

    // 对Map内所有value作utf8编码，拼接返回结果
    public String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            //    第一种方式使用的 jdk 自带的转码方式  第二种方式使用的 spring 的转码方法 两种均可
            //    queryString.append(URLEncoder.encode((String) pair.getValue(), "UTF-8").replace("+", "%20") + "&");
            queryString.append(UriUtils.encode((String) pair.getValue(), "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}