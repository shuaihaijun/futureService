package com.future.entity.user;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuUserIdentity {

    public static final String USER_ID = "user_id";
    public static final String IDENTITY = "identity";
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户角色（0 普通用户，1 代理，2 信号源，3 社区管理员，4 系统管理员）
     */
    private Integer identity;

    /**
     * 角色等级
     */
    private Integer identityLevel;

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 创建时间
     * @return create_date 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 用户ID
     * @return user_id 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 用户角色（0 普通用户，1 代理，2 信号源，3 社区管理员，4 系统管理员）
     * @return identity 用户角色（0 普通用户，1 代理，2 信号源，3 社区管理员，4 系统管理员）
     */
    public Integer getIdentity() {
        return identity;
    }

    /**
     * 用户角色（0 普通用户，1 代理，2 信号源，3 社区管理员，4 系统管理员）
     * @param identity 用户角色（0 普通用户，1 代理，2 信号源，3 社区管理员，4 系统管理员）
     */
    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    /**
     * 角色等级
     * @return identity_level 角色等级
     */
    public Integer getIdentityLevel() {
        return identityLevel;
    }

    /**
     * 角色等级
     * @param identityLevel 角色等级
     */
    public void setIdentityLevel(Integer identityLevel) {
        this.identityLevel = identityLevel;
    }
}