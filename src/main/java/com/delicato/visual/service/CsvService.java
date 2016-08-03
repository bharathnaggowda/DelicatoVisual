package com.delicato.visual.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CsvService {

	public void xlsToCsvDegreeDays(File inputFile, File outputFile) 
	{
		   try {
			   StringBuffer data = new StringBuffer();
				FileInputStream input_document = new FileInputStream(inputFile);
		        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document);
		        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
		        Iterator<Row> rowIterator = my_worksheet.iterator();
		        FileOutputStream fos = new FileOutputStream(outputFile);
		        double sum = 0;
		        while(rowIterator.hasNext()) {
		                Row row = rowIterator.next(); 
		                int i=0;
		                Iterator<Cell> cellIterator = row.cellIterator();
		                        while(cellIterator.hasNext()) {
		                                Cell cell = cellIterator.next(); 
		                                switch(cell.getCellType()) { 
		                                        
		                                case Cell.CELL_TYPE_STRING:
		                                	data.append(cell.getStringCellValue());                                              
		                                        break;
		                                case Cell.CELL_TYPE_NUMERIC:
		                                	if(cell.getCellStyle().getDataFormatString().equals("yyyy-MM-dd")){
		                                		data.append(cell.getDateCellValue());
		                                	}else{
		                                		sum += cell.getNumericCellValue();
		                                		data.append(sum);
		                                	}
		                                	                                              
	                                        break;
		                                }
		                                data.append(',');
		                                i=i+1;
		                        }
		                        data.append('\n');
		        }
		        fos.write(data.toString().getBytes());
		        fos.close();
		        input_document.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void xlsToCsv(File inputFile, File outputFile) 
	{
		   try {
			   StringBuffer data = new StringBuffer();
				FileInputStream input_document = new FileInputStream(inputFile);
		        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document);
		        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
		        Iterator<Row> rowIterator = my_worksheet.iterator();
		        FileOutputStream fos = new FileOutputStream(outputFile);
		        while(rowIterator.hasNext()) {
		                Row row = rowIterator.next(); 
		                int i=0;
		                Iterator<Cell> cellIterator = row.cellIterator();
		                        while(cellIterator.hasNext()) {
		                                Cell cell = (cellIterator.next()); 
		                                 
		                                switch(cell.getCellType()) { 
		                                case Cell.CELL_TYPE_STRING:
		                                	data.append(cell.getStringCellValue());                                              
		                                        break;
		                                case Cell.CELL_TYPE_NUMERIC:
		                                	
		                                	if(cell.getCellStyle().getDataFormatString().equals("yyyy-MM-dd")){
		                                		data.append(cell.getDateCellValue());
		                                	}else{
		                                		data.append(cell.getNumericCellValue());
		                                	}
		                                	                                              
	                                        break;
		                                }
		                                
		                                data.append(',');
		                             
		                                i=i+1;
		                                
		                        }
		                        data.append('\n');
		        }
		        fos.write(data.toString().getBytes());
		        fos.close();
		        
				input_document.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
