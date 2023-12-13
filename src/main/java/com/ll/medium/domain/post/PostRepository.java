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
    List<Post> findTop30ByOrderByCreateDateDesc();
    List<Post> findByAuthorMembername(String membername);
    //메서드 이름 규칙 중 하나
//    find: 조회 메서드임을 나타냅니다.

//    Top30: 최상위 30개의 결과를 가져옵니다.
//    By: 이후에 오는 속성에 대한 검색 조건을 설정합니다.
//    OrderByCreateDateDesc: createDate 속성을 기준으로 내림차순 정렬합니다.

}
