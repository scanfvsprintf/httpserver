package com.xyx.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import com.xyx.http.Request;
import com.xyx.http.Response;
import com.xyx.servlet.Servlet;

public class Dispatcher {
	private static Map<String, String> servletKV=new HashMap();
	private static Map<String, Servlet> servlets=new HashMap();
	private static String path;
	public static void initKV() throws Exception {
		char[] buffer=new char[1024];
		StringBuilder builder=new StringBuilder();
		File file=new File("webapps/web.properties");
		if(!file.exists()) {
			throw new Exception("配置文件缺失！");
		}
		BufferedReader reader=new BufferedReader(new FileReader(file));
		int len;
		while(-1!=(len=reader.read(buffer))) {
			builder.append(new String(buffer,0,len));
		}
		reader.close();
		String[] kvs=builder.toString().split("\r\n");
		for(String s:kvs) {
			int index=s.indexOf(':');
			if(index==-1) {
				throw new Exception("配置文件出错，格式有误！");
			}
			servletKV.put(s.substring(0,index), s.substring(index+1, s.length()));
		}
	}
	static {
		path=new File("webapps/classes").getAbsolutePath();
		try {
			initKV();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("出现致命错误");
			System.exit(0);
		}
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
		reader=new InputStreamReader(socket.getInputStream(),"utf-8");
		char[] buff=new char[1024*1024];
		StringBuilder builder=new StringBuilder();
		len=reader.read(buff);
		builder.append(new String(buff,0,len));
		value=builder.toString();
		request=new Request(value);
		response=new Response(socket.getOutputStream());
		String url=request.getUrl();
		if(url.equals("")) {
			url="index.html";
		}
		String servletName=servletKV.get(url);
		if(servletName!=null) {
			Servlet servlet=servlets.get(servletName);
			if(servlet==null) {
				URL p=new URL("file:"+path+"\\");
				URLClassLoader loader=new URLClassLoader(new URL[] {p});
				Class c=loader.loadClass(servletName);
				servlet=(Servlet) c.newInstance();
				servlet.init();
			}
			servlet.service(request, response);
			response.send();
		}
		else {
			File f=new File("webapps/static/"+url);
			if(f.exists()) {
				response.writeFile("webapps/static/"+url);
				response.send();
			}
			else {
				response.writeFile("webapps/static/"+"error.html");
				response.updateParam("Content-type", "application/octet-stream");
				response.setCode(404);
				response.send();
			}
		}
	}
}
