package com.example.jpa;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.MemberMemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.member.repository.MemberRepository;
import com.example.jpa.memo.repository.MemoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class JPACustomTest05 {

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    MemberRepository memberRepository;

//    @Test
//    public void testCode01() {
//        int result = memoRepository.updateTest("구현체를 통해 update", 15L);
//        System.out.println("성공실패: " + result);
//    }

    //Member 테이블의 예시 데이터 삽입
//    @Test
//    public void testCode02() {
//
//        for(int i = 1; i <= 5; i++ ) {
//            memberRepository.save(
//                    Member.builder()
//                            .id("abc"+ i)
//                            .name("admin" + i)
//                            .build()
//                    //sign_date는 JPA대신해서 데이터를 삽입
//            );
//        }
//
//    }

    @Test
    public void testCode03() {
        List<Memo> list = memoRepository.mtoJoin01(10L);
        for(Memo m : list) {
            System.out.println(m.toString());
        }
    }

    @Test
    public void testCode04() {
        List<Object[]> list = memoRepository.mtoJoin02(10L);
        for(Object[] arr : list) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testCode05() {
        List<Object[]> list = memoRepository.mtoJoin03("admin1");
        for(Object[] arr : list) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testCode06() {
        List<Memo> list = memoRepository.mtoJoin04();
        for(Memo m : list) {
            System.out.println(m.toString() );
        }
    }

    @Transactional
    @Test
    public void testCode07() {
        List<Memo> list = memoRepository.mtoJoin01(10L);
        for(Memo m : list) {
            System.out.println(m.toString() );
        }
    }

    //8번 9번 물어보기
    @Test
    public void testCode08() {
        Member m = memoRepository.otmJoin01("abc1");
        System.out.println(m.toString());
    }

    @Test
    public void testCode09() {
        List<Member> list = memoRepository.otmJoin02("abc1");
        for(Member m : list) {
            System.out.println(m.toString());
        }
    }

    //DTO
    @Test
    public void testCode10() {
        List<MemberMemoDTO> list = memoRepository.otmJoin03("abc1");
        for (MemberMemoDTO m : list) {
            System.out.println(m.toString() );
        }
    }

    @Test
    public void testCode11() {
        Page<MemberMemoDTO> list =
                memoRepository.joinPage("1", PageRequest.of(0, 10)); //1을 검색
        for(MemberMemoDTO m : list.getContent()) {
            System.out.println(m.toString() );
        }
        System.out.println("전체게시글 수: " + list.getTotalElements() );
    }
}
