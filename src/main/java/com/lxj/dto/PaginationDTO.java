package com.lxj.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOS;
    private Integer currentPage;
    private Integer totalPage;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showLastPage;
    private List<Integer> pages;

    public void setPagination(Integer currentPage,Integer totalPage){
        if (currentPage != 1) {
            showPrevious = true;
        } else {
            showPrevious = false;
        }
        if (currentPage >= 5) {
            showFirstPage = true;
        } else {
            showFirstPage = false;
        }
        if (currentPage == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        if (currentPage <= totalPage - 4) {
            showLastPage = true;
        } else {
            showLastPage = false;
        }
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(currentPage);
        Integer num = currentPage;
        for (int i = 0; i < 3; i++) {
            if(num <= 1){
                break;
            }
            num--;
            integers.add(num);
        }
        num = currentPage;
        for (int i = 0; i < 3; i++) {
            if(num >= totalPage){
                break;
            }
            num++;
            integers.add(num);
        }
        Collections.sort(integers);
        pages = integers;
    }
}
