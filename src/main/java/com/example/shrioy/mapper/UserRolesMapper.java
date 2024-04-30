package com.example.shrioy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shrioy.entity.UserRoles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRolesMapper extends BaseMapper<UserRoles> {

    default List<UserRoles> selectByUserId(Integer userId) {
        return selectList(new LambdaQueryWrapper<UserRoles>().eq(UserRoles::getUserId, userId));
    }

}
