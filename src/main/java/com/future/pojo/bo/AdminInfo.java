package com.future.pojo.bo;


import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 用于封装用户信息，将需要的用户信息放在其中，便于在session获取有效数据
 */
@Data
public class AdminInfo implements Serializable{

    /**用户id*/
    private Integer adminId;
    /**登录用户名*/
    private String adminLogin;
    /**用户名字*/
    private String adminName;
    /**用户密码*/
    private String adminPassword;
    /**角色字典表id*/
    private Integer r_id;
    /**角色名称*/
    private String r_name;

    public AdminInfo() {}

}