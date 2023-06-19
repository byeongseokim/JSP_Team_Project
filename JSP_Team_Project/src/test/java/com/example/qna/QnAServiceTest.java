package com.example.qna;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.qna.service.QnAService;
import com.example.qna.vo.QnA_Q_VO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;
import static org.junit.jupiter.api.Assertions.*;

public class QnAServiceTest {

    QnAService qnAService = SINGLETON_UTIL.qnAService();

    @Test
    @DisplayName("qna 작성")
    public void qna1(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            QnA_Q_VO qnaq = QnA_Q_VO
                    .builder()
                    .id("user1")
                    .title("title"+i)
                    .content("content"+i)
                    .build();
            assertTrue(qnAService.writeQnAQ(qnaq));
        });
    }

    @Test
    @DisplayName("qna 수정")
    public void qna2(){
        QnA_Q_VO qnaq = QnA_Q_VO
                .builder()
                .qqno(1L)
                .id("user1")
                .title("updated title")
                .content("updated content")
                .build();
        assertEquals(qnaq.getContent(),qnAService.modify(qnaq).getContent());
    }

    @Test
    @DisplayName("qna 관리자 삭제")
    public void qna3(){
        String id = "admin";
        Long qqno = 2L;
        qnAService.removeQnAQ(qqno);
    }

    @Test
    @DisplayName("qna 조회 리스트")
    public void qna4(){
        PageResponseVO<QnA_Q_VO> pageResponseVO = qnAService.getQnAQList(PageRequestVO.builder().build());
        assertNotNull(pageResponseVO);
        System.out.println(pageResponseVO);
    }

    @Test
    @DisplayName("qna 조회 단일")
    public void qna5(){
        QnA_Q_VO qnaq = qnAService.getQnaQ(1L);
        System.out.println("qnaq = " + qnaq);
        System.out.println("qnaq.getCnt() = " + qnaq.getCnt());
        assertNotNull(qnaq);
    }

}
