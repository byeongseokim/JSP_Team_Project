package com.example.review.service;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.review.dao.ReviewDAO;
import com.example.review.vo.ReviewVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;

    @Override
    public Long writeReview(ReviewVO reviewVO) {
        return reviewDAO.insert(reviewVO);
    }

    @Override
    public PageResponseVO<ReviewVO> getReviews(Long cno, PageRequestVO pageRequestVO) {
        return reviewDAO.selectAll_byCno(cno, pageRequestVO);
    }

    @Override
    public ReviewVO updateReview(ReviewVO reviewVO) {
        return reviewDAO.update(reviewVO);
    }

    @Override
    public void removeReview(Long re_no) {
        reviewDAO.delete(re_no);
    }

    @Override
    public ReviewVO getReview(Long re_no) {
        return reviewDAO.select(re_no);
    }

    @Override
    public ReviewVO getReview(String id,Long cno) {
        return reviewDAO.select(id,cno);
    }
}
