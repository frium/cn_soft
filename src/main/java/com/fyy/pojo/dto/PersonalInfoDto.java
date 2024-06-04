package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-05-28 14:08:53
 * @description
 */
@Data
@ApiModel("个人主页信息")
public class PersonalInfoDto {
    @ApiModelProperty(value = "性别",required = true)
    private String sex;
    @ApiModelProperty(value = "姓名",required = true)
    private String name;
    @ApiModelProperty(value = "电话",required = true)
    private Long phone;
    @ApiModelProperty(value = "身份证",required = true)
    private String personalId;
}
