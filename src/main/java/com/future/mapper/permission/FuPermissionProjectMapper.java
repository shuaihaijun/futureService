package com.future.mapper.permission;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.permission.FuPermissionProject;
import com.future.pojo.bo.permission.FuPermissionProjectBO;
import com.future.pojo.vo.permission.FuPermissionProjectVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 工程项目信息模块Mapper接口
 *
 * @author Admin
 * @version: 1.0
 */
@Repository
public interface FuPermissionProjectMapper extends BaseMapper<FuPermissionProject> {

    /**
     * 查询全部工程项目信息
     * <p>集合key为工程项目KEY</p>
     * <p>集合value为工程项目对象</p>
     *
     * @return 全部工程项目集合
     */
    @MapKey("projKey")
    Map<Integer, FuPermissionProject> selectAllProjet();

    /**
     * 通过工程项目ID集合查询工程项目KEY集合
     *
     * @param paramList 工程项目ID集合
     * @return 工程项目KEY集合
     */
    List<Integer> selectKeyById(@Param("param") List<Integer> paramList);

    /**
     * 通过工程项目BO查询工程项目VO集合
     *
     * @param fuPermissionProjectBO 工程项目BO
     * @return 工程项目VO集合
     */
    List<FuPermissionProjectVO> selectPageList(@Param("param") FuPermissionProjectBO fuPermissionProjectBO);

}