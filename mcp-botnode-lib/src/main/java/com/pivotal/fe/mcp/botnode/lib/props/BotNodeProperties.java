package com.pivotal.fe.mcp.botnode.lib.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "mcp.botnode")
@Data
public class BotNodeProperties {
    public String name;
    public int numbots;
    public int threads;
    public int id;
}
