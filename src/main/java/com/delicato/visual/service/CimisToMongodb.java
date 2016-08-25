package com.delicato.visual.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.delicato.visual.config.SpringMongoConfig;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CimisToMongodb {
	
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	MongoClient mongoClient=mongoConfig.getMongoClient();
	MongoDatabase db = mongoClient.getDatabase("delicato");
	MongoCollection<Document> cimisCollection = db.getCollection("cimisdata");
	
	public void getCimisData() {
		
		for(int i=2010; i<=2016; i++){
			for(int j=1; j<=12; j++){
				for(int k=1; k<=31; k++){
					addCimisData(i,j,k);
				}
			}
		}
		
	}
	
	
	public void addCimisData(int year, Integer m, Integer d){
		
		String month = m.toString();
		String day = d.toString();
		if(month.length() == 1) month = "0"+month;
		if(day.length() == 1) day = "0"+day;
		Document object = new Document();
		
		String url = "http://et.water.ca.gov/api/data?appKey=c9e7a59a-1e62-4365-8f79-5dd26e10d632&"
				+ "targets=113&unitOfMeasure=M&"
				+ "startDate="+year+"-"+month+"-"+day+"&endDate="+year+"-"+month+"-"+day+"&"
				+ "dataItems=hly-air-tmp,hly-dew-pnt,hly-eto,hly-net-rad,hly-asce-eto,hly-asce-etr,hly-precip,hly-rel-hum,"
				+ "hly-res-wind,hly-soil-tmp,hly-sol-rad,hly-vap-pres,hly-wind-dir,hly-wind-spd";
		
		DefaultHttpClient client = new DefaultHttpClient();
		  HttpGet request = new HttpGet(url);
		  HttpResponse response = null;
		  BufferedReader rd = null;
		  String line = "";
		  JsonParser parser = new JsonParser();
		  
		try {
			response = client.execute(request);
			rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
			while ((line = rd.readLine()) != null) {
				try {
				JSONObject result = new JSONObject(line);
				if(result.keySet().contains("Data")){
					
					JSONObject data = result.getJSONObject("Data");
					JSONArray providers = data.getJSONArray("Providers");
					JSONObject records = providers.getJSONObject(0);
					JSONArray contents = records.getJSONArray("Records");
					JSONObject first = contents.getJSONObject(0);
					String date = (String) first.get("Date");
					//System.out.println(sport.getString("name"));
					
					DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
					Date nowAsDate = null;
					
						nowAsDate = df1.parse((String) first.get("Date"));
					
					
					
					//DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
					//String nowAsString = df.format(nowAsDate);
				    //System.out.println(date);
				    parser.parse(records.toString());
				    object.put("_id", nowAsDate);
				    object.put("hourlydata", Document.parse(records.toString()));
				    cimisCollection.insertOne(object);
				}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
				
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
