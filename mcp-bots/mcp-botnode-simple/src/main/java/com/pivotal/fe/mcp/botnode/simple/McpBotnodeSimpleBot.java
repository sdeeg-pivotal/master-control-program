package com.pivotal.fe.mcp.botnode.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.pivotal.fe.mcp.botnode.lib.MCPBot;
import com.pivotal.fe.mcp.botnode.lib.MCPBot.MCPBotRun;

/**
 * @author Scott Deeg
 *
 */
@MCPBot
public class McpBotnodeSimpleBot {

	private static Logger log = LoggerFactory.getLogger(McpBotnodeSimpleBot.class);
	
	@Autowired
	ApplicationContext ctx;

	@MCPBotRun
    public void run() {

    	log.info("I'm a simple Bot, and I'm running");
    	if(ctx != null) {
    		log.info("The context is there.  Huraay!");
    	}
    	else {
    		log.info("Boo hiss .. no context so no processing.");
    	}
	}

}
