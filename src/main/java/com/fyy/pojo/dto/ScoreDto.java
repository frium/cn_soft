package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 *
 * @date 2024-05-22 20:32:06
 * @description
 */
@Data
@ApiModel("成绩")
public class ScoreDto {
    @Pattern(regexp = "^.{12}$", message = "学号长度有误")
    @ApiModelProperty(value = "学号", required = true, dataType = "string")
    private String studentId;

    @DecimalMax(value = "100", message = "成绩的范围应在0~100之间")
    @DecimalMin(value = "0", message = "成绩的范围应在0~100之间")
    @ApiModelProperty(value = "语文", required = true)
    private Float chinese;

    @DecimalMax(value = "100", message = "成绩的范围应在0~100之间")
    @DecimalMin(value = "0", message = "成绩的范围应在0~100之间")
    @ApiModelProperty(value = "数学", required = true)
    private Float math;

    @DecimalMax(value = "100", message = "成绩的范围应在0~100之间")
    @DecimalMin(value = "0", message = "成绩的范围应在0~100之间")
    @ApiModelProperty(value = "英语", required = true)
    private Float english;

    @DecimalMax(value = "100", message = "成绩的范围应在0~100之间")
    @DecimalMin(value = "0", message = "成绩的范围应在0~100之间")
    @ApiModelProperty(value = "物理", required = true)
    private Float physics;

    @DecimalMax(value = "100", message = "成绩的范围应在0~100之间")
    @DecimalMin(value = "0", message = "成绩的范围应在0~100之间")
    @ApiModelProperty(value = "化学", required = true)
    private Float chemistry;

    @DecimalMax(value = "100", message = "成绩的范围应在0~100之间")
    @DecimalMin(value = "0", message = "成绩的范围应在0~100之间")
    @ApiModelProperty(value = "历史", required = true)
    private Float history;

    @DecimalMax(value = "100", message = "成绩的范围应在0~100之间")
    @DecimalMin(value = "0", message = "成绩的范围应在0~100之间")
    @ApiModelProperty(value = "政治", required = true)
    private Float politics;

    @DecimalMax(value = "100", message = "成绩的范围应在0~100之间")
    @DecimalMin(value = "0", message = "成绩的范围应在0~100之间")
    @ApiModelProperty(value = "地理", required = true)
    private Float geography;


    @DecimalMax(value = "100", message = "成绩的范围应在0~100之间")
    @DecimalMin(value = "0", message = "成绩的范围应在0~100之间")
    @ApiModelProperty(value = "生物", required = true)
    private Float biology;


    @NotEmpty(message = "考试标题不能为空")
    @ApiModelProperty(value = "考试标题", required = true)
    private String title;
}
