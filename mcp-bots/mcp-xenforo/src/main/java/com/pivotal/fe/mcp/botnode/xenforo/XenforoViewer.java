/**
 * This is where all the Xenforo specific hack-a-dashery and programming sins 
 * take place.  The motto here is:
 * 
 * "What happens in XenforoViewer, stays in XenforoViewer"
 */
package com.pivotal.fe.mcp.botnode.xenforo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.pivotal.fe.mcp.botnode.dom.PageView;
import com.pivotal.fe.mcp.botnode.xenforo.webviewer.ForumViewer;
import com.pivotal.fe.mcp.botnode.xenforo.webviewer.HttpHelper;

public class XenforoViewer implements ForumViewer
{
	@Autowired
	CookieManager cookieManager;
	
    @Autowired
    HttpHelper httpHelper;
    
    @Autowired
    Random randy;
    
    @Value("${webapp.hostname}")
    private String hostName;
    
    @Value("${webapp.url.root}")
    private String urlRoot;
    
    @Value("${webapp.url.home}")
    private String appUrlHome;
    
    @Value("${webapp.url.login}")
    private String appUrlLogin;
    
	List<String> categories = new LinkedList<>();
	List<String> forums = new LinkedList<>();
	List<String> threads = new LinkedList<>();
	
    public XenforoViewer()
    {
    }
    
    public void addCategories(List<String> newCategories)
    {
    	categories.addAll(newCategories);
    }
    
    public void addForums(List<String> newForums)
    {
    	forums.addAll(newForums);
    }
    
    public void addThreads(List<String> newThreads)
    {
    	threads.addAll(newThreads);
    }
    
	@Override
    public PageView home()
    {
    	PageView pageView = new PageView();
    	pageView.setUrl(appUrlHome);
    	
    	try
    	{
        	//Get the homepage to establish the cookies
    		URL url = new URL(urlRoot+appUrlHome);
    		HttpURLConnection con = (HttpURLConnection)url.openConnection();
            BufferedReader httpReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
    		while ((line = httpReader.readLine()) != null)
    		{
//    			//Scan the line
//    			if(line.indexOf("_xfToken\"")>0)
//    			{
//    				int start = line.indexOf("value=\"")+7;
//    				int stop = line.indexOf("\"",start);
//    				//pageView.xfToken = line.substring(start,stop);
//    			}
    		}
    		httpReader.close();
    	}
    	catch(Exception e) {}

    	return httpHelper.doGet(pageView);
    }
    
	@Override
    public PageView login(String login, String password)
    {
    	PageView pageView = new PageView();
    	pageView.setUrl(appUrlHome);
    	try
    	{
			String postData = "login="+URLEncoder.encode(login,"UTF-8");
			postData += "&password="+URLEncoder.encode(password,"UTF-8");
			postData += "&register="+URLEncoder.encode("0","UTF-8");
			postData += "&remember="+URLEncoder.encode("1","UTF-8");
			postData += "&submit="+URLEncoder.encode("Log in","UTF-8");

			//hidden values
			postData += "&cookie_check="+URLEncoder.encode("1","UTF-8");
			postData += "&redirect="+URLEncoder.encode("/index.php","UTF-8");
			postData += "&_xfToken="+URLEncoder.encode("","UTF-8");
			
//			pageView = httpHelper.doPost(pageView, postData);
			URL url = new URL(urlRoot+appUrlLogin);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-length", String.valueOf(postData.length()));
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			//con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
			con.setDoOutput(true);
			con.setDoInput(true);

			//Write the POST data to the server
			pageView.timeStart = System.currentTimeMillis();
			DataOutputStream httpPostOutput = new DataOutputStream(con.getOutputStream());
			httpPostOutput.writeBytes(postData);
			httpPostOutput.close();

			//Scan the response and dump to a file
			BufferedReader httpReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
			while ((line = httpReader.readLine()) != null)
			{
				//Scan the line
//				if(line.indexOf("_xfToken\"")>0)
//				{
//					int start = line.indexOf("value=\"")+7;
//					int stop = line.indexOf("\"",start);
//    				//pageView.xfToken = line.substring(start,stop);
//				}
			}
			httpReader.close();
    		pageView.timeTotal = System.currentTimeMillis()-pageView.timeStart;
    		pageView.responseCode = con.getResponseCode();
    		pageView.responseMessage = con.getResponseMessage();
    		con.disconnect();
    	} catch(Exception ignore){}
    	
    	return pageView;
    }

