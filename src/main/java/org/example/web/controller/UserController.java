package org.example.web.controller;

import lombok.RequiredArgsConstructor;
import org.example.tuling.jvm.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author chenxuegui
 * @since 2024/1/12
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    @RequestMapping("/processUserData")
    public String processUserData() throws InterruptedException {
        ArrayList<User> users = queryUsers();

        for (User user: users) {
            //TODO 业务处理
            //System.out.println("user:" + user.toString());
        }
        Thread.sleep(20);

        return "end";
    }

    /**
     * 模拟批量查询用户场景
     * @return
     */
    private ArrayList<User> queryUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (long i = 0; i < 5000; i++) {
            users.add(new User(i,"zhuge"));
        }
        return users;
    }
}
