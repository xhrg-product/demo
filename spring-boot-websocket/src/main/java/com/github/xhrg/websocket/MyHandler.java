package com.github.xhrg.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("=====接受到的数据" + payload);
        session.sendMessage(new TextMessage("服务器返回收到的信息, " + payload));
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                    session.sendMessage(new TextMessage("3秒后服务器返回收到的信息, " + payload));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }
}