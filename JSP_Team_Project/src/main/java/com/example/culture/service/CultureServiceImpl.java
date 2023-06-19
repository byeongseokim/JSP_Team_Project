package com.example.culture.service;

import com.example.api.ApiProvider;
import com.example.api.ApiRatePolicy;
import com.example.common.vo.PageRequestVO;
import com.example.common.vo.PageResponseVO;
import com.example.culture.dao.CultureDAO;
import com.example.culture.vo.CultureVO;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static com.example.common.util.ConnectionUtil.CONN_UTIL;

@RequiredArgsConstructor
public class CultureServiceImpl implements CultureService {

    private final ApiProvider apiProvider;
    private final ApiRatePolicy apiRatePolicy;
    private final CultureDAO<CultureVO> cultureDAO;

    /**
     * 문화행사 api list로 불러온 후 insert
     */
    @Override
    public void register() {
        Connection conn = CONN_UTIL.getConnection();
        try {
            //가져온 api 데이터로 culture 객체를 완성 후 insert
            List<CultureVO> list = (List<CultureVO>) apiProvider.apiProvide();
            for (CultureVO cultureVO : list) {
                Map<String,Integer> map = apiRatePolicy.apiRatePolicy();
                cultureVO.setCapacity(map.get("capacity"));
                if(cultureVO.getPay_ay_nm().contains("유료")){
                    cultureVO.setPrice(map.get("price"));
                }
                else{
                    cultureVO.setPrice(0);
                }
                //repository에 insert
                cultureDAO.insert(cultureVO);
            }//for

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("api 등록에 실패했습니다");
            throw new RuntimeException(e);
        }
    }

    @Override
    public PageResponseVO<CultureVO> getCultures(PageRequestVO pageRequestVO) {
        return cultureDAO.selectAll(pageRequestVO);
    }

    @Override
    public List<CultureVO> getCultures() {
        return cultureDAO.selectAll();
    }

    @Override
    public CultureVO getCulture(Long cno) {
        return cultureDAO.selectOne(cno);
    }

    @Override
    public void removeAll() {
        cultureDAO.deleteAll();
    }

    @Override
    public PageResponseVO<CultureVO> searchedGetCultures_noType(String keyword, PageRequestVO pageRequestVO) {
        return cultureDAO.selectSearchedCultures_noType(keyword,pageRequestVO);
    }
}
