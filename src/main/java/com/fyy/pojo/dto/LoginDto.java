package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @date 2024-05-22 19:13:44
 * @description
 */
@Data
@ApiModel("用户登录")
public class LoginDto {
    @NotEmpty(message = "密码不能为空")
    @Size(min = 8,max = 16,message = "密码的长度有误")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotEmpty(message = "用户名不能为空")
    @Size(max = 18, min = 11, message = "用户名长度有误")
    @ApiModelProperty(value = "身份证或手机号", required = true)
    private String username;

    @NotNull(message = "isTeacher选项不能为空")
    @ApiModelProperty(value = "是否是老师", required = true)
    private Boolean isTeacher;

}
