package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-06-04 14:19:52
 * @description
 */
@Data
@ApiModel("ai文章书写")
public class CompositionDto {
    @ApiModelProperty(value = "语言",required = true)
    String language;
    @ApiModelProperty(value = "题目/需求",required = true)
    String requirement;
}
