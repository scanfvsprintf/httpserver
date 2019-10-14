package com.xyx.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
	private String value;
	private Map<String,String> header=new HashMap();
	private Map<String,List<String>> var=new HashMap();
	private String method;
	private String url;
	public Request(String value){
		this.value=value;
		System.out.println(value);
		init();
	}
	private void init() {
		String[] lines=value.split("\r\n");
		System.out.println(lines[0]);
		String[] messages=lines[0].split(" ");
		String vars=null;
		method=messages[0];
		url=messages[1].substring(1, messages[1].length());
		int i;
		int len=lines.length;
		String k,v;
		boolean varStart=false;
		for(i=1;i<len;i++) {
			if(varStart==false && lines[i].trim().length()!=0) {
				int index=lines[i].indexOf(':');
				//System.out.println(lines[i]);
				k=lines[i].substring(0,index);
				v=lines[i].substring(index+1,lines[i].length());
				header.put(k, v);
			}
			else if((method.equals("POST") || method.equals("post")) && varStart==false) {
				varStart=true;
			}
			else if(varStart==true){
				vars=lines[i];
			}
			
		}
		if(method.equals("GET") || method.equals("get")) {
			int t=url.indexOf('?');
			if(t>=0) {
				vars=url.substring(t+1,url.length());
				url=url.substring(0,t);
			}
			
		}
		if(vars!=null && vars!="") {
			String[] value=vars.split("&");
			for(String t:value) {
				if(t=="") {
					continue;
				}
				int index=t.indexOf('=');
				k=t.substring(0,index);
				v=t.substring(index+1,t.length());
				if(var.containsKey(k)) {
					List<String> l=var.get(k);
					l.add(v);
				}
				else {
					List<String> l=new ArrayList<>();
					l.add(v);
					var.put(k, l);
				}
			}
		}
	}
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getAddress() {
		return url;
	}
	public String getHeader(String key) {
		return header.get(key);
	}
	public List<String> getParam(String key) {
		if(var.containsKey(key)) {
			return var.get(key);
		}
		return null;
	}
}
