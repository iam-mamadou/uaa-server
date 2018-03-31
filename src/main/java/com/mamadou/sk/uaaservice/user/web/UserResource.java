package com.mamadou.sk.uaaservice.user.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @RequestMapping(name = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        return "test";
    }

}
