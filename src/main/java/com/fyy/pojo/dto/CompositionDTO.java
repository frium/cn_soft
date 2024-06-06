package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


/**
 *
 * @date 2024-06-04 14:19:52
 * @description
 */
@Data
@ApiModel("ai文章书写")
public class CompositionDTO {
    @NotEmpty(message = "选择语言不能为空")
    @ApiModelProperty(value = "语言",required = true)
    String language;

    @NotEmpty(message = "输入内容不能为空")
    @ApiModelProperty(value = "题目/需求",required = true)
    String requirement;
}
