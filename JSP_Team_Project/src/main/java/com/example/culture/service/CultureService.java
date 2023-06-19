package com.example.culture.service;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.culture.vo.CultureVO;

import java.util.List;

public interface CultureService {

    //api 등록 기능
    void register();
    //전체조회 list(limit skip , size)
    PageResponseVO<CultureVO> getCultures(PageRequestVO pageRequestVO);
    //전체조회 list
    List<CultureVO> getCultures();
    //개별조회
    CultureVO getCulture(Long cno);
    //전부삭제
    void removeAll();
    //검색
    PageResponseVO<CultureVO> searchedGetCultures_noType(String keyword, PageRequestVO pageRequestVO);
}
