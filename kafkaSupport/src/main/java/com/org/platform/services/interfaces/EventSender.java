package com.org.platform.services.interfaces;

public interface EventSender {

    void sendMessage(String topic, Object event);

}
