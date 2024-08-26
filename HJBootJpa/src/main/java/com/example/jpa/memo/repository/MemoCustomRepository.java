package com.example.jpa.memo.repository;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.MemberMemoDTO;
import com.example.jpa.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemoCustomRepository {

    int updateTest(String writer, long mno);


//# 1번
//UPDATE PMEMO SET writer = 'prac1' WHERE mno = 10;

//#2
//select * from pmemo inner join member where mno = 10;
List<Memo> mtoJoin01(long mno);
//
//#3
//select m.*, x.*
//from pmemo m
//left join pmember x
//on m.member_id = x.id
//where mno = 10;
    List<Object[]> mtoJoin02(long mno);
//
//#4
//select m.*, x.*
//from pmemo m
//inner join pmember x
//on m.writer = x.name
//where writer = 'admin1';
    List<Object[]> mtoJoin03(String writer);
//
//#5
//SELECT m.*, x.*
//FROM pmemo m
//LEFT JOIN pmember x ON m.member_id = x.id;
//
    List<Memo> mtoJoin04();
//#6
//#1번으로 eager 조인, lazy조인
//

//#7
//#member엔티티에 선언한 list를 이용해서
//SELECT m.*
//FROM member m
//INNER JOIN memo l ON m.id = l.member_id
//WHERE m.id = abc1;
//
    Member otmJoin01(String id);
//#8
//#fetch distinct를 이용해서 중복값 나오는걸 방지 list
//select * from pmember m inner join pmemo x where m.id = 'abc1';
//
    List<Member> otmJoin02(String id);
//#9
//#member와 memo의 dto를 만들어서 경로를 그대로 가져오고 컬럼값들도 가져와서
//select m.*, x.*
//from pmember m inner join pmemo x where m.id = 'abc1';

List<MemberMemoDTO> otmJoin03(String id);

Page<MemberMemoDTO> joinPage(String text, Pageable pageable);


}
