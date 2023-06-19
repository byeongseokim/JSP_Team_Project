package com.example.review.service;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.review.vo.ReviewVO;

public interface ReviewService {
    Long writeReview(ReviewVO reviewVO);
    PageResponseVO<ReviewVO> getReviews(Long cno, PageRequestVO pageRequestVO);
    ReviewVO updateReview(ReviewVO reviewVO);
    void removeReview(Long re_no);
    ReviewVO getReview(Long re_no);
    ReviewVO getReview(String id,Long cno);
}
