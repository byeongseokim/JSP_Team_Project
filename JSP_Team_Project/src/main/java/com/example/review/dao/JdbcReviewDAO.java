package com.example.review.dao;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.review.vo.ReviewVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.common.util.ConnectionUtil.CONN_UTIL;

public class JdbcReviewDAO implements ReviewDAO {


    @Override
    public Long insert(ReviewVO reviewVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into review (id, cno, content, grade)\n" +
                    "values (?,?,?,?)";
            conn = CONN_UTIL.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reviewVO.getId());
            pstmt.setLong(2, reviewVO.getCno());
            pstmt.setString(3, reviewVO.getContent());
            pstmt.setInt(4, reviewVO.getGrade());
            pstmt.executeUpdate();
            return reviewVO.getCno();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("리뷰 입력 실패");
        } finally {
            CONN_UTIL.close(pstmt, conn);
        }
    }

    @Override
    public PageResponseVO<ReviewVO> selectAll_byCno(Long cno, PageRequestVO pageRequestVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PageResponseVO<ReviewVO> pageResponseVO = null;
        try {
            String sql = "select * from review \n" +
                    "         where cno = ? \n" +
                    "         order by re_no desc\n" +
                    "limit ?,?";
            conn = CONN_UTIL.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,cno);
            pstmt.setInt(2, pageRequestVO.getSkip());
            pstmt.setInt(3, pageRequestVO.getSize());
            rs = pstmt.executeQuery();
            List<ReviewVO> reviewVOS = new ArrayList<>();
            while (rs.next()) {
                ReviewVO reviewVO = ReviewVO.builder()
                        .re_no(rs.getLong(1))
                        .id(rs.getString(2))
                        .cno(rs.getLong(3))
                        .content(rs.getString(4))
                        .grade(rs.getInt(5))
                        .regDate(rs.getDate(6))
                        .upDate(rs.getDate(7))
                        .build();
                reviewVOS.add(reviewVO);
            }
            pageResponseVO = PageResponseVO.<ReviewVO>withAll()
                    .pageRequestVO(pageRequestVO)
                    .total(selectCount(cno))
                    .pageList(reviewVOS)
                    .build();
            return pageResponseVO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("review 조회에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs, pstmt, conn);
        }
    }

    @Override
    public ReviewVO update(ReviewVO reviewVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "update review\n" +
                    "set content = ? , grade = ? , upDate_time = now() \n" +
                    "where re_no = ?;";
            conn = CONN_UTIL.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,reviewVO.getContent());
            pstmt.setInt(2,reviewVO.getGrade());
            pstmt.setLong(3,reviewVO.getRe_no());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("review 업데이트에 실패했습니다");
        } finally {
            CONN_UTIL.close(pstmt,conn);
        }
        return reviewVO;
    }

    @Override
    public void delete(Long re_no) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "delete\n" +
                    "from review\n" +
                    "where re_no = ?";
            conn = CONN_UTIL.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,re_no);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("review 삭제에 실패했습니다");
        } finally {
            CONN_UTIL.close(pstmt,conn);
        }
    }

    @Override
    public int selectCount(Long cno) {
        String sql = "select count(*) from review " +
                "where cno = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = CONN_UTIL.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,cno);
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("review 조회(count)에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }

    @Override
    public ReviewVO select(Long re_no) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReviewVO reviewVO = null;
        try {
            String sql = "select * from review " +
                    "where re_no = ?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,re_no);
            rs = pstmt.executeQuery();
            if(rs.next()){
                reviewVO = ReviewVO.builder()
                        .re_no(rs.getLong(1))
                        .id(rs.getString(2))
                        .cno(rs.getLong(3))
                        .content(rs.getString(4))
                        .grade(rs.getInt(5))
                        .regDate(rs.getDate(6))
                        .upDate(rs.getDate(7))
                        .build();
            }
            return reviewVO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("review 조회에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs, pstmt, conn);
        }
    }

    @Override
    public ReviewVO select(String id,Long cno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReviewVO reviewVO = null;
        try {
            String sql = "select * from review " +
                    "where id = ? and cno = ?";
            conn = CONN_UTIL.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.setLong(2,cno);
            rs = pstmt.executeQuery();
            if(rs.next()){
                reviewVO = ReviewVO.builder()
                        .re_no(rs.getLong(1))
                        .id(rs.getString(2))
                        .cno(rs.getLong(3))
                        .content(rs.getString(4))
                        .grade(rs.getInt(5))
                        .regDate(rs.getDate(6))
                        .upDate(rs.getDate(7))
                        .build();
            }
            return reviewVO;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("review 조회에 실패했습니다");
        } finally {
            CONN_UTIL.close(rs, pstmt, conn);
        }
    }
}
