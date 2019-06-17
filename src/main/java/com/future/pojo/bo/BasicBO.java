package com.future.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 抽取的公共属性BO类
 */
@Data
public class BasicBO {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "需要删除的id集合，英文逗号分隔")
    private String delIds;
    @ApiModelProperty(value = "查询id集合，英文逗号分隔")
    private String selectIds;
}