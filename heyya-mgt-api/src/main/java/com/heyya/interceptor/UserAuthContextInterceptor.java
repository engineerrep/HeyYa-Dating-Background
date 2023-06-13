package com.heyya.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.heyya.config.auth.UserAuthContext;
import com.heyya.config.auth.UserToken;
import com.heyya.config.auth.UserTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserAuthContextInterceptor extends HandlerInterceptorAdapter {

    private static final String HEADER_TOKEN = "X-TOKEN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = null;
        token = request.getParameter(HEADER_TOKEN);
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[]{});
        List<Cookie> cookieList = Arrays.asList(cookies).stream()
                .filter(cookie -> HEADER_TOKEN.equals(cookie.getName())).collect(Collectors.toList());
        Cookie cookie = cookieList.isEmpty() ? null : cookieList.get(0);
        token = StringUtils.isBlank(token) && Objects.nonNull(cookie) ? cookie.getValue() : token;
        token = StringUtils.isBlank(token) ? request.getHeader(HEADER_TOKEN) : token;
        if (StringUtils.isBlank(token)) {
            throw new JWTVerificationException("The token can not be empty!");
        }
        UserToken userTokenInfo = UserTokenUtils.verify(token);
        UserAuthContext.setUserTokenInfo(userTokenInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        UserAuthContext.release();
    }

}
