package com.lambdua.ecloud.chat_room;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * @author LiangTao
 * @date 2024年06月13 14:43
 **/
@Data
@FieldNameConstants
public class ChatRoomMember {
    /**
     * 群号
     */
    private String chatRoomId;

    /**
     * 群成员微信号
     * 假如需要手机上显示的微信号或更详细的信息，则需要再调用获取群成员详情接口获取
     */
    @JsonProperty("userName")
    private String wcId;

    /**
     * 群成员默认昵称
     */
    private String nickName;

    /**
     * 群成员修改后的昵称
     */
    private String displayName;

    /**
     * 微信号
     */
    private String aliasName;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 签名
     */
    private String signature;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 大头像
     */
    @JsonAlias("bigHead")
    private String bigHeadImgUrl;

    /**
     * 小头像
     */
    @JsonAlias("smallHead")
    private String smallHeadImgUrl;

    /**
     * 群成员标志
     */
    private Integer chatRoomMemberFlag;

    /**
     * 邀请人微信号（仅有群主和管理可以看到）
     */
    private String inviterUserName;


    private String labelList;

    /**
     * v1
     */
    private String v1;

    /**
     * v2
     */
    private String v2;


    private String chatRoomOwner;

    private Integer memberCount;

    private String notify;

    private String chatRoomMembers;

    private Boolean isManage;

    /**
     * 姓名备注
     */
    private String remark;


}
