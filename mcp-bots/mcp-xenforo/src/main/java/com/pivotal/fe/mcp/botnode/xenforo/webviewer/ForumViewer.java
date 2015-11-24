package com.pivotal.fe.mcp.botnode.xenforo.webviewer;

import com.pivotal.fe.mcp.botnode.dom.PageView;

public interface ForumViewer
{
	PageView home();
	PageView login(String login, String password);
	PageView startThread(String title, String message);
	PageView makeRandomPost(String message);
	PageView getRandomPage();
}
