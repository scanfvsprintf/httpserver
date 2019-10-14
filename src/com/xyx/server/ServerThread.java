package com.xyx.server;

import java.io.IOException;
import java.net.Socket;

public class ServerThread implements Runnable{
	Dispatcher dispatcher;
	public ServerThread(Dispatcher dispatcher) {
		this.dispatcher=dispatcher;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			dispatcher.init();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
