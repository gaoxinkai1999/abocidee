package com.abocidee.Dao;

import com.abocidee.Domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
    @Select("select * from abocidee.new_user where username =#{username}")
    User get(String username);

    @Insert("insert into abocidee.new_user values (#{username},#{cookie},#{userid})")
    void add(User user);

    @Update("update abocidee.new_user set cookie=#{cookie} where username=#{username}")
    void setcookie(String username, String cookie);
}
