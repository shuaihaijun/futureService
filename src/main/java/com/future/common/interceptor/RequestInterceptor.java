package com.future.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.future.common.annotation.UpGrade;
import com.future.common.constants.GlobalConstant;
import com.future.common.enums.GlobalResultCode;
import com.future.common.enums.RequestKey;
import com.future.common.exception.BusinessException;
import com.future.common.exception.InternalServerException;
import com.future.common.util.JsonUtils;
import com.future.common.util.LogUtil;
import com.future.common.util.ThreadCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 全局统一请求{@link HttpServletRequest}拦截器类,校验请求类型是否为psot、请求体是否为json格式
 *
 * @author Admin
 * @version: 1.0
 */
@UpGrade()
public class RequestInterceptor implements HandlerInterceptor {

    private static LogUtil logger = LogUtil.logger(RequestInterceptor.class);

    private List<String> urls;

    public RequestInterceptor() {
    }

    /**
     * 方法调用后执行，清除当前请求线程的本地请求的JSON数据
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//		SystemContext.removeRequestJSON();
    }

    /**
     * 方法调用执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 方法调用前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (isUpGrade()) {
            throw new BusinessException(GlobalResultCode.SYSTEM_CLOSE_UPGRADE);
        }
        response.setCharacterEncoding(GlobalConstant.PLATFORM_CHARACTER_ENCODING);
        response.setContentType(GlobalConstant.PLATFORM_RESPONSE_CONTENTTYPE_JSON);
        //接口放行
        if (urls != null && urls.contains(request.getRequestURI())) {
            return true;
        }
        String contentType = request.getContentType();
        if (contentType != null) {
            contentType = contentType.toLowerCase();
        }
        logger.info(request.getRequestURI());
        logger.info(request.getRemoteAddr());
        logger.info(request.getRemotePort() + "");
        logger.info(contentType);

        String source;
        String version;
        JSONObject requestMap = new JSONObject();
        if ("POST".equals(request.getMethod().toUpperCase())
                && (contentType != null && contentType.startsWith(GlobalConstant.PLATFORM_RESPONSE_CONTENTTYPE_JSON))) {
            // 获取请求参数
            String requestJSONStr = ThreadCache.getPostRequestParams();

//            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
//            String str = "";
//            while ((str = reader.readLine()) != null) {//一行一行的读取body体里面的内容；
//                requestJSONStr += str;
//            }

            if (StringUtils.isEmpty(requestJSONStr)) {
                throw new BusinessException(GlobalResultCode.PARAM_NULL_POINTER);
            }
            // 校验请求信息，是否为json格式
            if (!JsonUtils.isJson(requestJSONStr)) {
                throw new BusinessException(GlobalResultCode.DATA_COVERTET_JSON_ERROR);
            }

            // 字符串格式化为JSONObject
            requestMap = JSONObject.parseObject(requestJSONStr);
            // 校验请求json的必要属性(暂时只校验请求来源)
            source = requestMap.getString(RequestKey.source.name());
            version = requestMap.getString(RequestKey.version.name());
            // logger.info("filer-post请求参数:[params={}]", params);
        } else {
            // 获取请求参数
            source = request.getParameter(RequestKey.source.name());
            version = request.getParameter(RequestKey.version.name());
            // logger.info("非post请求");
        }

        request.setAttribute(RequestKey.source.name(), source);
        request.setAttribute(RequestKey.version.name(), version);

        return true;
    }

    /**
     * 获取统一请求拦截器类注解{@link RequestInterceptor},校验属性值是否允许请求访问系统
     *
     * @return true：不允许访问系统；false：允许访问系统
     */
    private boolean isUpGrade() {
        Class<RequestInterceptor> clazz = RequestInterceptor.class;
        // 得到类注解
        UpGrade upGrade = clazz.getAnnotation(UpGrade.class);
        if (upGrade == null) {
            throw new InternalServerException();
        }
        return upGrade.value();

    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

}