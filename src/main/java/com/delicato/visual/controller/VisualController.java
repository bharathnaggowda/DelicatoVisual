package com.delicato.visual.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.delicato.visual.config.SpringMongoConfig;
import com.delicato.visual.model.Factors;
import com.delicato.visual.service.CimisToMongodb;
import com.delicato.visual.service.CompareData;
import com.delicato.visual.service.CsvService;
import com.delicato.visual.service.DataService;
import com.delicato.visual.service.SurvivalModel;

@Controller
@RequestMapping("/")
public class VisualController {
		
	private final Properties configProp = new Properties();
	int comparision_start_year;
	int comparision_end_year;
	String mongolink;
	CompareData compareData;
	CimisToMongodb cimisToMongodb;
	DataService dataService;
	PredictionController pc;
	SurvivalModel survivalModel;
	SpringMongoConfig mongoConfig;
	CsvService csvService;
	
	String rootPath;
	File rScript;
	File folder;
	
	SimpleDateFormat format;
	SimpleDateFormat mFormat;
	List<String> sortedBlocksList;
	List<String> sortedGrapesList;
	
	private VisualController(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.properties");
	      System.out.println("Read all properties from file");
	      try {
	          configProp.load(in);
	          comparision_start_year = Integer.parseInt(configProp.getProperty("comparision_start_year"));
	          comparision_end_year = Integer.parseInt(configProp.getProperty("comparision_end_year"));
	          mongolink = configProp.getProperty("mongolink");
	          System.out.println("mongolink---->"+mongolink);
	          compareData = new CompareData(mongolink);
	          cimisToMongodb = new CimisToMongodb(mongolink);
	          dataService = new DataService(mongolink);
	          pc = new PredictionController(mongolink);
	          
	          format = new SimpleDateFormat("MM/dd/yyyy");
	      	  mFormat = new SimpleDateFormat("yyyy-MM-dd");
	      	
	      	 survivalModel = new SurvivalModel();
	      	 mongoConfig=new SpringMongoConfig();
	      	 csvService = new CsvService();
	      	
	      	 rootPath =System.getProperty("user.dir");
	      	 rScript = new File(rootPath+"/src/main/webapp/rQuery.txt");
	      	 folder = new File(rootPath+"/src/main/webapp");
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	}
	
	@RequestMapping(value="/home", method=RequestMethod.POST)
	public String home(Factors factors, Model model) 
	{
		sortedBlocksList = new ArrayList<String>(dataService.getBlocks());
		Collections.sort(sortedBlocksList);
		model.addAttribute("blocks", sortedBlocksList);
		
		sortedGrapesList = new ArrayList<String>(dataService.getGrapeVeriety());
		Collections.sort(sortedGrapesList);
		model.addAttribute("grapes", sortedGrapesList);
		try{
			for (File file : folder.listFiles()) {
				   if (file.getName().endsWith(".xls") || file.getName().endsWith(".csv")) {
				    file.delete();
				   }
				  }
		}catch(Exception e){
			e.printStackTrace();
		}
		  
		
		return "home";
	}
	
	
	@RequestMapping(value="/degreedays", method=RequestMethod.POST)
	public String degreedays(Factors factors, Model model) 
	{		
		try{
			for (File file : folder.listFiles()) {
				   if (file.getName().endsWith(".xls") || file.getName().endsWith(".csv")) {
				    file.delete();
				   }
				  }
		}catch(Exception e){
			e.printStackTrace();
		}
		Date sDate = null;
		Date eDate = null;
		int minTempTheshold = Integer.MIN_VALUE;
		int maxTempTheshold = Integer.MAX_VALUE;
		int minHumTheshold = Integer.MIN_VALUE;
		int maxWindTheshold = Integer.MAX_VALUE;
		
		model.addAttribute("blocks", sortedBlocksList);
		model.addAttribute("grapes", sortedGrapesList);
		
		try {
			sDate = format.parse(factors.getStartdate());
			eDate = format.parse(factors.getEnddate());
			if(factors.getMinTempTheshold() != "") minTempTheshold = Integer.parseInt(factors.getMinTempTheshold());
			if(factors.getMaxTempTheshold() != "") maxTempTheshold = Integer.parseInt(factors.getMaxTempTheshold());
			if(factors.getMinHumTheshold() != "") minHumTheshold = Integer.parseInt(factors.getMinHumTheshold());
			if(factors.getMaxWindTheshold() != "") maxWindTheshold = Integer.parseInt(factors.getMaxWindTheshold());
			
			//System.out.println(sDate+"--");
			
			dataService.getCimisData(sDate, eDate, minTempTheshold, maxTempTheshold, minHumTheshold, maxWindTheshold);
			
			model.addAttribute("startdate", factors.getStartdate());
			model.addAttribute("enddate", factors.getEnddate());
			model.addAttribute("minTempTheshold", minTempTheshold);
			model.addAttribute("maxTempTheshold", maxTempTheshold);
			model.addAttribute("minHumTheshold", minHumTheshold);
			model.addAttribute("maxWindTheshold", maxWindTheshold);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@RequestMapping(value="/cimis", method=RequestMethod.POST)
	public String cimisGraphs(Factors factors, Model model) 
	{
		try{
			for (File file : folder.listFiles()) {
				   if (file.getName().endsWith(".xls") || file.getName().endsWith(".csv")) {
				    file.delete();
				   }
				  }
		}catch(Exception e){
			e.printStackTrace();
		}
		Date sDate = null;
		Date eDate = null;

		model.addAttribute("blocks", sortedBlocksList);
		model.addAttribute("grapes", sortedGrapesList);
		double airtemp=0, dewpoint=0, relhum=0, soiltemp=0,  vappres=0, windspeed=0,winddir=0;
		
		try {
			sDate = format.parse(factors.getStartdate());
			eDate = format.parse(factors.getEnddate());
			
			HashMap<String, ArrayList<Double>> windRose = dataService.getCimisDataForGraphs(sDate, eDate, airtemp, dewpoint, relhum, soiltemp, vappres, windspeed, winddir);
			
			Iterator it = windRose.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        model.addAttribute(pair.getKey().toString(), pair.getValue());
		        System.out.println("------>"+pair.getKey()+"    "+pair.getValue());
		    }
			model.addAttribute("startdate", factors.getStartdate());
			model.addAttribute("enddate", factors.getEnddate());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@RequestMapping(value="/cimissubgraph", method=RequestMethod.POST)
	public String cimisCimisSubGraph(Factors factors, Model model) throws ParseException 
	{
		
		model.addAttribute("blocks", sortedBlocksList);
		model.addAttribute("grapes", sortedGrapesList);
		Date gDate=null;
		double airtemp=0, dewpoint=0, relhum=0, soiltemp=0 ,vappres=0, windspeed=0,winddir=0;
		
		try {
			gDate = factors.getGivendate();
			dataService.getCimisDataForSubGraph(airtemp, dewpoint, relhum, soiltemp, vappres, windspeed, winddir, gDate);
			
			model.addAttribute("startdate", factors.getStartdate());
			model.addAttribute("enddate", factors.getEnddate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@RequestMapping(value="/comparisions", method=RequestMethod.POST)
	public String comparisions(Factors factors, Model model) 
	{
		try{
			for (File file : folder.listFiles()) {
				   if (file.getName().endsWith(".xls") || file.getName().endsWith(".csv")) {
				    file.delete();
				   }
				  }
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("blocks", sortedBlocksList);
		model.addAttribute("grapes", sortedGrapesList);
		String block = factors.getBlock();
		
		try {
			
			for(int i=comparision_start_year ; i<comparision_end_year ; i++){
				compareData.generateIndividualGraphs(block, "01/01/"+i, "12/01/"+i, i, "dyostemCompare"+i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("blockC", block);
			
		return "home";
	}
	@RequestMapping(value="/blocknames", method=RequestMethod.POST)
	public String dyostemblockname(Factors factors, Model model) throws ParseException 
	{
		try{
			for (File file : folder.listFiles()) {
				   if (file.getName().endsWith(".xls") || file.getName().endsWith(".csv")) {
				    file.delete();
				   }
				  }
		}catch(Exception e){
			e.printStackTrace();
		}
		String grapename=null;
		String blockname="";
	     double tapbrix=0;
		//Date qDate=null;
	     model.addAttribute("blocks", sortedBlocksList);
			model.addAttribute("grapes", sortedGrapesList);
		try {
			grapename = factors.getGrapename();
			//qDate = factors.getGivendate();
			dataService.getblockname(blockname,grapename,tapbrix);
			
			model.addAttribute("grapename",factors.getGrapename());
			//model.addAttribute("givendate",factors.getGivendate());
			model.addAttribute("selectedgrape", grapename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	@RequestMapping(value="/predictions", method=RequestMethod.POST)
	public String predictions(Factors factors, Model model) 
	{
		try{
			try{
				for (File file : folder.listFiles()) {
					   if (file.getName().endsWith(".xls") || file.getName().endsWith(".csv")) {
					    file.delete();
					   }
					  }
			}catch(Exception e){
				e.printStackTrace();
			}
			
			model.addAttribute("blocks", sortedBlocksList);
			model.addAttribute("grapes", sortedGrapesList);
			String block = factors.getBlock();
			
			
			RConnection con = mongoConfig.getRconnection();
				
				//Harvest
			String harvest = rootPath+"/src/main/webapp/"+block+"Harvest.xls";
			String harvestCurrent = rootPath+"/src/main/webapp/"+block+"HarvestCurrent.xls";
			pc.generateTrainingData("07/25/2015","09/26/2015", harvestCurrent,block);
			File harvestFile = new File(harvest);
			if(!harvestFile.exists()){
					
					pc.generateTrainingData("07/25/2015","09/26/2015", harvest,block);
					pc.generateTrainingData("07/25/2014","09/26/2014", harvest,block);
					pc.generateTrainingData("07/25/2013","09/26/2013", harvest,block);
					//pc.generateTrainingData("07/25/2012","09/26/2012", harvest,block);
					//pc.generateTrainingData("07/25/2011","09/26/2011", harvest,block);
					//pc.generateTrainingData("07/25/2010","09/26/2010", harvest,block);
			}
			if(!con.isConnected()) con = mongoConfig.getRconnection();
			List<Double> predValueHarvest = survivalModel.generateModel(con, rScript, harvest, harvestCurrent);
			if(predValueHarvest != null){
				model.addAttribute("predValueHarvest", String.format( "%.2f", predValueHarvest.get(0)));
				model.addAttribute("predValueHarvestOne", predValueHarvest.get(1).intValue());
				model.addAttribute("predValueHarvestTwo", predValueHarvest.get(2).intValue());
				model.addAttribute("predValueHarvestThree", predValueHarvest.get(3).intValue());
				model.addAttribute("predValueHarvestFour", predValueHarvest.get(4).intValue());
				model.addAttribute("predValueHarvestFive", predValueHarvest.get(5).intValue());
				model.addAttribute("predValueHarvestSix", predValueHarvest.get(6).intValue());
				model.addAttribute("predValueHarvestSeven", predValueHarvest.get(7).intValue());
				model.addAttribute("predValueHarvestEight", predValueHarvest.get(8).intValue());
				model.addAttribute("predValueHarvestNine", predValueHarvest.get(9).intValue());
				model.addAttribute("predValueHarvestTen", predValueHarvest.get(10).intValue());
			}else{
				model.addAttribute("predValueHarvest", 0);
				model.addAttribute("predValueHarvestOne", 0);
				model.addAttribute("predValueHarvestTwo", 0);
				model.addAttribute("predValueHarvestThree", 0);
				model.addAttribute("predValueHarvestFour", 0);
				model.addAttribute("predValueHarvestFive", 0);
				model.addAttribute("predValueHarvestSix", 0);
				model.addAttribute("predValueHarvestSeven", 0);
				model.addAttribute("predValueHarvestEight", 0);
				model.addAttribute("predValueHarvestNine", 0);
				model.addAttribute("predValueHarvestTen", 0);
				
			}
			
				//Veraison
				String veraison = rootPath+"/src/main/webapp/"+block+"Veraison.xls";
				String veraisonCurrent = rootPath+"/src/main/webapp/"+block+"veraisonCurrent.xls";
				pc.generateTrainingData("07/10/2015","07/26/2015", veraisonCurrent,block);
				File veraisonFile = new File(veraison);
				if(!veraisonFile.exists()){
					
					pc.generateTrainingData("07/10/2015","07/26/2015", veraison,block);
					pc.generateTrainingData("07/10/2014","07/26/2014", veraison,block);
					pc.generateTrainingData("07/10/2013","07/26/2013", veraison,block);
					//pc.generateTrainingData("07/10/2012","07/26/2012", veraison,block);
					//pc.generateTrainingData("07/10/2011","07/26/2011", veraison,block);
					//pc.generateTrainingData("07/10/2010","07/26/2010", veraison,block);
				}
				if(!con.isConnected()) con = mongoConfig.getRconnection();
				List<Double> predValueVeraison = survivalModel.generateModel(con, rScript, veraison, veraisonCurrent);
				
				if(predValueVeraison != null){
					model.addAttribute("predValueVeraison", String.format( "%.2f", predValueVeraison.get(0)));
					model.addAttribute("predValueVeraisonOne", predValueVeraison.get(1).intValue());
					model.addAttribute("predValueVeraisonTwo", predValueVeraison.get(2).intValue());
					model.addAttribute("predValueVeraisonThree", predValueVeraison.get(3).intValue());
					model.addAttribute("predValueVeraisonFour", predValueVeraison.get(4).intValue());
					model.addAttribute("predValueVeraisonFive", predValueVeraison.get(5).intValue());
					model.addAttribute("predValueVeraisonSix", predValueVeraison.get(6).intValue());
					model.addAttribute("predValueVeraisonSeven", predValueVeraison.get(7).intValue());
					model.addAttribute("predValueVeraisonEight", predValueVeraison.get(8).intValue());
					model.addAttribute("predValueVeraisonNine", predValueVeraison.get(9).intValue());
					model.addAttribute("predValueVeraisonTen", predValueVeraison.get(10).intValue());
				}else{
					model.addAttribute("predValueVeraison", 0);
					model.addAttribute("predValueVeraisonOne", 0);
					model.addAttribute("predValueVeraisonTwo", 0);
					model.addAttribute("predValueVeraisonThree", 0);
					model.addAttribute("predValueVeraisonFour", 0);
					model.addAttribute("predValueVeraisonFive", 0);
					model.addAttribute("predValueVeraisonSix", 0);
					model.addAttribute("predValueVeraisonSeven", 0);
					model.addAttribute("predValueVeraisonEight", 0);
					model.addAttribute("predValueVeraisonNine", 0);
					model.addAttribute("predValueVeraisonTen", 0);
				}
				
				
				//Closure
				String closure = rootPath+"/src/main/webapp/"+block+"Closure.xls";
				String closureCurrent = rootPath+"/src/main/webapp/"+block+"ClosureCurrent.xls";
				pc.generateTrainingData("06/04/2015","07/11/2015", closureCurrent,block);
				File closureFile = new File(closure);
				if(!closureFile.exists()){
					
					pc.generateTrainingData("06/04/2015","07/11/2015", closure,block);
					pc.generateTrainingData("06/04/2014","07/11/2014", closure,block);
					pc.generateTrainingData("06/04/2013","07/11/2013", closure,block);
					//pc.generateTrainingData("06/04/2012","07/11/2012", closure,block);
					//pc.generateTrainingData("06/04/2011","07/11/2011", closure,block);
					//pc.generateTrainingData("06/04/2010","07/11/2010", closure,block);
				}
				if(!con.isConnected()) con = mongoConfig.getRconnection();
				List<Double> predValueClosure = survivalModel.generateModel(con, rScript, closure, closureCurrent);
				
				if(predValueClosure != null){
					model.addAttribute("predValueClosure", String.format( "%.2f", predValueClosure.get(0)));
					model.addAttribute("predValueClosureOne", predValueClosure.get(1).intValue());
					model.addAttribute("predValueClosureTwo", predValueClosure.get(2).intValue());
					model.addAttribute("predValueClosureThree", predValueClosure.get(3).intValue());
					model.addAttribute("predValueClosureFour", predValueClosure.get(4).intValue());
					model.addAttribute("predValueClosureFive", predValueClosure.get(5).intValue());
					model.addAttribute("predValueClosureSix", predValueClosure.get(6).intValue());
					model.addAttribute("predValueClosureSeven", predValueClosure.get(7).intValue());
					model.addAttribute("predValueClosureEight", predValueClosure.get(8).intValue());
					model.addAttribute("predValueClosureNine", predValueClosure.get(9).intValue());
					model.addAttribute("predValueClosureTen", predValueClosure.get(10).intValue());
				}else{
					model.addAttribute("predValueClosure", 0);
					model.addAttribute("predValueClosureOne", 0);
					model.addAttribute("predValueClosureTwo", 0);
					model.addAttribute("predValueClosureThree", 0);
					model.addAttribute("predValueClosureFour", 0);
					model.addAttribute("predValueClosureFive", 0);
					model.addAttribute("predValueClosureSix", 0);
					model.addAttribute("predValueClosureSeven", 0);
					model.addAttribute("predValueClosureEight", 0);
					model.addAttribute("predValueClosureNine", 0);
					model.addAttribute("predValueClosureTen", 0);
				}
				
				
				//Set
				String set = rootPath+"/src/main/webapp/"+block+"Set.xls";
				String setCurrent = rootPath+"/src/main/webapp/"+block+"SetCurrent.xls";
				pc.generateTrainingData("05/09/2015","06/05/2015", setCurrent,block);
				File setFile = new File(set);
				if(!setFile.exists()){
					
					pc.generateTrainingData("05/09/2015","06/05/2015", set,block);
					pc.generateTrainingData("05/09/2014","06/05/2014", set,block);
					pc.generateTrainingData("05/09/2013","06/05/2013", set,block);
					//pc.generateTrainingData("05/09/2012","06/05/2012", set,block);
					//pc.generateTrainingData("05/09/2011","06/05/2011", set,block);
					//pc.generateTrainingData("05/09/2010","06/05/2010", set,block);
				}
				if(!con.isConnected()) con = mongoConfig.getRconnection();
				List<Double> predValueSet = survivalModel.generateModel(con, rScript, set, setCurrent);
				
				if(predValueSet != null){
					model.addAttribute("predValueSet", String.format( "%.2f", predValueSet.get(0)));
					model.addAttribute("predValueSetOne", predValueSet.get(1).intValue());
					model.addAttribute("predValueSetTwo", predValueSet.get(2).intValue());
					model.addAttribute("predValueSetThree", predValueSet.get(3).intValue());
					model.addAttribute("predValueSetFour", predValueSet.get(4).intValue());
					model.addAttribute("predValueSetFive", predValueSet.get(5).intValue());
					model.addAttribute("predValueSetSix", predValueSet.get(6).intValue());
					model.addAttribute("predValueSetSeven", predValueSet.get(7).intValue());
					model.addAttribute("predValueSetEight", predValueSet.get(8).intValue());
					model.addAttribute("predValueSetNine", predValueSet.get(9).intValue());
					model.addAttribute("predValueSetTen", predValueSet.get(10).intValue());
				}else{
					model.addAttribute("predValueSet", 0);
					model.addAttribute("predValueSetOne", 0);
					model.addAttribute("predValueSetTwo", 0);
					model.addAttribute("predValueSetThree", 0);
					model.addAttribute("predValueSetFour", 0);
					model.addAttribute("predValueSetFive", 0);
					model.addAttribute("predValueSetSix", 0);
					model.addAttribute("predValueSetSeven", 0);
					model.addAttribute("predValueSetEight", 0);
					model.addAttribute("predValueSetNine", 0);
					model.addAttribute("predValueSetTen", 0);
				}
				
				
				//Bloom
				String bloom = rootPath+"/src/main/webapp/"+block+"Bloom.xls";
				String bloomCurrent = rootPath+"/src/main/webapp/"+block+"BloomCurrent.xls";
				pc.generateTrainingData("03/22/2015","05/10/2015", bloomCurrent,block);
				File bloomFile = new File(bloom);
				if(!bloomFile.exists()){
					
					pc.generateTrainingData("03/22/2015","05/10/2015", bloom,block);
					pc.generateTrainingData("03/22/2014","05/10/2014", bloom,block);
					pc.generateTrainingData("03/22/2013","05/10/2013", bloom,block);
					//pc.generateTrainingData("03/22/2012","05/10/2012", bloom,block);
					//pc.generateTrainingData("03/22/2011","05/10/2011", bloom,block);
					//pc.generateTrainingData("03/22/2010","05/10/2010", bloom,block);
				}
				if(!con.isConnected()) con = mongoConfig.getRconnection();
				List<Double> predValueBloom = survivalModel.generateModel(con, rScript, bloom, bloomCurrent);
				if(predValueBloom != null){
					
					model.addAttribute("predValueBloom", String.format( "%.2f", predValueBloom.get(0)));
					model.addAttribute("predValueBloomOne", predValueBloom.get(1).intValue());
					model.addAttribute("predValueBloomTwo", predValueBloom.get(2).intValue());
					model.addAttribute("predValueBloomThree", predValueBloom.get(3).intValue());
					model.addAttribute("predValueBloomFour", predValueBloom.get(4).intValue());
					model.addAttribute("predValueBloomFive", predValueBloom.get(5).intValue());
					model.addAttribute("predValueBloomSix", predValueBloom.get(6).intValue());
					model.addAttribute("predValueBloomSeven", predValueBloom.get(7).intValue());
					model.addAttribute("predValueBloomEight", predValueBloom.get(8).intValue());
					model.addAttribute("predValueBloomNine", predValueBloom.get(9).intValue());
					model.addAttribute("predValueBloomTen", predValueBloom.get(10).intValue());
				}else{
					model.addAttribute("predValueBloom", 0);
					model.addAttribute("predValueBloomOne", 0);
					model.addAttribute("predValueBloomTwo", 0);
					model.addAttribute("predValueBloomThree", 0);
					model.addAttribute("predValueBloomFour", 0);
					model.addAttribute("predValueBloomFive", 0);
					model.addAttribute("predValueBloomSix", 0);
					model.addAttribute("predValueBloomSeven", 0);
					model.addAttribute("predValueBloomEight", 0);
					model.addAttribute("predValueBloomNine", 0);
					model.addAttribute("predValueBloomTen", 0);
				}
				
				
				//BudBreak
				String budbreak = rootPath+"/src/main/webapp/"+block+"BudBreak.xls";
				String budbreakCurrent = rootPath+"/src/main/webapp/"+block+"BudBreakCurrent.xls";
				pc.generateTrainingData("12/01/2015","03/23/2015", budbreakCurrent,block);
				File budbreakFile = new File(budbreak);
				if(!budbreakFile.exists()){
					
					pc.generateTrainingData("12/01/2014","03/23/2015", budbreak,block);
					pc.generateTrainingData("12/01/2013","03/23/2014", budbreak,block);
					//pc.generateTrainingData("12/01/2012","03/23/2013", budbreak,block);
					//pc.generateTrainingData("12/01/2011","03/23/2012", budbreak,block);
					//pc.generateTrainingData("12/01/2010","03/23/2011", budbreak,block);
					//pc.generateTrainingData("12/01/2009","03/23/2010", budbreak,block);
				}
				
				if(!con.isConnected()) con = mongoConfig.getRconnection();
				List<Double> predValueBudBreak = survivalModel.generateModel(con, rScript, budbreak, budbreakCurrent);
				if(predValueBudBreak != null){
					model.addAttribute("predValueBudBreak", String.format( "%.2f", predValueBudBreak.get(0)));
					model.addAttribute("predValueBudBreakOne", predValueBudBreak.get(1).intValue());
					model.addAttribute("predValueBudBreakTwo", predValueBudBreak.get(2).intValue());
					model.addAttribute("predValueBudBreakThree", predValueBudBreak.get(3).intValue());
					model.addAttribute("predValueBudBreakFour", predValueBudBreak.get(4).intValue());
					model.addAttribute("predValueBudBreakFive", predValueBudBreak.get(5).intValue());
					model.addAttribute("predValueBudBreakSix", predValueBudBreak.get(6).intValue());
					model.addAttribute("predValueBudBreakSeven", predValueBudBreak.get(7).intValue());
					model.addAttribute("predValueBudBreakEight", predValueBudBreak.get(8).intValue());
					model.addAttribute("predValueBudBreakNine", predValueBudBreak.get(9).intValue());
					model.addAttribute("predValueBudBreakTen", predValueBudBreak.get(10).intValue());
				}else{
					model.addAttribute("predValueBudBreak", 0);
					model.addAttribute("predValueBudBreakOne", 0);
					model.addAttribute("predValueBudBreakTwo", 0);
					model.addAttribute("predValueBudBreakThree", 0);
					model.addAttribute("predValueBudBreakFour", 0);
					model.addAttribute("predValueBudBreakFive", 0);
					model.addAttribute("predValueBudBreakSix", 0);
					model.addAttribute("predValueBudBreakSeven", 0);
					model.addAttribute("predValueBudBreakEight", 0);
					model.addAttribute("predValueBudBreakNine", 0);
					model.addAttribute("predValueBudBreakTen", 0);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
			return "home";
	}
	
	@RequestMapping(value="/cimisupdate", method=RequestMethod.POST)
	public String updatecimis(Factors factors, Model model) 
	{
		//System.out.println("--->inside /cimisupdate");
		cimisToMongodb.getCimisData();
		model.addAttribute("blocks", sortedBlocksList);
		
		model.addAttribute("grapes", sortedGrapesList);
		for(String c :sortedGrapesList){
			//System.out.println(c);
		}
		
		return "home";
	}

}
