package com.pivotal.fe.mcp.botnode.lib.cli;

import java.io.Console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import com.pivotal.fe.mcp.botnode.lib.impl.NodeManagerImpl;

@Profile("botnode-cli")
@Component
public class MCPBotNodeCLI implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(MCPBotNodeCLI.class);

	@Autowired
	AbstractApplicationContext ctx;

    private final NodeManagerImpl nodeManager;
	
	@Autowired
	public MCPBotNodeCLI(NodeManagerImpl nodeManager)
	{
		this.nodeManager = nodeManager;
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		Console cons = System.console();
		if(cons != null) {
			cons.printf("hello, world>");
			String input = cons.readLine();
			cons.printf("The string is: %s\n", input);
		}
		else {
			log.info("Couldn't get the console.  Initializing...");
			nodeManager.initialize();
			log.info("Status: \n"+nodeManager.getStatus());
			log.info("Starting...");
			nodeManager.startBots();
			Thread.sleep(5000);
			nodeManager.stopBots();
			log.info("Done.  Exiting.");
		}
		ctx.close();
		System.exit(0);
	}

}

