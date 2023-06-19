package com.example.common.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseVO<E> {
    private int page;
    private int size;
    private int total;
    private int start;
    private int end;
    private int last;
    private boolean showPrev;
    private boolean showNext;
    private List<E> pageList;

    @Builder(builderMethodName = "withAll")
    public PageResponseVO(PageRequestVO pageRequestVO, List<E> pageList, int total){
        this.page= pageRequestVO.getPage();
        this.size= pageRequestVO.getSize();
        this.pageList = pageList;
        this.total=total;
        this.start=(this.page-1)/10*10+1;
        this.last=(int)(Math.ceil(this.total/(double)this.size));
        this.end= Math.min(this.last, this.start + 9);
        this.showPrev=this.start!=1;
        this.showNext=this.last>this.end;
    }

    //사용법
//    PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>withAll()
//            .pageRequestDTO(pageRequestDTO)
//            .total(boardDAO.getTotal(pageRequestDTO))
//            .dtoList(boardDTOList)
//            .build();
//        return pageResponseDTO;
}