package com.chale.chart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/**
 *
 * @author PengBin
 * @date 2016年6月24日 下午6:04:39
 */
public class LogWebSocketHandler extends TextWebSocketHandler {
    private static Logger log = LoggerFactory.getLogger(LogWebSocketHandler.class);

    public LogWebSocketHandler() {
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("ConnectionEstablished");

        session.sendMessage(new TextMessage("您已进入聊天室"));

    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String text = message.getPayload(); // 获取提交过来的消息
        System.out.println("handMessage:" + text);
        // template.convertAndSend("/topic/getLog", text); // 这里用于广播
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.debug("afterConnectionClosed" + closeStatus.getReason());

    }
}