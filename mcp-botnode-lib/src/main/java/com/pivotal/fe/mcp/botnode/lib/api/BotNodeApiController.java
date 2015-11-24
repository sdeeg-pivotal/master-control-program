package com.pivotal.fe.mcp.botnode.lib.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pivotal.fe.mcp.botnode.lib.dom.NodeManager;
import com.pivotal.fe.mcp.botnode.lib.dom.NodeManagerStatus;

@RestController
public class BotNodeApiController
{
    @Value("${botnet.name:my-botnet}")
	public String name;

    private final NodeManager shepherd;
	
	@Autowired
	public BotNodeApiController(NodeManager shepherd)
	{
		this.shepherd = shepherd;
	}
	
    @RequestMapping(value = {"/","/status"}, method = RequestMethod.GET)
	public NodeManagerStatus getStatus()
	{
    	NodeManagerStatus status = shepherd.getStatus();
    	status.botNetName = name;
    	return status;
	}
    
    @RequestMapping(value = "/initialize", method = RequestMethod.GET)
	public NodeManagerStatus initialize()
	{
    	NodeManagerStatus status = shepherd.initialize();
    	status.botNetName = name;
    	return status;
	}
    
    @RequestMapping(value = "/start", method = RequestMethod.GET)
	public NodeManagerStatus startBots()
	{
    	NodeManagerStatus status = shepherd.startBots();
    	status.botNetName = name;
    	return status;
	}

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public NodeManagerStatus stopBots()
	{
    	NodeManagerStatus status = shepherd.stopBots();
    	status.botNetName = name;
    	return status;
	}
}
