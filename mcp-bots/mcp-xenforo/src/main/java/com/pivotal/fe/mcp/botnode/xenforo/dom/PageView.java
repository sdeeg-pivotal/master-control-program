package com.pivotal.fe.mcp.botnode.xenforo.dom;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PageView
{
	public long id = 0;
	public String url = "";
	public String urlBase = "";
	public int responseCode = 0;
	public String responseMessage = "";
	public long timeStart = 0;
	public long timeTotal = 0;
	public Map<String,String> properties = new HashMap<>();
}
