package com.fyy.service.impl;

import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.pojo.dto.CompositionDTO;
import com.fyy.pojo.dto.TranslateByFileDTO;
import com.fyy.pojo.entity.SparkClient;
import com.fyy.service.AIService;
import com.fyy.utils.AIUtil;
import com.fyy.utils.FileUtil;
import com.fyy.utils.ITSUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @date 2024-05-22 15:06:35
 * @description
 */
@Service
public class AIServiceImpl implements AIService {
    @Resource
    SparkClient sparkClient;

    @Override
    public String translateByFile(TranslateByFileDTO translateByFileDto) {
        try {
            String file = FileUtil.convertMultipartFileToString(translateByFileDto.getFile());
            ITSUtil itsUtil = new ITSUtil(sparkClient);
            return itsUtil.AITranslate(translateByFileDto.getFrom(), translateByFileDto.getTo(), file);
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.VALUE_ERROR);
        }
    }

    @Override
    public String aiWriteComposition(CompositionDTO compositionDto) {
        String question="你现在是我的写作帮助老师,接下来你需要辅导我完成写作,这个是我的需求"+compositionDto.getRequirement()+"语言要求:"+compositionDto.getLanguage()+
                "不要有对你的行为的总结和祈使,只要给我生成一篇文章即可";
        AIUtil aiUtil=new AIUtil(sparkClient);
       return aiUtil.getAIAnswer(question);
    }
}
