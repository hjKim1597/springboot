package com.example.jpa.memo.repository;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.MemberMemoDTO;
import com.example.jpa.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class MemoCustomRepositoryImpl implements MemoCustomRepository {
    //MemoRepository가 MemoCustomRepository를 상속받아줍니다.

    @PersistenceContext //엔티티 매니저를 주입을 받을 때 사용하는 어노테이션
    private EntityManager entityManager;

    @Override
    @Transactional //update구문이므로
    public int updateTest(String writer, long mno) {
        //기존에 인터페이스를 통해서 처리하던 JPQL구문을 직접 작성이 가능합니다.

        String sql = "update Memo m set m.writer = :a where m.mno = :b"; //JPQL

        Query query = entityManager.createQuery(sql);
        query.setParameter("a", writer); //a파라미터에 writer를 채움
        query.setParameter("b", mno);

        int result = query.executeUpdate(); //insert, update, delete구문을 실행할 때 사용

        return result;
    }

    //select * from pmemo inner join member where mno = 10;
    @Override
    public List<Memo> mtoJoin01(long mno) {

        TypedQuery<Memo> result = entityManager.createQuery(
                "select m from Memo m inner join m.member where m.mno <= :mno"
                , Memo.class
        );
        result.setParameter("mno", mno);
        List<Memo> list = result.getResultList();

        return list;
    }

    @Override
    public List<Object[]> mtoJoin02(long mno) {
        //select m.*, x.*
        //from pmemo m
        //left join pmember x
        //on m.member_id = x.id
        //where mno = 10;
        TypedQuery<Object[]> result = entityManager.createQuery(
                "select m, x from Memo m left join m.member x where m.mno <= :mno"
                ,Object[].class
        );
        result.setParameter("mno", mno);
        List<Object[]> list = result.getResultList();

        return list;
    }
    //select m.*, x.*
    //from pmemo m
    //inner join pmember x
    //on m.writer = x.name
    //where writer = 'admin1';
    @Override
    public List<Object[]> mtoJoin03(String writer) {
        TypedQuery<Object[]> result = entityManager.createQuery(
                "select m, x from Memo m inner join m.member x on m.writer = x.name where m.writer = :writer"
                ,Object[].class
        );
        result.setParameter("writer", writer);
        List<Object[]> list = result.getResultList();
        return list;
    }

    @Override
    public List<Memo> mtoJoin04() {
        TypedQuery<Memo> result = entityManager.createQuery(
                "select m from Memo m left join fetch m.member x"
                ,Memo.class
        );

        return result.getResultList();
    }

    //SELECT m.*
    //FROM member m
    //INNER JOIN memo l ON m.id = l.member_id
    //WHERE m.id = abc1;
    @Override
    public Member otmJoin01(String id) {

        TypedQuery<Member> result = entityManager.createQuery(
                "select m from Member m inner join m.list where m.id = :id"
                ,Member.class
        );
        result.setParameter("id", id);
        Member m = result.getSingleResult();

        return m;
    }
    //select * from pmember m inner join pmemo x where m.id = 'abc1';
    @Override
    public List<Member> otmJoin02(String id) {

        TypedQuery<Member> result = entityManager.createQuery(
                "select distinct m from Member m inner join fetch m.list x where m.id = :id"
                        ,Member.class
        );
        result.setParameter("id",id);
        List<Member> list = result.getResultList();

        return list;
    }
//    from pmember m inner join pmemo x where m.id = 'abc1'
    @Override
    public List<MemberMemoDTO> otmJoin03(String id) {

        TypedQuery<MemberMemoDTO> result = entityManager.createQuery(
                "select new com.example.jpa.entity.MemberMemoDTO(m.id, m.name, m.signDate, x.mno, x.writer, x.text)" +
                        "from Member m inner join m.list x where m.id = :id"
                ,MemberMemoDTO.class
        );
        result.setParameter("id", id);
        List<MemberMemoDTO> list = result.getResultList();

        return list;
    }

    @Override
    public Page<MemberMemoDTO> joinPage(String text, Pageable pageable) {

        TypedQuery<MemberMemoDTO> result = entityManager.createQuery(
                "select new com.example.jpa.entity.MemberMemoDTO(x.id, x.name, x.signDate, m.mno, m.writer, m.text)" +
                        "from Memo m left join m.member x  where m.text like :text"
                ,MemberMemoDTO.class
        );
        result.setParameter("text", "%" + text + "%");
        result.setFirstResult((int)pageable.getOffset()); // getOffset() 페이지 시작번호 : 몇번째 데이터 부터 시작할 지 계산 (page number * page size)
        result.setMaxResults(pageable.getPageSize()); //setMaxResults : 한번에 가져올 데이터의 갯수 -> 한페이지에 표시할 데이터의 갯수를 가져옴
        List<MemberMemoDTO> list = result.getResultList();

        //countQuery : 특정 조건에 맞는 데이터의 총 갯수를 계산하기 위함
        Query countQuery = entityManager.createQuery(
                "select count(m) from Memo m left join m.member x where m.writer like : writer"
        );
        countQuery.setParameter("writer", "%" + text+ "%");
        Long count = (Long)countQuery.getSingleResult();

        //PageImpl을 사용하는 이유 : 사용자가 요청한 페이지의 데이터를 쉽게 반환하도록
        PageImpl<MemberMemoDTO> page = new PageImpl<>(list, pageable, count);
        //괄호 안의 list는 현재 페이지에 해당하는 데이터 목록
        //pageable은 페이지 요청에 대한 정보 포함
        //count는 전체 데이터의 총 갯수


        return page;
    }


}
