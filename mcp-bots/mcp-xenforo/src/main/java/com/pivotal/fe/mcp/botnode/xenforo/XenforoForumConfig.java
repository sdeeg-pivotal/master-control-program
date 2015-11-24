package com.pivotal.fe.mcp.botnode.xenforo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pivotal.fe.mcp.botnode.xenforo.webviewer.ForumViewer;
import com.pivotal.fe.mcp.botnode.xenforo.webviewer.UserUtil;

@Profile("xenforo")
@Configuration
public class XenforoForumConfig
{
    private static final Logger log = Logger.getLogger(XenforoForumConfig.class);

    @Value("#{'${webapp.users.ignore}'.split(',')}") 
	private List<String> ignoreUsers;

	@Autowired
	private JdbcTemplate template;

	@Bean
    public UserUtil userUtil()
    {
		UserUtil retVal = new UserUtil();
		ArrayList<String> names = new ArrayList<String>();
		String sql = " select username from xf_user";
		List<Map<String,Object>> result = template.queryForList(sql);
		if(result.size() <= 0)
		{
			log.error("Did not find any users in Xenforo.  Tests will not run!");
		}
		else
		{
			for(Map<String,Object> row : result)
			{
				String username = ""+row.get("username");
				if(!ignoreUsers.contains(username))
				{
					names.add(username);
				}
			}
			log.info("Found Xenforo users: "+names);
		}
		retVal.setNames(names);
		
    	return retVal;
    }

	@Bean
	public ForumViewer viewer()
	{
		ArrayList<String> categories = new ArrayList<String>();
		ArrayList<String> forums = new ArrayList<String>();
		ArrayList<String> threads = new ArrayList<String>();

		String sql = " select node_id, title, public_route_prefix as node_type from xf_node, xf_node_type where xf_node.node_type_id=xf_node_type.node_type_id";
		List<Map<String,Object>> result = template.queryForList(sql);
		if(result.size() <= 0)
		{
			log.error("Did not find any Nodes in Xenforo.");
		}
		
		for(Map<String,Object> row : result)
		{
			if("categories".equals(row.get("node_type")))
			{
				categories.add(getUrlTitle(row)+"."+row.get("node_id"));
			}
			else
			{
				forums.add(getUrlTitle(row)+"."+row.get("node_id"));
			}
		}
		log.info("Found Xenforo categories: "+categories);
		log.info("Found Xenforo forums: "+forums);

		sql = "select thread_id, title from xf_thread";
		result = template.queryForList(sql);
		if(result.size() <= 0)
		{
			log.warn("Did not find any Threads in Xenforo.  Views will be limited to the home page until threads exist.");
		}
		for(Map<String,Object> row : result)
		{
			threads.add(getUrlTitle(row)+"."+row.get("thread_id"));
		}
		log.info("Found Xenforo threads: "+threads);

		XenforoViewer viewer = new XenforoViewer();
    	viewer.addCategories(categories);
    	viewer.addForums(forums);
    	viewer.addThreads(threads);
		return viewer;
	}
	
	private String getUrlTitle(Map<String,Object> row)
	{
		return (row.get("title").toString().trim().toLowerCase().replace(' ', '-'));
	}
}
