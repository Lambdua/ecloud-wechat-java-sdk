package com.lambdua.ecloud;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdua.ecloud.chat_room.ChatRoomMember;
import com.lambdua.ecloud.client.AuthenticationInterceptor;
import com.lambdua.ecloud.client.ECloudClient;
import com.lambdua.ecloud.client.RateLimitInterceptor;
import com.lambdua.ecloud.common.ApiServerException;
import com.lambdua.ecloud.common.ApiResult;
import com.lambdua.ecloud.common.ApiExecuteException;
import com.lambdua.ecloud.download.GetImgRequest;
import com.lambdua.ecloud.login.Address;
import com.lambdua.ecloud.login.Contact;
import com.lambdua.ecloud.login.WeChat;
import com.lambdua.ecloud.receive.DetailReceiveData;
import com.lambdua.ecloud.receive.MessageResult;
import com.lambdua.ecloud.receive.MessageType;
import com.lambdua.ecloud.send.SendRequest;
import com.lambdua.ecloud.send.SendResult;
import lombok.NonNull;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author LiangTao
 * @date 2024年06月11 10:45
 **/
public class ECloudService {
    private String token;
    private String baseUrl;
    private ECloudClient client;
    private final ExecutorService executorService;

    /**
     * 当前在管理平台上在线的微信号和其对应的实例id
     * key: wcId,value:wId
     */
    public static final Map<String, String> ONLINE_WX_MAP = new HashMap<>();

    /**
     * 根据value: wId 获取key: wcId
     */
    @NonNull
    public static String getWcIdByWId(String wId) {
        return ONLINE_WX_MAP.entrySet().stream()
                .filter(entry -> entry.getValue().equals(wId))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }


    public ECloudService(String token, String baseUrl) {
        this.token = token;
        this.baseUrl = baseUrl;
        ObjectMapper mapper = defaultObjectMapper();
        OkHttpClient client = defaultClient(token, Duration.ofSeconds(10));
        Retrofit retrofit = defaultRetrofit(client, mapper, baseUrl);
        this.client = retrofit.create(ECloudClient.class);
        this.executorService = client.dispatcher().executorService();
        initialOnlineWxMap();
    }


    private void initialOnlineWxMap() {
        List<Map<String, String>> maps = queryLoginWx();
        for (Map<String, String> map : maps) {
            ONLINE_WX_MAP.put(map.get("wcId"), map.get("wId"));
        }
    }


    /**
     * 查询当前ecloud平台在线的wechat账号
     */
    public List<Map<String, String>> queryLoginWx() {
        ApiResult<List<Map<String, String>>> result = execute(client.queryLoginWx());
        statusCheck(result, "查询当前ecloud平台在线的wechat账号失败");
        return result.getData();
    }


    /*--------------------------login相关-------------------------*/

    public Address getAddress(String wId) {
        //1. 初始化
        ApiResult<Void> initialStatus = execute(client.initAddress(Map.of("wId", wId)));
        statusCheck(initialStatus, "初始化通讯录列表失败");
        //2. 获取列表
        ApiResult<Address> result = execute(client.getAddress(Map.of("wId", wId)));
        statusCheck(result, "获取通讯录列表失败");
        return result.getData();
    }

    /**
     * 获取当前登录实例微信号的详细信息
     */
    public WeChat getIPadLoginInfo(String wId) {
        ApiResult<WeChat> result = execute(client.getIPadLoginInfo(Map.of("wId", wId)));
        statusCheck(result, "获取当前登录实例微信号的详细信息失败");
        return result.getData();
    }


    /**
     * @param wId  登录实例id
     * @param wcId 好友微信id/群id,多个好友/群 以","分隔每次最多支持20个微信/群号,记得本接口随机间隔300ms-1500ms，频繁调用容易导致掉线
     */
    public List<Contact> getContactList(String wId, String wcId) {
        ApiResult<List<Contact>> result = execute(client.getContact(Map.of("wId", wId, "wcId", wcId)));
        statusCheck(result, "获取通讯录的联系人详情失败");
        return result.getData();
    }

    /*--------------------------login相关-------------------------*/




    /*--------------------------发送消息相关-------------------------*/

