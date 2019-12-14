package com.future.pojo.bo.follow;

import java.math.BigDecimal;
import java.util.Date;

public class UserFollowsAccountBO {

    /*跟随信息*/
    private Integer userId; //用户ID
    private Integer signalId; // 信号源ID
    private Date createDate;//创建时间
    private Date modifyDate;//修改时间
    private Integer ruleType;//规则类型（0 按手数比例，1 按净值金额，2 按固定手数）
    private BigDecimal amount;//数量
    private Integer ruleState;//规则状态（0 正常，1  隐藏，2 废弃）
    private BigDecimal limitUpperAmount;//单笔上限金额
    private BigDecimal accountEquityAmount;//账户最低安全净值
    private BigDecimal accountEquityPercentage;//账户最低安全净值百分比
    private BigDecimal followMaxAmount;//最大跟單金額
    private BigDecimal followAlarmAmount;//预警金额
    private BigDecimal followAlarmLose;//预警损失百分比 （ 0.2 , 0.3）
    private BigDecimal followAlarmLevel;//预警线 百分比（ 0.2 , 0.3）

    /*账户信息*/
    private String userServerName;//用户服务器
    private String userMtAccId;//用户mt平台账号
    private String signalServerName;//信号源服务器
    private String signalMtAccId;//信号源mt平台账号

    /**
     * 账户服务器地址
     */
    private String accountUrl;

    /**
     * 账户服务器端口
     */
    private Integer accountPort;
}