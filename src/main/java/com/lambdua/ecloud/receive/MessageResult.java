package com.lambdua.ecloud.receive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @author LiangTao
 * @date 2024年06月11 15:16
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class MessageResult {
    private String wcId;
    private String account;
    private MessageType messageType;
    private DetailReceiveData data;
}
