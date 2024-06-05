package com.fyy.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @date 2024-05-16 17:41:17
 * @description
 */
@Data
public class Score implements Serializable {
    private Long id;
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
    private String createTime;
    private String updateTime;
    private String title;

    public static Score fromRowData(List<String> rowData, String title) {
        Score studentScore = new Score();
        studentScore.setTitle(title);
        studentScore.setStudentId((long)Double.parseDouble(rowData.get(1)));
        studentScore.setChinese(Float.parseFloat(rowData.get(2)));
        studentScore.setMath(Float.parseFloat(rowData.get(3)));
        studentScore.setEnglish(Float.parseFloat(rowData.get(4)));
        studentScore.setPhysics(Float.parseFloat(rowData.get(5)));
        studentScore.setChemistry(Float.parseFloat(rowData.get(6)));
        studentScore.setHistory(Float.parseFloat(rowData.get(7)));
        studentScore.setPolitics(Float.parseFloat(rowData.get(8)));
        studentScore.setGeography(Float.parseFloat(rowData.get(9)));
        studentScore.setBiology(Float.parseFloat(rowData.get(10)));
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        studentScore.setCreateTime(formattedDateTime);
        studentScore.setUpdateTime(formattedDateTime);
        return studentScore;
    }
}
