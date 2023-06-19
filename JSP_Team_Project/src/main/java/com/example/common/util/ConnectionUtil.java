package com.example.common.util;

import com.example.AppConfig;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public final class ConnectionUtil {

    public static final ConnectionUtil CONN_UTIL
            = new ConnectionUtil();

    private final DataSource ds;

    private ConnectionUtil() {
        AppConfig appConfig = new AppConfig();
        this.ds= appConfig.dataSource();
    }

    //getConnection
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            if (conn != null) {
                log.info("DB 연결 성공");
                return conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB 연결 실패");
        }
        return null;
    }

    //close
    public void close(PreparedStatement pstmt,Connection conn){
        try {
            if(pstmt!=null){
                pstmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("db자원 반환에 실패했습니다");
        }
    }
    public void close(ResultSet rs,PreparedStatement pstmt,Connection conn){
        try {
            if(rs!=null){
                rs.close();
            }
            if(pstmt!=null){
                pstmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("db자원 반환에 실패했습니다");
        }
    }

    public void close(ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("db자원 반환에 실패했습니다");
        }
    }

    public void close(PreparedStatement pstmt){
        try {
            if(pstmt!=null){
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("db자원 반환에 실패했습니다");
        }
    }

    public void close(Connection conn){
        try {
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("db자원 반환에 실패했습니다");
        }
    }
}
