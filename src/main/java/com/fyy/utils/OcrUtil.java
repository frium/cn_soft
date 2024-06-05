package com.fyy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.pojo.entity.SparkClient;
import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static com.fyy.utils.FileUtil.read;

/**
 * appid、apiSecret、apiKey请到讯飞开放平台控制台获取；
 *图像数据,base64编码后最不得大于4M；
 */
@SuppressWarnings("all")
public class OcrUtil {
    private final String requestUrl = "https://api.xf-yun.com/v1/private/hh_ocr_recognize_doc";
    private static MultipartFile IMAGE;
    //解析Json
    private static Gson json = new Gson();

    private static String APPID;
    private static String API_SECRET;
    private static String API_KEY;

    public OcrUtil(SparkClient sparkClient) {
        this.API_KEY = sparkClient.apiKey;
        this.APPID = sparkClient.appid;
        this.API_SECRET = sparkClient.apiSecret;
    }

    public OcrUtil() {
    }

    public  String imageRecognition(MultipartFile file) throws Exception {
        OcrUtil demo = new OcrUtil();
        try {
            IMAGE = file;
            String resp = demo.doRequest();
            JsonParse myJsonParse = json.fromJson(resp, JsonParse.class);
            String textBase64Decode = new String(Base64.getDecoder().decode(myJsonParse.payload.recognizeDocumentRes.text), "UTF-8");
            JSONObject jsonObject = JSON.parseObject(textBase64Decode);
            String wholeText = jsonObject.getString("whole_text");
            return wholeText;
        } catch (Exception e) {
            throw new Exception(e);

        }
    }

    /**
     * 请求主方法
     * @return 返回服务结果
     * @throws Exception 异常
     */
    public String doRequest() throws Exception {

        URL realUrl = new URL(buildRequetUrl());
        URLConnection connection = realUrl.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type", "application/json");

        OutputStream out = httpURLConnection.getOutputStream();
        String params = buildParam();
        //System.out.println("params=>"+params);
        out.write(params.getBytes());
        out.flush();
        InputStream is = null;
        try {
            is = httpURLConnection.getInputStream();
        } catch (Exception e) {
            is = httpURLConnection.getErrorStream();
            throw new Exception("make request error:" + "code is " + httpURLConnection.getResponseMessage() + readAllBytes(is));
        }
        return readAllBytes(is);
    }

    /**
     * 处理请求URL
     * 封装鉴权参数等
     * @return 处理后的URL
     */
    public String buildRequetUrl() {
        URL url = null;
        // 替换调schema前缀 ，原因是URL库不支持解析包含ws,wss schema的url
        String httpRequestUrl = requestUrl.replace("ws://", "http://").replace("wss://", "https://");
        try {
            url = new URL(httpRequestUrl);
            //获取当前日期并格式化
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());
            String host = url.getHost();
			/*if (url.getPort()!=80 && url.getPort() !=443){
				host = host +":"+String.valueOf(url.getPort());
			}*/
            StringBuilder builder = new StringBuilder("host: ").append(host).append("\n").//
                    append("date: ").append(date).append("\n").//
                    append("POST ").append(url.getPath()).append(" HTTP/1.1");
            //System.out.println("host原始字段："+builder);
            Charset charset = Charset.forName("UTF-8");
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(API_SECRET.getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);
            //System.out.println("加密后的signature为："+sha);

            String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", API_KEY, "hmac-sha256", "host date request-line", sha);
            String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
            return String.format("%s?authorization=%s&host=%s&date=%s", requestUrl, URLEncoder.encode(authBase), URLEncoder.encode(host), URLEncoder.encode(date));

        } catch (Exception e) {
            throw new RuntimeException("assemble requestUrl error:" + e.getMessage());
        }
    }

    /**
     * 组装请求参数
     * 直接使用示例参数，
     * 替换部分值
     * @return 参数字符串
     */
    private String buildParam() throws Exception {
        String base64 = Base64.getEncoder().encodeToString(read(IMAGE));
        if(base64.length()>4*1024*1024){
            throw new MyException(StatusCodeEnum.FILE_LARGE);
        }
        String param = "{" +
                "    \"header\": {" +
                "        \"app_id\": \"" + APPID + "\"," +
                "        \"status\": 3" +
                "    }," +
                "    \"parameter\": {" +
                "        \"hh_ocr_recognize_doc\": {" +
                "            \"recognizeDocumentRes\": {" +
                "                \"encoding\": \"utf8\"," +
                "                \"compress\": \"raw\"," +
                "                \"format\": \"json\"" +
                "            }" +
                "        }" +
                "    }," +
                "    \"payload\": {" +
                "        \"image\": {" +
                "            \"encoding\": \"jpg\"," +
                "            \"image\": \"" + base64 + "\"," +
                "            \"status\": 3" +
                "        }" +
                "    }" +
                "}";
        return param;
    }

    /**
     * 读取流数据
     * @param is 流
     * @return 字符串
     * @throws IOException 异常
     */
    private String readAllBytes(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    //Json解析
    class JsonParse {
        public Header header;
        public Payload payload;
    }

    class Header {
        public int code;
        public String message;
        public String sid;
    }

    class Payload {
        public Result recognizeDocumentRes;
    }

    class Result {
        public String compress;
        public String encoding;
        public String format;
        public String text;
    }
}


