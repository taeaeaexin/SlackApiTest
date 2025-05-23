package com.myapp.slackapitest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SlackApiServiceImpl implements SlackApiService{
    @Value("${slack.bot.token}")
    private String slackBotToken;

    @Value("${slack.channel}")
    private String slackChannel;

    private final WebClient webClient;

    // 생성자 DI
    public SlackApiServiceImpl(){
        this.webClient = WebClient.builder()
                .baseUrl("https://slack.com/api")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    // Controller에서 호출
    @Override
    public void sendMessage(String message) {
        sendMessageToChannel(slackChannel, message);
    }

    @Override
    public void sendMessageToChannel(String channel, String message) {
        String jsonPayload = String.format(
                """ 
                {
                    "channel": "%s",
                    "text": "%s"
                }
                """,
                channel, message);
        webClient.post()
                .uri("/chat.postMessage")
                .header("Authorization", "Bearer " + slackBotToken)
                .bodyValue(jsonPayload)
                .retrieve()
                .bodyToMono(String.class) // Decode the body to the given target type
                .doOnSuccess(response -> System.out.println("Slack response: " + response))
                .doOnError(error -> System.err.println("Slack error: " + error.getMessage()))
                .subscribe();
    }
}
