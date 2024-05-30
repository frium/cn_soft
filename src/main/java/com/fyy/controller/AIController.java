package com.fyy.controller;

import com.fyy.common.R;
import com.fyy.pojo.dto.TranslateByFileDto;
import com.fyy.pojo.dto.TranslateDto;
import com.fyy.pojo.entity.SparkClient;
import com.fyy.service.AIService;
import com.fyy.utils.AIUtil;
import com.fyy.utils.ITSUtil;
import com.fyy.utils.TextCorrectionUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Resource
    private SparkClient sparkClient;
    @Autowired
    private AIService aiService;

    @PostMapping("/getAIAnswer")
    public R<String> getAIAnswer(@RequestBody String question) {
        AIUtil aiUtil = new AIUtil(sparkClient);
        return R.success(aiUtil.getAIAnswer(question));
    }

    //输入翻译
    @PostMapping("/translate")
    public R<String> translate(@RequestBody TranslateDto translateDto) {
        ITSUtil itsUtil=new ITSUtil(sparkClient);
        return R.success(itsUtil.AITranslate(translateDto.getFrom(), translateDto.getTo(), translateDto.getText()));
    }

    //文档翻译
    @PostMapping("/translateByFile")
    public R<String> translateByFile(@ModelAttribute TranslateByFileDto translateByFileDto) {
        return R.success(aiService.translateByFile(translateByFileDto));
    }

    @PostMapping("/writing")
    public R<String> writing() {
        return null;
    }

    //文本纠错
    @PostMapping("/textCorrection")
    public R<String> textCorrection(String text) throws Exception {
        TextCorrectionUtil textCorrectionUtil = new TextCorrectionUtil(sparkClient);
        return R.success(textCorrectionUtil.getTextCorrection(text));
    }

}
