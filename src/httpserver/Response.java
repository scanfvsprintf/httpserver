package httpserver;
//192.168.0.106
import java.io.*;
import java.net.*;
import java.util.Date;
public class Response {
	private BufferedWriter headWriter=null;
	private StringBuilder headValue=null;
	private String br="\r\n";
	private ByteArrayOutputStream fileWriter=null;
	private byte value[]=null;
	private OutputStream socketOutputStream=null;
	Response(Socket client){
		try {
			socketOutputStream=client.getOutputStream();
			headWriter=new BufferedWriter(new OutputStreamWriter(socketOutputStream,"utf-8"));
			headValue=new StringBuilder();
			fileWriter=new ByteArrayOutputStream();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void createHead(int code) {
		value=fileWriter.toByteArray();
		headValue.append("HTTP/1.1 ");
		headValue.append(code);
		headValue.append(" OK");
		headValue.append(br);
		headValue.append("Server:xjj/1.1.1").append(br);
		headValue.append("Date:").append(new Date()).append(br);
		headValue.append("Content-type:text/html;charset=utf-8").append(br);
		try {
			headValue.append("Content-Length:").append(value.length).append(br);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		headValue.append(br);
	}
	public void print(String text) {
		try {
			fileWriter.write(text.getBytes("utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void println(String text) {
		print(text);
		print(br);
	}
	public void printFile(File file) throws IOException{
		BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
		byte buff[]=new byte[1024];
		int len;
		while(-1!=(len=bis.read(buff))){
			fileWriter.write(buff,0,len);
		}
		bis.close();
	}
	public void printfile(String file) throws IOException {
		printFile(new File(file));
	}
	
	public void send() {
		createHead(200);
		try {
			headWriter.write(headValue.toString());
			headWriter.flush();
			socketOutputStream.write(value);
			System.out.println(value.toString());
			socketOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
