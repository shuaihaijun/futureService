package com.future.common.enums;




/**
 * API 全局统一返回状态码
 * 全局错误码区间：0-999
 *
 * @author Admin
 * @version: 1.0
 */
public enum GlobalResultCode implements ResultCode {

    /**
     * 成功状态码
     */
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),

    SYSTEM_INNER_ERROR(10, "系统繁忙，请稍后重试"),
    SYSTEM_CLOSE_UPGRADE(11, "系统升级中，请稍后重试"),


    REPEAT_FORM_SUBMISSION(20, "请勿重复提交"),


    PARAM_IS_INVALID(101, "参数无效"),
    PARAM_NULL_POINTER(102, "参数为空"),
    PARAM_TYPE_BIND_ERROR(103, "参数类型错误"),
    PARAM_NOT_COMPLETE(104, "参数缺失"),
    SHA256_VERIFY_ERROR(105, "参数验签失败"),
    PARAM_VERIFY_ERROR(106, "参数校验失败"),
    PARAM_CONSTRAINT_VIOLATION(107, "参数异常"),


    RESULE_DATA_NONE(201, "数据未找到"),
    DATA_IS_WRONG(202, "数据有误"),
    DATA_ALREADY_EXISTED(203, "数据已存在"),
    DATA_COVERTET_JSON_ERROR(204, "转换json格式错误"),


    SYSTEM_CLASS_NOTFOUND(301, "指定的类不存在"),
    SYSTEM_ARITHMETIC(302, "数学运算异常"),
    SYSTEM_INDEX_OUTOFBOUNDS(303, "数组下标越界"),
    DATE_FORMAT_ERROR(304, "日期格式化错误"),
    EXPORT_FILE_ERROR(305, "文件导出错误"),
    IMPORT_FILE_ERROR(306, "文件导入失败  请检查文件是否符合要求"),
    WRITE_FILE_ERROR(307, "文件写操作错误"),
    READ_FILE_ERROR(308, "文件读操作错误"),
    SERVICE_INVOCATION_EXCEPTION(309, "服务调用异常"),
    REMOTING_ERROR_NEW(310, "第三方调用异常"),


    LOGIN_AUTHENTICATION_ERROR(401, "登录认证失败"),
    LOGIN_PAST(402, "登录过期"),
    SYSTEM_ILLEGA_ACCESS(403, "用户无授权")
    ;

    /**
     * 全局异常状态码
     * 状态码规则：状态码是6位长度的字符串。示例：1 01 100
     * 1：应用标记（例如组织机构应用或者人员管理应用）
     * 01：应用下的模块（例组织机构下的获取机构数据）
     * 100：定义的业务异常
     */


    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String message;

    /**
     * 构造器
     */
    GlobalResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取状态码
     */
    @Override
    public Integer code() {
        return this.code;
    }

    /**
     * 获取提示信息
     */
    @Override
    public String message() {
        return this.message;
    }

    /**
     * 通过枚举属性名称获取提示信息
     */
    public static String getMessage(String name) {
        for (GlobalResultCode item : GlobalResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    /**
     * 通过枚举名称获取状态码
     */
    public static Integer getCode(String name) {
        for (GlobalResultCode item : GlobalResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

}