package com.future.entity.product;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class FuProductSignal {
    public static final String APPLY_ID = "id";
    public static final String USER_ID = "user_id";
    public static final String SIGNAL_ID = "signal_id";
    public static final String SIGNAL_NAME = "signal_name";
    public static final String SIGNAL_STATE = "signal_state";
    public static final String SERVER_NAME = "server_name";
    public static final String MT_ACC_ID = "mt_acc_id";
    public static final String CREATE_DATE = "create_date";
    public static final String CHECK_DATE = "check_date";
    public static final String PROJ_KEY = "proj_key";
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * ����ʱ��
     */
    private Date createDate;

    /**
     * �޸�ʱ��
     */
    private Date modifyDate;

    /**
     * �û�ID
     */
    private Integer userId;

    /**
     * �ź�Դ����
     */
    private String signalName;

    /**
     * �ź�Դ�ȼ�
     */
    private Integer signalLevel;

    /**
     * �ź�Դ����
     */
    private String signalDesc;

    /**
     * �ź�Դ�ۺ�����
     */
    private Integer signalRatings;

    /**
     * �ź�Դ״̬��0 ������1 ���أ�2 ɾ����
     */
    private Integer signalState;

    /**
     * ����ʱ��
     */
    private Date applyDate;

    /**
     * ���ʱ��
     */
    private Date checkDate;

    /**
     * �Ŷ�����
     */
    private String signalTem;

    /**
     * ��������
     */
    private Integer signalLots;

    /**
     * ����Ӯ����
     */
    private Integer signalLotsProfit;

    /**
     * �˻����
     */
    private BigDecimal signalAccountMoney;

    /**
     * ��Ҫ���ױ���
     */
    private String signalCurrency;

    /**
     * ��������
     */
    private Integer signalFollows;

    /**
     * �¾�����
     */
    private BigDecimal monthlyAverageIncome;

    /**
     * ��ʷ����
     */
    private BigDecimal historyIncome;

    /**
     * ��ʷ���س�
     */
    private BigDecimal historyWithdraw;

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
     * ��ע
     */
    private String remarks;

    /**
     * ����״̬��0 δ������1 �Ѽ�����
     */
    private Integer connectState;

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
     * �ź�Դ�ȼ�
     * @return signal_level �ź�Դ�ȼ�
     */
    public Integer getSignalLevel() {
        return signalLevel;
    }

    /**
     * �ź�Դ�ȼ�
     * @param signalLevel �ź�Դ�ȼ�
     */
    public void setSignalLevel(Integer signalLevel) {
        this.signalLevel = signalLevel;
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
     * �ź�Դ�ۺ�����
     * @return signal_ratings �ź�Դ�ۺ�����
     */
    public Integer getSignalRatings() {
        return signalRatings;
    }

    /**
     * �ź�Դ�ۺ�����
     * @param signalRatings �ź�Դ�ۺ�����
     */
    public void setSignalRatings(Integer signalRatings) {
        this.signalRatings = signalRatings;
    }

    /**
     * �ź�Դ״̬��0 ������1 ���أ�2 ɾ����
     * @return signal_state �ź�Դ״̬��0 ������1 ���أ�2 ɾ����
     */
    public Integer getSignalState() {
        return signalState;
    }

    /**
     * �ź�Դ״̬��0 ������1 ���أ�2 ɾ����
     * @param signalState �ź�Դ״̬��0 ������1 ���أ�2 ɾ����
     */
    public void setSignalState(Integer signalState) {
        this.signalState = signalState;
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
     * ��������
     * @return signal_lots ��������
     */
    public Integer getSignalLots() {
        return signalLots;
    }

    /**
     * ��������
     * @param signalLots ��������
     */
    public void setSignalLots(Integer signalLots) {
        this.signalLots = signalLots;
    }

    /**
     * ����Ӯ����
     * @return signal_lots_profit ����Ӯ����
     */
    public Integer getSignalLotsProfit() {
        return signalLotsProfit;
    }

    /**
     * ����Ӯ����
     * @param signalLotsProfit ����Ӯ����
     */
    public void setSignalLotsProfit(Integer signalLotsProfit) {
        this.signalLotsProfit = signalLotsProfit;
    }

    /**
     * �˻����
     * @return signal_account_money �˻����
     */
    public BigDecimal getSignalAccountMoney() {
        return signalAccountMoney;
    }

    /**
     * �˻����
     * @param signalAccountMoney �˻����
     */
    public void setSignalAccountMoney(BigDecimal signalAccountMoney) {
        this.signalAccountMoney = signalAccountMoney;
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
     * ��������
     * @return signal_follows ��������
     */
    public Integer getSignalFollows() {
        return signalFollows;
    }

    /**
     * ��������
     * @param signalFollows ��������
     */
    public void setSignalFollows(Integer signalFollows) {
        this.signalFollows = signalFollows;
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
     * ��ʷ����
     * @return history_income ��ʷ����
     */
    public BigDecimal getHistoryIncome() {
        return historyIncome;
    }

    /**
     * ��ʷ����
     * @param historyIncome ��ʷ����
     */
    public void setHistoryIncome(BigDecimal historyIncome) {
        this.historyIncome = historyIncome;
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
     * ��ע
     * @return remarks ��ע
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * ��ע
     * @param remarks ��ע
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * ����״̬��0 δ������1 �Ѽ�����
     * @return connect_state ����״̬��0 δ������1 �Ѽ�����
     */
    public Integer getConnectState() {
        return connectState;
    }

    /**
     * ����״̬��0 δ������1 �Ѽ�����
     * @param connectState ����״̬��0 δ������1 �Ѽ�����
     */
    public void setConnectState(Integer connectState) {
        this.connectState = connectState;
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