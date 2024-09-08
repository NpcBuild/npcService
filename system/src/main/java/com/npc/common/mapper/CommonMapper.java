package com.npc.common.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface CommonMapper {
    @MapKey("id")
    Map getById(int sid);

    Integer getSystemSet();
}
