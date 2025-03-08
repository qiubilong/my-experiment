package org.example.web.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenxuegui
 * @since 2025/3/6
 */
@TableName("order")
@Data
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("uid")
    private Long uid;

    @TableField("order_id")
    private Long orderId;
}
