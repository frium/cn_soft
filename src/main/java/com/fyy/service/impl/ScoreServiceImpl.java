package com.fyy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyy.common.MyException;
import com.fyy.common.R;
import com.fyy.common.StatusCodeEnum;
import com.fyy.context.BaseContext;
import com.fyy.mapper.ScoreMapper;
import com.fyy.pojo.dto.PageDTO;
import com.fyy.pojo.dto.ScoreDTO;
import com.fyy.pojo.entity.Score;
import com.fyy.pojo.vo.StudentScoreRankVO;
import com.fyy.pojo.vo.StudentScoreVO;
import com.fyy.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("教师用户 {} 通过title {} 获取所有学生的成绩 {}", currentId, pageDTO.getTitle(), LocalDateTime.now());
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
        Long currentId = BaseContext.getCurrentId();
        log.info("教师用户 {} 获取所有的考试标题{}", currentId, LocalDateTime.now());
        int offset = (pageDTO.getPage() - 1) * pageDTO.getPageSize();
        //分页查询所有考试的title
        //TODO 有bug
        List<String> allTitle = scoreMapper.getAllScores(currentId, offset, pageDTO.getPageSize(), pageDTO.getTitle());
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
        float total = scoreDTO.getBiology() + scoreDTO.getMath()
                + scoreDTO.getChemistry() + scoreDTO.getEnglish()
                + scoreDTO.getHistory() + scoreDTO.getChinese()
                + scoreDTO.getPhysics() + scoreDTO.getPolitics()
                + scoreDTO.getGeography();
        score.setTotalScore(total);
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
        Long currentId = BaseContext.getCurrentId();
        log.info("学生用户 {} 获取所有的考试标题{}", currentId, LocalDateTime.now());
        int offset = (pageDTO.getPage() - 1) * pageDTO.getPageSize();
        //分页模糊查询查看学生自己的成绩
        List<StudentScoreVO> studentScores = scoreMapper.getMyAllScores(currentId, offset, pageDTO.getPageSize(), pageDTO.getTitle());
        if (studentScores.isEmpty()) {
            throw new MyException(StatusCodeEnum.NOT_FOUND);
        }
        return studentScores;
    }

    @Override
    public StudentScoreRankVO getRank(String title) {
        Long currentId = BaseContext.getCurrentId();
        StudentScoreRankVO studentScoreRankVO = new StudentScoreRankVO();
        studentScoreRankVO.setChineseRank(scoreMapper.getRank(currentId, title, "chinese"));
        studentScoreRankVO.setMathRank(scoreMapper.getRank(currentId, title, "math"));
        studentScoreRankVO.setEnglishRank(scoreMapper.getRank(currentId, title, "english"));
        studentScoreRankVO.setBiologyRank(scoreMapper.getRank(currentId, title, "biology"));
        studentScoreRankVO.setChemistryRank(scoreMapper.getRank(currentId, title, "chemistry"));
        studentScoreRankVO.setPhysicsRank(scoreMapper.getRank(currentId, title, "physics"));
        studentScoreRankVO.setPoliticsRank(scoreMapper.getRank(currentId, title, "politics"));
        studentScoreRankVO.setGeographyRank(scoreMapper.getRank(currentId, title, "geography"));
        studentScoreRankVO.setHistoryRank(scoreMapper.getRank(currentId, title, "history"));
        studentScoreRankVO.setTotalScoreRank(scoreMapper.getRank(currentId, title, "total_score"));
        return studentScoreRankVO;
    }
}
