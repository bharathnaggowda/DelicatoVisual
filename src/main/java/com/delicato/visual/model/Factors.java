package com.delicato.visual.model;

import java.util.Date;



public class Factors {
    
	private String startdate;
	private String enddate;
	private String minTempTheshold;
	private String maxTempTheshold;
	private String minHumTheshold;
	private String maxWindTheshold;
	private Date givendate;
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public Date getGivendate(){
		return givendate;
	}
	public void setGivendate(Date givendate) {
		this.givendate = givendate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getMinTempTheshold() {
		return minTempTheshold;
	}
	public void setMinTempTheshold(String minTempTheshold) {
		this.minTempTheshold = minTempTheshold;
	}
	public String getMaxTempTheshold() {
		return maxTempTheshold;
	}
	public void setMaxTempTheshold(String maxTempTheshold) {
		this.maxTempTheshold = maxTempTheshold;
	}
	
	public String getMinHumTheshold() {
		return minHumTheshold;
	}
	public void setMinHumTheshold(String minHumTheshold) {
		this.minHumTheshold = minHumTheshold;
	}
	public String getMaxWindTheshold() {
		return maxWindTheshold;
	}
	public void setMaxWindTheshold(String maxWindTheshold) {
		this.maxWindTheshold = maxWindTheshold;
	}
	
}
