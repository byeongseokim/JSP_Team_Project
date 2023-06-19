package com.example.user;

import com.example.user.service.UserService;
import com.example.user.vo.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

public class UserServiceTest {

    UserService userService = SINGLETON_UTIL.userService();

    @Test
    @DisplayName("유저 조회")
    public void selectTest(){
        UserVO user = userService.getUser("user1");
        Assertions.assertNotNull(user);
    }

    @Test
    @DisplayName("로그인 성공")
    public void loginTest1(){
        boolean login = userService.login("user1", "user1");
        Assertions.assertTrue(login);
    }

    @Test
    @DisplayName("로그인 실패")
    public void loginTest2(){
        Assertions.assertThrows(Exception.class,
                ()->userService.login("user2", "user2"));
    }
}
