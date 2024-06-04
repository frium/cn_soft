package com.fyy.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @date 2024-05-28 14:06:49
 * @description
 */
@Data
@ApiModel("考试成绩展示")
public class StudentScoreVo {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("考试标题")
    private String title;
    @ApiModelProperty("学号")
    private Long studentId;
    @ApiModelProperty(value = "语文")
    private Float chinese;
    @ApiModelProperty(value = "数学")
    private Float math;
    @ApiModelProperty(value = "英语")
    private Float english;
    @ApiModelProperty(value = "物理")
    private Float physics;
    @ApiModelProperty(value = "化学")
    private Float chemistry;
    @ApiModelProperty(value = "历史")
    private Float history;
    @ApiModelProperty(value = "政治")
    private Float politics;
    @ApiModelProperty(value = "地理")
    private Float geography;
    @ApiModelProperty(value = "生物")
    private Float biology;

    public static StudentScoreVo fromRowData(List<String> rowData,String title) {
        StudentScoreVo studentScore = new StudentScoreVo();
        studentScore.setName(rowData.get(0));
        studentScore.setTitle(title);
        studentScore.setStudentId(Long.valueOf(rowData.get(1)));
        studentScore.setChinese(Float.parseFloat(rowData.get(2)));
        studentScore.setMath(Float.parseFloat(rowData.get(3)));
        studentScore.setEnglish(Float.parseFloat(rowData.get(4)));
        studentScore.setPhysics(Float.parseFloat(rowData.get(5)));
        studentScore.setChemistry(Float.parseFloat(rowData.get(6)));
        studentScore.setHistory(Float.parseFloat(rowData.get(7)));
        studentScore.setPolitics(Float.parseFloat(rowData.get(8)));
        studentScore.setGeography(Float.parseFloat(rowData.get(9)));
        studentScore.setBiology(Float.parseFloat(rowData.get(10)));
        return studentScore;
    }
}
