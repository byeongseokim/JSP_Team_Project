package com.example.common.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageRequestVO {
    @Builder.Default
    private Integer page=1;
    @Builder.Default
    private Integer size=9;
    public Integer getSkip(){
        return (page-1)*size;
    }
}
