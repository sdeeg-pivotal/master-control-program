package com.pivotal.fe.mcp.botnode.lib;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pivotal.fe.mcp.botnode.lib.api.BotNodeApiController;
import com.pivotal.fe.mcp.botnode.lib.api.NodeManager;
import com.pivotal.fe.mcp.botnode.lib.dom.NodeManagerStatus;

public class ControllerTests {

    private final NodeManager nodeManager = Mockito.mock(NodeManager.class);
    
    private final BotNodeApiController controller = new BotNodeApiController(nodeManager); 
	
//	@Test
//	public void controllerInit() {
//		BDDMockito.given(nodeManager.initialize()).willReturn(new NodeManagerStatus());
//		Assert.assertNotNull(controller.initialize());
//	}
	
    private MockMvc mockMvc = null;
    
    @Before
    public void setup() {
    	//Create a dummy return value
    	NodeManagerStatus status = new NodeManagerStatus();
    	status.setBotNodeName("ControllerTests");
    	
    	//Setup the mock to return the results from the API calls
		BDDMockito.given(nodeManager.initialize()).willReturn(status);
		BDDMockito.given(nodeManager.getStatus()).willReturn(status);

		//Create the MVC Mock
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
	public void controllerInit() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/initialize"))
		       .andExpect(MockMvcResultMatchers.status().isOk());
	}

    @Test
	public void controllerStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/status"))
		       .andExpect(MockMvcResultMatchers.status().isOk());
	}
}
