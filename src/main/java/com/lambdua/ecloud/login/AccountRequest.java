package com.lambdua.ecloud.login;

import lombok.Builder;

/**
 * @author LiangTao
 * @date 2024年06月12 15:00
 **/
@Builder
public record AccountRequest(
        String account,
        String password

) {
}
