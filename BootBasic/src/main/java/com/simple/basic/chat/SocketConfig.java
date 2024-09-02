package com.simple.basic.chat;

// 스프링 설정 파일

import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration //스프링 설정파일임을 나타냄
@EnableWebSocket //소켓을 사용함
public class SocketConfig implements WebSocketConfigurer {


    private SocketHandler socketHandler;

    //생성자로 주입받는 전략을 사용 vs Autowired
    public SocketConfig(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/api/chat") //요청연결을 허용할 경로 //"/api/chat/{example}/{num}"
                .setAllowedOriginPatterns("*") //서버가 다르더라도 허용함
                .withSockJS(); // 소켓 js와 함께~

//        registry.addHandler 는 여러개가 될 수도 있음
    }
}
