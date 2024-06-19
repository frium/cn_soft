package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 *
 * @date 2024-06-03 23:18:18
 * @description
 */
@Data
@ApiModel("定制学习计划")
public class PlanDTO {
    @NotEmpty(message = "选择定制的科目不能为空")
    @ApiModelProperty(value = "需要定制的科目", required = true)
    List<String> subjects;

    @Length(message = "输入的目标不能小于十五个字", min = 15)
    @ApiModelProperty(value = "目标(AI辅助)", required = true)
    String target;

    @NotEmpty(message = "选择的学习时间不能为空")
    @ApiModelProperty(value = "预计的学习时间", required = true)
    String time;

    @NotEmpty(message = "定制的学习程度不能为空")
    @ApiModelProperty(value = "学习程度",required = true)
    String level;
}
