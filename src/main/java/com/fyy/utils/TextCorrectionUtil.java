package com.fyy.utils;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @date 2024-05-24 15:04:52
 * @description
 */
public class TextCorrectionUtil {

    /**
     * 文本纠错 WebAPI 接口调用示例
     * 运行前：请先填写Appid、APIKey、APISecret以及图片路径
     * 运行方法：直接运行 main() 即可
     * 结果： 控制台输出结果信息
     * 接口文档（必看）：https://www.xfyun.cn/doc/nlp/textCorrection/API.html
     * uid与res_id可以到ResIdGet上传和获取
     *
     * @author iflytek
     */
    @Value("${ai.appid}")
    public String appid;
    @Value("${ai.apiSecret}")
    public String apiSecret;
    @Value("${ai.apiKey}")
    public String apiKey;
    // 地址与鉴权信息
    public static final String hostUrl = "https://api.xf-yun.com/v1/private/s9a87e3ec";

    // json
    public static final Gson gson = new Gson();

    // 主函数
    public String getTextCorrection(String text) throws Exception {
        System.out.println(appid);
        System.out.println(apiSecret);
        System.out.println(apiKey);
        String url = getAuthUrl(hostUrl, apiKey, apiSecret);
        String json = getRequestJson(text);
        String backResult = doPostJson(url, json);
        JsonParse jsonParse = gson.fromJson(backResult, JsonParse.class);
        return new String(Base64.getDecoder().decode(jsonParse.payload.result.text), StandardCharsets.UTF_8);
    }

    // 请求参数json拼接
    public String getRequestJson(String text) {
        return "{\n" +
                "  \"header\": {\n" +
                "    \"app_id\": \"" + appid + "\",\n" +
                //"    \"uid\": \"fa6cf0c7-3852-4d69-8814-703dcf681d7c\",\n" +
                "    \"status\": 3\n" +
                "  },\n" +
                "  \"parameter\": {\n" +
                "    \"s9a87e3ec\": {\n" +
                //"    \"res_id\": \"\"frium\",\n" +
                "      \"result\": {\n" +
                "        \"encoding\": \"utf8\",\n" +
                "        \"compress\": \"raw\",\n" +
                "        \"format\": \"json\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"payload\": {\n" +
                "    \"input\": {\n" +
                "      \"encoding\": \"utf8\",\n" +
                "      \"compress\": \"raw\",\n" +
                "      \"format\": \"plain\",\n" +
                "      \"status\": 3,\n" +
                "      \"text\": \"" + getBase64TextData(text) + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    // 读取文件
    public static String getBase64TextData(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    // 根据json和url发起post请求
    public static String doPostJson(String url, String json) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            resultString = EntityUtils.toString(closeableHttpResponse.getEntity(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (closeableHttpResponse != null) {
                    closeableHttpResponse.close();
                }
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    // 鉴权方法
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "POST " + url.getPath() + " HTTP/1.1";
        //System.out.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        return httpUrl.toString();
    }

    //返回的json结果拆解
    static class JsonParse {
        Payload payload;
    }

    static class Payload {
        Result result;
    }

    static class Result {
        String text;
    }
}

