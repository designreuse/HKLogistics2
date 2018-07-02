package com.hk.logistics.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hk.logistics.domain.AwbExcelPojo;
import com.hk.logistics.service.dto.AwbDTO;

@Component
public class AwbExcelUtil {

	@Value("${filepath:testawbs.xls}")
	private String filepath;
	
	public FileInputStream createExcel(List<AwbExcelPojo> list) throws IOException {

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {

			XSSFSheet sheet = workbook.createSheet("sheet1");// creating a blank sheet
			list.forEach(obj -> {
				int rownum = 0;
				Row row = sheet.createRow(rownum++);
				createRow(obj, row);
			});

			FileOutputStream out = new FileOutputStream(new File(filepath)); // file name with path
			workbook.write(out);
			
			FileInputStream file = new FileInputStream(filepath);
			
			return file;
		}
	}

	private void createRow(AwbExcelPojo dto, Row row) // creating cells for each row
	{
		Cell cell = row.createCell(0);
		cell.setCellValue(dto.getCourierId());

		cell = row.createCell(1);
		cell.setCellValue(dto.getAwbNumber());

	}
}
