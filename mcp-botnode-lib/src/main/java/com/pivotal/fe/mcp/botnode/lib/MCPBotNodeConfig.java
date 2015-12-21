package com.pivotal.fe.mcp.botnode.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pivotal.fe.mcp.botnode.lib.impl.BotRunner;
import com.pivotal.fe.mcp.botnode.lib.impl.NodeManager;
import com.pivotal.fe.mcp.botnode.lib.util.NameUtil;

/**
 * @author Scott Deeg
 *
 * This configuration bean is created when a class in an application is annotated with @MCPBotNode.  It triggers
 * the loading of all relevant beans and services for a BotNode, the most relevant of which are BotNodeApiController 
 * and BotNodeManager.
 */
@Configuration
@EnableDiscoveryClient
@ComponentScan("com.pivotal.fe.mcp.botnode.lib")
@PropertySource("application-mcp-botnode-lib.properties")
@EnableScheduling
public class MCPBotNodeConfig {

	private static Logger log = LoggerFactory.getLogger(MCPBotNodeConfig.class);

	@Value("${botnet.node.threads:4}")
	int numThreads;

	@Autowired
	ApplicationContext applicationContext;

    @Value("${mcp.botnode.numbots:1}")
	private int numbots;
    
	@Bean
	public NodeManager nodeManager() {
		return new NodeManager(generateBots(nameUtil()));
	}

	@Bean(name = "listOfBots")
	public List<BotRunner> generateBots(NameUtil nameUtil) {

		log.info("In generateBots.  Creating "+numbots+" bots");
		
		String beanNamesArray[] = applicationContext.getBeanNamesForAnnotation(MCPBot.class);
		Stream.of(beanNamesArray).forEach(b -> {
			log.info("Found bean name: "+b);
		});
		String botBeanName = beanNamesArray[0];
		
		List<BotRunner> retVal = new ArrayList<BotRunner>();
		for(int c=0;c<numbots;c++)
		{
			String botName = nameUtil.getName();
			Object newBot = applicationContext.getBean(botBeanName);
			BotRunner newRunner = applicationContext.getBean(BotRunner.class);
			newRunner.setName(botName);
			newRunner.setBot(newBot);
			log.info("Creating BotRunner bean with name: "+botName+" and bot: "+newBot.toString());
			retVal.add(newRunner);
		}
		
		log.info("Returning listOfBots: "+retVal.toString());
		return retVal;
	}
	
	@Bean
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(numThreads);
	}

	@Bean
	public NameUtil nameUtil() {
		return new NameUtil();
	}
}
