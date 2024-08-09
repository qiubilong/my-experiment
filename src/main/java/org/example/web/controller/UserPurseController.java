package org.example.web.controller;

import lombok.RequiredArgsConstructor;
import org.example.tuling.jvm.User;
import org.example.web.dao.entity.UserPurse;
import org.example.web.service.UserPurseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author chenxuegui
 * @since 2024/1/12
 */
@RestController
@RequestMapping("/userPurse")
@RequiredArgsConstructor
public class UserPurseController {

    private final UserPurseService userPurseService;

    @GetMapping("/getUserPurseInfo")
    public UserPurse getUserPurseInfo(@RequestParam Long uid){
        return userPurseService.getUserPurseInfo(uid);
    }

    @GetMapping("/getUserPurseInfoFromCacheRedis")
    public UserPurse getUserPurseInfoFromCache(@RequestParam Long uid){
        return userPurseService.getUserPurseInfoFromCacheRedis(uid);
    }

    @GetMapping("/getUserPurseInfoFromCacheLocal")
    public UserPurse getUserPurseInfoFromCacheLocal(@RequestParam Long uid){
        return UserPurse.EMPTY;
    }

    @GetMapping("/incrUserPurseGoldNum")
    public Long incrUserPurseGoldNum(@RequestParam Long uid,@RequestParam Long num){
        return userPurseService.incrUserPurseGoldNum(uid,num);
    }


    @RequestMapping("/processUserData")
    public String processUserData() {
        ArrayList<User> users = queryUsers();

        for (User user: users) {
            //TODO 业务处理
            System.out.println("user:" + user.toString());
        }
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
