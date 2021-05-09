package com.wmx.reddoor.scoket.spring;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * Spring WebSocket 配置类
 * 1、实现 WebSocketConfigurer 接口：这个接口中只有 registerWebSocketHandlers 一个方法
 * * @Component ：表示此类交由 Spring 容器管理，让它来进行IOC(即创建对象)
 * * @EnableWebSocket : 表示这个类支持 webSocket，这里就是 webSocket 后台服务端
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/9 10:50
 */
@Component
@EnableWebSocket
public class WebSocketChatConfig implements WebSocketConfigurer {

    /**
     * @Resource : 将 WebSocketHandler 处理器注册到 webSocket 服务器中
     */
    @Resource
    WebSocketChatHandler webSocketChatHandler;

    /**
     * 1、用于注册后台webSocket处理器，注意可以注册多个哦
     * 2、握手成功后，所有的连接，消息处理等主要操作全都要在webSocket处理器中完成
     *
     * @param webSocketHandlerRegistry ：提供配置{@link WebSocketHandler}请求映射的方法。
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        /**
         * 在指定的 URL 路径上配置 WebSocketHandler:
         * 一：/websocket/spring/chat.action：是 webSocket 客户端 连接的地址，
         * 二：h5 webSocket 客户端连接的路径如：ws://localhost:8080/websocket/spring/chat.action
         * 三："ws://" 是浏览器 webSocket 的固定写法，必须写死，后面依次是 ip(域名)、端口、应用名、下面的地址
         * 四：addInterceptors：为握手请求配置拦截器。
         */
        webSocketHandlerRegistry.addHandler(webSocketChatHandler, "/websocket/spring/chat.action").addInterceptors(new WebSocketChatInterceptor());
    }
}
















