package com.myapp.slackapitest.controller;

import com.myapp.slackapitest.service.SlackApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SlackApiController {
    private final SlackApiService slackApiService;

    @GetMapping("/notify")
    public String sendSlackSendMessage() {
        slackApiService.sendMessage("Hello, Slack First Message");
        return "Slack에 Message를 보냈습니다.";
    }
}
