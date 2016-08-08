package com.delicato.visual.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPDouble;
import org.rosuda.REngine.REXPFactor;
import org.rosuda.REngine.REXPInteger;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.REXPVector;
import org.rosuda.REngine.RFactor;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class SurvivalModel {

    public List<Double> generateModel(RConnection c, File file, String modelfile, String predFile) {
        
    	List<Double> predValue = null;
    	if(new File(modelfile).exists() &&  new File(predFile).exists()){
    		
            if(c.isConnected()) {
                System.out.println("Connected to RServe.");
                if(c.needLogin()) {
                    System.out.println("Providing Login");
                    try {
    					c.login("username", "password");
    				} catch (RserveException e) {
    					e.printStackTrace();
    				}
                }

                REXP x = null;
                System.out.println("Reading script...");

            					try{	
            						x = c.eval("library(readxl)");
            							x = c.eval("traindata <- read_excel(\""+modelfile+"\")");
            							x = c.eval("preddata <- read_excel(\""+predFile+"\")"); 
    		        					BufferedReader br = new BufferedReader(new FileReader(file));
    	            					System.out.println("after reading file----------->");
    		        					for(String line; (line = br.readLine()) != null; ) {
    	    			                    System.out.println(line);
    	    			                    x = c.eval(line);         // evaluates line in R
    	    			                    
    	    			                    System.out.println("after executing line----->"+x);
    	    			                    
    	    			                }
    		        					 predValue = convertData( x);
            				} catch (RserveException e) {
            					e.printStackTrace();
            				}catch (FileNotFoundException e) {
            					e.printStackTrace();
            				} catch (IOException e) {
            					e.printStackTrace();
            				}
            			
            } else {
                System.out.println("Rserve could not connect");
            }

            c.close();
            System.out.println("Session Closed");
    	}
    	
		return predValue;
    }
    
    private List<Double> convertData(REXP x){

    	List<Double> predValues = new ArrayList<Double>();
    	double predAvg = 0.0;
    	int count = 0;
    	int one = 0;
    	int two = 0;
    	int three = 0;
    	int four = 0;
    	int five = 0;
    	int six = 0;
    	int seven = 0;
    	int eight = 0;
    	int nine = 0;
    	int ten = 0;
    	double[] rlist = null;
		try {
			rlist = x.asDoubles();
			
    	  for (int i=0; i < rlist.length; i++) {
    	    Object result=rlist[i];
    	    double value = Double.parseDouble(result.toString());
    	    if(value > 0 && value < 0.5){
    	    	one++;
    	    }else if(value > 0.5 && value < 1){
    	    	two++;
    	    }else if(value > 1 && value < 1.5){
    	    	three++;
    	    }else if(value > 1.5 && value < 2){
    	    	four++;
    	    }else if(value > 2 && value < 2.5){
    	    	five++;
    	    }else if(value > 2.5 && value < 3){
    	    	six++;
    	    }else if(value > 3 && value < 3.5){
    	    	seven++;
    	    }else if(value > 3.5 && value < 4){
    	    	eight++;
    	    }else if(value > 4 && value < 4.5){
    	    	nine++;
    	    }else if(value > 4.5 && value < 5){
    	    	ten++;
    	    } 
    	    	
    	    predAvg += value;
    	    count++;
    	  }
    	  predValues.add(predAvg/count);
    	  predValues.add((double) one);
    	  predValues.add((double) two);
    	  predValues.add((double) three);
    	  predValues.add((double) four);
    	  predValues.add((double) five);
    	  predValues.add((double) six);
    	  predValues.add((double) seven);
    	  predValues.add((double) eight);
    	  predValues.add((double) nine);
    	  predValues.add((double) ten);
    	  
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		}
		return predValues;
		
	}    
   
}
