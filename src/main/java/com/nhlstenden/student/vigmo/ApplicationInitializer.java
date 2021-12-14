package com.nhlstenden.student.vigmo;

import com.nhlstenden.student.vigmo.config.ApiConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses () {
        return new Class<?>[]{ApiConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses () {
        return null;
    }

    @Override
    protected String[] getServletMappings () {
        return new String[]{"/"};
    }
}