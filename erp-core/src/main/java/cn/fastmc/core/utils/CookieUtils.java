package cn.fastmc.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import cn.fastmc.sys.service.PropertiesConfigService;

/**
 * Cookie 辅助类
 * 
 * 
 */
public class CookieUtils {

	public static void addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			Integer maxAge, String path, String domain, Boolean secure) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, value);
			if (maxAge != null) {
				cookie.setMaxAge(maxAge.intValue());
			}
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			if (secure != null) {
				cookie.setSecure(secure.booleanValue());
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException1) {
			localUnsupportedEncodingException1.printStackTrace();
		}
	}

	public static void addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			Integer maxAge) {

		addCookie(request, response, name, value, maxAge,
				PropertiesConfigService.getCookiePath(),
				PropertiesConfigService.getCookieDomain(), null);
	}

	public static void addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value) {

		addCookie(request, response, name, value, null,
				PropertiesConfigService.getCookiePath(),
				PropertiesConfigService.getCookieDomain(), null);
	}

	public static String getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Assert.hasText(name);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				name = URLEncoder.encode(name, "UTF-8");
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(),"UTF-8");
					}
				}
			} catch (UnsupportedEncodingException localUnsupportedEncodingException1) {
				localUnsupportedEncodingException1.printStackTrace();
			}
		}
		return null;
	}

	public static void removeCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String path,
			String domain) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			Cookie cookie = new Cookie(name, null);
			cookie.setMaxAge(0);
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException localUnsupportedEncodingException1) {
			localUnsupportedEncodingException1.printStackTrace();
		}
	}

}
