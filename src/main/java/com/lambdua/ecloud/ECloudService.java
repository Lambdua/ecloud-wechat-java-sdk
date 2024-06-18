package com.lambdua.ecloud;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdua.ecloud.chat_room.ChatRoomMember;
import com.lambdua.ecloud.client.AuthenticationInterceptor;
import com.lambdua.ecloud.client.ECloudClient;
import com.lambdua.ecloud.client.RateLimitInterceptor;
import com.lambdua.ecloud.common.ApiResult;
import com.lambdua.ecloud.download.GetImgRequest;
import com.lambdua.ecloud.login.Address;
import com.lambdua.ecloud.login.Contact;
import com.lambdua.ecloud.login.ContactRequest;
import com.lambdua.ecloud.send.SendRequest;
import com.lambdua.ecloud.send.SendResult;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
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
    // private String baseUrl = "http://59.36.146.193:9899";
    private String baseUrl;
    private ECloudClient client;
    private final ExecutorService executorService;


    public ECloudService(String token,String baseUrl) {
        this.token = token;
        this.baseUrl=baseUrl;
        ObjectMapper mapper = defaultObjectMapper();
        OkHttpClient client = defaultClient(token, Duration.ofSeconds(10));
        Retrofit retrofit = defaultRetrofit(client, mapper, baseUrl);
        this.client = retrofit.create(ECloudClient.class);
        this.executorService = client.dispatcher().executorService();
    }

    /*--------------------------login相关-------------------------*/

    public Address getAddress(String wId) {
        //1. 初始化
        execute(client.initAddress(Map.of("wId", wId)));
        //2. 获取列表
        return execute(client.getAddress(Map.of("wId", wId))).getData();
    }


    public List<Contact> getContactList(ContactRequest contactRequest) {
        return execute(client.getContact(contactRequest)).getData();
    }

    /*--------------------------login相关-------------------------*/


    /*--------------------------发送消息相关-------------------------*/

    public SendResult sendTextMsg(SendRequest sendRequest) {
        return execute(client.sendText(sendRequest)).getData();
    }

    /*--------------------------发送消息相关-------------------------*/



    /*--------------------------下载相关-------------------------*/

    /**
     * 返回下载链接
     */
    public String downloadMsgImage(GetImgRequest imgRequest) {
        ApiResult<Map<String, Object>> execute = execute(client.getMsgImg(imgRequest));
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
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
            ApiResult<T> body = call.execute().body();
            if (!body.isSuccess()) {
                throw new RuntimeException(body.getMessage());
            }
            return body;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}