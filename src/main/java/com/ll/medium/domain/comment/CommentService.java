package com.ll.medium.domain.comment;

import com.ll.medium.domain.member.SiteMember;
import com.ll.medium.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Post post, String content, SiteMember siteMember) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setAuthor(siteMember);
        comment.setPost(post);
        this.commentRepository.save(comment);
        return comment;
    }
}
