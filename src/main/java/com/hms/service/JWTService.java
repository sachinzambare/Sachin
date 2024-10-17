package com.hms.service;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private  int expiryTime;



    @PostConstruct
    public void postConstruct(){

    }

}
