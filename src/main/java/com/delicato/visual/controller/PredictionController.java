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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.bson.Document;

import com.delicato.visual.config.SpringMongoConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PredictionController {
	
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	MongoClient mongoClient=mongoConfig.getMongoClient();
	MongoDatabase db = mongoClient.getDatabase("delicato");
	MongoCollection<Document> cimisCollection = db.getCollection("cimisdata");
	MongoCollection<Document> dyostemCollection = db.getCollection("dyostemdata");
	MongoCollection<Document> ratingCollection = db.getCollection("VintageRating");
	MongoCollection<Document> dyostemCollectionNew = db.getCollection("dyostemdatanew");
	
	final SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dFormat = new SimpleDateFormat("MM/dd/yy");
	
	double confIndexProfileP;
	double confIndexBehavP ;
	double evolOfTheVolP  ;
	double tapCutOfDateP;
	double indexOfGlobalConfP ;
	double medHueP;
	double hue1stP ;
	double hue9thP  ;
	double avgVolP ;
	double volStdDevP  ;
	double sugQuantP  ;
	double sugConcP;
	double tapBrixP;
	double acidityP  ;
	double malicAcidP ;
	double phP ;
	double azoteAssiP ;
	double avgBerryWeightP  ;
	double berryCountP ;

	public void generateTrainingData(String startDate, String endDate, final String filePath, final String block){
		try {		
				Date sDate = null;
				Date eDate = null;
					sDate = dFormat.parse(startDate);
					eDate = dFormat.parse(endDate);
				
				
				BasicDBObject getQuery = new BasicDBObject();
			    getQuery.put("_id", new BasicDBObject("$gt", sDate).append("$lt", eDate));
				
				FindIterable<Document> cursor = cimisCollection.find(getQuery).sort(new Document("_id",-1));
				
				cursor.forEach(new Block<Document>() {
					
				    public void apply(Document document) {
				        //System.out.println("inside doc");
				        String date = "";
				        Document a = (Document) document.get("hourlydata");
				        ArrayList contents = (ArrayList) a.get("Records");
				        
				        String qDate = "";
				        Date d = null;
				        
				        double netRadValue = 0 ;
				        double relHumValue = 0 ;
				        double vapPresValue = 0 ;
				        double windDirValue = 0 ;
				        double etoValue = 0 ;
				        double asceEtrValue = 0 ;
				        double airTmpValue = 0 ;
				        double dewPntValue = 0 ;
				        double precipValue = 0 ;
				        double windSpdValue = 0 ;
				        double resWindValue = 0 ;
				        double solRadValue = 0 ;
				        double soilTmpValue = 0 ;
				        double asceEtoValue = 0 ;
				        
				        double confIndexProfile = 0 ;
						double confIndexBehav = 0  ;
						double evolOfTheVol = 0 ;
						double tapCutOfDate = 0 ;
						double indexOfGlobalConf = 0 ;
						double medHue = 0 ;
						double hue1st = 0 ;
						double hue9th = 0 ;
						double avgVol = 0 ;
						double volStdDev = 0 ;
						double sugQuant = 0 ;
						double sugConc = 0 ;
						double tapBrix = 0 ;
						double acidity = 0 ;
						double malicAcid = 0 ;
						double ph = 0 ;
						double azoteAssi = 0 ;
						double avgBerryWeight = 0 ;
						double berryCount = 0 ;
						Boolean matchFound = false;
						
				        int count = 1;
				        for(int i=0; i<contents.size(); i++){
				        	
				        	if(count == 12){
				        		
				        		Document doc = (Document) contents.get(i);
						        
						        Document netRad = (Document) doc.get("HlyNetRad");
						        Document relHum = (Document) doc.get("HlyRelHum");
						        Document vapPres = (Document) doc.get("HlyVapPres");
						        Document windDir = (Document) doc.get("HlyWindDir");
						        Document eto = (Document) doc.get("HlyEto");
						        date = (String) doc.get("Date");
						        try {
									d = mFormat.parse(date);
								} catch (ParseException e) {
									e.printStackTrace();
								}
						        qDate = dFormat.format(d);
						        Document asceEtr = (Document) doc.get("HlyAsceEtr");
						        Document airTmp = (Document) doc.get("HlyAirTmp");
						        Document dewPnt = (Document) doc.get("HlyDewPnt");
						        Document precip = (Document) doc.get("HlyPrecip");
						        Document windSpd = (Document) doc.get("HlyWindSpd");
						        Document resWind = (Document) doc.get("HlyResWind");
						        Document solRad = (Document) doc.get("HlySolRad");
						        Document soilTmp = (Document) doc.get("HlySoilTmp");
						        Document asceEto = (Document) doc.get("HlyAsceEto");
						        
						        if(netRad.get("Value") != null && netRad.get("Value") != "")
						        netRadValue = Double.parseDouble((String) netRad.get("Value"));
						        
						        if(relHum.get("Value") != null && relHum.get("Value") != "")
						        relHumValue = Double.parseDouble((String) relHum.get("Value"));
						        
						        if(vapPres.get("Value") != null && vapPres.get("Value") != "")
						        vapPresValue = Double.parseDouble((String) vapPres.get("Value"));
						        
						        if(windDir.get("Value") != null && windDir.get("Value") != "")
						        windDirValue = Double.parseDouble((String) windDir.get("Value"));
						        
						        if(eto.get("Value") != null && eto.get("Value") != "")
						        etoValue = Double.parseDouble((String) eto.get("Value"));
						        
						        if(asceEtr.get("Value") != null && asceEtr.get("Value") != "")
						        asceEtrValue = Double.parseDouble((String) asceEtr.get("Value"));
						        
						        if(airTmp.get("Value") != null && airTmp.get("Value") != "")
						        airTmpValue = Double.parseDouble((String) airTmp.get("Value"));
						        
						        if(dewPnt.get("Value") != null && dewPnt.get("Value") != "")
						        dewPntValue = Double.parseDouble((String) dewPnt.get("Value"));
						        
						        if(precip.get("Value") != null && precip.get("Value") != "")
						        precipValue = Double.parseDouble((String) precip.get("Value"));
						        
						        if(windSpd.get("Value") != null && windSpd.get("Value") != "")
						        windSpdValue = Double.parseDouble((String) windSpd.get("Value"));
						        
						        if(resWind.get("Value") != null && resWind.get("Value") != "")
						        resWindValue = Double.parseDouble((String) resWind.get("Value"));
						        
						        if(solRad.get("Value") != null && solRad.get("Value") != "")
						        solRadValue = Double.parseDouble((String) solRad.get("Value"));
						        
						        if(soilTmp.get("Value") != null && soilTmp.get("Value") != "")
						        soilTmpValue = Double.parseDouble((String) soilTmp.get("Value"));
						        
						        if(asceEto.get("Value") != null && asceEto.get("Value") != "")
						        asceEtoValue = Double.parseDouble((String) asceEto.get("Value"));
						        
						        
						        
						        BasicDBObject dyostemQuery = new BasicDBObject();
						        dyostemQuery.put("Date of Analysis", qDate);
						        dyostemQuery.put("Name of block", block);
						        //System.out.println("date for query"+qDate+"---->"+dyostemQuery);
								FindIterable<Document> cursor = dyostemCollection.find(dyostemQuery);
								
								Document match = cursor.first();
								//System.out.println("date for query"+match);
								if(match != null){
									
									matchFound = true;
									
									if(match.get("Confidence Index profile") != null && match.get("Confidence Index profile") != ""){
										if(match.get("Confidence Index profile") instanceof Double)
											confIndexProfile =  (Double) match.get("Confidence Index profile");
										else if(match.get("Confidence Index profile") instanceof Integer)
											confIndexProfile =  (double) (Integer) match.get("Confidence Index profile");
									}
									
									if(match.get("Confidence Index behaviour") != null && match.get("Confidence Index behaviour") != ""){
										if(match.get("Confidence Index behaviour") instanceof Double)
											confIndexBehav =  (Double) match.get("Confidence Index behaviour");
										else if(match.get("Confidence Index behaviour") instanceof Integer)
											confIndexBehav =  (double) (Integer) match.get("Confidence Index behaviour");
									}
									 
									
									if(match.get("Evolution of the volume") != null && match.get("Evolution of the volume") != ""){
										if(match.get("Evolution of the volume") instanceof Double)
											evolOfTheVol =  (Double) match.get("Evolution of the volume");
										else if(match.get("Evolution of the volume") instanceof Integer)
											evolOfTheVol =  (double) (Integer) match.get("Evolution of the volume");
									}
									
									if(match.get("TAP at cut-off date") != null && match.get("TAP at cut-off date") != ""){
										if(match.get("TAP at cut-off date") instanceof Double)
											tapCutOfDate =  (Double) match.get("TAP at cut-off date");
										else if(match.get("TAP at cut-off date") instanceof Integer)
											tapCutOfDate =  (double) (Integer) match.get("TAP at cut-off date");
									}
									
									if(match.get("Index of global confidence") != null && match.get("Index of global confidence") != ""){
										if(match.get("Index of global confidence") instanceof Double)
											indexOfGlobalConf =  (Double) match.get("Index of global confidence");
										else if(match.get("Index of global confidence") instanceof Integer)
											indexOfGlobalConf =  (double) (Integer) match.get("Index of global confidence");
									}
									
									if(match.get("Median hue") != null && match.get("Median hue") != ""){
										if(match.get("Median hue") instanceof Double)
											medHue =  (Double) match.get("Median hue");
										else if(match.get("Median hue") instanceof Integer)
											medHue =  (double) (Integer) match.get("Median hue");
									}
									
									if(match.get("Hue 1st decile") != null && match.get("Hue 1st decile") != ""){
										if(match.get("Hue 1st decile") instanceof Double)
											hue1st =  (Double) match.get("Hue 1st decile");
										else if(match.get("Hue 1st decile") instanceof Integer)
											hue1st =  (double) (Integer) match.get("Hue 1st decile");
									}
									 
									 if(match.get("Hue 9th decile") != null && match.get("Hue 9th decile") != ""){
											if(match.get("Hue 9th decile") instanceof Double)
												hue9th =  (Double) match.get("Hue 9th decile");
											else if(match.get("Hue 9th decile") instanceof Integer)
												hue9th =  (double) (Integer) match.get("Hue 9th decile");
										}
									 
									 if(match.get("Average Volume") != null && match.get("Average Volume") != ""){
											if(match.get("Average Volume") instanceof Double)
												avgVol =  (Double) match.get("Average Volume");
											else if(match.get("Average Volume") instanceof Integer)
												avgVol =  (double) (Integer) match.get("Average Volume");
										}
									 
									 if(match.get("Volume Standard deviation") != null && match.get("Volume Standard deviation") != ""){
											if(match.get("Volume Standard deviation") instanceof Double)
												volStdDev =  (Double) match.get("Volume Standard deviation");
											else if(match.get("Volume Standard deviation") instanceof Integer)
												volStdDev =  (double) (Integer) match.get("Volume Standard deviation");
										}
									 
									 if(match.get("Sugar quantity") != null && match.get("Sugar quantity") != ""){
											if(match.get("Sugar quantity") instanceof Double)
												sugQuant =  (Double) match.get("Sugar quantity");
											else if(match.get("Sugar quantity") instanceof Integer)
												sugQuant =  (double) (Integer) match.get("Sugar quantity");
										}
									 
									 if(match.get("Sugar concentration") != null && match.get("Sugar concentration") != ""){
											if(match.get("Sugar concentration") instanceof Double)
												sugConc =  (Double) match.get("Sugar concentration");
											else if(match.get("Sugar concentration") instanceof Integer)
												sugConc =  (double) (Integer) match.get("Sugar concentration");
										}
									 
									 if(match.get("TAP Brix") != null && match.get("TAP Brix") != ""){
											if(match.get("TAP Brix") instanceof Double)
												tapBrix =  (Double) match.get("TAP Brix");
											else if(match.get("TAP Brix") instanceof Integer)
												tapBrix =  (double) (Integer) match.get("TAP Brix");
										}
									 
									 if(match.get("Total acidity") != null && match.get("Total acidity") != ""){
											if(match.get("Total acidity") instanceof Double)
												acidity =  (Double) match.get("Total acidity");
											else if(match.get("Total acidity") instanceof Integer)
												acidity =  (double) (Integer) match.get("Total acidity");
										}
									 
									 if(match.get("Malic acid") != null && match.get("Malic acid") != ""){
											if(match.get("Malic acid") instanceof Double)
												malicAcid =  (Double) match.get("Malic acid");
											else if(match.get("Malic acid") instanceof Integer)
												malicAcid =  (double) (Integer) match.get("Malic acid");
										}
									 
									 if(match.get("pH") != null && match.get("pH") != ""){
											if(match.get("pH") instanceof Double)
												ph =  (Double) match.get("pH");
											else if(match.get("pH") instanceof Integer)
												ph =  (double) (Integer) match.get("pH");
										}
									 
									 if(match.get("Azote assimilable") != null && match.get("Azote assimilable") != ""){
											if(match.get("Azote assimilable") instanceof Double)
												azoteAssi =  (Double) match.get("Azote assimilable");
											else if(match.get("Azote assimilable") instanceof Integer)
												azoteAssi =  (double) (Integer) match.get("Azote assimilable");
										}
									 
									 if(match.get("Average berry weight") != null && match.get("Average berry weight") != ""){
											if(match.get("Average berry weight") instanceof Double)
												avgBerryWeight =  (Double) match.get("Average berry weight");
											else if(match.get("Average berry weight") instanceof Integer)
												avgBerryWeight =  (double) (Integer) match.get("Average berry weight");
										}
									 
									 if(match.get("Berries count") != null && match.get("Berries count") != ""){
											if(match.get("Berries count") instanceof Double)
												berryCount =  (Double) match.get("Berries count");
											else if(match.get("Berries count") instanceof Integer)
												berryCount =  (double) (Integer) match.get("Berries count");
										}
								}
								try{
									BasicDBObject ratingQuery = new BasicDBObject();
									ratingQuery.put("_id", (block+"-20"+qDate.split("/")[2]));
									//ratingQuery.put("_id", ("-20"+qDate.split("/")[2]));
							        System.out.println("q------->"+ratingQuery);
									FindIterable<Document> ratingCursor = ratingCollection.find(ratingQuery);
									
									Document ratingMatch = ratingCursor.first();
									System.out.println("q------->"+ratingMatch);
									Double rating = ratingMatch.getDouble("rating");
										
										writeToXls(matchFound, d, netRadValue, relHumValue, vapPresValue, windDirValue, etoValue, asceEtrValue, airTmpValue,
								        		dewPntValue, precipValue, windSpdValue, resWindValue, solRadValue, soilTmpValue, asceEtoValue,
								        		confIndexProfile, confIndexBehav, evolOfTheVol, tapCutOfDate, indexOfGlobalConf,
								        		medHue, hue1st, hue9th, avgVol, volStdDev, sugQuant, sugConc, tapBrix,
								        		acidity, malicAcid, ph, azoteAssi, avgBerryWeight, berryCount, rating, filePath);
								}catch(Exception e){
									e.printStackTrace();
								}
								
				        	}
					        
					        
				        	count++;
							
				        }
				        
				        //vc.writeToCsv(d, degree);
				        
				    }
				});
		//}
				//vc.xlsToCsv(inputFile, outputFile);
				//System.out.println("completed");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	public void writeToXls(Boolean matchFound, Date date, Double netRadValue, Double relHumValue, Double vapPresValue, Double windDirValue, Double etoValue, Double asceEtrValue, Double airTmpValue,
			Double dewPntValue, Double precipValue, Double windSpdValue, Double resWindValue, Double solRadValue, Double soilTmpValue, Double asceEtoValue,
			Double confIndexProfile, Double confIndexBehav, Double evolOfTheVol, Double tapCutOfDate, Double indexOfGlobalConf,
			Double medHue, Double hue1st, Double hue9th, Double avgVol, Double volStdDev, Double sugQuant, Double sugConc, Double tapBrix,
			Double acidity, Double malicAcid, Double ph, Double azoteAssi, Double avgBerryWeight, Double berryCount, Double rating, String filePath)
	{
		
		if(matchFound){
			confIndexProfileP = confIndexProfile ;
			 confIndexBehavP = confIndexBehav  ;
			 evolOfTheVolP = evolOfTheVol ;
			 tapCutOfDateP = tapCutOfDate ;
			 indexOfGlobalConfP = indexOfGlobalConf ;
			 medHueP = medHue ;
			 hue1stP = hue1st ;
			 hue9thP = hue9th ;
			 avgVolP = avgVol ;
			 volStdDevP = volStdDev ;
			 sugQuantP = sugQuant ;
			 sugConcP = sugConc ;
			 tapBrixP = tapBrix ;
			 acidityP = acidity ;
			 malicAcidP = malicAcid ;
			 phP = ph ;
			 azoteAssiP = azoteAssi ;
			 avgBerryWeightP = avgBerryWeight ;
			 berryCountP = berryCount ;
		}
			try {
				//System.out.println("inside csv--->");
				
				HSSFWorkbook wb = null;
				HSSFSheet sheet = null;
				HSSFRow row = null;
				HSSFCell cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20 = null;
				HSSFCell cell21, cell22, cell23, cell24, cell25, cell26, cell27, cell28, cell29, cell30, cell31, cell32, cell33, cell34 = null;
				//HSSFCell cell2 = null;
				File file = new File(filePath);
				if(file.exists()){
					InputStream inp = new FileInputStream(file);
					 
						wb = new HSSFWorkbook(inp);
					sheet = wb.getSheetAt(0);
					
					
				}else{
					wb = new HSSFWorkbook();
			         sheet = wb.createSheet();
			         row = sheet.createRow(0);
					 cell1 = row.createCell(0);
					 cell1.setCellValue("netRad");
					 cell2 = row.createCell(1);
					 cell2.setCellValue("relHum");
					 cell3 = row.createCell(2);
					 cell3.setCellValue("vapPres");
					 cell4 = row.createCell(3);
					 cell4.setCellValue("windDir");
					 cell5 = row.createCell(4);
					 cell5.setCellValue("eto");
					 cell6 = row.createCell(5);
					 cell6.setCellValue("asceEtr");
					 cell7 = row.createCell(6);
					 cell7.setCellValue("airTmp");
					 cell8 = row.createCell(7);
					 cell8.setCellValue("dewPnt");
					 cell9 = row.createCell(8);
					 cell9.setCellValue("precip");
					 cell10 = row.createCell(9);
					 cell10.setCellValue("windSpd");
					 cell11 = row.createCell(10);
					 cell11.setCellValue("resWind");
					 cell12 = row.createCell(11);
					 cell12.setCellValue("solRad");
					 cell13 = row.createCell(12);
					 cell13.setCellValue("soilTmp");
					 cell14 = row.createCell(13);
					 cell14.setCellValue("asceEto");
					 cell15 = row.createCell(14);
					 cell15.setCellValue("confIndexProfile");
					 cell16 = row.createCell(15);
					 cell16.setCellValue("confIndexBehav");
					 cell17 = row.createCell(16);
					 cell17.setCellValue("evolOfTheVol");
					 cell18 = row.createCell(17);
					 cell18.setCellValue("tapCutOfDate");
					 cell19 = row.createCell(18);
					 cell19.setCellValue("indexOfGlobalConf");
					 cell20 = row.createCell(19);
					 cell20.setCellValue("medHue");
					 cell21 = row.createCell(20);
					 cell21.setCellValue("hue1st");
					 cell22 = row.createCell(21);
					 cell22.setCellValue("hue9th");
					 cell23 = row.createCell(22);
					 cell23.setCellValue("avgVol");
					 cell24 = row.createCell(23);
					 cell24.setCellValue("volStdDev");
					 cell25 = row.createCell(24);
					 cell25.setCellValue("sugQuant");
					 cell26 = row.createCell(25);
					 cell26.setCellValue("sugConc");
					 cell27 = row.createCell(26);
					 cell27.setCellValue("tapBrix");
					 cell28 = row.createCell(27);
					 cell28.setCellValue("acidity");
					 cell29 = row.createCell(28);
					 cell29.setCellValue("malicAcid");
					 cell30 = row.createCell(29);
					 cell30.setCellValue("ph");
					 cell31 = row.createCell(30);
					 cell31.setCellValue("azoteAssi");
					 cell32 = row.createCell(31);
					 cell32.setCellValue("avgBerryWeight");
					 cell33 = row.createCell(32);
					 cell33.setCellValue("berryCount");
					 cell34 = row.createCell(33);
					 cell34.setCellValue("rating");
				} 
				
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell1 = row.createCell(0);
				cell2 = row.createCell(1);
				cell3 = row.createCell(2);
				cell4 = row.createCell(3);
				cell5 = row.createCell(4);
				cell6 = row.createCell(5);
				cell7 = row.createCell(6);
				cell8 = row.createCell(7);
				cell9 = row.createCell(8);
				cell10 = row.createCell(9);
				cell11 = row.createCell(10);
				cell12 = row.createCell(11);
				cell13 = row.createCell(12);
				cell14 = row.createCell(13);
				cell15 = row.createCell(14);
				cell16 = row.createCell(15);
				cell17 = row.createCell(16);
				cell18 = row.createCell(17);
				cell19 = row.createCell(18);
				cell20 = row.createCell(19);
				cell21 = row.createCell(20);
				cell22 = row.createCell(21);
				cell23 = row.createCell(22);
				cell24 = row.createCell(23);
				cell25 = row.createCell(24);
				cell26 = row.createCell(25);
				cell27 = row.createCell(26);
				cell28 = row.createCell(27);
				cell29 = row.createCell(28);
				cell30 = row.createCell(29);
				cell31 = row.createCell(30);
				cell32 = row.createCell(31);
				cell33 = row.createCell(32);
				cell34 = row.createCell(33);
				
				cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell1.setCellValue(netRadValue);
				cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell2.setCellValue(relHumValue);
				cell3.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell3.setCellValue(vapPresValue);
				cell4.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell4.setCellValue(windDirValue);
				cell5.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell5.setCellValue(etoValue);
				cell6.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell6.setCellValue(asceEtrValue);
				cell7.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell7.setCellValue(airTmpValue);
				cell8.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell8.setCellValue(dewPntValue);
				cell9.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell9.setCellValue(precipValue);
				cell10.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell10.setCellValue(windSpdValue);
				cell11.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell11.setCellValue(resWindValue);
				cell12.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell12.setCellValue(solRadValue);
				cell13.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell13.setCellValue(soilTmpValue);
				cell14.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell14.setCellValue(asceEtoValue);
				cell15.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell15.setCellValue(confIndexProfileP);
				cell16.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell16.setCellValue(confIndexBehavP);
				cell17.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell17.setCellValue(evolOfTheVolP);
				cell18.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell18.setCellValue(tapCutOfDateP);
				cell19.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell19.setCellValue(indexOfGlobalConfP);
				cell20.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell20.setCellValue(medHueP);
				cell21.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell21.setCellValue(hue1stP);
				cell22.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell22.setCellValue(hue9thP);
				cell23.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell23.setCellValue(avgVolP);
				cell24.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell24.setCellValue(volStdDevP);
				cell25.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell25.setCellValue(sugQuantP);
				cell26.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell26.setCellValue(sugConcP);
				cell27.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell27.setCellValue(tapBrixP);
				cell28.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell28.setCellValue(acidityP);
				cell29.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell29.setCellValue(malicAcidP);
				cell30.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell30.setCellValue(phP);
				cell31.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell31.setCellValue(azoteAssiP);
				cell32.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell32.setCellValue(avgBerryWeightP);
				cell33.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell33.setCellValue(berryCountP);
				cell34.setCellType(Cell.CELL_TYPE_NUMERIC);
				cell34.setCellValue(rating);
				
				FileOutputStream fileOut = new FileOutputStream(filePath);
				wb.write(fileOut);
				fileOut.close();
			}  catch (IOException e) {
				e.printStackTrace();
			}
		  }
}
