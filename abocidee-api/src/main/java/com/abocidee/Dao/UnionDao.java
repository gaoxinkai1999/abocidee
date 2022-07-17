package com.abocidee.Dao;

import com.abocidee.Domain.Union;
import com.abocidee.Domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface UnionDao {
    @Select("select * from abocidee.`union` where unionname =#{unionname}")
    public Union get(String unionname);

    @Select("select * from abocidee.`union`")
    public ArrayList<Union> getAll();

    @Update("update abocidee.`union` set cookie=#{cookie} where unionname=#{unionname}")
    public void setCookie(String unionname, String cookie);

    @Insert("insert into abocidee.`union` values (#{unionname},#{cookie},#{unionid},#{token})")
    void add(Union union);
}
