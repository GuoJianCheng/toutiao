package com.zhou.toutiao.async.handle;

import com.zhou.toutiao.async.EventHandler;
import com.zhou.toutiao.async.EventModel;
import com.zhou.toutiao.async.EventType;
import com.zhou.toutiao.model.Message;
import com.zhou.toutiao.service.MessageService;
import com.zhou.toutiao.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by zhou on 2019/4/18.
 */
@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MailSender mailSender;

    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel eventModel) {
        Message message = new Message();
        message.setToId(eventModel.getActorId());
        message.setCreatedDate(new Date());
        message.setContent("登录异常");
        message.setFromId(eventModel.getEntityOwnerId());
        messageService.addMessage(message);

        Map<String, Object> map = new HashMap<>();
        map.put("username", eventModel.get("username"));
        mailSender.sendWithHTMLTemplate(eventModel.get("email"),"登录异常","mails/welcome.html", map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}