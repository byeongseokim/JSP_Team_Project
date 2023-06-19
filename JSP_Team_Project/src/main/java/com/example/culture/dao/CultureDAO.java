package com.example.culture.dao;

import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.culture.vo.CultureVO;

import java.util.List;

public interface CultureDAO<T> {
    void insert(T t);
    PageResponseVO<CultureVO> selectAll(PageRequestVO pageRequestVO);
    List<CultureVO> selectAll();
    CultureVO selectOne(Long cno);
    int selectCount();
    void deleteAll();
    PageResponseVO<CultureVO> selectSearchedCultures_noType(String keyword, PageRequestVO pageRequestVO);
    int selectSearchedCnt(String keyword);
}
