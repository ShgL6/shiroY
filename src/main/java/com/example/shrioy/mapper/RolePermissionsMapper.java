package com.example.shrioy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shrioy.entity.RolePermissions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionsMapper extends BaseMapper<RolePermissions> {
    default List<RolePermissions> getRolePermissionsByRoleId(List<Integer> roleId) {
        return selectList(new LambdaQueryWrapper<RolePermissions>().in(RolePermissions::getRoleId, roleId));
    }
}
