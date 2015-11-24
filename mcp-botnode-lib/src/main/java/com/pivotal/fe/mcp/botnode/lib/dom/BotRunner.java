package com.pivotal.fe.mcp.botnode.lib.dom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

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
public class BotRunner implements Callable<BotResult>
{
    private static final Logger log = Logger.getLogger(BotRunner.class);

	@Value("${bot.actions:10}")
	protected int numActions;

	@Value("${bot.sleep.min:100}") 
	protected int sleepMin;

	@Value("${bot.sleep.max:1000}") 
	protected int sleepMax;
	
	public String login;
	
	protected BotResult status;
	private Object bot;
	
	public BotRunner(String login, Object bot)
	{
		this.login = login;
		this.bot = bot;
		
		status = new BotResult();
		status.botName = login;
	}
	
	public void setNumActions(int numActions) { this.numActions = numActions; }

	@Override
	public BotResult call() throws Exception {
		log.info("Bot "+login+" is starting up");
		status.startTime = System.currentTimeMillis();
		status.state = "running";
		
		doSession();
		
		status.stopTime = System.currentTimeMillis();
		status.state = "stopped";
		log.info("Bot "+login+" finished");
		
		return status;
	}
	
	public void doSession()
	{
		try {
			Method botRun = bot.getClass().getMethod("run",null);
			botRun.invoke(bot, null);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BotResult getStatus()
	{
		return status;
	}
}
