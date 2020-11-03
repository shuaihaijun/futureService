package com.future.mapper.report;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.report.FuReportOrderSum;
import com.future.pojo.vo.report.FuReportSignalVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FuReportOrderSumMapper extends BaseMapper<FuReportOrderSum> {
    int deleteByPrimaryKey(Integer id);

    Integer insert(FuReportOrderSum record);

    int insertSelective(FuReportOrderSum record);

    FuReportOrderSum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FuReportOrderSum record);

    int updateByPrimaryKey(FuReportOrderSum record);

    List<FuReportSignalVO> querySignalOrderSumPermit(Map conditon);
}