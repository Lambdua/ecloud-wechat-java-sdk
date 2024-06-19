package com.lambdua.ecloud.client;

import com.lambdua.ecloud.chat_room.ChatRoomMember;
import com.lambdua.ecloud.common.ApiResult;
import com.lambdua.ecloud.download.GetImgRequest;
import com.lambdua.ecloud.login.Address;
import com.lambdua.ecloud.login.Contact;
import com.lambdua.ecloud.login.WeChat;
import com.lambdua.ecloud.send.SendRequest;
import com.lambdua.ecloud.send.SendResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;

/**
 * @author LiangTao
 * @date 2024年06月11 10:21
 **/
public interface ECloudClient {
    /*---- ecloud账户管理----- */

    /**
     * 查询账号中在线的微信列表
     */
    @POST("queryLoginWx")
    Call<ApiResult<List<Map<String, String>>>> queryLoginWx();

    /**
     * 执行微信登录,以登录的可以使用这个查询微信账户信息
     */
    @POST("getIPadLoginInfo")
    Call<ApiResult<WeChat>> getIPadLoginInfo(@Body Map<String, Object> request);





    /*--------------------------login相关-------------------------*/

    /**
     * 初始化通讯录列表
     */
    @POST("initAddressList")
    Call<ApiResult<Void>> initAddress(@Body Map<String, Object> request);

    /**
     * 获取通讯录列表
     */
    @POST("getAddressList")
    @RateLimit(limit = 10)
    Call<ApiResult<Address>> getAddress(@Body Map<String, Object> request);

    /**
     * 获取通讯录的联系人详情
     */
    @POST("getContact")
    @RateLimit(limit = 10, delay = "300,1500", type = "contactDetail")
    Call<ApiResult<List<Contact>>> getContact(@Body Map<String, Object> request);

    /*--------------------------login相关-------------------------*/


    /*--------------------------发送消息相关-------------------------*/

    /**
     * 发送文本消息,一分钟限制四十次,随机延迟200~600毫秒在发送
     */
    @POST("sendText")
    @RateLimit(limit = 40, limitTime = 60, type = "sendMsg", delay = "200,600")
    Call<ApiResult<SendResult>> sendText(@Body SendRequest sendTextRequest);

    /**
     * 发送语音消息
     */
    @POST("sendVoice")
    @RateLimit(limit = 20, limitTime = 60, delay = "500,4000", type = "sendMsg")
    Call<ApiResult<SendResult>> sendVoice(@Body Map<String, Object> request);

    /**
     * 发送图片消息 sendImg接口返回的data中没有msgId相关信息
     */
    @POST("sendImg2")
    @RateLimit(type = "sendMsg")
    Call<ApiResult<SendResult>> sendImg(@Body Map<String, Object> request);

    /**
     * 发送链接消息
     */
    @POST("sendUrl")
    @RateLimit(type = "sendMsg")
    Call<ApiResult<SendResult>> sendUrl(@Body Map<String, Object> request);

    /**
     * 发送名片消息
     */
    @POST("sendNameCard")
    @RateLimit(type = "sendMsg")
    Call<ApiResult<SendResult>> sendNameCard(@Body Map<String, Object> request);

    /**
     * 发送表情包
     */
    @POST("sendEmoji")
    @RateLimit(type = "sendMsg")
    Call<ApiResult<SendResult>> sendEmoji(@Body Map<String, Object> request);

    /**
     * 撤回消息
     */
    @POST("revokeMsg")
    @RateLimit(limit = 10, limitTime = 120, delay = "1000,2000", type = "revokeMsg")
    Call<ApiResult<Void>> revokeMsg(@Body Map<String, Object> request);




    /*--------------------------发送消息相关-------------------------*/


    /*--------------------------下载相关-------------------------*/

    /**
     * 下载图片
     */
    @POST("getMsgImg")
    @RateLimit(limit = 10, limitTime = 60, delay = "500,4000", type = "download")
    Call<ApiResult<Map<String, Object>>> getMsgImg(@Body GetImgRequest getImgRequest);

    /**
     * 下载语音
     */
    @POST("getMsgVoice")
    @RateLimit(type = "download")
    Call<ApiResult<Map<String, Object>>> getMsgVoice(@Body Map<String, Object> request);

    /**
     * 下载表情包
     */
    @POST("getMsgEmoji")
    @RateLimit(type = "download")
    Call<ApiResult<Map<String, Object>>> getMsgEmoji(@Body Map<String, Object> request);


    /*--------------------------下载相关-------------------------*/


    /*--------------------------群聊相关-------------------------*/

    /**
     * 群保存/取消到通讯录
     */
    @POST("showInAddressBook")
    Call<ApiResult<Void>> showInAddressBook(@Body Map<String, Object> request);


    /**
     * 获取群成员列表
     */
    @POST("getChatRoomMember")
    Call<ApiResult<List<ChatRoomMember>>> getChatRoomMemberList(@Body Map<String, Object> request);

    /**
     * 获取群成员详情
     */
    @POST("getChatRoomMemberInfo")
    @RateLimit(limit = 60, limitTime = 60, delay = "100,1000", type = "chatRoomDetail")
    Call<ApiResult<List<ChatRoomMember>>> getDetailChatRoomMember(@Body Map<String, Object> request);

    /**
     * 自动通过群（url）
     */
    @POST("acceptUrl")
    @RateLimit(limit = 1, limitTime = 120, delay = "1000,2000", type = "acceptGroupInvite")
    Call<ApiResult<Void>> acceptInvitation(@Body Map<String, Object> request);


    /*--------------------------群聊相关-------------------------*/

}
