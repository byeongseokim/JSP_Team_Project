package com.example.api;

import java.util.HashMap;
import java.util.Map;

public class ProjectApiRatePolicy implements ApiRatePolicy{
    @Override
    public Map<String,Integer> apiRatePolicy() {
        Map<String,Integer> culture_rp = new HashMap<>();
        //프로젝트용 rate policy 입력
        culture_rp.put("capacity",(int) ((Math.random()*8)+2)*10);
        culture_rp.put("price",((int) ((Math.random()*7)+1))*1000);
        //
        return culture_rp;
    }
}
