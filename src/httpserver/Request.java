package httpserver;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class Request {
	private BufferedReader reader=null;
	private String value;
	private String method=null;
	private String address=null;
	private Map<String,List<String>> params=null;
	Request(Socket client){
		try {
			reader=new BufferedReader(new InputStreamReader(client.getInputStream(),"utf-8"));
			StringBuilder t = new StringBuilder();
			char temp[]=new char[99999];
			int len=0;
			
			len=reader.read(temp);
			t.append(new String(temp,0,len));
			value=t.toString();
			System.out.println("ÏìÓ¦Í·£º"+value);
			solve();
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void solve() {
		int t1=0;
		int t2=0;
		t2=value.indexOf('/');
		method=value.substring(t1, t2).trim();
		t1=t2;
		if(method.equalsIgnoreCase("get") && value.indexOf('?')>0) {
			t2=value.indexOf('?');
		}
		else {
			t2=value.indexOf("HTTP");
		}
		address=value.substring(t1+1, t2);
		String[] temp=value.split("\r\n");
		/*
		for(String a:temp) {
			System.out.println(a);
		}
		*/
		String param=null;
		if(method.equalsIgnoreCase("post")) {
			param=temp[temp.length-1].trim();
		}
		else {
			if((t1=value.indexOf("?"))>0 && t1<(t2=value.indexOf("HTTP"))) {
				t1++;
				System.out.println("t1:"+t1+"t2:"+t2);
				param=value.substring(t1, t2).trim();
			}
		}
		params=new HashMap<String,List<String>>();
		if(param!=null) {
			String[] paramsTemp=param.split("&");
			for(String each:paramsTemp) {
				String[] kv=each.split("=");
				if(kv.length==2) {
					if(params.containsKey(kv[0])) {
						params.get(kv[0]).add(kv[1]);
					}
					else {
						List t=new LinkedList<String>();
						t.add(kv[1]);
						params.put(kv[0],t);
					}
				}
			}
		}
		
	}
	public String getMethod() {
		return method;
	}
	public String getAddress() {
		return address.trim();
	}
	public List<String> getParam(String key) {
		if(params.containsKey(key)) {
			return params.get(key);
		}
		return null;
	}

}
