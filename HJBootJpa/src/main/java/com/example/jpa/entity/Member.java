package com.example.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@ToString(exclude = "list") // OneToMany는 ToString()을 사용할 때 무한루프 발생함 그래서 exclude=list를 붙이면 해결 가능
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) //JPA엔티티를 관리하는 영역에서 자동으로 변수에 적용해주는 역할
//오디팅 기능을 활성화 시키려면 main클래스 @EnableJpaAutoding을 추가합니다.
public class Member {

    @Id //PK
    private String id;
    @Column(nullable = false, length = 50) //null 허용x, 길이는 50
    private String name;

    @CreatedDate //JPA를 통해서 인서트시에 날짜가 자동 입력됨
    @Column(updatable = false) //이 컬럼이 JPA에 의해서 자동으로 변경되는 것은 방지합니다.
    private LocalDateTime signDate;

    //원투매니 단방향 조인 => 그냥 사용하게 되면, 연관관계의 주인 1이 되면서 맵핑 테이블 자동생성
    //연관관계 주인 - FK 를 관리하는 주체
//    @OneToMany(fetch = FetchType.EAGER) // OneToMany 의 기본조인 방식은 Lazy 입니다
//    @JoinColumn(name = "member_id") // N테이블의 member_id 컬럼을 Fk로 조인을 걸겠다(적지 않으면 맵핑 테이블 자동생성)
//    private final List<Memo> list = new ArrayList<>();

    //양방향 맵핑
    // ToString 어노테이션을 메모와 멤버 둘중 하나만 (둘다 쓸 경우 오버플로우 오류 발생)
    @OneToMany(mappedBy = "member") //mappedBy는 1쪽에서 지정하고, 연관관계의 주인을 나타내는 키워드 (N쪽의 필드명 member)
    private final List<Memo> list = new ArrayList<>();
}
