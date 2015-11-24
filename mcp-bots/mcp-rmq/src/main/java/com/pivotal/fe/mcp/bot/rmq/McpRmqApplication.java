package com.pivotal.fe.mcp.bot.rmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pivotal.fe.mcp.botnode.lib.MCPBotNode;

@SpringBootApplication
@MCPBotNode
public class McpRmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpRmqApplication.class, args);
    }
}
