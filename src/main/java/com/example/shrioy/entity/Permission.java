package com.example.shrioy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("permissions")
public class Permission {
    private Integer permissionId;
    private String permissionName;
}
