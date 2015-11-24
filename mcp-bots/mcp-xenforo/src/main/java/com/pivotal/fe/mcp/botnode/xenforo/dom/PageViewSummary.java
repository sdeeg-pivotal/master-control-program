package com.pivotal.fe.mcp.botnode.xenforo.dom;

import lombok.Data;

@Data
public class PageViewSummary
{
	public String url;
	public String name="";
	public int numberOfTrys;
	public int numberOfErrors;
	public long longestTime;
	public long shortestTime=99999;
	public long totalTime;
	public long averageTime;
	public float stdDev;
}
