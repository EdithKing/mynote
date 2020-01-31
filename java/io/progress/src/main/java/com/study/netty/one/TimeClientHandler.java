package com.study.netty.one;

import java.nio.file.FileVisitResult;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {
//	private final ByteBuf firstMessage;

	private int counter;

	private byte[] req;

	public TimeClientHandler() {
//		req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
		req = ("QUERY TIME ORDER$_").getBytes();
//		firstMessage = Unpooled.buffer(req.length);
//		firstMessage.writeBytes(req);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ByteBuf buf = (ByteBuf) msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req, "UTF-8");
		System.out.println("Now is : " + msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
