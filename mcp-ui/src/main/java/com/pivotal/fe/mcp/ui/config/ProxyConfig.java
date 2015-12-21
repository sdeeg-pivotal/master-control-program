package com.pivotal.fe.mcp.ui.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("proxy")
@Configuration
@EnableZuulProxy
@EnableDiscoveryClient
public class ProxyConfig {

}
