package com.abocidee.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface demoDao {
    @Select("select sum(sys_ban) from oa_danpukj_com.sys_user_base_info")
    public int demo();
}