    public MessageResult sendTextMsg(SendRequest request) {
        ApiResult<SendResult> apiResult = execute(client.sendText(request));
        statusCheck(apiResult, "发送文本消息失败");
        String currentWcId = getWcIdByWId(request.wId());
        SendResult result = apiResult.getData();
        MessageResult msg = new MessageResult();
        DetailReceiveData detail = new DetailReceiveData();
        msg.setWcId(currentWcId);
        if (request.wcId().contains("@chatroom")) {
            msg.setMessageType(MessageType.GROUP_TEXT);
            //群聊消息的,接收者是群id和自己
            detail.setFromGroup(request.wcId());
            detail.setToUser(currentWcId);
        } else {
            msg.setMessageType(MessageType.PRIVATE_TEXT);
            detail.setToUser(request.wcId());
        }
        msg.setData(detail);
        detail.setSelf(true);
        detail.setContent(request.content());
        //发送者就是当前微信号
        detail.setFromUser(currentWcId);
        detail.setMsgId(result.msgId());
        detail.setNewMsgId(result.newMsgId());
        detail.setTimestamp(result.createTime() == 0L ? System.currentTimeMillis() : result.createTime());
        detail.setWId(request.wId());
        detail.setType(result.type());
        detail.setUrl(request.path());
        if (request.at() != null) {
            String[] at = request.at().split(",");
            detail.setAtList(List.of(at));
        }
        return msg;
    }

    public SendResult setImageMsg(String wId, String wcId, String url) {
        ApiResult<SendResult> apiResult = execute(client.sendImg(Map.of("wId", wId, "wcId", wcId, "content", url)));
        statusCheck(apiResult, "发送图片消息失败");
        return apiResult.getData();
        // MessageResult msg = new MessageResult();
        // DetailReceiveData detail = new DetailReceiveData();
        // String currentWcId = getWcIdByWId(wId);
        // msg.setWcId(currentWcId);
        // if (wcId.contains("@chatroom")) {
        //     msg.setMessageType(MessageType.GROUP_IMAGE);
        //     //群聊消息的,接收者是群id和自己
        //     detail.setFromGroup(wcId);
        //     detail.setToUser(currentWcId);
        // } else {
        //     msg.setMessageType(MessageType.PRIVATE_IMAGE);
        //     detail.setToUser(wcId);
        // }
        // msg.setData(detail);
        // detail.setSelf(true);
        // detail.setContent(url);
        // //根据url base64
        // // detail.setImg();
        // detail.setFromUser(currentWcId);
        // detail.setMsgId(result.msgId());
        // detail.setNewMsgId(result.newMsgId());
        // detail.setTimestamp(result.createTime() == 0L ? System.currentTimeMillis() : result.createTime());
        // detail.setWId(wId);
        // detail.setType(result.type());
        // detail.setUrl(url);
        // return msg;
    }


    /**
     * @param url    语音url （silk/amr 格式,可以下载消息中的语音返回silk格式）
     * @param length 语音时长（回调消息xml数据中的voicelength字段）
     * @return com.lambdua.ecloud.send.SendResult
     * @author liangtao
     * @date 2024/6/18
     **/
    public SendResult sendVoiceMsg(String wId, String wcId, String url, Integer length) {
        ApiResult<SendResult> apiResult = execute(client.sendVoice(Map.of(
                "wId", wId,
                "wcId", wcId,
                "content", url,
                "length", length
        )));
        statusCheck(apiResult, "发送语音消息失败");
        return apiResult.getData();
    }


    public SendResult sendUrlMsg(String wId, String wcId, String title, String description, String url, String thumbUrl) {
        ApiResult<SendResult> apiResult = execute(client.sendUrl(Map.of(
                "wId", wId,
                "wcId", wcId,
                "title", title,
                "description", description,
                "thumbUrl", thumbUrl,
                "url", url
        )));
        statusCheck(apiResult, "发送链接消息失败");
        return apiResult.getData();
    }


    /**
     * @param nameCardId 要发送的名片id
     */
    public SendResult sendNameCard(String wId, String wcId, String nameCardId) {
        ApiResult<SendResult> apiResult = execute(client.sendNameCard(Map.of(
                "wId", wId,
                "wcId", wcId,
                "nameCardId", nameCardId
        )));
        statusCheck(apiResult, "发送名片消息失败");
        return apiResult.getData();
    }

