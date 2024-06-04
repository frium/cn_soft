package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-05-22 19:13:44
 * @description
 */
@Data
@ApiModel("用户登录")
public class LoginDto {
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "身份证或密码",required = true)
    private String username;
    @ApiModelProperty(value = "是否是老师",required = true)
    private Boolean isTeacher;

}
