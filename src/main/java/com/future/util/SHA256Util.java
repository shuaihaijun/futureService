package com.future.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.Collator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SHA256请求校验帮助类
 */
@SuppressWarnings("rawtypes")
public class SHA256Util {

	private static final String startWithChinesePattern = "^[\u4e00-\u9fa5]";
	private static Logger logger = LoggerFactory.getLogger(SHA256Util.class);
	private static Comparator comparator = Collator.getInstance(Locale.CHINA);



	@SuppressWarnings("unchecked")
	public static Boolean verifyRequest(JSONObject jobj, String secretKey) {
		Map<String, String> parames = new HashMap<String, String>();
		Iterator it = jobj.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			parames.put(entry.getKey(), entry.getValue().toString());
		}
		return verifyRequest(parames, secretKey);
	}

	/**
	 * 校验请求是否合法
	 * 
	 * @param parames
	 *            校验的请求参数Map
	 * @param secretKey
	 *            sha256密钥
	 * @return 校验结果: true 正确, false 错误
	 */
	protected static Boolean verifyRequest(Map<String, String> parames,
			String secretKey) {

		if (!isHaveCtimeAndNounce(parames)) {
			logger.debug("result:fail. ctime, nonce, sign some of them value can *not* be empty.");
			return false;
		}
		logger.debug("result:ok. ctime, nonce, sign validated.");

		String sign = parames.get("sign");
		parames.remove("sign");

		// 使用英文排序进行检测
		String serverSign = shuffAndSha256(parames, secretKey);
		if (serverSign.equals(sign)) {
			logger.info("Sha256 sign same. severSign use ASCII sort");
			return true;
		}

		// 使用中文排序进行检测
		String serverSignUseChinaSort = shuffAndSha256UseChinaSort(parames,
				secretKey);
		if (serverSignUseChinaSort.equals(sign)) {
			logger.info("Sha256 sign same. serverSign use China sort");
			return true;
		}

		// 两种检测都不正确,反回失败
		logger.warn("Sha256 sign *not* same. " + " ,severSignUseASCIISort:"
				+ serverSign + " ,serverSignUseChinaSort:"
				+ serverSignUseChinaSort + " ,request sign:" + sign);
		return false;
	}

	/**
	 * 生成SHA256校验Sign
	 * 
	 * @param parames
	 *            校验请求参数的Map
	 * @param secretKey
	 *            sha256密钥
	 * @return 校验结果: true 正确, false 错误
	 */
	public static String genSha256Sign(Map<String, String> parames,
			String secretKey) {
		return shuffAndSha256UseChinaSort(parames, secretKey);
	}

	/**
	 * 生成SHA256校验Sign 并返回含有sign的Map
	 * 
	 * @param parames
	 *            校验请求参数的Map
	 * @param secretKey
	 *            sha256密钥
	 * @return 校验结果: true 正确, false 错误
	 */
	public static Map<String, String> genSha256SignMap(
			Map<String, String> parames, String secretKey) {

		parames.put("sign", shuffAndSha256(parames, secretKey));
		return parames;
	}

	/**
	 * 混淆请求值并Base64. 使用ASCII排序.
	 * 
	 * @param requestMap
	 *            校验请求参数的Map
	 * @return 校验结果: true 正确, false 错误
	 */
	private static String shuffAndSha256(Map<String, String> requestMap,
			String secretKey) {

		// 分割线
		String values = shuff(requestMap, secretKey);
		return new String(DigestUtils.sha256Hex(values.getBytes()));
	}

	/**
	 * 混淆请求值并Base64. 按中文拼音排序
	 * 
	 * @param requestMap
	 *            校验请求参数的Map
	 * @return 校验结果: true 正确, false 错误
	 */
	private static String shuffAndSha256UseChinaSort(
			Map<String, String> requestMap, String secretKey) {
		String values = shuffUseChinaSort(requestMap, secretKey);
		return new String(DigestUtils.sha256Hex(values.getBytes()));
	}

	/**
	 * 根据参数值进行排序.
	 * 
	 * @param requestMap
	 * @param secretKey
	 * @return
	 */
	protected static String shuff(Map<String, String> requestMap,
			String secretKey) {

		String values = "";
		List<String> valueList = new ArrayList<String>();

		for (Map.Entry<String, String> entry : requestMap.entrySet()) {
			valueList.add(entry.getValue());
		}
		valueList.add(secretKey);
		Collections.sort(valueList);

		if (logger.isDebugEnabled()) {
			logger.debug("sort use ASCII:");
			for (String s : valueList)
				logger.debug(s);
		}

		for (String s : valueList)
			values += s;

		logger.debug("sort use ASCII in one line:" + values + " length:"
				+ values.length());
		return values;
	}

	/**
	 * 根据参数值进行排序. 中文将单独进行排序,排在非中文后面.
	 * 
	 * @param requestMap
	 * @param secretKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static String shuffUseChinaSort(Map<String, String> requestMap,
			String secretKey) {

		String values = "";
		List<String> enValuesList = new ArrayList<String>();
		List<String> chValuesList = new ArrayList<String>();

		for (Map.Entry<String, String> entry : requestMap.entrySet()) {
			String value = entry.getValue();
			if (isStartWithChinese(value)) {
				chValuesList.add(value);
			} else {
				enValuesList.add(value);
			}
		}

		enValuesList.add(secretKey);
		Collections.sort(enValuesList);
		Collections.sort(chValuesList, comparator);

		if (logger.isDebugEnabled()) {
			logger.debug("sort use china:");
			for (String s : enValuesList)
				logger.debug(s);
			for (String s : chValuesList)
				logger.debug(s);
		}

		for (String s : enValuesList)
			values += s;
		for (String s : chValuesList)
			values += s;

		logger.debug("sort use china in one line:" + values + " length:"
				+ values.length());
		return values;
	}

	/**
	 * 检测是否有ctime及nounce&sign
	 * 
	 * @param parames
	 *            请求参数map
	 * @return 包含必要参数true
	 */
	private static Boolean isHaveCtimeAndNounce(Map<String, String> parames) {
		String ctime = parames.get("ctime");
		String nonce = parames.get("nonce");
		String sign = parames.get("sign");
		return !(StringUtils.isEmpty(ctime)||StringUtils.isEmpty(nonce)||StringUtils.isEmpty(sign));
	}

	/**
	 * 检测是否为中文
	 * 
	 * @param word
	 *            输入
	 * @return true以中文开头
	 */
	protected static boolean isStartWithChinese(String word) {
		Pattern r = Pattern.compile(startWithChinesePattern);
		Matcher matcher = r.matcher(word);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 校验请求是否合法, 这是新写的,和以前得不同的是。
	 * 以前是转为byte数据进行加密,而这个没有,因为他的底层已经转了。
	 * 
	 * @param parames
	 *            校验的请求参数Map,和对方的sign。parames.put("sign",sign)
	 * @param secretKey
	 *            sha256密钥
	 * @return 校验结果: true 正确, false 错误
	 */
	public static Boolean verifyRequestStr(Map<String, String> parames,
			String secretKey) {

		String sign = parames.get("sign");

		if(StringUtils.isEmpty(sign)|| ObjectUtils.isEmpty(parames)){
			logger.debug("Sha256 param empty");
			return false;
		}
		parames.remove("sign");
		// 使用英文排序进行检测
		String bytesString = shuffAndSha256Str(parames, secretKey);
		if (sign.equals(bytesString)) {
			logger.debug("Sha256 English success");
			return true;
		}
		// 使用中文排序进行检测
		String chinaString = shuffAndSha256UseChinaSortStr(parames, secretKey);
		if (sign.equals(chinaString)) {
			logger.debug("Sha256 China success");
			return true;
		}
		// 校验没通过
		logger.debug("SHA256 sign==>bytesString=" + bytesString
				+ ",chinaString=" + chinaString);
		return false;
	}

	/**
	 * 混淆请求值并Base64. 使用ASCII排序.
	 * 
	 * @param requestMap
	 *            校验请求参数的Map
	 * @param secretKey
	 *            SHA256key
	 * @return Map key=signBytes,key=signString
	 */
	private static String shuffAndSha256Str(Map<String, String> requestMap,
			String secretKey) {

		// 排序
		String values = shuff(requestMap, secretKey);
		logger.debug("SHA256 English Sort==>" + values);
		// 计算sign
		String signString = new String(DigestUtils.sha256Hex(values));
		return signString;
	}

	/**
	 * 混淆请求值并Base64. 按中文拼音排序
	 * 
	 * @param requestMap
	 *            校验请求参数的Map
	 * @param secretKey
	 *            SHA256key
	 * @return map key=signBytes,key=signString
	 */
	private static String shuffAndSha256UseChinaSortStr(
			Map<String, String> requestMap, String secretKey) {
		// 排序
		String values = shuffUseChinaSort(requestMap, secretKey);
		logger.debug("SHA256 China Sort==>" + values);
		// 计算sign
		String signString = new String(DigestUtils.sha256Hex(values));
		return signString;
	}
	/**
	 * 发送短信加密
	 * @param requestParames
	 * @param secretKey
	 * @return
	 */
	public static Map<String, String> sha256sign(Map<String, String> requestParames, String secretKey) {
        requestParames.put("ctime", System.currentTimeMillis() + "");
        requestParames.put("nonce", String.valueOf(new Random().nextInt(Integer.MAX_VALUE)));
        return SHA256Util.genSha256SignMap(requestParames, secretKey);
    }
}
