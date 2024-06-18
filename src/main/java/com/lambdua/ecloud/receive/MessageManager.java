package com.lambdua.ecloud.receive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LiangTao
 * @date 2024年06月18 11:40
 **/
public class MessageManager {
    private final List<MessageProcessor> messageProcessors;

    public MessageManager() {
        this.messageProcessors = new ArrayList<>();
    }

    public MessageManager(List<MessageProcessor> messageProcessors) {
        this.messageProcessors = new ArrayList<>();
        this.messageProcessors.addAll(messageProcessors);
    }

    public void addProcessor(MessageProcessor... processors) {
        messageProcessors.addAll(Arrays.asList(processors));
    }

    public boolean removeProcessor(MessageProcessor processor) {
        return messageProcessors.remove(processor);
    }


    public void process(MessageResult message) {
        for (MessageProcessor processor : messageProcessors) {
            if (processor.supportMessageProcessor(message.getMessageType())) {
                processor.process(message);
            }
        }
    }

}
