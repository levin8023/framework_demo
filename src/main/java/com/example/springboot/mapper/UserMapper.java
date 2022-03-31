package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    @Results({@Result(property = "createTime", column = "create_time")})
    List<User> findAll();

    @Select("select * from user where id = #{id}")
    @Results({@Result(property = "createTime", column = "create_time")})
    User selectUser(Long id);

    @Insert("insert into user(name) values(#{name})")
    int insertUser(String name);

    @Update("update user set name = #{name} where id = #{id}")
    int updateUser(User user);

    @Delete("delete from user where id = #{id}")
    int deleteUser(Long id);

    @Select("select * from user where name = #{name}")
    @Results({@Result(property = "createTime", column = "create_time")})
    List<User> selectUserByName(String name);

    @Select("select * from user u left join user_log log on u.id = log.id where log.name = #{name}")
    @Results({@Result(property = "createTime", column = "create_time")})
    List<User> selectUserByLogName(String name);

    @Select("select count(*) from user")
    int getUserCount();

    @Select("select * from user where name like concat('%', #{name}, '%')")
    @Results({@Result(property = "createTime", column = "create_time", javaType = java.util.Date.class)})
    List<Map<String, Object>> selectUserByNameLike(String name);

    @Select("select id, name from user where name like concat('%', #{name}, '%')")
    @Results({@Result(property = "createTime", column = "create_time")})
    @MapKey("id")
    Map<String, Object> selectUserByNameAsMap(String name);

    @SelectProvider(type = UserSqlBuilder.class, method = "retrievePageSql")
    Page<Map<String, Object>> retrieveUserBySqlBuilder(Page<?> page, Long userId, String userName, String logName);

    @Select("select user.id, user.name, log.name as logName from user " +
            "left join user_log log on log.userid = user.id ${ew.customSqlSegment}")
    Page<Map<String, Object>> retrieveUserByQueryWrapper(Page<?> page, @Param(Constants.WRAPPER) QueryWrapper<?> wrapper);

    class UserSqlBuilder {
        public static String retrievePageSql(Long userId, String userName, String logName) {
            return new SQL() {{
                SELECT("user.id, user.name, log.name as logName");
                FROM("user user");
                LEFT_OUTER_JOIN("user_log log on log.userid = user.id");
                if (userId != null) {
                    WHERE("user.id = #{userId}");
                }
                if (StringUtils.isNotEmpty(userName)) {
                    WHERE("user.name like concat('%', #{userName}, '%')");
                }
                if (StringUtils.isNotEmpty(logName)) {
                    WHERE("log.name like concat('%', #{logName}, '%')");
                }
                ORDER_BY("user.create_time desc");
            }}.toString();
        }
    }

}
