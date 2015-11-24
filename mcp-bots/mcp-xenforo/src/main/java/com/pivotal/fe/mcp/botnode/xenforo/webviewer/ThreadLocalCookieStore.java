package com.pivotal.fe.mcp.botnode.xenforo.webviewer;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class ThreadLocalCookieStore implements CookieStore {

    private final static ThreadLocal<CookieStore> ms_cookieJars = new ThreadLocal<CookieStore>() {
        @Override
        protected synchronized CookieStore initialValue() { 
            return (new CookieManager()).getCookieStore(); /*InMemoryCookieStore*/ 
            }
    };


    public void add(URI uri, HttpCookie cookie) {
        ms_cookieJars.get().add(uri, cookie);
    }

    public List<HttpCookie> get(URI uri) {
        return ms_cookieJars.get().get(uri);
    }

	@Override
	public List<HttpCookie> getCookies() {
		return ms_cookieJars.get().getCookies();
	}

	@Override
	public List<URI> getURIs() {
		return ms_cookieJars.get().getURIs();
	}

	@Override
	public boolean remove(URI uri, HttpCookie cookie) {
		return ms_cookieJars.get().remove(uri, cookie);
	}

	@Override
	public boolean removeAll() {
		return ms_cookieJars.get().removeAll();
	}
}