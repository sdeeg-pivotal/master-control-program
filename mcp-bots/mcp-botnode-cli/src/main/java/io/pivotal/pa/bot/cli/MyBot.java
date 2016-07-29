package io.pivotal.pa.bot.cli;

import com.pivotal.fe.mcp.botnode.lib.MCPBot;
import com.pivotal.fe.mcp.botnode.lib.MCPBot.MCPBotRun;

@MCPBot
public class MyBot {

	@MCPBotRun
    public void run() {
		System.out.println("hello, CLI world");
	}

}
