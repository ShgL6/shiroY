package com.example.shrioy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shrioy.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    default List<Role> selectByRoleId(List<Integer> roleId) {
        return selectList(new LambdaQueryWrapper<Role>().in(Role::getRoleId, roleId));
    }

}
