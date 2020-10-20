package com.wjf.mapper;

import com.wjf.security.MyUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RBACMapper {

    MyUserDetails findByUsername(String username);

    List<String> findRolesByUsername(String username);

    List<String> findURISByUsername(String username);

    List<String> findAuthoritiesByRoles(List<String> roles);

}
