package com.lambdua.ecloud.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * @author LiangTao
 * @date 2024年06月11 14:20
 **/
@Data
@FieldNameConstants
public class Contact {

    /**
     * 微信id
     */
    @JsonProperty("userName")
    private String wxId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 签名
     */
    private String signature;

    /**
     * 性别
     */
    private int sex;

    /**
     * 微信号
     */
    private String aliasName;

    /**
     * 国家
     */
    private String country;

    /**
     * 大头像
     */
    private String bigHead;

    /**
     * 小头像
     */
    private String smallHead;

    /**
     * 标签列表
     */
    private String labelList;

    /**
     * 用户的wxId，都是以v1开头的一串数值，v2数据，则是作为v1数据的辅助
     */
    private String v1;

    /**
     * 省份
     */
    private String province;


    /**
     * 城市
     */
    private String city;

    /**
     * 描述
     */
    private String desc;

    /**
     * 卡片图片地址
     */
    private String cardImgUrl;

    /**
     * 拼音首字母
     */
    private String pyInitial;

    /**
     * 备注拼音首字母
     */
    private String remarkPyInitial;

    /**
     * 电话号码列表
     */
    // private String phoneNumList;


}
