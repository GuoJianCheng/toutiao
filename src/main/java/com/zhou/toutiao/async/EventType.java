package com.zhou.toutiao.async;

/**
 * Created by zhou on 2019/4/18.
 */
// 事件类型枚举类
public enum  EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    SCORE(4);

    private int value;
    EventType(int value) {
        this.value = value;
    }
}