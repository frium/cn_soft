package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @date 2024-06-03 23:18:18
 * @description
 */
@Data
@ApiModel("定制学习计划")
public class PlanDto {
    @ApiModelProperty(value = "需要定制的科目",required = true)
    List<String> subjects;
    @ApiModelProperty(value = "目标",required = true)
    String target;
    @ApiModelProperty(value = "预计的学习时间",required = true)
    String time;
}
