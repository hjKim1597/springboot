package com.hj00.prac.util;

import lombok.Data;
/*Criteria 클래스는 주로 페이징 처리와 검색 기능을 구현할 때 사용하는 데이터 전송 객체 DTO*/
@Data
public class Criteria {

    private int page;
    private int amount;

     //검색 키워드
    private String searchName; //상품명
    private String searchContent; //상품내용
    private String searchPrice; //정렬방식
    private String startDate; //판매시작일
    private String endDate; //판매종료일

    public Criteria() {
        this(1, 10);
    }

    public Criteria(int page, int amount) {
        this.page = page;
        this.amount = amount;
    }

    public int getPageStart() {
        return (page - 1) * amount;
    }
}
