package com.pivotal.fe.mcp.botnode.lib.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pivotal.fe.mcp.botnode.lib.dom.NodeManagerStatus;

@RestController
public class BotNodeApiController
{
    @Value("${botnet.name:my-botnet}")
	public String name;

    private final NodeManager nodeManager;
	
	@Autowired
	public BotNodeApiController(NodeManager nodeManager)
	{
		this.nodeManager = nodeManager;
	}
	
    @RequestMapping(value = {"/","/status"}, method = RequestMethod.GET)
	public NodeManagerStatus status()
	{
    	NodeManagerStatus status = nodeManager.getStatus();
    	status.botNodeName = this.name;
    	return status;
	}
    
    @RequestMapping(value = {"/initialize", "/init", "/reset"}, method = RequestMethod.GET)
	public NodeManagerStatus initialize()
	{
    	NodeManagerStatus status = nodeManager.initialize();
    	status.botNodeName = this.name;
    	return status;
	}
    
    @RequestMapping(value = "/start", method = RequestMethod.GET)
	public NodeManagerStatus start()
	{
    	NodeManagerStatus status = nodeManager.startBots();
    	status.botNodeName = this.name;
    	return status;
	}

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public NodeManagerStatus stop()
	{
    	NodeManagerStatus status = nodeManager.stopBots();
    	status.botNodeName = this.name;
    	return status;
	}
}
