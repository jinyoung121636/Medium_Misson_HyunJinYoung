package com.ll.medium.domain.member;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 회원가입 템플릿을 불러줌
    @GetMapping("/join")
    public String singup(MemberCreateForm memberCreateForm){
        return "domain/member/join_form";
    }

    // 회원가입 정보를 저장
    @PostMapping("/join")
    public String signup(@Valid MemberCreateForm memberCreateForm){
        Member member = memberService.create(memberCreateForm.getUsername(), memberCreateForm.getEmail(), memberCreateForm.getPassword());
        long id = member.getId();
        return "redirect:/?msg=No %d member joined.".formatted(id);
    }

    // 로그인 템플릿을 불러줌
    @GetMapping("/login")
    public String login(){
        return "domain/member/login_form";
    }
}