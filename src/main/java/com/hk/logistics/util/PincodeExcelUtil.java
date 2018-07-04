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
import com.hk.logistics.service.dto.PincodeDTO;

@Component
public class PincodeExcelUtil {

	@Value("${filepath:testpincodes.xls}")
	private String filepath;
	
	public FileInputStream createExcel(List<PincodeDTO> list) throws IOException {

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {

			XSSFSheet sheet = workbook.createSheet("sheet1");// creating a blank sheet
			int rowNum = 0;
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(0);
			cell.setCellValue("PINCODE");
			Cell cell1 = row.createCell(1);
			cell1.setCellValue("CITY");
			Cell cell2 = row.createCell(2);
			cell2.setCellValue("STATE");
			Cell cell3 = row.createCell(3);
			cell3.setCellValue("LOCALITY");
			Cell cell4 = row.createCell(4);
			cell4.setCellValue("ZONE");
			Cell cell5 = row.createCell(5);
			cell5.setCellValue("NEAREST_HUB");
			Cell cell6 = row.createCell(6);
			cell6.setCellValue("CONVEYANCE_COST");
		
			for(PincodeDTO dto: list ) {
				row = sheet.createRow(rowNum++);
				createRow(dto, row);
			};

			FileOutputStream out = new FileOutputStream(new File(filepath)); // file name with path
			workbook.write(out);
			
			FileInputStream file = new FileInputStream(filepath);
			
			return file;
		}
	}

	private void createRow(PincodeDTO dto, Row row) // creating cells for each row
	{
		Cell cell = row.createCell(0);
		if(dto.getPincode() != null)
		cell.setCellValue(dto.getPincode());

		Cell cell1 = row.createCell(1);
		if(dto.getCityName()!=null)
		cell1.setCellValue(dto.getCityName());
		
		Cell cell2 = row.createCell(2);
		if(dto.getStateName()!=null)
		cell2.setCellValue(dto.getStateName());
		
		Cell cell3 = row.createCell(3);
		if(dto.getLocality()!=null)
		cell3.setCellValue(dto.getLocality());
		
		Cell cell4 = row.createCell(4);
		if(dto.getLocality()!=null)
		cell4.setCellValue(dto.getZoneName());
		
		Cell cell5 = row.createCell(5);
		if(dto.getHubName()!=null)
		cell5.setCellValue(dto.getHubName());
		
		Cell cell6 = row.createCell(6);
		if(dto.getLastMileCost()!=null)
		cell6.setCellValue(dto.getLastMileCost());

	}
}
