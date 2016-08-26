package com.delicato.visual.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.bson.Document;

import com.delicato.visual.config.SpringMongoConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DataService {
	
	SpringMongoConfig mongoConfig;
	MongoClient mongoClient;
	MongoDatabase db;
	MongoCollection<Document> cimisCollection;
	MongoCollection<Document> dyostemCollection;
	String root_path;
	
	public DataService(String mongolink, String rootPath){
		
		mongoConfig = new SpringMongoConfig();
		mongoClient = mongoConfig.getMongoClient(mongolink);
		db = mongoClient.getDatabase("delicato");
		cimisCollection = db.getCollection("cimisdata");
		dyostemCollection = db.getCollection("dyostemdata");
		root_path = rootPath;
	}
	
	ExcelService xlsService = new ExcelService();
	CsvService csvService = new CsvService();
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	HashSet<String> blocks = new HashSet<String>();
	HashSet<String> grapes = new HashSet<String>();
	
	public static HashMap<String, ArrayList<Double>> windRose;
	
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
	public HashSet<String> getGrapeVeriety() {
		
		if(grapes.isEmpty()){
			
			BasicDBObject getQuery = new BasicDBObject();
		    FindIterable<Document> cursor = dyostemCollection.find(getQuery);
		    
		    cursor.forEach(new Block<Document>() {
				
			    public void apply(Document document) {
			    	
			    	String grape = (String) document.get("Grape variety");
			    	grapes.add(grape);
			    }
		    });
		}
	    return grapes;
	}
	
	public void getCimisData(Date sDate, Date eDate, int minTempTheshold, int maxTempTheshold, int minWindTheshold, int maxWindTheshold){
			
		File inputFile = new File(root_path+"degreedays.xls");
	    File outputFile = new File(root_path+"degreedays.csv");
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
			        //System.out.println("inside doc");
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
				        //System.out.println("Wind--> "+windSpeed.get("Value")+"--"+"Humidity--> "+humidity.get("Value"));
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
			        //System.out.println("daysCount----->"+daysCount);
			        //System.out.println(degree);
			        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date d = null;
					String dat = null;
			        try {
						d = dateFormat.parse(date);
						dat = sdf.format(d);
					} catch (ParseException e) {
						e.printStackTrace();
					}
			        xlsService.writeToXls(dat, degree, root_path);
			    }
			});
			
			csvService.xlsToCsvDegreeDays(inputFile, outputFile);
			//System.out.println("completed");
		}
	
	public HashMap<String, ArrayList<Double>> getCimisDataForGraphs(Date sDate, Date eDate, double airtemp, double dewpoint, double relhum, double soiltemp, double vappres, double windspeed, double winddir ){
		
		windRose = new HashMap<String, ArrayList<Double>>();
				
		File inputFile = new File(root_path+"cimis1.xls");
		File outputFile = new File(root_path+"cimis1.csv");
		File inputFile1 = new File(root_path+"cimis3.xls");
		File outputFile1 = new File(root_path+"cimis3.csv");
		File inputFile2 = new File(root_path+"cimis2.xls");
	    File outputFile2 = new File(root_path+"cimis2.csv");
		File inputFile3 = new File(root_path+"cimis4.xls");
	    File outputFile3 = new File(root_path+"cimis4.csv");
	   
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
			        //System.out.println("inside doc");
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
			        
			        xlsService.writeToXlsCimisFirstGraph(dat, airtemp1,dewpoint1,vappres1,windspeed1,soiltemp1,root_path );
			        xlsService.writeToXlsCimisSecGraph(dat,relhum1 ,winddir1,root_path );
			        addToHashMap(winddir1,windspeed1);
			        
			        
			    }
			});
			
			csvService.xlsToCsv(inputFile, outputFile);
			csvService.xlsToCsv(inputFile1, outputFile1);
			//System.out.println("completed");
			
			return windRose;
	}
	
	
	
	public HashMap<String, ArrayList<Double>> addToHashMap(double winddir1, double windspeed1){
		
		if(winddir1 < 11.25 && winddir1 > 348.75){
			if(windRose.containsKey("N")){
				windRose.get("N").add(windspeed1);
			}
			else{
				windRose.put("N", new ArrayList<Double>());
				windRose.get("N").add(windspeed1);
			}
		}else if(winddir1 > 11.25 && winddir1 < 33.75){
			if(windRose.containsKey("NNE")){
				windRose.get("NNE").add(windspeed1);
			}
			else{
				windRose.put("NNE", new ArrayList<Double>());
				windRose.get("NNE").add(windspeed1);
			}
		}else if(winddir1 > 33.75 && winddir1 < 56.25){
			if(windRose.containsKey("NE")){
				windRose.get("NE").add(windspeed1);
				}
				else{
				windRose.put("NE", new ArrayList<Double>());
				windRose.get("NE").add(windspeed1);
				}
				}else if(winddir1 > 56.25 && winddir1 < 78.75){
				if(windRose.containsKey("ENE")){
				windRose.get("ENE").add(windspeed1);
				}
				else{
				windRose.put("ENE", new ArrayList<Double>());
				windRose.get("ENE").add(windspeed1);
				}
				}else if(winddir1 > 78.75 && winddir1 < 101.25){
				if(windRose.containsKey("E")){
				windRose.get("E").add(windspeed1);
				}
				else{
				windRose.put("E", new ArrayList<Double>());
				windRose.get("E").add(windspeed1);
				}
				}else if(winddir1 > 101.25 && winddir1 < 123.75){
				if(windRose.containsKey("ESE")){
				windRose.get("ESE").add(windspeed1);
				}
				else{
				windRose.put("ESE", new ArrayList<Double>());
				windRose.get("ESE").add(windspeed1);
				}
				}else if(winddir1 > 123.75 && winddir1 < 146.25){
				if(windRose.containsKey("SE")){
				windRose.get("SE").add(windspeed1);
				}
				else{
				windRose.put("SE", new ArrayList<Double>());
				windRose.get("SE").add(windspeed1);
				}
				}else if(winddir1 > 146.25 && winddir1 < 168.75){
				if(windRose.containsKey("SSE")){
				windRose.get("SSE").add(windspeed1);
				}
				else{
				windRose.put("SSE", new ArrayList<Double>());
				windRose.get("SSE").add(windspeed1);
				}
				}else if(winddir1 > 168.75 && winddir1 < 191.25){
				if(windRose.containsKey("S")){
				windRose.get("S").add(windspeed1);
				}
				else{
				windRose.put("S", new ArrayList<Double>());
				windRose.get("S").add(windspeed1);
				}
				}else if(winddir1 > 191.25 && winddir1 < 213.75){
				if(windRose.containsKey("SSW")){
				windRose.get("SSW").add(windspeed1);
				}
				else{
				windRose.put("SSW", new ArrayList<Double>());
				windRose.get("SSW").add(windspeed1);
				}
				}else if(winddir1 > 213.75 && winddir1 < 236.25){
				if(windRose.containsKey("SW")){
				windRose.get("SW").add(windspeed1);
				}
				else{
				windRose.put("SW", new ArrayList<Double>());
				windRose.get("SW").add(windspeed1);
				}
				}else if(winddir1 > 236.25 && winddir1 < 258.75){
				if(windRose.containsKey("WSW")){
				windRose.get("WSW").add(windspeed1);
				}
				else{
				windRose.put("WSW", new ArrayList<Double>());
				windRose.get("WSW").add(windspeed1);
				}
				}else if(winddir1 > 258.75 && winddir1 < 281.25){
				if(windRose.containsKey("W")){
				windRose.get("W").add(windspeed1);
				}
				else{
				windRose.put("W", new ArrayList<Double>());
				windRose.get("W").add(windspeed1);
				}
				}else if(winddir1 > 281.25 && winddir1 < 303.75){
				if(windRose.containsKey("WNW")){
				windRose.get("WNW").add(windspeed1);
				}
				else{
				windRose.put("WNW", new ArrayList<Double>());
				windRose.get("WNW").add(windspeed1);
				}
				}else if(winddir1 > 303.75 && winddir1 < 326.25){
				if(windRose.containsKey("NW")){
				windRose.get("NW").add(windspeed1);
				}
				else{
				windRose.put("NW", new ArrayList<Double>());
				windRose.get("NW").add(windspeed1);
				}
				}else if(winddir1 > 326.25 && winddir1 < 348.75){
				if(windRose.containsKey("NNW")){
				windRose.get("NNW").add(windspeed1);
				}
				else{
				windRose.put("NNW", new ArrayList<Double>());
				windRose.get("NNW").add(windspeed1);
				}}
		
		return windRose;
	}
	
	public void getCimisDataForSubGraph(double airtemp, double dewpoint, double relhum, double soiltemp, double vappres, double windspeed, double winddir, Date gDate ){
		
		File inputFile = new File(root_path+"cimis2.xls");
	    File outputFile = new File(root_path+"cimis2.csv");
		File inputFile1 = new File(root_path+"cimis4.xls");
	    File outputFile1 = new File(root_path+"cimis4.csv");
	   
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
		    //System.out.println("qqqqqqq"+gDate);
			FindIterable<Document> cursor = cimisCollection.find(getQuery);
			
			cursor.forEach(new Block<Document>() {
				
			    public void apply(Document document) {
			    	
			        //System.out.println("inside doc");
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
				        
				        //System.out.println("airtemp--> "+airtemp.get("Value")+"--"+"dewpoint--> "+dewpoint.get("Value")+"--"+"windspeed--> "+windspeed.get("Value")+"--"+"vappres--> "+vappres.get("Value")+"--"+"relhum--> "+relhum.get("Value")+"--"+"soiltemp--> "+soiltemp.get("Value")+"winddir--> "+winddir.get("Value"));
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
							//System.out.println("sssssssssssssssssssdate---------->"+d);
						} catch (ParseException e) {
							e.printStackTrace();
						}
				        xlsService.writeToCsv2(dat, airtemp1,dewpoint1,vappres1,windspeed1,soiltemp1,root_path );
				        xlsService.writeToCsv(dat,relhum1,winddir1,root_path );
			        }
			    }
			});
			
			csvService.xlsToCsv(inputFile, outputFile);
			csvService.xlsToCsv(inputFile1, outputFile1);
			System.out.println("completed2");
		}
	
