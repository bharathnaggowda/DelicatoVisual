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

    public void generateModel(RConnection c, File file, String modelfile) {
        
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
        							x = c.eval("preddata <- read_excel(\""+modelfile+"\")"); 
		        					BufferedReader br = new BufferedReader(new FileReader(file));
	            					System.out.println("after reading file----------->");
		        					for(String line; (line = br.readLine()) != null; ) {
	    			                    System.out.println(line);
	    			                    x = c.eval(line);         // evaluates line in R
	    			                    System.out.println("after executing line----->"+x);
	    			                    
	    			                }
		        					convertData( x);
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
    
    private void convertData(REXP x){

    	boolean isTerminate = false;
    	StringBuffer sb = new StringBuffer();
    	double[] rlist = null;
		try {
			rlist = x.asDoubles();
		
    	  if (!isTerminate)   sb.append("list(");
    	  for (int i=0; i < rlist.length; i++) {
    	    Object result=rlist[i];
    	    System.out.println("---------------->"+result);
    	    if (result instanceof REXPVector) {
    	      REXPVector vector=(REXPVector)result;
    	      handleVector(vector,sb,isTerminate);
    	    }
    	 else     if (result instanceof REXPString) {
    	      sb.append("'" + ((REXPString)result).asString() + "'");
    	    }
    	 else     if (result instanceof REXPDouble) {
    	      sb.append(Double.toString(((REXPDouble)result).asDouble()));
    	    }
    	 else     if (result instanceof REXPInteger) {
    	      sb.append(Integer.toString(((REXPInteger)result).asInteger()));
    	    }
    	    if (i < (rlist.length - 1))     sb.append(",");
    	  }
    	  if (!isTerminate)   sb.append(")");
    	  System.out.println("---------------->"+sb);
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		}
		
		//if(!array.isEmpty())
		//writeToExcel(array.get(array.size()/2), modelfile,  rateType,  ap, hotel);
    
	}    
    public void writeToExcel(double value,String modelfile, String rateType, int ap, int hotel){
    	
    	
    	XSSFWorkbook workbook = null;
    	XSSFSheet sheet ;
    	FileInputStream appendfile;
    	
			try {
	    	
	    	if(new File(modelfile).exists()){
				System.out.println("file exists");
					appendfile = new FileInputStream(modelfile);
					workbook = new XSSFWorkbook(appendfile);
				
				 sheet = workbook.getSheetAt(0);
				 
			}else{
				workbook = new XSSFWorkbook(); 
				 sheet = workbook.createSheet();
				 Row row = sheet.createRow(0);
				 Cell cell1 = row.createCell(0);
				 cell1.setCellValue("Hotel");
				 Cell cell2 = row.createCell(1);
				 cell2.setCellValue("ap");
				 Cell cell3 = row.createCell(2);
				 cell3.setCellValue("D");
				 Cell cell4 = row.createCell(3);
				 cell4.setCellValue("M");
				 Cell cell5 = row.createCell(4);
				 cell5.setCellValue("P");
				 Cell cell6 = row.createCell(5);
				 cell6.setCellValue("R");
			}
	    	
	    	int rownum = sheet.getLastRowNum()+1;
	    	System.out.println("last row number----> "+rownum);
	    	
	    	int rowno = 0;
	    	int cellno = 0;
	    	int c = 1;
	    	Iterator<Row> rowIterator = sheet.iterator();
	    	
			System.out.println("before while");
			while (rowIterator.hasNext()) 
			{
				
					Row row = rowIterator.next();
					if(c>1){
					int h = (int) row.getCell(0).getNumericCellValue();
					int a = (int) row.getCell(1).getNumericCellValue();
					System.out.println("before if "+ h+"-"+ hotel +"-"+ a +"-"+ ap);
					if(row.getCell(0).getNumericCellValue() == hotel && row.getCell(1).getNumericCellValue() == ap){
						System.out.println("inside if "+row.getCell(0).getNumericCellValue() + hotel + row.getCell(1).getNumericCellValue() + ap);
						rowno = row.getRowNum();
						
					}
				}
				c++;
			}
			if(rateType == "D"){
				cellno = 2;
			}else if(rateType == "M"){
				cellno = 3;
			}else if(rateType == "P"){
				cellno = 4;
			}else if(rateType == "R"){
				cellno = 5;
			}
			if(rowno == 0){
				System.out.println("inside if rowno == 0");
				Row newrow = sheet.createRow(rownum++);
		    	Cell hotelId = newrow.createCell(0);
		    	hotelId.setCellValue(hotel);
		    	Cell apv = newrow.createCell(1);
		    	apv.setCellValue(ap);
		    	Cell predictedValue = newrow.createCell(cellno);
		    	predictedValue.setCellValue(value);
			}else{
				System.out.println("inside else rowno == 0");
				Row newrow = sheet.getRow(rowno);
		    	
		    	Cell predictedValue = newrow.createCell(cellno);
		    	predictedValue.setCellValue(value);
			}
			
	    	FileOutputStream out = new FileOutputStream(modelfile);
		    workbook.write(out);
		    out.close();
			} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private void handleVector(REXPVector vector,StringBuffer sb,boolean isTerminate) {
    	  if (!isTerminate)   sb.append("c(");
    	  if (vector.isNumeric()) {
    	    double[] value = null;
			try {
				value = vector.asDoubles();
			} catch (REXPMismatchException e) {
				e.printStackTrace();
			}
    	    for (int j=0; j < value.length; j++) {
    	      sb.append(Double.toString(value[j]));
    	      if (j < (value.length - 1))       sb.append(",");
    	    }
    	  }
    	 else   if (vector.isString()) {
    	    String[] values = null;
			try {
				values = vector.asStrings();
			} catch (REXPMismatchException e) {
				e.printStackTrace();
			}
    	    for (int j=0; j < values.length; j++) {
    	      sb.append("'" + values[j] + "'");
    	      if (j < (values.length - 1))       sb.append(",");
    	    }
    	  }
    	  if (!isTerminate)   sb.append(")");
    	}

}
