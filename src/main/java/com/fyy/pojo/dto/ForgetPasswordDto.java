package com.fyy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-05-28 21:45:59
 * @description
 */
@Data
@ApiModel("忘记密码")
public class ForgetPasswordDto {
    @ApiModelProperty(value = "手机号",required = true)
    private Long phone;
    @ApiModelProperty(value = "身份证",required = true)
    private String personalId;
}
