package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-05-22 16:31:08
 * @description
 */
@Data
public class RegisterDto {

    @ApiModelProperty(value = "密码",required = true,dataType = "String")
    private String password;
    @ApiModelProperty(value = "手机",required = true,dataType = "Long")
    private Long phone;
    @ApiModelProperty(value = "身份证",required = true,dataType = "String")
    private String personalId;
    @ApiModelProperty(value = "是否是老师",required = true,dataType = "Boolean")
    private Boolean isTeacher;
    @ApiModelProperty(value = "验证码",required = true,dataType = "String")
    private String verify;
}
