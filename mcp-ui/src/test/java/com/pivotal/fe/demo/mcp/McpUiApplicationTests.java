package com.pivotal.fe.demo.mcp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.pivotal.fe.mcp.ui.McpUiApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = McpUiApplication.class)
@WebAppConfiguration
public class McpUiApplicationTests {

	@Test
	public void contextLoads() {
	}

}
