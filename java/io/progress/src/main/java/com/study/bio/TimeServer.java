package com.study.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
	public static void main(String[] args) throws IOException {
		int port = 3333;
		if (args != null && args.length != 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
			}
		}
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("The time server is start in port : " + port);
			Socket socket = null;
			TimeServerHandlerExecutePool executePool = new TimeServerHandlerExecutePool(50, 10000);
			while (true) {
				socket = server.accept();
				/**
				 * 同步阻塞io，一个客户端请求一个线程
				 */
//				new Thread(new TimeServerHandler(socket)).start();
				/**
				 * 采用线程池，避免线程太多
				 */
				executePool.execute(new TimeServerHandler(socket));
			}

		} finally {
			if (server != null) {
				System.out.println("The time server close");
				server.close();
				server = null;
			}
		}

	}
}
