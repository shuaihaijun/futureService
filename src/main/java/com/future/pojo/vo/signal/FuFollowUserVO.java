package com.future.pojo.vo.signal;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信号源信息表
 */
@Data
public class FuFollowUserVO {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 信号源ID
     */
    private Integer signalId;

    /**
     * 用户服务器
     */
    private String userServerName;

    /**
     * 用户mt平台账号
     */
    private String userMtAccId;

    /**
     * 信号源服务器
     */
    private String signalServerName;

    /**
     * 信号源mt平台账号
     */
    private String signalMtAccId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 规则类型（0 按手数比例，1 按固定金额，2 按固定手数）
     */
    private Integer followType;

    /**
     * 规则状态（0 正常，1  隐藏，2 废弃）
     */
    private Integer followState;

    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 姓名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String refName;

    /**
     * 介绍人
     */
    private Integer introducer;

    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 推荐人数
     */
    private Integer recommend;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 最大跟单比例
     */
    private BigDecimal followLevel;
    /**
     * 杠杆
     */
    private BigDecimal leverage;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 信用额
     */
    private BigDecimal credit;

    /**
     * 收益
     */
    private BigDecimal profit;

    /**
     * 净值
     */
    private BigDecimal equity;

    /**
     * 保证金
     */
    private BigDecimal margin;

}