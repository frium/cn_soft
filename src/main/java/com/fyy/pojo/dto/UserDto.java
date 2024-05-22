package com.fyy.pojo.dto;

import lombok.Data;

/**
 *
 * @date 2024-05-22 16:31:08
 * @description
 */
@Data
public class UserDto {
    private String sex;
    private String name;
    private String password;
    private Long phone;
    private String personalId;
}
