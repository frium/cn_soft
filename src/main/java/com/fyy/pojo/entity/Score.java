package com.fyy.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @date 2024-05-16 17:41:17
 * @description
 */
@Data
public class Score implements Serializable {
    private Long id;
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
    private String createTime;
    private String updateTime;
    private String title;
}
