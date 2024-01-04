package org.example.web.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author chenxuegui
 * @since 2024/1/4
 */
@Data
@TableName("users_purse")
public class UserPurse {
    @TableId(type = IdType.INPUT)
    private Long uid;

    private Long goldNum;
    private Long nobleGoldNum;
    private double diamondNum;

    private Date updateTime;
    private Date createTime;
    private Integer version;
    private Integer state;
    private Integer diamondState;


}
