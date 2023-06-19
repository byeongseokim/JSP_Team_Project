package com.example.qna.service;

import com.example.common.util.MyTransactional;
import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.qna.dao.QnADAO;
import com.example.qna.vo.QnA_A_VO;
import com.example.qna.vo.QnA_Q_VO;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.common.util.ConnectionUtil.CONN_UTIL;

@RequiredArgsConstructor
public class QnAServiceImpl implements QnAService {

    private final QnADAO qnADAO;

    /**
     * qna 목록 조회
     */
    @Override
    public PageResponseVO<QnA_Q_VO> getQnAQList(PageRequestVO pageRequestVO) {
        return qnADAO.selectAllQnAQ(pageRequestVO);
    }

    /**
     * qna 개별 조회
     */
    @Override
    @MyTransactional
    public QnA_Q_VO getQnaQ(Long qqno) {
        Connection conn = null;
        try {
            conn = CONN_UTIL.getConnection();

            ///////////
            Objects.requireNonNull(conn).setAutoCommit(false);
            ///////////

            QnA_Q_VO qnaq = qnADAO.selectOne(qqno,conn);
            qnaq.setCnt(qnaq.getCnt()+1);
            qnADAO.updateQnAQ(qnaq,conn);

            ////////////
            conn.commit();
            conn.setAutoCommit(true);
            ////////////

            return qnaq;
        } catch (Exception e) {

            /////////////////
            try {
                Objects.requireNonNull(conn).rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("롤백중 예외 발생");
            }
            ////////////////
            e.printStackTrace();
            throw new RuntimeException("rollback");
        } finally {
            CONN_UTIL.close(conn);
        }
    }

    /**
     * qna 조회
     */
    @Override
    public PageResponseVO<QnA_A_VO> getQnAAList(Long qqno, PageRequestVO pageRequestVO) {
        return qnADAO.selectAllQnAA(qqno,pageRequestVO);
    }

    /**
     * qna 검색
     */
    @Override
    public PageResponseVO<QnA_Q_VO> searchedGetQnAQList(String type, String keyword, PageRequestVO pageRequestVO) {
        return qnADAO.searchQnA(pageRequestVO,type,keyword);
    }

    @Override
    public PageResponseVO<QnA_Q_VO> searchedGetQnAQList_noType(String keyword, PageRequestVO pageRequestVO) {
        return qnADAO.searchQnA_noType(pageRequestVO,keyword);
    }

    /**
     * qna 작성
     */
    @Override
    public boolean writeQnAQ(QnA_Q_VO qnaq) {
        return qnADAO.insertQnA_Q(qnaq)==1;
    }

    /**
     * qna 수정
     */
    @Override
    public QnA_Q_VO modify(QnA_Q_VO qnaq) {
        return qnADAO.updateQnAQ(qnaq);
    }

    /**
     * qna 삭제
     */
    @Override
    @MyTransactional
    public void removeQnAQ(Long qqno) {
        Connection conn = CONN_UTIL.getConnection();
        try {
            Objects.requireNonNull(conn).setAutoCommit(false);
            qnADAO.deleteAllQnAA(qqno,conn);
            qnADAO.deleteQnA_Q(qqno,conn);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                Objects.requireNonNull(conn).rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("rollback 실패");
            }
            e.printStackTrace();
            throw new RuntimeException("QnAQ삭제 실패(rollback)");
        } finally {
            CONN_UTIL.close(conn);
        }
    }

    /**
     * qna 댓글 작성
     */
    @Override
    @MyTransactional
    public void writeQnAA(QnA_A_VO qnaa) {
        Connection conn = CONN_UTIL.getConnection();
        try {
            ///////////
            Objects.requireNonNull(conn).setAutoCommit(false);
            ////////////
            qnADAO.insertQnA_A(qnaa,conn);
            QnA_Q_VO qnaQ = qnADAO.selectOne(qnaa.getQqno(), conn);
            qnaQ.setCommentCnt(qnaQ.getCommentCnt()+1);
            qnADAO.updateQnAQ(qnaQ,conn);
            /////////////
            conn.commit();
            conn.setAutoCommit(true);
            /////////////
        } catch (SQLException e) {
            try {
                Objects.requireNonNull(conn).rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException("롤백 실패");
            }
            throw new RuntimeException("conn.rollback");
        } finally {
            CONN_UTIL.close(conn);
        }
    }

    /**
     * qna 댓글 수정
     */
    @Override
    public void modify(QnA_A_VO qnaa) {
        qnADAO.updateQnAA(qnaa);
    }

    /**
     * qna 댓글 삭제
     */
    @Override
    public void removeQnAA(Long qano) {
        qnADAO.deleteQnA_A(qano);
    }


    @Override
    public QnA_A_VO getQnAA(Long qano) {
        return qnADAO.selectOneQnAA(qano);
    }
}
