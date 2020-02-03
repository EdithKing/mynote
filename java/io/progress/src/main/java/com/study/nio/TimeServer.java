package com.study.nio;

public class TimeServer {
	public static void main(String[] args) {
		int port = 3333;
		if (args != null && args.length != 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {

			}
		}
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
	}
}
