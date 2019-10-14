package com.xyx.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Response {
	private byte[] value;
	private ByteArrayOutputStream out=new ByteArrayOutputStream();
	private Writer writer;
	private OutputStream stream;
	private Map<String,String> header=new HashMap();
	private int code=200;
	public Response(OutputStream ops){
		stream=ops;
		header.put("Content-type", "text/html;charset=utf-8");
		header.put("Date", new Date().toString());
		header.put("Server", "xyx/1.2");
	}
	public void updateParam(String key,String value) {
		header.put(key, value);
	}
	public String getParam(String key) {
		return header.get(key);
	}
	public void write(String value) throws UnsupportedEncodingException, IOException {
		out.write(value.getBytes("utf-8"));
	}
	public void writeFile(String file) throws IOException {
		File f=new File(file);
		BufferedInputStream input=new BufferedInputStream(new FileInputStream(f));
		byte[] b=new byte[1024*1024];
		int len;
		while(-1!=(len=input.read(b))) {
			out.write(b,0,len);
		}
		input.close();
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void send() throws UnsupportedEncodingException, IOException {
		value=out.toByteArray();
		updateParam("Content-Length", String.valueOf(value.length));
		stream.write(new StringBuffer().append("HTTP/1.1 ").append(code).append(" OK").append("\r\n").toString().getBytes("utf-8"));
		for(Map.Entry<String, String> entry:header.entrySet()) {
			stream.write(entry.getKey().getBytes("utf-8"));
			stream.write(":".getBytes("utf-8"));
			stream.write(entry.getValue().getBytes("utf-8"));
			stream.write("\r\n".getBytes("utf-8"));
		}
		stream.write("\r\n".getBytes("utf-8"));
		stream.write(value);
		stream.flush();
	}
}
