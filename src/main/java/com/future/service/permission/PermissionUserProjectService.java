package com.future.service.permission;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.RmsResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.util.StringUtils;
import com.future.entity.permission.FuPermissionUserProject;
import com.future.mapper.permission.FuPermissionUserProjectMapper;
import com.future.pojo.bo.BasicBO;
import com.future.pojo.bo.permission.FuPermissionUserProjectBO;
import com.future.pojo.vo.permission.FuPermissionProjectVO;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户工程项目关联信息模块业务实现类
 *
 * @author Admin
 * @version: 1.0
 */
@Service
@Slf4j
public class PermissionUserProjectService extends ServiceImpl<FuPermissionUserProjectMapper, FuPermissionUserProject> {

    @Autowired
    private FuPermissionUserProjectMapper fuPermissionUserProjectMapper;
    @Autowired
    private PermissionProjectService permissionProjectService;

    /**
     * 通过用户ID查询所管理的工程项目KEY集合
     *
     * @param userId 用户ID
     * @return 工程项目KEY集合
     */
    public List<Integer> findPorjKeysByUserId(Integer userId) {
        //验证必要参数值是否为空
        if (userId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        return fuPermissionUserProjectMapper.selectPorjKeysByUserId(userId);
    }

    /**
     * 通过用户ID查询所管理的工程项目KEY集合
     *
     * @param bo 查询参数（用户ID）
     * @return 工程项目KEY集合和工程项目集合
     * @throws Exception 异常抛出
     */
    //TODO:是否保留方法
    public Map<String, Object> findByUserId(BasicBO bo) throws Exception {
        //验证参数对象是否为空
        if (bo == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer userid = bo.getId();
        //验证必要参数值是否为空
        if (userid == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }

        //获取用户所管理的工程项目KEY列表
        List<Integer> porjKeys = findPorjKeysByUserId(userid);
        //获取所有工程项目信息列表
        List<FuPermissionProjectVO> fuPermissionProjectVO = permissionProjectService.findAllList();
        Map<String, Object> result = new HashMap<>();
        result.put("project", fuPermissionProjectVO);
        result.put("containedPorjKeys", porjKeys);
        return result;
    }

    /**
     * 新增用户工程项目关联信息
     * <p>参数中仅有userId时，将会删除用户所管理的所有工程项目信息</p>
     *
     * @param fuPermissionUserProjectBO 保存的数据信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(FuPermissionUserProjectBO fuPermissionUserProjectBO) {
        //验证参数对象是否为空
        if (fuPermissionUserProjectBO == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        Integer userId = fuPermissionUserProjectBO.getUserId();
        List<Integer> projKeys = fuPermissionUserProjectBO.getProjKeys();
        //验证必要参数值是否为空
        if (userId == null) {
            throw new BusinessException(GlobalResultCode.PARAM_NOT_COMPLETE);
        }

        //通过用户ID删除所管理的工程项目数据
        delete(new EntityWrapper<FuPermissionUserProject>().eq(FuPermissionUserProject.USER_ID, userId));
        if (StringUtils.isNotEmpty(projKeys)) {
            //批量新增用户所管理的工程项目
            List<FuPermissionUserProject> param = Lists.newArrayList();
            for (Integer projKey : projKeys) {
                FuPermissionUserProject pa = new FuPermissionUserProject();
                pa.setUserId(userId);
                pa.setProjKey(projKey);
                param.add(pa);
            }
            boolean isSuccess = insertBatch(param);
            if (!isSuccess) {
                throw new BusinessException(RmsResultCode.PERMISSION_USER_PROJECT_DATA_SAVE_FAILURE);
            }
        }
    }

}