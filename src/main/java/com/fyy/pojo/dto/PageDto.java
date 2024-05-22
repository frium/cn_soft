package com.fyy.pojo.dto;

import lombok.Data;

/**
 *
 * @date 2024-05-22 19:32:02
 * @description
 */
@Data
public class PageDto {
    String id;
    int page;
    int pageSize;
    String title;
}
