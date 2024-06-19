package com.fyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.context.BaseContext;
import com.fyy.mapper.LanguageMapper;
import com.fyy.pojo.entity.Language;
import com.fyy.pojo.vo.LanguageVO;
import com.fyy.service.LanguageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @date 2024-06-04 13:30:30
 * @description
 */
@Slf4j
@Service
public class LanguageServiceImpl extends ServiceImpl<LanguageMapper, Language> implements LanguageService {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    /**
     * description:获取翻译语言的中文名和缩写
     * @return: java.util.List<com.fyy.pojo.vo.LanguageVO>
    */
    @Override
    public List<LanguageVO> getTranslationLanguage() {
        Long currentId = BaseContext.getCurrentId();
        log.info("用户 {} 获取翻译语言的中文名和缩写{}", currentId, LocalDateTime.now());
        String key = "languageVOList";
        // 从 Redis 中尝试获取列表
        List<LanguageVO> languageVOList = (List<LanguageVO>) redisTemplate.opsForValue().get(key);
        if (languageVOList == null) {
            // 如果 Redis 中没有数据，从数据库查询
            List<Language> list = lambdaQuery().list();
            languageVOList = new ArrayList<>();
            for (Language language : list) {
                LanguageVO languageVo = new LanguageVO();
                BeanUtils.copyProperties(language, languageVo);
                languageVOList.add(languageVo);
            }
            redisTemplate.opsForValue().set(key, languageVOList); // 存入 Redis 以便下次查找
        }
        return languageVOList;
    }
}
