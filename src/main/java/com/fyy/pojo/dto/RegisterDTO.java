package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @date 2024-05-22 16:31:08
 * @description
 */
@Data
public class RegisterDTO {

    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 16, message = "密码的长度有误")
    @ApiModelProperty(value = "密码", required = true, dataType = "String")
    private String password;

    @NotEmpty(message = "手机号不能为空 ")
    @Pattern(regexp = "^.{11}$",message = "手机号长度有误 ")
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    @NotEmpty(message = "身份证不能为空 ")
    @Pattern(regexp = "^.{18}$",message = "身份证长度有误 ")
    @ApiModelProperty(value = "身份证",required = true)
    private String personalId;

    @NotNull(message = "是否为老师不能为空 ")
    @ApiModelProperty(value = "是否是老师", required = true, dataType = "Boolean")
    private Boolean isTeacher;

    @NotEmpty(message = "验证码不能为空 ")
    @ApiModelProperty(value = "验证码", required = true, dataType = "String")
    private String verify;
}
