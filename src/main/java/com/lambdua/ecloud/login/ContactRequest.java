package com.lambdua.ecloud.login;

import lombok.Builder;

/**
 * @author LiangTao
 * @date 2024年06月11 14:25
 **/
@Builder
public record ContactRequest(
        /**
         * wId	是	String	登录实例标识
         * wcId	是	String	好友微信id/群id,多个好友/群 以","分隔每次最多支持20个微信/群号,记得本接口随机间隔300ms-1500ms，频繁调用容易导致掉线
         */

        String wId,
        String wcId

) {
}
