package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-05-22 20:32:06
 * @description
 */
@Data
@ApiModel("成绩")
public class ScoreDto {
    @ApiModelProperty(value = "学号",required = true,dataType = "string")
    private Long studentId;
    @ApiModelProperty(value = "语文",required = true)
    private Float chinese;
    @ApiModelProperty(value = "数学",required = true)
    private Float math;
    @ApiModelProperty(value = "英语",required = true)
    private Float english;
    @ApiModelProperty(value = "物理",required = true)
    private Float physics;
    @ApiModelProperty(value = "化学",required = true)
    private Float chemistry;
    @ApiModelProperty(value = "历史",required = true)
    private Float history;
    @ApiModelProperty(value = "政治",required = true)
    private Float politics;
    @ApiModelProperty(value = "地理",required = true)
    private Float geography;
    @ApiModelProperty(value = "生物",required = true)
    private Float biology;
    @ApiModelProperty(value = "考试标题",required = true)
    private String title;
}
