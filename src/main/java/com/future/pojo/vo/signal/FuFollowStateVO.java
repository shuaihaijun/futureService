package com.future.pojo.vo.signal;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信号源信息表
 */
@Data
public class FuFollowStateVO {

    private Integer signalConnectState;
    private Integer userConnectState;


    private Integer id;
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
     * 跟单状态（0 正常，1 暂停，2 废弃）
     */
    private Integer followState;

    /**
     * 跟单方向（0 正向跟单，1  反向跟单）
     */
    private Integer followDirect;

    /**
     * 跟单模式（0 多空跟单，1 只跟多单，2 只跟空单）
     */
    private Integer followMode;

    /**
     * 跟单类型（0 按手数比例，1 按固定金额，2 按固定手数）
     */
    private Integer followType;

    /**
     * 跟单数量
     */
    private BigDecimal followAmount;

    /**
     * 最大跟單金額
     */
    private BigDecimal followMaxAmount;

    /**
     * 最小跟单金额
     */
    private BigDecimal followMinAmount;

    /**
     * 预警金额
     */
    private BigDecimal followAlarmAmount;

    /**
     * 最大跟单手数
     */
    private BigDecimal followMaxHands;

    /**
     * 最小跟单手数
     */
    private BigDecimal followMinHands;

    /**getUserByIdOrName
     * 预警损失百分比 （ 0.2 , 0.3）
     */
    private BigDecimal followAlarmLose;

    /**
     * 预警线 百分比（ 0.2 , 0.3）
     */
    private BigDecimal followAlarmLevel;

    /**
     * 账户最低安全净值百分比
     */
    private BigDecimal accountEquityPercentage;

    /**
     * 账户最低安全净值
     */
    private BigDecimal accountEquityAmount;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 创建时间
     */
    private Date createDate;

}