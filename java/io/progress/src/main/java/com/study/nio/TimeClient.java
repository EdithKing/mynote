package com.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TimeClient {
	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		InetSocketAddress address = new InetSocketAddress("127.0.0.1",3333);
		if(!socketChannel.connect(address)) {
			while(!socketChannel.finishConnect()) {
				System.out.println("连接需要时间");
			}
		}
		String str = "QUERY THE TIME";
		ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
		socketChannel.write(buffer);
		
		System.in.read();
		
	}
}
