package org.example.web.controller;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import org.example.web.dao.entity.UserPurse;
import org.example.web.dao.mapper.UserPurseMapper;
import org.example.web.service.UserPurseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenxuegui
 * @since 2024/1/12
 */
@RestController
@RequestMapping("/userPurse")
@RequiredArgsConstructor
public class UserController {

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
}
