package com.ll.medium.domain.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Article findBySubject(String subject);
    Article findBySubjectAndContent(String subject, String content);
    List<Article> findBySubjectLike(String subject);
}
