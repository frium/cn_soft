package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @date 2024-05-29 16:19:06
 * @description
 */
@Data
@ApiModel("翻译")
public class TranslateByFileDto {
    @ApiModelProperty(value = "输入语言",required = true)
    private String from;
    @ApiModelProperty(value = "翻译出的语言",required = true)
    private String to;
    @ApiModelProperty(value = "上传的文档",required = true)
    private MultipartFile file;
}
