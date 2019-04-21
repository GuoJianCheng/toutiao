package com.zhou.toutiao.model;

import org.springframework.stereotype.Component;

/**
 * Created by zhou on 2019/4/18.
 * 保存当前访问的用户信息，通过拦截器拦截时注入
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUsers(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}