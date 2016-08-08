package com.delicato.visual.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.bson.Document;

import com.delicato.visual.config.SpringMongoConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DataService {
	
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	MongoClient mongoClient=mongoConfig.getMongoClient();
	MongoDatabase db = mongoClient.getDatabase("delicato");
	MongoCollection<Document> cimisCollection = db.getCollection("cimisdata");
	MongoCollection<Document> dyostemCollection = db.getCollection("dyostemdata");
	
	String rootPath =System.getProperty("user.dir");
	String filePath = rootPath+"/src/main/webapp/";
	
	ExcelService xlsService = new ExcelService();
	CsvService csvService = new CsvService();
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	HashSet<String> blocks = new HashSet<String>();
	
	public HashSet<String> getBlocks() {
		
		if(blocks.isEmpty()){
			
			BasicDBObject getQuery = new BasicDBObject();
		    FindIterable<Document> cursor = dyostemCollection.find(getQuery);
		    
		    cursor.forEach(new Block<Document>() {
				
			    public void apply(Document document) {
			    	
			    	String block = (String) document.get("Name of block");
			    	blocks.add(block);
			    }
		    });
		}
	    return blocks;
	}
	
	public void getCimisData(Date sDate, Date eDate, int minTempTheshold, int maxTempTheshold, int minWindTheshold, int maxWindTheshold){
			
		File inputFile = new File(filePath+"degreedays.xls");
	    File outputFile = new File(filePath+"degreedays.csv");
			if(inputFile.exists()){
				inputFile.delete();
			}
			if(outputFile.exists()){
				outputFile.delete();
			}
			
			BasicDBObject getQuery = new BasicDBObject();
		    getQuery.put("_id", new BasicDBObject("$gte", sDate).append("$lte", eDate));
			
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
			        double degree = 0.0001;
			        int daysCount = 0;
			        int c =1;
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
					        	degree = (degree - lThreshold + (value+32)*9/5)/24;
					        	daysCount++;
					        }else if(value > hThreshold){
					        	degree = (degree - lThreshold + (hThreshold+32)*9/5)/24;
					        	daysCount++;
					        }
				        }
			        }
			        //degree = degree/daysCount;
			        System.out.println("daysCount----->"+daysCount);
			        System.out.println(degree);
			        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date d = null;
					String dat = null;
			        try {
						d = dateFormat.parse(date);
						dat = sdf.format(d);
					} catch (ParseException e) {
						e.printStackTrace();
					}
			        xlsService.writeToXls(dat, degree);
			    }
			});
			
			csvService.xlsToCsvDegreeDays(inputFile, outputFile);
			System.out.println("completed");
		}
	
	public void getCimisDataForGraphs(Date sDate, Date eDate, double airtemp, double dewpoint, double relhum, double soiltemp, double vappres, double windspeed, double winddir ){
		
		File inputFile = new File(filePath+"cimis1.xls");
		File outputFile = new File(filePath+"cimis1.csv");
		File inputFile1 = new File(filePath+"cimis3.xls");
		File outputFile1 = new File(filePath+"cimis3.csv");
		File inputFile2 = new File(rootPath+"/src/main/webapp/cimis2.xls");
	    File outputFile2 = new File(rootPath+"/src/main/webapp/cimis2.csv");
		File inputFile3 = new File(rootPath+"/src/main/webapp/cimis4.xls");
	    File outputFile3 = new File(rootPath+"/src/main/webapp/cimis4.csv");
	   
			if(inputFile2.exists()){
				inputFile2.delete();
			}
			if(outputFile2.exists()){
				outputFile2.delete();
			}
			if(inputFile3.exists()){
				inputFile3.delete();
			}
			if(outputFile3.exists()){
				outputFile3.delete();
			}
			if(inputFile.exists()){
				inputFile.delete();
			}
			if(outputFile.exists()){
				outputFile.delete();
			}
			if(inputFile1.exists()){
				inputFile1.delete();
			}
			if(outputFile1.exists()){
				outputFile1.delete();
			}
			
			BasicDBObject getQuery = new BasicDBObject();
		    getQuery.put("_id", new BasicDBObject("$gte", sDate).append("$lte", eDate));

			FindIterable<Document> cursor = cimisCollection.find(getQuery);
			
			cursor.forEach(new Block<Document>() {
				
			    public void apply(Document document) {
			        System.out.println("inside doc");
			        String date = "";
			        Document a = (Document) document.get("hourlydata");
			        ArrayList contents = (ArrayList) a.get("Records");
			        double airtemp1=0, dewpoint1=0, relhum1=0, soiltemp1=0, vappres1=0, windspeed1=0, winddir1=0;
			        int count = 0;
			        for(int i=0; i<contents.size(); i++){
				        Document doc = (Document) contents.get(i);
				        Document airtemp = (Document) doc.get("HlyAirTmp");
				        Document dewpoint = (Document) doc.get("HlyDewPnt");
				        
				        Document relhum = (Document) doc.get("HlyRelHum");
				        Document soiltemp=(Document) doc.get("HlySoilTmp");
				        //Document solrad = (Document) doc.get("HlySolRad");
				        Document vappres = (Document) doc.get("HlyVapPres");
				        
				        Document windspeed = (Document) doc.get("HlyWindSpd");
				        Document winddir=(Document) doc.get("HlyWindDir");
				       // System.out.println(airtemp);
				        date = (String) doc.get("Date");
				       if(airtemp.get("Value") != null && airtemp.get("Value") != "" &&
				        		dewpoint.get("Value") != null && dewpoint.get("Value") != "" &&
				        				relhum.get("Value") != null && relhum.get("Value") != "" &&
				        				soiltemp.get("Value") != null && soiltemp.get("Value") != "" && 
					    		   vappres.get("Value") != null && vappres.get("Value") != "" &&
					    				   windspeed.get("Value") != null && windspeed.get("Value") != "" &&
					    						   winddir.get("Value") != null && winddir.get("Value") != ""){
				        	
				        	Double airtempvalue = Double.parseDouble((String) airtemp.get("Value"));
				        	Double dewpointValue = Double.parseDouble((String) dewpoint.get("Value"));
				        	Double relhumValue = Double.parseDouble((String) relhum.get("Value"));
				        	Double soiltempValue = Double.parseDouble((String) soiltemp.get("Value"));
				        	Double vappresValue = Double.parseDouble((String)vappres.get("Value"));
				        	Double windspeedValue = Double.parseDouble((String) windspeed.get("Value"));
				        	Double winddirValue = Double.parseDouble((String) winddir.get("Value"));
				        	
				        	vappres1+=vappresValue;
				        	windspeed1+=windspeedValue;
				        	winddir1+=winddirValue;
				        	airtemp1+= airtempvalue;
				        	dewpoint1+=dewpointValue;
				        	relhum1+=relhumValue;
				        	soiltemp1+=soiltempValue;
				        	count++;
				       }
			        }
			        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date d = null;
					String dat = null;
			        try {
						d = dateFormat.parse(date);
						dat = sdf.format(d);
					} catch (ParseException e) {
						e.printStackTrace();
					}
			        
			        vappres1 = vappres1/count;
			        windspeed1 = windspeed1/count;
			        winddir1 = winddir1/count;
			        airtemp1 = airtemp1/count;
			        dewpoint1 = dewpoint1/count;
			        relhum1 = relhum1/count;
			        soiltemp1 = soiltemp1/count;
			        
			        xlsService.writeToXlsCimisFirstGraph(dat, airtemp1,dewpoint1,vappres1,windspeed1,soiltemp1 );
			        xlsService.writeToXlsCimisSecGraph(dat,relhum1 ,winddir1 );
			        
			    }
			});
			
			csvService.xlsToCsv(inputFile, outputFile);
			csvService.xlsToCsv(inputFile1, outputFile1);
			System.out.println("completed");
	}
	
	public void getCimisDataForSubGraph(double airtemp, double dewpoint, double relhum, double soiltemp, double vappres, double windspeed, double winddir, Date gDate ){
		
		File inputFile = new File(rootPath+"/src/main/webapp/cimis2.xls");
	    File outputFile = new File(rootPath+"/src/main/webapp/cimis2.csv");
		File inputFile1 = new File(rootPath+"/src/main/webapp/cimis4.xls");
	    File outputFile1 = new File(rootPath+"/src/main/webapp/cimis4.csv");
	   
			if(inputFile.exists()){
				inputFile.delete();
			}
			if(outputFile.exists()){
				outputFile.delete();
			}
			if(inputFile1.exists()){
				inputFile1.delete();
			}
			if(outputFile1.exists()){
				outputFile1.delete();
			}
			
			BasicDBObject getQuery = new BasicDBObject();
		    getQuery.put("_id", gDate);
		    System.out.println("qqqqqqq"+gDate);
			FindIterable<Document> cursor = cimisCollection.find(getQuery);
			
			cursor.forEach(new Block<Document>() {
				
			    public void apply(Document document) {
			    	
			        System.out.println("inside doc");
			        String date = "";
			        Document a = (Document) document.get("hourlydata");
			        ArrayList contents = (ArrayList) a.get("Records");
			        double airtemp1=0, dewpoint1=0, relhum1=0, soiltemp1=0,  vappres1=0, windspeed1=0, winddir1=0;
			     
			        for(int i=0; i<contents.size(); i++){
			        	//System.out.println(contents.get(i));
				        Document doc = (Document) contents.get(i);
				        Document airtemp = (Document) doc.get("HlyAirTmp");
				        Document dewpoint = (Document) doc.get("HlyDewPnt");
				        
				        Document relhum = (Document) doc.get("HlyRelHum");
				        Document soiltemp=(Document) doc.get("HlySoilTmp");
				        //Document solrad = (Document) doc.get("HlySolRad");
				        Document vappres = (Document) doc.get("HlyVapPres");
				        
				        Document windspeed = (Document) doc.get("HlyWindSpd");
				        Document winddir=(Document) doc.get("HlyWindDir");
				        date = (String) doc.get("Date");
				        
				        System.out.println("airtemp--> "+airtemp.get("Value")+"--"+"dewpoint--> "+dewpoint.get("Value")+"--"+"windspeed--> "+windspeed.get("Value")+"--"+"vappres--> "+vappres.get("Value")+"--"+"relhum--> "+relhum.get("Value")+"--"+"soiltemp--> "+soiltemp.get("Value")+"winddir--> "+winddir.get("Value"));
				       if(airtemp.get("Value") != null && airtemp.get("Value") != "" &&
				        		dewpoint.get("Value") != null && dewpoint.get("Value") != "" &&
				        				relhum.get("Value") != null && relhum.get("Value") != "" &&
				        				soiltemp.get("Value") != null && soiltemp.get("Value") != ""){
				        	
				        	Double airtempvalue = Double.parseDouble((String) airtemp.get("Value"));
				        	Double dewpointValue = Double.parseDouble((String) dewpoint.get("Value"));
				        	Double relhumValue = Double.parseDouble((String) relhum.get("Value"));
				        	Double soiltempValue = Double.parseDouble((String) soiltemp.get("Value"));
				        	//Double solradvalue = Double.parseDouble((String) solrad.get("Value"));
				        	Double vappresValue = Double.parseDouble((String)vappres.get("Value"));
				        	Double windspeedValue = Double.parseDouble((String) windspeed.get("Value"));
				        	Double winddirValue = Double.parseDouble((String) winddir.get("Value"));
				        	
				        	airtemp1= airtempvalue;
				        	dewpoint1=dewpointValue;
				        	relhum1=relhumValue;
				        	soiltemp1=soiltempValue;
				        	//solrad1= solradvalue;
				        	//System.out.println("airtemp"+airtemp1);
				        	vappres1=vappresValue;
				        	windspeed1=windspeedValue;
				        	winddir1=winddirValue;
				        	
				        }
				       
				        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date d = null;
						String dat = null;
				        try {
							d = dateFormat.parse(date);
							dat = sdf.format(d);
							System.out.println("sssssssssssssssssssdate---------->"+d);
						} catch (ParseException e) {
							e.printStackTrace();
						}
				        xlsService.writeToCsv2(dat, airtemp1,dewpoint1,vappres1,windspeed1,soiltemp1 );
				        xlsService.writeToCsv(dat,relhum1,winddir1 );
			        }
			    }
			});
			
			csvService.xlsToCsv(inputFile, outputFile);
			csvService.xlsToCsv(inputFile1, outputFile1);
			System.out.println("completed2");
		}

	
}
