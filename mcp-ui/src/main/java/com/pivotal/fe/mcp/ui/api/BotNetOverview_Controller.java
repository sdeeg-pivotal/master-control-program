package com.pivotal.fe.mcp.ui.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotNetOverview_Controller {

	@Autowired
	private DiscoveryClient dClient;
	
	@RequestMapping("/nodes")
	public List<String> nodes() {
		List<String> services = dClient.getServices();
		
		return services;
	}
}
