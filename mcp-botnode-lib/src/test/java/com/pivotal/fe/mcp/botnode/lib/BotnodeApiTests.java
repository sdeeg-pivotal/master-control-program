package com.pivotal.fe.mcp.botnode.lib;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.pivotal.fe.mcp.botnode.lib.api.NodeManager;
import com.pivotal.fe.mcp.botnode.lib.impl.NodeManagerImpl;

public class BotnodeApiTests {

    private final NodeManager nodeManager = new NodeManagerImpl(null);
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
	
    @Before
    public void setup() {
    	System.out.println("Setup");
    }
    
	@Test
	public void initialTest() {
		Assert.assertNotNull(this.nodeManager.getStatus());
	}
	
	@Test
	public void iaeTest() {
//		thrown.expect(IllegalArgumentException.class);
		this.nodeManager.getStatus();
	}
}
