package com.ll.medium.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findBySubject(String subject);
    Post findBySubjectAndContent(String subject, String content);
    List<Post> findBySubjectLike(String subject);
    Page<Post> findAll(Pageable pageable);
}
