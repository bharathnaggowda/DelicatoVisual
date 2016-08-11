package com.delicato.visual.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.bson.Document;

import com.delicato.visual.config.SpringMongoConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CompareData {

	CsvService csvService = new CsvService();
	
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	MongoClient mongoClient=mongoConfig.getMongoClient();
	MongoDatabase db = mongoClient.getDatabase("delicato");
	MongoCollection<Document> cimisCollection = db.getCollection("cimisdata");
	MongoCollection<Document> dyostemCollectionnew = db.getCollection("dyostemdatanew");
	MongoCollection<Document> ratingCollection = db.getCollection("VintageRating");
	
	String rootPath =System.getProperty("user.dir");
	String filePath = rootPath+"/src/main/webapp/";
	
    File file;
    
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    
	public void generateIndividualGraphs(String block, String sDate, String eDate, String year, final String newFile) {
		
		final String tapBrixXls = "tapBrix"+newFile+".xls";
		final File tapBrixXlsFile = new File(filePath+tapBrixXls);
		final String tapBrixCsv = "tapBrix"+newFile+".csv";
		final File tapBrixCsvFile = new File(filePath+tapBrixCsv);
		final String sugQuantXls = "sugQuant"+newFile+".xls";
		final File sugQuantXlsFile = new File(filePath+sugQuantXls);
		final String sugQuantCsv = "sugQuant"+newFile+".csv";
		final File sugQuantCsvFile = new File(filePath+sugQuantCsv);
		final String phXls = "ph"+newFile+".xls";
		final File phXlsFile = new File(filePath+phXls);
		final String phCsv = "ph"+newFile+".csv";
		final File phCsvFile = new File(filePath+phCsv);
	    
		if(tapBrixXlsFile.exists()){
			tapBrixXlsFile.delete();
		}
		if(tapBrixCsvFile.exists()){
			tapBrixCsvFile.delete();
		}
		if(sugQuantXlsFile.exists()){
			sugQuantXlsFile.delete();
		}
		if(sugQuantCsvFile.exists()){
			sugQuantCsvFile.delete();
		}
		if(phXlsFile.exists()){
			phXlsFile.delete();
		}
		if(phCsvFile.exists()){
			phCsvFile.delete();
		}
		
		BasicDBObject getQuery = new BasicDBObject();
		getQuery.put("Name of block", block);
	    try {
			getQuery.put("Date of Analysis", new BasicDBObject("$gte", format.parse(sDate)).append("$lte", format.parse(eDate)));
			
			System.out.println("dates--------->"+getQuery);
			FindIterable<Document> cursor = dyostemCollectionnew.find(getQuery).sort(new Document("_id",1));
			//if(!cursor.first().isEmpty()){
				cursor.forEach(new Block<Document>() {
					public void apply(Document document) {
						System.out.println("vvvvvvvvvvvvvvvvvvvvvv------------->"+document);
						 
						Date dateOfAnalysis = null;
						Double tapBrix = null;
						Double sugQuant = null;
						Double ph = null;
						 if(document.get("Date of Analysis") != null && document.get("Date of Analysis") != ""){
								 dateOfAnalysis = (Date) document.get("Date of Analysis");
							}
						 System.out.println("dateOfAnalysis->"+dateOfAnalysis);
						 if(document.get("TAP Brix") != null && document.get("TAP Brix") != ""){
								if(document.get("TAP Brix") instanceof Double)
									tapBrix =  (Double) document.get("TAP Brix");
								else if(document.get("TAP Brix") instanceof Integer)
									tapBrix =  (double) (Integer) document.get("TAP Brix");
							}
						 
						 if(document.get("Sugar quantity") != null && document.get("Sugar quantity") != ""){
								
								if(document.get("Sugar quantity") instanceof Double)
									sugQuant =  (Double) document.get("Sugar quantity");
								else if(document.get("Sugar quantity") instanceof Integer)
									sugQuant =  (double) (Integer) document.get("Sugar quantity");
							}
						 if(document.get("pH") != null && document.get("pH") != ""){
								
								if(document.get("pH") instanceof Double)
									ph =  (Double) document.get("pH");
								else if(document.get("pH") instanceof Integer)
									ph =  (double) (Integer) document.get("pH");
							}
						 createXls(dateOfAnalysis, tapBrix, tapBrixXls);
						 createXls(dateOfAnalysis, sugQuant, sugQuantXls);
						 createXls(dateOfAnalysis, ph, phXls);
						 csvService.xlsToCsv(tapBrixXlsFile, tapBrixCsvFile);
						 csvService.xlsToCsv(sugQuantXlsFile, sugQuantCsvFile);
						 csvService.xlsToCsv(phXlsFile, phCsvFile);
					}					
				});
			//}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	}
	
	private void createXls(Date dateOfAnalysis, Double tapBrix, String extendedName) {

		file = new File(filePath+extendedName);
		try {
			HSSFWorkbook wb = null;
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell1 = null;
			HSSFCell cell2 = null;
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
				 cell2.setCellValue("Tap Brix");
			} 
			
			row = sheet.createRow(sheet.getLastRowNum()+1);
			cell1 = row.createCell(0);
			cell2 = row.createCell(1);
			String d = format.format(dateOfAnalysis);
			String[] dates = d.split("/");
			String date = "1980-"+dates[0]+"-"+dates[1];
			cell1.setCellType(Cell.CELL_TYPE_STRING);
			cell1.setCellValue(date);
			
			cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell2.setCellValue(tapBrix);
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
		}  catch (IOException e) {
			e.printStackTrace();
	}

	}
}
