package com.ll.medium.domain.post;


import com.ll.medium.domain.comment.CommentForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String list(
            Model model,
            @RequestParam(value = "page", defaultValue = "0")
            int page){
        Page<Post> paging = this.postService.getList(page);
        model.addAttribute("paging",paging);
        return "domain/post/post_list";
    }

    @GetMapping("/new")
    public String getNewPost(Model model){
        List<Post> newlist = this.postService.getNewList();
        model.addAttribute("newlist",newlist);
        return "domain/post/post_new";
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
