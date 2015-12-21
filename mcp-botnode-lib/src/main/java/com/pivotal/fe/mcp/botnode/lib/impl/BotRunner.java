package com.pivotal.fe.mcp.botnode.lib.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pivotal.fe.mcp.botnode.lib.dom.BotResult;

/**
 * 
 * @author Scott Deeg
 *
 * This class wraps a Bot in a Callable.  When called it 
 * executes the appropriate method on the Bot.  The order is:
 * 
 * The Bot contains a single method
 * The Bot contains a method annotated with @MCPBotRun
 * 
 * The method can return void, null, or some data.  If returning data it should be
 * JSON friendly.  Like a Map<String,Object>.
 */
@Component
@Scope("prototype")
public class BotRunner implements Callable<BotResult>
{
    private static final Logger log = Logger.getLogger(BotRunner.class);

	@Value("${bot.actions:10}")
	protected int numActions;

	@Value("${bot.sleep.min:100}") 
	protected int sleepMin;

	@Value("${bot.sleep.max:1000}") 
	protected int sleepMax;
	
	public String name;
	
	protected BotResult status;
	private Object bot;
	
	public BotRunner()
	{
		name = "constructor_name";
		status = new BotResult();
		status.botName = name;
		
		log.info("In BotRunner Create");
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBot(Object bot) {
		this.bot = bot;
	}
	
	public void setNumActions(int numActions) { this.numActions = numActions; }

	@Override
	public BotResult call() throws Exception {
		log.info("Bot "+name+" is starting up");
		if(bot == null) {
			log.error("The Bot is null");
		}
		status.startTime = System.currentTimeMillis();
		status.state = "running";
		
		if(bot != null) {
			doSession();
		}
		else {
			log.error("The bot is null, can not do anything in this BotRunner");
		}
		
		status.stopTime = System.currentTimeMillis();
		status.state = "stopped";
		log.info("Bot "+name+" finished");
		
		return status;
	}
	
	public void doSession()
	{
		try {
			Method botRun = bot.getClass().getMethod("run");
			botRun.invoke(bot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BotResult getStatus()
	{
		return status;
	}
}
