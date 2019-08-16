package com.future.entity.com;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuComLogs {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 日志时间
     */
    private Date time;

    /**
     * 日志类型
     */
    private int type;

    /**
     * 操作（登录，删除，交易，跟单）
     */
    private String oper;

    /**
     * 操作人ID
     */
    private Integer operId;

    /**
     * 日志内容
     */
    private String content;

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
     * 日志时间
     * @return time 日志时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 日志时间
     * @param time 日志时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 日志类型
     * @return type 日志类型
     */
    public int getType() {
        return type;
    }

    /**
     * 日志类型
     * @param type 日志类型
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 操作（登录，删除，交易，跟单）
     * @return oper 操作（登录，删除，交易，跟单）
     */
    public String getOper() {
        return oper;
    }

    /**
     * 操作（登录，删除，交易，跟单）
     * @param oper 操作（登录，删除，交易，跟单）
     */
    public void setOper(String oper) {
        this.oper = oper == null ? null : oper.trim();
    }

    /**
     * 操作人ID
     * @return oper_id 操作人ID
     */
    public Integer getOperId() {
        return operId;
    }

    /**
     * 操作人ID
     * @param operId 操作人ID
     */
    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    /**
     * 日志内容
     * @return content 日志内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 日志内容
     * @param content 日志内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}