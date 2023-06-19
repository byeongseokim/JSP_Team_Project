package com.example.qna.dao;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.qna.vo.QnA_A_VO;
import com.example.qna.vo.QnA_Q_VO;

import java.sql.Connection;

public interface QnADAO {

    /**
     * 공통
     */
    // 전체 게시물수 조회
    Integer selectQnAQCnt();
    Integer selectQnAACnt(Long qqno);
    //목록(전체조회)
    PageResponseVO<QnA_Q_VO> selectAllQnAQ(PageRequestVO pageRequestVO);
    //목록(개별조회)
    QnA_Q_VO selectOne(Long qqno, Connection conn);
    PageResponseVO<QnA_A_VO> selectAllQnAA(Long qqno, PageRequestVO pageRequestVO);
    //검색
    PageResponseVO<QnA_Q_VO> searchQnA(PageRequestVO pageRequestVO,String type,String keyword);
    PageResponseVO<QnA_Q_VO> searchQnA_noType(PageRequestVO pageRequestVO,String keyword);
    void deleteAllQnAA(Long qqno,Connection conn);
    int selectSearchedCnt(String keyword);

    /**
     * 고객
     */
    //작성
    Integer insertQnA_Q(QnA_Q_VO qnaq);
    //수정
    QnA_Q_VO updateQnAQ(QnA_Q_VO qnaq,Connection conn);
    QnA_Q_VO updateQnAQ(QnA_Q_VO qnaq);
    //삭제
    Integer deleteQnA_Q(Long qqno,Connection conn);


    /**
     * 댓글
     */
    //작성
    void insertQnA_A(QnA_A_VO qnaa, Connection conn);
    //수정
    QnA_A_VO updateQnAA(QnA_A_VO qnaa);
    //삭제(관리자권한 글목록 삭제)
    //메서드 재활용
    //삭제(댓글 삭제)
    Integer deleteQnA_A(Long qano);
    QnA_A_VO selectOneQnAA(Long qano);

}
