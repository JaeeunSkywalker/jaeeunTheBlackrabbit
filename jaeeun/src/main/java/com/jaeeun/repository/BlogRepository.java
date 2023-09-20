package com.jaeeun.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaeeun.domain.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
