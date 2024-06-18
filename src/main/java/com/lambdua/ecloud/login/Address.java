package com.lambdua.ecloud.login;

import lombok.Data;

import java.util.List;

/**
 * @author LiangTao
 * @date 2024年06月11 10:40
 **/
@Data
public class Address {

    /**
     * 群组列表
     */
    private List<String> chatrooms;

    /**
     * 好友列表,不包含企微好友
     */
    private List<String> friends;

    /**
     * 公众号列表
     */
    private List<String> ghs;

    /**
     * 微信其他相关
     */
    private List<String> others;
}
