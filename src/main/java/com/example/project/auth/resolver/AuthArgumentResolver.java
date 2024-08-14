package com.example.project.auth.resolver;

import com.example.project.auth.annotation.AuthMember;
import com.example.project.auth.domain.Authentication;
import com.example.project.auth.domain.UserDetail;
import com.example.project.auth.token.TokenProvider;
import com.example.project.common.header.HeaderUtil;

import com.example.project.user.domain.vo.RoleType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {
    private final TokenProvider tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Objects.nonNull(parameter.getParameterAnnotation(AuthMember.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        var httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        var token = HeaderUtil.getAccessToken(httpServletRequest);
        UserDetail userData = null;

        if (token != null && tokenProvider.validateDateToken(token)) {
            userData = tokenProvider.getAuthentication(token).getUserDetail();
        }

        if(token == null){
            userData = new Authentication(new UserDetail(), RoleType.USER).getUserDetail();
        }

        return Optional.of(userData).orElseThrow(NullPointerException::new);
    }
}