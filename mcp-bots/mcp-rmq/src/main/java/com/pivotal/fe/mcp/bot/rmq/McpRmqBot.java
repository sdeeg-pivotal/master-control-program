package com.pivotal.fe.mcp.bot.rmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pivotal.fe.mcp.botnode.lib.MCPBot;

@MCPBot
public class McpRmqBot {

	private static Logger log = LoggerFactory.getLogger(McpRmqBot.class);

    public void run() {

    	log.info("I'm a Bot, and I'm running!!!");
	}
}
