package com.xyx.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.xyx.http.Request;
import com.xyx.http.Response;
import com.xyx.servlet.Servlet;

public class Dispatcher {
	private static Map<String, String> servletKV;
	private static Map<String, Servlet> servlets;
	static {
		servletKV=new HashMap<>();
		servlets=new HashMap<>();
		servletKV.put("test", "com.xyx.servlet.TestServlet");
	}
	private Socket socket;
	private String value;
	private InputStreamReader reader;
	private Request request;
	private Response response;
	public Dispatcher(Socket socket) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.socket=socket;
		
	}
	public void init() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int len;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader=new InputStreamReader(socket.getInputStream(),"utf-8");
		char[] buff=new char[1024*1024];
		StringBuilder builder=new StringBuilder();
		while(-1!=(len=reader.read(buff))) {
			builder.append(new String(buff,0,len));
		}
		value=builder.toString();
		request=new Request(value);
		response=new Response(socket.getOutputStream());
		String url=request.getUrl();
		String servletName=servletKV.get(url);
		if(servletName!=null) {
			Servlet servlet=servlets.get(servletName);
			if(servlet==null) {
				Class c=Class.forName(servletName);
				servlet=(Servlet) c.newInstance();
				servlet.init();
			}
			servlet.service(request, response);
			response.send();
		}
	}
}
