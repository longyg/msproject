package com.longyg.account.clients;

import com.longyg.account.model.Score;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ScoreDiscoveryClient {
    private static final Logger logger = LoggerFactory.getLogger(ScoreDiscoveryClient.class);
    @Autowired
    private DiscoveryClient discoveryClient;

    public Score getScore(String accountId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("score-service");

        if (instances.size() == 0) return null;

        String serviceUri = String.format("%s/v1/score/%s",
                instances.get(0).getUri().toString(),
                accountId);

        logger.info("serviceUri: " + serviceUri);
        ResponseEntity<Score> restExchange = restTemplate.exchange(
                serviceUri,
                HttpMethod.GET,
                null, Score.class, accountId);
        return restExchange.getBody();
    }
}
