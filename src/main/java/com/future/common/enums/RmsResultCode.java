package com.future.common.enums;


/**
 * @Description: 权限模块统一返回状态码及描述信息
 * 用户模块错误码区间：300000-399999
 */
public enum RmsResultCode implements ResultCode{


    RMS_MODULE_ERROR(300000, " 权限模块异常"),
    /* 工程项目信息模块业务异常 */
    PERMISSION_PROJECT_DATA_SAVE_FAILURE(300001, "工程项目信息新增失败"),
    PERMISSION_PROJECT_DATA_UPDATE_FAILURE(300002, "工程项目信息更新失败"),
    PERMISSION_PROJECT_DATA_DEL_FAILURE(300003, "工程项目信息删除失败"),
    PERMISSION_PROJECT_DATA_IS_EXIST(300004, "此工程项目已存在"),
    PERMISSION_PROJECT_DATA_DEL_FAILURE_INFOS(300005, "工程项目信息删除失败"),


    /** 权限资源信息模块业务异常 */
    PERMISSION_RESOURCE_DATA_IS_EXIST(300101, "此权限资源已存在"),
    PERMISSION_RESOURCE_DATA_SAVE_FAILURE(300102, "权限资源信息新增失败"),
    PERMISSION_RESOURCE_DATA_UPDATE_FAILURE(300103, "权限资源信息更新失败"),
    PERMISSION_RESOURCE_DATA_DEL_FAILURE(300104, "权限资源信息删除失败"),
    PERMISSION_RESOURCE_INDEX_IS_NULL(300105, "权限资源信息更新失败，更新主键缺失"),
    PERMISSION_RESOURCE_IS_NOT_EXIST(300106, "暂无权限资源信息"),

    /** 角色信息模块业务异常 */
    PERMISSION_ROLE_DATA_IS_EXIST(300201, "此角色已存在"),
    PERMISSION_ROLE_DATA_SAVE_FAILURE(300202, "角色信息新增失败"),
    PERMISSION_ROLE_DATA_BATCHSAVE_FAILURE(300203, "角色权限关联信息批量保存失败"),
    PERMISSION_ROLE_DATA_UPDATE_FAILURE(300204, "角色信息更新失败"),
    PERMISSION_ROLE_DATA_DEL_FAILURE(300205, "角色信息删除失败"),
    PERMISSION_ROLE_INDEX_IS_NULL(300206, "角色信息更新失败，更新主键缺失"),


    /** 用户工程项目关联模块业务异常 */
    PERMISSION_USER_PROJECT_DATA_SAVE_FAILURE(300302, "用户工程项目信息保存失败"),


    /** 角色权限关系模块业务异常 */
    PERMISSION_ROLE_RESOURCE_DATA_DEL_FAILURE(300401, "角色权限关系删除失败"),


    /** 用户角色关联模块业务异常 */
    PERMISSION_USER_ROLE_BIND(300501, "用户与角色已绑定");




    private Integer code;

    private String message;

    RmsResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}