package com.pivotal.fe.mcp.botnode.lib;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("cloud")
@Configuration
@EnableDiscoveryClient
public class MCPBotNodeConfigCloud {

}
