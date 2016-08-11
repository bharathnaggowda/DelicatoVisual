package com.delicato.visual.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;

import com.delicato.visual.config.SpringMongoConfig;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DyostemDataController {
	
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	MongoClient mongoClient=mongoConfig.getMongoClient();
	MongoDatabase db = mongoClient.getDatabase("delicato");
	MongoCollection<Document> dyostemCollection = db.getCollection("dyostemdata");
	MongoCollection<Document> dyostemCollectionNew = db.getCollection("dyostemdatanew");
	SimpleDateFormat dFormat = new SimpleDateFormat("MM/dd/yy");

	public static void main(String[] args) {
		
		DyostemDataController ddc = new DyostemDataController();
		ddc.updateDyostemData();
	}
	
	public void updateDyostemData(){
		
		FindIterable<Document> cursor = dyostemCollection.find();
		
			cursor.forEach(new Block<Document>() {
				public void apply(Document match) {
					Document dyostemInsert = new Document();
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
					String dateOfAnalysis = "";
					String blockName = null;
					
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
					 
					 
					if(match.get("Name of block") != null && match.get("Name of block") != ""){
								blockName =  (String) match.get("Name of block");
						}
					 
					 if(match.get("Date of Analysis") != null && match.get("Date of Analysis") != ""){
							dateOfAnalysis =  (String) match.get("Date of Analysis");
							
						}
					 Date d = null;
					try {
							d = dFormat.parse(dateOfAnalysis);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					 dyostemInsert.put("Date of Analysis", d);
					 dyostemInsert.put("Name of block", blockName);
					 dyostemInsert.put("Confidence Index profile", confIndexProfile);
					 dyostemInsert.put("Confidence Index behaviour", confIndexBehav);
					 dyostemInsert.put("Evolution of the volume", evolOfTheVol);
					 dyostemInsert.put("TAP at cut-off date", tapCutOfDate);
					 dyostemInsert.put("Index of global confidence", indexOfGlobalConf);
					 dyostemInsert.put("Median hue", medHue);
					 dyostemInsert.put("Hue 1st decile", hue1st);
					 dyostemInsert.put("Hue 9th decile", hue9th);
					 dyostemInsert.put("Average Volume", avgVol);
					 dyostemInsert.put("Volume Standard deviation", volStdDev);
					 dyostemInsert.put("Sugar quantity", sugQuant);
					 dyostemInsert.put("Sugar concentration", sugConc);
					 dyostemInsert.put("TAP Brix", tapBrix);
					 dyostemInsert.put("Total acidity", acidity);
					 dyostemInsert.put("Malic acid", malicAcid);
					 dyostemInsert.put("pH", ph);
					 dyostemInsert.put("Azote assimilable", azoteAssi);
					 dyostemInsert.put("Average berry weight", avgBerryWeight);
					 dyostemInsert.put("Berries count", berryCount);
					 dyostemCollectionNew.insertOne(dyostemInsert);
				}
			});
	}

}
