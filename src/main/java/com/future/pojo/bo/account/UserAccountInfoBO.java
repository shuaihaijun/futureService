package com.future.pojo.bo.account;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/*用户社区账户BO*/
@Data
public class UserAccountInfoBO {

    private Integer accountId;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 账户等级
     */
    private String accountLevel;

    /**
     * 交易密码
     */
    private String accountSecurities;

    /**
     * 交易的特权
     */
    private String accountOptions;

    /**
     * 社区总收益
     */
    private String accountCommodity;

    /**
     * 社区账户余额
     */
    private BigDecimal accountMoney;

    /**
     * 社区币余额
     */
    private BigDecimal accountCoin;

    /**
     * 期债(需付金额）
     */
    private BigDecimal accountDebt;

    /**
     * 账户上次余额
     */
    private BigDecimal accountLastAmt;

    /**
     * 社区总投资额
     */
    private BigDecimal accountInvestment;

    /**
     * 账户状态（0 正常，1冻结，2 删除）
     */
    private Integer accountState;

    /**
     * 相关账户信息
     */
    private String isrelativeContent;

    /**
     * 用户账号
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
     * 用户类型（0 普通用户、1 试用会员、2 普通会员、3 VIP会员、4 业务员、5 IB、6 MIB、7 PIB、8 分析师、9 总监、10 总经理、11 信号源）
     */
    private Integer userType;

    /**
     * 用户状态(0 正常，1 未审核，2 待审核，2 删除）
     */
    private Integer userState;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 是否已验证身份
     */
    private Integer isVerified;

    /**
     * 是否已绑定第三方账户
     */
    private Integer isAccount;

    /**
     * 介绍人
     */
    private Integer introducer;

}
