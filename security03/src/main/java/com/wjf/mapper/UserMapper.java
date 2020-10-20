package com.wjf.mapper;

import com.wjf.entity.Role;
import com.wjf.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User loadUserByUserName(@Param("username") String username);
    List<Role> getUserRolesByUid(Integer uid);
}
