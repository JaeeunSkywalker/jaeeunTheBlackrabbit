package com.jaeeun;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public void test() {
        // create
        memberRepository.save(new Member(1L, "A"));

        // read
        Optional<Member> member = memberRepository.findById(1L);
        List<Member> allMembers = memberRepository.findAll();

        // delete
        memberRepository.deleteById(1L);
    }

}
