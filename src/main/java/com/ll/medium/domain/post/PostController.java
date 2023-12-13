package com.ll.medium.domain.post;


import com.ll.medium.domain.comment.CommentForm;
import com.ll.medium.domain.comment.CommentService;
import com.ll.medium.domain.member.MemberService;
import com.ll.medium.domain.member.SiteMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    // post / list
    @GetMapping("/list")
    public String list(
            Model model,
            @RequestParam(value = "page", defaultValue = "0")
            int page){
        Page<Post> paging = this.postService.getList(page);
        model.addAttribute("paging",paging);
        return "domain/post/post_list";
    }

    // 최신글 보기
    @GetMapping("/new")
    public String getNewPost(Model model){
        List<Post> newlist = this.postService.getNewList();
        model.addAttribute("newlist",newlist);
        return "domain/post/post_new";
    }

    // 글 상세보기
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id")Integer id, CommentForm commentForm){
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "domain/post/post_detail";
    }

    // 글 생성
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

    // 글 수정(get)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(
            PostForm postForm,
            @PathVariable("id") Integer id,
            Principal principal){
        Post post = this.postService.getPost(id);

        if(!post.getAuthor().getMembername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        postForm.setSubject(post.getSubject());
        postForm.setContent(post.getContent());
        return "domain/post/post_form";
    }

    // 글 수정 (post)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String postModify(
            @Valid PostForm postForm,
            BindingResult bindingResult,
            Principal principal,
            @PathVariable("id") Integer id)
    {
        if(bindingResult.hasErrors()){
            return "domain/post/post_form";
        }
        Post post = this.postService.getPost(id);
        if(!post.getAuthor().getMembername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        this.postService.modify(post, postForm.getSubject(), postForm.getContent());
        return String.format("redirect:/post/detail/%s", id);
    }

    // 글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete(
            Principal principal,
            @PathVariable("id") Integer id)
    {
        Post post = this.postService.getPost(id);
        if(!post.getAuthor().getMembername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.delete(post);
        return "redirect:/";
    }

    // mylist
    @GetMapping("/mylist")
    public String getmylist(Model model){
        List<Post> mylist = this.postService.getMylist("user1");
        model.addAttribute("mylist",mylist);
        return "domain/post/post_mylist";
    }

}
