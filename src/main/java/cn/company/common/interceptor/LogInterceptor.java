package cn.company.common.interceptor;

import cn.company.common.utils.IpUtils;
import cn.company.common.utils.JsonUtils;
import cn.company.common.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * 日志拦截器
 *
 * @author donaldhan
 */
public class LogInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) {
        MDC.put("requestId", UUID.randomUUID().toString().replaceAll("-", ""));
        logger.info("----------,{},----------", request.getRequestURI());
        logger.info("请求IP地址:{}", IpUtils.getIpAddress(request));
        logger.info("客户端信息:{}", request.getHeader("user-agent"));
        logger.info("请求的参数如下:");

        Map<String, String[]> requestData = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : requestData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            logger.info("{}-->{}", key, value);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        logger.info("Results--->{}", JsonUtils.objectToJson(ResponseUtils.getResult()));
        ResponseUtils.remove();
    }
}
