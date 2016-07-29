package com.pivotal.fe.mcp.botnode.simple;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Scott Deeg
 *
 * Create a random number generator for the bot to use.
 * 
 */
@Configuration
public class McpBotnodeSimpleConfig {

	@Bean
	public Random randy() {
		return new Random(System.currentTimeMillis());
	}
}