    /**
     * 发送表情包
     */
    public SendResult sendEmoji(String wId, String wcId, String imageMd5, String imgSize) {
        ApiResult<SendResult> apiResult = execute(client.sendEmoji(Map.of(
                "wId", wId,
                "wcId", wcId,
                "imageMd5", imageMd5,
                "imgSize", imgSize
        )));
        statusCheck(apiResult, "发送表情包消息失败");
        return apiResult.getData();
    }

    /**
     * 撤回msg
     */
    public void revokeMsg(String wId, String wcId, Long msgId, Long newMsgId, Long createTime) {
        ApiResult<Void> apiResult = execute(client.revokeMsg(Map.of(
                "wId", wId,
                "wcId", wcId,
                "msgId", msgId,
                "newMsgId", newMsgId,
                "createTime", createTime
        )));
        statusCheck(apiResult, "撤回消息失败");
    }
    /*--------------------------发送消息相关-------------------------*/





    /*--------------------------下载相关-------------------------*/

    /**
     * 返回下载链接
     */
    public String downloadMsgImage(GetImgRequest imgRequest) {
        ApiResult<Map<String, Object>> execute = execute(client.getMsgImg(imgRequest));
        statusCheck(execute, "下载图片失败");
        return (String) execute.getData().get("url");
    }

    /**
     * 下载语音
     */
    public String downloadMsgVoice(String wId, String wcId, String msgId, Integer length, Integer bufId) {
        ApiResult<Map<String, Object>> execute = execute(client.getMsgVoice(Map.of(
                "wId", wId,
                "fromUser", wcId,
                "msgId", msgId,
                "length", length,
                "bufId", bufId
        )));
        statusCheck(execute, "下载语音失败");
        return (String) execute.getData().get("url");
    }

    /**
     * 下载表情包
     */
    public String downloadEmoji(String wId, String msgId, String content) {
        ApiResult<Map<String, Object>> execute = execute(client.getMsgEmoji(Map.of( "wId", wId, "msgId", msgId, "content", content )));
        statusCheck(execute, "下载表情包失败");
        return (String) execute.getData().get("url");
    }




    /*--------------------------下载相关-------------------------*/


    /*--------------------------群聊相关-------------------------*/
    public List<ChatRoomMember> getChatRoomMemberList(String wId, String chatRoomId) {
        return execute(client.getChatRoomMemberList(Map.of("wId", wId, "chatRoomId", chatRoomId))).getData();
    }

    /**
     * @param wcId 群成员标识 PS: 暂不支持多个群成员查询，可间隔调用获取
     */
    public ChatRoomMember getDetailChatRoomMember(String wId, String chatRoomId, String wcId) {
        List<ChatRoomMember> roomMembers = execute(client.getDetailChatRoomMember(Map.of(
                "wId", wId,
                "chatRoomId", chatRoomId,
                "userList", wcId
        ))).getData();
        return roomMembers == null ? null : roomMembers.getFirst();
    }

    /**
     * @param wId 登录实例标识
     * @param url 原始 url，好友发送的入群邀请卡片信息链接(回调中取)
     * @return 成功失败
     */
    public boolean acceptInvitation(String wId, String url) {
        return execute(client.acceptInvitation(Map.of(
                "wId", wId,
                "url", url
        ))).isSuccess();
    }

    /**
     * {
     * "wId": "349be9b5-8734-45ce-811d-4e10ca568c67",
     * "chatRoomId": "24343869723@chatroom",
     * "flag":0
     * }
     *
     * @param flag 3：保存到群通讯录  2：从通讯录移除群
     */
    public void showInAddressBook(String wId, String chatRoomId, int flag) {
        execute(client.showInAddressBook(Map.of(
                "wId", wId,
                "chatRoomId", chatRoomId,
                "flag", flag
        )));
    }

    /*--------------------------群聊相关-------------------------*/


    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    public static OkHttpClient defaultClient(String token, Duration timeout) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .addInterceptor(new RateLimitInterceptor(token))
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .readTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

    public static Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();
    }

    private <T> ApiResult<T> execute(Call<ApiResult<T>> call) {
        try {
            return call.execute().body();
        } catch (Exception e) {
            throw new ApiServerException("ecloud 接口调用异常", e);
        }
    }

    private <T> void statusCheck(ApiResult<T> result, String businessMsg) {
        if (!result.isSuccess()) {
            throw new ApiExecuteException(businessMsg + ":+" + result.getMessage());
        }
    }

}
