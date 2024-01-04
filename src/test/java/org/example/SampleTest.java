package org.example;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.web.dao.entity.User;
import org.example.web.dao.mapper.UserMapper;
import org.example.web.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private  SqlSessionFactory sqlSessionFactory;

    private static int begin = 0;

    public static List<User> generateUserList() {
        Date now = new Date();
        int max = 10_0000;
        List<User> list = new ArrayList<>(max);
        for (int i = begin; i < max; i++) {
            User user = new User();
            user.setAge(i%30+ 3000);
            user.setName("name"+i);
            user.setEmail((i+1000000)+"@qq.com");
            if(i%2 ==0){
                user.setEmail((i+3000000)+"@qq.com");
            }
            user.setCreateTime(now);
            user.setUpdateTime(now);
            list.add(user);
        }
        return list;
    }

    @Test
    public List<User> testMessageTime(){
        List<User> list = new ArrayList<>(215);

        String ms = "1700496448093\n" +
                "1700508842866\n" +
                "1700508844712\n" +
                "1700508862694\n" +
                "1700508863234\n" +
                "1700508863539\n" +
                "1700508863729\n" +
                "1700508863894\n" +
                "1700508865051\n" +
                "1700508866204\n" +
                "1700508870664\n" +
                "1700521112213\n";

        String strDateFormat = "yyyy-MM-dd-HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String[] split = ms.split("\n");
        int i = begin;
        for (String id : split) {
            System.out.println(sdf.format(new Date(Long.valueOf(id))));
            User user = new User();
            user.setAge(i%30+ 3000);
            user.setName("name"+i);
            user.setEmail((i+1000000)+"@qq.com");
            if(i%2 ==0){
                user.setEmail((i+3000000)+"@qq.com");
            }
            user.setCreateTime(new Date(Long.valueOf(id)));
            i++;
            list.add(user);
        }
        return list;
    }



    @Test
    public void testBatchInsertValues() {
        List<User> list = generateUserList();
        List<List<User>> lists = Lists.partition(list, 1000);
        long start = System.currentTimeMillis();
        lists.forEach(l->{
            userMapper.batchInsert(l);
        });
        long cost = System.currentTimeMillis() - start;
        log.info("testBatchInsertValues cost={}",cost);
    }

    @Test
    public void testBatchInsertInSession(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);

        List<User> list = testMessageTime();
        List<List<User>> lists = Lists.partition(list, 1000);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        long start = System.currentTimeMillis();
        for (List<User> users : lists) {
            for (User user : users) {
                mapper.insert(user);
            }
            sqlSession.commit();
            sqlSession.clearCache();
        }
        log.info("testBatchInsertInSession cost={}",( System.currentTimeMillis() - start));
        sqlSession.close();
    }

    @Test
    public void testBatchInsertOne() {
        List<User> list = generateUserList();
        long start = System.currentTimeMillis();
        list.forEach(user->{
            userMapper.insert(user);
        });
        long cost = System.currentTimeMillis() - start;
        log.info("testBatchInsertOne cost={}",cost);
    }

    @Test
    public void testBatchUpdateValues() {
        List<User> list = generateUserList();
        List<List<User>> lists = Lists.partition(list, 1000);
        long start = System.currentTimeMillis();
        lists.forEach(l->{
            userMapper.batchInsertOnUpdate(l);
        });
        long cost = System.currentTimeMillis() - start;
        log.info("testBatchUpdateValues cost={}",cost);
    }

    @Test
    public void testBatchUpdateInSession(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);

        List<User> list = generateUserList();
        List<List<User>> lists = Lists.partition(list, 1000);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        long start = System.currentTimeMillis();
        for (List<User> users : lists) {
            for (User user : users) {
                mapper.update(user);
            }
            sqlSession.commit();
            sqlSession.clearCache();
        }
        log.info("testBatchUpdateInSession cost={}",( System.currentTimeMillis() - start));
        sqlSession.close();
    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testError() {
       try {
           String str = "121323#";
           String[] split = str.split("#");
           String s = split[0];
           String s1 = split[1];
       }catch (Exception e){
           String str = ExceptionUtils.getStackTrace(e);
           str = str.replaceAll("\r\n\t","#");
           log.error("test error={}",str);
       }
    }

}