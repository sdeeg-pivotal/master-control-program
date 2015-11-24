package com.pivotal.fe.demo.mcp.rmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pivotal.fe.mcp.bot.rmq.McpRmqApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = McpRmqApplication.class)
@WebAppConfiguration
public class McpRmqApplicationTests {

	@Test
	public void contextLoads() {
	}

}
