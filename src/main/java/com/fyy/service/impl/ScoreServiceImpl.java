package com.fyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.common.MyException;
import com.fyy.common.StatusCodeEnum;
import com.fyy.context.BaseContext;
import com.fyy.mapper.ScoreMapper;
import com.fyy.pojo.dto.PageDTO;
import com.fyy.pojo.dto.ScoreDTO;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.vo.StudentScoreVO;
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

    /**
     * description:利用title查看指定考试的所有学生的成绩(教师端)
     * @Param pageDTO:分页模糊查询dto
     * @return: java.util.List<com.fyy.pojo.vo.StudentScoreVO>
     */
    @Override
    public List<StudentScoreVO> getAllStudentsScores(PageDTO pageDTO) {
        Long currentId = BaseContext.getCurrentId();
        int offset = (pageDTO.getPage() - 1) * pageDTO.getPageSize();
        //分页查询当前考试的所有学生成绩
        List<StudentScoreVO> allStudentsScores = scoreMapper.getAllStudentsScores(currentId, offset, pageDTO.getPageSize(), pageDTO.getTitle(), false);
        if (allStudentsScores.isEmpty()) {
            throw new MyException(StatusCodeEnum.NOT_FOUND);
        }
        return allStudentsScores;
    }

    /**
     * description:利用page,pageSize,title进行成绩标题的模糊查询(教师端)
     * @Param pageDTO: 分页模糊查询dto
     * @return: java.util.List<java.lang.String>
     */
    @Override
    public List<String> getAllScoresTitle(PageDTO pageDTO) {
        int offset = (pageDTO.getPage() - 1) * pageDTO.getPageSize();
        //分页查询所有考试的title
        List<String> allTitle = scoreMapper.getAllScores(offset, pageDTO.getPageSize(), pageDTO.getTitle());
        if (allTitle.isEmpty()) {
            throw new MyException(StatusCodeEnum.NOT_FOUND);
        }
        return allTitle;
    }

    /**
     * description:添加学生的考试分数(教师端)
     * @Param scoreDTO: 分数DTO
     * @return: void
     */
    @Override
    public void addStudentScore(ScoreDTO scoreDTO) {
        Score score = new Score();
        BeanUtils.copyProperties(scoreDTO, score);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        score.setCreateTime(formattedDateTime);
        score.setUpdateTime(formattedDateTime);
        save(score);
    }

    /**
     * description:分页模糊查询查看自己的成绩(学生端)
     * @Param pageDTO: 分页模糊查询dto
     * @return: java.util.List<com.fyy.pojo.vo.StudentScoreVO>
     */
    @Override
    public List<StudentScoreVO> getMyAllScores(PageDTO pageDTO) {
        Long id = BaseContext.getCurrentId();
        int offset = (pageDTO.getPage() - 1) * pageDTO.getPageSize();
        //分页模糊查询查看学生自己的成绩
        List<StudentScoreVO> studentScores = scoreMapper.getMyAllScores(id, offset, pageDTO.getPageSize(),pageDTO.getTitle());
        if (studentScores.isEmpty()) {
            throw new MyException(StatusCodeEnum.NOT_FOUND);
        }
        return studentScores;
    }
}
