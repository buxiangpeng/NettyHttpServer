package com.example.demo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ServerlInitializer extends ChannelInitializer<SocketChannel>{
	
	@Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        //负载http 请求编码解码
        channelPipeline.addLast(new HttpServerCodec());
        channelPipeline.addLast( new HttpObjectAggregator(65536));  
        channelPipeline.addLast(new ChunkedWriteHandler());  
        //实际处理请求
        channelPipeline.addLast(new ServerHandler());
    }
}
