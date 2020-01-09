package com.future.entity.product;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuProductSignalValuation {
    public static String SIGNAL_ID="signal_id";

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 信号源ID
     */
    private Integer signalId;

    /**
     * 评估时间
     */
    private Date valuationDate;

    /**
     * 评估等级
     */
    private Byte level;

    /**
     * 稳健性
     */
    private BigDecimal steadyScore;

    /**
     * 盈利能力
     */
    private BigDecimal profitScore;

    /**
     * 风控能力
     */
    private BigDecimal riskControlScore;

    /**
     * 资金规模
     */
    private BigDecimal fundSizeScore;

    /**
     * 费侥幸获利
     */
    private BigDecimal nonFlukeProfitScore;

    /**
     * 综合评分
     */
    private BigDecimal score;

    /**
     * 备注
     */
    private String meno;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 信号源ID
     * @return signal_id 信号源ID
     */
    public Integer getSignalId() {
        return signalId;
    }

    /**
     * 信号源ID
     * @param signalId 信号源ID
     */
    public void setSignalId(Integer signalId) {
        this.signalId = signalId;
    }

    /**
     * 评估时间
     * @return valuation_date 评估时间
     */
    public Date getValuationDate() {
        return valuationDate;
    }

    /**
     * 评估时间
     * @param valuationDate 评估时间
     */
    public void setValuationDate(Date valuationDate) {
        this.valuationDate = valuationDate;
    }

    /**
     * 评估等级
     * @return level 评估等级
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * 评估等级
     * @param level 评估等级
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * 稳健性
     * @return steady_score 稳健性
     */
    public BigDecimal getSteadyScore() {
        return steadyScore;
    }

    /**
     * 稳健性
     * @param steadyScore 稳健性
     */
    public void setSteadyScore(BigDecimal steadyScore) {
        this.steadyScore = steadyScore;
    }

    /**
     * 盈利能力
     * @return profit_score 盈利能力
     */
    public BigDecimal getProfitScore() {
        return profitScore;
    }

    /**
     * 盈利能力
     * @param profitScore 盈利能力
     */
    public void setProfitScore(BigDecimal profitScore) {
        this.profitScore = profitScore;
    }

    /**
     * 风控能力
     * @return risk_control_score 风控能力
     */
    public BigDecimal getRiskControlScore() {
        return riskControlScore;
    }

    /**
     * 风控能力
     * @param riskControlScore 风控能力
     */
    public void setRiskControlScore(BigDecimal riskControlScore) {
        this.riskControlScore = riskControlScore;
    }

    /**
     * 资金规模
     * @return fund_size_score 资金规模
     */
    public BigDecimal getFundSizeScore() {
        return fundSizeScore;
    }

    /**
     * 资金规模
     * @param fundSizeScore 资金规模
     */
    public void setFundSizeScore(BigDecimal fundSizeScore) {
        this.fundSizeScore = fundSizeScore;
    }

    /**
     * 费侥幸获利
     * @return non_fluke_profit_score 费侥幸获利
     */
    public BigDecimal getNonFlukeProfitScore() {
        return nonFlukeProfitScore;
    }

    /**
     * 费侥幸获利
     * @param nonFlukeProfitScore 费侥幸获利
     */
    public void setNonFlukeProfitScore(BigDecimal nonFlukeProfitScore) {
        this.nonFlukeProfitScore = nonFlukeProfitScore;
    }

    /**
     * 综合评分
     * @return score 综合评分
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * 综合评分
     * @param score 综合评分
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * 备注
     * @return meno 备注
     */
    public String getMeno() {
        return meno;
    }

    /**
     * 备注
     * @param meno 备注
     */
    public void setMeno(String meno) {
        this.meno = meno == null ? null : meno.trim();
    }
}