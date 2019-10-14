package com.xyx.servlet;

import com.xyx.http.Request;
import com.xyx.http.Response;

public interface Servlet {
	public void init();
	public void service(Request request,Response response);
	public void destroy();
}
