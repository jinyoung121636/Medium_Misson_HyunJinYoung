package com.ll.medium.domain.comment;

import com.ll.medium.domain.member.MemberService;
import com.ll.medium.domain.member.SiteMember;
import com.ll.medium.domain.post.Post;
import com.ll.medium.domain.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/comment")
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

    // comment 생성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(
            Model model,
            @PathVariable("id") Integer id,
            @Valid CommentForm commentForm,
            BindingResult bindingResult,
            Principal principal)
    {
        Post post = this.postService.getPost(id);
        SiteMember siteMember = this.memberService.getMember(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("post", post);
            return "domain/post/post_detail";
        }
        Comment comment = this.commentService.create(post, commentForm.getContent(), siteMember);
        return String.format("redirect:/post/detail/%s", id);
    }

    //comment 수정(get)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String commentModify(
            CommentForm commentForm,
            @PathVariable("id") Integer id,
            Principal principal)
    {
        Comment comment = this.commentService.getComment(id);
        if(!comment.getAuthor().getMembername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        commentForm.setContent(comment.getContent());
        return "domain/comment/comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(
            Principal principal,
            @PathVariable("id") Integer id)
    {
        Comment comment = this.commentService.getComment(id);
        if(!comment.getAuthor().getMembername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return String.format("redirect:/post/detail/%s", comment.getPost().getId());
    }

    // comment  좋아요/취소

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/like")
    public String commentVote(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getComment(id);
        SiteMember siteMember = this.memberService.getMember(principal.getName());

        if (comment.getVoter().contains(siteMember)) {
            this.commentService.voteCancle(comment, siteMember);
        } else {
            this.commentService.vote(comment, siteMember);
        }
        return String.format("redirect:/post/detail/%s",comment.getPost().getId());
    }


}
