package com.fyy.controller;

import com.fyy.common.MyException;
import com.fyy.common.R;
import com.fyy.pojo.dto.CompositionDTO;
import com.fyy.pojo.dto.TranslateByFileDTO;
import com.fyy.pojo.dto.TranslateDTO;
import com.fyy.pojo.entity.SparkClient;
import com.fyy.pojo.vo.LanguageVO;
import com.fyy.service.AIService;
import com.fyy.service.LanguageService;
import com.fyy.utils.AIUtil;
import com.fyy.utils.ITSUtil;
import com.fyy.utils.OcrUtil;
import com.fyy.utils.TextCorrectionUtil;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *AI集成功能
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
    @Autowired
    private LanguageService languageService;

    @ApiOperation("调用星火大模型")
    @PostMapping("/getAIAnswer")
    public R<?> getAIAnswer(@NotNull @RequestBody String question) {
        AIUtil aiUtil = new AIUtil(sparkClient);
        return R.success(aiUtil.getAIAnswer(question));
    }

    @ApiOperation("输入内容翻译")
    @PostMapping("/translate")
    public R<?> translate(@NotNull @RequestBody TranslateDTO translateDto) {
        ITSUtil itsUtil = new ITSUtil(sparkClient);
        return R.success(itsUtil.AITranslate(translateDto.getFrom(), translateDto.getTo(), translateDto.getText()));
    }

    @ApiOperation("上传文件进行翻译")
    @PostMapping("/translateByFile")
    public R<?> translateByFile(@NotNull @ModelAttribute TranslateByFileDTO translateByFileDto) {
        return R.success(aiService.translateByFile(translateByFileDto));
    }

    @ApiOperation("AI写作")
    @PostMapping("/writeComposition")
    public R<?> aiWriteComposition(@NotNull @RequestBody CompositionDTO compositionDto) {
        return R.success(aiService.aiWriteComposition(compositionDto));
    }

    @ApiOperation("文本纠错")
    @PostMapping("/textCorrection")
    public R<?> textCorrection(@NotNull String text) {
        TextCorrectionUtil textCorrectionUtil = new TextCorrectionUtil(sparkClient);
        try {
            return R.success(textCorrectionUtil.getTextCorrection(text));
        } catch (Exception e) {
            throw new MyException(String.valueOf(e));
        }
    }

    @ApiOperation("获取翻译语言")
    @GetMapping("/getTranslationLanguage")
    public R<List<LanguageVO>> getTranslationLanguage() {
        return R.success(languageService.getTranslationLanguage());
    }

    @ApiOperation("图片答题")
    @PostMapping("/imageAnswer")
    public R<?> imageAnswer(@NotNull @RequestParam("image") MultipartFile image) throws Exception {
        OcrUtil ocrUtil = new OcrUtil(sparkClient);
        AIUtil aiUtil = new AIUtil(sparkClient);
        return R.success(aiUtil.getAIAnswer(ocrUtil.imageRecognition(image)));
    }
}
