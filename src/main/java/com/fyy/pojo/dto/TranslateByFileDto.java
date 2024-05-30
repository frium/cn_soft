package com.fyy.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @date 2024-05-29 16:19:06
 * @description
 */
@Data
public class TranslateByFileDto {
    private String from;
    private String to;
    private MultipartFile file;
}
