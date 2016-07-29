package io.pivotal.pa.bot.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pivotal.fe.mcp.botnode.lib.MCPBotNode;

@SpringBootApplication
@MCPBotNode
public class McpBotnodeCliApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpBotnodeCliApplication.class, args);
	}
}
