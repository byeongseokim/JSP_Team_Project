package com.example.api;

import com.example.culture.dao.CultureDAO;
import com.example.culture.vo.CultureVO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class CultureJsonApiProvider implements ApiProvider{
    private final String KEY; //인증키 6653645678736b6139317441527257
    private final String SUB_CATEGORY; //문화행사
//    private Long cno = 1L;
    private final CultureDAO<CultureVO> cultureDAO;


//    public CultureJsonApiProvider(String KEY, String SUB_CATEGORY) {
//        this.KEY = KEY;
//        this.SUB_CATEGORY = SUB_CATEGORY;
//    }

    @Override
    public List<CultureVO> apiProvide() throws IOException {
        //전체 api 리스트 카운트
        Integer list_total_count = getTotalCnt();
        if(list_total_count==-1){
            throw new IOException();
        }
        List<CultureVO> apiList = new ArrayList<>(); //변환한 데이터를 저장 & 반환 할 리스트
        try {
            //해당 api url을 지정하고
            URL url = new URL("http://openapi.seoul.go.kr:8088/"+KEY+
                    "/json/ListPublicReservationCulture/1/"+list_total_count+"/문화행사");
            //url에서 시스템으로 가져오는 스트림을 열어주고 result에 읽어온다
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String result = br.readLine();
            br.close();
            //얻어온 JSON 정리
            JsonParser jsonParser = new JsonParser();
            //jsonObject 타입으로 파싱
            JsonObject resultByJson = (JsonObject) jsonParser.parse(result);
            //파싱된 result에서 필요한 ListPublicReservationCulture의 value만 추출
            JsonObject ListPublicReservationCulture = (JsonObject) resultByJson.get("ListPublicReservationCulture");

            //ListPublicReservationCulture의 row만 추출
            JsonArray row = (JsonArray) ListPublicReservationCulture.get("row");

            //추출한 row에서 필요한 정보만 culture 객체로 저장
            List<CultureVO> cultureVOList = cultureDAO.selectAll();
            outer:
            for(int i = 0 ; i<row.size();i++){
                JsonObject element = (JsonObject) row.get(i);
                for(CultureVO culture : cultureVOList) {
                    if(parseNonDQM(element.get("SVCID")).equals(culture.getSvc_id())){
                        log.info("SVCID : "+culture.getSvc_id()+"중복");
                        continue outer;
                    }
                }
                CultureVO cultureVO = CultureVO.builder()
//                        .cno(cno)
                        .svc_id(parseNonDQM(element.get("SVCID")))
                        .svc_nm(parseNonDQM(element.get("SVCNM")))
                        .area_nm(parseNonDQM(element.get("AREANM")))
                        .place_nm(parseNonDQM(element.get("PLACENM")))
                        .tel_no(parseNonDQM(element.get("TELNO")))
                        .pay_ay_nm(parseNonDQM(element.get("PAYATNM")))
                        .use_tgt_info(parseNonDQM(element.get("USETGTINFO")))
                        .svc_url(parseNonDQM(element.get("SVCURL")))
                        .img_url(parseNonDQM(element.get("IMGURL")))
                        .dtlcont(parseNonDQM(element.get("DTLCONT")))
                        .svc_opn_bgn_dt(parseNonDQM(element.get("SVCOPNBGNDT")))
                        .svc_opn_end_dt(parseNonDQM(element.get("SVCOPNENDDT")))
                        .v_min(parseNonDQM(element.get("V_MIN")))
                        .v_max(parseNonDQM(element.get("V_MAX")))
                        .rcpt_bgn_dt(parseNonDQM(element.get("RCPTBGNDT")))
                        .rcpt_end_dt(parseNonDQM(element.get("RCPTENDDT")))
                        .revstd_day_nm(parseNonDQM(element.get("REVSTDDAYNM")))
                        .revstd_day(parseNonDQM(element.get("REVSTDDAY")))
                        .build();
                apiList.add(cultureVO);
//                cno++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("api 리스트를 불러오지 못했습니다");
        }
        return apiList;
    }


    public Integer getTotalCnt() {
        try {
            URL url = new URL("http://openapi.seoul.go.kr:8088/"+ KEY +
                    "/json/ListPublicReservationCulture/1/1/"+ SUB_CATEGORY);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String result = br.readLine();
            br.close();
            JsonParser jsonParser = new JsonParser();
            JsonObject resultByJson = (JsonObject) jsonParser.parse(result);
            JsonObject ListPublicReservationCulture = (JsonObject) resultByJson.get("ListPublicReservationCulture");
            //전체 리스트 카운트 저장
            return ListPublicReservationCulture.get("list_total_count").getAsInt();
        } catch (IOException e) {
            return -1;
        }
    }

    //""<-제거해주는 메서드
    public static String parseNonDQM(JsonElement element){
        if(element.isJsonNull()){
            return "";
        }
        String str = String.valueOf(element);
        if(str == null || str.length() < 2){
            return "";
        }
        return str.substring(1,str.length()-1);
    }
}
