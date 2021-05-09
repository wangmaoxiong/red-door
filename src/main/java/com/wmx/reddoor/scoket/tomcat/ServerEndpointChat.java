package com.wmx.reddoor.scoket.tomcat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * * @ServerEndpoint ：标识此类为 Tomcat 的 websocket 服务终端，websocket/tomcat/chat.action 是客户端连接请求的路径
 * * @Component ：将本类交由 spring IOC 容器管理
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/9 10:50
 */
@ServerEndpoint(value = "/websocket/tomcat/chat.action")
@Component
public class ServerEndpointChat {
    private static Logger logger = LoggerFactory.getLogger(ServerEndpointChat.class);
    /**
     * 用 Set 来 存储 客户端 连接
     */
    private static Set<Session> sessionSet = new HashSet<>();

    /**
     * 连接成功后自动触发
     *
     * @param session
     */
    @OnOpen
    public void afterConnectionEstablished(Session session) {
        /**
         * session 表示一个连接会话，整个连接会话过程中它都是固定的，每个不同的连接 session 不同
         * String queryString = session.getQueryString();//获取请求地址中的查询字符串
         * Map<String, List<String>> parameterMap = session.getRequestParameterMap();//获取请求地址中参数
         * Map<String, String> stringMap = session.getPathParameters();
         * URI uri = session.getRequestURI();
         */
        sessionSet.add(session);
        logger.info("新客户端加入，session id=" + session.getId() + ",当前客户端格个数为：" + sessionSet.size());

        /**
         * session.getBasicRemote().sendText(textMessage);同步发送
         * session.getAsyncRemote().sendText(textMessage);异步发送
         */
        session.getAsyncRemote().sendText("我是服务器，你连接成功!" + new Date());
    }

    /**
     * 连接断开后自动触发，连接断开后，应该清楚掉 session 集合中的值
     *
     * @param session
     */
    @OnClose
    public void afterConnectionClosed(Session session) {
        sessionSet.remove(session);
        logger.info("客户端断开，session id=" + session.getId() + ",当前客户端格个数为：" + sessionSet.size());
    }

    /**
     * 收到客户端消息后自动触发
     *
     * @param session
     * @param textMessage ：客户端传来的文本消息
     */
    @OnMessage
    public void handleMessage(Session session, String textMessage) {
        try {
            logger.info("接收到客户端信息，session id=" + session.getId() + ":" + textMessage);
            /**
             * 原样回复文本消息
             * getBasicRemote：同步发送
             * session.getAsyncRemote().sendText(textMessage);异步发送
             * */
            session.getBasicRemote().sendText("收到【" + textMessage + "】");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息传输错误后
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void handleTransportError(Session session, Throwable throwable) {
        System.out.println("shake client And server handleTransportError,session.getId()=" + session.getId() + " -- " + throwable.getMessage());
        logger.error("与客户端 session id=" + session.getId() + " 通信错误...");
    }
}