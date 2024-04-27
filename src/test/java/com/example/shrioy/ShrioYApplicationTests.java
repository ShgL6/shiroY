package com.example.shrioy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//@SpringBootTest
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

}
