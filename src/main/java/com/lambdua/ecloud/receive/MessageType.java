package com.lambdua.ecloud.receive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author LiangTao
 * @date 2024年06月11 14:55
 **/
@Getter
public enum MessageType {
    // 通知消息
    TEST_CALLBACK_URL("测试回调地址", "00000"),
    OFFLINE_NOTIFICATION("离线通知", "30000"),
    FRIEND_ADD_REQUEST("好友添加请求", "30001"),
    DOWNLOAD_VIDEO_COMPLETE("下载消息视频完成通知", "30002"),
    SEND_VIDEO_MOMENT_COMPLETE("发送视频朋友圈完成通知", "30003"),

    // 私聊消息
    PRIVATE_TEXT("私聊文本", "60001"),
    PRIVATE_IMAGE("私聊图片", "60002"),
    PRIVATE_VIDEO("私聊视频", "60003"),
    PRIVATE_VOICE("私聊语音", "60004"),
    BUSINESS_CARD("名片消息", "60005"),
    EMOJI("emoji表情", "60006"),
    LINK("链接消息", "60007"),
    FILE("文件消息", "60008"),
    FILE_SEND_COMPLETE("文件发送完成消息", "60009"),
    MINI_PROGRAM("小程序", "60010"),
    CHAT_RECORD("聊天记录", "60011"),
    VOICE_CHAT("语音聊天", "60012"),
    VOICE_CHAT_HANGUP("语音聊天挂断", "60013"),
    QUOTED_MESSAGE("引用消息", "60014"),
    TRANSFER("转账", "60015"),
    RED_PACKET("红包", "60016"),
    VIDEO_CHANNEL("视频号", "60017"),
    RECALL_MESSAGE("撤回消息", "60018"),
    TAP("拍一拍", "60019"),
    LOCATION("地理位置", "60020"),
    MUSIC_SHARE("音乐分享", "60021"),
    GROUP_CHAT_INVITE_LINK("群聊邀请链接", "60022"),
    FRIEND_INFO_CHANGE_NOTIFICATION("好友信息变更通知", "65001"),
    FRIEND_VERIFICATION_NOTIFICATION("好友开启朋友验证通知(被删除)", "65002"),
    BLACKLIST_NOTIFICATION("被对方绝收了(被拉黑)", "65003"),
    DELETE_FRIEND("删除好友", "65004"),
    PRIVATE_OTHER("私聊其他消息", "60999"),

    // 群聊消息
    GROUP_TEXT("群聊文本", "80001"),
    GROUP_IMAGE("群聊图片", "80002"),
    GROUP_VIDEO("群聊视频", "80003"),
    GROUP_VOICE("群聊语音", "80004"),
    GROUP_BUSINESS_CARD("名片消息", "80005"),
    GROUP_EMOJI("emoji表情", "80006"),
    GROUP_LINK("链接消息", "80007"),
    GROUP_FILE("文件消息", "80008"),
    GROUP_FILE_SEND_COMPLETE("文件发送完成消息", "80009"),
    GROUP_MINI_PROGRAM("小程序", "80010"),
    GROUP_CHAT_RECORD("聊天记录", "80011"),
    GROUP_VOICE_CHAT("语音聊天", "80012"),
    GROUP_QUOTED_MESSAGE("引用消息", "80014"),
    GROUP_TRANSFER("转账", "80015"),
    GROUP_RED_PACKET("红包", "80016"),
    GROUP_VIDEO_CHANNEL("视频号", "80017"),
    GROUP_RECALL_MESSAGE("撤回消息", "80018"),
    GROUP_TAP("拍一拍", "80019"),
    GROUP_LOCATION("地理位置", "80020"),
    GROUP_MUSIC_SHARE("音乐分享", "80021"),
    GROUP_INFO_CHANGE_NOTIFICATION("群聊信息变更通知", "85001"),
    GROUP_KICKED_OUT("被移出群聊", "85002"),
    GROUP_KICK_OUT_MEMBER("你把xxx踢出群聊", "85003"),
    GROUP_DISMISS("解散群聊", "85004"),
    GROUP_NAME_CHANGE("修改群名称", "85005"),
    GROUP_ADMIN_ADDED("xxx被添加为管理员", "85006"),
    GROUP_ADMIN_REMOVED("xxx从群管理员中被移除", "85007"),
    GROUP_MEMBER_INVITE("xxx邀请xxx加入群聊", "85008"),
    GROUP_MEMBER_JOIN("xxx通过扫描xxx的二维码加入群聊", "85009"),
    GROUP_OWNER_CHANGE("更换群主", "85010"),
    GROUP_ANNOUNCEMENT("发布群公告", "85011"),
    GROUP_TASK("群待办", "85012"),
    GROUP_INVITE_CONFIRMATION("群聊邀请确认", "85013"),
    GROUP_INVITE_VERIFICATION_NOTIFICATION("开启'群聊邀请确认'后的邀请验证通知", "85014"),
    GROUP_EXIT("退出群聊", "85015"),
    GROUP_OTHER("群聊其他消息", "80999"),

    // 公众号消息
    PUBLIC_ACCOUNT_ARTICLE("公众号文章", "90001"),
    UNFOLLOW_PUBLIC_ACCOUNT("取关公众号", "95001");

    @JsonValue
    private final String description;

    private final String typeId;

    @JsonCreator
    public static MessageType forValue(String value) {
        for (MessageType messageType : values()) {
            if (messageType.typeId.equals(value)) {
                return messageType;
            }
        }
        throw new IllegalArgumentException("Unknown typeId: " + value);
    }

    MessageType(String description, String typeId) {
        this.description = description;
        this.typeId = typeId;
    }

}

