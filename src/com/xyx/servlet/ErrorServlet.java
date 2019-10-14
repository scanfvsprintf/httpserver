package com.xyx.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.xyx.http.Request;
import com.xyx.http.Response;

public class ErrorServlet implements Servlet{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		System.out.println("Error正在初始化");
	}

	@Override
	public void service(Request request, Response response) {
		// TODO Auto-generated method stub
		try {
			response.write("404");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("执行完毕");
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
