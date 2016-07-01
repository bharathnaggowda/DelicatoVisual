package com.delicato.visual.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.delicato.visual.config.SpringMongoConfig;
import com.delicato.visual.model.Factors;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Controller
@RequestMapping("/")
public class VisualController {
	
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	MongoClient mongoClient=mongoConfig.getMongoClient();
	MongoDatabase db = mongoClient.getDatabase("delicato");
	MongoCollection<Document> cimisCollection = db.getCollection("cimisdata");
	
	String rootPath =System.getProperty("user.dir");
	
	@RequestMapping(value="/degreedays", method=RequestMethod.POST)
	public String degreedays(Factors factors, Model model) 
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		Date sDate = null;
		Date eDate = null;
		int minTempTheshold = Integer.MIN_VALUE;
		int maxTempTheshold = Integer.MAX_VALUE;
		int minHumTheshold = Integer.MIN_VALUE;
		int maxWindTheshold = Integer.MAX_VALUE;
		
		try {
			sDate = format.parse(factors.getStartdate());
			eDate = format.parse(factors.getEnddate());
			if(factors.getMinTempTheshold() != "") minTempTheshold = Integer.parseInt(factors.getMinTempTheshold());
			if(factors.getMaxTempTheshold() != "") maxTempTheshold = Integer.parseInt(factors.getMaxTempTheshold());
			if(factors.getMinHumTheshold() != "") minHumTheshold = Integer.parseInt(factors.getMinHumTheshold());
			if(factors.getMaxWindTheshold() != "") maxWindTheshold = Integer.parseInt(factors.getMaxWindTheshold());
			
			System.out.println(sDate+"--");
			
			getCimisData(sDate, eDate, minTempTheshold, maxTempTheshold, minHumTheshold, maxWindTheshold);
			
			model.addAttribute("startdate", factors.getStartdate());
			model.addAttribute("enddate", factors.getEnddate());
			model.addAttribute("minTempTheshold", minTempTheshold);
			model.addAttribute("maxTempTheshold", maxTempTheshold);
			model.addAttribute("minHumTheshold", minHumTheshold);
			model.addAttribute("maxWindTheshold", maxWindTheshold);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "index";
	}
	
