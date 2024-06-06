package com.fyy.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-05-28 14:06:49
 * @description
 */
@Data
@ApiModel("考试成绩展示")
public class StudentScoreVO {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("考试标题")
    private String title;
    @ApiModelProperty("学号")
    private String studentId;
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


}
