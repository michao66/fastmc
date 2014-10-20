package cn.fastmc.web.spring.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.fastmc.core.utils.CookieUtils;
import cn.fastmc.core.utils.UidUtils;

public class TokenInterceptor extends HandlerInterceptorAdapter {
	// public static final java.lang.String TOKEN_NAMESPACE = "spring.tokens";
	public static final java.lang.String TOKEN_NAME_FIELD = "spring.token";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String token = CookieUtils.getCookie(request, TOKEN_NAME_FIELD);
		if (request.getMethod().equalsIgnoreCase("POST")) {
			String str2 = request.getHeader("X-Requested-With");
			if ((str2 != null) && (str2.equalsIgnoreCase("XMLHttpRequest"))) {
				if ((token != null)
						&& (token.equals(request.getHeader(TOKEN_NAME_FIELD)))) {
					return true;
				}
				response.addHeader("tokenStatus", "accessDenied");
			} else if ((token != null) && (token.equals(request.getParameter(TOKEN_NAME_FIELD)))) {
				return true;
			}
			if (token == null) {
				token = UidUtils.UID();
				CookieUtils.addCookie(request, response, TOKEN_NAME_FIELD,token);
			}
			response.sendError(403, "Bad or missing token!");
			return false;
		}
		if (token == null) {
			token = UidUtils.UID();
			CookieUtils.addCookie(request, response, TOKEN_NAME_FIELD, token);
		}
		request.setAttribute(TOKEN_NAME_FIELD, token);
		return true;
	}

}
