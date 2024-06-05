package com.fyy.utils;

import com.fyy.pojo.entity.Score;
import com.fyy.pojo.vo.StudentScoreVo;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 *
 * @date 2024-05-29 16:50:30
 * @description
 */
public class ExcelUtil {
    /**
     * 解析 Excel 文件
     * @param file MultipartFile 形式的 Excel 文件
     * @return 二维字符串列表，包含 Excel 表格数据
     */
    public static List<Score> parseExcel(MultipartFile file) throws IOException {
        List<Score> studentScores = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> cells = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cells.add(getCellValueAsString(cell));
                }
                String fileName = file.getOriginalFilename();
                // 转换行数据为 StudentScoreVo 对象并添加到列表中
                if (fileName != null) {
                    studentScores.add(Score.fromRowData(cells, fileName.substring(0,fileName.lastIndexOf("."))));
                }else {
                    studentScores.add(Score.fromRowData(cells, UUID.randomUUID().toString().substring(0,30)));
                }
            }
        }
        return studentScores;
    }


    /**
     * 将单元格的值转换为字符串
     * @param cell 单元格对象
     * @return 单元格的值字符串表示
     */
    private static String getCellValueAsString(Cell cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue().toString();
                } else {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case BLANK:
                cellValue = "";
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    public static void writeStudentScoresToExcel(List<StudentScoreVo> studentScores, HttpServletResponse response) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(studentScores.get(0).getTitle());

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"姓名", "学号", "语文", "数学", "英语", "物理", "化学", "历史", "政治", "地理", "生物"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (StudentScoreVo studentScore : studentScores) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(studentScore.getName());
                row.createCell(1).setCellValue(studentScore.getStudentId());
                row.createCell(2).setCellValue(studentScore.getChinese());
                row.createCell(3).setCellValue(studentScore.getMath());
                row.createCell(4).setCellValue(studentScore.getEnglish());
                row.createCell(5).setCellValue(studentScore.getPhysics());
                row.createCell(6).setCellValue(studentScore.getChemistry());
                row.createCell(7).setCellValue(studentScore.getHistory());
                row.createCell(8).setCellValue(studentScore.getPolitics());
                row.createCell(9).setCellValue(studentScore.getGeography());
                row.createCell(10).setCellValue(studentScore.getBiology());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            // 设置响应头信息
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=student_scores.xlsx");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            // 将 Excel 写入响应输出流
            response.getOutputStream().write(outputStream.toByteArray());
            response.getOutputStream().flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
