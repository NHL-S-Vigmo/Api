package com.nhlstenden.student.vigmo.controllers.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ResourceSizeHandler implements ResponseBodyAdvice<List<?>> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //Checks if this advice is applicable.
        //In this case it applies to any endpoint which returns a collection.
        try{
            String typeName = ((ParameterizedType)returnType.getGenericParameterType()).getActualTypeArguments()[0].getTypeName();
            return typeName.contains("java.util.List");
        }catch(Exception exception){
            return List.class.isAssignableFrom(returnType.getParameterType());
        }
    }

    @Override
    public List<?> beforeBodyWrite(List<?> body, MethodParameter returnType, MediaType selectedContentType,
                                         Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                         ServerHttpRequest request,
                                         ServerHttpResponse response) {
        response.getHeaders().add("X-Total-Count", String.valueOf(body.size()));
        return body;
    }
}