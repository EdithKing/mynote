package com.study.netty.one;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {

	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChildChannelHandler());
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();
			// 等待服务器监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//			使用LineBasedFrameDecoder解决粘包拆包，分割的字符串为\r\n或者\n
//			ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
			ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new TimeServerHandler());
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 3333;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (Exception e) {
			}
		}
		System.out.println("服务器启动");
		new TimeServer().bind(port);
	}

}
