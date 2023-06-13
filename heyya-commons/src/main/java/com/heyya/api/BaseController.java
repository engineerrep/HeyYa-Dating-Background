package com.heyya.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.common.collect.Maps;
import com.heyya.exception.BaseException;
import com.heyya.model.resp.ErrorTracker;
import com.heyya.model.resp.Response;
import com.heyya.tools.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @ExceptionHandler(Exception.class)
    public Response<Object> exceptionHandler(Exception e) {
        String requestURI = "";
        String requestBody = "";
        int code = 8500;
        String msg = "Internal error!";
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
            Map<String, String> errorMap = Maps.newHashMap();
            for (FieldError fieldError : fieldErrors) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Response.paramsInvalid(errorMap);
        } else if (e instanceof BindException) {
            BindException exception = (BindException) e;
            List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
            Map<String, String> errorMap = Maps.newHashMap();
            for (FieldError fieldError : fieldErrors) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Response.paramsInvalid(errorMap);
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exception = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
            Map<String, String> errorMap = Maps.newHashMap();
            String property = null;
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                property = constraintViolation.getPropertyPath().toString();
                property = StringUtils.substring(property, property.lastIndexOf(".") + 1);
                if (errorMap.get(property) != null) {
                    String errorMsg = errorMap.get(property);
                    errorMap.put(property, StringUtils.join(errorMsg, ",", constraintViolation.getMessage()));
                } else {
                    errorMap.put(property, constraintViolation.getMessage());
                }
            }
            return Response.paramsInvalid(errorMap);
        } else if (e instanceof JWTVerificationException) {
            return Response.INVALID_TOKEN;
        } else if (e instanceof BaseException) {
            BaseException exp = (BaseException) e;
            code = exp.getCode();
            msg = exp.getMsg();
        }
        if (request != null && request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            requestBody = StringUtils.toEncodedString(wrapper.getContentAsByteArray(),
                    Charset.forName(wrapper.getCharacterEncoding()));
            requestURI = wrapper.getRequestURI();
        }
        ErrorTracker tracker = new ErrorTracker();
        log.error("\nRequest error: "
                        + "\nThe request errorNo is: {}"
                        + "\nThe request URL is: {}"
                        + "\nThe request Method is: {}"
                        + "\nThe request params is: {}"
                        + "\nThe request body is: {}"
                        + "\nThe exception is: ",
                tracker.getErrorNo(), requestURI, request.getMethod(), GsonUtils.toJson(request.getParameterMap()),
                requestBody, e);
        return Response.failed(code, msg, tracker);
    }
}
