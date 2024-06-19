package com.lambdua.ecloud.client;

import com.lambdua.ecloud.chat_room.ChatRoomMember;
import com.lambdua.ecloud.common.ApiResult;
import com.lambdua.ecloud.download.GetImgRequest;
import com.lambdua.ecloud.friend.SearchUser;
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



    /*--------------------------好友相关-------------------------*/

    /**
     * 微信搜索好友
     * 是好友的情况:
     * {
     * "code": "1000",
     * "message": "搜索联系人成功",
     * "data": {
     * "userName": "wxid_xk161a6quh8b22",
     * "nickName": "二十二",
     * "sex": 1,
     * "bigHead": "http://wx.qlogo.cn/mmhead/ver_1/g42ZMFBe9wHNrcfiaK6fqyO5MWrF8ia8f0tUUuvmR7nkhzyknc0fNQ3aVn4IgJJtfe1lomib6f2o0G3tyJ3wszqChHicmCBAGMsA456mIiag2aicM/0",
     * "smallHead": "http://wx.qlogo.cn/mmhead/ver_1/g42ZMFBe9wHNrcfiaK6fqyO5MWrF8ia8f0tUUuvmR7nkhzyknc0fNQ3aVn4IgJJtfe1lomib6f2o0G3tyJ3wszqChHicmCBAGMsA456mIiag2aicM/132",
     * "v1": "wxid_xk161a6quh8b22",
     * "v2": null
     * }
     * }
     * 不是好友的返回
     * {
     * "code": "1000",
     * "message": "搜索联系人成功",
     * "data": {
     * "userName": "v3_020b3826fd03010000000000138e1730487c89000000501ea9a3dba12f95f6b60a0536a1adb601dac2676713cf04bfe30b35ea9ab26e99ffe3941916ac74e2491991cdac285fc2616835b1a3a54785ce3743fbc7aab52dbc01575611bd3a4edba37d@stranger",
     * "nickName": "xxxx",
     * "sex": 0,
     * "bigHead": "http://wx.qlogo.cn/mmhead/ver_1/t2f5aOpJNSGG1wr3Nt2A0ULomjy6nic5ia8OdnrWaKuia1hfq2F4q3lwiaM9zwT4bpHS6rwcwgAr7s97ez02bJOQIbHLvpKWtp4UxMMgoSFdqmI/0",
     * "smallHead": "http://wx.qlogo.cn/mmhead/ver_1/t2f5aOpJNSGG1wr3Nt2A0ULomjy6nic5ia8OdnrWaKuia1hfq2F4q3lwiaM9zwT4bpHS6rwcwgAr7s97ez02bJOQIbHLvpKWtp4UxMMgoSFdqmI/132",
     * "v1": "v3_020b3826fd03010000000000138e1730487c89000000501ea9a3dba12f95f6b60a0536a1adb601dac2676713cf04bfe30b35ea9ab26e99ffe3941916ac74e2491991cdac285fc2616835b1a3a54785ce3743fbc7aab52dbc01575611bd3a4edba37d@stranger",
     * "v2": "v4_000b708f0b040000010000000000d044a803df1aae4eea84808d72661000000050ded0b020927e3c97896a09d47e6e9e6022ce22106b445c2d7cbefebf22e938d3c207614b5047f93c21c3fd5a52c160573b61c21b0df6ae3965f0dec0f1eedaa526d09d3306340021935d2b93f734102c53b1479cd29844@stranger"
     * }
     * }
     */
    @POST("searchUser")
    // 限制20次/24小时
    @RateLimit(limit = 15, limitTime = 60 * 60 * 24, delay = "10000,20000", type = "searchUser")
    Call<ApiResult<SearchUser>> searchUser(@Body Map<String, Object> request);


    /**
     * 好友添加
     */
    @POST("addUser")
    //24 小时只能加 15-25 位好友，每 2 小时不要超过 8 人,
    @RateLimit(limit = 5, limitTime = 60 * 60 * 2, delay = "20000,60000", type = "addUser")
    Call<ApiResult<Void>> addUser(@Body Map<String, Object> request);

    /**
     * 修改好友备注
     */
    @POST("modifyRemark")
    Call<ApiResult<Void>> modifyRemark(@Body Map<String, Object> request);

    /**
     * 修改个人头像
     */
    @POST("sendHeadImage")
    @RateLimit(limit = 1, limitTime = 60, delay = "1000,2000", type = "sendHeadImage")
    Call<ApiResult<Void>> sendHeadImage(@Body Map<String, Object> request);

    /**
     * 同意添加好友
     */
    @POST("acceptUser")
    @RateLimit(limit = 1, limitTime = 120, delay = "1000,10000", type = "acceptUser")
    Call<ApiResult<Void>> acceptUser(@Body Map<String, Object> request);


    /**
     * 修改我在群里的昵称
     */
    @POST("updateIInChatRoomNickName")
    Call<ApiResult<Void>> updateIInChatRoomNickName(@Body Map<String, Object> request);

    /**
     * 添加群成员为好友
     */
    @POST("addRoomMemberFriend")
    @RateLimit(type = "addUser")
    Call<ApiResult<Void>> addRoomMemberFriend(@Body Map<String, Object> request);

    /*--------------------------好友相关-------------------------*/

    /**
     * 朋友圈点赞
     */
    @POST("snsPraise")
    Call<ApiResult<Void>> snsPraise(@Body Map<String, Object> request);

    /**
     * 取消点赞
     */
    @POST("snsCancelPraise")
    Call<ApiResult<Void>> snsCancelPraise(@Body Map<String, Object> request);

    /**
     * 朋友圈评论
     */
    @POST("snsComment")
    Call<ApiResult<Void>> snsComment(@Body Map<String, Object> request);

    /**
     * 删除朋友圈评论
     */
    @POST("snsCommentDel")
    Call<ApiResult<Void>> snsCommentDel(@Body Map<String, Object> request);




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
