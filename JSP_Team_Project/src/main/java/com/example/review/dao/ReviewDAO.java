package com.example.review.dao;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.review.vo.ReviewVO;

public interface ReviewDAO {
    Long insert(ReviewVO reviewVO);
    PageResponseVO<ReviewVO> selectAll_byCno(Long cno, PageRequestVO pageRequestVO);
    ReviewVO update(ReviewVO reviewVO);
    void delete(Long re_no);
    int selectCount(Long cno);
    ReviewVO select(Long re_no);
    ReviewVO select(String id,Long cno);
}
