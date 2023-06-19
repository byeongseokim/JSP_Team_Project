package com.example.qna.dao;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.qna.vo.QnA_A_VO;
import com.example.qna.vo.QnA_Q_VO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.common.util.ConnectionUtil.CONN_UTIL;

public class JdbcQnADAO implements QnADAO{
    @Override
    public Integer insertQnA_Q(QnA_Q_VO qnaq) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into QnA_Q (id, title, content)\n" +
                    "values (?,?,?)";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setString(1,qnaq.getId());
            pstmt.setString(2, qnaq.getTitle());
            pstmt.setString(3,qnaq.getContent());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("insertQnA_Q 실패");
        } finally {
            CONN_UTIL.close(pstmt,conn);
        }
    }

    @Override
    public PageResponseVO<QnA_Q_VO> selectAllQnAQ(PageRequestVO pageRequestVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<QnA_Q_VO> qnaqList = new ArrayList<>();
        try {
            String sql = "select * from QnA_Q order by qqno desc Limit ?,?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setInt(1,pageRequestVO.getSkip());
            pstmt.setInt(2,pageRequestVO.getSize());
            rs = pstmt.executeQuery();
            while(rs.next()){
                QnA_Q_VO qnaq = QnA_Q_VO
                        .builder()
                        .qqno(rs.getLong(1))
                        .id(rs.getString(2))
                        .title(rs.getString(3))
                        .content(rs.getString(4))
                        .cnt(rs.getInt(5))
                        .commentCnt(rs.getInt(6))
                        .regDate(new Date(rs.getDate(7).getTime()))
                        .build();
                if(rs.getDate(8)!=null){
                    qnaq.setUpdateDate(new Date(rs.getDate(8).getTime()));
                }
                qnaqList.add(qnaq);
            }
            return PageResponseVO.<QnA_Q_VO>withAll()
                    .pageRequestVO(pageRequestVO)
                    .pageList(qnaqList)
                    .total(selectQnAQCnt())
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("selectALlQnA 실패");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }

    @Override
    public Integer selectQnAQCnt() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select count(*) from QnA_Q";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("selectCnt from QnA_Q 실패");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
        return null;
    }


    @Override
    public Integer selectQnAACnt(Long qqno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select count(*) from QnA_A where qqno = ?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,qqno);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("selectCnt from QnA_A 실패");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
        return null;
    }

    @Override
    public QnA_Q_VO selectOne(Long qqno,Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from QnA_Q where qqno = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,qqno);
            rs = pstmt.executeQuery();
            if(rs.next()){
              QnA_Q_VO qnaq = QnA_Q_VO
                      .builder()
                      .qqno(rs.getLong(1))
                      .id(rs.getString(2))
                      .title(rs.getString(3))
                      .content(rs.getString(4))
                      .cnt(rs.getInt(5))
                      .commentCnt(rs.getInt(6))
                      .regDate(new Date(rs.getDate(7).getTime()))
                      .build();
              if(rs.getDate(8)!=null){
                  qnaq.setUpdateDate((new Date(rs.getDate(8).getTime())));
              }
              return qnaq;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("selectOne from QnA_Q 실패");
        } finally {
            CONN_UTIL.close(rs);
            CONN_UTIL.close(pstmt);
        }
        return null;
    }

    @Override
    public PageResponseVO<QnA_A_VO> selectAllQnAA(Long qqno, PageRequestVO pageRequestVO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<QnA_A_VO> qnaaList = new ArrayList<>();
        try {
            String sql = "select * from QnA_A where qqno = ? order by qano desc Limit ?,?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,qqno);
            pstmt.setInt(2,pageRequestVO.getSkip());
            pstmt.setInt(3,pageRequestVO.getSize());
            rs = pstmt.executeQuery();
            while(rs.next()){
                QnA_A_VO qnaa = QnA_A_VO
                        .builder()
                        .qano(rs.getLong(1))
                        .qqno(rs.getLong(2))
                        .content(rs.getString(3))
                        .regDate(new Date(rs.getDate(4).getTime()))
                        .id(rs.getString("id"))
                        .build();
                if(rs.getDate(5)!=null){
                    qnaa.setUpdateDate(new Date(rs.getDate(5).getTime()));
                }
                qnaaList.add(qnaa);
            }
            return PageResponseVO.<QnA_A_VO>withAll()
                    .pageRequestVO(pageRequestVO)
                    .pageList(qnaaList)
                    .total(selectQnAACnt(qqno))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("selectAllQnAA 실패");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }

    @Override
    public PageResponseVO<QnA_Q_VO> searchQnA(PageRequestVO pageRequestVO, String type, String keyword) { //type = t c a
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<QnA_Q_VO> qnaqList = new ArrayList<>();
        try {
            if("t".equalsIgnoreCase(type)){ //제목
                String sqlT = "select * from QnA_Q " +
                        "where title like ? " +
                        "order by qqno desc Limit ?,?";
                conn = CONN_UTIL.getConnection();
                pstmt = Objects.requireNonNull(conn).prepareStatement(sqlT);
                pstmt.setString(1,"%"+keyword+"%");
                pstmt.setInt(2,pageRequestVO.getSkip());
                pstmt.setInt(3,pageRequestVO.getSize());
            }
            else if("c".equalsIgnoreCase(type)){ //내용
                String sqlC = "select * from QnA_Q " +
                        "where content like ? " +
                        "order by qqno desc Limit ?,?";
                conn = CONN_UTIL.getConnection();
                pstmt = Objects.requireNonNull(conn).prepareStatement(sqlC);
                pstmt.setString(1,"%"+keyword+"%");
                pstmt.setInt(2,pageRequestVO.getSkip());
                pstmt.setInt(3,pageRequestVO.getSize());
            }
            else { //제목+내용
                String sqlA = "select * from QnA_Q " +
                        "where (title like ? or content like ?) " +
                        "order by qqno desc Limit ?,?";
                conn = CONN_UTIL.getConnection();
                pstmt = Objects.requireNonNull(conn).prepareStatement(sqlA);
                pstmt.setString(1,"%"+keyword+"%");
                pstmt.setString(2,"%"+keyword+"%");
                pstmt.setInt(3,pageRequestVO.getSkip());
                pstmt.setInt(4,pageRequestVO.getSize());
            }
            rs = pstmt.executeQuery();
            while(rs.next()){
                QnA_Q_VO qnaq = QnA_Q_VO
                        .builder()
                        .qqno(rs.getLong(1))
                        .id(rs.getString(2))
                        .title(rs.getString(3))
                        .content(rs.getString(4))
                        .cnt(rs.getInt(5))
                        .commentCnt(rs.getInt(6))
                        .regDate(new Date(rs.getDate(7).getTime()))
//                        .updateDate(new Date(rs.getDate(8).getTime()))
                        .build();
                qnaqList.add(qnaq);
            }
            return PageResponseVO.<QnA_Q_VO>withAll()
                    .pageRequestVO(pageRequestVO)
                    .pageList(qnaqList)
                    .total(selectQnAQCnt())
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("searchQnA 실패");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }

    @Override
    public PageResponseVO<QnA_Q_VO> searchQnA_noType(PageRequestVO pageRequestVO, String keyword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<QnA_Q_VO> qnaqList = new ArrayList<>();
        try {
            String sqlA = "select * from QnA_Q " +
                    "where (title like ? or content like ?) " +
                    "order by qqno desc Limit ?,?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sqlA);
            pstmt.setString(1,"%"+keyword+"%");
            pstmt.setString(2,"%"+keyword+"%");
            pstmt.setInt(3,pageRequestVO.getSkip());
            pstmt.setInt(4,pageRequestVO.getSize());
            rs = pstmt.executeQuery();
            while(rs.next()){
                QnA_Q_VO qnaq = QnA_Q_VO
                        .builder()
                        .qqno(rs.getLong(1))
                        .id(rs.getString(2))
                        .title(rs.getString(3))
                        .content(rs.getString(4))
                        .cnt(rs.getInt(5))
                        .commentCnt(rs.getInt(6))
                        .regDate(new Date(rs.getDate(7).getTime()))
//                        .updateDate(new Date(rs.getDate(8).getTime()))
                        .build();
                qnaqList.add(qnaq);
            }
            return PageResponseVO.<QnA_Q_VO>withAll()
                    .pageRequestVO(pageRequestVO)
                    .pageList(qnaqList)
                    .total(selectSearchedCnt(keyword))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("searchQnA 실패");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
    }


    @Override
    public int selectSearchedCnt(String keyword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select count(*) from QnA_Q " +
                    "where (title like ? or content like ?)";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setString(1,"%"+keyword+"%");
            pstmt.setString(2,"%"+keyword+"%");
            rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("selectCnt from QnA_Q 실패");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
        return -1;
    }

    @Override
    public QnA_Q_VO updateQnAQ(QnA_Q_VO qnaq,Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String sql = "update QnA_Q\n" +
                    "set title = ? , content = ? ,cnt = ?,commentCnt = ? \n" +
                    "where qqno = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setString(1,qnaq.getTitle());
            pstmt.setString(2,qnaq.getContent());
            pstmt.setInt(3,qnaq.getCnt());
            pstmt.setInt(4,qnaq.getCommentCnt());
            pstmt.setLong(5,qnaq.getQqno());
            pstmt.executeUpdate();
            //
            qnaq.setUpdateDate(new Date());
            return qnaq;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("updateQnAQ 실패");
        } finally {
            CONN_UTIL.close(pstmt);
        }
    }

    @Override
    public QnA_Q_VO updateQnAQ(QnA_Q_VO qnaq) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "update QnA_Q\n" +
                    "set title = ? , content = ? , updateDate = now() \n" +
                    "where qqno = ?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setString(1,qnaq.getTitle());
            pstmt.setString(2,qnaq.getContent());
            pstmt.setLong(3,qnaq.getQqno());
            pstmt.executeUpdate();
            //
            qnaq.setUpdateDate(new Date());
            return qnaq;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("updateQnAQ 실패");
        } finally {
            CONN_UTIL.close(pstmt,conn);
        }
    }

    @Override
    public Integer deleteQnA_Q(Long qqno,Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String sql = "delete from QnA_Q where qqno = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,qqno);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("deleteQnA_Q 실패");
        } finally {
            CONN_UTIL.close(pstmt);
        }
    }

    @Override
    public void insertQnA_A(QnA_A_VO qnaa, Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String sql = "insert into QnA_A (qqno, content ,id)\n" +
                    "values (?,?,?)";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,qnaa.getQqno());
            pstmt.setString(2,qnaa.getContent());
            pstmt.setString(3,qnaa.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("insertQnA_A 실패");
        } finally {
            CONN_UTIL.close(pstmt);
        }
    }

    @Override
    public QnA_A_VO updateQnAA(QnA_A_VO qnaa) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "update QnA_A\n" +
                    "set content = ? , updateDate = now() \n" +
                    "where qano = ?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setString(1,qnaa.getContent());
            pstmt.setLong(2,qnaa.getQano());
            pstmt.executeUpdate();
            //
            qnaa.setUpdateDate(new Date());
            return qnaa;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("updateQnAA 실패");
        } finally {
            CONN_UTIL.close(pstmt,conn);
        }
    }

    @Override
    public Integer deleteQnA_A(Long qano) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "delete from QnA_A where qano = ?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,qano);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("deleteQnA_A 실패");
        } finally {
            CONN_UTIL.close(pstmt,conn);
        }
    }

    @Override
    public QnA_A_VO selectOneQnAA(Long qano) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select * from QnA_A where qano = ?";
            conn = CONN_UTIL.getConnection();
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,qano);
            rs = pstmt.executeQuery();
            if(rs.next()){
                 QnA_A_VO qnaA = QnA_A_VO
                        .builder()
                        .qano(rs.getLong(1))
                        .qqno(rs.getLong(2))
                        .content(rs.getString(3))
                        .regDate(new Date(rs.getDate(4).getTime()))
                         .id(rs.getString("id"))
                        .build();
                if(rs.getDate(5)!=null){
                    qnaA.setUpdateDate(new Date(rs.getDate(5).getTime()));
                }
                return qnaA;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("selectOneQnAA 실패");
        } finally {
            CONN_UTIL.close(rs,pstmt,conn);
        }
        return null;
    }

    @Override
    public void deleteAllQnAA(Long qqno,Connection conn) {
        PreparedStatement pstmt = null;
        try {
            String sql = "delete from QnA_A where qqno = ?";
            pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
            pstmt.setLong(1,qqno);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("deleteQnA_A(qqno) 실패");
        } finally {
            CONN_UTIL.close(pstmt);
        }
    }
}
