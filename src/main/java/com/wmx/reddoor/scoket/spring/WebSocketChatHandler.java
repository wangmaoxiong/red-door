package com.wmx.reddoor.scoket.spring;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * 1、@Component ：表示交由 Spring 容器管理，由它实例化，WebSocketConfig 中还需要注入它
 * 2、这个类作为 webSocket 的处理器，所有的消息交互都在这里进行
 * 3、WebSocketHandler : spring webSocket 处理器的根接口, 一共五个方法，除了实现此接口，还可以实现它的子接口，或继承它的实现类
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/9 10:50
 */
@Component
public class WebSocketChatHandler implements WebSocketHandler {
    /**
     * 在线客户端列表
     * 1、当客户端连接成功后，我们将它的唯一标识以及它的WebSocketSession放入进来
     * 2、有了WebSocketSession，则可以给任何客户端发送消息
     * 复习一下：
     * 1）static final 如果修饰的基本类型，那么基本类型的值不能再修改
     * 2）static final 如果修饰的 Set、List、Map、Array 等，那么是它指向的地址不能再修改，里面的值是可以修改的
     */
    private static final Map<String, WebSocketSession> CLIENT_MAP = new Hashtable<>();

    /**
     * WebSocket 客戶端连接建立成功后自动触发
     *
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        // getAttributes 返回的 Map 是 WebSocketInterceptor "握手前"方法中的参数 map，那里存放的值，这里可以直接获取
        Map<String, Object> map = webSocketSession.getAttributes();
        String clientId = (String) map.get("clientId");
        clientId = clientId == null || clientId.length() == 0 ? webSocketSession.getId() : clientId;

        CLIENT_MAP.put(clientId, webSocketSession);

        String message = "客户端【" + clientId + "】连接成功";
        System.out.println(message);

        TextMessage textMessage = new TextMessage(message);
        this.sendMessageToClient(clientId, textMessage);
    }

    /**
     * WebSocket 客户端 发送消息来时自动触发
     *
     * @param webSocketSession ：websocket 会话
     * @param webSocketMessage ：websocket 消息
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession,
                              WebSocketMessage<?> webSocketMessage) throws Exception {
        Map<String, Object> map = webSocketSession.getAttributes();
        String clientId = (String) map.get("clientId");

        // 获取客户端发来的消息内容，不会为 null
        String message = (String) webSocketMessage.getPayload();
        // 获取消息的字节长度
        int messageLength = webSocketMessage.getPayloadLength();

        System.out.println("客户端【" + clientId + "】发来消息，长度为：" + messageLength + " 字节，内容为：" + message);

        // 给客户端回复消息
        WebSocketMessage feedbackMessage = new TextMessage("谢谢! 收到您的消息：" + message);
        webSocketSession.sendMessage(feedbackMessage);
    }

    /**
     * 消息传输错误后自动触发
     * 1、相应操作是：关闭出错会话的连接，意思是要 WebSocketSession.close 调用
     * 2、同时如果有记录了这个 WebSocketSession，也要移除它
     *
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws IOException {
        Map<String, Object> map = webSocketSession.getAttributes();
        String clientId = (String) map.get("clientId");

        System.out.println("客户端【" + clientId + "】消息传输错误，关闭连接。");
        if (webSocketSession.isOpen()) {
            //如果 chrome 浏览器在连接成功的情况进行页面刷新，下面 close 的时候，会抛异常 而firefox则不会
            webSocketSession.close(CloseStatus.BAD_DATA);
            CLIENT_MAP.remove(clientId);
        }
    }

    /**
     * 与客户端连接关闭后自动触发
     * 1、连接关闭意思是 WebSocketSession 已经 close 了
     * 2、相应的操作是：如果我们有记录了这个 WebSocketSession，则要移除它
     *
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        Map<String, Object> map = webSocketSession.getAttributes();
        String clientId = (String) map.get("clientId");

        System.out.println("客户端【" + clientId + "】连接已经关闭");
        CLIENT_MAP.remove(clientId);
    }

    /**
     * 方法字面意思是：支持部分媒体消息，不常用
     *
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给指定用户发送信息 - 提供给其它业务方法调用
     *
     * @param clientId    ：客户端标识ID
     * @param textMessage : 待发送的消息, TextMessage 是间接实现了 WebSocketMessage 接口的
     * @return 返回是否发送成功
     */
    public boolean sendMessageToClient(String clientId, TextMessage textMessage) {
        boolean result = false;
        System.out.println("向客户端【" + clientId + "】发送消息：\"" + textMessage.getPayload());
        try {
            if (clientId == null || "".equals(clientId) || textMessage == null || textMessage.getPayloadLength() <= 0) {
                return false;
            }
            WebSocketSession webSocketSession = CLIENT_MAP.get(clientId);
            if (webSocketSession != null && webSocketSession.isOpen()) {
                webSocketSession.sendMessage(textMessage);
                result = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 广播信息 ：给所有用户发送信息
     *
     * @param textMessage : 待发送的消息,TextMessage是间接实现了WebSocketMessage接口的
     * @return
     */
    public boolean sendMessageToAllClient(TextMessage textMessage) {
        boolean result = false;
        System.out.println("向所有客户端发送消息：" + textMessage.getPayload());
        if (textMessage == null || textMessage.getPayloadLength() <= 0) {
            return false;
        }
        try {
            Set<Map.Entry<String, WebSocketSession>> entrySet = CLIENT_MAP.entrySet();
            for (Map.Entry<String, WebSocketSession> entry : entrySet) {
                WebSocketSession webSocketSession = entry.getValue();
                if (webSocketSession != null && webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(textMessage);
                }
            }
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
