package com.fyy.service;

import com.fyy.pojo.dto.TranslateByFileDto;

/**
 *
 * @date 2024-05-22 15:07:00
 * @description
 */

public interface AIService {
    //翻译
    String translateByFile(TranslateByFileDto translateByFileDto);
}
