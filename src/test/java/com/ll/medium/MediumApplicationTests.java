package com.ll.medium;

import com.ll.medium.domain.comment.Comment;
import com.ll.medium.domain.comment.CommentRepository;
import com.ll.medium.domain.member.MemberRepository;
import com.ll.medium.domain.member.MemberService;
import com.ll.medium.domain.member.SiteMember;
import com.ll.medium.domain.post.Post;
import com.ll.medium.domain.post.PostRepository;
import com.ll.medium.domain.post.Post;
import com.ll.medium.domain.post.PostService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MediumApplicationTests {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("리포지터리가 연결되었는지 실행")
    void t1() {
        Post a = new Post();
        a.setSubject("연결됨?");
        a.setContent("아마도");
        a.setCreateDate(LocalDateTime.now());
        postRepository.save(a);
    }

    @Test
    @DisplayName("리포지터리가 연결되었는지 실행")
    void t2() {
        Post a = new Post();
        a.setSubject("연결됨?2");
        a.setContent("아마도2");
        a.setCreateDate(LocalDateTime.now());
        postRepository.save(a);
    }

    @Test
    @DisplayName("리포지터리에서 모든 데이터 조회하기")
    void t3() {
        List<Post> all = this.postRepository.findAll();
        assertEquals(2, all.size());

        Post a = all.get(0);
        assertEquals("연결됨?", a.getSubject());
    }

    @Test
    @DisplayName("리포지터리에서 Id로 데이터 조회하기")
    void t4() {
        Optional<Post> oa = this.postRepository.findById(1);
        if (oa.isPresent()) {
            Post a = oa.get();
            assertEquals("연결됨?", a.getSubject());
        }
    }

    @Test
    @DisplayName("리포지터리에서 Subject로 데이터 조회하기")
    void t5() {
        Post a = this.postRepository.findBySubject("연결됨?");
        assertEquals(1, a.getId());
    }

    @Test
    @DisplayName("리포지터리에서 SubjectAndContent로 데이터 조회하기")
    void t6() {
        Post a = this.postRepository.findBySubjectAndContent("연결됨?", "아마도");
        assertEquals(1, a.getId());
    }

    @Test
    @DisplayName("리포지터리에서 SubjectLike로 데이터 조회하기")
    void t7() {
        List<Post> postListist = this.postRepository.findBySubjectLike("연결%");
        Post a = postListist.get(0);
        assertEquals("연결됨?", a.getSubject());
    }

    @Test
    @DisplayName("리포지터리에서 데이터 수정하기")
    void t8() {
        Optional<Post> oa = this.postRepository.findById(1);
        assertTrue(oa.isPresent());
        Post post = oa.get();
        post.setSubject("수정된 제목");
        this.postRepository.save(post);
    }

    @Test
    @DisplayName("리포지터리에서 데이터 삭제하기")
    void t9() {
        assertEquals(2, postRepository.count());
        Optional<Post> oa = this.postRepository.findById(1);
        assertTrue(oa.isPresent());
        Post post = oa.get();
        this.postRepository.delete(post);
        assertEquals(1, postRepository.count());
    }

    @Test
    @DisplayName("comment 데이터 생성하기")
    void t10() {
        Optional<Post> oa = this.postRepository.findById(2);
        assertTrue(oa.isPresent());
        Post post = oa.get();

        Comment comment = new Comment();
        comment.setContent("생성됐습니다.");
        comment.setPost(post);
        comment.setCreateDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    @Test
    @DisplayName("comment 데이터 조회하기")
    void t11() {
        Optional<Comment> oc = this.commentRepository.findById(1);
        assertTrue(oc.isPresent());
        Comment comment = oc.get();
        assertEquals(2, comment.getPost().getId());
    }

    @Test
    @Transactional
    @DisplayName("aricle에 연결된 comment 찾기")
    void t12() {
        Optional<Post> oa = this.postRepository.findById(2);
        assertTrue(oa.isPresent());
        Post post = oa.get();

        List<Comment> commentList = post.getCommentList();
        assertEquals(1, commentList.size());
        assertEquals("생성됐습니다.", commentList.get(0).getContent());
    }

    @Test
    @DisplayName("테스트용 데이터 생성")
    void t13() {
        for(int i=0; i<5; i++) {
            Post a = new Post();
            a.setSubject("테스트");
            a.setContent("테스트입니다");
            a.setCreateDate(LocalDateTime.now());
            postRepository.save(a);
        }
    }

    @Test
    @DisplayName("테스트용 데이터 생성")
    void t14() {
        SiteMember member = this.memberService.getMember("user1");
        for(int i=0; i<5; i++) {
            Post a = new Post();
            a.setAuthor(member);
            a.setSubject("테스트5");
            a.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
            a.setCreateDate(LocalDateTime.now());
            postRepository.save(a);
        }
    }


}
