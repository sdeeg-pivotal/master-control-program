package com.pivotal.fe.mcp.botnode.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pivotal.fe.mcp.botnode.lib.MCPBotNode;

@SpringBootApplication
@MCPBotNode
public class McpBotnodeSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpBotnodeSimpleApplication.class, args);
    }
}
