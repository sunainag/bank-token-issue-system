package com.abs.banking;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

@Component
public class AbsBankingRequestInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbsBankingRequestInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		String postData;
        HttpServletRequest requestCacheWrapperObject = null;
        
        requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
        requestCacheWrapperObject.getParameterMap();
        postData = readPayload(requestCacheWrapperObject);
        LOGGER.info("REQUEST DATA: " + postData);
        
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LOGGER.info("RESPONSE: " + response.getStatus());
	}
	
	private String readPayload(final HttpServletRequest request) throws IOException {
        String payloadData = null;
        ContentCachingRequestWrapper contentCachingRequestWrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (null != contentCachingRequestWrapper) {
            byte[] buf = contentCachingRequestWrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payloadData = new String(buf, 0, buf.length, contentCachingRequestWrapper.getCharacterEncoding());
            }
        }
        return payloadData;
    }

}
