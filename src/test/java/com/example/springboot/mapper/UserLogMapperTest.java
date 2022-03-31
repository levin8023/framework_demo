package com.example.springboot.mapper;

import com.example.springboot.entity.User;
import com.example.springboot.entity.UserLog;
import com.example.springboot.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserLogMapperTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserLogMapper userLogMapper;
    ObjectWriter objectWriter;

    @BeforeEach
    void setUp() {
        objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    @Test
    void insertUserLog() throws JsonProcessingException {
        User user = new User();
        user.setId(1L);
        user.setName("name");
        userService.saveOrUpdate(user);

        UserLog log = new UserLog();
        log.setUserid(user.getId());
        log.setName("登录");
        userLogMapper.insertUserLog(log);
        System.out.println(objectWriter.writeValueAsString(log));
    }

    @Test
    void findAll() throws JsonProcessingException {
        List<UserLog> all = userLogMapper.findAll();
        System.out.println(objectWriter.writeValueAsString(all));
    }
}