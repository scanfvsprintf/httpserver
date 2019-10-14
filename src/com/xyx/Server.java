package com.xyx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.xyx.server.Dispatcher;
import com.xyx.server.ServerThread;

public class Server {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket server=new ServerSocket(80);
		while(true) {
			try {
				Socket socket=server.accept();
				new Thread(new ServerThread(new Dispatcher(socket))).start();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
