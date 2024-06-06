package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @date 2024-05-29 16:19:06
 * @description
 */
@Data
@ApiModel("翻译")
public class TranslateByFileDto {
    @NotEmpty(message = "输入语言不能为空")
    @ApiModelProperty(value = "输入语言",required = true)
    private String from;

    @NotEmpty(message = "输出语言不能为空")
    @ApiModelProperty(value = "翻译出的语言",required = true)
    private String to;

    @NotNull("上传文档不能为空")
    @ApiModelProperty(value = "上传的文档",required = true)
    private MultipartFile file;
}
