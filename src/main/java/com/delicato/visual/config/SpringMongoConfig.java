package com.delicato.visual.config;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class SpringMongoConfig {
    
    public MongoClient getMongoClient() {
    	MongoClient mongoClient=null ;
    	String link ="mongodb://localhost:27017/delicato";
    	//String link ="mongodb://bharathnaggowda:mongoPa$$@ds015939.mlab.com:15939/delicato";
    	MongoClientURI uri =new MongoClientURI(link);
    	
    	try{
    		mongoClient = new MongoClient(uri);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

}
