package com.zhou.toutiao.dao;

import com.zhou.toutiao.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by zhou on 2019/4/18.
 */
@Mapper
public interface LoginTicketDAO {

    String TABLE_NAME = " login_ticket ";
    String INSET_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id, user_id, ticket, expired, status ";

    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{userId}, #{ticket}, #{expired}, #{status})"})
    int addTicket(LoginTicket loginTicket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    LoginTicket selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where user_id = #{userId}"})
    LoginTicket selectByName(int userId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket = #{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status = #{status} where ticket = #{ticket}"})
    void updateStatus(@Param(value = "ticket") String ticket, @Param(value = "status") int status);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);
}