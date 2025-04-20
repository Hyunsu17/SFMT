package com.temp.inflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temp.inflow.dto.InstagramProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class InstagramApiClient {

    private final RestTemplate restTemplate;
    public InstagramApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InstagramProfileResponse fetchInstagramProfile(String igUserId, String accessToken){
        String url = UriComponentsBuilder.fromHttpUrl("https://graph.instagram.com/v19.0/" + igUserId)
                .queryParam("fields", "username,name,media_count,followers_count")
                .queryParam("access_token", accessToken)
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return InstagramProfileResponse.builder()
                    .username(rootNode.get("username").asText())
                    .name(rootNode.get("name").asText())
                    .mediaCount(rootNode.get("media_count").asInt())
                    .followersCount(rootNode.get("followers_count").asInt())
                    .build();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
