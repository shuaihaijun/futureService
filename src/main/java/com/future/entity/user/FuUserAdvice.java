package com.future.entity.user;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuUserAdvice {
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
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 提交时间
     */
    private Date submitDate;

    /**
     * 所属工程项目key即fu_permission_project.proj_key
     */
    private Integer projKey;

    /**
     * 来源（0 官网留言, 1 官网跟随申请）
     */
    private Integer adviceSource;

    /**
     * 类型（0 建议，1 申请， 2 问题反馈， 3 投诉）
     */
    private Integer adviceType;

    /**
     * 状态（0 处理完成，1 处理中， 2 待处理， 3 删除）
     */
    private Integer adviceState;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 昵称
     */
    private String refName;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 建议简称
     */
    private String adviceHead;

    /**
     * 建议内容
     */
    private String adviceContent;

    /**
     * 处理人
     */
    private Integer adviceHandler;

    /**
     * 关键词
     */
    private String adviceKeyWords;

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
     * 修改时间
     * @return modify_date 修改时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 修改时间
     * @param modifyDate 修改时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 提交时间
     * @return submit_date 提交时间
     */
    public Date getSubmitDate() {
        return submitDate;
    }

    /**
     * 提交时间
     * @param submitDate 提交时间
     */
    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * 所属工程项目key即fu_permission_project.proj_key
     * @return proj_key 所属工程项目key即fu_permission_project.proj_key
     */
    public Integer getProjKey() {
        return projKey;
    }

    /**
     * 所属工程项目key即fu_permission_project.proj_key
     * @param projKey 所属工程项目key即fu_permission_project.proj_key
     */
    public void setProjKey(Integer projKey) {
        this.projKey = projKey;
    }

    /**
     * 来源（0 官网留言, 1 官网跟随申请）
     * @return advice_source 来源（0 官网留言, 1 官网跟随申请）
     */
    public Integer getAdviceSource() {
        return adviceSource;
    }

    /**
     * 来源（0 官网留言, 1 官网跟随申请）
     * @param adviceSource 来源（0 官网留言, 1 官网跟随申请）
     */
    public void setAdviceSource(Integer adviceSource) {
        this.adviceSource = adviceSource;
    }

    /**
     * 类型（0 建议，1 申请， 2 问题反馈， 3 投诉）
     * @return advice_type 类型（0 建议，1 申请， 2 问题反馈， 3 投诉）
     */
    public Integer getAdviceType() {
        return adviceType;
    }

    /**
     * 类型（0 建议，1 申请， 2 问题反馈， 3 投诉）
     * @param adviceType 类型（0 建议，1 申请， 2 问题反馈， 3 投诉）
     */
    public void setAdviceType(Integer adviceType) {
        this.adviceType = adviceType;
    }

    /**
     * 状态（0 处理完成，1 处理中， 2 待处理， 3 删除）
     * @return advice_state 状态（0 处理完成，1 处理中， 2 待处理， 3 删除）
     */
    public Integer getAdviceState() {
        return adviceState;
    }

    /**
     * 状态（0 处理完成，1 处理中， 2 待处理， 3 删除）
     * @param adviceState 状态（0 处理完成，1 处理中， 2 待处理， 3 删除）
     */
    public void setAdviceState(Integer adviceState) {
        this.adviceState = adviceState;
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
     * 昵称
     * @return ref_name 昵称
     */
    public String getRefName() {
        return refName;
    }

    /**
     * 昵称
     * @param refName 昵称
     */
    public void setRefName(String refName) {
        this.refName = refName == null ? null : refName.trim();
    }

    /**
     * 电子邮件
     * @return email 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 电子邮件
     * @param email 电子邮件
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 电话号码
     * @return phone 电话号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话号码
     * @param phone 电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 建议简称
     * @return advice_head 建议简称
     */
    public String getAdviceHead() {
        return adviceHead;
    }

    /**
     * 建议简称
     * @param adviceHead 建议简称
     */
    public void setAdviceHead(String adviceHead) {
        this.adviceHead = adviceHead == null ? null : adviceHead.trim();
    }

    /**
     * 建议内容
     * @return advice_content 建议内容
     */
    public String getAdviceContent() {
        return adviceContent;
    }

    /**
     * 建议内容
     * @param adviceContent 建议内容
     */
    public void setAdviceContent(String adviceContent) {
        this.adviceContent = adviceContent == null ? null : adviceContent.trim();
    }

    /**
     * 处理人
     * @return advice_handler 处理人
     */
    public Integer getAdviceHandler() {
        return adviceHandler;
    }

    /**
     * 处理人
     * @param adviceHandler 处理人
     */
    public void setAdviceHandler(Integer adviceHandler) {
        this.adviceHandler = adviceHandler;
    }

    /**
     * 关键词
     * @return advice_key_words 关键词
     */
    public String getAdviceKeyWords() {
        return adviceKeyWords;
    }

    /**
     * 关键词
     * @param adviceKeyWords 关键词
     */
    public void setAdviceKeyWords(String adviceKeyWords) {
        this.adviceKeyWords = adviceKeyWords == null ? null : adviceKeyWords.trim();
    }
}