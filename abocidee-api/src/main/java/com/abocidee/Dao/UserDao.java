package com.abocidee.Dao;

import com.abocidee.Domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
    @Select("select * from abocidee.user where username =#{username}")
    User get(String username);

    @Insert("insert into abocidee.user values (#{username},#{cookie},#{userid})")
    void add(User user);

    @Update("update abocidee.user set cookie=#{cookie} where username=#{username}")
    void setcookie(String username, String cookie);
}
