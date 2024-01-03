package com.ll.medium.domain.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.URLEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;

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


    @PostMapping("/join")
    public String signup(@Valid MemberCreateForm memberCreateForm, Model model) {
        String username = memberCreateForm.getUsername();
        String email = memberCreateForm.getEmail();

        // 중복 사용자 이름 또는 이메일 확인
        if (memberService.existsByUsernameOrEmail(username, email)) {
            model.addAttribute("errorMessage","이미 존재하는 회원입니다.");
            return "redirect:/member/join";
        }

        // 회원 가입
        Member member= memberService.create(username, email, memberCreateForm.getPassword());
        model.addAttribute("successMessage","회원가입을 축하합니다.");
        return "redirect:/member/login";
    }

    // 로그인 템플릿을 불러줌
    @GetMapping("/login")
    public String login(){
        return "domain/member/login_form";
    }
}