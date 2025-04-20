package com.temp.inflow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temp.inflow.service.InstagramAuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class InstagramAuthController {

    private final InstagramAuthService instagramAuthService;



    InstagramAuthController(InstagramAuthService instagramAuthService){
        this.instagramAuthService = instagramAuthService;
    } 
//    private final JwtUtil jwtUtil;

    @GetMapping("/instagram/callback")
    public ResponseEntity<?> instagramCallback(@RequestParam("code") String code) {
        String token = instagramAuthService.processInstagramAuth(code);

        // JWT 생성
        //String jwtToken = jwtUtil.generateToken(facebookUser.getEmail());
        String jwtToken = "";

        // 클라이언트에게 JWT 반환
        return ResponseEntity.ok(Map.of("token", jwtToken));
    }

}
