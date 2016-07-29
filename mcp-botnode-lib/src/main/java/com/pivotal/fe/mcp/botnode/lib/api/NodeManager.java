package com.pivotal.fe.mcp.botnode.lib.api;

import com.pivotal.fe.mcp.botnode.lib.dom.NodeManagerStatus;

public interface NodeManager {

	public NodeManagerStatus initialize();
	public NodeManagerStatus startBots();
	public NodeManagerStatus stopBots();
	public NodeManagerStatus getStatus();

}
