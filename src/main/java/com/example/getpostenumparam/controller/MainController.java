package com.example.getpostenumparam.controller;

import com.example.getpostenumparam.model.User;
import com.example.getpostenumparam.type.GenderEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhixiao.mzx
 * @date 2018/10/27
 */
@RestController
public class MainController {
    @RequestMapping(value = "/get_or_post_enum", method = {RequestMethod.GET, RequestMethod.POST})
    public GenderEnum getOrPostEnum(GenderEnum gender) {
        return gender;
    }

    @RequestMapping(value = "/get_or_post_object_contain_enum", method = {RequestMethod.GET, RequestMethod.POST})
    public User getOrPostObjectContainEnum(User user) {
        return user;
    }

    @PostMapping("/post_json")
    public User postJson(@RequestBody User user) {
        return user;
    }
}
