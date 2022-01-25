package com.nhlstenden.student.vigmo;

import com.nhlstenden.student.vigmo.config.ApiConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.annotation.Generated;

@Generated("org.springframework") // Is deze klasse gegenereerd? Hebben jullie dit echt niet zelf geschreven? Hmmm?
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ApiConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
