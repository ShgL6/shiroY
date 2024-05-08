package com.example.shrioy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shrioy.entity.RolePermissions;
import com.example.shrioy.entity.User;
import com.example.shrioy.mapper.RolePermissionsMapper;
import com.example.shrioy.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SpringBootTest
class ShrioYApplicationTests {

    @Test
    void testFindPath() throws IOException {
        System.out.print("ini path: ");
        String iniPath;
        System.out.println(iniPath = this.getClass().getClassLoader().getResource("shiro_config.ini").getPath());
        try(
                BufferedReader reader = new BufferedReader(new FileReader(iniPath))
        ){
            String line;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }
    }

    @Autowired
    DataSource dataSource;

    @Test
    void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");
        while (resultSet.next()){
            System.out.println(resultSet.getString("username"));
        }
        connection.close();

    }

    @Autowired
    UserMapper userMapper;

    @Test
    void testMapper(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUsername, "jack");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user.getUsername() + ": " + user.getPassword());

        queryWrapper.clear();
        queryWrapper.eq(User::getUserId, 1);
        user = userMapper.selectOne(queryWrapper);
        System.out.println(user.getUsername() + ": " + user.getPassword());
    }

    @Autowired
    RolePermissionsMapper rolePermissionsMapper;

    @Test
    void testMapperWithUnderScoreTable(){
        LambdaQueryWrapper<RolePermissions> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermissions::getRoleId, 1);
        List<RolePermissions> rolePermissions = rolePermissionsMapper.selectList(queryWrapper);
        rolePermissions.forEach(System.out::println);
    }

}
