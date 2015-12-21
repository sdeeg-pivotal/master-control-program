package com.pivotal.fe.mcp.botnode.lib.util;

import java.util.List;

//@Service
public class NameUtil
{
	private List<String> names;
	
	public void setNames(List<String> names) { this.names = names; }

	int counter = 0;

	public String getName()
	{
		String retVal = "default";
		if(names != null) {
			if(counter == names.size()) { counter = 0; }
			retVal = names.get(counter++);
		}
		
		return retVal;
	}
}
