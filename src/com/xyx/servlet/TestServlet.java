package com.xyx.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.xyx.http.Request;
import com.xyx.http.Response;

public class TestServlet implements Servlet{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		System.out.println("TestServlet���ڳ�ʼ��");
	}

	@Override
	public void service(Request request, Response response) {
		// TODO Auto-generated method stub
		try {
			response.write(request.getHeader("Accept-Language"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ִ�����");
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
