package com.pivotal.fe.mcp.botnode.lib.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.pivotal.fe.mcp.botnode.lib.MCPBot.MCPBotStart;
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

	//Look for a method annotated with @MCPBotStart.  If none, look for a start() method.
	//TODO: move logic to check for an appropriate sooner in the bot lifecycle.
	public void doSession()
	{
		Method botStart = null;
		try {
			List<Method> methods = Arrays.asList(bot.getClass().getMethods());
			for(Method m : methods) {
				if(AnnotationUtils.findAnnotation(m, MCPBotStart.class)!=null) {
					log.info("Found method "+m.getName()+" annotated with @MCPBotStart");
					botStart = m;
				}
			}
			if(botStart == null) {
				botStart = bot.getClass().getMethod("start");
			}
			if(botStart != null) {
				botStart.invoke(bot);
			}
			else {
				log.error("Tried to start a Bot, but couldn't find a method annotated with @MCPBotStart nor a start() method"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BotResult getStatus()
	{
		return status;
	}
}
