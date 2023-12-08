package com.ll.medium.domain.comment;

import com.ll.medium.domain.post.Post;
import com.ll.medium.domain.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/comment")
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(
            Model model,
            @PathVariable("id") Integer id,
            @Valid CommentForm commentForm,
            BindingResult bindingResult)
    {
        Post post = this.postService.getPost(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("post", post);
            return "post_detail";
        }
        this.commentService.create(post, commentForm.getContent());
        return String.format("redirect:/post/detail/%s", id);
    }
}
