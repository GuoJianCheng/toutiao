package com.zhou.toutiao.interceptor;

import com.zhou.toutiao.dao.LoginTicketDAO;
import com.zhou.toutiao.dao.UserDAO;
import com.zhou.toutiao.model.HostHolder;
import com.zhou.toutiao.model.LoginTicket;
import com.zhou.toutiao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by zhou on 2019/4/18.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //处理用户信息，判断是否有ticket,一个用户一个ticket，但是有时限
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
            //判断ticket是否过期和无效
            if (ticket != null) {
                LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
                if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                    return true;
                } else {
                    User user = userDAO.selectById(loginTicket.getUserId());
                    //将用户信息报错到当前请求线程中
                    hostHolder.setUsers(user);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //渲染之前提供的后处理方法，可以添加模型数据，自动传给前端
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject(hostHolder.getUser());
            hostHolder.clear();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}