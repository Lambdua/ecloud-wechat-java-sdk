package com.lambdua.ecloud.receive;

/**
 * @author LiangTao
 * @date 2024年06月18 11:38
 **/
public interface MessageProcessor {
    /**
     * 处理消息
     *
     * @param message
     */
    void process(MessageResult message);

    boolean supportMessageProcessor(MessageType messageType);
}
