package com.example.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
	
	 //负责接收客户端连接
    EventLoopGroup boosGroup = new NioEventLoopGroup();
    //处理连接
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    ServerBootstrap bootstrap = null;
	
	public Server(Integer port) {
		if(bootstrap == null) {
			bootstrap = new ServerBootstrap();
	        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
	        bootstrap.group(boosGroup,workerGroup)
	            .channel(NioServerSocketChannel.class)
	            .childHandler(new ServerlInitializer());
	        //绑定端口号
	        ChannelFuture channelFuture;
			try {
				channelFuture = bootstrap.bind(port).sync();
				channelFuture.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        boosGroup.shutdownGracefully();
	        workerGroup.shutdownGracefully();
		}
	}
}
