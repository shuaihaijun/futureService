package com.future.common.enums;


/**
 * 请求体附带参数key枚举
 *
 * @author Admin
 * @version: 1.0
 */
public enum RequestKey {

    /**
     * 请求参数
     */
    para,
    /**
     * 请求来源
     */
    source,
    /**
     * 设备信息,如手机系统、系统版本、唯一标识等
     */
    device,
    /**
     * 业务数据返回结果
     */
    data,
    /**
     * 提示信息
     */
    msg,
    /**
     * 1(参数失败),2(认证失败),3(数据集为空),4(service不存在),5(方法不存在 ),6 (执行异常), 200（成功）
     */
    code,
    /**
     * 分页信息
     */
    page,
    /**
     * client端版本号
     */
    version,
    /**
     * 签名
     */
    sign,
    /**
     * 时间戳
     */
    timestamp,
    /**
     * 密钥KEY
     */
    secret,
    /**
     * 等录凭证
     */
    admintoken,
    /**
     * 登录人信息
     */
    loginAdminInfo

}