	@RequestMapping(value="/dyostem", method=RequestMethod.POST)
	public String dyostem(Factors factors, Model model) 
	{
		System.out.println(factors.getStartdate()+"---"+factors.getEnddate());
		
		return "index";
	}
	
public void getCimisData(Date sDate, Date eDate, int minTempTheshold, int maxTempTheshold, int minWindTheshold, int maxWindTheshold){
		
		//String month = m.toString();
		//String day = d.toString();
		//if(month.length() == 1) month = "0"+month;
		//if(day.length() == 1) day = "0"+day;
		//final String date = year+"/"+month+"/"+day;
		//Document doc = new Document();
		//doc.append("_id","{$gt: sDate, $lt: eDate}");
	
	File inputFile = new File(rootPath+"/src/main/webapp/data.xls");
    File outputFile = new File(rootPath+"/src/main/webapp/data.csv");
		if(inputFile.exists()){
			inputFile.delete();
		}
		if(outputFile.exists()){
			outputFile.delete();
		}
		
		
		BasicDBObject getQuery = new BasicDBObject();
	    getQuery.put("_id", new BasicDBObject("$gt", sDate).append("$lt", eDate));
		
		final int lThreshold = ((minTempTheshold - 32) * 5) / 9;
		final int hThreshold = ((maxTempTheshold - 32) * 5) / 9;
		final Double maxWindThresh = (double) maxWindTheshold;
		final Double minHumThresh = (double) minWindTheshold;
		
		//System.out.println(lThreshold+"-"+hThreshold);
		FindIterable<Document> cursor = cimisCollection.find(getQuery);
		
		cursor.forEach(new Block<Document>() {
			
		    public void apply(Document document) {
		        System.out.println("inside doc");
		        String date = "";
		        Document a = (Document) document.get("hourlydata");
		        ArrayList contents = (ArrayList) a.get("Records");
		        double degree = 0;
		        int daysCount = 0;
		        for(int i=0; i<contents.size(); i++){
		        	//System.out.println(contents.get(i));
			        Document doc = (Document) contents.get(i);
			        Document temp = (Document) doc.get("HlyAirTmp");
			        Document windSpeed = (Document) doc.get("HlyWindSpd");
			        
			        Document humidity = (Document) doc.get("HlyRelHum");
			        
			        date = (String) doc.get("Date");
			        System.out.println("Wind--> "+windSpeed.get("Value")+"--"+"Humidity--> "+humidity.get("Value"));
			        if(temp.get("Value") != null && temp.get("Value") != "" &&
			        		windSpeed.get("Value") != null && windSpeed.get("Value") != "" &&
			        				humidity.get("Value") != null && humidity.get("Value") != ""){
			        	
			        	Double value = Double.parseDouble((String) temp.get("Value"));
			        	Double windSpeedValue = Double.parseDouble((String) windSpeed.get("Value"));
			        	Double humidityValue = Double.parseDouble((String) humidity.get("Value"));
			        	
			        	if((value < lThreshold) || (windSpeedValue > maxWindThresh && humidityValue < minHumThresh)){
			        		daysCount++;
			        	}else if(value > lThreshold && value < hThreshold){
				        	degree = degree + (value+32)*9/5;
				        	daysCount++;
				        }else if(value > hThreshold){
				        	degree = degree + (hThreshold+32)*9/5;
				        	daysCount++;
				        }
			        }
		        }
		        degree = degree/daysCount;
		        System.out.println("daysCount----->"+daysCount);
		        System.out.println(degree);
		        //TreeMap<String, Double> map = new TreeMap<String, Double>();
		        //map.put(date, degree);
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date d = null;
		        try {
					d = dateFormat.parse(date);
					//System.out.println("date---------->"+d);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        writeToCsv(d, degree);
		        
		    }
		});
		
		xlsToCsv(inputFile, outputFile);
		System.out.println("completed");
	}

public void writeToCsv(Date date, Double degree )
{
		try {
			System.out.println("inside csv--->");
			
			HSSFWorkbook wb = null;
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell1 = null;
			HSSFCell cell2 = null;
			File file = new File(rootPath+"/src/main/webapp/data.xls");
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
			//System.out.println("---------------->"+date);
			HSSFCellStyle cellStyle = (HSSFCellStyle) wb.createCellStyle();
			
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(
			    createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
			//cell = row.createCell(1);
			//cell.setCellValue(new Date());
			//cell.setCellStyle(cellStyle);
			
			//cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell1.setCellValue((Date) date);
			//cellStyle.setDataFormat(HSSFDataFormat
				//	.getBuiltinFormat("yyyy-MM-dd"));
			cell1.setCellStyle(cellStyle);
			
			cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell2.setCellValue(degree);
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(rootPath+"/src/main/webapp/data.xls");
			wb.write(fileOut);
			fileOut.close();
		}  catch (IOException e) {
			e.printStackTrace();
		}
	  }

	public void xlsToCsv(File inputFile, File outputFile) 
	{
		//we created our file..!!
		   try {
			   StringBuffer data = new StringBuffer();
				FileInputStream input_document = new FileInputStream(inputFile);
		        // Read workbook into HSSFWorkbook
		        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
		        // Read worksheet into HSSFSheet
		        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
		        // To iterate over the rows
		        Iterator<Row> rowIterator = my_worksheet.iterator();
		        // OpenCSV writer object to create CSV file
		        //FileWriter my_csv=new FileWriter(outputFile);
		        //CSVWriter my_csv_output=new CSVWriter(my_csv); 
		        //Loop through rows.
		        FileOutputStream fos = new FileOutputStream(outputFile);
		        double sum = 0;
		        while(rowIterator.hasNext()) {
		                Row row = rowIterator.next(); 
		                int i=0;//String array
		                //change this depending on the length of your sheet
		                //String[] csvdata = new String[2];
		                Iterator<Cell> cellIterator = row.cellIterator();
		                        while(cellIterator.hasNext()) {
		                                Cell cell = cellIterator.next(); //Fetch CELL
		                                switch(cell.getCellType()) { //Identify CELL type
		                                        //you need to add more code here based on
		                                        //your requirement / transformations
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
		        //my_csv_output.writeNext(csvdata);
		        }
		        fos.write(data.toString().getBytes());
		        fos.close();
		        //my_csv_output.close(); //close the CSV file
		        
					input_document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //close xls
	}
}
