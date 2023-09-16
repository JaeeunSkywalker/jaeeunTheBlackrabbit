package com.jaeeun;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//얘는 DB와의 매핑 작업에 필요한 인터페이스입니다.

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
