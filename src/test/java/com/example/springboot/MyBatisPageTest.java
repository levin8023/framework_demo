package com.example.springboot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserLog;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.UserLogService;
import com.example.springboot.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPageTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private UserMapper userMapper;
    private ObjectWriter objectWriter;

    @BeforeEach
    void setUp() {
        objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    @Test
    @Transactional()
    void insertUser() {
        List<User> users = new ArrayList<>();
        List<UserLog> hobbies = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            User user = new User();
            user.setName("name-" + i);
            users.add(user);
        }
        userService.saveBatch(users);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            UserLog log = new UserLog();
            log.setUserid(user.getId());
            log.setName("log-" + (i + 1));
            hobbies.add(log);
        }
        userLogService.saveBatch(hobbies);
    }

    @Test
    void testPage() throws JsonProcessingException {
        Page<User> userPage = userService.page(new Page<>(1, 10));
        System.out.println(new ObjectMapper().writeValueAsString(userPage.getRecords()));
    }

    @Test
    void retrieveUserBySqlBuilder() throws JsonProcessingException {
        Page<Map<String, Object>> page = new Page<>(1, 10);
        Page<Map<String, Object>> pageData = userMapper.retrieveUserBySqlBuilder(
                page, null, "name", null);
        System.out.println(objectWriter.writeValueAsString(pageData));
    }

    @Test
    void retrieveUserByQueryWrapper() throws JsonProcessingException {
        Page<Map<String, Object>> page = new Page<>(1, 5);
        QueryWrapper<?> wrapper = new QueryWrapper<>();
        wrapper.like(true, "user.name", "name-1");
        Page<Map<String, Object>> pageData = userMapper.retrieveUserByQueryWrapper(page, wrapper);
        System.out.println(objectWriter.writeValueAsString(pageData));
    }

    @Test
    void deleteAllUser() {
        userLogService.remove(null);
        userService.remove(null);
    }
}
