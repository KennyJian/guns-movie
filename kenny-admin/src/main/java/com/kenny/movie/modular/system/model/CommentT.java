package com.kenny.movie.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kenny
 * @since 2019-02-05
 */
@TableName("comment_comment_t")
public class CommentT extends Model<CommentT> {

    private static final long serialVersionUID = 1L;

    @TableId("UUID")
    private String uuid;
    @TableField("film_id")
    private Integer filmId;
    @TableField("user_id")
    private Integer userId;
    private String comment;
    @TableField("create_time")
    private Date createTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "CommentT{" +
        "uuid=" + uuid +
        ", filmId=" + filmId +
        ", userId=" + userId +
        ", comment=" + comment +
        ", createTime=" + createTime +
        "}";
    }
}
