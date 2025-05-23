package com.myapp.slackapitest.service;

public interface SlackApiService {
    void sendMessage(String message);
    void sendMessageToChannel(String channel, String message);
}
