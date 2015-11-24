package com.pivotal.fe.demo.mcp.xenforo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pivotal.fe.mcp.botnode.xenforo.McpXenforoApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = McpXenforoApplication.class)
@WebAppConfiguration
public class McpXenforoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
