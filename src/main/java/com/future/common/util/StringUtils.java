package com.future.common.util;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 字符串操作工具类
 *
 * @author Admin
 * @version: 1.0
 */
public class StringUtils {

    /**
     * 判断参数对象是否为空，空值返回true
     *
     * @param _object
     * @return boolean
     */
    public static boolean isEmpty(Object _object) {
        return _object == null || "".equals(_object);
    }

    /**
     * isNotEmpty 方法
     * <p>方法说明: </p>
     *
     * @param _data
     * @return boolean
     */
    public static boolean isNotEmpty(String _data) {
        return !isEmpty(_data);
    }

    /**
     * isNotEmpty 方法
     * <p>方法说明: </p>
     *
     * @param _object
     * @return boolean
     */
    public static boolean isNotEmpty(Object _object) {
        return !isEmpty(_object);
    }

    /**
     * 判断参数字符串数组是否为空，空值返回true
     * <p>方法说明: </p>
     *
     * @param array
     * @return boolean
     */
    public static boolean isEmpty(String[] array) {
        if ((array == null) || (array.length == 0)) {
            return true;
        }
        return false;
    }

    /**
     * isNotEmpty 方法
     * <p>方法说明: </p>
     *
     * @param array
     * @return boolean
     */
    public static boolean isNotEmpty(String[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断参数集合是否为空，空值返回true
     * <p>方法说明: </p>
     *
     * @param _data
     * @return boolean
     */
    public static boolean isEmpty(Collection<? extends Object> _data) {
        return (_data == null) || _data.isEmpty();
    }

    /**
     * isNotEmpty 方法
     * <p>方法说明: </p>
     *
     * @param data
     * @return boolean
     */
    public static boolean isNotEmpty(Collection<? extends Object> data) {
        return !isEmpty(data);
    }

    /**
     * 判断参数字符串为空，空值返回true
     * <p>方法说明: </p>
     *
     * @param _str
     * @return boolean
     */
    public static boolean isEmpty(String _str) {
        return (_str == null) || _str.length() == 0;
    }

    /**
     * 将参数字符串转换为全小写
     * <p>方法说明: </p>
     *
     * @param _str
     * @return String
     */
    public static String lower(String _str) {
        if (!isEmpty(_str)) {
            return _str.toLowerCase();
        }
        return _str;
    }

    /**
     *  将参数字符串转换为全大写
     * <p>方法说明: </p>
     *
     * @param _str
     * @return String
     */
    public static String upper(String _str) {
        if (!isEmpty(_str)) {
            return _str.toUpperCase();
        }
        return _str;
    }

    /**
     * nvl 方法
     * <p>方法说明: </p>
     *
     * @param _object
     * @return String
     */
    public static String nvl(Object _object) {
        return isEmpty(_object) ? "" : _object.toString();
    }

    /**
     * nvl 方法
     * <p>方法说明: </p>
     *
     * @param _object
     * @param _rep
     * @return String
     */
    public static String nvl(Object _object, String _rep) {
        return isEmpty(_object) ? _rep : _object.toString();
    }

    /**
     * nvl 方法
     * <p>方法说明: </p>
     *
     * @param str
     * @param rep
     * @return String
     */
    public static String nvl(String str, String rep) {
        return isEmpty(str) ? rep : str;
    }

    /**
     * getWebInfPath 方法
     * <p>方法说明: </p>
     *
     * @return String
     */
    public static String getWebInfPath() {
        String str = "";
        try {
            str = StringUtils.class.getProtectionDomain().getCodeSource().toString();
            str = str.substring(str.indexOf("/"), str.lastIndexOf("/"));
            str = str.substring(1, str.indexOf("WEB-INF") + 8);
            if (System.getProperty("os.name").indexOf("Windows") == -1) {
                str = "/" + str;
            }
        } catch (Exception localException) {
        }
        return str;
    }

    /**
     * getWebRoot 方法
     * <p>方法说明: </p>
     *
     * @return String
     */
    public static String getWebRoot() {
        String path = getWebInfPath();
        try {
            if (path.indexOf("WEB-INF") > 0) {
                path = path.substring(0, path.indexOf("WEB-INF"));
            }
        } catch (Exception localException) {
        }
        return path;
    }

    /**
     * str2Int 方法
     * <p>方法说明: </p>
     *
     * @param _value
     * @return int
     */
    public static int str2Int(String _value) {
        return Integer.parseInt(_value);
    }

    /**
     * str2Int 方法
     * <p>方法说明: </p>
     *
     * @param value
     * @param defaultVal
     * @return int
     */
    public static int str2Int(String value, int defaultVal) {
        int rtnVal = -1;
        try {
            if (isEmpty(value)) {
                return defaultVal;
            }
            rtnVal = Integer.parseInt(value);
        } catch (Exception e) {
            rtnVal = defaultVal;
        }
        return rtnVal;
    }

    public static int obj2Int(Object iObj) {
        int rntVal = -1;
        try {
            if (isEmpty(iObj)) {
                return -1001;
            }
            rntVal = Integer.parseInt(iObj.toString());
        } catch (Exception e) {
            rntVal = -1000;
        }
        return rntVal;
    }

    /**
     * obj2Int 方法
     * <p>方法说明: </p>
     *
     * @param iObj
     * @param iDefault
     * @return int
     */
    public static int obj2Int(Object iObj, int iDefault) {
        int rntVal = iDefault;
        try {
            if (isEmpty(iObj)) {
                return iDefault;
            }
            rntVal = Integer.parseInt(iObj.toString());
        } catch (Exception e) {
            rntVal = iDefault;
        }
        return rntVal;
    }

    /**
     * str2Long 方法
     * <p>方法说明: </p>
     *
     * @param _s
     * @return long
     */
    public static long str2Long(String _s) {
        long rtnVal = -1L;
        try {
            rtnVal = Long.parseLong(_s);
        } catch (Exception e) {
            rtnVal = -1L;
        }
        return rtnVal;
    }

    /**
     * obj2Long 方法
     * <p>方法说明: </p>
     *
     * @param obj
     * @return long
     */
    public static long obj2Long(Object obj) {
        long rtnVal = -1L;
        try {
            rtnVal = Long.parseLong(nvl(obj, "-1"));
        } catch (Exception e) {
            rtnVal = -1L;
        }
        return rtnVal;
    }

    /**
     * str2Double 方法
     * <p>方法说明: </p>
     *
     * @param _s
     * @return double
     */
    public static double str2Double(String _s) {
        double rtnVal = -0.0D;
        try {
            rtnVal = Double.parseDouble(_s);
        } catch (Exception e) {
            rtnVal = -1.0D;
        }
        return rtnVal;
    }

    /**
     * obj2Double 方法
     * <p>方法说明: </p>
     *
     * @param obj
     * @return double
     */
    public static double obj2Double(Object obj) {
        double rtnVal = -0.0D;
        try {
            rtnVal = Double.parseDouble(nvl(obj, "0.00"));
        } catch (Exception e) {
            rtnVal = -1.0D;
        }
        return rtnVal;
    }

    /**
     * 首字母大写
     * <p>方法说明: </p>
     *
     * @param value
     * @return String
     */
    public static String trnasVariable(String value) {
        if (isEmpty(value)) {
            return "";
        }
        StringBuffer temp = new StringBuffer(value.substring(0, 1).toUpperCase());
        temp.append(value.substring(1, value.length()).toLowerCase());
        return temp.toString();
    }

    /**
     * formatNumbers 方法
     * <p>方法说明: </p>
     *
     * @param source
     * @param format
     * @return String
     */
    public static String formatNumbers(double source, String format) {
        format = nvl(format, "#0.00");
        DecimalFormat fmt = new DecimalFormat(format);
        return fmt.format(source);
    }

    /**
     * str2Ascii 方法
     * <p>方法说明: </p>
     *
     * @param string
     * @return String
     */
    public static String str2Ascii(String string) {
        StringBuffer sbVal = new StringBuffer();
        if (isNotEmpty(string)) {
            byte[] bs = string.getBytes();
            for (int i = 0; i < bs.length; i++) {
                sbVal.append(Integer.toHexString(bs[i]));
            }
        }
        return sbVal.toString();
    }

    /**
     * removeLastChar 方法
     * <p>方法说明: </p>
     *
     * @param string
     * @return String
     */
    public static String removeLastChar(String string) {
        String sVal = "";
        if (isEmpty(string)) {
            return sVal;
        }
        if (string.length() <= 1) {
            return string;
        }
        sVal = string.substring(0, string.length() - 1);
        return sVal;
    }

    /**
     * removeLastChar 方法
     * <p>方法说明: </p>
     *
     * @param string
     * @param regex
     * @return String
     */
    public static String removeLastChar(String string, String regex) {
        String sVal = "";
        if (isEmpty(string)) {
            return sVal;
        }
        if (string.length() <= 1) {
            return string;
        }
        sVal = string.substring(0, string.length() - regex.length());
        return sVal;
    }

    /**
     * arrayToString 方法
     * <p>方法说明: </p>
     *
     * @param list
     * @param separated
     * @return String
     */
    public static String arrayToString(List<String> list, String separated) {
        StringBuffer sbChar = new StringBuffer();
        if (isNotEmpty(list)) {
            if (isEmpty(separated)) {
                separated = "'";
            }
            for (String c : list) {
                sbChar.append(separated).append(c).append(separated).append(",");
            }
            return removeLastChar(sbChar.toString());
        }
        return sbChar.toString();
    }

    /**
     * arrayToString 方法
     * <p>方法说明: </p>
     *
     * @param data
     * @param separated
     * @return String
     */
    public static String arrayToString(Collection<String> data, String separated) {
        StringBuffer sbChar = new StringBuffer();
        if (isNotEmpty(data)) {
            if (isEmpty(separated)) {
                separated = "'";
            }
            for (String c : data) {
                sbChar.append(separated).append(c).append(separated).append(",");
            }
            return removeLastChar(sbChar.toString());
        }
        return sbChar.toString();
    }

    /**
     * arrayToString 方法
     * <p>方法说明: </p>
     *
     * @param arrs
     * @param separated
     * @return String
     */
    public static String arrayToString(String[] arrs, String separated) {
        StringBuffer sbChar = new StringBuffer();
        if (isNotEmpty(arrs)) {
            if (isEmpty(separated)) {
                separated = "'";
            }
            String[] arrayOfString = arrs;
            int j = arrs.length;
            for (int i = 0; i < j; i++) {
                String c = arrayOfString[i];
                sbChar.append(separated).append(c).append(separated).append(",");
            }
            return removeLastChar(sbChar.toString());
        }
        return sbChar.toString();
    }

    /**
     * 判断传入的是否存在空字符串
     */
    public static boolean isAnyBlank(CharSequence... css) {
        if (css == null || css.length == 0) {
            return true;
        }

        for (CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 子字符串出现的个数
     */
    public static int getSubStrCount(String str, String subStr) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(subStr, index)) != -1) {
            index = index + subStr.length();
            count++;
        }
        return count;
    }


    /**
     * 替换字符串
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (isNotEmpty(inString) && isNotEmpty(oldPattern) && newPattern != null) {
            int index = inString.indexOf(oldPattern);
            if (index == -1) {
                return inString;
            } else {
                int capacity = inString.length();
                if (newPattern.length() > oldPattern.length()) {
                    capacity += 16;
                }

                StringBuilder sb = new StringBuilder(capacity);
                int pos = 0;

                for (int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                    sb.append(inString.substring(pos, index));
                    sb.append(newPattern);
                    pos = index + patLen;
                }

                sb.append(inString.substring(pos));
                return sb.toString();
            }
        } else {
            return inString;
        }
    }

    /**
     * 格式化字符串（替换符为%s）
     */
    public static String formatIfArgs(String format, Object... args) {
        if (isEmpty(format)) {
            return format;
        }

        return (args == null || args.length == 0) ? String.format(format.replaceAll("%([^n])", "%%$1")) : String.format(format, args);
    }

    /**
     * 格式化字符串(替换符自己指定)
     */
    public static String formatIfArgs(String format, String replaceOperator, Object... args) {
        if (isEmpty(format) || isEmpty(replaceOperator)) {
            return format;
        }

        format = replace(format, replaceOperator, "%s");
        return formatIfArgs(format, args);
    }

}