package com.fyy.pojo.dto;

import lombok.Data;

/**
 *
 * @date 2024-05-22 20:32:06
 * @description
 */
@Data
public class ScoreDto {
    private Long studentId;
    private Integer chinese;
    private Integer math;
    private Integer english;
    private Integer physics;
    private Integer chemistry;
    private Integer history;
    private Integer politics;
    private Integer geography;
    private Integer biology;
    private String title;
}
