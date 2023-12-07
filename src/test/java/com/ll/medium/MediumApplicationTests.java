package com.ll.medium;

import com.ll.medium.domain.article.Article;
import com.ll.medium.domain.article.ArticleRepository;
import com.ll.medium.domain.article.ArticleService;
import com.ll.medium.domain.comment.Comment;
import com.ll.medium.domain.comment.CommentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@SpringBootTest
class MediumApplicationTests {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("리포지터리가 연결되었는지 실행")
    void t1() {
        Article a = new Article();
        a.setSubject("연결됨?");
        a.setContent("아마도");
        a.setCreateDate(LocalDateTime.now());
        articleRepository.save(a);
    }

    @Test
    @DisplayName("리포지터리가 연결되었는지 실행")
    void t2() {
        Article a = new Article();
        a.setSubject("연결됨?2");
        a.setContent("아마도2");
        a.setCreateDate(LocalDateTime.now());
        articleRepository.save(a);
    }

    @Test
    @DisplayName("리포지터리에서 모든 데이터 조회하기")
    void t3() {
        List<Article> all = this.articleRepository.findAll();
        assertEquals(2, all.size());

        Article a = all.get(0);
        assertEquals("연결됨?", a.getSubject());
    }

    @Test
    @DisplayName("리포지터리에서 Id로 데이터 조회하기")
    void t4() {
        Optional<Article> oa = this.articleRepository.findById(1);
        if (oa.isPresent()) {
            Article a = oa.get();
            assertEquals("연결됨?", a.getSubject());
        }
    }

    @Test
    @DisplayName("리포지터리에서 Subject로 데이터 조회하기")
    void t5() {
        Article a = this.articleRepository.findBySubject("연결됨?");
        assertEquals(1, a.getId());
    }

    @Test
    @DisplayName("리포지터리에서 SubjectAndContent로 데이터 조회하기")
    void t6() {
        Article a = this.articleRepository.findBySubjectAndContent("연결됨?", "아마도");
        assertEquals(1, a.getId());
    }

    @Test
    @DisplayName("리포지터리에서 SubjectLike로 데이터 조회하기")
    void t7() {
        List<Article> articleListist = this.articleRepository.findBySubjectLike("연결%");
        Article a = articleListist.get(0);
        assertEquals("연결됨?", a.getSubject());
    }

    @Test
    @DisplayName("리포지터리에서 데이터 수정하기")
    void t8() {
        Optional<Article> oa = this.articleRepository.findById(1);
        assertTrue(oa.isPresent());
        Article article = oa.get();
        article.setSubject("수정된 제목");
        this.articleRepository.save(article);
    }

    @Test
    @DisplayName("리포지터리에서 데이터 삭제하기")
    void t9() {
        assertEquals(2, articleRepository.count());
        Optional<Article> oa = this.articleRepository.findById(1);
        assertTrue(oa.isPresent());
        Article article = oa.get();
        this.articleRepository.delete(article);
        assertEquals(1, articleRepository.count());
    }

    @Test
    @DisplayName("comment 데이터 생성하기")
    void t10() {
        Optional<Article> oa = this.articleRepository.findById(2);
        assertTrue(oa.isPresent());
        Article article = oa.get();

        Comment comment = new Comment();
        comment.setContent("생성됐습니다.");
        comment.setArticle(article);
        comment.setCreateDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    @Test
    @DisplayName("comment 데이터 조회하기")
    void t11() {
        Optional<Comment> oc = this.commentRepository.findById(1);
        assertTrue(oc.isPresent());
        Comment comment = oc.get();
        assertEquals(2, comment.getArticle().getId());
    }

    @Test
    @Transactional
    @DisplayName("aricle에 연결된 comment 찾기")
    void t12() {
        Optional<Article> oa = this.articleRepository.findById(2);
        assertTrue(oa.isPresent());
        Article article = oa.get();

        List<Comment> commentList = article.getCommentList();
        assertEquals(1, commentList.size());
        assertEquals("생성됐습니다.", commentList.get(0).getContent());
    }

    @Test
    @DisplayName("테스트용 데이터 생성")
    void t13() {
        for(int i=0; i<5; i++) {
            Article a = new Article();
            a.setSubject("테스트");
            a.setContent("테스트입니다");
            a.setCreateDate(LocalDateTime.now());
            articleRepository.save(a);
        }
    }
}
