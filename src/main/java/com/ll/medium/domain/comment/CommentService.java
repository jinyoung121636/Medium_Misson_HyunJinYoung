package com.ll.medium.domain.comment;

import com.ll.medium.DataNotFoundException;
import com.ll.medium.domain.member.SiteMember;
import com.ll.medium.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
    // 코멘트 가져오기
    public Comment getComment(Integer id){
        Optional<Comment> comment = this.commentRepository.findById(id);
        if(comment.isPresent()){
            return comment.get();
        }else{
            throw new DataNotFoundException("comment not found");
        }
    }


}
