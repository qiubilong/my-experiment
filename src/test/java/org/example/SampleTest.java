package org.example;

import org.example.web.dao.entity.User;
import org.example.web.dao.mapper.UserMapper;
import org.example.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

    @Test
    public void testTransactional() {
        User user = new User();
        user.setName("Transactional");
        user.setAge(19);
        userService.insertUser(user);
    }

}