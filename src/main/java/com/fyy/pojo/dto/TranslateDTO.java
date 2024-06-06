package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @date 2024-05-22 16:22:27
 * @description
 */
@Data
public class TranslateDTO {
    @NotEmpty(message = "输入语言不能为空")
    @ApiModelProperty(value = "输入语言",required = true)
    private String from;

    @NotEmpty(message = "输出语言不能为空")
    @ApiModelProperty(value = "翻译出的语言",required = true)
    private String to;

    @NotEmpty(message = "输入内容不能为空")
    @ApiModelProperty(value = "输入的内容",required = true)
    private String text;
}
