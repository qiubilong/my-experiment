package org.example.web.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.web.dao.entity.User;
import org.example.web.dao.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserMapper userMapper;

    @Transactional
    public void insertUser(User user) {
        userMapper.insert(user);
    }
}
