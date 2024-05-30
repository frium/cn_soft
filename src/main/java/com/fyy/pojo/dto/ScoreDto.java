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
    private Float chinese;
    private Float math;
    private Float english;
    private Float physics;
    private Float chemistry;
    private Float history;
    private Float politics;
    private Float geography;
    private Float biology;
    private String title;
}
