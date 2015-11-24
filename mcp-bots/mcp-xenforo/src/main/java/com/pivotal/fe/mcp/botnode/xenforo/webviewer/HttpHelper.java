package com.pivotal.fe.mcp.botnode.xenforo.webviewer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pivotal.fe.mcp.botnode.dom.PageView;

@Component
public class HttpHelper
{
    @Value("${webapp.url.root}")
    private String urlRoot;
    
    public PageView doGet(PageView pageView)
    {
    	try
    	{
    		//Get the homepage to establish the cookies
    		URL url = new URL(urlRoot+pageView.url);
    		HttpURLConnection con = (HttpURLConnection)url.openConnection();
    		pageView.timeStart = System.currentTimeMillis();

    		//Xenforo Hack ... turn into something that makes use of a lambda.
    		BufferedReader httpReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String xfToken = "***";
            String line;
    		while ((line = httpReader.readLine()) != null)
    		{
    			//Scan the line
    			if(line.indexOf("_xfToken\"")>0)
    			{
    				int start = line.indexOf("value=\"")+7;
    				int stop = line.indexOf("\"",start);
    				//pageView.xfToken = line.substring(start,stop);
    			}
    		}
    		httpReader.close();
    		
//    		con.getContent();
    		pageView.timeTotal = System.currentTimeMillis()-pageView.timeStart;
    		pageView.responseCode = con.getResponseCode();
    		pageView.responseMessage = con.getResponseMessage();
    		con.disconnect();
    	}
    	catch(Exception e) {}

    	return pageView;
    }
    
    public PageView doPost(PageView pageView, String postData)
    {
    	try
    	{
    		//Get the homepage to establish the cookies
    		URL url = new URL(urlRoot+pageView.url);
    		HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-length", String.valueOf(postData.length()));
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			//con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
			con.setDoOutput(true);
			con.setDoInput(true);

			DataOutputStream httpPostOutput = new DataOutputStream(con.getOutputStream());

			//Write the POST data to the server, and get the response
			pageView.timeStart = System.currentTimeMillis();
			httpPostOutput.writeBytes(postData);
			httpPostOutput.close();
    		con.getContent();
    		pageView.timeTotal = System.currentTimeMillis()-pageView.timeStart;

    		pageView.responseCode = con.getResponseCode();
    		pageView.responseMessage = con.getResponseMessage();
    		con.disconnect();
    	}
    	catch(Exception e) {}
    	return pageView;
    }
}
