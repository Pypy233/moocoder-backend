package com.moekr.aes.web.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
public class LoginRedirectHandler implements AuthenticationFailureHandler, LogoutSuccessHandler {
	private static final String REDIRECT_URL = "/login.html";

	private final FlashMapManager flashMapManager;

	@Autowired
	public LoginRedirectHandler(FlashMapManager flashMapManager) {
		this.flashMapManager = flashMapManager;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
		onRedirect(request, response, Collections.singletonMap("from", "login"));
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		onRedirect(request, response, Collections.singletonMap("from", "logout"));
	}

	private void onRedirect(HttpServletRequest request, HttpServletResponse response, Map<String, Object> flashParam) throws IOException {
		FlashMap flashMap = new FlashMap();
		flashMap.putAll(flashParam);
		request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, flashMap);
		request.setAttribute(DispatcherServlet.FLASH_MAP_MANAGER_ATTRIBUTE, flashMapManager);
		RequestContextUtils.saveOutputFlashMap(REDIRECT_URL, request, response);
		response.sendRedirect(REDIRECT_URL);
	}
}
