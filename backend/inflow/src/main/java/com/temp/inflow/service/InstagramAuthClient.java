package com.temp.inflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.temp.inflow.dto.InstagramTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;


@Service
public class InstagramAuthClient {
    private final RestTemplate restTemplate;
    @Value("${api.instagram.accessTokenUrl.short}")
    private  String TOKEN_SHORT_URL;
    @Value("${api.instagram.accessTokenUrl.long}")
    private  String TOKEN_LONG_URL;
    @Value("${oAuth.instagram.appId}")
    private  String INSTAGRAM_APP_ID;
    @Value("${oAuth.instagram.appSecret}")
    private  String INSTAGRAM_APP_SECRET;
    @Value("${oAuth.instagram.redirectUri}")
    private  String INSTAGRAM_REDIRECT_URI;
    public InstagramAuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InstagramTokenResponse fetchInstagramShortToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 요청 바디 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", INSTAGRAM_APP_ID);
        body.add("client_secret", INSTAGRAM_APP_SECRET);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", INSTAGRAM_REDIRECT_URI);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response  = restTemplate.exchange(TOKEN_SHORT_URL, HttpMethod.POST, requestEntity, String.class);

        try {
            // JSON 응답 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            String accessToken = rootNode.get("access_token").asText();
            Long userId = rootNode.get("user_id").asLong();

            // permissions는 배열이므로 문자열 배열로 파싱하거나, 쉼표로 join 가능
            ArrayNode permissionsNode = (ArrayNode) rootNode.get("permissions");
            List<String> permissionsList = new ArrayList<>();

            for (JsonNode permission : permissionsNode) {
                permissionsList.add(permission.asText());
            }

            return new InstagramTokenResponse(accessToken, userId, "permissions");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Instagram token response", e);
        }
    }

    public InstagramTokenResponse fetchInstagramLongToken(String token){
        String url = UriComponentsBuilder.fromHttpUrl(TOKEN_LONG_URL)
                .queryParam("grant_type", "ig_exchange_token")
                .queryParam("client_secret", INSTAGRAM_APP_SECRET)
                .queryParam("access_token", token)
                .toUriString();


        ResponseEntity<String> response  = restTemplate.getForEntity(url, String.class);


        try {
            // JSON 응답 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            String accessToken = rootNode.get("access_token").asText();
            long userId = rootNode.get("token_type").asLong();
            String permissions = rootNode.get("expires_in").asText();

            return new InstagramTokenResponse(accessToken, userId, permissions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Instagram token response", e);
        }
    }


}
