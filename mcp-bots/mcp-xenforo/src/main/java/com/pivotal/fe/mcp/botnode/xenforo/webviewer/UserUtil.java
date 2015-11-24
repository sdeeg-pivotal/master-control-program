package com.pivotal.fe.mcp.botnode.xenforo.webviewer;

import java.util.List;

public class UserUtil
{
	private List<String> names;
	
	public void setNames(List<String> names) { this.names = names; }

	int counter = 0;
	public String getUserName()
	{
		if(counter == names.size()) { counter = 0; }
		return names.get(counter++);
	}
}
