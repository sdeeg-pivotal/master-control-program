package com.pivotal.fe.mcp.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = McpUiApplication.class)
@WebAppConfiguration
public class McpUiTests {

	@Autowired
	WebApplicationContext context;
	
    private MockMvc mockMvc = null;
    
    @Before
    public void setup() {
		//Create the MVC Mock
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
	public void tokenTest() throws Exception {
    	MockHttpServletRequestBuilder getToken = MockMvcRequestBuilders.get("/uaa/token");
    	ResultMatcher ok = MockMvcResultMatchers.status().isOk();

    	mockMvc.perform(getToken)
		       .andExpect(ok);
	}

}
