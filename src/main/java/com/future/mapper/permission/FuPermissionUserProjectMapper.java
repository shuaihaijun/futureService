package com.future.mapper.permission;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.future.entity.permission.FuPermissionUserProject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户工程项目关联信息模块Mapper接口
 *
 * @author Admin
 * @version: 1.0
 */
@Repository
public interface FuPermissionUserProjectMapper extends BaseMapper<FuPermissionUserProject> {

    /**
     * 通过用户ID查询所管理的工程项目KEY
     *
     * @param userId 用户ID
     * @return 工程项目KEY集合
     */
    List<Integer> selectPorjKeysByUserId(@Param("userId") Integer userId);

}