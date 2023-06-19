package com.example.review;


import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.review.service.ReviewService;
import com.example.review.vo.ReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import static com.example.common.util.ConnectionUtil.CONN_UTIL;
import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

@Slf4j
public class ReviewServiceTest {

    private final ReviewService reviewService = SINGLETON_UTIL.reviewService();

    @Test
    @DisplayName("리뷰 작성")
    public void writeReviewTest() throws Exception {
        //given
        Long cno = 53L;
        String id = "user1";
        int count = 0;
        //when
        for(int i=0;i<100;i++){
            ReviewVO reviewVO = ReviewVO.builder()
                    .id(id)
                    .cno(cno)
                    .content("content/"+i)
                    .grade((int)(Math.random()*5+1))
                    .build();
            reviewService.writeReview(reviewVO);
        }
        //then
        Assertions.assertTrue(
                reviewService.getReviews(cno, PageRequestVO.builder().build())
                        .getPageList().size()>0);
    }

    @Test
    @DisplayName("리뷰목록 가져오기+페이징 테스트")
    public void readReviewsTest() throws Exception {
        //given
        Long cno = 1L;
        PageRequestVO pageRequestVO = PageRequestVO.builder().build();
        log.info("page = "+ pageRequestVO.getPage());
        log.info("size = "+ pageRequestVO.getSize());
        //when
        PageResponseVO<ReviewVO> pageResponseVO =
                reviewService.getReviews(cno, pageRequestVO);
        //then
        Assertions.assertNotNull(pageResponseVO);
        System.out.println("pageResponseVO = " + pageResponseVO);

        boolean showPrev = pageResponseVO.isShowPrev();
        System.out.println("showPrev = " + showPrev);
        List<ReviewVO> reviewVOS = pageResponseVO.getPageList();
        System.out.println("reviewVOS = " + reviewVOS);
        boolean showNext = pageResponseVO.isShowNext();
        System.out.println("showNext = " + showNext);
        int start = pageResponseVO.getStart();
        System.out.println("start = " + start);
        int end = pageResponseVO.getEnd();
        System.out.println("end = " + end);
        int last = pageResponseVO.getLast();
        System.out.println("last = " + last);
        int totalCnt = pageResponseVO.getTotal();
        System.out.println("totalCnt = " + totalCnt);
    }

    @Test
    @DisplayName("리뷰 업데이트 테스트")
    public void updateReviewTest(){
        //given
        Long re_no = 2L;
        String updatedContent = "updated Content";
        ReviewVO reviewVO1 = reviewService.getReview(re_no);
        reviewVO1.setContent(updatedContent);
        //when
        ReviewVO reviewVO2 = reviewService.updateReview(reviewVO1);
        //then
        Assertions.assertEquals(reviewVO2.getContent(),updatedContent);
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    public void deleteReviewTest() throws Exception {
        Long re_no = 3L;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete\n" +
                "from review\n" +
                "where re_no = ?";
        conn = CONN_UTIL.getConnection();
        Objects.requireNonNull(conn).setAutoCommit(false);
        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,re_no);
        int rowCnt = pstmt.executeUpdate();
        Assertions.assertEquals(1,rowCnt);
        conn.rollback();
        conn.setAutoCommit(true);
        CONN_UTIL.close(pstmt,conn);
    }
}
