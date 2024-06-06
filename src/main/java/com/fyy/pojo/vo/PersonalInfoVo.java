package com.fyy.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @date 2024-05-28 15:29:50
 * @description
 */
@Data
@ApiModel("展示个人信息")
public class PersonalInfoVo {
    @ApiModelProperty("教师名称")
    private String teacherName;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("学生姓名")
    private String name;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("身份证")
    private String personalId;
    @ApiModelProperty("学号")
    private String studentId;
}
