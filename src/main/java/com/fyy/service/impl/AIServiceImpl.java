package com.fyy.service.impl;

import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.context.BaseContext;
import com.fyy.pojo.dto.CompositionDTO;
import com.fyy.pojo.dto.TranslateByFileDTO;
import com.fyy.pojo.entity.SparkClient;
import com.fyy.service.AIService;
import com.fyy.utils.AIUtil;
import com.fyy.utils.FileUtil;
import com.fyy.utils.ITSUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 *
 * @date 2024-05-22 15:06:35
 * @description
 */
@Slf4j
@Service
public class AIServiceImpl implements AIService {
    @Resource
    SparkClient sparkClient;

    /**
     * description: 上传文件进行翻译
     * @Param translateByFileDTO: 翻译dto
     * @return: java.lang.String
    */
    @Override
    public String translateByFile(TranslateByFileDTO translateByFileDTO) {
        Long currentId = BaseContext.getCurrentId();
        log.info("用户 {} 上传文件进行翻译 {}", currentId, LocalDateTime.now());
        try {
            String file = FileUtil.convertMultipartFileToString(translateByFileDTO.getFile());
            ITSUtil itsUtil = new ITSUtil(sparkClient);
            return itsUtil.AITranslate(translateByFileDTO.getFrom(), translateByFileDTO.getTo(), file);//调用ai进行翻译
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.VALUE_ERROR);
        }
    }

    /**
     * description:调用大模型进行写作
     * @Param compositionDTO: 写作dto
     * @return: java.lang.String
    */
    @Override
    public String aiWriteComposition(CompositionDTO compositionDTO) {
        Long currentId = BaseContext.getCurrentId();
        log.info("用户 {} 调用大模型进行写作 {}", currentId, LocalDateTime.now());
        //添加前提需求
        String question="你现在是我的写作帮助老师,接下来你需要辅导我完成写作,这个是我的需求"+compositionDTO.getRequirement()+
                "不要有对你的行为的总结和祈使,只要给我生成一篇文章即可";
        AIUtil aiUtil=new AIUtil(sparkClient);
       return aiUtil.getAIAnswer(question);//调用大模型生产文章
    }
}
