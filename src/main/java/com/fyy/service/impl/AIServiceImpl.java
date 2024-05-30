package com.fyy.service.impl;

import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.pojo.dto.TranslateByFileDto;
import com.fyy.pojo.entity.SparkClient;
import com.fyy.service.AIService;
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
    public String translateByFile(TranslateByFileDto translateByFileDto) {
        try {
            String file = FileUtil.convertMultipartFileToString(translateByFileDto.getFile());
            ITSUtil itsUtil = new ITSUtil(sparkClient);
            return itsUtil.AITranslate(translateByFileDto.getFrom(), translateByFileDto.getTo(), file);
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.VALUE_ERROR);
        }
    }
}
