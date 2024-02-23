package com.npc.common.modular.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.user.entity.User;
import com.npc.common.modular.user.model.parame.UserParame;
import com.npc.common.modular.user.model.result.UserResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User>{
    UserResult findByAccount(@Param("account") String account);
    void regist(@Param("user") UserParame user);
    void insert(List<User> users);
}
