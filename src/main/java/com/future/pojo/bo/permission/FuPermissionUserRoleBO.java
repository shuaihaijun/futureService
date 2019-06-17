package com.future.pojo.bo.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色关系信息BO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("用户角色关系信息BO类")
@Data
public class FuPermissionUserRoleBO implements Serializable {

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
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
    /**
     * 角色ID集合
     */
    @ApiModelProperty(value = "角色ID集合")
    private List<Integer> roleIds;

}