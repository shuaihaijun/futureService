package com.future.entity.product;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuProductSignalApply {

    public static final String APPLY_ID = "id";
    public static final String USER_ID = "user_id";
    public static final String SIGNAL_NAME = "signal_name";
    public static final String APPLY_STATE = "apply_state";
    public static final String SERVER_NAME = "server_name";
    public static final String MT_ACC_ID = "mt_acc_id";
    public static final String CREATE_DATE = "create_date";
    public static final String APPLY_DATE = "apply_date";
    public static final String CHECK_DATE = "check_date";
    public static final String PROJ_KEY = "proj_key";
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * �û�ID
     */
    private Integer userId;

    /**
     * �ź�Դ����
     */
    private String signalName;

    /**
     * �Ŷ�����
     */
    private String signalTem;

    /**
     * �ź�Դ����
     */
    private String signalDesc;

    /**
     * ��Ҫ���ױ���
     */
    private String signalCurrency;

    /**
     * �¾�����
     */
    private BigDecimal monthlyAverageIncome;

    /**
     * ��ʷ���س�
     */
    private BigDecimal historyWithdraw;

    /**
     * ����״̬��0 ������1 �ݴ棬2 ����ˣ�3 δͨ����
     */
    private Integer applyState;

    /**
     * ����ʱ��
     */
    private Date applyDate;

    /**
     * ���ʱ��
     */
    private Date checkDate;

    /**
     * ����ʱ��
     */
    private Date createDate;

    /**
     * �޸�ʱ��
     */
    private Date modifyDate;

    /**
     * �����ʼ�
     */
    private String email;

    /**
     * �绰����
     */
    private String phone;

    /**
     * qq����
     */
    private String qqNumber;

    /**
     * ����������
     */
    private String serverName;

    /**
     * mtƽ̨�˺�
     */
    private String mtAccId;

    /**
     * ���뽻��
     */
    private String mtPasswordTrade;

    /**
     * ��Ħ����
     */
    private String mtPasswordWatch;

    /**
     * ���ٲ����ʽ�
     */
    private BigDecimal minimum;

    /**
     * �껯Ԥ��������
     */
    private BigDecimal annualizedExpectedReturn;

    /**
     * ��ʷ������(��)
     */
    private BigDecimal historicalReturn;

    /**
     * �����������
     */
    private BigDecimal suggestCycle;

    /**
     * 
     */
    private String remarks;

    /**
     * ��Ŀ����KEY
     */
    private Integer projKey;

    /**
     * ��Ŀ��������
     */
    private String projName;

    /**
     * ������ID
     */
    private Integer operUserId;

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
     * �û�ID
     * @return user_id �û�ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * �û�ID
     * @param userId �û�ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * �ź�Դ����
     * @return signal_name �ź�Դ����
     */
    public String getSignalName() {
        return signalName;
    }

    /**
     * �ź�Դ����
     * @param signalName �ź�Դ����
     */
    public void setSignalName(String signalName) {
        this.signalName = signalName == null ? null : signalName.trim();
    }

    /**
     * �Ŷ�����
     * @return signal_tem �Ŷ�����
     */
    public String getSignalTem() {
        return signalTem;
    }

    /**
     * �Ŷ�����
     * @param signalTem �Ŷ�����
     */
    public void setSignalTem(String signalTem) {
        this.signalTem = signalTem == null ? null : signalTem.trim();
    }

    /**
     * �ź�Դ����
     * @return signal_desc �ź�Դ����
     */
    public String getSignalDesc() {
        return signalDesc;
    }

    /**
     * �ź�Դ����
     * @param signalDesc �ź�Դ����
     */
    public void setSignalDesc(String signalDesc) {
        this.signalDesc = signalDesc == null ? null : signalDesc.trim();
    }

    /**
     * ��Ҫ���ױ���
     * @return signal_currency ��Ҫ���ױ���
     */
    public String getSignalCurrency() {
        return signalCurrency;
    }

    /**
     * ��Ҫ���ױ���
     * @param signalCurrency ��Ҫ���ױ���
     */
    public void setSignalCurrency(String signalCurrency) {
        this.signalCurrency = signalCurrency == null ? null : signalCurrency.trim();
    }

    /**
     * �¾�����
     * @return monthly_average_income �¾�����
     */
    public BigDecimal getMonthlyAverageIncome() {
        return monthlyAverageIncome;
    }

    /**
     * �¾�����
     * @param monthlyAverageIncome �¾�����
     */
    public void setMonthlyAverageIncome(BigDecimal monthlyAverageIncome) {
        this.monthlyAverageIncome = monthlyAverageIncome;
    }

    /**
     * ��ʷ���س�
     * @return history_withdraw ��ʷ���س�
     */
    public BigDecimal getHistoryWithdraw() {
        return historyWithdraw;
    }

    /**
     * ��ʷ���س�
     * @param historyWithdraw ��ʷ���س�
     */
    public void setHistoryWithdraw(BigDecimal historyWithdraw) {
        this.historyWithdraw = historyWithdraw;
    }

    /**
     * ����״̬��0 ������1 �ݴ棬2 ����ˣ�3 δͨ����
     * @return apply_state ����״̬��0 ������1 �ݴ棬2 ����ˣ�3 δͨ����
     */
    public Integer getApplyState() {
        return applyState;
    }

    /**
     * ����״̬��0 ������1 �ݴ棬2 ����ˣ�3 δͨ����
     * @param applyState ����״̬��0 ������1 �ݴ棬2 ����ˣ�3 δͨ����
     */
    public void setApplyState(Integer applyState) {
        this.applyState = applyState;
    }

    /**
     * ����ʱ��
     * @return apply_date ����ʱ��
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * ����ʱ��
     * @param applyDate ����ʱ��
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    /**
     * ���ʱ��
     * @return check_date ���ʱ��
     */
    public Date getCheckDate() {
        return checkDate;
    }

    /**
     * ���ʱ��
     * @param checkDate ���ʱ��
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * ����ʱ��
     * @return create_date ����ʱ��
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * ����ʱ��
     * @param createDate ����ʱ��
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * �޸�ʱ��
     * @return modify_date �޸�ʱ��
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * �޸�ʱ��
     * @param modifyDate �޸�ʱ��
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * �����ʼ�
     * @return email �����ʼ�
     */
    public String getEmail() {
        return email;
    }

    /**
     * �����ʼ�
     * @param email �����ʼ�
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * �绰����
     * @return phone �绰����
     */
    public String getPhone() {
        return phone;
    }

    /**
     * �绰����
     * @param phone �绰����
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * qq����
     * @return qq_number qq����
     */
    public String getQqNumber() {
        return qqNumber;
    }

    /**
     * qq����
     * @param qqNumber qq����
     */
    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber == null ? null : qqNumber.trim();
    }

    /**
     * ����������
     * @return server_name ����������
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * ����������
     * @param serverName ����������
     */
    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }

    /**
     * mtƽ̨�˺�
     * @return mt_acc_id mtƽ̨�˺�
     */
    public String getMtAccId() {
        return mtAccId;
    }

    /**
     * mtƽ̨�˺�
     * @param mtAccId mtƽ̨�˺�
     */
    public void setMtAccId(String mtAccId) {
        this.mtAccId = mtAccId == null ? null : mtAccId.trim();
    }

    /**
     * ���뽻��
     * @return mt_password_trade ���뽻��
     */
    public String getMtPasswordTrade() {
        return mtPasswordTrade;
    }

    /**
     * ���뽻��
     * @param mtPasswordTrade ���뽻��
     */
    public void setMtPasswordTrade(String mtPasswordTrade) {
        this.mtPasswordTrade = mtPasswordTrade == null ? null : mtPasswordTrade.trim();
    }

    /**
     * ��Ħ����
     * @return mt_password_watch ��Ħ����
     */
    public String getMtPasswordWatch() {
        return mtPasswordWatch;
    }

    /**
     * ��Ħ����
     * @param mtPasswordWatch ��Ħ����
     */
    public void setMtPasswordWatch(String mtPasswordWatch) {
        this.mtPasswordWatch = mtPasswordWatch == null ? null : mtPasswordWatch.trim();
    }

    /**
     * ���ٲ����ʽ�
     * @return minimum ���ٲ����ʽ�
     */
    public BigDecimal getMinimum() {
        return minimum;
    }

    /**
     * ���ٲ����ʽ�
     * @param minimum ���ٲ����ʽ�
     */
    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    /**
     * �껯Ԥ��������
     * @return annualized_expected_return �껯Ԥ��������
     */
    public BigDecimal getAnnualizedExpectedReturn() {
        return annualizedExpectedReturn;
    }

    /**
     * �껯Ԥ��������
     * @param annualizedExpectedReturn �껯Ԥ��������
     */
    public void setAnnualizedExpectedReturn(BigDecimal annualizedExpectedReturn) {
        this.annualizedExpectedReturn = annualizedExpectedReturn;
    }

    /**
     * ��ʷ������(��)
     * @return historical_return ��ʷ������(��)
     */
    public BigDecimal getHistoricalReturn() {
        return historicalReturn;
    }

    /**
     * ��ʷ������(��)
     * @param historicalReturn ��ʷ������(��)
     */
    public void setHistoricalReturn(BigDecimal historicalReturn) {
        this.historicalReturn = historicalReturn;
    }

    /**
     * �����������
     * @return suggest_cycle �����������
     */
    public BigDecimal getSuggestCycle() {
        return suggestCycle;
    }

    /**
     * �����������
     * @param suggestCycle �����������
     */
    public void setSuggestCycle(BigDecimal suggestCycle) {
        this.suggestCycle = suggestCycle;
    }

    /**
     * 
     * @return remarks 
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 
     * @param remarks 
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * ��Ŀ����KEY
     * @return proj_key ��Ŀ����KEY
     */
    public Integer getProjKey() {
        return projKey;
    }

    /**
     * ��Ŀ����KEY
     * @param projKey ��Ŀ����KEY
     */
    public void setProjKey(Integer projKey) {
        this.projKey = projKey;
    }

    /**
     * ��Ŀ��������
     * @return proj_name ��Ŀ��������
     */
    public String getProjName() {
        return projName;
    }

    /**
     * ��Ŀ��������
     * @param projName ��Ŀ��������
     */
    public void setProjName(String projName) {
        this.projName = projName == null ? null : projName.trim();
    }

    /**
     * ������ID
     * @return oper_user_id ������ID
     */
    public Integer getOperUserId() {
        return operUserId;
    }

    /**
     * ������ID
     * @param operUserId ������ID
     */
    public void setOperUserId(Integer operUserId) {
        this.operUserId = operUserId;
    }
}