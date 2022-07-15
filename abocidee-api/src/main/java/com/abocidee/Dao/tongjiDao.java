package com.abocidee.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface tongjiDao {
    @Update("update abocidee.统计 set 次数=次数+1 where type=#{type} ")
    public void update(String type);
}
