package com.lambdua.ecloud.send;

import lombok.Builder;

/**
 * @author LiangTao
 * @date 2024年06月11 14:37
 **/
@Builder
public record SendRequest(

        /**
         * 登录实例标识
         */
        String wId,

        /**
         * 接收人微信id/群id
         */
        String wcId,

        String content,

        /**
         * 文件url链接
         */
        String path,
        /**
         * 文件名
         */
        String fileName,

        String base64,

        /**
         * 群聊中@某人
         */
        String at
) {
}
