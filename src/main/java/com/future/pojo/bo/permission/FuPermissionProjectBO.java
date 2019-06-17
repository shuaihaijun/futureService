package com.future.pojo.bo.permission;

import com.future.common.util.BeanUtil;
import com.future.entity.permission.FuPermissionProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 工程项目信息BO类
 *
 * @author Admin
 * @version: 1.0
 */
@ApiModel("工程项目信息BO类")
@Data
public class FuPermissionProjectBO implements Serializable {

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
     * 工程项目KEY
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
     * 工程项目KEY集合
     */
    @ApiModelProperty(value = "工程项目KEY集合")
    private List<Integer> projKeys;

    /**
     * 将业务层对象转换为数据访问实体对象
     *
     * @param fuPermissionProjectBO 业务层对象
     * @return 数据访问实体对象
     */
    public static FuPermissionProject boToModel(FuPermissionProjectBO fuPermissionProjectBO) {
        return boToModel(fuPermissionProjectBO, null);
    }

    /**
     * 将业务层对象转换为数据访问实体对象
     *
     * @param fuPermissionProjectBO 业务层对象
     * @param permissionProject   数据访问实体对象
     * @return 数据访问实体对象
     */
    public static FuPermissionProject boToModel(FuPermissionProjectBO fuPermissionProjectBO, FuPermissionProject permissionProject) {
        if (fuPermissionProjectBO == null) {
            return null;
        }
        if (permissionProject == null) {
            permissionProject = new FuPermissionProject();
        }
        BeanUtil.copyProperties(fuPermissionProjectBO, permissionProject);
        return permissionProject;
    }
}