package com.pivotal.fe.mcp.botnode.xenforo.webviewer;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.pivotal.fe.mcp.botnode.bot.Bot;
import com.pivotal.fe.mcp.botnode.dom.PageViewSummary;

public class WebViewerBot extends Bot {
    private static final Logger log = Logger.getLogger(WebViewerBot.class);

    @Autowired
	ForumViewer viewer;

	@Autowired
    Random randy;

	public WebViewerBot(String login) {
		super(login);
	}

	@Override
	public void doSession()
	{
		try
		{
			//Hit the home page to establish a session
			home();
			Thread.sleep(100);
			
			//Log the user in
			login();
			Thread.sleep(1000);
			
			for(int c=0; c<numActions; c++)
			{
				int randomSleepTime = sleepMin+randy.nextInt(sleepMax-sleepMin);
				int randomAction = randy.nextInt(100);
				
				if(randomAction<10)
				{
					//Create a thread
					startThread();
				}
				else if(randomAction<20)
				{
					//Respond to a thread
					makeRandomPost();
				}
				else if(randomAction<40)
				{
					home();
				}
				else
				{
					getRandomPage();
				}

				Thread.sleep(randomSleepTime);
			}
		}
		catch(Exception e){System.out.println(e.toString());}
	}

	private void home()
	{
		log.info("Bot "+login+" hitting home page");
		PageViewSummary summary = getPVS("home");
		long startTime = System.currentTimeMillis();
		viewer.home();
		updateSummary(summary, System.currentTimeMillis() - startTime);
	}
	
	private void login()
	{
		log.info("Bot "+login+" logging in to forum");
		PageViewSummary summary = getPVS("login");
		long startTime = System.currentTimeMillis();
		viewer.login(login, login);
		updateSummary(summary, System.currentTimeMillis() - startTime);
	}
	
	private void startThread()
	{
		//Make a random post
		PageViewSummary summary = getPVS("startThread");
		int random = 1000+randy.nextInt(999);
		String title = "Title-"+random;
		String message = "Message-"+random;

		log.info("Bot "+login+" creating thread "+title);
		long startTime = System.currentTimeMillis();
		viewer.startThread(title, message);
		updateSummary(summary, System.currentTimeMillis() - startTime);
	}

	private void makeRandomPost()
	{
		log.info("Bot "+login+" making a random post");

		//Make a random reply
		PageViewSummary summary = getPVS("postReply");
		int random = 1000+randy.nextInt(999);
		String message = "Message-"+random;
		long startTime = System.currentTimeMillis();
		viewer.makeRandomPost(message);
		updateSummary(summary, System.currentTimeMillis() - startTime);
	}

	private void getRandomPage()
	{
		log.info("Bot "+login+" viewing a page");
		PageViewSummary summary = getPVS("view");
		long startTime = System.currentTimeMillis();
		viewer.getRandomPage();
		updateSummary(summary, System.currentTimeMillis() - startTime);
	}
	
	private PageViewSummary getPVS(String name)
	{
		PageViewSummary summary = pageViewSummary.get(name);
		if(summary == null) { summary = new PageViewSummary(); summary.name = name; }
		pageViewSummary.put(name, summary);
		return summary;
	}
	
	private void updateSummary(PageViewSummary summary, long time)
	{
		summary.numberOfTrys++;
		
		if(summary.longestTime < time) { summary.longestTime = time; }
		if(summary.shortestTime > time) { summary.shortestTime = time; }
		summary.totalTime+=time;
		summary.averageTime = summary.totalTime/summary.numberOfTrys;
	}
}
