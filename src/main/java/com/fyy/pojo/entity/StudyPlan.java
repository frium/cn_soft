package com.fyy.pojo.entity;

import lombok.Data;

/**
 *
 * @date 2024-06-04 14:44:25
 * @description
 */
@Data
public class StudyPlan {
    Integer id;
    String studentId;
    String plan;
    String createTime;
}
