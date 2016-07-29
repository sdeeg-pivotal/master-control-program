package com.pivotal.fe.mcp.botnode.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McpBotnodeSimpleApplication {

	private static Logger log = LoggerFactory.getLogger(McpBotnodeSimpleApplication.class);

	public static void main(String[] args) {
    	log.info("Starting McpBotnodeSimpleApplication");
        SpringApplication.run(McpBotnodeSimpleApplication.class, args);
    }
}
