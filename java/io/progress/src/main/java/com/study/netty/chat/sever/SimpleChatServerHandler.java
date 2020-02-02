package com.study.netty.chat.sever;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SimpleChatServerHandler extends ChannelHandlerAdapter {

	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			if (channel != incoming) {
				channel.writeAndFlush("[" + incoming.remoteAddress() +"] \n" + msg + "\n");
			} else {
				channel.writeAndFlush("                                                [me] " + msg + "\n");
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close().sync();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("已經有多少個人了" + channels.size());
		for (Channel channel : channels) {
			channel.writeAndFlush("                     [SERVER] - " + incoming.remoteAddress() + " 用户： " + incoming.id() + " 加入");
		}
		channels.add(ctx.channel());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		Channel incoming = ctx.channel();
		for (Channel channel : channels) {
			channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 用户： " + incoming.id() + " 离开\n");
		}
		channels.remove(ctx.channel());
	}
}
