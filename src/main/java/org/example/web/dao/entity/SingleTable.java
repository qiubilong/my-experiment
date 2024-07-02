package org.example.web.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@TableName("single_table")
public class SingleTable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("普通索引key1")
    @TableField("key1")
    private String key1;

    @ApiModelProperty("唯一索引key2")
    @TableField("key2")
    private Integer key2;

    @ApiModelProperty("普通索引key3")
    @TableField("key3")
    private String key3;

    @TableField("key_part1")
    private String keyPart1;

    @TableField("key_part2")
    private String keyPart2;

    @TableField("key_part3")
    private String keyPart3;

    @TableField("common_field")
    private String commonField;
}