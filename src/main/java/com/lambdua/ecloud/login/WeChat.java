package com.lambdua.ecloud.login;

import lombok.Data;

/**
 * 登录的微信账号信息
 *
 * @author LiangTao
 * @date 2024年06月19 10:49
 **/
@Data
public class WeChat {

    /**
     * 微信实例id
     */
    private String wId;

    /**
     * 微信id
     */
    private String wcId;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 国家
     */
    private String country;

    /**
     * 城市
     */
    private String city;


    /**
     * 签名
     */
    private String signature;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headUrl;

    /**
     * 小头像
     */
    private String smallHeadImgUrl;

    /**
     * 手机号
     */
    private String mobilePhone;


    /**
     * 性别
     */
    private Integer sex;

    /**
     * 手机上显示的微信号(用户若手机改变微信号，本值会变)
     */
    private String wAccount;

    /**
     * 识别码
     */
    private Integer uin;

    /**
     * 保留字段
     */
    private String status;

    private String username;

    private Integer newDevice;

    private Integer source;

    private Integer type;

    private String deviceId;

    private String mac;

}
