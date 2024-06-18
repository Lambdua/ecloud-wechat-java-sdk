package com.lambdua.ecloud.receive;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * @author LiangTao
 * @date 2024年06月12 17:56
 **/

@Data
@FieldNameConstants
public class DetailReceiveData {

    @JsonProperty("wId")
    private String wId;

    /**
     * 异步ID
     */
    private String asynId;

    /**
     * 添加联系人场景
     */
    private Integer addContactScene;

    /**
     * @列表
     */
    @JsonAlias({"atlist", "atList"})
    private List<String> atList;

    /**
     * bigHeadImgUrl	大头像URL
     */
    private String bigHeadImgUrl;

    /**
     * 小头像URL
     */
    private String smallHeadImgUrl;

    /**
     * 缩略图URL
     */
    private String thumbUrl;

    /**
     * 位编码
     */
    private Long bitMask;

    /**
     * 消息变更通知 1:变更
     */
    private Integer chatRoomNotify;

    /**
     * 聊天室拥有者
     */
    private String chatRoomOwner;

    /**
     * 聊天室状态
     */
    private Integer chatRoomStatus;

    /**
     * 描述: 变更
     */
    @JsonAlias("des")
    private String description;


    /**
     * 内容
     */
    private String content;


    /**
     * 时间戳
     */
    @JsonAlias("createTime")
    private Long timestamp;

    /**
     * 加密用户名
     */
    private String encryptUserName;


    /*
     * 发送群号
     */
    private String fromGroup;

    /**
     * 缓存ID
     */
    private String bufId;

    /**
     * 发送者
     */
    private String fromUser;

    /**
     * ID: ecloud那边返回的id
     */
    @JsonProperty("id")
    private String eCloudWechatId;

    /**
     * 图片
     */
    private String img;

    /**
     * 标签ID列表
     */
    private String labelIdList;

    /**
     * 长度
     */
    private Integer length;

    /**
     * MD5值
     */
    private String md5;

    /**
     * 成员数量
     */
    private Integer memberCount;

    /**
     * 消息ID
     */
    private Long msgId;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 新消息ID
     */
    private Long newMsgId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 描述
     */
    private String objectDesc;

    /**
     * 是否为自己
     */
    private Boolean self;

    /**
     * 标题
     */
    private String title;

    /**
     * 接收者
     */
    private String toUser;

    /**
     * 类型
     */
    private Integer type;

    /**
     * URL
     */
    private String url;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 语音长度
     */
    private Integer voiceLength;

    private String remark;

    private String pushContent;

    private String alias;

    private String remarkName;

    private String sourceUser;

    private String v1;
}
