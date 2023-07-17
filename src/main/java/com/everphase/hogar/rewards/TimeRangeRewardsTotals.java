package com.everphase.hogar.rewards;

import java.util.Map;
import java.util.Objects;

import java.util.HashMap;

public class TimeRangeRewardsTotals{

	  private Map<String, Integer> timeRangeRewardsTotals = new HashMap<String, Integer>();
	  
	  public TimeRangeRewardsTotals(){
		  
	  }
	  
	  public Map<String, Integer> getTimeRangeRewardsTotals(){
		  return timeRangeRewardsTotals;
	  }
	
	  public void setTimeRangeRewardsTotal(Map<String, Integer> timeRangeRewardsTotals){
		  this.timeRangeRewardsTotals = timeRangeRewardsTotals;
	  }
	  
	  public void put(String timeRange, Integer rewardsPointsTotal){
		  timeRangeRewardsTotals.put(timeRange, rewardsPointsTotal);
	  }
	  
	  public Integer get(String timeRange){
		  return timeRangeRewardsTotals.get(timeRange);
	  }
	  
	  @Override
	  public boolean equals(Object o) {	
	    if (this == o)
	      return true;
	    if (!(o instanceof TimeRangeRewardsTotals))
	      return false;
	    TimeRangeRewardsTotals timeRangeRewardsTotals = (TimeRangeRewardsTotals) o;
	    return Objects.equals(this.timeRangeRewardsTotals, timeRangeRewardsTotals.timeRangeRewardsTotals);
	  }
	
	  @Override
	  public int hashCode() {
	    return Objects.hash(this.timeRangeRewardsTotals);
	  }
	  
	  @Override
	  public String toString() {
	    return "" + this.timeRangeRewardsTotals;
	  }  
}