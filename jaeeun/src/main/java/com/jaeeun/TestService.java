package com.jaeeun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    MemberRepository MemberRepository;

    public List<Member> getAllMembers() {
        return MemberRepository.findAll();
    }

}
