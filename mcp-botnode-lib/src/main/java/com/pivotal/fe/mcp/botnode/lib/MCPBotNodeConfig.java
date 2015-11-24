/**
 */
package com.pivotal.fe.mcp.botnode.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import org.springframework.context.annotation.Scope;

import com.pivotal.fe.mcp.botnode.lib.dom.BotRunner;
import com.pivotal.fe.mcp.botnode.lib.dom.NodeManager;
import com.pivotal.fe.mcp.botnode.lib.util.NameUtil;

/**
 * @author Scott Deeg
 *
 * This class is loaded when a class in an application is annotated with @MCPBotNode.  It triggers
 * the loading of all relevant beans, most relevant of which are BotNodeApiController 
 * and BotNodeManager.
 */
@Configuration
@EnableDiscoveryClient
@ComponentScan("com.pivotal.fe.mcp.botnode.lib")
@PropertySource("application-mcp-botnode-lib.yml")
public class MCPBotNodeConfig {

	private static Logger log = LoggerFactory.getLogger(MCPBotNodeConfig.class);

	@Value("${botnet.node.threads:4}")
	int numThreads;

	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	@MCPBot
	Object bot;

    @Value("${mcp.botnode.numbots:1}")
	private int numbots;

    @Autowired
    private NameUtil nameUtil;
    
    @Autowired
    private Class<?> botProtoClass;
    

	@Bean
	public NodeManager nodeManager() {
		return new NodeManager();
	}

	@Bean
	public List<BotRunner> listOfBots() {
		List<BotRunner> retVal = new ArrayList<BotRunner>();
		log.info("Creating "+numbots+" bots");
		for(int c=0;c<numbots;c++)
		{
			retVal.add(getBotRunner(nameUtil.getName(), botProtoClass));
		}
		return retVal;
	}

	@Bean(name = "botProto")
	@Scope("prototype")
	public BotRunner getBotRunner(String name, Class<?> botProtoClass) {
		BotRunner runner = null;
		try {
			runner = new BotRunner(name, getBotInstance(botProtoClass));
		}
		catch(Exception e) { runner = null; }
		return runner;
	}
	
	@Bean
	@Scope("prototype")
	public Object getBotInstance(Class<?> botProtoClass) {
		Object retVal = null;
		try {
			retVal = botProtoClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Bean
	public Class<?> botProtoClass(@MCPBot Object[] bots) {

		log.info("Looking for an instance of the Bot");

		Class<?> botProtoClassFound = null;
		
		for(Object botObj : bots) {
			botProtoClassFound = botObj.getClass();
			log.info("Found bot class: "+botObj.toString());
		}
		
		return botProtoClassFound;
	}
}
