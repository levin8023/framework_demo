package com.example.springboot;

import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MyBatisTransactionTest {

    @Autowired
    private UserService userService;

    @Test
    void insertUser() {
        insertUser("insertUser");
    }

    void insertUser(String name) {
        if (name == null) {
            name = "111";
        }
        User user = new User();
        user.setId(1L);
        user.setName(name);
        // null 值跳过不更新
        user.setCreateTime(null);
        userService.saveOrUpdate(user);
    }

    @Test
    @Transactional()
    void insertUserWithTransactional(String name) {
        User user = new User();
        user.setId(1L);
        user.setName(name);
        userService.saveOrUpdate(user);
    }

    @Test
    @Transactional(readOnly = true)
    void testReadOnlyTransaction() {
        User user = userService.getById(1L);
        user.setName("testReadOnlyTransaction");
        userService.updateById(user);
    }

    @Test
    void updateUser2() {
        User user1 = userService.getById(1L);
        System.out.println("user1 from db: " + user1);
        user1.setName("q");
        userService.updateById(user1);

        User user2 = userService.getById(1L);
        System.out.println("user2 from db: " + user2);
        // data too long
        user2.setName("transactionRollback-transactionRollback");
        userService.updateById(user2);
    }

    /**
     * 事务没有传播，每个方法中的数据库操作都是独立的事务
     */
    @Test
    void transactionRollback() {
        // 事务提交
        insertUser("transactionRollback");
        // 事务提交 报错调用事务回滚
        updateUser2();
    }

    /**
     * 事务传播
     * 事务传播行为（默认值）：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务
     * 如果方法抛出异常，则回滚所有事务；如果方法正常执行，则提交事务
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    void transactionRollbackWithTransactional() {
        // 事务回滚
        insertUser("transactionRollbackWithTransactional");
        // 事务回滚
        updateUser2();
    }

    @Test
    void transactionalBreak() {
        // 事务提交
        insertUserWithTransactional("transactionalBreak");
        // 事务回滚
        transactionRollbackWithTransactional();
    }

}