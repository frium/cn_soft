package com.fyy.pojo.dto;

import lombok.Data;

/**
 *
 * @date 2024-05-22 19:13:44
 * @description
 */
@Data
public class LoginDto {
    private String password;
    private String username;
    private Boolean isTeacher;

}
