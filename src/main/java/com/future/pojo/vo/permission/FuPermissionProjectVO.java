package com.future.pojo.vo.permission;

import com.future.common.util.BeanUtil;
import com.future.entity.permission.FuPermissionProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工程项目信息VO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("工程项目信息VO类")
@Data
public class FuPermissionProjectVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 工程项目名称
     */
    @ApiModelProperty(value = "工程项目名称")
    private String projName;
    /**
     * 工程项目key
     */
    @ApiModelProperty(value = "工程项目KEY")
    private Integer projKey;
    /**
     * 工程项目负责人姓名
     */
    @ApiModelProperty(value = "工程项目负责人姓名")
    private String projAdmin;
    /**
     * 状态 1 有效 0 无效
     */
    @ApiModelProperty(value = "状态 1：有效 0：无效")
    private Integer projStatus;

    /**
     * 类型 0普通系统 1特殊系统
     */
    @ApiModelProperty(value = "类型 0普通系统 1特殊系统")
    private Integer projType;

    /**
     * 项目工程logo
     */
    private String projLogo;

    /**
     * 项目工程标语
     */
    private String projSlogan;

    /**
     * 项目工程描述
     */
    private String projDesc;

    /**
     * 创建人姓名
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 将数据访问实体对象转换为表现层对象
     *
     * @param permissionProject 数据访问实体对象
     * @return 表现层对象
     */
    public static FuPermissionProjectVO modelToVO(FuPermissionProject permissionProject) {
        if (permissionProject == null) {
            return null;
        }
        FuPermissionProjectVO fuPermissionProjectVO = new FuPermissionProjectVO();
        BeanUtil.copyProperties(permissionProject, fuPermissionProjectVO);
        return fuPermissionProjectVO;
    }
}