package com.gospace.exploration.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author rumidipto
 * @since 3/22/24
 */
public class ServletUtil {

    private static final String REQUEST_METHOD_GET = "GET";

    private static final String REQUEST_METHOD_POST = "POST";

    private static final String REQUEST_METHOD_PUT = "PUT";

    private static final String REQUEST_METHOD_DELETE = "DELETE";

    public static boolean isGetRequest() {
        return REQUEST_METHOD_GET.equalsIgnoreCase(getHttpServletRequest().getMethod());
    }

    public static boolean isPostRequest() {
        return REQUEST_METHOD_POST.equalsIgnoreCase(getHttpServletRequest().getMethod());
    }

    public static boolean isPutRequest() {
        return REQUEST_METHOD_PUT.equalsIgnoreCase(getHttpServletRequest().getMethod());
    }

    public static boolean isDeleteRequest() {
        return REQUEST_METHOD_DELETE.equalsIgnoreCase(getHttpServletRequest().getMethod());
    }

    private static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;

        return attributes.getRequest();
    }
}
