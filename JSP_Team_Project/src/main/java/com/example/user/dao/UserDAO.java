package com.example.user.dao;


import com.example.user.vo.UserVO;

public interface UserDAO {
    void insert(UserVO userVO);

    UserVO getById(String id);
}
