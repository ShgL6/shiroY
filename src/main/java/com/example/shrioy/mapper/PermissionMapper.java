package com.example.shrioy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shrioy.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    default List<Permission> selectByPermissionId(List<Integer> permissionId) {
        return selectList(new LambdaQueryWrapper<Permission>().in(Permission::getPermissionId, permissionId));
    }
}
