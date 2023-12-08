package com.ll.medium.domain.post;


import com.ll.medium.domain.comment.CommentForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model){
        List<Post> postList = this.postService.getList();
        model.addAttribute("postList",postList);
        return "domain/post/post_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id")Integer id, CommentForm commentForm){
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "domain/post/post_detail";
    }

    @GetMapping(value = "/create")
    public String postCreate(PostForm postForm){
        return "/domain/post/post_form";
    }

    @PostMapping(value = "/create")
    public String articleCreate(@Valid PostForm postForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "domain/post/post_form";
        }
        this.postService.create(postForm.getSubject(), postForm.getContent());
        return "redirect:/post/list";
    }
}
