package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @date 2024-05-22 19:32:02
 * @description
 */
@Data
@ApiModel("成绩分页查询")
public class PageDTO {

    @NotEmpty(message = "最小从第一页展示")
    @ApiModelProperty(value = "页数",required = true)
    Integer page;

    @Min(value = 1,message = "最少每页展示一条数据")
    @ApiModelProperty(value = "每页展示数量",required = true)
    Integer pageSize;

    @ApiModelProperty("成绩标题")
    String title;
}
