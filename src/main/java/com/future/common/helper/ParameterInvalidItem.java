package com.future.common.helper;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ParameterInvalidItem
 * @Description: 参数无效项
 * @author： lizhen
 * @version:1.0
 * @date： 2018/5/30 9:33
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParameterInvalidItem {

    /**
     * 参数无效项名称
     */
    private String fieldName;

    /**
     * 参数无效项说明
     */
    private String message;

}
