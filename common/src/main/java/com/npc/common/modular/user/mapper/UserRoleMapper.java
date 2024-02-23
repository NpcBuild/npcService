package com.npc.common.modular.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.npc.common.modular.user.entity.UserRole;
import com.npc.common.modular.user.model.result.UserRoleResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRoleResult> findRoleByUserId(@Param("userId") Integer userId);
}