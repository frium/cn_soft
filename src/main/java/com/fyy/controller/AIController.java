package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.dto.TranslateDto;
import com.fyy.utils.AIUtil;
import com.fyy.utils.ITSUtil;
import com.fyy.utils.TextCorrectionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @date 2024-05-15 18:16:38
 * @description
 */
@RestController
@RequestMapping("/AI")
public class AIController {
    @Value("${ai.id}")
    private String appid;
    @Value("${ai.secret}")
    private String apiSecret;
    @Value("${ai.key}")
    private String apiKey;


    @PostMapping("/getAIAnswer")
    public R<String> getAIAnswer(@RequestBody String question){
        AIUtil aiUtil=new AIUtil(appid,apiKey,apiSecret);
        return R.success(aiUtil.getAIAnswer(question));


    }
    //输入翻译
    @PostMapping("/translate")
    public R<String> translate(@RequestBody TranslateDto translateDto) throws Exception {
        return R.success(ITSUtil.AITranslate(translateDto.getFrom(),translateDto.getTo(),translateDto.getText()));
    }
    //文档翻译
    @PostMapping("/translateByFile")
    public R<String> translateByFile(@RequestParam("file") MultipartFile file){
        return null;
    }
    @PostMapping("/writing")
    public R<String> writing (){
        return null;
    }
    //文本纠错
    @PostMapping("/textCorrection")
    public R<String> textCorrection(String text) throws Exception {
        TextCorrectionUtil textCorrectionUtil=new TextCorrectionUtil();
        return R.success(textCorrectionUtil.getTextCorrection(text));
    }

}
