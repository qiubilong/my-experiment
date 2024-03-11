package org.example.web.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.web.dao.entity.MomentComment;

import java.util.List;

/**
 * @author Ashin
 */
public interface MomentCommentMapper extends BaseMapper<MomentComment> {

    /**
     * 根据动态id以及用户id查询
     * @param dynamicId
     * @param userId
     * @return
     */
    @Select("select id, dynamic_id, user_id, content, comment_time, status, update_time from moment_comment where user_id = #{userId} and dynamic_id = #{dynamicId} and status = 1")
    MomentComment selectMomentCommentByDynamicIdAndUserId(@Param("dynamicId") Long dynamicId, @Param("userId")Long userId);

    /**
     * 根据评论id查询
     * @param commentId
     * @return
     */
    @Select("select id, dynamic_id, user_id, content, comment_time, status, update_time from moment_comment where id = #{commentId} and status = 1")
    MomentComment selectMomentCommentByCommentId(@Param("commentId")Long commentId);

    /**
     * 根据动态ID查询评论
     * @param page
     * @param dynamicId
     * @return
     */
    @Select("select id, dynamic_id, user_id, content, comment_time, status, update_time from moment_comment where dynamic_id = #{dynamicId} and status = 1 and parent_comment_id is null order by comment_time asc")
    List<MomentComment> selectMomentCommonByDynamicId(Page<MomentComment> page , @Param("dynamicId") Long dynamicId);

    /**
     * 评论-时间排序
     */
    @Select("<script>" +
            "select id, dynamic_id, user_id, content, comment_time, praise_num, reply_num from moment_comment " +
            "where dynamic_id = #{dynamicId} " +
            "<if test='maxId > 0'> " +
            " and id <![CDATA[<]]> #{maxId} " +
            "</if> " +
            "and status = 1 and parent_comment_id is null " +
            " order by id desc " +
            " limit #{pageSize}" +
            "</script>")
    List<MomentComment> selectCommentsByTime(@Param("dynamicId") Long dynamicId,@Param("maxId") Long maxId,@Param("pageSize") Integer pageSize);

    /**
     * 评论-点赞数排序
     */
    @Select("<script>" +
            "select id, dynamic_id, user_id, content, comment_time, praise_num, reply_num from moment_comment " +
            "where dynamic_id = #{dynamicId} " +
            "<if test='maxId > 0'> " +
            " and id <![CDATA[<]]> #{maxId} " +
            "</if> " +
            "and status = 1 and parent_comment_id is null " +
            " order by praise_num,id desc " +
            " limit #{pageSize}" +
            "</script>")
    List<MomentComment> selectCommentsByHot(@Param("dynamicId") Long dynamicId,@Param("maxId") Long maxId,@Param("pageSize") Integer pageSize);


    /**
     * 根据评论父id查询回复
     * @param commentId
     * @return
     */
    @Select("select id, dynamic_id, user_id, parent_comment_id, content, comment_time, status, update_time from moment_comment where parent_comment_id = #{commentId} and status = 1")
    List<MomentComment> selectMomentCommentReplyByCommentId(@Param("commentId") Long commentId);



    /**
     * 评论列表中，批量查询前几条回复
     */
    @Select("<script>" +
            "select * " +
            "from (" +
            "select id, dynamic_id, parent_comment_id, user_id, content, comment_time, praise_num, row_number() over( partition by parent_comment_id order by id asc ) as rn\n" +
            "from moment_comment where " +
            " and parent_comment_id in " +
            "<foreach item='item' collection='pids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            " and status=1" +
            ") as s " +
            "where rn <![CDATA[<=]]> 2" +
            "</script>")
    List<MomentComment> selectCommentReplysByPid(@Param("pids") List<Long> pids);


    /**
     * 根据评论ID查询回复评论
     */
    @Select("<script>" +
            "select id, dynamic_id, user_id, content, comment_time from moment_comment " +
            " where parent_comment_id = #{commentId} " +
            "<if test='maxId > 0'> " +
            " and id <![CDATA[<]]> #{maxId} " +
            "</if> " +
            " and status = 1 " +
            " order by id asc " +
            " limit #{pageSize}" +
            "</script>")
    List<MomentComment> selectRepliesByCommentId(@Param("commentId") Long commentId, @Param("maxId") Long maxId,  @Param("pageSize") Integer pageSize);

    /**
     * 根据动态id更新所有状态无效
     * @param dynamicId
     */
    @Update("update moment_comment set status = 0 where dynamic_id = #{dynamicId}")
    void updateStatueByDynamicId(@Param("dynamicId") Long dynamicId);


    /**
     * 变更-评论-点赞数
     */
    @Update("update moment_comment set praise_num = praise_num + #{changeNum} where id = #{commentId}")
    int updateCommentPraiseNum(@Param("commentId") Long commentId,@Param("changeNum") Integer changeNum);

    /**
     * 变更-评论-回复数
     */
    @Update("update moment_comment set reply_num = reply_num + #{changeNum} where id = #{commentId}")
    int updateCommentReplyNum(@Param("commentId") Long commentId,@Param("changeNum") Integer changeNum);


    /**
     * 根据动态id查询所有评论
     * @param dynamicId
     * @return
     */
    @Select("select id, dynamic_id, user_id, parent_comment_id, content, comment_time, status, update_time from moment_comment where dynamic_id = #{dynamicId} and status = 1")
    List<MomentComment> selectCommentByDynamicId(@Param("dynamicId") Long dynamicId);

}
