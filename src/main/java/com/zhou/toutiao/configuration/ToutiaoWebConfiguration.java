package com.zhou.toutiao.configuration;

import com.zhou.toutiao.interceptor.LoginRequiredInterceptor;
import com.zhou.toutiao.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zhou on 2019/4/18.
 */
@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        //这个拦截器可以让没有登陆的用户无法访问某些页面、
        //通过url匹配指定拦截的页面。
        //首先要让前一个拦截器来判断用户的状态，然后根据用户状态执行后续的拦截器
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/setting*");
        super.addInterceptors(registry);
    }
}