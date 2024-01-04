package org.example.web.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.web.dao.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    @Update({"update user set age=#{vo.age},update_time=now() where email=#{vo.email}"})
    int update(@Param("vo") User user);


    @Insert({"<script>insert into user " +
            "(name, age, email) " +
            "values " +
            "<foreach collection='list' item='vo' separator=','> " +
            "(#{vo.name}, #{vo.age}, #{vo.email})" +
            "</foreach></script>"})
    int batchInsert(@Param("list") List<User> list);




    @Insert({"<script>insert into user " +
            "(name, age, email) " +
            "values " +
            "<foreach collection='list' item='vo' separator=','> " +
            "(#{vo.name}, #{vo.age}, #{vo.email})" +
            "</foreach>" +
            "on duplicate key update " +
            "age = VALUES(age)," +
            "update_time = now()" +
            "</script> " })
    int batchInsertOnUpdate(@Param("list") List<User> list);

}