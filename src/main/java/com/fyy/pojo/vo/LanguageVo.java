package com.fyy.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-06-04 13:25:53
 * @description
 */
@Data
@ApiModel("翻译语言展示")
public class LanguageVo {
    @ApiModelProperty(value = "语言名称",example = "英语")
    String language;
    @ApiModelProperty(value = "语言缩写",example = "cn")
    String abbr;
}
