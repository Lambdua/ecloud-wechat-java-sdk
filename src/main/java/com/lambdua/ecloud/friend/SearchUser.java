package com.lambdua.ecloud.friend;

import lombok.Data;

/**
 * @author LiangTao
 * @date 2024年06月19 15:59
 **/
@Data
public class SearchUser {
    /**
     * 搜索好友经常搭配添加好友接口使用，好友同意添加成功后会有回调，用户可根据本接口返回的v1和添加成功后回调返回的v1及wcid对应起来
     */
    private String userName;

    private String nickName;

    private Integer sex;

    private String bigHead;

    private String smallHead;

    /**
     * 添加好友凭证1
     * （如果是好友 会返回微信id）
     * 唯一不变值
     * 好友添加成功后回调会返会此值
     */
    private String v1;

    private String v2;
}
