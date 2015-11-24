package com.pivotal.fe.mcp.botnode.lib.dom;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class BotResult
{
	public String botName;
	public String state = "stopped";
	public long startTime=0;
	public long stopTime=0;
	public Map<String,Object> results = new HashMap<>();
}
