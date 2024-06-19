package com.fyy.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-06-04 15:04:08
 * @description
 */
@Data
@ApiModel("学习计划展示")
public class StudyPlanVO {
    @ApiModelProperty("历史生成的计划内容")
    String plan;
    @ApiModelProperty("标题")
    String title;
    @ApiModelProperty("生成的时间")
    String createTime;
    @ApiModelProperty("完成状态")
    boolean status;
}