	@Override
	public PageView getRandomPage()
	{
		String name = "";
		String randomPageURL = "/index.php?forums/"+name;
		int randomPageType = randy.nextInt(2);
		if(randomPageType<1)
		{
			randomPageURL = "/index.php?forums/"+forums.get(randy.nextInt(forums.size()));
		}
		else
		{
			if(threads.size()>0)
			{
				randomPageURL = "/index.php?threads/"+threads.get(randy.nextInt(threads.size()));
			}
			else
			{
				randomPageURL = "/index.php";
			}
		}
    	PageView pageView = new PageView();
    	pageView.setUrl(randomPageURL);
    	
    	return httpHelper.doGet(pageView);
	}

	@Override
	public PageView makeRandomPost(String message)
	{
		PageView pageView = new PageView();
    	pageView.setUrl("");
		if(threads.size() == 0) { return pageView; }
		String randomThreadName = threads.get(randy.nextInt(threads.size()));
		String httpURL  = urlRoot+"/index.php?threads/"+randomThreadName;
		String httpURLAdd = urlRoot+"/index.php?threads/"+randomThreadName+"/add-reply";

		try
		{
			//Get the thread page
			URL url = new URL(httpURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			BufferedReader httpReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String xfToken = "***";
	        String attachmentHash = "1234";
	        String line;
			while ((line = httpReader.readLine()) != null)
			{
				//Scan the line
				if(line.indexOf("_xfToken\"")>0)
				{
					int start = line.indexOf("value=\"")+7;
					int stop = line.indexOf("\"",start);
					xfToken = line.substring(start,stop);
				}
				//Scan the line
				if(line.indexOf("name=\"attachment_hash\"")>0)
				{
					int start = line.indexOf("value=\"")+7;
					int stop = line.indexOf("\"",start);
					attachmentHash = line.substring(start,stop);
				}
			}
			httpReader.close();
			
			String postData = "message_html="+URLEncoder.encode("<p>"+message+"<br></p>","UTF-8")
					+ "&_xfRelativeResolver=http%3A%2F%2F"+hostName+"%2Findex.php%3Fthreads%2Fblah.41%2F"
					+ "&attachment_hash="+attachmentHash
					+ "&last_date=1418322969"
					+ "&last_known_date=1418322969"
				    + "&_xfToken="+URLEncoder.encode(xfToken,"UTF-8");
					
			//Post thread
			url = new URL(httpURLAdd);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-length", String.valueOf(postData.length()));
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Referer", "http://rivals-forum.cfapps.io/index.php?forums/main-forum.2/create-thread");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:30.0) Gecko/20100101 Firefox/30.0");
			con.setDoOutput(true);
			con.setDoInput(true);

			//Write the POST data to the server
			pageView.timeStart = System.currentTimeMillis();
			DataOutputStream httpPostOutput = new DataOutputStream(con.getOutputStream());
			httpPostOutput.writeBytes(postData);
			httpPostOutput.close();

			//Scan the response and dump to a file
//			file = new File("/home/pivotal/dev/rivals/postpost.html");
//			fileOutput = new BufferedWriter(new FileWriter(file));
			httpReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((line = httpReader.readLine()) != null)
			{
				//Scan the line
//				fileOutput.write(line);
			}
			httpReader.close();
//			fileOutput.close();
    		pageView.timeTotal = System.currentTimeMillis()-pageView.timeStart;
    		pageView.responseCode = con.getResponseCode();
    		pageView.responseMessage = con.getResponseMessage();
    		con.disconnect();
		}
		catch(Exception e) { System.out.println(e.toString()); }
		
    	return pageView;
	}

	@Override
	public PageView startThread(String title, String message)
	{
		String randomForumName = forums.get(randy.nextInt(forums.size()));
		String httpURLCreate  = urlRoot+"/index.php?forums/"+randomForumName+"/create-thread";
		String httpURLAdd = urlRoot+"/index.php?forums/"+randomForumName+"/add-thread";

		PageView pageView = new PageView();
    	pageView.setUrl(httpURLAdd);

		try
		{
			//Get the create page and grab 
			URL url = new URL(httpURLCreate);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			BufferedReader httpReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String xfToken = "***";
	        String attachmentHash = "";
	        String line;
			while ((line = httpReader.readLine()) != null)
			{
				//Scan the line
				if(line.indexOf("_xfToken\"")>0)
				{
					int start = line.indexOf("value=\"")+7;
					int stop = line.indexOf("\"",start);
					xfToken = line.substring(start,stop);
				}
				//Scan the line
				if(line.indexOf("name=\"attachment_hash\"")>0)
				{
					int start = line.indexOf("value=\"")+7;
					int stop = line.indexOf("\"",start);
					attachmentHash = line.substring(start,stop);
				}
			}
			httpReader.close();
			
			//System.out.println("Got create thread page.  xfToken="+xfToken+" attachment_hash="+attachmentHash);
			
			//Create the data for a new thread
			String postData = "title="+URLEncoder.encode(title,"UTF-8");
			postData += "&message_html="+URLEncoder.encode("<p>"+message+"<br></p>","UTF-8");
			postData += "&_xfRelativeResolver="+URLEncoder.encode("http://rivals-forum.cfapps.io/index.php?forums/main-forum.2/create-thread","UTF-8");
			postData += "&attachment_hash="+attachmentHash;
			postData += "&watch_thread=1";
			postData += "&watch_thread_email=1";
			postData += "&watch_thread_state=1";
			postData += "&poll%5Bquestion%5D=";
			postData += "&poll%5Bresponses%5D%5B%5D=";
			postData += "&poll%5Bresponses%5D%5B%5D=";
			postData += "&poll%5Bmax_votes_type%5D=single";
			postData += "&poll%5Bchange_vote%5D=1";
			postData += "&poll%5Bview_results_unvoted%5D=1";
			postData += "&_xfRequestUri=%2Findex.php%3Fforums%2F"+randomForumName+"%2Fcreate-thread";
			postData += "&_xfNoRedirect=1";
			postData += "&_xfToken="+URLEncoder.encode(xfToken,"UTF-8");
			postData += "&_xfResponseType=json";
			
			//postData += "&submit="+URLEncoder.encode("Create Thread","UTF-8");

			//Post thread
			url = new URL(httpURLAdd);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-length", String.valueOf(postData.length()));
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Referer", "http://rivals-forum.cfapps.io/index.php?forums/main-forum.2/create-thread");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:30.0) Gecko/20100101 Firefox/30.0");
			con.setDoOutput(true);
			con.setDoInput(true);

			//Write the POST data to the server
			pageView.timeStart = System.currentTimeMillis();
			DataOutputStream httpPostOutput = new DataOutputStream(con.getOutputStream());
			httpPostOutput.writeBytes(postData);
			httpPostOutput.close();

			//Scan the response to get the thread id
			String threadName = "";
	        httpReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((line = httpReader.readLine()) != null)
			{
				//Scan the line
				int start = line.indexOf(title.toLowerCase()+".");
				if(start>0)
				{
					int stop = line.indexOf("\\",start);
					threadName = line.substring(start,stop);
					threads.add(threadName);
				}
			}
			httpReader.close();
    		pageView.timeTotal = System.currentTimeMillis()-pageView.timeStart;
    		pageView.responseCode = con.getResponseCode();
    		pageView.responseMessage = con.getResponseMessage();
    		con.disconnect();
		}
		catch(Exception e){System.out.println(e.toString());}
		
		return pageView;
	}
}
