package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-05-22 19:32:02
 * @description
 */
@Data
@ApiModel("成绩分页查询")
public class PageDto {
    @ApiModelProperty(value = "页数",required = true)
    Integer page;
    @ApiModelProperty(value = "每页展示数量",required = true)
    Integer pageSize;
    @ApiModelProperty("成绩标题")
    String title;
}
