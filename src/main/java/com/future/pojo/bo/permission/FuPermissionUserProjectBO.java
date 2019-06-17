package com.future.pojo.bo.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户工程项目关系信息BO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("用户工程项目关系信息BO类")
@Data
public class FuPermissionUserProjectBO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**
     * 所拥有管理员的系统
     */
    @ApiModelProperty(value = "管理员用户所管理的工程项目KEY")
    private Integer projKey;
    /**
     * 工程项目key集合
     */
    @ApiModelProperty(value = "工程项目KEY集合")
    private List<Integer> projKeys;
}