package com.hyd.fx.springboot;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getUserName() {
        return "User from Spring Boot service object.";
    }
}
