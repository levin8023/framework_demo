package com.example.springboot.mapper;

import com.example.springboot.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    ObjectWriter objectWriter;

    @BeforeEach
    void setUp() {
        objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    @Test
    void selectUser() {
        User test = userMapper.selectUser(1L);
        System.out.println(test);
    }

    @Test
    void insertUser() {
        userMapper.insertUser("test");
    }

    @Test
    void updateUser() {
        String username = "test";
        User user = new User();
        user.setName("test2");
        int count = userMapper.updateUser(user);
        System.out.println(count);

    }

    @Test
    void deleteUser() {
        userMapper.deleteUser(1L);
    }

    @Test
    void findAll() throws JsonProcessingException {
        List<User> all = userMapper.findAll();
        System.out.println(new ObjectMapper().writeValueAsString(all));
    }

    @Test
    void selectUserByName() throws JsonProcessingException {
        List<User> users = userMapper.selectUserByName("test");
        System.out.println(new ObjectMapper().writeValueAsString(users));
    }


    @Test
    void selectUserByHobby() throws JsonProcessingException {
        List<User> h = userMapper.selectUserByLogName("篮球");
        System.out.println(objectWriter.writeValueAsString(h));
    }

    @Test
    void getUserCount() {
        System.out.println(userMapper.getUserCount());
    }

    @Test
    void selectUserByNameLike() throws JsonProcessingException {
        List<Map<String, Object>> map = userMapper.selectUserByNameLike("t");
        System.out.println(objectWriter.writeValueAsString(map));
    }

    @Test
    void selectUserByNameLike2() throws JsonProcessingException {
        Map<String, Object> map = userMapper.selectUserByNameAsMap("t");
        System.out.println(objectWriter.writeValueAsString(map));
    }
}