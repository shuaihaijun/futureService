package com.future.common.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * 工具类 - 公用
*/

public class CommonUtil {
	

	public static Integer str2Num(String str){
		if(isEmpty(str)){
			return null;
		}else {
			return new BigDecimal(str.trim()).intValue();
		}
	}
	
	public static BigDecimal str2BigDecimal(String str){
		if(isEmpty(str)){
			return BigDecimal.ZERO;
		}else{
			return new BigDecimal(str.trim());
		}
	}


	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}
	

	@SuppressWarnings({"rawtypes" })
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}


	/**
	 * @description 判断不为空多参数
	 * @author hely
	 * @date 2017-07-21
	 * @param
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Object... ary) {
		for (Object pObj : ary) {
			if (pObj == null)
				return false;
			if (pObj instanceof String) {
				if (((String) pObj).trim().length() == 0) {
					return false;
				}
			} else if (pObj instanceof Collection) {
				if (((Collection) pObj).size() == 0) {
					return false;
				}
			} else if (pObj instanceof Map) {
				if (((Map) pObj).size() == 0) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	@SuppressWarnings({"rawtypes" })
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description 多参数判断空
	 * @author hely
	 * @date 2017-07-21
	 * @param
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object... ary) {
		for (Object pObj : ary) {
			if (pObj == null)
				return true;
			if (pObj instanceof String) {
				if (((String) pObj).trim().length() == 0) {
					return true;
				}
			} else if (pObj instanceof Collection) {
				if (((Collection) pObj).size() == 0) {
					return true;
				}
			} else if (pObj instanceof Map) {
				if (((Map) pObj).size() == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	* 获得随即颜色
	* @return
	* @author add by x@2012-10-30
	* @return String
	*
	*/
	public static String getRandom(int count){
		String S = "0123456789ABCDEF";
		Random r = new Random();
		String tmp ="";
		for (int i=0;i<count;i++){
			int index = r.nextInt(S.length());
			tmp += S.charAt(index);
		}
		return tmp;
	}
	
	/**
	 * 获得随机数字
	 * @param count  字符长度
	 * @return
	 */
	public static String getRandomNumber(int count) {
		String S = "0123456789";
		Random r = new Random();
		String tmp ="";
		for (int i=0;i<count;i++){
			int index = r.nextInt(S.length());
			tmp += S.charAt(index);
		}
		return tmp;
	}
	
	public static String fixStringLen(String str,int len,String fixStr){
		if (isEmpty(str) || str.length()>len){
			return str;
		}
		int qty = len - str.length(); //缺少多少位
		String rtn = "";
		for (int i=0;i<qty;i++){
			rtn +=fixStr;
		}
		return rtn+str;
	}
	

	  /** 
	    * 把传入的字符串 转化成where xxx in (xxx) 的形式，
		* @param str  1,2,3,4
		* @return	('1','2','3','4')
		* @author add by x@2012-9-17
	    * @return String  
		*
	    */  
	public static String getSqlWhereIn(String str){
		if (str == null || "".equals(str)) return "('')";
		
		if (str.indexOf(",") > 0){
			String[] list= str.split(",");
			StringBuffer sb = new StringBuffer("(");
			for (int i=0;i<list.length;i++){
				sb.append("'"+list[i]+"'");
				if (i != list.length - 1){
					sb.append(",");
				}
			}
			sb.append(")");
			return sb.toString();
		}else{
			return "('"+str+"')";
		}
	}

	/**
	 * 生成订单号
	 * orderid=业务编码(2位,businessCode)
	 * +客户端类型(2位,client)+版本号(4位,version)
	 * +客户类型(2位,userType)+资金账户(6位,userid)
	 * +创建时间(8位,当前秒数时间戳后8位)+随机数(4位)
	 * 例：Z012011000377777405162367386
	 * @param businessCode
	 * @param clientId
	 * @param version
	 * @param userType
	 * @param userId
	 * @return
	 */
	public static String createOrderId(String businessCode,String clientId,String version,String userType,Integer userId){

		/*校验空*/
		if(StringUtils.isEmpty(businessCode)
				||StringUtils.isEmpty(businessCode)
				||StringUtils.isEmpty(version)
				||StringUtils.isEmpty(userType)
				||StringUtils.isEmpty(userId)){
			return null;
		}

		/*版本号中有小数点*/
		if(version.indexOf(".")>-1){
			version=version.replace(".","");
		}
		if(version.length()<4){
			for(int i=4-version.length();i>0;i--){
				version="0"+version;
			}
		}
		if(version.length()>4){
			version=version.substring(version.length()-4,version.length()-1);
		}

		/*业务编码*/
		if(businessCode.length()>2){
			businessCode.substring(businessCode.length()-2,businessCode.length()-1);
		}

		/*客户端编码*/
		if(clientId.length()>2){
			clientId.substring(clientId.length()-2,clientId.length()-1);
		}

		/*客资类型*/
		if(userType.length()>2){
			userType.substring(userType.length()-2,userType.length()-1);
		}
		if(userType.length()<2){
			userType="0"+userType;
		}

		/*校验规则*/
		String orderId=businessCode+clientId+version+userType+userId;
		String dateTime=String.valueOf(new Date().getTime());
		String timeStamp=dateTime.substring(dateTime.length()-9,dateTime.length()-1);
		orderId=orderId+timeStamp+ CommonUtil.getRandomNumber(4);

		/*大于28位后，截取前28位*/
		if(orderId.length()>28){
			orderId=orderId.substring(0,27);
		}
		return orderId;
	}
	

 /**
	* 清空STRINGBUFFER
	* @param  sb
	*
	* @return void
	*
	*/
	public static void clearStringBuffer(StringBuffer sb){
		sb.delete(0, sb.length());
	}
	
	public static String getOSName(){
		return System.getProperties().getProperty("os.name");
	}
	
	public static Boolean isWindows(){
		if (getOSName().toLowerCase().indexOf("window")>-1){
			return true;
		}else{
			return false;
		}
	}
	public static Boolean isLinux(){
		if (getOSName().toLowerCase().indexOf("linux")>-1){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * 根据正则表达式查找符合规则的子串
	 * @param _regexp 正则表达式
	 * @param content 源字符串
	 * @return 返回子串
	 * @return List<String>  
	 * @author add by chengxw
	 * @date 2013-5-24
	 */
	public static List<String> findString(String _regexp,String content){
		Pattern pattern = Pattern.compile(_regexp);  
	    Matcher match = pattern.matcher(content); 
	    List<String> list = new ArrayList<String>();
	    while(match.find()){
	    	list.add(match.group());
	    }
		return list;
	}
	
	
	/**
	 * 字符串解密
	 * @param s
	 * @return
	 * @return String
	 * @author add by chengxw
	 * @date 2013-5-31
	 */
	public static String decrypt(String s){
        int iLen = 0;
        String sText =s;
        String sResult ="";
        //String sRtn = "";
        iLen = sText.length();
        for (int i =1;i <= iLen;i++){
            sResult =  (char)(sText.substring(i - 1, i ).codePointAt(0) - (2 * (iLen - i -2))) +sResult ;
        }
        return sResult;
    }

	/**
	 * 字符串加密
	 * @param str
	 * @return
	 * @return String
	 * @author add by chengxw
	 * @date 2013-5-31
	 */
	public static String encrypt(String str){
		StringBuffer resStr = new StringBuffer();
		StringBuffer buf = new StringBuffer(str.trim());
		buf = buf.reverse();//字符串翻转
		for (int i = 1; i <= buf.length(); i++) {
			char c = (char)(((buf.substring(i-1, i)).codePointAt(0))+((buf.length() - i - 2) * 2));
			resStr.append(c);
		}
		return resStr.toString();
	}
	
	/**JAVA 加密
	 * @param strSrc 需要加密人字符串，支持中文
	 * @param encName  空位MD5，支持MD5 ,SHA-1 SHA-256
	 * @return String
	 * @author add by x
	 * @date 2013-6-8
	 */
	public static String encrypt(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			if (encName == null || encName.equals("")) {
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}


	//获取MAC地址的方法  
    private static String getMACAddress(InetAddress ia)throws Exception{  
        //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。  
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();  
          
        //下面代码是把mac地址拼装成String  
        StringBuffer sb = new StringBuffer();  
          
        for(int i=0;i<mac.length;i++){  
            if(i!=0){  
                sb.append("-");  
            }  
            //mac[i] & 0xFF 是为了把byte转化为正整数  
            String s = Integer.toHexString(mac[i] & 0xFF);  
            sb.append(s.length()==1?0+s:s);  
        }  
          
        //把字符串所有小写字母改为大写成为正规的mac地址并返回  
        return sb.toString().toUpperCase();  
    }
    
	public static String getMAC(){
    	String macAddress = "";
    	try {
    		InetAddress ia = InetAddress.getLocalHost();//获取本地IP对象  
    		macAddress = getMACAddress(ia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return macAddress;
    }
	

	/**
	 * 将驼峰法命名的字符串转换为数据库中的字段名
	 * add by lipan on 20150906
	 * @param s
	 * @return
	 */
	public static String toUnderlineName(String s) {
        if (s == null) {
            return null;
        }
 
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
 
            boolean nextUpperCase = true;
 
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
 
            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) sb.append('_');
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
 
            sb.append(Character.toLowerCase(c));
        }
 
        return sb.toString();
    }

	/**
	 * @description id数组转字符串 e.g. 将 [1,2,3,4] 转为 1,2,3,4
	 * @author hely
	 * @date 2017-06-08
	 * @param
	 */
	public static String convertLongAryToStr(Long[] ids){
		String str = "";
		for (Long id : ids) {
			str+=id+",";
		}
		if (str.endsWith(",")){
			str=str.substring(0,str.length()-1);
		}
		return str;
	}

	/**
	 * 校验特殊字符
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static boolean checkSpecialChar(String str) throws PatternSyntaxException {
		// 清除掉所有特殊字符
		String regEx =  ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 检测是否有emoji字符
	 *
	 * @param source
	 * @return 一旦含有就抛出
	 */
	public  boolean containsEmoji(String source) {
		if (StringUtils.isEmpty(source)) {
			return false;
		}
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isNotEmojiCharacter(codePoint)) {
				//判断到了这里表明，确认有表情字符
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为非Emoji字符
	 *
	 * @param codePoint 比较的单个字符
	 * @return
	 */
	private boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) ||
				(codePoint == 0x9) ||
				(codePoint == 0xA) ||
				(codePoint == 0xD) ||
				((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
				((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
				((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 利用正则表达式判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}

	/**
	 * Object 转 map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> Obj2Map(Object obj) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field:fields){
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}
 		return map;
	}

	/**
	 * @param regex
	 * 正则表达式字符串
	 * @param str
	 * 要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 验证邮箱
	 * @param str 待验证的字符串
	 * @return 如果是符合的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isEmail(String str) {
		String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, str);
	}

	/**
	 * 验证输入身份证号
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsIDcard(String str) {
		String regex = "(^\\d{18}$)|(^\\d{15}$)";
		return match(regex, str);
	}

	/**
	 * 验证输入手机号码
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsHandset(String str) {
		String regex = "^[1]+[3,5]+\\d{9}$";
		return match(regex, str);
	}

	/**
	 * 验证输入邮政编号
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsPostalcode(String str) {
		String regex = "^\\d{6}$";
		return match(regex, str);
	}

	/**
	 * 验证电话号码
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsTelephone(String str) {
		String regex = "^(\\d{3,4}-)?\\d{6,8}$";
		return match(regex, str);
	}


	/**
	 * 用户名验证 必须是6-10位字母、数字、下划线 不能以数字开头
	 *  @param name
	 *  @return
	 */
	public static boolean checkName(String name) {
		String regExp = "^[^0-9][\\w_]{5,9}$";
		if(name.matches(regExp)) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 密码验证 必须是6-20位的字母、数字、下划线
	 *  @param pwd
	 *  @return
	 */
	public static boolean verifyPassword(String pwd) {
		String regExp = "^[\\w_]{6,20}$";
		if(pwd.matches(regExp)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断string是否符合json格式 fastjson
	 * @param jsonString
	 * @return
	 */
	public static boolean isJSONValid(String jsonString) {
		try {
			JSONObject.parseObject(jsonString);
		} catch (JSONException ex) {
			try {
				JSONObject.parseArray(jsonString);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

}