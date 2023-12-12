package com.ll.medium.domain.post;


import com.ll.medium.domain.comment.CommentForm;
import com.ll.medium.domain.comment.CommentService;
import com.ll.medium.domain.member.MemberService;
import com.ll.medium.domain.member.SiteMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

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

    @PreAuthorize("isAuthenticated()")
    @PostMapping( "/create")
    public String postCreate(
            @Valid PostForm postForm,
            BindingResult bindingResult,
            Principal principal)
    {
        if(bindingResult.hasErrors()){
            return "domain/post/post_form";
        }
        SiteMember siteMember = this.memberService.getMember(principal.getName());
        this.postService.create(postForm.getSubject(), postForm.getContent(), siteMember);
        return "redirect:/post/list";
    }
}
