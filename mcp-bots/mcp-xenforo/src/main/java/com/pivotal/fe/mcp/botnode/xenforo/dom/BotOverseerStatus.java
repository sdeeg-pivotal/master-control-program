package com.pivotal.fe.mcp.botnode.xenforo.dom;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class BotOverseerStatus
{
	public String botNetName;
	public int numBots;
	public List<BotStatus> botStats = new LinkedList<>();
	public List<BotStatus> botStatsCurrent = new LinkedList<>();
	public Map<String,PageViewSummary> pageViewSummary = new HashMap<>();
	
	public long totalRequests;
	public long totalTimeRequests;
	public long totalTimeActive;
	public int pvSec;
}
