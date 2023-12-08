package com.ll.medium.domain.comment;

import com.ll.medium.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void create(Post post, String content){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPost(post);
        this.commentRepository.save(comment);
    }
}
