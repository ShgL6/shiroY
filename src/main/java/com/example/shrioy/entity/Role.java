package com.example.shrioy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("roles")
public class Role {
    private Integer roleId;
    private String roleName;
}
