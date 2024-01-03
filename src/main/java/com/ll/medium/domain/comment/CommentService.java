package com.ll.medium.domain.comment;

import com.ll.medium.DataNotFoundException;
import com.ll.medium.domain.member.Member;
import com.ll.medium.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    //comment 생성
    public Comment create(Post post, String content, Member member) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setAuthor(member);
        comment.setPost(post);
        this.commentRepository.save(comment);
        return comment;
    }
    // comment 가져오기
    public Comment getComment(Integer id){
        Optional<Comment> comment = this.commentRepository.findById(id);
        if(comment.isPresent()){
            return comment.get();
        }else{
            throw new DataNotFoundException("comment not found");
        }
    }

    //comment 수정
    public void modify(Comment comment, String content){
        comment.setContent(content);
        this.commentRepository.save(comment);
        // 새 객체를 만들지 않은 이유는 위에 객체가 생성되어있기때문에
        // 객체를 만들면 새로운 이름으로 comment가 기록된다.
    }

    public void delete(Comment comment){
        this.commentRepository.delete(comment);
    }

    public void vote(Comment comment, Member member){
        comment.getVoter().add(member);
        this.commentRepository.save(comment);
    }

    public void voteCancle(Comment comment, Member member){
        comment.getVoter().remove(member);
        this.commentRepository.save(comment);
    }
}
