package org.example.web.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("moment_comment")
public class MomentComment {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("dynamic_id")
    private Long dynamicId;
    @TableField("user_id")
    private Long userId;
    @TableField("content")
    private String content;
    @TableField("comment_time")
    private Date commentTime;
    @TableField("status")
    private Integer status;
    @TableField("update_time")
    private Date updateTime;
    @TableField("parent_comment_id")
    private Long parentCommentId;

    @TableField("praise_num")
    private Long praiseNum;

    @TableField("reply_num")
    private Long replyNum;
}