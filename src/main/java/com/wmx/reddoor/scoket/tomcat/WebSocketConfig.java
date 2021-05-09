package com.wmx.reddoor.scoket.tomcat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/9 10:59
 */
@Configuration
public class WebSocketConfig {
    /**
     * 创建 ServerEndpointExporter 对象，交由 spring IOC 容器管理，它会自动扫描注册应用中所有的 @ServerEndpoint
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}