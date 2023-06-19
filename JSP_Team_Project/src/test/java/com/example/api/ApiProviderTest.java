package com.example.api;


import com.example.AppConfig;
import com.example.culture.service.CultureService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.example.common.util.SingletonProvideUtil.SINGLETON_UTIL;

@Slf4j
public class ApiProviderTest {
    ApiProvider apiProvider = new CultureJsonApiProvider
            ("6653645678736b6139317441527257","문화행사",new AppConfig().cultureDAO());

    CultureService cultureService = SINGLETON_UTIL.cultureService();

    @Test
    @DisplayName("서울시 문화행사 api 연결")
    void apiProviderTest() throws IOException {
       List<?> list =  apiProvider.apiProvide();
       log.info("불러온 데이터의 수 : "+list.size());
       Assertions.assertNotNull(list);
    }

    @Test
    @DisplayName("서울시 문화행사 api db 최신화")
    void registerServiceTest(){
        Assertions.assertDoesNotThrow(()->{
            cultureService.register();
        });
    }
}
