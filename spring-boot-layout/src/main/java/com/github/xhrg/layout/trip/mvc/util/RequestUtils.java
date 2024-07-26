package com.github.xhrg.layout.trip.mvc.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {

//    public static String getUserName() {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest();
//        UserContext userContext = (UserContext) request.getAttribute(Constant.USER_CONTEXT);
//        return userContext.getName();
//    }

    /**
     * 
     * 2023.12.29.备注
     * 一般是先取x-forwarded-for，x-real-ip是兜底的。如果先取x-real-ip的话，那第一层nginx配置后，后面的nginx就不能再配置
     * X-Real-IP 了
     * 
     * 
     * 在nginx设置中，一般是如下设置。x-real-ip是覆盖式设置，而X-Forwarded-For是追加式设置
     * 
     * proxy_set_header X-Real-IP $remote_addr;
     * 
     * proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
     * 
     * 
     * @return
     */
    public static String getRequestIP() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return valueNotEmpty(request.getHeader("page-ip"), request.getHeader("x-forwarded-for"),
                request.getHeader("x-real-ip"), request.getRemoteAddr());
    }

    public static String valueNotEmpty(String... args) {
        if (args == null || args.length == 0) {
            return "";
        }
        for (String ip : args) {
            if (ip != null && ip.length() > 0) {
                return ip;
            }
        }
        return "";
    }
}
