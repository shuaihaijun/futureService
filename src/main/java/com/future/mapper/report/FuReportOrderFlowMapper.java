package com.future.mapper.report;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.report.FuReportOrderFlow;
import com.future.pojo.bo.report.FuReportOrderFlowBo;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface FuReportOrderFlowMapper extends BaseMapper<FuReportOrderFlow> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuReportOrderFlow record);

    int insertSelective(FuReportOrderFlow record);

    FuReportOrderFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuReportOrderFlow record);

    int updateByPrimaryKey(FuReportOrderFlow record);

    FuReportOrderFlowBo getOrderFlowDaily(Map condition);

    FuReportOrderFlow getOrderSumGroupBetween(Map condition);
}