package com.example.productstore.config;
import com.example.productstore.interceptor.ApiKeyInterceptor;
import com.example.productstore.interceptor.ExecutionTimeInterceptor;
import com.example.productstore.interceptor.InterceptorExample;
import com.example.productstore.interceptor.UserActivityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private ApiKeyInterceptor apiKeyInterceptor;

    @Autowired
    private InterceptorExample interceptorExample;

    @Autowired
    private UserActivityInterceptor userActivityInterceptor;

    @Autowired
    private ExecutionTimeInterceptor executionTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/**");

        registry.addInterceptor(interceptorExample)
                .addPathPatterns("/**");

        registry.addInterceptor(userActivityInterceptor)
                .addPathPatterns("/api/products/**", "/api/categories/**");

        registry.addInterceptor(executionTimeInterceptor).addPathPatterns("/api/**");
    }
}
