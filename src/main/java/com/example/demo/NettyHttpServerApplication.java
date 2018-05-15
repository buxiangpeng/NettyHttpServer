package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyHttpServerApplication {

	public static void main(String[] args) {
		//SpringApplication.run(NettyHttpServerApplication.class, args);
		new Server(8800);
	}
}
