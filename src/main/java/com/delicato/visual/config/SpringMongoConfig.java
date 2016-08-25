package com.delicato.visual.config;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class SpringMongoConfig {
    
    public MongoClient getMongoClient(String link) {
    	MongoClient mongoClient=null ;
    	//String link ="mongodb://localhost:27017/delicato";
    	//String link ="mongodb://bharathnaggowda:mongoPa$$@ds015939.mlab.com:15939/delicato";
    	//String link ="mongodb://admin:GU2Sc5FqmqBw@ds015939.mlab.com:15939/delicuatoanalytics";
    	MongoClientURI uri;
    	
    	try{
    		System.out.println("inside mongo");
    		uri =new MongoClientURI(link);
    		mongoClient = new MongoClient(uri);
    		System.out.println("inside mongo2");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return mongoClient;
    }
    
    public RConnection getRconnection(){
		
		RConnection con = null;
		try {
			con =  new RConnection("localhost", 6311);
		} catch (RserveException e) {
			e.printStackTrace();
		}
		return con;
	}

}
