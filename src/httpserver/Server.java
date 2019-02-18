package httpserver;
import java.io.*;
import java.net.*;
public class Server {
	//一个简单的测试
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket server=new ServerSocket(80);	
		while(true) {
			Socket client=server.accept();
			System.out.println("接收到响应");
			Request request=new Request(client);
			Response response=new Response(client);
			System.out.println(request.getAddress());
			if(request.getAddress()==null || request.getAddress().length()==0 || request.getAddress().equals("index.html")) {
				response.printfile("test1.html");
			}
			else if(request.getAddress().equals("login.html")){
				response.println("<html>\r\n<head>\r\n<title>欢迎使用</title></head>\r\n<body>");
				response.println("<p1>欢迎登陆xjj的网站</p1></br>");
				response.print("<p2>您的用户名为");
				response.print(request.getParam("userid").get(0));
				response.print("</p2></br>");
				response.print("<p2>您的密码为");
				response.print(request.getParam("pass").get(0));
				response.print("请牢记</p2></br>\r\n");
				response.println("<p2>现在你可以查看</p2><a href=\"test2.html\">带图片的测试网页</a></br>\r\n");
				response.print("</body>\r\n</html>");
			}
			else if(new File(request.getAddress()).exists()){
				response.printfile(request.getAddress());
			}
			else {
				response.printfile("error.html");
			}
			response.send();
			client.close();
		}
	}
}
