package com.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer {

	private int port;

	public MultiplexerTimeServer(int port) {
		this.port = port;
		register();
	}

	public void register() {
		try {
			ServerSocketChannel channel = ServerSocketChannel.open();

			Selector selector = Selector.open();

			InetSocketAddress address = new InetSocketAddress(port);

			channel.socket().bind(address);
			channel.configureBlocking(false);

			channel.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
				if (selector.select(5000) == 0) {
					System.out.println("服务器等待了1s，没有任何人连接");
					continue;
				}
				Set<SelectionKey> selectionKeys = selector.selectedKeys();

				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					if (key.isAcceptable()) {
						System.out.println("有一个新的客户端连接");
						SocketChannel socketChannel = channel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
					}
					if (key.isReadable()) {
						SocketChannel socketChannel = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						socketChannel.read(buffer);
						String body = new String(buffer.array());
						System.out.println("form 客户端 ： " + body.trim());
//						if (body.trim().equalsIgnoreCase("QUERY THE TIME")) {
//							doWirte(socketChannel, new Date(System.currentTimeMillis()).toString());
//						} else {
//							doWirte(socketChannel, "BAD REQUEST");
//						}
					}
					iterator.remove();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doWirte(SocketChannel socketChannel, String message) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(message.getBytes());
		System.out.println("发送消息" + message);

		try {
			socketChannel.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
