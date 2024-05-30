package com.fyy.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 *
 * @date 2024-05-28 14:06:49
 * @description
 */
@Data
public class StudentScoreVo {
    private String name;
    private String title;
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

    public static StudentScoreVo fromRowData(List<String> rowData,String title) {
        StudentScoreVo studentScore = new StudentScoreVo();
        // 根据 Excel 列的顺序设置属性值
        studentScore.setName(rowData.get(0));
        studentScore.setTitle(title);
        studentScore.setStudentId(Long.valueOf(rowData.get(1)));
        studentScore.setChinese(Float.parseFloat(rowData.get(2)));
        studentScore.setMath(Float.parseFloat(rowData.get(3)));
        studentScore.setEnglish(Float.parseFloat(rowData.get(4)));
        studentScore.setPhysics(Float.parseFloat(rowData.get(5)));
        studentScore.setChemistry(Float.parseFloat(rowData.get(6)));
        studentScore.setHistory(Float.parseFloat(rowData.get(7)));
        studentScore.setPolitics(Float.parseFloat(rowData.get(8)));
        studentScore.setGeography(Float.parseFloat(rowData.get(9)));
        studentScore.setBiology(Float.parseFloat(rowData.get(10)));

        return studentScore;
    }
}
