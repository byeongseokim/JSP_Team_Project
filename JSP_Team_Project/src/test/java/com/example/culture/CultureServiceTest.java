package com.example.culture;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.culture.service.CultureService;
import com.example.culture.vo.CultureVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CultureServiceTest {
    CultureService cultureService = SINGLETON_UTIL.cultureService();

    @Test
    @DisplayName("culture list 조회1")
    void getCulturesTest1(){
        PageRequestVO pageRequestVO = PageRequestVO.builder()
                .size(5)
                .build();
        PageResponseVO<CultureVO> pageResponseVO = cultureService.getCultures(pageRequestVO);
        List<CultureVO> cultureVOS = pageResponseVO.getPageList();
        assertEquals(5, cultureVOS.size());
    }
    @Test
    @DisplayName("culture list 조회2")
    void getCulturesTest2(){
        CultureVO cultureVO = cultureService.getCulture(56L);
        assertNotNull(cultureVO);
    }

    @Test
    @DisplayName("culture list 페이징 테스트")
    void getCulturesTest3(){
        PageRequestVO pageRequestVO = PageRequestVO.builder()
                .size(5)
                .build();
        PageResponseVO<CultureVO> pageResponseVO = cultureService.getCultures(pageRequestVO);
        //페이징 테스트
        if(pageResponseVO.isShowPrev()){
            System.out.println("[PREV]");
        }
        System.out.println(pageResponseVO.getPageList().size());
        if(pageResponseVO.isShowNext()){
            System.out.println("[NEXT]");
        }
        System.out.println("pageResponse.getTotal() = " + pageResponseVO.getTotal());
        System.out.println("pageResponse.getLast() = " + pageResponseVO.getLast());
        System.out.println("pageResponse.getEnd() = " + pageResponseVO.getEnd());
    }
}
