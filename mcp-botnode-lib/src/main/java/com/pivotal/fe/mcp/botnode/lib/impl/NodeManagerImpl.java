/**
 * The NodeManager is a singleton in the process that is responsible for
 * controlling the pool of Bots.  It manages their threads, aggregates
 * data, and controls all access to them.
 */
package com.pivotal.fe.mcp.botnode.lib.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.pivotal.fe.mcp.botnode.lib.api.NodeManager;
import com.pivotal.fe.mcp.botnode.lib.dom.BotResult;
import com.pivotal.fe.mcp.botnode.lib.dom.NodeManagerStatus;

public class NodeManagerImpl implements NodeManager
{
    private static final Logger log = Logger.getLogger(NodeManagerImpl.class);

    @Autowired
    private ExecutorService executor;
    
	public List<BotRunner> listOfBots;

    private NodeManagerStatus status = new NodeManagerStatus();
    
    @Autowired
    ApplicationContext context;

	public NodeManagerImpl(List<BotRunner> listOfBots) {
		this.listOfBots = listOfBots;
		NodeManagerStatus status = new NodeManagerStatus();
		status.numBots = listOfBots != null ? listOfBots.size() : 0;
	}

	//TODO: determine if there is anything to do here, otherwise get rid of it
	@Override
	public NodeManagerStatus initialize()
	{
		return getStatus();
	}
	
	@Override
	public NodeManagerStatus startBots()
	{
		log.info("In startBots()");
		if(listOfBots ==  null) {
			log.info("getting the list of bots from the context");
			listOfBots = (List<BotRunner>) context.getBean("listOfBots");
		}

		if(listOfBots.size()>0) { 
			(new Thread(new Runnable() {
				@Override
				public void run() {
					long startTime = System.currentTimeMillis();
					try {
						List<Future<BotResult>> futureStats = executor.invokeAll(listOfBots);
						updateStatus(futureStats,System.currentTimeMillis()-startTime);
						log.info("Done in Start");
					} catch (Exception e) {e.printStackTrace();}
				}
			})).start();
		}
		else {
			log.error("The command to start the Bots came, but there are no bots to start.");
		}
		return getStatus();
	}

	@Override
	//TODO: shut down the executor
	public NodeManagerStatus stopBots()
	{
		return getStatus();
	}
	
	@Override
	public NodeManagerStatus getStatus()
	{
		//Update with current bot state/stats
		status.botStatsCurrent.clear();
		if(listOfBots != null) {
			listOfBots.forEach(bot -> {
				BotResult bs = bot.getStatus();
				status.botStatsCurrent.add(bs);
			});
		}
		
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
