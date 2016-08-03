package com.delicato.visual.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.delicato.visual.config.SpringMongoConfig;
import com.delicato.visual.model.Factors;
import com.delicato.visual.service.CsvService;
import com.delicato.visual.service.DataService;
import com.delicato.visual.service.SurvivalModel;

@Controller
@RequestMapping("/")
public class VisualController {
		
	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	DataService dataService = new DataService();
	PredictionController pc = new PredictionController();
	SurvivalModel survivalModel = new SurvivalModel();
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	CsvService csvService = new CsvService();
	
	String rootPath =System.getProperty("user.dir");
	File rScript = new File(rootPath+"/src/main/webapp/rQuery.txt");
	List sortedList;
	
	@RequestMapping(value="/home", method=RequestMethod.POST)
	public String home(Factors factors, Model model) 
	{
		sortedList = new ArrayList(dataService.getBlocks());
		Collections.sort(sortedList);
		model.addAttribute("blocks", sortedList);
		return "home";
	}
	
	@RequestMapping(value="/predictions", method=RequestMethod.POST)
	public String predictions(Factors factors, Model model) 
	{
		model.addAttribute("blocks", sortedList);
		
		
		String block = factors.getBlock();
		RConnection con = mongoConfig.getRconnection();
			
			//Harvest
		String harvest = rootPath+"/src/main/webapp/"+block+"Harvest.xls";
		File harvestFile = new File(harvest);
		if(!harvestFile.exists()){
				pc.generateTrainingData("07/25/2015","09/26/2015", harvest,block);
				pc.generateTrainingData("07/25/2014","09/26/2014", harvest,block);
				//pc.generateTrainingData("07/25/2013","09/26/2013", harvest,block);
				//pc.generateTrainingData("07/25/2012","09/26/2012", harvest,block);
				//pc.generateTrainingData("07/25/2011","09/26/2011", harvest,block);
				//pc.generateTrainingData("07/25/2010","09/26/2010", harvest,block);
		}
		if(!con.isConnected()) con = mongoConfig.getRconnection();
		survivalModel.generateModel(con, rScript, harvest);
		
	
			//Veraison
			String veraison = rootPath+"/src/main/webapp/"+block+"Veraison.xls";
			File veraisonFile = new File(veraison);
			if(!veraisonFile.exists()){
				pc.generateTrainingData("07/10/2015","07/26/2015", veraison,block);
				pc.generateTrainingData("07/10/2014","07/26/2014", veraison,block);
				//pc.generateTrainingData("07/10/2013","07/26/2013", veraison,block);
				//pc.generateTrainingData("07/10/2012","07/26/2012", veraison,block);
				//pc.generateTrainingData("07/10/2011","07/26/2011", veraison,block);
				//pc.generateTrainingData("07/10/2010","07/26/2010", veraison,block);
			}
			if(!con.isConnected()) con = mongoConfig.getRconnection();
			survivalModel.generateModel(con, rScript, veraison);
			
			
			//Closure
			String closure = rootPath+"/src/main/webapp/"+block+"Closure.xls";
			File closureFile = new File(closure);
			if(!closureFile.exists()){
				pc.generateTrainingData("06/04/2015","07/11/2015", closure,block);
				pc.generateTrainingData("06/04/2014","07/11/2014", closure,block);
				//pc.generateTrainingData("06/04/2013","07/11/2013", closure,block);
				//pc.generateTrainingData("06/04/2012","07/11/2012", closure,block);
				//pc.generateTrainingData("06/04/2011","07/11/2011", closure,block);
				//pc.generateTrainingData("06/04/2010","07/11/2010", closure,block);
			}
			if(!con.isConnected()) con = mongoConfig.getRconnection();
			survivalModel.generateModel(con, rScript, closure);
			
			
			//Set
			String set = rootPath+"/src/main/webapp/"+block+"Set.xls";
			File setFile = new File(set);
			if(!setFile.exists()){
				pc.generateTrainingData("05/09/2015","06/05/2015", set,block);
				pc.generateTrainingData("05/09/2014","06/05/2014", set,block);
				//pc.generateTrainingData("05/09/2013","06/05/2013", set,block);
				//pc.generateTrainingData("05/09/2012","06/05/2012", set,block);
				//pc.generateTrainingData("05/09/2011","06/05/2011", set,block);
				//pc.generateTrainingData("05/09/2010","06/05/2010", set,block);
			}
			if(!con.isConnected()) con = mongoConfig.getRconnection();
			survivalModel.generateModel(con, rScript, set);
			
			
			//Bloom
			String bloom = rootPath+"/src/main/webapp/"+block+"Bloom.xls";
			File bloomFile = new File(bloom);
			if(!bloomFile.exists()){
				pc.generateTrainingData("03/22/2015","05/10/2015", bloom,block);
				pc.generateTrainingData("03/22/2014","05/10/2014", bloom,block);
				//pc.generateTrainingData("03/22/2013","05/10/2013", bloom,block);
				//pc.generateTrainingData("03/22/2012","05/10/2012", bloom,block);
				//pc.generateTrainingData("03/22/2011","05/10/2011", bloom,block);
				//pc.generateTrainingData("03/22/2010","05/10/2010", bloom,block);
			}
			if(!con.isConnected()) con = mongoConfig.getRconnection();
			survivalModel.generateModel(con, rScript, bloom);
			
			//BudBreak
			String budbreak = rootPath+"/src/main/webapp/"+block+"BudBreak.xls";
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
			survivalModel.generateModel(con, rScript, budbreak);
			
		return "home";
	}
	
	@RequestMapping(value="/degreedays", method=RequestMethod.POST)
	public String degreedays(Factors factors, Model model) 
	{		
		Date sDate = null;
		Date eDate = null;
		int minTempTheshold = Integer.MIN_VALUE;
		int maxTempTheshold = Integer.MAX_VALUE;
		int minHumTheshold = Integer.MIN_VALUE;
		int maxWindTheshold = Integer.MAX_VALUE;
		
		try {
			sDate = format.parse(factors.getStartdate());
			eDate = format.parse(factors.getEnddate());
			if(factors.getMinTempTheshold() != "") minTempTheshold = Integer.parseInt(factors.getMinTempTheshold());
			if(factors.getMaxTempTheshold() != "") maxTempTheshold = Integer.parseInt(factors.getMaxTempTheshold());
			if(factors.getMinHumTheshold() != "") minHumTheshold = Integer.parseInt(factors.getMinHumTheshold());
			if(factors.getMaxWindTheshold() != "") maxWindTheshold = Integer.parseInt(factors.getMaxWindTheshold());
			
			System.out.println(sDate+"--");
			
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
		Date sDate = null;
		Date eDate = null;

		double airtemp=0, dewpoint=0, relhum=0, soiltemp=0,  vappres=0, windspeed=0,winddir=0;
		
		try {
			sDate = format.parse(factors.getStartdate());
			eDate = format.parse(factors.getEnddate());
			
			dataService.getCimisDataForGraphs(sDate, eDate, airtemp, dewpoint, relhum, soiltemp, vappres, windspeed, winddir);
			
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
	
	

}
