package com.future.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.future.common.constants.CommonConstant;
import com.future.common.constants.GlobalConstant;
import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderFollowInfo;
import com.future.entity.order.FuOrderSignal;
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
     * 将jsonObject转换成Fuorderinfo
     * @param orderInfo
     * @return
     */
    public static FuOrderFollowInfo convertOrderInfo(JSONObject orderInfo){

        if(ObjectUtils.isEmpty(orderInfo)){
            return null;
        }

        FuOrderFollowInfo fuOrderInfo=new FuOrderFollowInfo();

        fuOrderInfo.setUserMtAccId(orderInfo.getString("login"));
        fuOrderInfo.setOrderId(orderInfo.getString("order"));
        fuOrderInfo.setOrderType(orderInfo.getInteger("cmd"));

        fuOrderInfo.setCreateDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")));
        fuOrderInfo.setOrderCloseDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")));
        fuOrderInfo.setOrderOpenDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("open_time")));
        fuOrderInfo.setOrderOpenPrice(new BigDecimal(orderInfo.getDouble("open_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderClosePrice(new BigDecimal(orderInfo.getDouble("close_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderExpiration(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("expiration")));

        fuOrderInfo.setOrderLots(new BigDecimal(orderInfo.getDouble("volume")*0.01).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderSymbol(orderInfo.getString("symbol"));
        fuOrderInfo.setComment(orderInfo.getString("comment"));

        /*fuOrderInfo.setOrderTradeOperation(orderInfo.getType().val);*/
        fuOrderInfo.setOrderProfit(new BigDecimal(orderInfo.getDouble("profit")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderStoploss(new BigDecimal(orderInfo.getDouble("stoploss")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderTakeprofit(new BigDecimal(orderInfo.getDouble("takeprofit")).setScale(6,BigDecimal.ROUND_HALF_UP));

        fuOrderInfo.setOrderMagic(new BigDecimal(orderInfo.getInteger("magic")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderSwap(new BigDecimal(orderInfo.getDouble("storage")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderInfo.setOrderCommission(new BigDecimal(orderInfo.getDouble("commission")).setScale(6,BigDecimal.ROUND_HALF_UP));

        return fuOrderInfo;
    }

    /**
     * 将jsonObject转换成Fuorderinfo
     * @param orderInfos
     * @return
     */
    public static List<FuOrderFollowInfo> convertOrderInfos(JSONArray orderInfos){

        if(ObjectUtils.isEmpty(orderInfos)||orderInfos.size()==0){
            return null;
        }
        List<FuOrderFollowInfo> fuOrderFollowInfos=new ArrayList<>();
        for(int i=0;i<orderInfos.size();i++){
            JSONObject orderInfo=orderInfos.getJSONObject(i);
            FuOrderFollowInfo fuOrderInfo=new FuOrderFollowInfo();

            fuOrderInfo.setUserMtAccId(orderInfo.getString("login"));
            fuOrderInfo.setOrderId(orderInfo.getString("order"));
            fuOrderInfo.setOrderType(orderInfo.getInteger("cmd"));

            fuOrderInfo.setCreateDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")));
            fuOrderInfo.setOrderCloseDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")));
            fuOrderInfo.setOrderOpenDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("open_time")));
            fuOrderInfo.setOrderOpenPrice(new BigDecimal(orderInfo.getDouble("open_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderClosePrice(new BigDecimal(orderInfo.getDouble("close_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderExpiration(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("expiration")));

            fuOrderInfo.setOrderLots(new BigDecimal(orderInfo.getDouble("volume")*0.01).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderSymbol(orderInfo.getString("symbol"));
            fuOrderInfo.setComment(orderInfo.getString("comment"));

            /*fuOrderInfo.setOrderTradeOperation(orderInfo.getType().val);*/
            fuOrderInfo.setOrderProfit(new BigDecimal(orderInfo.getDouble("profit")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderStoploss(new BigDecimal(orderInfo.getDouble("stoploss")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderTakeprofit(new BigDecimal(orderInfo.getDouble("takeprofit")).setScale(6,BigDecimal.ROUND_HALF_UP));

            fuOrderInfo.setOrderMagic(new BigDecimal(orderInfo.getInteger("magic")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderSwap(new BigDecimal(orderInfo.getDouble("storage")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderInfo.setOrderCommission(new BigDecimal(orderInfo.getDouble("commission")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderFollowInfos.add(fuOrderInfo);
        }

        return fuOrderFollowInfos;
    }

    /**
     * 将fuOrderInfo转换成FuOrderCustomer
     * @param orderInfos
     * @return
     */
    public static List<FuOrderCustomer> convertOrderCustomers(JSONArray orderInfos){

        if(ObjectUtils.isEmpty(orderInfos)||orderInfos.size()==0){
            return null;
        }
        List<FuOrderCustomer> customers=new ArrayList<>();
        for(int i=0;i<orderInfos.size();i++) {
            JSONObject orderInfo = orderInfos.getJSONObject(i);

            FuOrderCustomer fuOrderCustomer=new FuOrderCustomer();
            fuOrderCustomer.setModifyDate(new Date());

            /*订单信息*/
            fuOrderCustomer.setMtAccId(orderInfo.getString("login"));
            fuOrderCustomer.setOrderId(orderInfo.getString("order"));
            fuOrderCustomer.setOrderType(orderInfo.getInteger("cmd"));
            fuOrderCustomer.setOrderLots(new BigDecimal(orderInfo.getDouble("volume")*0.01).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderCustomer.setOrderSymbol(orderInfo.getString("symbol"));

            /*时间 价格*/
            /*fuOrderCustomer.setCreateDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")));*/
            fuOrderCustomer.setOrderCloseDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")));
            fuOrderCustomer.setOrderOpenDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("open_time")));
            fuOrderCustomer.setOrderOpenPrice(new BigDecimal(orderInfo.getDouble("open_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderCustomer.setOrderClosePrice(new BigDecimal(orderInfo.getDouble("close_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderCustomer.setOrderExpiration(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("expiration")));

            /*交易 收益*/

            fuOrderCustomer.setOrderProfit(new BigDecimal(orderInfo.getDouble("profit")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderCustomer.setOrderStoploss(new BigDecimal(orderInfo.getDouble("stoploss")).setScale(6,BigDecimal.ROUND_HALF_UP));
            /*fuOrderCustomer.setOrderTakeprofit(new BigDecimal(orderInfo.getDouble("takeprofit")).setScale(6,BigDecimal.ROUND_HALF_UP));*/
            fuOrderCustomer.setOrderMagic(new BigDecimal(orderInfo.getInteger("magic")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderCustomer.setOrderSwap(new BigDecimal(orderInfo.getDouble("storage")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderCustomer.setOrderCommission(new BigDecimal(orderInfo.getDouble("commission")).setScale(6,BigDecimal.ROUND_HALF_UP));

            /*用户 账户信息*/
            /*fuOrderCustomer.setUserId(fuOrderInfo.getUserId());
            fuOrderCustomer.setMtServerId(fuOrderInfo.getUserServerId());
            fuOrderCustomer.setMtAccId(fuOrderInfo.getUserMtAccId());*/
            fuOrderCustomer.setComment(orderInfo.getString("comment"));

            /*判断是否是社区跟单订单*/
            if(checkMagic(orderInfo.getString("comment"),orderInfo.getInteger("magic"))){
                fuOrderCustomer.setOrderFlag(CommonConstant.COMMON_YES);
            }else {
                fuOrderCustomer.setOrderFlag(CommonConstant.COMMON_NO);
            }

            customers.add(fuOrderCustomer);
        }
        return customers;
    }

    /**
     * 将fuOrderInfo转换成FuOrderCustomer
     * @param orderInfo
     * @return
     */
    public static FuOrderCustomer convertOrderCustomer(JSONObject orderInfo){

        if(ObjectUtils.isEmpty(orderInfo)){
            return null;
        }
        FuOrderCustomer fuOrderCustomer=new FuOrderCustomer();
        fuOrderCustomer.setModifyDate(new Date());
        fuOrderCustomer.setCreateDate(new Date());

        /*订单信息*/
        fuOrderCustomer.setMtAccId(orderInfo.getString("login"));
        fuOrderCustomer.setOrderId(orderInfo.getString("order"));
        fuOrderCustomer.setOrderType(orderInfo.getInteger("cmd"));
        fuOrderCustomer.setOrderLots(new BigDecimal(orderInfo.getDouble("volume")*0.01).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderSymbol(orderInfo.getString("symbol"));

        /*时间 价格*/
        fuOrderCustomer.setCreateDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")));
        fuOrderCustomer.setOrderCloseDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")));
        fuOrderCustomer.setOrderOpenDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("open_time")));
        fuOrderCustomer.setOrderOpenPrice(new BigDecimal(orderInfo.getDouble("open_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderClosePrice(new BigDecimal(orderInfo.getDouble("close_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderExpiration(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("expiration")));

        /*交易 收益*/

        fuOrderCustomer.setOrderProfit(new BigDecimal(orderInfo.getDouble("profit")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderStoploss(new BigDecimal(orderInfo.getDouble("stoploss")).setScale(6,BigDecimal.ROUND_HALF_UP));
        /*fuOrderCustomer.setOrderTakeprofit(new BigDecimal(orderInfo.getDouble("takeprofit")).setScale(6,BigDecimal.ROUND_HALF_UP));*/
        fuOrderCustomer.setOrderMagic(new BigDecimal(orderInfo.getInteger("magic")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderSwap(new BigDecimal(orderInfo.getDouble("storage")).setScale(6,BigDecimal.ROUND_HALF_UP));
        fuOrderCustomer.setOrderCommission(new BigDecimal(orderInfo.getDouble("commission")).setScale(6,BigDecimal.ROUND_HALF_UP));

        /*用户 账户信息*/
        /*fuOrderCustomer.setUserId(fuOrderInfo.getUserId());
        fuOrderCustomer.setMtServerId(fuOrderInfo.getUserServerId());
        fuOrderCustomer.setMtAccId(fuOrderInfo.getUserMtAccId());*/
        fuOrderCustomer.setComment(orderInfo.getString("comment"));

        /*判断是否是社区跟单订单*/
        if(checkMagic(orderInfo.getString("comment"),orderInfo.getInteger("magic"))){
            fuOrderCustomer.setOrderFlag(CommonConstant.COMMON_YES);
        }else {
            fuOrderCustomer.setOrderFlag(CommonConstant.COMMON_NO);
        }

        return fuOrderCustomer;
    }

    /**
     * 将FuOrderSignal转换成FuOrderCustomer
     * @param orderInfo
     * @return
     */
    public static FuOrderCustomer convertOrderCustomer(FuOrderSignal orderInfo){

        if(ObjectUtils.isEmpty(orderInfo)){
            return null;
        }
        FuOrderCustomer fuOrderCustomer=new FuOrderCustomer();
        fuOrderCustomer.setModifyDate(new Date());
        fuOrderCustomer.setCreateDate(new Date());

        /*订单信息*/
        fuOrderCustomer.setMtAccId(orderInfo.getMtAccId());
        fuOrderCustomer.setOrderId(orderInfo.getOrderId());
        fuOrderCustomer.setOrderType(orderInfo.getOrderType());
        fuOrderCustomer.setOrderLots(orderInfo.getOrderLots());
        fuOrderCustomer.setOrderSymbol(orderInfo.getOrderSymbol());

        /*时间 价格*/
        fuOrderCustomer.setCreateDate(orderInfo.getCreateDate());
        fuOrderCustomer.setOrderCloseDate(orderInfo.getOrderCloseDate());
        fuOrderCustomer.setOrderOpenDate(orderInfo.getOrderOpenDate());
        fuOrderCustomer.setOrderOpenPrice(orderInfo.getOrderOpenPrice());
        fuOrderCustomer.setOrderClosePrice(orderInfo.getOrderClosePrice());
        fuOrderCustomer.setOrderExpiration(orderInfo.getOrderExpiration());

        /*交易 收益*/

        fuOrderCustomer.setOrderProfit(orderInfo.getOrderProfit());
        fuOrderCustomer.setOrderStoploss(orderInfo.getOrderStoploss());
        /*fuOrderCustomer.setOrderTakeprofit(new BigDecimal(orderInfo.getDouble("takeprofit")).setScale(6,BigDecimal.ROUND_HALF_UP));*/
        fuOrderCustomer.setOrderMagic(orderInfo.getOrderMagic());
        fuOrderCustomer.setOrderSwap(orderInfo.getOrderSwap());
        fuOrderCustomer.setOrderCommission(orderInfo.getOrderCommission());

        /*用户 账户信息*/
        if(orderInfo.getUserId()!=null){
            fuOrderCustomer.setUserId(orderInfo.getUserId());
        }
        if(orderInfo.getMtServerName()!=null){
            fuOrderCustomer.setMtServerName(orderInfo.getMtServerName());
        }
        fuOrderCustomer.setOrderState(orderInfo.getOrderState());
        fuOrderCustomer.setComment(orderInfo.getComment());

        /*判断是否是社区跟单订单*/
        if(checkMagic(orderInfo.getComment(),orderInfo.getOrderMagic().intValue())){
            fuOrderCustomer.setOrderFlag(CommonConstant.COMMON_YES);
        }else {
            fuOrderCustomer.setOrderFlag(CommonConstant.COMMON_NO);
        }

        return fuOrderCustomer;
    }

    /**
     * 将fuOrderInfo转换成FuOrderSignal
     * @param orderInfos
     * @return
     */
    public static List<FuOrderSignal> convertOrderSignals(JSONArray orderInfos){

        if(ObjectUtils.isEmpty(orderInfos)||orderInfos.size()==0){
            return null;
        }
        List<FuOrderSignal> signals=new ArrayList<>();
        for(int i=0;i<orderInfos.size();i++) {
            JSONObject orderInfo = orderInfos.getJSONObject(i);

            FuOrderSignal fuOrderSignal=new FuOrderSignal();
            fuOrderSignal.setModifyDate(new Date());
            fuOrderSignal.setCreateDate(new Date());

            /*订单信息*/
            fuOrderSignal.setMtAccId(orderInfo.getString("login"));
            fuOrderSignal.setOrderId(orderInfo.getString("order"));
            fuOrderSignal.setOrderType(orderInfo.getInteger("cmd"));
            fuOrderSignal.setOrderLots(new BigDecimal(orderInfo.getDouble("volume")*0.01).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderSignal.setOrderSymbol(orderInfo.getString("symbol"));

            /*时间 价格*/
            fuOrderSignal.setOrderCloseDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("close_time")));
            fuOrderSignal.setOrderOpenDate(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("open_time")));
            fuOrderSignal.setOrderOpenPrice(new BigDecimal(orderInfo.getDouble("open_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderSignal.setOrderClosePrice(new BigDecimal(orderInfo.getDouble("close_price")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderSignal.setOrderExpiration(DateUtil.toDataFormTimeStamp(orderInfo.getInteger("expiration")));

            /*交易 收益*/

            fuOrderSignal.setOrderProfit(new BigDecimal(orderInfo.getDouble("profit")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderSignal.setOrderStoploss(new BigDecimal(orderInfo.getDouble("stoploss")).setScale(6,BigDecimal.ROUND_HALF_UP));
            /*fuOrderSignal.setOrderTakeprofit(new BigDecimal(orderInfo.getDouble("takeprofit")).setScale(6,BigDecimal.ROUND_HALF_UP));*/
            fuOrderSignal.setOrderMagic(new BigDecimal(orderInfo.getInteger("magic")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderSignal.setOrderSwap(new BigDecimal(orderInfo.getDouble("storage")).setScale(6,BigDecimal.ROUND_HALF_UP));
            fuOrderSignal.setOrderCommission(new BigDecimal(orderInfo.getDouble("commission")).setScale(6,BigDecimal.ROUND_HALF_UP));

            fuOrderSignal.setComment(orderInfo.getString("comment"));

            signals.add(fuOrderSignal);
        }
        return signals;
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

        signal.setProjKey(apply.getProjKey());
        signal.setProjName(apply.getProjName());

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

    /**
     * 校验magic是否合规
     * @param comment
     * @param magic
     * @return
     */
    public static boolean checkMagic(String comment,int magic){
        if(StringUtils.isEmpty(comment)||magic==0){
            return false;
        }
        String[] followInfo=comment.split(":");
        try {
            int followName=Integer.parseInt(followInfo[0]);
            int orderId=Integer.parseInt(followInfo[1]);
            int newMagic=getMagic(followName,orderId);
            if(magic!=newMagic){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 根据跟随账号和信号源订单生成跟随magic
     * @param followName
     * @param orderId
     * @return
     */
    public static int getMagic(int followName,int orderId){
        int magic= GlobalConstant.ORDER_FOLLOW_MAGIC>>2|followName<<4&orderId<<3;
        return magic%1000000;
    }
}