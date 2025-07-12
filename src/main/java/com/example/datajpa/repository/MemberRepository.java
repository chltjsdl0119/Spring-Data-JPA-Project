package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // 스프링 데이터 JPA로 Named 쿼리 호출
    @Query(name = "Member.findByUsername") // 생략 가능
    List<Member> findByUsername(@Param("username") String username);

    // @Query, 리포지토리 메소드에 쿼리 정의하기
    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // @Query, 값, DTO 조회하기
    // 단순히 값 하나를 조회
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO로 직접 조회
    @Query("select new com.example.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // 파라미터 바인딩
    @Query("select m from Member m where m.username = :name")
    Member findMembers(@Param("name") String username);

    // 컬렉션 파라미터 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    // 페이징과 정렬 사용 예제
//    Page<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용
//    Slice<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
//    List<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
//    List<Member> findByUsername(String name, Sort sort);

    // 스프링 데이터 JPA를 사용한 벌크성 수정 쿼리
    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // EntityGraph
    // 공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();
    //JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();
    //메서드 이름으로 쿼리에서 특히 편리하다.
//    @EntityGraph(attributePaths = {"team"})
//    List<Member> findByUsername(String username)

    // NamedEntityGraph 사용 방법
//    @EntityGraph("Member.all")
//    @Query("select m from Member m")
//    List<Member> findMemberEntityGraph();
}
