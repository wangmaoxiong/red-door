package com.wmx.reddoor.scoket.spring;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

/**
 * 1、每次用户在浏览器客户端 js 中发起 webSocket 连接后，都会先进入本拦截器，主要作用如下：
 * 1）用于权限控制：比如可以控制没有登录的用户或者权限不足的用户无法连接进行操作
 * 2）用于标识用户：便于在 webSocket 处理器(WebSocketFacebookHandler)中向指定用户发送消息
 * 3）还可以进行其它的类似安全框架的安全监测，日志系统的记录日志等
 * 2、HandshakeInterceptor 接口中只有两个方法 beforeHandshake(握手前)、afterHandshake(握手后)
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/9 10:50
 */
public class WebSocketChatInterceptor implements HandshakeInterceptor {
    /**
     * 握手前
     * 1、无论是登录型系统还是随意可以访问的系统(如门户网站)，标识用户的方法很多：
     * 一：可以在用户从后台进入前端页面的时候设置 HttpSession，于是再从客户端 js 中 webSocket 发起连接时，就可以在下面取值了，即知道是谁连接了
     * 二：直接在客户端 js 中 webSocket 连接的路径中附带一个唯一的参数过来，如：ws://localhost:8080/facebook/webSocket.action?clientId=xxx
     *
     * @param serverHttpRequest  ：2、ServerHttpRequest 强转为 ServletServerHttpRequest，然后通过 getServletRequest 方法得到 HttpServletRequest
     *                           前面两个是 Spring 的类，HttpServletRequest 则是熟悉的 servlet 了
     * @param serverHttpResponse ：与上面同理
     * @param webSocketHandler   ：3、WebSocket 消息和生命周期事件的处理程序。
     * @param map                ：4、获取了用户标识之后可以将它放入 map 中，因为接收消息的主战场是在 webSocketHandler 处理器中
     *                           这个参数 Map 类似一个容器，可以在 webSocketHandler 处理器中从 WebSocketSession 中再取出来，
     *                           WebSocketSession 在整个连接会话中都会存在的，因为 Spring 既然提供了 Session，那就无需再使用 HttpSession 存值这么麻烦了
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                   ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler,
                                   Map<String, Object> map) throws Exception {

        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpServletRequest httpServletRequest = servletServerHttpRequest.getServletRequest();

            String clientId = httpServletRequest.getParameter("clientId");
            clientId = clientId == null || clientId.length() == 0 ? UUID.randomUUID().toString().replace("-", "") : clientId;
            System.out.println("webSocket 拦截成功，clientId=" + clientId);
            map.put("clientId", clientId);

            /**
             * 如果返回 true，则会进入 webSocket 处理器中进行下一步的 "连接" 操作，连接成功后就能互通消息了
             * 如果返回 false，则不会再进行下一步，客户端 webSocket 直接握手失败，即连接失败
             */
            return true;
        } else {
            return false;
        }
    }

    /**
     * 握手后
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
