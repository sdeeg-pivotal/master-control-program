package com.pivotal.fe.mcp.botnode.lib.dom;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class NodeManagerStatus
{
	public String botNetName;
	public int numBots;
	public List<BotResult> botStats = new LinkedList<>();
	public List<BotResult> botStatsCurrent = new LinkedList<>();
	
	public long totalRequests;
	public long totalTimeRequests;
	public long totalTimeActive;
	public int pvSec;
}
