package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @date 2024-05-28 21:45:59
 * @description
 */


@Data
@ApiModel("忘记密码")
public class ForgetPasswordDTO {

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^.{11}$",message = "手机号长度有误")
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    @NotEmpty(message = "身份证不能为空")
    @Pattern(regexp = "^.{18}$",message = "身份证长度有误")
    @ApiModelProperty(value = "身份证",required = true)
    private String personalId;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 8,max = 16,message = "密码的长度有误")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
