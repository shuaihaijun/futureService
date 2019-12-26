package com.future.common.util;

import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.entity.product.FuProductSignal;
import com.future.entity.product.FuProductSignalApply;
import com.jfx.strategy.OrderInfo;
import org.springframework.util.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * BEAN转化工具
 * @author haijun
 */
public class ConvertUtil {
    /**
    * 将MAP转化成BEAN
    * @param map 需要转化的MAP集合
    * @param cls 转化成的类型
    * @return
    * @author add by  haijun
    * @return Object
    *
    */
    @SuppressWarnings({ "rawtypes" })
    public static Object MapToJavaBean(HashMap map,Class cls){
        Object obj = null;
        try {
            obj = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //取出bean里的所有方法
        Method[] methods = cls.getMethods();
        for(int i=0; i<methods.length;i++){
            //取方法名
            String method = methods[i].getName();
            //取出方法的类型
             Class[] cc = methods[i].getParameterTypes();
             if (cc.length != 1) continue;

            //如果方法名没有以set开头的则退出本次for
            if(method.indexOf("set") < 0 ) continue;
            //类型
            String type = cc[0].getSimpleName();


            try {
                //转成小写
                Object value = method.substring(3,4).toLowerCase()+method.substring(4);
                //如果map里有该key
                if(map.containsKey(value)){
                    //调用其底层方法
                    setValue(type, map.get(value), i, methods, obj);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return obj;
    }

     /***
     * 调用底层方法设置值
     */
    private static void setValue(String type, Object value, int i, Method[] method, Object bean) throws Exception {
         if (value!=null && !value.equals("")){
             if(type.equals("String")){
                 //第一个参数:从中调用基础方法的对象		第二个参数:用于方法调用的参数
                method[i].invoke(bean, new Object[] {value.toString()});
             }
             else if(type.equals("int") || type.equals("Integer")) {
                method[i].invoke(bean, new Object[] { new Integer(""+value) });
             }
             else if(type.equals("long") || type.equals("Long")) {
                method[i].invoke(bean, new Object[] { new Long(""+value) });
             }else if (type.equals("BigDecimal")){
                 method[i].invoke(bean, new Object[] { new BigDecimal(""+value) });
             } else if(type.equals("boolean") || type.equals("Boolean")) {
                 //add by x@
                 if ("Integer".equals(value.getClass().getSimpleName())){;
                     if ((Integer)value == 1){
                         value="true";
                     }else{
                         value="false";
                     }
                 }
                method[i].invoke(bean, new Object[] { Boolean.valueOf(""+value) });
             }
             else if(type.equals("Date")) {
                 Date date =null;
                 //modify by x@
                 if (value.getClass().getName().equals("java.util.Date")){
                    date=(Date) value;
                 }else if ("String".equals(value.getClass().getSimpleName())){
                     String tmp = (String)value;
                     if (tmp.indexOf("T")>0){
                         tmp = tmp.replace("T", " ");
                     }
                     if(tmp.length() == 10){
                         tmp = tmp+" 00" + ":" + "00" + ":" + "00";
                     }
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     Date joindateDate = sdf.parse(tmp);
                     date = new java.sql.Timestamp((joindateDate).getTime());
                 }
                 method[i].invoke(bean, new Object[] { date });
             }
             else if(type.equals("byte[]")){
                 method[i].invoke(bean, new Object[] { new String(value+"").getBytes() });
             }
         }
     }

     /***
     * 调用底层方法设置值
     */
    public static void setValue(String type, Object value,Method method, Object bean) throws Exception {
         if (value!=null && !value.equals("")){
             if(type.equals("String")){
                 //第一个参数:从中调用基础方法的对象		第二个参数:用于方法调用的参数
                method.invoke(bean, new Object[] {value.toString()});
             }
             else if(type.equals("int") || type.equals("Integer")) {
                method.invoke(bean, new Object[] { new Integer(""+value) });
             }
             else if(type.equals("long") || type.equals("Long")) {
                method.invoke(bean, new Object[] { new Long(""+value) });
             }else if (type.equals("BigDecimal")){
                 method.invoke(bean, new Object[] { new BigDecimal(""+value) });
             } else if(type.equals("boolean") || type.equals("Boolean")) {
                 //add by x@
                 if ("Integer".equals(value.getClass().getSimpleName())){;
                     if ((Integer)value == 1){
                         value="true";
                     }else{
                         value="false";
                     }
                 }
                method.invoke(bean, new Object[] { Boolean.valueOf(""+value) });
             }
             else if(type.equals("Date")) {
                 Date date =null;
                 //modify by x@
                 if (value.getClass().getName().equals("java.util.Date")){
                    date=(Date) value;
                 }else if ("String".equals(value.getClass().getSimpleName())){
                     String tmp = (String)value;
                     if (tmp.indexOf("T")>0){
                         tmp = tmp.replace("T", " ");
                     }
                     if(tmp.length() == 10){
                         tmp = tmp+" 00" + ":" + "00" + ":" + "00";
                     }
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     Date joindateDate = sdf.parse(tmp);
                     date = new java.sql.Timestamp((joindateDate).getTime());
                 }
                 method.invoke(bean, new Object[] { date });
             }
             else if(type.equals("byte[]")){
                 method.invoke(bean, new Object[] { new String(value+"").getBytes() });
             }
         }
     }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @Deprecated
    public static Object convertMap(Class type, Map map) throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    /**
     * 将MTorderinfo转换成Fuorderinfo
     * @param mtOrders
     * @return
     */
    public static List<FuOrderFollowInfo> convertOrderInfo(Map<Long, OrderInfo> mtOrders ){

        List<FuOrderFollowInfo> orderInfos= new ArrayList<FuOrderFollowInfo>();

        if(ObjectUtils.isEmpty(mtOrders)){
            return null;
        }

        int i=0;//考虑到效率问题，只转换前1000条
        for(OrderInfo order:mtOrders.values()){
            if(++i>1000){
                break;
            }
            FuOrderFollowInfo fuOrderInfo=new FuOrderFollowInfo();
            fuOrderInfo.setCreateDate(new Date());

            fuOrderInfo.setOrderType(order.getType().val);

            fuOrderInfo.setOrderId(String.valueOf(order.ticket()));
            fuOrderInfo.setOrderCloseDate(order.getCloseTime());
            fuOrderInfo.setOrderOpenDate(order.getOpenTime());

            fuOrderInfo.setOrderLots(new BigDecimal(order.getLots()).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderOpenPrice(new BigDecimal(order.getOpenPrice()).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderClosePrice(new BigDecimal(order.getClosePrice()).setScale(6,BigDecimal.ROUND_HALF_UP));

            fuOrderInfo.setOrderSymbol(order.getSymbol());
            fuOrderInfo.setComment(order.getComment());
            fuOrderInfo.setOrderProfit(new BigDecimal(order.getProfit()).setScale(6,BigDecimal.ROUND_HALF_UP));

            fuOrderInfo.setOrderTradeOperation(order.getType().val);
            fuOrderInfo.setOrderStoploss(new BigDecimal(order.getSl()).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderTakeprofit(new BigDecimal(order.getTp()).setScale(6,BigDecimal.ROUND_HALF_UP));

            fuOrderInfo.setOrderMagic(new BigDecimal(order.getMagic()).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderExpiration(order.getExpiration());
            fuOrderInfo.setOrderSwap(new BigDecimal(order.getSwap()).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderCommission(new BigDecimal(order.getCommission()));
            orderInfos.add(fuOrderInfo);
        }

        return orderInfos;
    }

    /**
     * 将MTorderinfo转换成Fuorderinfo
     * @param orderInfo
     * @return
     */
    public static FuOrderFollowInfo convertOrderInfo(OrderInfo orderInfo){

        if(ObjectUtils.isEmpty(orderInfo)){
            return null;
        }

        FuOrderFollowInfo fuOrderInfo=new FuOrderFollowInfo();

        fuOrderInfo.setOrderId(String.valueOf(orderInfo.ticket()));
        fuOrderInfo.setCreateDate(orderInfo.getOpenTime());
        fuOrderInfo.setOrderCloseDate(orderInfo.getCloseTime());
        fuOrderInfo.setOrderOpenDate(orderInfo.getOpenTime());

        fuOrderInfo.setOrderLots(new BigDecimal(orderInfo.getLots()).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderOpenPrice(new BigDecimal(orderInfo.getOpenPrice()).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderClosePrice(new BigDecimal(orderInfo.getClosePrice()).setScale(6,BigDecimal.ROUND_HALF_UP));

        fuOrderInfo.setOrderSymbol(orderInfo.getSymbol());
        fuOrderInfo.setComment(orderInfo.getComment());
        fuOrderInfo.setOrderProfit(new BigDecimal(orderInfo.getProfit()).setScale(6,BigDecimal.ROUND_HALF_UP));

        fuOrderInfo.setOrderTradeOperation(orderInfo.getType().val);
        fuOrderInfo.setOrderStoploss(new BigDecimal(orderInfo.getSl()).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderTakeprofit(new BigDecimal(orderInfo.getTp()).setScale(6,BigDecimal.ROUND_HALF_UP));

        fuOrderInfo.setOrderMagic(new BigDecimal(orderInfo.getMagic()).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderSwap(new BigDecimal(orderInfo.getMagic()).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderCommission(new BigDecimal(orderInfo.getCommission()));

        return fuOrderInfo;
    }


    /**
     * 将fuOrderInfo转换成FuOrderCustomer
     * @param fuOrderInfo
     * @return
     */
    public static FuOrderCustomer convertOrderCustomer(FuOrderFollowInfo fuOrderInfo){


        if(ObjectUtils.isEmpty(fuOrderInfo)){
            return null;
        }

        FuOrderCustomer fuOrderCustomer=new FuOrderCustomer();
        fuOrderCustomer.setModifyDate(new Date());
        fuOrderCustomer.setCreateDate(new Date());

        /*订单信息*/
        fuOrderCustomer.setOrderId(fuOrderInfo.getOrderId());
        fuOrderCustomer.setOrderLots(fuOrderInfo.getOrderLots().setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderType(fuOrderInfo.getOrderType());
//        fuOrderCustomer.setOrderState(fuOrderInfo.getOrderState());

        /*时间 价格*/
        fuOrderCustomer.setCreateDate(fuOrderInfo.getCreateDate());
        fuOrderCustomer.setOrderCloseDate(fuOrderInfo.getOrderCloseDate());
        fuOrderCustomer.setOrderClosePrice(fuOrderInfo.getOrderClosePrice().setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderOpenDate(fuOrderInfo.getOrderOpenDate());
        fuOrderCustomer.setOrderOpenPrice(fuOrderInfo.getOrderOpenPrice().setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderExpiration(fuOrderInfo.getOrderExpiration());

        /*交易 收益*/
        fuOrderCustomer.setOrderSymbol(fuOrderInfo.getOrderSymbol());
        fuOrderCustomer.setComment(fuOrderInfo.getComment());
        fuOrderCustomer.setOrderProfit(fuOrderInfo.getOrderProfit().setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderStoploss(fuOrderInfo.getOrderStoploss().setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderMagic(fuOrderInfo.getOrderMagic().setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderSwap(fuOrderInfo.getOrderSwap().setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderCommission(fuOrderInfo.getOrderCommission());

        /*用户 账户信息*/
        fuOrderCustomer.setUserId(fuOrderInfo.getUserId());
        fuOrderCustomer.setMtServerId(fuOrderInfo.getUserServerId());
        fuOrderCustomer.setMtAccId(fuOrderInfo.getUserMtAccId());


        return fuOrderCustomer;
    }

    /**
     * 将申请信息转换为信号源
     * @param apply
     * @return
     */
    public static FuProductSignal convertSignal(FuProductSignalApply apply){
        FuProductSignal signal=new FuProductSignal();

        signal.setUserId(apply.getUserId());

        signal.setSignalName(apply.getSignalName());
        signal.setSignalTem(apply.getSignalTem());
        signal.setSignalDesc(apply.getSignalDesc());
        signal.setSignalCurrency(apply.getSignalCurrency());

        signal.setMonthlyAverageIncome(apply.getMonthlyAverageIncome());
        signal.setHistoryWithdraw(apply.getHistoryWithdraw());
        signal.setApplyDate(apply.getApplyDate());

        signal.setEmail(apply.getEmail());
        signal.setPhone(apply.getPhone());
        signal.setQqNumber(apply.getQqNumber());

        signal.setServerName(apply.getServerName());
        signal.setMtAccId(apply.getMtAccId());
        signal.setMtPasswordTrade(apply.getMtPasswordTrade());
        signal.setMtPasswordWatch(apply.getMtPasswordWatch());

        signal.setMinimum(apply.getMinimum());
        signal.setAnnualizedExpectedReturn(apply.getAnnualizedExpectedReturn());
        signal.setHistoricalReturn(apply.getHistoricalReturn());
        signal.setSuggestCycle(apply.getSuggestCycle());

        return signal;
    }
}