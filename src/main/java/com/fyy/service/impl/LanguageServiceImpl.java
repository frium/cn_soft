package com.fyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.mapper.LanguageMapper;
import com.fyy.pojo.entity.Language;
import com.fyy.pojo.vo.LanguageVo;
import com.fyy.service.LanguageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @date 2024-06-04 13:30:30
 * @description
 */
@Service
public class LanguageServiceImpl extends ServiceImpl<LanguageMapper, Language> implements LanguageService {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Override
    public List<LanguageVo> getTranslationLanguage() {
        String key = "languageVoList";
        // 从 Redis 中尝试获取列表
        List<LanguageVo> languageVoList = (List<LanguageVo>) redisTemplate.opsForValue().get(key);
        if (languageVoList == null) {
            // 如果 Redis 中没有数据，从数据库查询
            List<Language> list = lambdaQuery().list();
            languageVoList = new ArrayList<>();
            for (Language language : list) {
                LanguageVo languageVo = new LanguageVo();
                BeanUtils.copyProperties(language, languageVo);
                languageVoList.add(languageVo);
            }
            // 存入 Redis 以便下次查找
            redisTemplate.opsForValue().set(key, languageVoList);
        }
        return languageVoList;
    }
}