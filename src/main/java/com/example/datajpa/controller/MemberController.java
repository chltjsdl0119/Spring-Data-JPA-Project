package com.example.datajpa.controller;

import com.example.datajpa.entity.Member;
import com.example.datajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class MemberController {

    private final MemberRepository memberRepository;
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 조회만 가능. 권장하지 않음.
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    // Paging
    // 요청 파라미터 예제: /members?page=0&size=3&sort=id,desc&sort=username,desc
    @GetMapping("/members")
    public Page<Member> list(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }

    // @PageableDefault 사용 예제
//    @RequestMapping(value = "/members_page", method = RequestMethod.GET)
//    public String list(@PageableDefault(size = 12, sort = "username",
//            direction = Sort.Direction.DESC) Pageable pageable) {
//    }
}
