package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.UserLog;
import com.example.springboot.mapper.UserLogMapper;
import com.example.springboot.service.UserLogService;
import org.springframework.stereotype.Service;

@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogService {
}
