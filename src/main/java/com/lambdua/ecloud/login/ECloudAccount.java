package com.lambdua.ecloud.login;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author LiangTao
 * @date 2024年06月12 15:13
 **/
@Data
public class ECloudAccount {
    private String callbackUrl;
    private int status; // 状态（0：正常，1：冻结，2：到期）
    @JsonAlias("Authorization")
    private String token;
}
