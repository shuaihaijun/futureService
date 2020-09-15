package com.future.entity.permission;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class FuPermissionControl {

    public static String PROJ_KEY ="proj_key";
    public static String CONTROL_SOURCE ="control_source";
    public static String CONTROL_TYPE ="control_type";
    public static String CONTROL_POINT ="control_point";
    public static String CONTROL_ACTION ="control_action";
    public static String CONTROL_STATE ="control_state";
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
     * 项目工程KEY
     */
    private Integer projKey;

    /**
     * 来源（0 官网, 1 crm）
     */
    private Integer controlSource;

    /**
     * 类型（0 页面，1 logo，2 url）
     */
    private Integer controlType;

    /**
     * 控制点（自定义）
     */
    private Integer controlPoint;

    /**
     * 控制动作（0 关，1 开）
     */
    private Integer controlAction;

    /**
     * 状态（0 正常，1 准备中）
     */
    private Integer controlState;

    /**
     * 控制内容
     */
    private String controlContent;

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
     * 项目工程KEY
     * @return proj_key 项目工程KEY
     */
    public Integer getProjKey() {
        return projKey;
    }

    /**
     * 项目工程KEY
     * @param projKey 项目工程KEY
     */
    public void setProjKey(Integer projKey) {
        this.projKey = projKey;
    }

    /**
     * 来源（0 官网, 1 crm）
     * @return control_source 来源（0 官网, 1 crm）
     */
    public Integer getControlSource() {
        return controlSource;
    }

    /**
     * 来源（0 官网, 1 crm）
     * @param controlSource 来源（0 官网, 1 crm）
     */
    public void setControlSource(Integer controlSource) {
        this.controlSource = controlSource;
    }

    /**
     * 类型（0 页面，1 logo，2 url）
     * @return control_type 类型（0 页面，1 logo，2 url）
     */
    public Integer getControlType() {
        return controlType;
    }

    /**
     * 类型（0 页面，1 logo，2 url）
     * @param controlType 类型（0 页面，1 logo，2 url）
     */
    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }

    /**
     * 控制点（自定义）
     * @return control_point 控制点（自定义）
     */
    public Integer getControlPoint() {
        return controlPoint;
    }

    /**
     * 控制点（自定义）
     * @param controlPoint 控制点（自定义）
     */
    public void setControlPoint(Integer controlPoint) {
        this.controlPoint = controlPoint;
    }

    /**
     * 控制动作（0 关，1 开）
     * @return control_action 控制动作（0 关，1 开）
     */
    public Integer getControlAction() {
        return controlAction;
    }

    /**
     * 控制动作（0 关，1 开）
     * @param controlAction 控制动作（0 关，1 开）
     */
    public void setControlAction(Integer controlAction) {
        this.controlAction = controlAction;
    }

    /**
     * 状态（0 正常，1 准备中）
     * @return control_state 状态（0 正常，1 准备中）
     */
    public Integer getControlState() {
        return controlState;
    }

    /**
     * 状态（0 正常，1 准备中）
     * @param controlState 状态（0 正常，1 准备中）
     */
    public void setControlState(Integer controlState) {
        this.controlState = controlState;
    }

    /**
     * 控制内容
     * @return control_content 控制内容
     */
    public String getControlContent() {
        return controlContent;
    }

    /**
     * 控制内容
     * @param controlContent 控制内容
     */
    public void setControlContent(String controlContent) {
        this.controlContent = controlContent == null ? null : controlContent.trim();
    }
}