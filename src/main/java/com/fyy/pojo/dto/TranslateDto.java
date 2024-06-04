package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-05-22 16:22:27
 * @description
 */
@Data
public class TranslateDto {
    @ApiModelProperty(value = "输入语言",required = true)
    private String from;
    @ApiModelProperty(value = "翻译出的语言",required = true)
    private String to;
    @ApiModelProperty(value = "输入的内容",required = true)
    private String text;
}
