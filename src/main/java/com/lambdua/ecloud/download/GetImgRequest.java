package com.lambdua.ecloud.download;

import lombok.Builder;
import lombok.NonNull;

/**
 * @author LiangTao
 * @date 2024年06月12 15:50
 **/
@Builder
public record GetImgRequest(
        /**
         * wId	是	string	登录实例标识 (包含此参数 所有参数都是从消息回调中取）
         * msgId	是	long	消息id
         * content	是	string	收到的消息的xml数据
         * type	否	int	0：常规图片 1：高清图
         */

        String wId,
        @NonNull
        long messageId,
        String content,
        Integer type
) {
}
