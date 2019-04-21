package com.zhou.toutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.zhou.toutiao.util.JedisAdapter;
import com.zhou.toutiao.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhou on 2019/4/18.
 */
// 事件生产者，负责把事件放进消息队列。
@Service
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
//        jedisAdapter.setObject(eventModel);
            return true;
        }catch (Exception e) {
            logger.error("事件放入队列失败" + e.getMessage());
            return false;
        }
    }
}