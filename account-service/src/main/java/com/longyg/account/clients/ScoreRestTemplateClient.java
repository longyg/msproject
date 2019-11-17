package com.longyg.account.clients;

import com.longyg.account.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScoreRestTemplateClient {
    private static final String SCORE_SERVICE_URI = "http://score-service/v1/score/{accountId}";

    @Autowired
    private RestTemplate restTemplate;

    public Score getScore(String accountId) {
        ResponseEntity<Score> restExchange = restTemplate.exchange(
            SCORE_SERVICE_URI,
            HttpMethod.GET,
            null, Score.class, accountId
        );
        return restExchange.getBody();
    }
}