package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

public class ServerHandler extends SimpleChannelInboundHandler<HttpObject>{
	
	
	@Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		//请求体内数据
		FullHttpRequest httpRequest = (FullHttpRequest)msg;
        
        HttpRequest request = (HttpRequest) msg;
        String uri = request.uri();
        HttpMethod method = request.method();
        boolean isGet = method.equals(HttpMethod. GET);
        boolean isPost = method.equals(HttpMethod. POST);
        System. out.println(String. format("Uri:%s method %s", uri, method));
        
        if(isGet){
              System.out.println("doing something here.");
              String param = "hello";
              String str = getParamerByNameFromGET(param,request);
              System. out.println(param + ":" + str);
        }
        if(isPost){
        	ByteBuf jsonBuf = httpRequest.content();
            String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
            System.out.println("接收到数据："+jsonStr);
        }
         
        ctx.writeAndFlush("received your message !\n");  
    }
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 给ChannelFuture加上关闭的监听器
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	/**
     * 根据传入参数的key获取value
     * @param name
     * @param decoderQuery
     * @return
     */
    private String getParameterByName(String name, QueryStringDecoder decoderQuery) {
          Map<String, List<String>> uriAttributes = decoderQuery.parameters();
           for (Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                String key = attr.getKey();
                 for (String attrVal : attr.getValue()) {
                       if (key.equals(name)) {
                             return attrVal;
                      }
                }
          }
           return null;
    }
    
    private String getParamerByNameFromGET(String name,HttpRequest request) {
        QueryStringDecoder decoderQuery = new QueryStringDecoder(request.uri());
         return getParameterByName(name, decoderQuery);
    }
}
