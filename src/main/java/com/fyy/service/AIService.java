package com.fyy.service;

import com.fyy.pojo.dto.CompositionDto;
import com.fyy.pojo.dto.TranslateByFileDto;

/**
 *
 * @date 2024-05-22 15:07:00
 * @description
 */

public interface AIService {
    String translateByFile(TranslateByFileDto translateByFileDto);
    String aiWriteComposition(CompositionDto compositionDto);
}
