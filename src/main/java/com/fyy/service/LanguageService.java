package com.fyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyy.pojo.entity.Language;
import com.fyy.pojo.vo.LanguageVO;

import java.util.List;

/**
 *
 * @date 2024-06-04 13:29:30
 * @description
 */
public interface LanguageService extends IService<Language> {
    List<LanguageVO> getTranslationLanguage();
}
