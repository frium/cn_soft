package com.fyy.service;

import com.fyy.pojo.dto.CompositionDTO;
import com.fyy.pojo.dto.TranslateByFileDTO;

/**
 *
 * @date 2024-05-22 15:07:00
 * @description
 */

public interface AIService {
    String translateByFile(TranslateByFileDTO translateByFileDto);
    String aiWriteComposition(CompositionDTO compositionDto);
}
