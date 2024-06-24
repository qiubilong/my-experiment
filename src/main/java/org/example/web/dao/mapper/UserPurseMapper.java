package org.example.web.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.web.dao.entity.UserPurse;

/**
 * @author chenxuegui
 * @since 2024/1/4
 */
public interface UserPurseMapper extends BaseMapper<UserPurse> {

    @Update("UPDATE `users_purse` SET `gold_num`=`gold_num`-#{goldCost},`update_time`=now() WHERE `uid`=#{uid} and state = 1  AND `gold_num` - #{goldCost} >= 0")
    int decrGoldCost(@Param("uid") Long uid, @Param("goldCost") Long goldCost);


    @Update("UPDATE `users_purse` SET `gold_num`=#{goldCost},`update_time`=now() WHERE `uid`=#{uid} and state = 1")
    int updateGoldCost(@Param("uid") Long uid, @Param("goldCost") Long goldCost);
}
