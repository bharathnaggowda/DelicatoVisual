package com.delicato.visual.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.delicato.visual.model.Factors;
import com.delicato.visual.service.DataService;

@Controller
@RequestMapping("/")
public class VisualController {
		
	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	DataService dataService = new DataService();
	
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
		
		return "index";
	}
	
	@RequestMapping(value="/cimis", method=RequestMethod.POST)
	public String cimisGraphs(Factors factors, Model model) 
	{
		Date sDate = null;
		Date eDate = null;

		double airtemp=0, dewpoint=0, relhum=0, soiltemp=0, solrad=0, vappres=0, windspeed=0,winddir=0;
		
		try {
			sDate = format.parse(factors.getStartdate());
			eDate = format.parse(factors.getEnddate());
			
			dataService.getCimisDataForGraphs(sDate, eDate, airtemp, dewpoint, relhum, soiltemp,solrad,vappres,windspeed,winddir);
			
			model.addAttribute("startdate", factors.getStartdate());
			model.addAttribute("enddate", factors.getEnddate());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "index";
	}
	
	@RequestMapping(value="/cimissubgraph", method=RequestMethod.POST)
	public String cimisCimisSubGraph(Factors factors, Model model) throws ParseException 
	{
		Date gDate=null;
		double airtemp=0, dewpoint=0, relhum=0, soiltemp=0;
		
		try {
			gDate = factors.getGivendate();
			dataService.getCimisDataForSubGraph(airtemp, dewpoint, relhum, soiltemp,gDate);
			
			model.addAttribute("givendate",factors.getGivendate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "index";
	}

}
