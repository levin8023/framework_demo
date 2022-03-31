package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_log")
public class UserLog {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    private Long userid;
    private String name;
    @TableField("create_time")
    private Date createTime;
}
