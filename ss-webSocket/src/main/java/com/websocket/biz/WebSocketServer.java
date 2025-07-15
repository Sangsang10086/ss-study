package com.websocket.biz;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@Slf4j
@Service
@ServerEndpoint("/webSocket/{senderId}")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //存储用户ID与session的映射关系
    private static ConcurrentHashMap<String,Session> userSessions = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("senderId") String senderId) {
        userSessions.put(senderId, session);
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:" + senderId + ",当前在线人数为:" + getOnlineCount());
        sendMessage(session,"连接成功，您的用户ID："+senderId);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("senderId") String senderId) {
        userSessions.remove(senderId);
        subOnlineCount();           //在线数减1
        //断开连接情况下，更新主板占用情况为释放
        log.info("释放的userId为："+senderId);
        //这里写你 释放的时候，要处理的业务
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @ Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message,@PathParam("senderId") String senderId) {

        // 解析JSON消息
        JSONObject jsonMessage = new JSONObject(message);
        String type = jsonMessage.getString("type");

        if ("private".equals(type)) {
            // 私聊消息
            String recipientId = jsonMessage.getString("to");
            String content = jsonMessage.getString("content");
            sendPrivateMessage(senderId, recipientId, content);
        } else if ("group".equals(type)) {
            // 群聊消息（示例）
            String groupId = jsonMessage.getString("group");
            String content = jsonMessage.getString("content");
            sendGroupMessage(senderId, groupId, content);
        } else {
            sendMessage(userSessions.get(senderId), "未知消息类型: " + type);
        }
    }

    /**
     * @ Param session
     * @ Param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    // 发送消息的工具方法
    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sendPrivateMessage(String senderId, String recipientId, String content) {
        Session recipientSession = userSessions.get(recipientId);
        if (recipientSession != null && recipientSession.isOpen()) {
            String formattedMessage = String.format("[%s] 对你说: %s", senderId, content);
            sendMessage(recipientSession, formattedMessage);

            // 给发送者回显
            String echoMessage = String.format("你对 [%s] 说: %s", recipientId, content);
            sendMessage(userSessions.get(senderId), echoMessage);
        } else {
            sendMessage(userSessions.get(senderId), "用户 [" + recipientId + "] 不在线或不存在");
        }
    }

    // 发送群聊消息（示例）
    private void sendGroupMessage(String senderId, String groupId, String content) {
        // 实际项目中需要维护群组与成员的关系
        String formattedMessage = String.format("[%s] 在群 [%s] 中说: %s", senderId, groupId, content);

        // 简单示例：向所有用户广播
        userSessions.forEach((userId, session) -> {
            if (!userId.equals(senderId) && session.isOpen()) {
                sendMessage(session, formattedMessage);
            }
        });
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
