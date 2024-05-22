package com.fyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.mapper.ScoreMapper;
import com.fyy.pojo.dto.PageDto;
import com.fyy.pojo.dto.ScoreDto;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.vo.StudentVo;
import com.fyy.service.ScoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @date 2024-05-16 17:50:53
 * @description
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;

    @Override
    public List<StudentVo> getAllStudentsScores(PageDto pageDto) {
        int offset = (pageDto.getPageSize() - 1) * pageDto.getPage();
        List<StudentVo> allStudentsScores = scoreMapper.getAllStudentsScores(pageDto.getId(), offset, pageDto.getPage(), pageDto.getTitle());
        if (allStudentsScores.isEmpty()) {
            throw new MyException(StatusCodeEnum.NOT_FOUND);
        }
        return allStudentsScores;
    }

    @Override
    public void addStudentScore(ScoreDto scoreDto) {
        Score score = new Score();
        BeanUtils.copyProperties(scoreDto, score);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        score.setCreateTime(formattedDateTime);
        score.setUpdateTime(formattedDateTime);
        save(score);
    }

    @Override
    public List<Score> getStudentScores(String ID) {
        List<Score> studentScores = scoreMapper.getStudentScores(ID);
        if (studentScores.isEmpty()) {
            throw new MyException(StatusCodeEnum.NOT_FOUND);
        }
        return studentScores;
    }
}