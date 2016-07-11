package com.delicato.visual.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;

public class ExcelService {
	
	String rootPath =System.getProperty("user.dir");
	String filePath = rootPath+"/src/main/webapp/";

	public void writeToXls(Date date, Double degree )
	{
			try {
				System.out.println("inside csv--->");
				
				HSSFWorkbook wb = null;
				HSSFSheet sheet = null;
				HSSFRow row = null;
				HSSFCell cell1 = null;
				HSSFCell cell2 = null;
				File file = new File(filePath+"degreedays.xls");
				if(file.exists()){
					InputStream inp = new FileInputStream(file);
					 
					wb = new HSSFWorkbook(inp);
					sheet = wb.getSheetAt(0);
					
				}else{
					wb = new HSSFWorkbook();
			         sheet = wb.createSheet();
			         row = sheet.createRow(0);
					 cell1 = row.createCell(0);
					 cell1.setCellValue("Date");
					 cell2 = row.createCell(1);
					 cell2.setCellValue("Degree");
				} 
				
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell1 = row.createCell(0);
				cell2 = row.createCell(1);
				HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
				
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(
				createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				
				cell1.setCellValue((Date) date);
				cell1.setCellStyle(cellStyle);
				
				cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell2.setCellValue(degree);
				FileOutputStream fileOut = new FileOutputStream(filePath+"degreedays.xls");
				wb.write(fileOut);
				fileOut.close();
			}  catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public void writeToXlsCimisFirstGraph(Date date, double airtemp, double dewpoint, double relhum, double soiltemp )
	{
			try {
				HSSFWorkbook wb = null;
				HSSFSheet sheet = null;
				HSSFRow row = null;
				HSSFCell cell1 = null;
				HSSFCell cell2 = null;
				HSSFCell cell3 = null;
				HSSFCell cell4 = null;
				HSSFCell cell5 = null;
				File file = new File(filePath+"cimis1.xls");
				if(file.exists()){
					InputStream inp = new FileInputStream(file);
					 
						wb = new HSSFWorkbook(inp);
					sheet = wb.getSheetAt(0);
					
					
				}else{
					wb = new HSSFWorkbook();
			         sheet = wb.createSheet();
			         row = sheet.createRow(0);
					 cell1 = row.createCell(0);
					 cell1.setCellValue("Date");
					 cell2 = row.createCell(1);
					 cell2.setCellValue("airtemp");
					 cell3 = row.createCell(2);
					 cell3.setCellValue("dewpoint");
					 cell4 = row.createCell(3);
					 cell4.setCellValue("relhum");
					 cell5 = row.createCell(4);
					 cell5.setCellValue("soiltemp");
				} 
				
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell1 = row.createCell(0);
				cell2 = row.createCell(1);
				cell3 = row.createCell(2);
				cell4 = row.createCell(3);
				cell5 = row.createCell(4);
				//System.out.println("---------------->"+date);
				HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
				
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(
				    createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				cell1.setCellValue((Date) date);
				cell1.setCellStyle(cellStyle);
				
				cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell2.setCellValue(airtemp);
				System.out.println("------------------->"+airtemp);
				cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell3.setCellValue(dewpoint);
				System.out.println("------------------->"+dewpoint);
				cell4.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell4.setCellValue(relhum);
				System.out.println("------------------->"+relhum);
				cell5.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell5.setCellValue(soiltemp);
				System.out.println("------------------->"+soiltemp);
				// Write the output to a file
				FileOutputStream fileOut = new FileOutputStream(filePath+"cimis1.xls");
				wb.write(fileOut);
				fileOut.close();
			}  catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public void writeToXlsCimisSecGraph(Date date, double solrad, double vappres, double windspeed, double winddir )
	{
			try {
				
				HSSFWorkbook wb = null;
				HSSFSheet sheet = null;
				HSSFRow row = null;
				HSSFCell cell1 = null;
				HSSFCell cell2 = null;
				HSSFCell cell3 = null;
				HSSFCell cell4 = null;
				HSSFCell cell5 = null;
				File file = new File(filePath+"cimis3.xls");
				if(file.exists()){
					InputStream inp = new FileInputStream(file);
					 
					wb = new HSSFWorkbook(inp);
					sheet = wb.getSheetAt(0);
					
				}else{
					wb = new HSSFWorkbook();
			         sheet = wb.createSheet();
			         row = sheet.createRow(0);
					 cell1 = row.createCell(0);
					 cell1.setCellValue("Date");
					 cell2 = row.createCell(1);
					 cell2.setCellValue("solrad");
					 cell3 = row.createCell(2);
					 cell3.setCellValue("vappres");
					 cell4 = row.createCell(3);
					 cell4.setCellValue("windspeed");
					 cell5 = row.createCell(4);
					 cell5.setCellValue("winddir");
				} 
				
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell1 = row.createCell(0);
				cell2 = row.createCell(1);
				cell3 = row.createCell(2);
				cell4 = row.createCell(3);
				cell5 = row.createCell(4);
				HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
				
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(
				createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				cell1.setCellValue((Date) date);
				cell1.setCellStyle(cellStyle);
				
				cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell2.setCellValue(solrad);
				System.out.println("------------------->"+solrad);
				cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell3.setCellValue(vappres);
				System.out.println("------------------->"+vappres);
				cell4.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell4.setCellValue(windspeed);
				System.out.println("------------------->"+windspeed);
				cell5.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell5.setCellValue(winddir);
				System.out.println("------------------->"+winddir);
				FileOutputStream fileOut = new FileOutputStream(filePath+"cimis3.xls");
				wb.write(fileOut);
				fileOut.close();
			}  catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public void writeToCsv2(Date date, double airtemp, double dewpoint, double relhum, double soiltemp)
	{
			try {
				System.out.println("inside csv--->");
				
				HSSFWorkbook wb = null;
				HSSFSheet sheet = null;
				HSSFRow row = null;
				HSSFCell cell1 = null;
				HSSFCell cell2 = null;
				HSSFCell cell3 = null;
				HSSFCell cell4 = null;
				HSSFCell cell5 = null;
				File file = new File(filePath+"cimis2.xls");
				if(file.exists()){
					InputStream inp = new FileInputStream(file);
					 
					wb = new HSSFWorkbook(inp);
					sheet = wb.getSheetAt(0);
					
					
				}else{
					wb = new HSSFWorkbook();
			         sheet = wb.createSheet();
			         row = sheet.createRow(0);
					 cell1 = row.createCell(0);
					 cell1.setCellValue("Date");
					 cell2 = row.createCell(1);
					 cell2.setCellValue("airtemp");
					 cell3 = row.createCell(2);
					 cell3.setCellValue("dewpoint");
					 cell4 = row.createCell(3);
					 cell4.setCellValue("relhum");
					 cell5 = row.createCell(4);
					 cell5.setCellValue("soiltemp");
				} 
				
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell1 = row.createCell(0);
				cell2 = row.createCell(1);
				cell3 = row.createCell(2);
				cell4 = row.createCell(3);
				cell5 = row.createCell(4);
				HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
				
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(
				    createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				cell1.setCellValue((Date) date);
				cell1.setCellStyle(cellStyle);
				
				cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell2.setCellValue(airtemp);
				System.out.println("------------------->"+airtemp);
				cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell3.setCellValue(dewpoint);
				System.out.println("------------------->"+dewpoint);
				cell4.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell4.setCellValue(relhum);
				System.out.println("------------------->"+relhum);
				cell5.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell5.setCellValue(soiltemp);
				System.out.println("------------------->"+soiltemp);
				FileOutputStream fileOut = new FileOutputStream(filePath+"cimis2.xls");
				wb.write(fileOut);
				fileOut.close();
			}  catch (IOException e) {
				e.printStackTrace();
			}
		  }
}
