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

	public void writeToXls(String date, Double degree )
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
				
				cell1.setCellValue(date);
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
	
	public void writeToXlsCimisFirstGraph(String date, double airtemp, double dewpoint, double windspeed, double vappres, double soiltemp )
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
				HSSFCell cell6 = null;
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
					 cell4.setCellValue("windspeed");
					 cell5 = row.createCell(4);
					 cell5.setCellValue("vappres");
					 cell6 = row.createCell(5);
					 cell6.setCellValue("soiltemp");
				} 
				
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell1 = row.createCell(0);
				cell2 = row.createCell(1);
				cell3 = row.createCell(2);
				cell4 = row.createCell(3);
				cell5 = row.createCell(4);
				cell6 = row.createCell(5);
				//System.out.println("---------------->"+date);
				HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
				
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(
				    createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				cell1.setCellValue(date);
				cell1.setCellStyle(cellStyle);
				
				cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell2.setCellValue(airtemp);
				System.out.println("air------------------->"+airtemp);
				cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell3.setCellValue(dewpoint);
				System.out.println("dew------------------->"+dewpoint);
				cell4.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell4.setCellValue(windspeed);
				System.out.println("wind------------------->"+windspeed);
				cell5.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell5.setCellValue(vappres);
				System.out.println("vap------------------->"+vappres);
				cell6.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell6.setCellValue(soiltemp);
				System.out.println("soil------------------->"+soiltemp);
				// Write the output to a file
				FileOutputStream fileOut = new FileOutputStream(filePath+"cimis1.xls");
				wb.write(fileOut);
				fileOut.close();
			}  catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public void writeToXlsCimisSecGraph(String date, double relhum, double winddir )
	{
			try {
				
				HSSFWorkbook wb = null;
				HSSFSheet sheet = null;
				HSSFRow row = null;
				HSSFCell cell1 = null;
				HSSFCell cell2 = null;
				HSSFCell cell3 = null;
				
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
					 cell2.setCellValue("relhum");
					 
					 cell3 = row.createCell(4);
					 cell3.setCellValue("winddir");
				} 
				
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell1 = row.createCell(0);
				cell2 = row.createCell(1);
				cell3 = row.createCell(2);
				
				HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
				
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(
				createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				cell1.setCellValue(date);
				cell1.setCellStyle(cellStyle);
				
				cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell2.setCellValue(relhum);
				System.out.println("rel------------------->"+relhum);
				
				cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell3.setCellValue(winddir);
				System.out.println("dir------------------->"+winddir);
				FileOutputStream fileOut = new FileOutputStream(filePath+"cimis3.xls");
				wb.write(fileOut);
				fileOut.close();
			}  catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public void writeToCsv2(String date, double airtemp, double dewpoint, double windspeed, double vappres, double soiltemp)
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
				HSSFCell cell6 = null;
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
					 cell4.setCellValue("windspeed");
					 cell5 = row.createCell(4);
					 cell5.setCellValue("vappres");
					 cell6 = row.createCell(5);
					 cell6.setCellValue("soiltemp");
				} 
				
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell1 = row.createCell(0);
				cell2 = row.createCell(1);
				cell3 = row.createCell(2);
				cell4 = row.createCell(3);
				cell5 = row.createCell(4);
				cell6=  row.createCell(5);
				//HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
				
				//CreationHelper createHelper = wb.getCreationHelper();
				//cellStyle.setDataFormat(
				    //createHelper.createDataFormat().getFormat("##"));
				int hour = cell1.getRowIndex();
				cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell1.setCellValue(hour);
				//cell1.setCellStyle(cellStyle);
				
				cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell2.setCellValue(airtemp);
				System.out.println("------------------->"+airtemp);
				cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell3.setCellValue(dewpoint);
				System.out.println("------------------->"+dewpoint);
				cell4.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell4.setCellValue(windspeed);
				System.out.println("------------------->"+windspeed);
				cell5.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell5.setCellValue(vappres);
				System.out.println("------------------->"+vappres);
				cell6.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell6.setCellValue(soiltemp);
				System.out.println("------------------->"+soiltemp);
				FileOutputStream fileOut = new FileOutputStream(filePath+"cimis2.xls");
				wb.write(fileOut);
				fileOut.close();
			}  catch (IOException e) {
				e.printStackTrace();
			}
		  }
	public void writeToCsv(String date, double relhum, double winddir)
	{
			try {
				System.out.println("inside csv--->");
				
				HSSFWorkbook wb = null;
				HSSFSheet sheet = null;
				HSSFRow row = null;
				HSSFCell cell1 = null;
				HSSFCell cell2 = null;
				HSSFCell cell3 = null;
				
				File file = new File(filePath+"cimis4.xls");
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
					 cell2.setCellValue("relhum");
					 
					 cell3 = row.createCell(2);
					 cell3.setCellValue("winddir");
				} 
				
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell1 = row.createCell(0);
				cell2 = row.createCell(1);
				cell3 = row.createCell(2);
				
				//HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
				
				//CreationHelper createHelper = wb.getCreationHelper();
				//cellStyle.setDataFormat(
				    //createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				cell1.setCellValue(cell1.getRowIndex());
				//cell1.setCellStyle(cellStyle);
				
				cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell2.setCellValue(relhum);
				System.out.println("------------------->"+relhum);
				
				cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell3.setCellValue(winddir);
				System.out.println("------------------->"+winddir);
				FileOutputStream fileOut = new FileOutputStream(filePath+"cimis4.xls");
				wb.write(fileOut);
				fileOut.close();
			}  catch (IOException e) {
				e.printStackTrace();
			}
		  }
}
