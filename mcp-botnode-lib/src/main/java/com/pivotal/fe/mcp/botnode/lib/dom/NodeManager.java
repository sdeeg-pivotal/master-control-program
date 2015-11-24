/**
 * The NodeManager is a singlton in the process that is responsible for
 * controlling the pool of Bots.  It manages their threads, aggregates
 * data, and controls all access to them.
 */
package com.pivotal.fe.mcp.botnode.lib.dom;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.pivotal.fe.mcp.botnode.lib.util.NameUtil;

public class NodeManager
{
    private static final Logger log = Logger.getLogger(NodeManager.class);

    @Autowired
    private ExecutorService executor;
    
    @Autowired
	public List<BotRunner> bots;

    private NodeManagerStatus status = new NodeManagerStatus();
    
	public NodeManager()
	{
		bots = new LinkedList<BotRunner>();
		NodeManagerStatus status = new NodeManagerStatus();
		status.numBots = bots.size();
	}
	
	public NodeManagerStatus initialize()
	{
		return getStatus();
	}
	
	public NodeManagerStatus startBots()
	{
		if(bots.size()>0) { 
			(new Thread(new Runnable() {
				@Override
				public void run() {
					long startTime = System.currentTimeMillis();
					try {
						List<Future<BotResult>> futureStats = executor.invokeAll(bots);
						updateStatus(futureStats,System.currentTimeMillis()-startTime);
						log.info("Done in Start");
					} catch (Exception e) {e.printStackTrace();}
				}
			})).start();
		}
		return getStatus();
	}

	public NodeManagerStatus stopBots()
	{
		return getStatus();
	}
	
	public NodeManagerStatus getStatus()
	{
		//Update with current bot state/stats
		status.botStatsCurrent.clear();
		bots.forEach(bot -> {
			BotResult bs = bot.getStatus();
			status.botStatsCurrent.add(bs);
		});
		
		return status;
	}
	
	private void updateStatus(List<Future<BotResult>> futureStats, long time) throws Exception
	{
		futureStats.forEach(bsFuture -> {
			BotResult bs;
			try {
				bs = bsFuture.get();
				status.botStats.add(bs);
				status.totalTimeActive += time;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
