package com.fyy.pojo.dto;

import lombok.Data;

/**
 *
 * @date 2024-05-22 16:31:08
 * @description
 */
@Data
public class RegisterDto {
    private String password;
    private Long phone;
    private String personalId;
    private Boolean isTeacher;
    private String verify;
}
