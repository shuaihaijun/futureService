package com.future.util;

import com.future.entity.order.FuOrderCustomer;
import com.future.entity.order.FuOrderInfo;
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
    public static List<FuOrderInfo> convertOrderInfo(Map<Long, OrderInfo> mtOrders ){

        List<FuOrderInfo> orderInfos= new ArrayList<FuOrderInfo>();

        if(ObjectUtils.isEmpty(mtOrders)){
            return null;
        }

        int i=0;//考虑到效率问题，只转换前1000条
        for(OrderInfo order:mtOrders.values()){
            if(++i>1000){
                break;
            }
            FuOrderInfo fuOrderInfo=new FuOrderInfo();

            fuOrderInfo.setOrderId(String.valueOf(order.ticket()));
            fuOrderInfo.setCreateDate(order.getOpenTime());
            fuOrderInfo.setOrderCloseDate(order.getCloseTime());
            fuOrderInfo.setOrderOpenDate(order.getOpenTime());

            fuOrderInfo.setOrderLots(new BigDecimal(order.getLots()));
            fuOrderInfo.setOrderOpenPrice(new BigDecimal(order.getOpenPrice()));
            fuOrderInfo.setOrderClosePrice(new BigDecimal(order.getClosePrice()));

            fuOrderInfo.setOrderSymbol(order.getSymbol());
            fuOrderInfo.setComment(order.getComment());
            fuOrderInfo.setOrderProfit(new BigDecimal(order.getProfit()));

            fuOrderInfo.setOrderTradeOperation(order.getType().val);
            fuOrderInfo.setOrderStoploss(new BigDecimal(order.getSl()));
            fuOrderInfo.setOrderTakeprofit(new BigDecimal(order.getTp()));

            fuOrderInfo.setOrderMagic(new BigDecimal(order.getMagic()));
            fuOrderInfo.setOrderSwap(new BigDecimal(order.getMagic()));
            fuOrderInfo.setOrderCommission(new BigDecimal(order.getCommission()));

            orderInfos.add(fuOrderInfo);
        }

        return orderInfos;
    }


    /**
     * 将fuOrderInfo转换成FuOrderCustomer
     * @param fuOrderInfo
     * @return
     */
    public static FuOrderCustomer convertOrderCustomer(FuOrderInfo fuOrderInfo){


        if(ObjectUtils.isEmpty(fuOrderInfo)){
            return null;
        }

        FuOrderCustomer fuOrderCustomer=new FuOrderCustomer();

        /*订单信息*/
        fuOrderCustomer.setOrderId(fuOrderInfo.getOrderId());
        fuOrderCustomer.setOrderLots(fuOrderInfo.getOrderLots());
        fuOrderCustomer.setOrderType(fuOrderInfo.getOrderType());
        fuOrderCustomer.setOrderState(fuOrderInfo.getOrderState());

        /*时间 价格*/
        fuOrderCustomer.setCreateDate(fuOrderInfo.getCreateDate());
        fuOrderCustomer.setOrderCloseDate(fuOrderInfo.getOrderCloseDate());
        fuOrderCustomer.setOrderClosePrice(fuOrderInfo.getOrderClosePrice());
        fuOrderCustomer.setOrderOpenDate(fuOrderInfo.getOrderOpenDate());
        fuOrderCustomer.setOrderOpenPrice(fuOrderInfo.getOrderOpenPrice());

        /*交易 收益*/
        fuOrderCustomer.setOrderSymbol(fuOrderInfo.getOrderSymbol());
        fuOrderCustomer.setComment(fuOrderInfo.getComment());
        fuOrderCustomer.setOrderProfit(fuOrderInfo.getOrderProfit());
        fuOrderCustomer.setOrderStoploss(fuOrderInfo.getOrderStoploss());
        fuOrderCustomer.setOrderProfit(fuOrderInfo.getOrderTakeprofit());
        fuOrderCustomer.setOrderMagic(fuOrderInfo.getOrderMagic());
        fuOrderCustomer.setOrderSwap(fuOrderInfo.getOrderSwap());
        fuOrderCustomer.setOrderCommission(fuOrderInfo.getOrderCommission());

        /*用户 账户信息*/
        fuOrderCustomer.setUserId(fuOrderInfo.getUserId());
        fuOrderCustomer.setUserType(fuOrderInfo.getOrderType());
        fuOrderCustomer.setMtId(fuOrderInfo.getMtId());


        return fuOrderCustomer;
    }
}