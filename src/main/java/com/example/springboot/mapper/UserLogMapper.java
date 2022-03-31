package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.UserLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLogMapper extends BaseMapper<UserLog> {

    @Insert("insert into user_log(userid, name) values(#{userid}, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUserLog(UserLog userLog);

    @Select("select * from user_log")
    List<UserLog> findAll();
}
