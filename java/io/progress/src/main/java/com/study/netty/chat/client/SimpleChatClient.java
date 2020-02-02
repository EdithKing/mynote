package com.study.netty.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleChatClient {
	private int port;
	private String host;

	public SimpleChatClient() {
		this(3333, "127.0.0.1");
	}

	public SimpleChatClient(int port, String host) {
		this.port = port;
		this.host = host;
	}

	public void connect() throws InterruptedException, IOException {
		EventLoopGroup clientGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(clientGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
					.handler(new SimpleChatClientInitializer());

			Channel channel = b.connect(host, port).sync().channel();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				channel.writeAndFlush(in.readLine() + "\r\n");
			}
		} finally {
			clientGroup.shutdownGracefully();
		}
	}
	public static void main(String[] args) throws Exception {
		new SimpleChatClient().connect();
	}

}
