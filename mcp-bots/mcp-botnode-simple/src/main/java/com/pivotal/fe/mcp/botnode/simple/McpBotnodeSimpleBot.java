package com.pivotal.fe.mcp.botnode.simple;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pivotal.fe.mcp.botnode.lib.MCPBot;
import com.pivotal.fe.mcp.botnode.lib.MCPBot.MCPBotStart;;

/**
 * @author Scott Deeg
 *
 */
@MCPBot
public class McpBotnodeSimpleBot {

	private static Logger log = LoggerFactory.getLogger(McpBotnodeSimpleBot.class);
	
	@Autowired(required = false)
	Random randy;

	@MCPBotStart
    public void start() {

    	log.info("I'm a simple Bot, and I'm running");
    	if(randy != null) {
    		log.info("Here's a random number: "+randy.nextInt(100));
    	}
    	else {
    		log.info("Boo hiss .. no instance of Random so probably no bean processing happening.");
    	}
	}

}