public void getblockname( String blockname, String grapename, double tapbrix){
		
		File inputFile = new File(root_path+"blockname.xls");
	    File outputFile = new File(root_path+"blockname.csv");
		
	   
			if(inputFile.exists()){
				inputFile.delete();
			}
			if(outputFile.exists()){
				outputFile.delete();
			}
			
			//Boolean matchFound = false;
			BasicDBObject dyostemQuery = new BasicDBObject();
	        //dyostemQuery.put("Date of Analysis", qDate);
	        dyostemQuery.put("Grape variety", grapename);
	        //System.out.println("grapename---"+grapename+"---->"+dyostemQuery);
	        //FindIterable<Document> cursor = dyostemCollection.find(dyostemQuery);
	        
			FindIterable<Document> cursor = dyostemCollection.find(dyostemQuery);
			
			cursor.forEach(new Block<Document>() {
				public void apply(Document document) {
			    	
					String blockname;
					double tapbrix = 0;
			        //Object value = document.get("TAP Brix");
			        if(!document.get("TAP Brix").toString().trim().equals("") && document.get("TAP Brix") != null && !(document.get("TAP Brix") instanceof Integer)){
	                	tapbrix= Double.parseDouble(document.get("TAP Brix").toString());
	                }else if(document.get("TAP Brix") instanceof Integer){
	                	tapbrix=  (Integer) document.get("TAP Brix");
	                }
	                	
	                blockname=(String) document.get("Name of block");
	                xlsService.writeToCsvforblockname( blockname,tapbrix,root_path);
				}
			});
			
			
			/*SimpleDateFormat dFormat = new SimpleDateFormat("MM/dd/yy");
				Date d = null;
		        try {
					d = dFormat.parse(date);
					System.out.println("sssssssssssssssssssdate---------->"+d);
				} catch (ParseException e) {
					e.printStackTrace();
				}*/
			 
		        xlsService.writeToCsvforblockname(blockname,tapbrix,root_path);
    	

			   
			csvService.xlsToCsvDyostem(inputFile, outputFile);
			
			//System.out.println("completedblocknames");
		}
	
}
