package com.chensha.universalsurveysystem.utils;

import com.chensha.universalsurveysystem.vo.ExcelVo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {

    public static byte[] exportExcel(List<ExcelVo> data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            Row headerRow = sheet.createRow(0);
            // 设置表头
            headerRow.createCell(0).setCellValue("序号");
            headerRow.createCell(1).setCellValue("回答时间");
            headerRow.createCell(2).setCellValue("用户id");
            headerRow.createCell(3).setCellValue("用户名");
            if (data.size() > 0) {
                ExcelVo excelVo = data.get(0);
                List<String> questions = excelVo.getQuestions();
                for (int i = 0; i < questions.size(); i++) {
                    headerRow.createCell(i + 4).setCellValue(questions.get(i));
                }
            }

            // 设置数据行
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Object obj = data.get(i);
                // 设置每个单元格的值
                row.createCell(0).setCellValue(data.get(i).getIndex());
                row.createCell(1).setCellValue(data.get(i).getUpdateTime());
                row.createCell(2).setCellValue(data.get(i).getUserId());
                row.createCell(3).setCellValue(data.get(i).getUserName());
                for(int j = 0; j < data.get(i).getAnswers().size(); j++){
                    row.createCell(j + 4).setCellValue(data.get(i).getAnswers().get(j));
                }
            }
            workbook.write(outputStream);
        }
        return outputStream.toByteArray();
    }
}
