package com.example.user.dao;


import com.example.user.vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

import static com.example.common.util.ConnectionUtil.CONN_UTIL;

public class JdbcUserDAO implements UserDAO {
    //유저테이블 수정 해야함

    @Override
    public void insert(UserVO userVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into user_basic "
                    + "(id,pwd,name)"
                    + " values(?,?,?) ";
            conn = CONN_UTIL.getConnection();
            Objects.requireNonNull(conn).setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userVO.getId());
            pstmt.setString(2, userVO.getPwd());
            pstmt.setString(3, userVO.getName());
            pstmt.executeUpdate();

            sql = "insert into user_res "
                    + "(id,email,phone)"
                    + " values(?,?,?) ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userVO.getId());
            pstmt.setString(2, userVO.getEmail());
            pstmt.setString(3, userVO.getPhone());
            pstmt.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("user insert를 롤백합니다");
            }
            e.printStackTrace();
            throw new RuntimeException("회원가입에 실패했습니다");
        } finally {
            CONN_UTIL.close(pstmt,conn);
        }
    }

    @Override
    public UserVO getById(String id) {
        String sql = "select basic.id,basic.pwd,basic.name,basic.regDate," +
                "res.email,res.phone,res.payment_amount from " +
                "(user_basic as basic " +
                "inner join user_res as res " +
                "on basic.id=res.id)" +
                "where basic.id = ? " +
                "order by basic.id";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserVO userVO = null;
        try {
            conn = CONN_UTIL.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                userVO = UserVO.builder()
                        .id(rs.getString("id"))
                        .pwd(rs.getString("pwd"))
                        .name(rs.getString("name"))
                        .regDate(new Date(rs.getDate("regDate").getTime()))
                        .email(rs.getString("email"))
                        .phone(rs.getString("phone"))
                        .payment_amount(rs.getInt("payment_amount"))
                        .build();
                return userVO;
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException("회원정보 조회에 실패 했습니다");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
        return null;
    }
}
