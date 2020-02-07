package com.future.service.account;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.future.common.constants.CommissionConstant;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.GlobalConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.exception.BusinessException;
import com.future.common.exception.DataConflictException;
import com.future.common.exception.ParameterInvalidException;
import com.future.common.helper.PageInfoHelper;
import com.future.common.util.BeanUtil;
import com.future.common.util.StringUtils;
import com.future.entity.account.FuAccountCommission;
import com.future.entity.account.FuAccountInfo;
import com.future.entity.account.FuAccountWithdraw;
import com.future.entity.account.FuAccountWithdrawApply;
import com.future.entity.com.FuComAgent;
import com.future.entity.user.FuUser;
import com.future.entity.user.FuUserBank;
import com.future.mapper.account.FuAccountWithdrawApplyMapper;
import com.future.mapper.account.FuAccountWithdrawMapper;
import com.future.service.user.AdminService;
import com.future.service.user.FuUserBankService;
import com.future.service.user.UserCommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FuAccountWithdrawService extends ServiceImpl<FuAccountWithdrawMapper, FuAccountWithdraw> {

    Logger log= LoggerFactory.getLogger(FuAccountWithdrawService.class);
    @Autowired
    AdminService adminService;
    @Autowired
    FuUserBankService fuUserBankService;
    @Autowired
    FuAccountInfoService fuAccountInfoService;
    @Autowired
    FuAccountCommissionService fuAccountCommissionService;
    @Autowired
    UserCommonService userCommonService;
    @Autowired
    FuAccountWithdrawMapper fuAccountWithdrawMapper;
    @Autowired
    FuAccountWithdrawApplyMapper fuAccountWithdrawApplyMapper;

    /**
     * 根据条件查询佣金提取申请信息
     * @param queryCondition
     * @param helper
     * @return
     */
    public Page<FuAccountWithdrawApply> commissonWithdrawApplyQuery(Map queryCondition, PageInfoHelper helper){
        /*判断查询条件*/
        if(queryCondition == null||queryCondition.get("operUserId")==null){
            log.error("查询申请列表,获取参数为空！");
            throw new ParameterInvalidException("查询申请列表,获取参数为空！");
        }
        /*判断权限*/
        String operUserId=String.valueOf(queryCondition.get("operUserId"));
        if(StringUtils.isEmpty(operUserId)){
            log.error("查询申请列表,操作人信息为空！");
            throw new ParameterInvalidException("查询申请列表,操作人信息为空！");
        }
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operUserId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operUserProj);
        if(operUserProj==null){
            log.error("查询申请列表,用户权限有误！");
            throw new ParameterInvalidException("查询申请列表,用户权限有误！");
        }

        Page<FuAccountWithdrawApply> applies;
        if(helper==null){
            helper=new PageInfoHelper();
        }
        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
            applies=PageHelper.startPage(helper.getPageNo(),helper.getPageSize(),"id desc");
            fuAccountWithdrawApplyMapper.selectByMap(queryCondition);
            return applies;
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            queryCondition.put("projKey",operUserProj);
            return findWithdrawApplyByProjCondtion(queryCondition,helper);
        }else {
            /*普通用户查找*/
            queryCondition.put("userId",operUserId);
            applies=PageHelper.startPage(helper.getPageNo(),helper.getPageSize(),"id desc");
            fuAccountWithdrawApplyMapper.selectByMap(queryCondition);
            return applies;
        }
    }

    /**
     * 根据proj获取申请信息
     * @param queryCondition
     * @param helper
     */
    public Page<FuAccountWithdrawApply> findWithdrawApplyByProjCondtion(Map queryCondition,PageInfoHelper helper){
        /*判断查询条件*/
        if(queryCondition == null||queryCondition.get("projKey")==null){
            log.error("查询申请列表,获取参数为空！");
            throw new ParameterInvalidException("查询申请列表,获取参数为空！");
        }
        if(helper==null){
            helper=new PageInfoHelper();
        }
        Page<FuAccountWithdrawApply> userPage= PageHelper.startPage(helper.getPageNo(),helper.getPageSize());
        fuAccountWithdrawApplyMapper.findWithdrawApplyByProjCondtion(queryCondition);
        return userPage;
    }

    /**
     * 佣金提取
     * @param userId
     * @param accountId
     * @param operId
     * @param commissionMoney
     * @return
     */
    public Boolean accountCommissonWithdraw(Integer userId, Integer accountId,Integer operId,BigDecimal commissionMoney){
        if(userId==null||accountId==null||operId==null
                ||userId==0||accountId==0||operId==0){
            log.error("佣金提取,传入数据为空！");
            throw new BusinessException("佣金提取,传入数据为空！");
        }

        if(commissionMoney==null||commissionMoney.compareTo(new BigDecimal(0))==0){
            log.error("佣金提取,传入金额为空！");
            throw new BusinessException("佣金提取,传入金额为空！");
        }

        FuAccountCommission commission=fuAccountCommissionService.selectOne(new EntityWrapper<FuAccountCommission>().eq(FuAccountCommission.USER_ID,userId)
                .and().eq(FuAccountCommission.ACCOUNT_ID,accountId));
        if(commission==null||commission.getId()==null){
            log.error("佣金提取,根據用户查询账户为空！");
            throw new BusinessException("佣金提取,根據用户查询账户为空！");
        }
        /*判断余额是否够用*/
        if(commissionMoney.compareTo(commission.getCommissionMoney())>0){
            log.error("佣金提取,提取额度超过剩余余额！");
            throw new BusinessException("佣金提取,提取额度超过剩余余额！");
        }

        FuUser operUser=adminService.selectById(operId);
        if(operUser==null||operUser.getId()==null){
            log.error("佣金提取,根據用户查询操作员为空！");
            throw new BusinessException("佣金提取,根據用户查询操作员为空！");
        }
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        Integer operUserProj=userCommonService.getUserProjKey(operId);
        Boolean isProjAdmin=userCommonService.isAdministrator(operId,operUserProj);
        if(operUserProj==null){
            log.error("查询用户列表,用户权限有误！");
            throw new ParameterInvalidException("查询用户列表,用户权限有误！");
        }
        /*判断权限*/
        if(isProjAdmin&&operUserProj==0){
            /*超管查询*/
        }else if(isProjAdmin){
            /*资源组管理员查找*/
            Integer userProj=userCommonService.getUserProjKey(userId);
            if(!operUserProj.equals(userProj)){
                log.error("无权限查看该用户数据，请检查用户ID！");
                throw new ParameterInvalidException("无权限查看该用户数据，请检查用户ID！");
            }
        }else {
            /*普通用户查找*/
            log.error("佣金提取,该用户无提取佣金权限！");
            throw new BusinessException("佣金提取,该用户无提取佣金权限！");
        }

        FuAccountWithdraw withdraw=new FuAccountWithdraw();
        withdraw.setUserId(userId);
        withdraw.setAccountId(commission.getAccountId());
        withdraw.setWithdrawType(CommissionConstant.COMMISSION_WITHDRAW_TYPE_COMMISSION);
        withdraw.setWithdrawAmount(commissionMoney);

        withdraw.setAccountBefore(commission.getCommissionMoney());
        withdraw.setAccountAfter(commission.getCommissionMoney().subtract(commissionMoney));
        withdraw.setWithdrawState(CommissionConstant.COMMISSION_STATE_SUCCESS);
        withdraw.setComment(operId.toString());

        commission.setModifyDate(new Date());
        commission.setCommissionPaid(commission.getCommissionPaid().add(commissionMoney));
        commission.setCommissionMoney(commission.getCommissionMoney().subtract(commissionMoney));

        fuAccountWithdrawMapper.insertSelective(withdraw);
        fuAccountCommissionService.updateById(commission);

        return true;
    }

    /**
     * 佣金账户 佣金提取申请保存
     * @param apply
     * @return
     */
    public boolean commissonWithdrawApplySave(FuAccountWithdrawApply apply){

        if(apply==null){
            log.error("佣金提取申请保存,参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        if(apply.getUserId()==null||apply.getUserId()==0
                ||apply.getApplyUserId()==null||apply.getApplyUserId()==0){
            log.error("佣金提取申请保存,参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        if(apply.getWithdrawAmount()==null||apply.getWithdrawAmount().compareTo(new BigDecimal(0))==0){
            log.error("佣金提取申请保存,提取金额为空！");
            throw new DataConflictException("佣金提取申请保存,提取金额为空！");
        }

        /*用户信息*/
        FuUser fuUser=adminService.selectById(apply.getUserId());
        if(fuUser==null||fuUser.getId()==0){
            log.error("佣金提取申请保存,查询用户信息为空！");
            throw new DataConflictException("佣金提取申请保存,查询用户信息为空！");
        }
        apply.setUsername(fuUser.getUsername());
        apply.setRefName(fuUser.getRefName());

        /*银行卡信息*/
        FuUserBank bank=fuUserBankService.getBankByUserId(null,apply.getUserId());
        if(bank==null||bank.getId()==0){
            log.error("佣金提取申请保存,查询用户银行卡信息为空！");
            throw new DataConflictException("佣金提取申请保存,查询用户银行卡信息为空！");
        }
        apply.setBankName(bank.getBankName());
        apply.setBankCode(bank.getCode());
        apply.setHostname(bank.getHostName());

        /*账户id*/
        FuAccountInfo accountInfo=fuAccountInfoService.findByUserId(apply.getUserId());
        if(accountInfo==null||accountInfo.getId()==0){
            log.error("佣金提取申请保存,查询用户账户信息为空！");
            throw new DataConflictException("佣金提取申请保存,查询用户账户信息为空！");
        }
        apply.setAccountId(accountInfo.getId());

        apply.setApplyState(CommonConstant.APPLY_STATE_SAVE);
        fuAccountWithdrawApplyMapper.insertSelective(apply);
        return true;
    }

    /**
     * 佣金账户 佣金提取申请提交
     * @param apply
     * @return
     */
    public boolean commissonWithdrawApplySubmit(FuAccountWithdrawApply apply){

        if(apply==null|| apply.getId()==0){
            log.error("佣金提取申请提交,参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        FuAccountWithdrawApply oldApply=fuAccountWithdrawApplyMapper.selectByPrimaryKey(apply.getId());
        if(oldApply==null){
            log.error("佣金提取申请提交,根据参数查询数据为空！");
            throw new DataConflictException("佣金提取申请提交,根据参数查询数据为空！");
        }

        BeanUtil.copyProperties(apply,oldApply);

        oldApply.setApplyDate(new Date());
        oldApply.setApplyState(CommonConstant.APPLY_STATE_CHECK);

        fuAccountWithdrawApplyMapper.updateByPrimaryKey(oldApply);

        return true;
    }

    /**
     * 佣金账户 佣金提取申请审核
     * @param checkMap
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean commissonWithdrawApplyCheck(Map checkMap){

        if(checkMap==null||checkMap.get("id")==null||checkMap.get("oper")==null){
            log.error("佣金提取申请审核,参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }

        String applyId=String.valueOf(checkMap.get("id"));
        String oper=String.valueOf(checkMap.get("oper"));
        String checkDesc=String.valueOf(checkMap.get("checkDesc"));
        String operUserId=String.valueOf(checkMap.get("operUserId"));
        if(StringUtils.isEmpty(applyId)||StringUtils.isEmpty(oper)){
            log.error("佣金提取申请审核,参数为空！");
            throw new DataConflictException(GlobalResultCode.PARAM_NULL_POINTER);
        }
        if(StringUtils.isEmpty(operUserId)){
            log.error("佣金提取申请审核,操作人为空！");
            throw new DataConflictException("佣金提取申请审核,操作人为空！");
        }

        FuAccountWithdrawApply apply=fuAccountWithdrawApplyMapper.selectByPrimaryKey(Integer.parseInt(applyId));
        if(apply==null){
            log.error("佣金提取申请审核,查询申请数据为空！");
            throw new DataConflictException("佣金提取申请审核,查询申请数据为空！");
        }

        //判断权限
        //获取配置文件中超级管理员，判断当前登录用户是否为超级管理员,true为超管
        Integer operUserProj=userCommonService.getUserProjKey(Integer.parseInt(operUserId));
        Boolean isProjAdmin=userCommonService.isAdministrator(Integer.parseInt(operUserId),operUserProj);
        if(operUserProj==null){
            log.error("佣金提取申请审核,用户权限有误！");
            throw new ParameterInvalidException("佣金提取申请审核,用户权限有误！");
        }
        /*判断权限*/
        if(isProjAdmin&&operUserProj==0){
            /*超管*/
        }else if(isProjAdmin){
            /*资源组管理员*/
            Integer userProj=userCommonService.getUserProjKey(apply.getUserId());
            if(!operUserProj.equals(userProj)){
                log.error("无权限操作该用户数据，请检查用户ID！");
                throw new ParameterInvalidException("无权限操作该用户数据，请检查用户ID！");
            }
        }else {
            /*普通用户*/
            log.error("佣金提取申请审核,无权限操作该用户数据！");
            throw new BusinessException("佣金提取申请审核,无权限操作该用户数据！");
        }

        if(Integer.parseInt(oper)==CommonConstant.CHECK_YES){
            //审核通过
            //1、插入流水表 2、变更佣金表
            accountCommissonWithdraw(apply.getUserId(),apply.getAccountId(),Integer.parseInt(operUserId),apply.getWithdrawAmount());
            //3、修改申请表状态
            apply.setApplyState(CommonConstant.APPLY_STATE_DONE);
        }else {
            //审核未通过
            //1、修改申请表状态
            apply.setApplyState(CommonConstant.APPLY_STATE_FAIL);
        }
        apply.setCheckDate(new Date());
        apply.setCheckDesc(checkDesc);
        fuAccountWithdrawApplyMapper.updateByPrimaryKeySelective(apply);

        return true;
    }
}
