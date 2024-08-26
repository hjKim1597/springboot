package com.example.jpa.memo.repository;

import com.example.jpa.entity.MemberMemoDTO;
import com.example.jpa.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long>, //<엔티티타입, PK(id)에 대한 타입>  //데이터 관리영역
                                        MemoCustomRepository{   //커스텀레포지토리
    //JpaRepository 로 부터 , 몇개의 추상 메서드를 자동으로 상속받게 됨

    //쿼리메서드
    Memo findByWriterAndText(String writer, String text);

    List<Memo> findByMnoLessThan(Long mno);

    //SELECT * FROM MEMO WHERE MNO = 11;
    Memo findByMno(Long mno);
    //SELECT * FROM MEMO WHERE MNO BETWEEN 10 AND 20;
    List<Memo> findByMnoBetween(Long start, Long end);
    //SELECT * FROM MEMO WHERE WRITER LIKE '%10%';
    List<Memo> findByWriterLike(String writer);
    //SELECT * FROM MEMO WHERE WRITER = 'example1' ORDER BY WRITER DESC;
    List<Memo> findByWriterOrderByWriterDesc(String writer);
    //SELECT * FROM MEMO WHERE MNO IN (10, 20, 30, 40, 50);
    List<Memo> findByMnoIn(List<Long> list);

    //쿼리메서드의 마지막 매개변수에 Pageable을 주면, 페이징처리합니다.
    Page<Memo> findByMnoLessThanEqual(Long mno, Pageable pageable);
    
    ////////////////////////////////////////////////////////////////////
    //JPQL - SQL 과 비슷하나 , 엔티티를 사용해서 sql 문을 작성
    //select, update, delete는 제공되는데 , insert는 제공하지 않음
    
    //1. 테이블 명이 아니라 엔티티가 사용됨
    //2. 속성은 대소문자를 전부 구분
    //3. 별칭은 필수 
    //4. SQL 키워드 구분 X

    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc(); // 메서드 명은 자유

    //JPQL 파라미터 전달 @Param(이름), : 이름
    @Query("select m from Memo m where m.mno > :num order by m.mno desc") //:파라미터
    List<Memo> getListDesc2(@Param("num") Long mno);

    //JPQL select 문의 실행 결과를 선별적으로 받으려면 Object[] 사용함
    @Query("select m.writer, m.text from Memo m where m.mno > :num order by m.mno desc")
    List<Object[]> getListDesc3(@Param("num") Long mno);

    //JPQL 업데이트
    @Transactional //트랜잭선 반영
    @Modifying //업데이트임
    @Query("update Memo m set m.writer = :a where m.mno = :b")
    int updateMemo(@Param("a") String a, @Param("b") Long b);

    //JPQL 업데이트 - 객체 파라미터를 넘기는 방법 #{객체}
    @Transactional
    @Modifying
    @Query("update Memo m set m.writer = :#{#a.writer}, m.text = :#{#a.text} where m.mno = :#{#a.mno}")
    int updateMemo(@Param("a") Memo memo);

    //JPQL delete
    //delete from memo where mno = 10;
    @Transactional
    @Modifying
    @Query("delete from Memo m where m.mno = :a ")
    int deleteMemo(@Param("a") Long mno);

    //JPQL도 마지막 매개변수에 Pageable을 주면 , 페이지 처리함
    //page 처리에는 countQuery가 필요합니다 (countQuery 구문은 직접 작성하는게 가능함)
    @Query(value = "select m from Memo m where m.mno <= :a",
           countQuery = "select count(m) from Memo m where m.mno <= :a")
    Page<Memo> getListJPQL(@Param("a") Long mno, Pageable pageable);

    // select mno, writer, text, concat(writer, text) as col, current_timestamp
    // from memo
    // where mno <= 100;
    //페이지네이션 처리하는 JPQL
    @Query("select m.mno, m.writer, m.text, concat(m.writer, m.text) as col, current_timestamp from Memo m where m.mno <= :a" )
    Page<Object[]> getJPQL(@Param("a") Long mno, Pageable pageable);

    //네이티브쿼리 - JPQL 이 너무 어려우면 SQL 방식으로 사용하는 것을 제공함
    @Query(value = "select * from memo where mno = ?", nativeQuery = true)
    Memo getNative(Long mno);

    ////////////////////////////////////////////////////////////////////////////
    /*"select new com.example.jpa.entity.MemberMemoDTO(x.id, x.name, x.signDate, m.mno, m.writer, m.text)" +
                            "from Memo m left join m.member x  where m.text like :text"*/
    @Query(value = "select new com.example.jpa.entity.MemberMemoDTO(x.id, x.name, x.signDate, m.mno, m.writer, m.text) " +
                    "from Memo m left join m.member x where m.text like %:text%"
                    ,countQuery = "select count(m) from Memo m left join m.member x where m.text like %:text%"
        )
            Page<MemberMemoDTO> joinPage(@Param("text") String text, Pageable pageable);



}
