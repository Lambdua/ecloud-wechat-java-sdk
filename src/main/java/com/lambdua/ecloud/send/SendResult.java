package com.lambdua.ecloud.send;

/**
 * @author LiangTao
 * @date 2024年06月11 14:35
 **/
public record SendResult(
        /**
         * 类型
         */
        Integer type,

        /**
         * 消息msgId
         */
        Long msgId,

        /**
         * 消息newMsgId
         */
        Long newMsgId,

        /**
         * 消息发送时间戳
         */
        Long createTime,

        /**
         * 消息接收方id
         */
        String wcId
) {
}
