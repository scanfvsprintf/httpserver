package httpserver;
import java.io.*;
import java.net.*;
public class Server {
	//һ���򵥵Ĳ���
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket server=new ServerSocket(80);	
		while(true) {
			Socket client=server.accept();
			System.out.println("���յ���Ӧ");
			Request request=new Request(client);
			Response response=new Response(client);
			System.out.println(request.getAddress());
			if(request.getAddress()==null || request.getAddress().length()==0 || request.getAddress().equals("index.html")) {
				response.printfile("test1.html");
			}
			else if(request.getAddress().equals("login.html")){
				response.println("<html>\r\n<head>\r\n<title>��ӭʹ��</title></head>\r\n<body>");
				response.println("<p1>��ӭ��½xjj����վ</p1></br>");
				response.print("<p2>�����û���Ϊ");
				response.print(request.getParam("userid").get(0));
				response.print("</p2></br>");
				response.print("<p2>��������Ϊ");
				response.print(request.getParam("pass").get(0));
				response.print("���μ�</p2></br>\r\n");
				response.println("<p2>��������Բ鿴</p2><a href=\"test2.html\">��ͼƬ�Ĳ�����ҳ</a></br>\r\n");
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
