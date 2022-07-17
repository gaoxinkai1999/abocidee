package com.abocidee.websocketdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket的处理类。
 * 作用相当于HTTP请求
 * 中的controller
 */
//@Component
@Slf4j
@ServerEndpoint("/api/pushMessage/{username}")
public class WebSocketServer {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收username
     */
    private String username = "";

    /**
     * 发送自定
     * 义消息
     **/
    public static void sendInfo(String message, String username) {
        log.info("发送消息到:" + username + "，报文:" + message);
        if (StringUtils.isNotBlank(username) && webSocketMap.containsKey(username)) {
            webSocketMap.get(username).sendMessage(message);
        } else {
            log.error("用户" + username + ",不在线！");
        }
    }

    /**
     * 获得此时的
     * 在线人数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线人
     * 数加1
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 在线人
     * 数减1
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    /**
     * 连接建立成
     * 功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;
        if (webSocketMap.containsKey(username)) {
            webSocketMap.remove(username);
            //加入set中
            webSocketMap.put(username, this);
        } else {
            //加入set中
            webSocketMap.put(username, this);
            //在线数加1
            addOnlineCount();
        }
        log.info("用户连接:" + username + ",当前在线人数为:" + getOnlineCount());

        sendMessage("连接成功");
    }

    /**
     * 连接关闭
     * 调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(username)) {
            webSocketMap.remove(username);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:" + username + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消
     * 息后调用的方法
     *
     * @param message 客户端发送过来的消息
     **/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:" + username + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                for (WebSocketServer value : webSocketMap.values()) {
                    value.sendMessage(message);
                }
//                //追加发送人(防止串改)
//                jsonObject.put("fromusername", this.username);
//                String tousername = jsonObject.getString("tousername");
//                //传送给对应tousername用户的websocket
//                if (StringUtils.isNotBlank(tousername) && webSocketMap.containsKey(tousername)) {
//                    webSocketMap.get(tousername).sendMessage(message);
//                } else {
//                    //否则不在这个服务器上，发送到mysql或者redis
//                    log.error("请求的username:" + tousername + "不在该服务器上");
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误:" + this.username + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务
     * 器主动推送
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

